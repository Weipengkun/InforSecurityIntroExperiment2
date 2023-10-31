package MultipleEncryptionAndDecryption;

import SimplifiedAES.SimplifiedAESDecrypt;

public class MultipleDecryption {
    private static SimplifiedAESDecrypt aesDecrypt = new SimplifiedAESDecrypt();

    public static void main(String[] args) {
        // 定义两个密钥和密文
        String key1 = "2D55";
        String key2 = "A1F3";
        String ciphertext = "0000001111011100";

        // 调用doubleDecrypt方法测试双重解密
        String doubleEncryptedText = doubleDecrypt(ciphertext, key1, key2);
        System.out.println("双重解密结果如下：");
        System.out.println(doubleEncryptedText);  // 输出双重加密的密文


        String key3 = "B276";
        String tripleCiphertext="1010101001111001";
        // 调用tripleDecrypt方法测试三重解密
        String tripleDecryptedText = tripleDecrypt(tripleCiphertext, key1, key2, key3);
        System.out.println("三重解密结果如下：");
        System.out.println(tripleDecryptedText);  // 输出三重解密的明文
    }

    /**
     * 双重解密方法
     * @param ciphertext 要解密的密文
     * @param key1 第一个密钥
     * @param key2 第二个密钥
     * @return 双重解密的明文
     */
    public static String doubleDecrypt(String ciphertext, String key1, String key2) {
        // 使用第二个密钥进行第一轮解密
        String firstDecryptionResult = aesDecrypt.decryptText(ciphertext, key2);
        // 使用第一个密钥对第一轮解密的结果进行第二轮解密
        String doubleDecryptionResult = aesDecrypt.decryptText(firstDecryptionResult, key1);
        return doubleDecryptionResult;
    }

    /**
     * 三重解密方法
     * @param ciphertext 要解密的密文
     * @param key1 第一个密钥
     * @param key2 第二个密钥
     * @param key3 第三个密钥
     * @return 三重解密的明文
     */
    public static String tripleDecrypt(String ciphertext, String key1, String key2, String key3) {
        // 使用第三个密钥进行第一轮解密
        String firstDecryptionResult = aesDecrypt.decryptText(ciphertext, key3);
        // 使用第二个密钥对第一轮解密的结果进行第二轮解密
        String secondDecryptionResult = aesDecrypt.decryptText(firstDecryptionResult, key2);
        // 使用第一个密钥对第二轮解密的结果进行第三轮解密
        String tripleDecryptionResult = aesDecrypt.decryptText(secondDecryptionResult, key1);
        return tripleDecryptionResult;
    }
}
