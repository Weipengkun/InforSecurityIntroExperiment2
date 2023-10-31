package SimplifiedAES;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimplifiedAESDecrypt {
    // 逆S-box数组
    private static final String[][] InverseS_BOX = {
            {"A", "5", "9", "B"},
            {"1", "7", "8", "F"},
            {"6", "0", "2", "3"},
            {"C", "4", "D", "E"}
    };

    // 逆混淆矩阵
    private static final int[][] InverseMIXING_MATRIX = {
            {9, 2},
            {2, 9}
    };
    // GF2_4类实例，用于执行特定的算术运算
    private static final GF2_4 gf = new GF2_4();

    public static void main(String[] args) throws IOException {
        String initialKey = "2D55";  // 初始16位密钥

        String ciphertext = "1011100011100001";
        String encryptedText = decryptText(ciphertext, initialKey);
        System.out.println("解密结果如下：");
        System.out.println(encryptedText); // 输出秘文结果

        String encryptedTextASCII = "¯\u008C¾(\u0098[ÎN·yag\u001D/"; // ASCII编码的加密文本
        String decryptedTextASCII = decryptASCII(encryptedTextASCII, initialKey);
        System.out.println("解密结果如下：");
        System.out.println(decryptedTextASCII); // 输出解密结果

//        // 读取src/output.txt文件中的内容
//        String fileContent = new String(Files.readAllBytes(Paths.get("src/output.txt")));
//        // 解密ASCII文本
//        String encryptedTextTxt = decryptASCII(fileContent, initialKey);
//        // 将二进制的加密文本写入到输出文件
//        Files.write(Paths.get("src/decryptTxt.txt"), encryptedTextTxt.getBytes());
    }
    /**
     * 使用简化的 AES 算法解密ASCII字符串。
     * @param encryptedTextASCII 要解密的ASCII编码的加密文本
     * @param initialKey 解密的初始密钥
     * @return ASCII格式的解密文本
     */
    public static String decryptASCII(String encryptedTextASCII, String initialKey) {
        StringBuilder decryptedTextBuilder = new StringBuilder();
        for (int i = 0; i < encryptedTextASCII.length(); i += 2) {
            // 获取下一个16位的块（2个字符）
            String block = encryptedTextASCII.substring(i, Math.min(i + 2, encryptedTextASCII.length()));
            // 将字符转换为二进制
            String binaryBlock = asciiToBinary(block);
            // 解密二进制块
            String decryptedBinaryBlock = decryptText(binaryBlock, initialKey);
            // 将解密的二进制块转换回ASCII
            String decryptedBlock = binaryToAscii(decryptedBinaryBlock);
            decryptedTextBuilder.append(decryptedBlock);
        }
        return decryptedTextBuilder.toString();
    }

    /**
     * 使用简化的 AES 算法解密16bit的二进制文本。
     * @param ciphertext 待解密二进制文本
     * @paraminitialKey 解密的初始密钥
     * @return 二进制格式的解密文本
     */
    public static String decryptText(String ciphertext, String initialKey) {
        String[] expandedKeys = KeyExpansion.expandKey(initialKey);  // 秘钥拓展
        String roundOneKey=expandedKeys[4]+expandedKeys[5]; // 第一轮解密密钥
        String roundTwoKey =expandedKeys[2]+expandedKeys[3]; // 第二轮解密密钥
        // 第一步: 轮密钥加
        String[][] preRoundOneMatrix  = roundKeyAddition(ciphertext, roundOneKey);
        // 第二步: 第一轮解密
        String[][] afterRoundOneMatrix  = decryptFirstRound(preRoundOneMatrix , roundTwoKey);
        // 第三步：第二轮解密
        String[][] decryptedMatrix  = decryptSecondRound(afterRoundOneMatrix, hexToBinary(initialKey,16));
        return printMatrix(decryptedMatrix);  // 返回字符串格式的密文
    }

    // 轮密钥加函数
    public static String[][] roundKeyAddition(String ciphertext, String key) {
        String[][] stateMatrix = convertToMatrix(ciphertext);
        String[][] keyMatrix = convertToMatrix(key);
        return xorMatrices(stateMatrix, keyMatrix);
    }

    // 第一轮解密轮函数
    public static String[][] decryptFirstRound(String[][] inputMatrix, String key) {
        // 1. 逆行位移操作
        String[][] invShiftRowsMatrix = inverseShiftRows(inputMatrix);
        // 2. 逆半字节代替操作
        String[][] invSubNibblesMatrix = inverseSubNibbles(invShiftRowsMatrix);
        // 3. 轮密钥加操作
        String[][] keyMatrix = convertToMatrix(key);
        String[][] xorMatricesMatrix = xorMatrices(invSubNibblesMatrix, keyMatrix);
        // 4. 逆列混淆操作
        String[][] invMixColumnsMatrix = inverseMixColumns(xorMatricesMatrix);
        return invMixColumnsMatrix;
    }

    // 第二轮解密轮函数
    public static String[][] decryptSecondRound(String[][] inputMatrix, String key) {
        // 1. 逆行位移操作
        String[][] invShiftRowsMatrix = inverseShiftRows(inputMatrix);
        // 2. 逆半字节代替操作
        String[][] invSubNibblesMatrix = inverseSubNibbles(invShiftRowsMatrix);
        // 3. 轮密钥加操作
        String[][] keyMatrix = convertToMatrix(key);
        String[][] xorMatricesMatrix = xorMatrices(invSubNibblesMatrix, keyMatrix);
        return xorMatricesMatrix;
    }

    // 一：逆行位移函数，向右移动半个字节
    public static String[][] inverseShiftRows(String[][] matrix) {
        // 交换第二行的两个元素
        String temp = matrix[1][0];
        matrix[1][0] = matrix[1][1];
        matrix[1][1] = temp;
        return matrix;
    }

    // 二：逆半字节代替函数
    public static String[][] inverseSubNibbles(String[][] matrix) {
        String[][] result = new String[2][2];
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                String nibble = matrix[row][col];
                int rowIndex = Integer.parseInt(nibble.substring(0, 2), 2);
                int colIndex = Integer.parseInt(nibble.substring(2, 4), 2);
                // 直接从S_BOX中获取值
                result[row][col] =hexToBinary(InverseS_BOX[rowIndex][colIndex],4); ;
            }
        }
        return result;
    }

    // 四：逆列混淆函数
    public static String[][] inverseMixColumns(String[][] matrix) {
        String[][] result = new String[2][2];
        for (int i = 0; i < 2; i++) {  // 对于每列
            // 确保每个半字节都是4位长
            String upperNibble = String.format("%4s", matrix[0][i]).replace(' ', '0');
            String lowerNibble = String.format("%4s", matrix[1][i]).replace(' ', '0');
            // 将二进制字符串转换为整数
            int upperValue = Integer.parseInt(upperNibble, 2);
            int lowerValue = Integer.parseInt(lowerNibble, 2);
            // 执行列混淆运算
            int resultUpperValue = gf.multiply(InverseMIXING_MATRIX[0][0], upperValue)
                    ^ gf.multiply(InverseMIXING_MATRIX[0][1], lowerValue);
            int resultLowerValue = gf.multiply(InverseMIXING_MATRIX[1][0], upperValue)
                    ^ gf.multiply(InverseMIXING_MATRIX[1][1], lowerValue);
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
