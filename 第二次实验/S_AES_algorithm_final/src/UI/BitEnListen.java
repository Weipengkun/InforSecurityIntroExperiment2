package UI;
import javax.swing.*;
import java.awt.event.*;
import SimplifiedAES.*;

public class BitEnListen implements MyCommandListener{
	JTextField key,bitInput,stringInput;
	JTextArea output;
	
	public void setJTextField1(JTextField text) {
		key=text;
	}
	public void setJTextField2(JTextField text) {
		bitInput=text;
	}
	public void setJTextField3(JTextField text) {
		stringInput=text;
	}
	public void setJTextField4(JTextField text) {
		
	}

	
	public void setJTextArea(JTextArea area) {
		output=area;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String str1=key.getText();
		String str2=bitInput.getText();
		String str3=stringInput.getText();

        // 验证输入内容
        boolean isBitKeyValid = validateBitKey(str1);
        boolean isBitInputValid = validateBitInput(str2);

        // 显示消息对话框
        if (!isBitKeyValid) {
        	JOptionPane.showMessageDialog(null,"key输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
        }
        
        
        if(str2.trim().equals("输入16位二进制明文/密文")) {
        	String ciphertext1 = SimplifiedAESEncrypt.encryptASCII(str3, str1);
        	output.setText("");
    		output.append("ASCII加密结果为: " + ciphertext1 + "\n");
        }
        else {
        	if(str3.trim().equals("输入明文/密文")) {
        		String ciphertext = SimplifiedAESEncrypt.encryptText(str2, str1);
            	output.setText("");
            	output.append("16bit加密结果为: " + ciphertext + "\n");
            }
        	else {
        		if (!isBitInputValid) {
                	JOptionPane.showMessageDialog(null,"16bit明文/密文输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
                }
            	String ciphertext = SimplifiedAESEncrypt.encryptText(str2, str1);
        		String ciphertext1 = SimplifiedAESEncrypt.encryptASCII(str3, str1);
        		output.setText("");
        		output.append("16bit加密结果为: " + ciphertext + "\n");
        		output.append("ASCII加密结果为: " + ciphertext1 + "\n");
        	}
        }
	}
	private boolean validateBitKey(String bitKey) {
        // 验证bitKey是否为4位16进制数
        return bitKey.matches("[0-9a-fA-F]{4}");
    }

    private boolean validateBitInput(String bitInput) {
        // 验证bitInput是否为16位二进制数
        return bitInput.matches("[01]{16}");
    }
}
