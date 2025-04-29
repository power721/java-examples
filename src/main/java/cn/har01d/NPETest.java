package cn.har01d;

public class NPETest {
    public static void main(String[] args) {
        System.out.println(test().length());
    }

    private static String test() {
        String str = null;
        System.out.println(str.length());
        return str;
    }
}
