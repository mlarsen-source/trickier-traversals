import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {

    if(node == null) return 0;
    if(node.left == null && node.right == null) return node.value;
    int left = sumLeafNodes(node.left);
    int right = sumLeafNodes(node.right);
    return left + right;
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    if(node == null) return 0;
    if(node.left == null && node.right == null) return 0;
    int left = countInternalNodes(node.left);
    int right = countInternalNodes(node.right);
    return left + right + 1;
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {
    if(node == null) return "";
    String left = buildPostOrderString(node.left);
    String right = buildPostOrderString(node.right);
    return left + right + node.value;
  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) 
  {
    List<T> list = new ArrayList<>();
    Queue<TreeNode<T>> queue = new LinkedList<>();
    if(node == null) return list;
    queue.offer(node);
    while(!queue.isEmpty()) {
      TreeNode<T> temp = queue.poll();
      if(temp != null) {
        list.add(temp.value);
        queue.add(temp.left);
        queue.add(temp.right);
      }
    }
    return list;
  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
  
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
    Set<Integer> set = new HashSet<>();
    Queue<TreeNode<Integer>> queue = new LinkedList<>();
    if(node == null) return 0;
    queue.offer(node);
    while(!queue.isEmpty()) {
      TreeNode<Integer> temp = queue.poll();
      if(temp != null) {
        set.add(temp.value);
        queue.add(temp.left);
        queue.add(temp.right);
      }
    }
    return set.size();
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    if(node == null) return false;
    if (node.left == null && node.right == null) return true;
    boolean left = node.left !=null && node.left.value > node.value && hasStrictlyIncreasingPath(node.left);
    boolean right = node.right!=null && node.right.value > node.value && hasStrictlyIncreasingPath(node.right);
    return left || right;
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    if(nodeA == null && nodeB == null) return true;
    if (nodeA == null || nodeB == null) return false;
    boolean left = haveSameShape(nodeA.left, nodeB.left);
    boolean right = haveSameShape(nodeA.right, nodeB.right);
    return left && right;
  }


  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    List<List<T>> listA = new ArrayList<>();
    List<T> listB = new ArrayList<>();  
    Stack<TreeNode<T>> nodeStack = new Stack<>();
    Stack<List<T>> listStack = new Stack<>();
    if(node == null) return listA;
    
    listB.add(node.value);
    nodeStack.push(node);
    listStack.push(listB);

    while(!nodeStack.isEmpty()) {
      TreeNode<T> tempNode = nodeStack.pop();
      List<T> tempList = listStack.pop();
      
      if (tempNode.left == null && tempNode.right == null) {
        listA.add(tempList);
      }

      if(tempNode.right != null) {
        nodeStack.push(tempNode.right);
        List<T> rightList = new ArrayList<>(tempList);
        rightList.add(tempNode.right.value);
        listStack.push(rightList);
      }

      if(tempNode.left != null) {
        nodeStack.push(tempNode.left);
        List<T> leftList = new ArrayList<>(tempList);
        leftList.add(tempNode.left.value);
        listStack.push(leftList);
      }
    }
    return listA;
  }
}
