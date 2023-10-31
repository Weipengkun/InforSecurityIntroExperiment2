package CBCMode;
import SimplifiedAES.SimplifiedAESEncrypt;

public class CBCModeEncrypt {

    private static SimplifiedAESEncrypt aesEncrypt = new SimplifiedAESEncrypt();


    public static void main(String[] args) {
        String plaintext = "0000000011111111111111110000000011111111111111110000000000000000";  // 64-bit 长明文00FF FF00 FFFF 0000
        String initialVector = "1010101010101010";  // 协商好的16位初始向量
        String key = "2D55";  // 16-bit key

        String encryptedText = CBCModeEncrypt(plaintext, initialVector, key);
        System.out.println("加密结果如下：");
        System.out.println(encryptedText);  // 输出加密结果
    }

    //密码分组链模式加密
    public static String CBCModeEncrypt(String plaintext, String initialVector, String key) {
        StringBuilder encryptedTextBuilder = new StringBuilder();
        String previousCiphertextBlock = initialVector;  // 初始向量作为第一块的"前一块密文"

        for (int i = 0; i < plaintext.length(); i += 16) {
            // 获取下一个16位的块
            String block = plaintext.substring(i, i + 16);
            // 将当前块与前一块的密文（或初始向量）进行按位异或操作
            String xorResult = xor(block, previousCiphertextBlock);
            // 使用密钥加密异或结果
            String encryptedBlock = aesEncrypt.encryptText(xorResult, key);
            // 将加密块添加到加密文本构建器中
            encryptedTextBuilder.append(encryptedBlock);
            // 更新"前一块密文"以供下一块使用
            previousCiphertextBlock = encryptedBlock;
        }

        return encryptedTextBuilder.toString();
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
