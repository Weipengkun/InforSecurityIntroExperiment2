import SimplifiedAES.SimplifiedAESDecrypt;
import SimplifiedAES.SimplifiedAESEncrypt;

import java.util.*;

public class MeetInTheMiddleAttack {

    private static SimplifiedAESEncrypt aesEncrypt = new SimplifiedAESEncrypt();
    private static SimplifiedAESDecrypt aesDecrypt = new SimplifiedAESDecrypt();

    public static void main(String[] args) {
        String plaintext = "0000000000000000";
        String ciphertext = "0000001111011100";
        findKeys(plaintext, ciphertext);
    }

    public static void findKeys(String plaintext, String ciphertext) {
        // 创建两个HashMap来存储中间结果
        Map<String, String> encryptionMap = new HashMap<>();
        Map<String, String> decryptionMap = new HashMap<>();
        List<String> foundKeys = new ArrayList<>();  // 创建一个列表来存储找到的密钥对

        String[] hexChars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

        // 遍历所有可能的Key1
        for (String char1 : hexChars) {
            for (String char2 : hexChars) {
                for (String char3 : hexChars) {
                    for (String char4 : hexChars) {
                        String key1 = char1 + char2 + char3 + char4;
                        String midText1 = aesEncrypt.encryptText(plaintext, key1);
                        encryptionMap.put(midText1, key1);
                    }
                }
            }
        }

        // 遍历所有可能的Key2
        for (String char1 : hexChars) {
            for (String char2 : hexChars) {
                for (String char3 : hexChars) {
                    for (String char4 : hexChars) {
                        String key2 = char1 + char2 + char3 + char4;
                        String midText2 = aesDecrypt.decryptText(ciphertext, key2);
                        decryptionMap.put(midText2, key2);
                    }
                }
            }
        

        // 查找匹配的中间文本
        for (Map.Entry<String, String> entry1 : encryptionMap.entrySet()) {
            String midText1 = entry1.getKey();
            String key1 = entry1.getValue();
            if (decryptionMap.containsKey(midText1)) {
                String key2 = decryptionMap.get(midText1);
                foundKeys.add("Key1 = " + key1 + ", Key2 = " + key2);  // 将找到的密钥对添加到列表中
            }
        }

        // 打印所有找到的密钥对
        if (foundKeys.isEmpty()) {
            System.out.println("未找到匹配的密钥对");
        } else {
            System.out.println("找到的密钥对如下：");
            for (String keys : foundKeys) {
                System.out.println(keys);
            }
        }
    }
  }
}
