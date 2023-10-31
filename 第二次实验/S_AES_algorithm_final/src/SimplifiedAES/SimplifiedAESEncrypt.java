package SimplifiedAES;

import SimplifiedAES.GF2_4;
import SimplifiedAES.KeyExpansion;

import java.io.*;

public class SimplifiedAESEncrypt {
    // S-box数组
    private static final String[][] S_BOX = {
            {"9", "4", "A", "B"},
            {"D", "1", "8", "5"},
            {"6", "2", "0", "3"},
            {"C", "E", "F", "7"}
    };


    // 混淆矩阵
    private static final int[][] MIXING_MATRIX = {
            {1, 4},
            {4, 1}
    };

    // GF2_4类实例，用于执行特定的算术运算
    private static final GF2_4 gf = new GF2_4();

    public static void main(String[] args) throws IOException {

        String initialKey = "2D55";  // 初始16位密钥

        String plaintext = "0000000000000000";
        String encryptedText = encryptText(plaintext, initialKey);
        System.out.println("加密结果如下：");
        System.out.println(encryptedText); // 输出秘文结果

        String plaintextASCII = "wpk in software enginer  and this is a test txt file"; // 输入String类型的 ASCII
        String encryptedTextASCII = encryptASCII(plaintextASCII, initialKey);
        System.out.println("加密结果如下：");
        System.out.println(encryptedTextASCII); // 输出加密结果

//        // 读取输入文件input.txt
//        String plaintextASCIITxt = new String(Files.readAllBytes(Paths.get("src/input.txt")));
//        System.out.println("plaintextASCIITxt:"+plaintextASCIITxt);
//        // 加密ASCII文本
//        String encryptedTextTxt = encryptASCII(plaintextASCIITxt, initialKey);
//        // 将二进制的加密文本写入到输出文件
//        Files.write(Paths.get("src/output.txt"), encryptedTextTxt.getBytes());

    }

    /**
     * 使用简化的 AES 算法加密 ASCII 字符串。
     * @param plaintextASCII 要加密的 ASCII 文本
     * @paraminitialKey 加密的初始密钥
     * @return ASCII格式的加密文本
     */
    public static String encryptASCII(String plaintextASCII, String initialKey) {
        StringBuilder encryptedTextBuilder = new StringBuilder();
        for (int i = 0; i < plaintextASCII.length(); i += 2) {
            // 获取下一个16位的块（2个字符）
            String block = plaintextASCII.substring(i, Math.min(i + 2, plaintextASCII.length()));
            // 将字符转换为二进制
            String binaryBlock = asciiToBinary(block);
            // 加密二进制块
            String encryptedBinaryBlock = encryptText(binaryBlock, initialKey);
            // 将加密的二进制块转换回ASCII
            String encryptedBlock = binaryToAscii(encryptedBinaryBlock);
            encryptedTextBuilder.append(encryptedBlock);
        }
        return encryptedTextBuilder.toString();
    }


    /**
     * 使用简化的 AES 算法加密16bit的二进制文本。
     * @param plaintext 待加密的二进制文本
     * @paraminitialKey 加密的初始密钥
     * @return 二进制格式的加密文本
     */
    public static String encryptText(String plaintext, String initialKey) {
        String[] expandedKeys = KeyExpansion.expandKey(initialKey);  // 秘钥拓展
        String Key1 = expandedKeys[2] + expandedKeys[3];
        String Key2 = expandedKeys[4] + expandedKeys[5];
        // 第一步: 轮密钥加
        String[][] initialMatrix = roundKeyAddition(plaintext, initialKey);
        // 第二步: 第一轮
        String[][] firstRoundResult = firstRound(initialMatrix, Key1);
        // 第三步：第二轮
        String[][] ciphertext = SecondRound(firstRoundResult, Key2);
        return printMatrix(ciphertext);  // 返回字符串格式的密文
    }

    // 轮密钥加函数
    public static String[][] roundKeyAddition(String plaintext, String key) {
        String binaryKey = hexToBinary(key,16);  // 将16进制密钥转换为二进制
        String[][] stateMatrix = convertToMatrix(plaintext);
        String[][] keyMatrix = convertToMatrix(binaryKey);
        return xorMatrices(stateMatrix, keyMatrix);
    }

    // 第一轮函数
    public static String[][] firstRound(String[][] inputMatrix, String key) {
        // 一：半字节代替函数
        String[][] subNibblesMatrix = subNibbles(inputMatrix);
        // 二：行位移函数
        String[][] shiftRowsMatrix = shiftRows(subNibblesMatrix);
        // 三：列混淆函数
        String[][] mixColumnsMatrix = mixColumns(shiftRowsMatrix);
        // 四：轮密钥加函数
        String[][] keyMatrix = convertToMatrix(key);
        String[][] xorMatricesMatrix=xorMatrices(mixColumnsMatrix, keyMatrix);
        return xorMatricesMatrix;
    }

    // 第二轮函数
    public static String[][] SecondRound(String[][] inputMatrix, String key){
        // 一：半字节代替函数
        String[][] subNibblesMatrix = subNibbles(inputMatrix);
        // 二：行位移函数
        String[][] shiftRowsMatrix = shiftRows(subNibblesMatrix);
        // 三：轮密钥加函数
        String[][] keyMatrix = convertToMatrix(key);
        String[][] xorMatricesMatrix=xorMatrices(shiftRowsMatrix, keyMatrix);
        return xorMatricesMatrix;
    }

    // 一：半字节代替函数
    public static String[][] subNibbles(String[][] matrix) {
        String[][] result = new String[2][2];
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                String nibble = matrix[row][col];
                int rowIndex = Integer.parseInt(nibble.substring(0, 2), 2);
                int colIndex = Integer.parseInt(nibble.substring(2, 4), 2);
                // 直接从S_BOX中获取值
                result[row][col] =hexToBinary(S_BOX[rowIndex][colIndex],4); ;
            }
        }
        return result;
    }

    // 二：行位移函数
    public static String[][] shiftRows(String[][] matrix) {
        String temp = matrix[1][0];
        matrix[1][0] = matrix[1][1];
        matrix[1][1] = temp;
        return matrix;
    }

    // 三：列混淆函数
    public static String[][] mixColumns(String[][] matrix) {
        String[][] result = new String[2][2];
        for (int i = 0; i < 2; i++) {  // 对于每列
            // 确保每个半字节都是4位长
            String upperNibble = String.format("%4s", matrix[0][i]).replace(' ', '0');
            String lowerNibble = String.format("%4s", matrix[1][i]).replace(' ', '0');
            // 将二进制字符串转换为整数
            int upperValue = Integer.parseInt(upperNibble, 2);
            int lowerValue = Integer.parseInt(lowerNibble, 2);
            // 执行列混淆运算
            int resultUpperValue = gf.multiply(MIXING_MATRIX[0][0], upperValue)
                    ^ gf.multiply(MIXING_MATRIX[0][1], lowerValue);
            int resultLowerValue = gf.multiply(MIXING_MATRIX[1][0], upperValue)
                    ^ gf.multiply(MIXING_MATRIX[1][1], lowerValue);
            // 将结果转换回二进制字符串并存储在结果矩阵中
            result[0][i] = String.format("%4s", Integer.toBinaryString(resultUpperValue)).replace(' ', '0');
            result[1][i] = String.format("%4s", Integer.toBinaryString(resultLowerValue)).replace(' ', '0');
        }
        return result;
    }

    // 将16进制转换为二进制
    public static String hexToBinary(String hex, int bitLength) {
        return String.format("%" + bitLength + "s", Integer.toBinaryString(Integer.parseInt(hex, 16)))
                .replace(' ', '0');
    }

    // 将输入字符串转换为2x2矩阵
    public static String[][] convertToMatrix(String input) {
        return new String[][]{
                {input.substring(0, 4), input.substring(8, 12)},
                {input.substring(4, 8), input.substring(12, 16)}
        };
    }

    // 执行按位异或操作的函数
    public static String[][] xorMatrices(String[][] matrix1, String[][] matrix2) {
        String[][] result = new String[2][2];
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                result[row][col] = xor(matrix1[row][col], matrix2[row][col]);
            }
        }
        return result;
    }

    // 异或操作函数
    public static String xor(String a, String b) {
        StringBuilder xorResult = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            xorResult.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return xorResult.toString();
    }

    // 打印2x2矩阵的函数
    public static String printMatrix(String[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int col = 0; col < 2; col++) {  // 先遍历列
            for (int row = 0; row < 2; row++) {  // 再遍历行
                result.append(matrix[row][col]);
            }
        }
        return result.toString();
    }

    /**
     * 将 ASCII 字符串转换为其二进制表示形式。
     * @param ascii ASCII 字符串
     * @return ASCII字符串的二进制表示
     */
    public static String asciiToBinary(String ascii) {
        StringBuilder binary = new StringBuilder();
        for (char ch : ascii.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(ch)).replace(' ', '0'));
        }
        return binary.toString();
    }

    /**
     * 将二进制字符串转换为其 ASCII 表示形式。
     * @param binary 二进制字符串
     * @return 二进制字符串的 ASCII 表示
     */
    public static String binaryToAscii(String binary) {
        StringBuilder ascii = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            ascii.append((char)charCode);
        }
        return ascii.toString();
    }
}
