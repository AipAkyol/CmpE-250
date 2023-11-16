package adt;

public class BinaryHeap <T extends Comparable<? super T>> {

    public BinaryHeap() {
        heap = (T[]) new Comparable[10];
        size = 0;
    }

    private T[] heap;
    private int size;

    public void insert(T data) {
        if (size == heap.length) {
            T[] newHeap = (T[]) new Comparable[heap.length * 2];
            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
        heap[size] = data;
        size++;
        int current = size - 1;
        while (current > 0 && heap[current].compareTo(heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public T remove() {
        if (size == 0) {
            throw new RuntimeException("Heap is empty");
        }
        T data = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return data;
    }

    public T peek() {
        if (size == 0) {
            throw new RuntimeException("Heap is empty");
        }
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        heap = (T[]) new Comparable[10];
        size = 0;
    }

    private void heapify(int current) {
        int left = left(current);
        int right = right(current);
        int smallest = current;
        if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }
        if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }
        if (smallest != current) {
            swap(current, smallest);
            heapify(smallest);
        }
    }

    private int parent(int current) {
        return (current - 1) / 2;
    }

    private int left(int current) {
        return 2 * current + 1;
    }

    private int right(int current) {
        return 2 * current + 2;
    }

    private void swap(int a, int b) {
        T temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    
}
