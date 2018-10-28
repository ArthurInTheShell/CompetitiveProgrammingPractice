import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MaxFlowEK {
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

    void setEdge(int from, int to, long capacity) {
      vertexList.get(from).outList.add(new EdgeLong(from, to, capacity));
      vertexList.get(to).outList.add(new EdgeLong(to, from, 0));
    }

  }

  static class Vertex {
    ArrayList<EdgeLong> outList = new ArrayList<EdgeLong>();
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

  static class EdgeLong {
    int from;
    int to;
    long capacity;

    EdgeLong(int f, int t, long c) {
      from = f;
      to = t;
      capacity = c;
    }
  }

  /**
   * This destroys the original graph
   * 
   * @param residue
   * @param source
   * @param sink
   * @param V
   * @return
   */
  public static long maxFlowEKLong(AdjacencyList residue, int source, int sink, final int V) {
    long maxFlow = 0;
    // build original

    EdgeLong[] parent = new EdgeLong[V];
    while (maxFlowEKBFSLong(residue, source, sink, parent, V)) {
      long flow = Long.MAX_VALUE;
      int current = sink;
      for (; current != source; current = parent[current].from) {
        EdgeLong e = parent[current];
        flow = Math.min(flow, e.capacity);
      }
      for (current = sink; current != source; current = parent[current].from) {
        EdgeLong e = parent[current];
        e.capacity -= flow;
        for (EdgeLong back : residue.getVertex(e.to).outList) {
          if (back.to == e.from) {
            back.capacity += flow;
            break;
          }
        }
      }
      maxFlow += flow;
    }
    return maxFlow;
  }

  private static boolean maxFlowEKBFSLong(AdjacencyList graph, int source, int sink, EdgeLong[] parent, final int V) {
    boolean[] seen = new boolean[V];
    Queue<Integer> queue = new LinkedList<Integer>();
    queue.offer(source);
    seen[source] = true;
    parent[source] = null;
    while (!queue.isEmpty()) {
      int current = queue.poll();
      if (current == sink)
        return true;
      for (EdgeLong e : graph.getVertex(current).outList) {
        if (e.capacity > 0 && !seen[e.to]) {
          queue.offer(e.to);
          parent[e.to] = e;
          seen[e.to] = true;
        }
      }
    }
    return seen[sink];
  }

  public static long maxFlowEKLong(long[][] graph, int source, int sink, final int V) {
    long maxFlow = 0;
    long[][] residue = new long[V][V];
    // build original
    for (int u = 0; u < V; u++) {
      for (int v = 0; v < V; v++) {
        residue[u][v] = graph[u][v];
      }
    }
    int[] parent = new int[V];
    while (maxFlowEKBFSLong(residue, source, sink, parent, V)) {
      long flow = Long.MAX_VALUE;
      int current = sink;
      for (; current != source; current = parent[current]) {
        flow = Math.min(flow, residue[parent[current]][current]);
      }
      for (current = sink; current != source; current = parent[current]) {
        residue[parent[current]][current] -= flow;
        residue[current][parent[current]] += flow;
      }
      maxFlow += flow;
    }
    return maxFlow;
  }

  private static boolean maxFlowEKBFSLong(long[][] graph, int source, int sink, int[] parent, final int V) {
    boolean[] seen = new boolean[V];
    Queue<Integer> queue = new LinkedList<Integer>();
    queue.offer(source);
    seen[source] = true;
    parent[source] = -1;
    while (!queue.isEmpty()) {
      int current = queue.poll();
      // if (current == sink)
      // return true;
      for (int next = 0; next < V; next++) {
        if (graph[current][next] > 0 && !seen[next]) {
          queue.offer(next);
          parent[next] = current;
          seen[next] = true;
        }
      }
    }
    return seen[sink];
  }

  public static int maxFlowEK(int[][] graph, int source, int sink, final int V) {
    int maxFlow = 0;
    int[][] residue = new int[V][V];
    // build original
    for (int u = 0; u < V; u++) {
      for (int v = 0; v < V; v++) {
        residue[u][v] = graph[u][v];
      }
    }
    int[] parent = new int[V];
    while (maxFlowEKBFS(residue, source, sink, parent, V)) {
      int flow = Integer.MAX_VALUE;
      int current = sink;
      for (; current != source; current = parent[current]) {
        flow = Math.min(flow, residue[parent[current]][current]);
      }
      for (current = sink; current != source; current = parent[current]) {
        residue[parent[current]][current] -= flow;
        residue[current][parent[current]] += flow;
      }
      maxFlow += flow;
    }
    return maxFlow;
  }

  private static boolean maxFlowEKBFS(int[][] graph, int source, int sink, int[] parent, final int V) {
    boolean[] seen = new boolean[V];
    Queue<Integer> queue = new LinkedList<Integer>();
    queue.offer(source);
    seen[source] = true;
    parent[source] = -1;
    while (!queue.isEmpty()) {
      int current = queue.poll();
      // if (current == sink)
      // return true;
      for (int next = 0; next < V; next++) {
        if (graph[current][next] > 0 && !seen[next]) {
          queue.offer(next);
          parent[next] = current;
          seen[next] = true;
        }
      }
    }
    return seen[sink];
  }

  public static void main(String[] args) {

    int[][] graph = { { 0, 20, 10, 0 }, { 0, 0, 30, 10 }, { 0, 0, 0, 20 }, { 0, 0, 0, 0 } };
    System.out.println(maxFlowEK(graph, 0, 3, 4));
  }
}
