package adt;

public class Stack {
    
    private class Node {
        protected Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
        public int data;
        public Node next;
    }
    
    public Stack() {
        top = null;
    }

    public Stack(int data) {
        top = new Node(data, null);
    }

    private Node top;

    public void push(int data) {
        top = new Node(data, top);
    }

    public int pop() {
        if (top == null) {
            throw new RuntimeException("Stack is empty");
        }
        int data = top.data;
        top = top.next;
        return data;
    }

    public int peek() {
        if (top == null) {
            throw new RuntimeException("Stack is empty");
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void print() {
        Node current = top;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    public void clear() {
        top = null;
    }

}
