package adt;

public class StackUsingArray {

    public StackUsingArray() {
        data = new int[10];
        size = 0;
    }

    public StackUsingArray(int data) {
        this.data = new int[10];
        this.data[0] = data;
        size = 1;
    }

    private int[] data;
    private int size;

    public void push(int data) {
        if (size == this.data.length) {
            int[] temp = new int[this.data.length * 2];
            for (int i = 0; i < this.data.length; i++) {
                temp[i] = this.data[i];
            }
            this.data = temp;
        }
        this.data[size] = data;
        size++;
    }

    public int pop() {
        if (size == 0) {
            throw new RuntimeException("Stack is empty");
        }
        size--;
        return data[size];
    }

    public int peek() {
        if (size == 0) {
            throw new RuntimeException("Stack is empty");
        }
        return data[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void print() {
        for (int i = size - 1; i >= 0; i--) {
            System.out.print(data[i] + " ");
        }
    }

    public void clear() {
        size = 0;
    }
    
}
