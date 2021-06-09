import java.util.*;

class Node {
    Integer value;
    Node left;
    Node right;
    public Node(int value)
    {
        this.value = value;
        left = right = null;
    }
}

class BinaryTree {
    Node root;
    BinaryTree() { root = null; }

    BinaryTree(Node root){
        this.root = root;
    }
    public void binarySearchTreeToList(Node node, ArrayList<Node> treeNodes){
        if(node == null){
            return;
        }
        binarySearchTreeToList(node.left, treeNodes);
        treeNodes.add(node);
        binarySearchTreeToList(node.right, treeNodes);
    }
/////////////////////////////// NEEDS REFACTORING ///////////////////////////////////////////

    public Node insert(Node root, int key)
    {
        // if the root is null, create a new node and return it
        if (root == null) {
            return new Node(key);
        }

        // if the given key is less than the root node, recur for the left subtree
        if (key < root.value) {
            root.left = insert(root.left, key);
        }

        // if the given key is more than the root node, recur for the right subtree
        else {
            root.right = insert(root.right, key);
        }

        return root;
    }


    void deleteDeepest(Node root, Node delNode)
    {
        Queue<Node> elementsAfterNodeToDelete = new LinkedList<Node>();
        elementsAfterNodeToDelete.add(root);
        Node temp = null;
        // Do level order traversal until last node
        while (!elementsAfterNodeToDelete.isEmpty())
        {
            temp = elementsAfterNodeToDelete.peek();
            elementsAfterNodeToDelete.remove();
            if (temp == delNode)
            {
                temp = null;
                return;
            }
            if (temp.right!=null)
            {
                if (temp.right == delNode)
                {
                    temp.right = null;
                    return;
                }
                else
                    elementsAfterNodeToDelete.add(temp.right);
            }

            if (temp.left != null)
            {
                if (temp.left == delNode)
                {
                    temp.left = null;
                    return;
                }
                else
                    elementsAfterNodeToDelete.add(temp.left);
            }
        }
    }

    void deleteNode(Node root, int key)
    {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            if (root.value == key)
            {
                root = null;
                return;
            }
            else
                return;
        }
        Queue<Node> elementsAfterNodeToDelete = new LinkedList<Node>();
        elementsAfterNodeToDelete.add(root);
        Node temp = null;
        Node keyNode = null;
        while (!elementsAfterNodeToDelete.isEmpty())
        {
            temp = elementsAfterNodeToDelete.peek();
            elementsAfterNodeToDelete.remove();
            if (temp.value == key)
                keyNode = temp;
            if (temp.left != null)
                elementsAfterNodeToDelete.add(temp.left);
            if (temp.right != null)
                elementsAfterNodeToDelete.add(temp.right);
        }
        if (keyNode != null)
        {
            int x = temp.value;
            deleteDeepest(root, temp);
            keyNode.value = x;
        }
    }



    /////////////////////////////// NEEDS REFACTORING ///////////////////////////////////////////


}

public class Main {

    static void printInPreorder(Node node) {
        if (node == null) {
            return;
        }
        printInPreorder(node.left);
        System.out.print(node.value + " ");
        printInPreorder(node.right);
    }

    static void printNodesInList(ArrayList <Node> treeNodes){
        for(Node node : treeNodes){
            System.out.print( " " + node.value);
        }
    }

    static Node findMisplacedElement(ArrayList <Node> treeNodes){
        Node wrongNode;
        for(int i = 0; i < treeNodes.size()-1;i++) {
            if(treeNodes.get(i).value > treeNodes.get(i+1).value) {
                wrongNode = treeNodes.get(i+1);
                return wrongNode;
            }
        }
        return null;
    }

    static Node sortedArrayToTree(ArrayList<Node> treeNodes, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node node = new Node(treeNodes.get(mid).value);
        node.left = sortedArrayToTree(treeNodes, start, mid - 1);
        node.right = sortedArrayToTree(treeNodes, mid + 1, end);
        return node;
    }


    public static void main(String[] args)
    {
        BinaryTree tree = new BinaryTree();

        /* create a simple tree */
        tree.root = new Node(10);
        tree.root.left = new Node(3);
        tree.root.left.left = new Node(2);
        tree.root.left.right = new Node(4);

        tree.root.right = new Node(5);
        tree.root.right.left = new Node(11);
        tree.root.right.right = new Node(13);

        /* print our simple tree */
        printInPreorder(tree.root);

        /* add nodes of the tree to the list which should be sorted if the tree is correct */
        ArrayList<Node> treeNodes = new ArrayList<Node>();
        tree.binarySearchTreeToList(tree.root, treeNodes);
        
        System.out.println("\ntree as list");
        printNodesInList(treeNodes);

        /* find misplaced element */
        Node wrongNode = findMisplacedElement(treeNodes);
        System.out.println("\nmisplaced element is " + wrongNode.value);

        /* save the wrong value */
        int wrongValue = wrongNode.value;

        /* deleting the misplaced element */
        tree.deleteNode(tree.root, wrongValue);
        System.out.println("\nafter wrong node deletion");
        printInPreorder(tree.root);

        /* insert the misplaced node back */
        tree.insert(tree.root, wrongValue);
        System.out.println("\nafter wrong node " + wrongValue + " insertion");
        printInPreorder(tree.root);

        /* sort our list and create new tree from it */
/*

        Collections.sort(treeNodes,
                Comparator.comparing(o -> o.value));
        System.out.println("\ntree as sorted list");
        printNodesInList(treeNodes);

        Node binaryNode = sortedArrayToTree(treeNodes, 0, treeNodes.size() - 1);
        BinaryTree finalTree = new BinaryTree(binaryNode);
        System.out.println("\nresulting tree ");
        printInPreorder(finalTree.root);

        */


    }

}