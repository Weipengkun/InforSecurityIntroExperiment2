package MultipleEncryptionAndDecryption;

import SimplifiedAES.SimplifiedAESEncrypt;

public class MultipleEncryption {

    private static SimplifiedAESEncrypt aesEncrypt = new SimplifiedAESEncrypt();

    public static void main(String[] args) {
        // 定义两个密钥和明文
        String key1 = "2D55";
        String key2 = "A1F3";
        String plaintext = "0000000000000000";

        // 调用doubleEncrypt方法测试双重加密
        String doubleEncryptedText = doubleEncrypt(plaintext, key1, key2);
        System.out.println("双重加密结果如下：");
        System.out.println(doubleEncryptedText);  // 输出双重加密的密文

        String key3 = "B276";
        // 调用tripleEncrypt方法测试三重加密
        String tripleEncryptedText = tripleEncrypt(plaintext, key1, key2, key3);
        System.out.println("三重加密结果如下：");
        System.out.println(tripleEncryptedText);  // 输出三重加密的密文
    }
    /**
     * 双重加密方法
     * @param plaintext 要加密的明文
     * @param key1 第一个密钥
     * @param key2 第二个密钥
     * @return 双重加密的密文
     */
    public static String doubleEncrypt(String plaintext, String key1, String key2) {
        // 使用第一个密钥进行第一轮加密
        String firstEncryptionResult = aesEncrypt.encryptText(plaintext, key1);
        // 使用第二个密钥对第一轮加密的结果进行第二轮加密
        String doubleEncryptionResult = aesEncrypt.encryptText(firstEncryptionResult, key2);
        return doubleEncryptionResult;
    }

    /**
     * 三重加密方法
     * @param plaintext 要加密的明文
     * @param key1 第一个密钥
     * @param key2 第二个密钥
     * @param key3 第三个密钥
     * @return 三重加密的密文
     */
    public static String tripleEncrypt(String plaintext, String key1, String key2, String key3) {
        // 使用第一个密钥进行第一轮加密
        String firstEncryptionResult = aesEncrypt.encryptText(plaintext, key1);
        // 使用第二个密钥对第一轮加密的结果进行第二轮加密
        String secondEncryptionResult = aesEncrypt.encryptText(firstEncryptionResult, key2);
        // 使用第三个密钥对第二轮加密的结果进行第三轮加密
        String tripleEncryptionResult = aesEncrypt.encryptText(secondEncryptionResult, key3);
        return tripleEncryptionResult;
    }

}
