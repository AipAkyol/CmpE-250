public class temp {
    public static void main(String[] args) {
        System.out.println(hashCode("call"));
    }

    public static int hashCode(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++)
            hash = (31 * hash) + str.charAt(i);
        return hash;
    }
}
