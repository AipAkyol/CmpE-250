package adt;

public class BinarySearchTree {

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

    public BinarySearchTree() {
        root = null;
    }

    public BinarySearchTree(int data) {
        root = new Node(data, null, null);
    }

    private Node root;

    public void insert(int data) {
        if (root == null) {
            root = new Node(data, null, null);
        } else {
            Node current = root;
            while (true) {
                if (data < current.data) {
                    if (current.left == null) {
                        current.left = new Node(data, null, null);
                        break;
                    } else {
                        current = current.left;
                    }
                } else if (data > current.data) {
                    if (current.right == null) {
                        current.right = new Node(data, null, null);
                        break;
                    } else {
                        current = current.right;
                    }
                } else {
                    break;
                }
            }
        }
    }

    public Node find(int data) {
        Node current = root;
        while (current != null && current.data != data) {
            if (data < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    public Node findMin() {
        Node current = root;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }

    public Node findMax() {
        Node current = root;
        while (current != null && current.right != null) {
            current = current.right;
        }
        return current;
    }


    //remove function using findmax method
    public void remove(int data) {
        Node current = root;
        Node parent = null;
        while (current != null && current.data != data) {
            parent = current;
            if (data < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current == null) {
            return;
        } else if (current.left == null && current.right == null) {
            if (parent == null) {
                root = null;
            } else if (parent.left == current) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else if (parent.left == current) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.right == null) {
            if (parent == null) {
                root = current.left;
            } else if (parent.left == current) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else {
            Node max = current.left;
            Node maxParent = current;
            while (max.right != null) {
                maxParent = max;
                max = max.right;
            }
            current.data = max.data;
            if (maxParent.left == max) {
                maxParent.left = max.left;
            } else {
                maxParent.right = max.left;
            }
        }
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
