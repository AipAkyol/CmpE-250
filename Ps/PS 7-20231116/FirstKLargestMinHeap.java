import java.util.Iterator;
import java.util.PriorityQueue;

public class FirstKLargestMinHeap {

    public static void FirstKelements(int arr[],
                                      int size,
                                      int k)
    {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int i = 0; i < k; i++)
        {
            minHeap.add(arr[i]);
        }
        for(int i = k; i < size; i++)
        {

            if (minHeap.peek() > arr[i])
                continue;
            else
            {
                minHeap.poll();
                minHeap.add(arr[i]);
            }
        }

        Iterator iterator = minHeap.iterator();

        while (iterator.hasNext())
        {
            System.out.print(iterator.next() + " ");
        }
    }

    public static void main (String[] args)
    {
        int arr[] = { 98,87,22,18,44,2,3,18,34,91,8199};

        int size = arr.length;

        int k = 3;

        FirstKelements(arr, size, k);
    }
}
