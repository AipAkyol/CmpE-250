package adt;

public class BinomialQueue {

    private class Node {
        protected Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
        public int data;
        public Node left;
        public Node right;
    }

    public BinomialQueue() {
        root = null;
    }   

    public BinomialQueue(int data) {
        root = new Node(data, null, null);
    }
    
    private Node root;

    public void merge(BinomialQueue other) {
        root = merge(root, other.root);
        other.root = null;
    }

    private Node merge(Node a, Node b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        } else if (a.data < b.data) {
            a.right = merge(a.right, b);
            return a;
        } else {
            b.right = merge(b.right, a);
            return b;
        }
    }

    public void insert(int data) {
        BinomialQueue temp = new BinomialQueue(data);
        merge(temp);
    }

    public int findMin() {
        if (root == null) {
            throw new RuntimeException("Queue is empty");
        }
        return root.data;
    }

    public int deleteMin() {
        if (root == null) {
            throw new RuntimeException("Queue is empty");
        }
        int data = root.data;
        root = merge(root.left, root.right);
        return data;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void print() {
        print(root);
    }

    private void print(Node current) {
        if (current != null) {
            print(current.left);
            System.out.print(current.data + " ");
            print(current.right);
        }
    }

    public void clear() {
        root = null;
    }

}