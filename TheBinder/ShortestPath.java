import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ShortestPath {
  public static int shortestv3(int[][] dist, int V, int start, int end) {
    // am[i][i]=0;
    // am[edge]=edge
    // am[!edge]=inf (max_value/2-100)
    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          if (dist[i][j] > dist[i][k] + dist[k][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
          }
        }
      }
    }
    return dist[start][end];
  }

  public static int dijkstra(AdjacencyList al, int start, int end) {

    AdjustHeap<Distance> heap = new AdjustHeap<Distance>(new HashMap<Distance, Integer>());
    HashMap<Integer, Distance> distances = new HashMap<Integer, Distance>();
    HashSet<Integer> visited = new HashSet<Integer>();
    Distance dist = new Distance();
    dist.vertex = start;
    dist.distance = 0;
    dist.path.add(start);
    heap.offer(dist);
    distances.put(start, dist);
    while (!heap.isEmpty()) {
      Distance visiting = heap.poll();
      visited.add(visiting.vertex);
      if (visiting.vertex == end) {
        break;
      }
      for (Edge e : al.getVertex(visiting.vertex).outList) {
        int addDist = e.capacity;
        if (!visited.contains(e.to)) {
          Distance distObj = distances.get(e.to);
          if (distObj == null) {
            distObj = new Distance();
            distObj.vertex = e.to;
            distObj.distance = Integer.MAX_VALUE;
            distances.put(e.to, distObj);
            heap.offer(distObj);
          }
          if (visiting.distance + addDist < distObj.distance) {
            distObj.distance = visiting.distance + addDist;
            distObj.path.clear();
            distObj.path.addAll(visiting.path);
            distObj.path.add(e.to);
            heap.adjust(distObj);
          }
        }
      }

    }
    Distance endDist = distances.get(end);
    if (endDist == null)
      return -1;// return null;
    else
      return endDist.distance;// return endDist.path;
  }

  static class Distance implements Comparable<Distance> {
    int vertex;
    int distance;
    List<Integer> path = new ArrayList<Integer>();

    @Override
    public int compareTo(Distance arg0) {
      return distance - arg0.distance;
    }

    public int hashCode() {
      return vertex;
    }

    public boolean equals(Object o) {
      return ((Distance) o).vertex == vertex;
    }
  }

  static class AdjacencyList {
    ArrayList<Vertex> vertexList;

    AdjacencyList(int size) {
      vertexList = new ArrayList<Vertex>(size);
      for (int i = 0; i < size; i++) {
        vertexList.add(new Vertex());
      }
    }

    Vertex getVertex(int i) {
      return vertexList.get(i);
    }

    void setEdge(int from, int to, int capacity) {
      vertexList.get(from).outList.add(new Edge(from, to, capacity));
    }

  }

  static class Vertex {
    ArrayList<Edge> outList = new ArrayList<Edge>();
  }

  static class Edge {
    int from;
    int to;
    int capacity;

    Edge(int f, int t, int c) {
      from = f;
      to = t;
      capacity = c;
    }
  }

  public static void main(String[] args) {
    int[][] am = new int[7][7];
    am[0][3] = 5;
    am[0][1] = 3;
    am[0][2] = 8;
    am[3][4] = 7;
    am[3][1] = 10;
    am[2][1] = 9;
    am[2][4] = 2;
    am[1][4] = 14;
    am[2][6] = 10;
    am[4][6] = 4;
    am[4][5] = 6;
    am[6][5] = 2;
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 7; j++) {
        if (i == j) {
          am[i][j] = 0;
        } else if (am[i][j] == 0) {
          am[i][j] = Integer.MAX_VALUE / 2 - 100;
        }
      }
    }
    System.out.println(shortestv3(am, 7, 0, 5));
    AdjacencyList al = new AdjacencyList(7);
    al.setEdge(0, 3, 5);
    al.setEdge(0, 1, 3);
    al.setEdge(0, 2, 8);
    al.setEdge(3, 4, 7);
    al.setEdge(3, 1, 10);
    al.setEdge(2, 1, 9);
    al.setEdge(2, 4, 2);
    al.setEdge(1, 4, 14);
    al.setEdge(2, 6, 10);
    al.setEdge(4, 6, 4);
    al.setEdge(4, 5, 8);
    al.setEdge(6, 5, 2);
    System.out.println(dijkstra(al, 0, 5));
  }
}
