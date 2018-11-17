import java.util.ArrayDeque;
import java.util.Queue;

public class SuffixTree {

  static final char END = '$';
  static final int CHAR_TOTAL = 27;// 256 for all ASCII
  private String str;
  private int endGlobal;

  class Node {
    int start;// -1 if root
    int end;// -2 if end global
    int leaveValue = -1;// -1 if internal
    Node suffixLink;
    Node[] nodes;

    Node() {
      nodes = new Node[CHAR_TOTAL];
    }

    int start() {
      return start;
    }

    int end() {
      return end == -2 ? endGlobal : end;
    }

//    void toString(StringBuilder build, int depth) {
//      if (start == -1) {
//        // root
//        build.append("R, ");
//      } else {
//        System.err.println(leaveValue);
//        build.append(depth).append("e[").append(start).append("-").append(end()).append("]")
//            .append(str.substring(start, end() + 1));
//        if (leaveValue != -1) {
//          build.append("(").append(leaveValue).append(")");
//        }
//        build.append(", ");
//      }
//      for (Node next : nodes) {
//        if (next != null)
//          next.toString(build, depth + 1);
//      }
//    }

//    void fillSuffix(List<String> suffix, String path) {
//      if (leaveValue != -1) {
//        suffix.add("(" + leaveValue + ")" + path + str.substring(start, end() + 1));
//      } else {
//        if (start != -1) {
//          path += str.substring(start, end() + 1);
//        }
//        for (Node next : nodes) {
//          if (next != null)
//            next.fillSuffix(suffix, path);
//        }
//      }
//    }

    int edgeLength() {
      return end() - start() + 1;
    }

    boolean walkDown() {
      if (activeLength >= edgeLength()) {
        activeEdge += edgeLength();
        activeLength -= edgeLength();
        activeNode = this;
        return true;
      }
      return false;
    }

  }

  private Node root;
  private int activeLength;
  private int activeEdge;
  private Node activeNode;

  private int remaining;
  private Node lastNewNode;

  public SuffixTree(String str) {
    this.str = str + END;
    build();
  }

  private Node newNode(int start, int end) {
    Node node = new Node();
    node.suffixLink = root;
    node.start = start;
    node.end = end;
    node.leaveValue = -1;
    return node;
  }

  private void extend(int pos) {
    endGlobal = pos;

    remaining++;

    lastNewNode = null;

    while (remaining > 0) {
      if (activeLength == 0)
        activeEdge = pos;
      int edgeIndex = charToEdge(str.charAt(activeEdge));
      if (activeNode.nodes[edgeIndex] == null) {
        activeNode.nodes[edgeIndex] = newNode(pos, -2);// end global;

        if (lastNewNode != null) {
          lastNewNode.suffixLink = activeNode;
          lastNewNode = null;
        }
      } else {
        Node next = activeNode.nodes[edgeIndex];
        if (next.walkDown()) {
          continue;
        }
        if (str.charAt(next.start() + activeLength) == str.charAt(pos)) {
          if (lastNewNode != null && activeNode != root) {
            lastNewNode.suffixLink = activeNode;
            lastNewNode = null;
          }
          activeLength++;
          break;
        }
        int splitEnd = next.start() + activeLength - 1;
        Node split = newNode(next.start(), splitEnd);
        activeNode.nodes[edgeIndex] = split;
        split.nodes[charToEdge(str.charAt(pos))] = newNode(pos, -2);
        next.start += activeLength;
        split.nodes[charToEdge(str.charAt(next.start))] = next;

        if (lastNewNode != null) {
          lastNewNode.suffixLink = split;
        }
        lastNewNode = split;
      }

      remaining--;
      if (activeNode == root && activeLength > 0) {
        activeLength--;
        activeEdge++;// = pos - remaining + 1;
      } else if (activeNode != root) {
        activeNode = activeNode.suffixLink;
      }
    }
  }

  private void setLeafIndices(Node n, int labelHeight) {
    if (n == null)
      return;
    boolean isLeaf = true;
    for (Node child : n.nodes) {
      if (child != null) {
        isLeaf = false;
        setLeafIndices(child, labelHeight + child.edgeLength());
      }
    }
    if (isLeaf) {
      n.leaveValue = str.length() - labelHeight;
    }
  }

  private void build() {
    root = newNode(-1, -1);
    activeNode = root;
    for (int i = 0; i < str.length(); i++) {
      extend(i);
    }
    setLeafIndices(root, 0);
  }

  private int charToEdge(char c) {
    if (c == END)
      return 26;
    else
      return c - 'a';
    // return (int) c for all ASCII
  }

//  public String toString() {
//    StringBuilder build = new StringBuilder("SuffixTree" + System.identityHashCode(this));
//    build.append("[");
//    root.toString(build, 0);
//    build.append("]");
//    List<String> suf = new ArrayList<String>();
//    root.fillSuffix(suf, "");
//    build.append(suf.toString());
//    return build.toString();
//  }

  public String longestRepeatedSubString() {
    // go down each branch
    int maxLength = 0;
    int start = 0;
    int end = 0;
    Queue<Node> bfsQ = new ArrayDeque<Node>();
    bfsQ.offer(root);
    while (!bfsQ.isEmpty()) {
      Node c = bfsQ.poll();
      boolean hasNextInternal = false;
      for (Node next : c.nodes) {
        if (next != null && next.leaveValue == -1) {
          bfsQ.offer(next);
          hasNextInternal = true;
        }
      }
      if (!hasNextInternal) {
        for (Node next : c.nodes) {
          if (next != null) {
            int newEnd = next.start();
            int newStart = next.leaveValue;
            if (newEnd - newStart > maxLength) {
              maxLength = newEnd - newStart;
              end = newEnd;
              start = newStart;
            }
            break;
          }
        }
      }
    }
    return str.substring(start, end);
  }

  public static void main(String[] args) {
    SuffixTree tree = new SuffixTree("abababa");
    System.out.println(tree.longestRepeatedSubString());
  }

}
