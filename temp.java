import java.util.Arrays;

public class temp {
    public static void main(String[] args) {
        //System.out.println(hashCode("call"));
        //int[] arr = {-1, 2, 3, -4, 5};
        //System.out.println(max_subarray(arr));
        //System.out.println(hash("Cannur Aktas"));
        /*int[][] twoDArray = {
            {1, 2, 3},
            {4, 5},
            {6, 7, 8, 9}
        };

        // Initialize an empty 1D array
        int[] oneDArray = concatenateArrays(twoDArray);

        // Print the resulting 1D array
        for (int value : oneDArray) {
            System.out.print(value + " ");
        }*/

        String binaryString = Integer.toBinaryString(12);
        binaryString = String.format("%5s", binaryString).replace(' ', '0');
        System.out.println(binaryString);
    }

    private static int[] concatenateArrays(int[][] array2D) {
        // Find the maximum row length
        int maxRowLength = 0;
        for (int[] row : array2D) {
            maxRowLength = Math.max(maxRowLength, row.length);
        }

        // Initialize the 1D array with the calculated length
        int[] resultArray = new int[array2D.length * maxRowLength];

        // Index to keep track of the current position in the result array
        int currentIndex = 0;

        // Iterate through each column
        for (int col = 0; col < maxRowLength; col++) {
            // Iterate through each row
            for (int[] row : array2D) {
                // Check if the current row has elements in the current column
                if (col < row.length) {
                    // Copy the element to the result array
                    resultArray[currentIndex++] = row[col];
                }
            }
        }

        // Trim the result array to remove trailing zeros
        return Arrays.copyOf(resultArray, currentIndex);
    }


    public static int hashCode(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++)
            hash = (31 * hash) + str.charAt(i);
        return hash;
    }

    public static int max_subarray(int[] arr) {
        int max_sum = 0;
        int current_sum = 0;
        for (int el : arr) {
            current_sum = Math.max(current_sum + el, el);
            max_sum = Math.max(max_sum, current_sum);
        }
        return max_sum;
    }

    private static int hash(String input) {
        int hash = 0;
        for (int i = 0; i < input.length(); i++) {
            hash = (hash * 31 + input.charAt(i));
        }
        int index = hash % 223;
        if (index < 0) {
            index += 223;
        }
        return index;
    }
}
