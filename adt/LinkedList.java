package adt;

public class LinkedList {
    
    private class Node {
        protected Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
        public int data;
        public Node next;
    }
    
    public LinkedList() {
        head = null;
    }

    public LinkedList(int data) {
        head = new Node(data, null);
    }

    private Node head;

    public void insert(int data) {
        if (head == null) {
            head = new Node(data, null);
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(data, null);
        }
    }

    public void remove(int data) {
        if (head == null) {
            return;
        } else if (head.data == data) {
            head = head.next;
        } else {
            Node current = head;
            while (current.next != null && current.next.data != data) {
                current = current.next;
            }
            if (current.next != null) {
                current.next = current.next.next;
            }
        }
    }

    public boolean contains(int data) {
        Node current = head;
        while (current != null && current.data != data) {
            current = current.next;
        }
        return current != null;
    }
    
    public void print() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    public Node getHead() {
        return head;
    }

    public Node findkthNode(int k) {
        Node current = head;
        while (current != null && k > 0) {
            current = current.next;
            k--;
        }
        return current;
    }
}
