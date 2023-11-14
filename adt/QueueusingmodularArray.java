package adt;

public class QueueusingmodularArray {

    private int[] array;
    private int head;
    private int tail;
    private int size;

    public QueueusingmodularArray() {
        array = new int[10];
        head = 0;
        tail = 0;
        size = 0;
    }

    public QueueusingmodularArray(int data) {
        array = new int[10];
        array[0] = data;
        head = 0;
        tail = 1;
        size = 1;
    }

    public void enqueue(int data) {
        if (size == array.length) {
            int[] newArray = new int[array.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[(head + i) % array.length];
            }
            array = newArray;
            head = 0;
            tail = size;
        }
        array[tail] = data;
        tail = (tail + 1) % array.length;
        size++;
    }

    public int dequeue() {
        if (size == 0) {
            throw new RuntimeException("Queue is empty");
        }
        int data = array[head];
        head = (head + 1) % array.length;
        size--;
        return data;
    }

    public int peek() {
        if (size == 0) {
            throw new RuntimeException("Queue is empty");
        }
        return array[head];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        array = new int[10];
        head = 0;
        tail = 0;
        size = 0;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[(head + i) % array.length] + " ");
        }
    }
}