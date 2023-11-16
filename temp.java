public class temp {
    public static void main(String[] args) {
        //System.out.println(hashCode("call"));
        int[] arr = {-1, 2, 3, -4, 5};
        System.out.println(max_subarray(arr));
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
}
