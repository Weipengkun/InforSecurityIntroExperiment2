package SimplifiedAES;

public class GF2_4 {
    private static final int[] fieldElements = {
            0b0000, 0b0001, 0b0010, 0b0011,
            0b0100, 0b0101, 0b0110, 0b0111,
            0b1000, 0b1001, 0b1010, 0b1011,
            0b1100, 0b1101, 0b1110, 0b1111
    };

    private static final int MOD_POLY = 0b10011;  // x^4 + x + 1

    // 加法运算
    public static int add(int a, int b) {
        return a ^ b;
    }

    // 乘法运算
    public static int multiply(int a, int b) {
        if (a == 0 || b == 0) return 0;  // 如果其中一个乘数为0，则结果为0

        int[] logTable = {0, 0, 1, 4, 2, 8, 5, 10, 3, 14, 9, 7, 6, 13, 11, 12};
        int[] expTable = {1, 2, 4, 8, 3, 6, 12, 11, 5, 10, 7, 14, 15, 13, 9};

        int logA = logTable[a];
        int logB = logTable[b];
        int logResult = (logA + logB) % 15;

        return expTable[logResult];
    }
}
