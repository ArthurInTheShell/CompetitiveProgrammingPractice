import java.util.HashMap;
import java.util.Map;

public class AdjustHeap<T extends Comparable<? super T>> {
  private Map<T, Integer> indexMap;
  private Object[] array;
  private int size;

  public AdjustHeap(HashMap<T, Integer> indexMap) {
    this(16, indexMap);
  }

  public AdjustHeap(int size, HashMap<T, Integer> indexMap) {
    array = new Object[size + 1];
    this.size = 0;
    this.indexMap = indexMap;
  }

  public void offer(T t) {
    if (size + 1 == array.length)// array full
      expand();

    int hole = ++size;
    array[hole] = t;
    percUp(hole);
  }

  private void percUp(int hole) {
    array[0] = array[hole];
    for (; compare(0, hole / 2) < 0; hole /= 2) {
      array[hole] = array[hole / 2];
      indexMap.put((T) array[hole], hole);
    }
    array[hole] = array[0];
    indexMap.put((T) array[hole], hole);
  }

  private int compare(int o1, int o2) {
    return ((T) array[o1]).compareTo((T) array[o2]);
  }

  private void expand() {
    Object[] newArray = new Object[array.length * 2];
    System.arraycopy(array, 1, newArray, 1, size);
    array = newArray;
  }

  public T poll() {
    if (size == 0)
      return null;
    T toReturn = (T) array[1];
    array[1] = array[size];
    size--;
    percDown(1);
    indexMap.remove(toReturn);
    return toReturn;
  }

  private void percDown(int hole) {
    array[0] = array[hole];
    int child = hole;
    for (; child * 2 <= size; hole = child) {
      child = hole * 2;
      if (child + 1 <= size && compare(child, child + 1) > 0)
        child++;
      if (compare(0, child) > 0) {
        array[hole] = array[child];
        indexMap.put((T) array[hole], hole);
      } else {
        break;
      }
    }
    array[hole] = array[0];
    indexMap.put((T) array[hole], hole);
  }

  public void adjust(T t) {
    int index = indexMap.get(t);
    if (compare(index, index / 2) < 0) {
      percUp(index);
    } else {
      int child = index * 2;
      if (child + 1 <= size && compare(child, child + 1) > 0)
        child++;
      if (compare(index, child) > 0) {
        percDown(index);
      }
    }
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public static void main(String[] args) {
    AdjustHeap<Mutable> heap = new AdjustHeap<Mutable>(new HashMap<Mutable, Integer>());
    heap.offer(new Mutable(0, 0));
    System.out.println(heap.poll());
    heap.offer(new Mutable(3, 1));
    heap.offer(new Mutable(5, 2));
    heap.offer(new Mutable(4, 3));
    heap.offer(new Mutable(1, 4));
    heap.offer(new Mutable(2, 5));
    System.out.println(heap.poll());
    System.out.println(heap.poll());
    System.out.println(heap.poll());
    Mutable m = new Mutable(6, 6);
    heap.offer(m);
    System.out.println(heap.poll());
    heap.offer(new Mutable(1, 7));
    heap.offer(new Mutable(2, 8));
    System.out.println(heap.poll());
    m.m = 0;
    heap.adjust(m);
    System.out.println(heap.poll());
    System.out.println(heap.poll());
    System.out.println(heap.poll());
    System.out.println(heap.isEmpty());
  }

  static class Mutable implements Comparable<Mutable> {
    int m;
    int id;

    public Mutable(int i, int id) {
      m = i;
      this.id = id;
    }

    @Override
    public int compareTo(Mutable arg0) {
      return m - arg0.m;
    }

    public boolean equals(Object o) {
      return o instanceof Mutable && ((Mutable) o).id == id;
    }

    public int hashCode() {
      return id;
    }

    public String toString() {
      return String.valueOf(m);
    }

  }

}
