package adt;

public class Queue {

    private class Node {
        protected Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
        public int data;
        public Node next;
    }

    public Queue() {
        head = null;
        tail = null;
    }

    public Queue(int data) {
        head = new Node(data, null);
        tail = head;
    }

    private Node head;
    private Node tail;

    public void enqueue(int data) {
        if (head == null) {
            head = new Node(data, null);
            tail = head;
        } else {
            tail.next = new Node(data, null);
            tail = tail.next;
        }
    }

    public int dequeue() {
        if (head == null) {
            throw new RuntimeException("Queue is empty");
        }
        int data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return data;
    }

    public int peek() {
        if (head == null) {
            throw new RuntimeException("Queue is empty");
        }
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
        tail = null;
    }
    
    public void print() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }
}
