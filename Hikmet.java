public class Hikmet {
    public static void main(String[] args) {
        
    }

    public void percolateDown(int[] arr, int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if (left < arr.length && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < arr.length && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(arr, i, largest);
            percolateDown(arr, largest);
        }
    }

    public void percolateUp(int[] arr, int i) {
        int parent = (i - 1) / 2;
        if (parent >= 0 && arr[parent] < arr[i]) {
            swap(arr, i, parent);
            percolateUp(arr, parent);
        }
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
