package SimplifiedAES;

public class KeyExpansion {
    // S-box array
    private static final String[][] S_BOX = {
            {"9", "4", "A", "B"},
            {"D", "1", "8", "5"},
            {"6", "2", "0", "3"},
            {"C", "E", "F", "7"}
    };

    // RC constants
    private static final String[] RC = {"10000000", "00110000"};

    public static void main(String[] args) {
        String initialKey = "2D55";  // Initial 16-bit key
        String[] expandedKeys = expandKey(initialKey);

        // Print expanded keys in binary
        for (int i = 0; i < expandedKeys.length; i++) {
            System.out.println("w" + i + ": " + expandedKeys[i]);
        }
    }

    // Method to expand the initial key
    public static String[] expandKey(String initialKey) {
        String w0 = initialKey.substring(0, 2);
        String w1 = initialKey.substring(2, 4);

        // Convert hex to binary
        w0 = hexToBinary(w0);
        w1 = hexToBinary(w1);
        // Key expansion logic as per the description
        String w2 = xor(xor(w0, RC[0]), g(w1));
        String w3 = xor(w2, w1);
        String w4 = xor(xor(w2, RC[1]),g(w3));
        String w5 = xor(w4, w3);

        // Returning the expanded keys as an array of binary strings
        return new String[]{w0, w1, w2, w3, w4, w5};

    }

    // Method to perform the g function transformation
    public static String g(String w) {
        String N0 = w.substring(0, 4);
        String N1 = w.substring(4, 8);

        // Swap N0 and N1
        String temp = N0;
        N0 = N1;
        N1 = temp;
        // Lookup in S-box
        N1 = sBoxLookup(N1);
        N0 = sBoxLookup(N0);

        // Combining the transformed N1 and N0 by concatenating
        return N0 + N1;
    }

    // Method to perform the S-box lookup
    // Method to perform the S-box lookup
    public static String sBoxLookup(String nibble) {
        // Parsing the row and column indices from the nibble
        int row = Integer.parseInt(nibble.substring(0, 2), 2);
        int col = Integer.parseInt(nibble.substring(2, 4), 2);

        // Converting the S-box value from hex to binary and ensuring it's a 4-bit binary string
        String sBoxValue = S_BOX[row][col];
        return String.format("%4s", Integer.toBinaryString(Integer.parseInt(sBoxValue, 16)))
                .replace(' ', '0');
    }

    // Method to convert hex to binary
    public static String hexToBinary(String hex) {
        return String.format("%8s", Integer.toBinaryString(Integer.parseInt(hex, 16)))
                .replace(' ', '0');
    }

    // Method to perform XOR operation
    public static String xor(String a, String b) {
        StringBuilder xorResult = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            xorResult.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }

        return xorResult.toString();
    }
}
