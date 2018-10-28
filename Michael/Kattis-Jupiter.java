import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Jupiter {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int W = scan.nextInt();
    int Q = scan.nextInt();
    int S = scan.nextInt();
    int V = 2 + 2 * Q * W + W;
    int[] sensorToQueue = new int[S];
    for (int i = 0; i < S; i++) {
      sensorToQueue[i] = scan.nextInt() - 1;
    }
    int[] queueSize = new int[Q];
    for (int i = 0; i < Q; i++) {
      queueSize[i] = scan.nextInt();
    }
    int[] windowSize = new int[W];
    long[][] dataFlow = new long[W][Q];
    for (int i = 0; i < W; i++) {
      windowSize[i] = scan.nextInt();
      for (int j = 0; j < S; j++) {
        long data = scan.nextLong();
        dataFlow[i][sensorToQueue[j]] += data;
      }
    }
    scan.close();
    AdjacencyList graph = new AdjacencyList(V);
    long dataLoad = 0;
    for (int w = 0; w < W; w++) {
      int windowHead = windowHead(w, Q, W);
      for (int q = 0; q < Q; q++) {
        int head = queueHead(q, w, Q);
        // System.err.print(dataFlow[w][q] + " ");
        dataLoad += dataFlow[w][q];
        graph.setEdge(0, head, dataFlow[w][q]);
        graph.setEdge(head, head + 1, queueSize[q]);
        if (w < W - 1) {
          int nextHead = queueHead(q, w + 1, Q);
          graph.setEdge(head + 1, nextHead, Long.MAX_VALUE);
        }
        graph.setEdge(head + 1, windowHead, Long.MAX_VALUE);
      }
      graph.setEdge(windowHead, V - 1, windowSize[w]);
      // System.err.println();
    }

    long maxFlow = maxFlowEKLong(graph, 0, V - 1, V);
//    for (long[] arr : graph) {
//      for (long l : arr) {
//        if (l > Integer.MAX_VALUE)
//          System.err.print("i ");
//        else
//          System.err.print(l + " ");
//      }
//      System.err.println();
//    }
    // System.out.println(maxFlow);
    // System.out.println(dataLoad);
    System.out.println(maxFlow >= dataLoad ? "possible" : "impossible");

  }

  private static int queueHead(int q, int w, int Q) {
    return 1 + Q * w * 2 + q * 2;
  }

  private static int windowHead(int w, int Q, int W) {
    return 1 + Q * W * 2 + w;
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

}
