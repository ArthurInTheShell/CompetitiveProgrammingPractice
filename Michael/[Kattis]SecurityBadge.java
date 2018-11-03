import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;

public class SecurityBadge {
  static class AdjacencyList {
    Vertex[] vertices;

    AdjacencyList(int size) {
      vertices = new Vertex[size];
      for (int i = 0; i < size; i++) {
        vertices[i] = new Vertex();
      }
    }

    void addEdge(int from, int to, int lo, int hi) {
      Edge e = new Edge(from, to, lo, hi);
      vertices[from].outList.add(e);
    }

    boolean bfs(int from, int to, int lo, int hi) {
      if (from == to)
        return true;
      Queue<Vertex> q = new LinkedList<Vertex>();
      BitSet visited = new BitSet();
      q.offer(vertices[from]);
      visited.set(from);
      while (!q.isEmpty()) {
        Vertex current = q.poll();
        for (Edge e : current.outList) {
          if (!visited.get(e.to)) {
            if (e.canPass(lo, hi)) {
              if (e.to == to)
                return true;
              visited.set(e.to);
              q.offer(vertices[e.to]);
            }
          }
        }
      }
      return false;
    }
  }

  static class Vertex {
    ArrayList<Edge> outList = new ArrayList<Edge>();
  }

  static class Edge {
    int from;
    int to;
    int lo;
    int hi;

    public Edge(int from, int to, int lo, int hi) {
      super();
      this.from = from;
      this.to = to;
      this.lo = lo;
      this.hi = hi;
    }

    boolean canPass(int l, int h) {
      return l >= lo && h <= hi;
    }
  }

  private static int solve(AdjacencyList graph, Integer[] ranges, int from, int to) {
    int total = 0;
    for (int i = 0; i < ranges.length - 1; i++) {
      int lo = ranges[i];
      int hi = ranges[i + 1];
      if (graph.bfs(from, to, lo, hi - 1)) {
        total += hi - lo;
      }
    }

    return total;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int V = scan.nextInt();
    int E = scan.nextInt();
    int B = scan.nextInt();
    int from = scan.nextInt();
    int to = scan.nextInt();
    TreeSet<Integer> limits = new TreeSet<Integer>();
    AdjacencyList graph = new AdjacencyList(V + 1);
    for (int i = 0; i < E; i++) {
      int a = scan.nextInt();
      int b = scan.nextInt();
      int c = scan.nextInt();
      int d = scan.nextInt();
      graph.addEdge(a, b, c, d);
      limits.add(c);
      limits.add(d + 1);
    }
    limits.add(B + 1);
    Integer[] ranges = limits.toArray(new Integer[limits.size()]);
    scan.close();
    System.out.println(solve(graph, ranges, from, to));
  }
}
