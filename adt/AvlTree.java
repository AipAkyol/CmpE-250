package adt;

public class AvlTree {

    private class Node {
        protected Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
        public int data;
        public Node left;
        public Node right;
        public int height;
    }

    public AvlTree() {
        root = null;
    }

    public AvlTree(int data) {
        root = new Node(data, null, null);
    }

    private Node root;

    public void insert(int data) {
        root = insert(root, data);
    }

    private Node insert(Node current, int data) {
        if (current == null) {
            return new Node(data, null, null);
        } else if (data < current.data) {
            current.left = insert(current.left, data);
        } else if (data > current.data) {
            current.right = insert(current.right, data);
        } else {
            return current;
        }
        return balance(current);
    }

    public void remove(int data) {
        root = remove(root, data);
    }

    private Node remove(Node current, int data) {
        if (current == null) {
            return null;
        } else if (data < current.data) {
            current.left = remove(current.left, data);
        } else if (data > current.data) {
            current.right = remove(current.right, data);
        } else if (current.left == null) {
            return current.right;
        } else if (current.right == null) {
            return current.left;
        } else {
            current.data = findMin(current.right).data;
            current.right = remove(current.right, current.data);
        }
        return balance(current);
    }

    private Node findMin(Node current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public boolean contains(int data) {
        Node current = root;
        while (current != null && current.data != data) {
            if (data < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current != null;
    }
    
    public void print() {
        print(root);
        System.out.println();
    }

    private void print(Node current) {
        if (current != null) {
            print(current.left);
            System.out.print(current.data + " ");
            print(current.right);
        }
    }  

    private Node balance(Node current) {
        if (current == null) {
            return null;
        } else if (height(current.left) - height(current.right) > 1) {
            if (height(current.left.left) >= height(current.left.right)) {
                current = rotateWithLeftChild(current);
            } else {
                current = doubleWithLeftChild(current);
            }
        } else if (height(current.right) - height(current.left) > 1) {
            if (height(current.right.right) >= height(current.right.left)) {
                current = rotateWithRightChild(current);
            } else {
                current = doubleWithRightChild(current);
            }
        }
        current.height = Math.max(height(current.left), height(current.right)) + 1;
        return current;
    }

    private Node rotateWithLeftChild(Node current) {
        Node left = current.left;
        current.left = left.right;
        left.right = current;
        current.height = Math.max(height(current.left), height(current.right)) + 1;
        left.height = Math.max(height(left.left), current.height) + 1;
        return left;
    }

    private Node rotateWithRightChild(Node current) {
        Node right = current.right;
        current.right = right.left;
        right.left = current;
        current.height = Math.max(height(current.left), height(current.right)) + 1;
        right.height = Math.max(height(right.right), current.height) + 1;
        return right;
    }

    private Node doubleWithLeftChild(Node current) {
        current.left = rotateWithRightChild(current.left);
        return rotateWithLeftChild(current);
    }

    private Node doubleWithRightChild(Node current) {
        current.right = rotateWithLeftChild(current.right);
        return rotateWithRightChild(current);
    }

    private int height(Node current) {
        return current == null ? -1 : current.height;
    }
    
}
