package CBCMode;

import SimplifiedAES.SimplifiedAESDecrypt;

public class CBCModeDecrypt {
    private static SimplifiedAESDecrypt aesDecrypt = new SimplifiedAESDecrypt();
    public static void main(String[] args) {
        String encryptedText = "1101110011010101001111010000111100111100000001111110000110001011";  // 64-bit 长明文00FF FF00 FFFF 0000
        String initialVector = "1010101010101010";  // 协商好的16位初始向量
        String key = "2D55";  // 16-bit key

        String decryptedText = CBCModeDecrypt(encryptedText, initialVector, key);
        System.out.println("解密结果如下：");
        System.out.println(decryptedText);  // 输出解密结果
    }
    //密码分组链模式解密
    public static String CBCModeDecrypt(String ciphertext, String initialVector, String key) {
        StringBuilder decryptedTextBuilder = new StringBuilder();
        String previousCiphertextBlock = initialVector;  // 初始向量作为第一块的"前一块密文"

        for (int i = 0; i < ciphertext.length(); i += 16) {
            // 获取下一个16位的块
            String block = ciphertext.substring(i, i + 16);
            // 使用密钥解密当前块
            String decryptedBlock = aesDecrypt.decryptText(block, key);
            // 将解密的块与前一块的密文（或初始向量）进行按位异或操作
            String xorResult = xor(decryptedBlock, previousCiphertextBlock);
            // 将解密的块添加到解密文本构建器中
            decryptedTextBuilder.append(xorResult);
            // 更新"前一块密文"以供下一块使用
            previousCiphertextBlock = block;
        }

        return decryptedTextBuilder.toString();
    }

    // 异或操作函数
    public static String xor(String a, String b) {
        StringBuilder xorResult = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            xorResult.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return xorResult.toString();
    }
}
