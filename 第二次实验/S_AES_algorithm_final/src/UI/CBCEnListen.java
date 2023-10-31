package UI;
import javax.swing.*;
import CBCMode.*;
import java.awt.event.*;

public class CBCEnListen implements MyCommandListener{
	JTextField key,vector,input;
	JTextArea output;
	
	public void setJTextField1(JTextField text) {
		key=text;
	}
	public void setJTextField2(JTextField text) {
		vector=text;
	}
	public void setJTextField3(JTextField text) {
		input=text;
	}
	public void setJTextField4(JTextField text) {
		
	}

	
	public void setJTextArea(JTextArea area) {
		output=area;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String str1=key.getText();
		String str2=vector.getText();
		String str3=input.getText();

        // 验证输入内容
        boolean isBitKeyValid = validateBitKey(str1);
        boolean isVectorValid = validateBitVictor(str2);
        boolean isBitInputValid = validateBitInput(str3);

        // 显示消息对话框
        if (!isBitKeyValid) {
        	JOptionPane.showMessageDialog(null,"key输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
        }
        if (!isVectorValid) {
        	JOptionPane.showMessageDialog(null,"16bit向量输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
        }
        if (!isBitInputValid) {
        	JOptionPane.showMessageDialog(null,"64bit明文/密文输入错误！","消息提示",JOptionPane.WARNING_MESSAGE);	//消息对话框
        }
        

    	String ciphertext = CBCModeEncrypt.CBCModeEncrypt(str3, str2,str1);
		output.setText("");
		output.append("加密结果为: " + ciphertext + "\n");
	}

        		
	private boolean validateBitKey(String bitKey) {
        // 验证bitKey是否为4位16进制数
        return bitKey.matches("[0-9a-fA-F]{4}");
    }
	private boolean validateBitVictor(String vector) {
        // 验证bitInput是否为16位二进制数
        return vector.matches("[01]{16}");
    }
    private boolean validateBitInput(String bitInput) {
        // 验证bitInput是否为16位二进制数
        return bitInput.matches("[01]{64}");
    }
}
