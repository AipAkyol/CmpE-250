package adt;

public class SkewHeap {

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

    public SkewHeap() {
        root = null;
    }

    public SkewHeap(int data) {
        root = new Node(data, null, null);
    }

    private Node root;

    public void insert(int data) {
        root = merge(root, new Node(data, null, null));
    }

    public int remove() {
        if (root == null) {
            throw new RuntimeException("Heap is empty");
        }
        int data = root.data;
        root = merge(root.left, root.right);
        return data;
    }

    public int peek() {
        if (root == null) {
            throw new RuntimeException("Heap is empty");
        }
        return root.data;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
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

    public static void main(String[] args) {
        SkewHeap heap = new SkewHeap();
        heap.insert(5);
        heap.insert(3);
        heap.insert(7);
        heap.insert(1);
        heap.insert(4);
        heap.insert(6);
        heap.insert(8);
        heap.print();
        while (!heap.isEmpty()) {
            System.out.print(heap.remove() + " ");
        }
        System.out.println();
        heap.print();
    }


    
}
