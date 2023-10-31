package UI;
import javax.swing.*;
import java.awt.event.*;
import MultipleEncryptionAndDecryption.*;

public class multEnListen implements MyCommandListener{
	JTextField key1,key2,key3,input;
	JTextArea output;
	
	public void setJTextField1(JTextField text) {
		key1=text;
	}
	public void setJTextField2(JTextField text) {
		key2=text;
	}
	public void setJTextField3(JTextField text) {
		key3=text;
	}
	public void setJTextField4(JTextField text) {
		input=text;
	}

	
	public void setJTextArea(JTextArea area) {
		output=area;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String str1=key1.getText();
		String str2=key2.getText();
		String str3=key3.getText();
		String str4=input.getText();

        // 验证输入内容
        boolean isBitKeyValid = validateBitKey(str1);
        boolean isBitKeyValid2 = validateBitKey(str2);
        boolean isBitKeyValid3 = validateBitKey(str3);
        boolean isBitInputValid = validateBitInput(str4);

        // 显示消息对话框
        if (!isBitKeyValid) {
        	JOptionPane.showMessageDialog(null,"key1输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
        }
        if (!isBitKeyValid2) {
        	JOptionPane.showMessageDialog(null,"key2输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
        }

        if (!isBitInputValid) {
        	JOptionPane.showMessageDialog(null,"16bit明文/密文输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
        }

        if(str3.trim().equals("输入4位16进制密钥作为key3")) {
        	String douencryptedText = MultipleEncryption.doubleEncrypt(str4, str1,str2);
        	output.setText("");
    		output.append("16bit双重加密结果为: " + douencryptedText + "\n");
        }
        else {
        	if (!isBitKeyValid3) {
            	JOptionPane.showMessageDialog(null,"key3输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
            }
        	
        	String triencryptedText = MultipleEncryption.tripleEncrypt(str4, str1,str2,str3);
        	output.setText("");
        	output.append("16bit三重加密结果为: " + triencryptedText + "\n");
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
