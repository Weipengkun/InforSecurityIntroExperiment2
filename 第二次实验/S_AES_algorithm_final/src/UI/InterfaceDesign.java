package UI;

import java.awt.*;
import javax.swing.*;

public class InterfaceDesign extends JFrame {
	JFrame f;
	JLabel title,title2,title3,pic1,pic2,pic3;
	JPanel bitEncrypt,multiEncrypt,cbcMode;

	JTabbedPane jtbp;
	JTextField bitKey,bitInput,stringInput,multKey1,multKey2,multKey3,multInput,cbcKey,cbcVector,cbcInput;
	JTextArea bitOutput,multOuput,cbcOutput;
	JButton bitEnBut,bitDeBut,seEnBut,seDeBut,cbcEnBut,cbcDeBut;
	MyCommandListener BitEnlistener,BitDelistener,MultEnlistener,MultDelistener,CBCEnlistener,CBCDelistener;
	
	
	public InterfaceDesign()
	{
		f=new JFrame("S-AES加密系统");
		bitEncrypt=new JPanel();//面板1(16bits加密面板)
		Color bgColor = new Color(245, 245, 245);
		bitEncrypt.setBackground(bgColor);
        
		multiEncrypt=new JPanel();//面板2(多重加密面板)
		multiEncrypt.setBackground(bgColor);
		
		cbcMode=new JPanel();//面板3(CBC模式加密面板)
		cbcMode.setBackground(bgColor);
		
		title=new JLabel("S-AES加密程序");
		title.setFont(new  java.awt.Font("宋体",  1,  30));
		title2=new JLabel("S-AES多重加密程序");
		title2.setFont(new  java.awt.Font("宋体",  1,  30));
		title3=new JLabel("CBC模式加密程序");
		title3.setFont(new  java.awt.Font("宋体",  1,  30));
		
		pic1=new JLabel();
		pic1.setIcon(new ImageIcon("src/UI/flowChart.png"));
		pic2=new JLabel();
		pic2.setIcon(new ImageIcon("src/UI/flowChart.png"));
		pic3=new JLabel();
		pic3.setIcon(new ImageIcon("src/UI/CBC.png"));
		
		bitKey = new JTextField();
		bitKey.setPreferredSize(new Dimension (200,30));
		bitKey.addFocusListener(new JTextFieldHintListener(bitKey, "输入4位16进制密钥"));
		bitInput = new JTextField();
		bitInput.addFocusListener(new JTextFieldHintListener(bitInput, "输入16位二进制明文/密文"));
		stringInput= new JTextField();
		stringInput.addFocusListener(new JTextFieldHintListener(stringInput, "输入明文/密文"));
		bitEnBut=new JButton("加密");
		bitEnBut .setFont(new  java.awt.Font("黑体",  0,  16));
		bitEnBut.setBackground(new Color(181, 181 ,181));
		bitDeBut=new JButton("解密");
		bitDeBut .setFont(new  java.awt.Font("黑体",  0,  16));
		bitDeBut.setBackground(new Color(181, 181 ,181));
		bitOutput=new JTextArea(11,35);
		bitOutput.setEditable(false);
		bitOutput.setLineWrap(true);        //激活自动换行功能 
		bitOutput.setWrapStyleWord(true); 
		
		multKey1 = new JTextField();
		multKey1.setPreferredSize(new Dimension (200,30));
		multKey1.addFocusListener(new JTextFieldHintListener(multKey1, "输入4位16进制密钥作为key1"));
		multKey2 = new JTextField();
		multKey2.setPreferredSize(new Dimension (200,30));
		multKey2.addFocusListener(new JTextFieldHintListener(multKey2, "输入4位16进制密钥作为key2"));
		multKey3 = new JTextField();
		multKey3.setPreferredSize(new Dimension (200,30));
		multKey3.addFocusListener(new JTextFieldHintListener(multKey3, "输入4位16进制密钥作为key3"));
		multInput = new JTextField();
		multInput.addFocusListener(new JTextFieldHintListener(multInput, "输入16位二进制明文/密文"));
		seEnBut=new JButton("加密");
		seEnBut .setFont(new  java.awt.Font("黑体",  0,  16));
		seEnBut.setBackground(new Color(181, 181 ,181));
		seDeBut=new JButton("解密");
		seDeBut .setFont(new  java.awt.Font("黑体",  0,  16));
		seDeBut.setBackground(new Color(181, 181 ,181));
		multOuput=new JTextArea(10,35);
		multOuput.setEditable(false);
		multOuput.setLineWrap(true);   
		multOuput.setWrapStyleWord(true); 
		
		cbcKey = new JTextField();
		cbcKey.setPreferredSize(new Dimension (200,30));
		cbcKey.addFocusListener(new JTextFieldHintListener(cbcKey, "输入4位16进制密钥"));
		
		cbcVector = new JTextField();
		cbcVector.addFocusListener(new JTextFieldHintListener(cbcVector, "输入16位初始向量"));
		
		cbcInput= new JTextField();
		cbcInput.addFocusListener(new JTextFieldHintListener(cbcInput, "输入64位明文/密文"));
		
		cbcEnBut=new JButton("加密");
		cbcEnBut .setFont(new  java.awt.Font("黑体",  0,  16));
		cbcEnBut.setBackground(new Color(181, 181 ,181));
		
		cbcDeBut=new JButton("解密");
		cbcDeBut .setFont(new  java.awt.Font("黑体",  0,  16));
		cbcDeBut.setBackground(new Color(181, 181 ,181));
		
		cbcOutput=new JTextArea(11,35);
		cbcOutput.setEditable(false);
		cbcOutput.setLineWrap(true);    
		cbcOutput.setWrapStyleWord(true);       
		
		
		jtbp=new JTabbedPane();//采用默认的选项卡面板
		
	}
	
	public void displayWindow()
	{

		//bitEncrypt.setLayout(new BorderLayout());
		//基本加密页
		//两按钮放入hBox
        Box hBoxbit = Box.createHorizontalBox();
        hBoxbit.add(bitEnBut);
        hBoxbit.add(Box.createHorizontalStrut(200));
        hBoxbit.add(bitDeBut);
       
        //vBox
        Box vBoxbit = Box.createVerticalBox();
        vBoxbit.add(Box.createVerticalStrut(10));
        vBoxbit.add(bitKey);
        vBoxbit.add(Box.createVerticalStrut(15));
        vBoxbit.add(bitInput);
        vBoxbit.add(Box.createVerticalStrut(15));
        vBoxbit.add(stringInput);
        vBoxbit.add(Box.createVerticalStrut(15));
        vBoxbit.add(hBoxbit);
        vBoxbit.add(Box.createVerticalStrut(15));
        vBoxbit.add(bitOutput);
        
        Box hBoxbit1 = Box.createHorizontalBox();
        hBoxbit1.add(vBoxbit);
        hBoxbit1.add(Box.createHorizontalStrut(30));
        hBoxbit1.add(pic1);

        bitEncrypt.add(title);
        bitEncrypt.add(hBoxbit1);
        
      //多重加密页
  		//两按钮放入hBox
          Box hBoxmult1 = Box.createHorizontalBox();
          hBoxmult1.add(seEnBut);
          hBoxmult1.add(Box.createHorizontalStrut(150));
          hBoxmult1.add(seDeBut);

         
          //vBox
          Box vBoxmult = Box.createVerticalBox();
          vBoxmult.add(Box.createVerticalStrut(10));
          vBoxmult.add(multKey1);
          vBoxmult.add(Box.createVerticalStrut(15));
          vBoxmult.add(multKey2);
          vBoxmult.add(Box.createVerticalStrut(15));
          vBoxmult.add(multKey3);
          vBoxmult.add(Box.createVerticalStrut(15));
          vBoxmult.add(multInput);
          vBoxmult.add(Box.createVerticalStrut(15));
          vBoxmult.add(hBoxmult1);
          vBoxmult.add(Box.createVerticalStrut(15));
          vBoxmult.add(multOuput);
          
          Box hBoxmult3 = Box.createHorizontalBox();
          hBoxmult3.add(vBoxmult);
          hBoxmult3.add(Box.createHorizontalStrut(30));
          hBoxmult3.add(pic2);

          
          multiEncrypt.add(title2);
          multiEncrypt.add(hBoxmult3);
        
          
        //CBC加密页
    		//两按钮放入hBox
            Box hBoxcbc = Box.createHorizontalBox();
            hBoxcbc.add(cbcEnBut);
            hBoxcbc.add(Box.createHorizontalStrut(150));
            hBoxcbc.add(cbcDeBut);

           
            //vBox
            Box vBoxcbc = Box.createVerticalBox();
            vBoxcbc.add(Box.createVerticalStrut(10));
            vBoxcbc.add(cbcKey);
            vBoxcbc.add(Box.createVerticalStrut(15));
            vBoxcbc.add(cbcVector);
            vBoxcbc.add(Box.createVerticalStrut(15));
            vBoxcbc.add(cbcInput);
            vBoxcbc.add(Box.createVerticalStrut(15));
            vBoxcbc.add(hBoxcbc);
            vBoxcbc.add(Box.createVerticalStrut(15));
            vBoxcbc.add(cbcOutput);
            
            Box hBoxcbc2 = Box.createHorizontalBox();
            hBoxcbc2.add(vBoxcbc);
            hBoxcbc2.add(Box.createHorizontalStrut(30));
            hBoxcbc2.add(pic3);

            
            cbcMode.add(title3);
            cbcMode.add(hBoxcbc2);
            
            
            

		jtbp.addTab("AES基本加密", bitEncrypt);//添加选项卡进选项卡面板
		jtbp.setFont(new  java.awt.Font("黑体",  1,  15));
		jtbp.addTab("多重加密", multiEncrypt);//添加选项卡进选项卡面板
		jtbp.setFont(new  java.awt.Font("黑体",  1,  15));
		jtbp.addTab("CBC模式加密", cbcMode);//添加选项卡进选项卡面板
		jtbp.setFont(new  java.awt.Font("黑体",  1,  15));
		

		
		
		f.setContentPane(jtbp);
		f.setSize(1000, 600);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public void setMyCommandListenerEn(MyCommandListener listener) {
		BitEnlistener=listener;
		listener.setJTextField1(bitKey);
		listener.setJTextField2(bitInput);
		listener.setJTextField3(stringInput);
		listener.setJTextArea(bitOutput);
		bitEnBut.addActionListener(listener);
		
	}
	public void setMyCommandListenerDe(MyCommandListener listener) {
		BitDelistener=listener;
		listener.setJTextField1(bitKey);
		listener.setJTextField2(bitInput);
		listener.setJTextField3(stringInput);
		listener.setJTextArea(bitOutput);
		bitDeBut.addActionListener(listener);
		
	}
	
	public void setMyCommandListenerMultEn(MyCommandListener listener) {
		MultEnlistener=listener;
		listener.setJTextField1(multKey1);
		listener.setJTextField2(multKey2);
		listener.setJTextField3(multKey3);
		listener.setJTextField4(multInput);
		listener.setJTextArea(multOuput);
		seEnBut.addActionListener(listener);
		
	}
	public void setMyCommandListenerMultDe(MyCommandListener listener) {
		MultDelistener=listener;
		listener.setJTextField1(multKey1);
		listener.setJTextField2(multKey2);
		listener.setJTextField3(multKey3);
		listener.setJTextField4(multInput);
		listener.setJTextArea(multOuput);
		seDeBut.addActionListener(listener);
		
	}
	public void setMyCommandListenerCBCEn(MyCommandListener listener) {
		CBCEnlistener=listener;
		listener.setJTextField1(cbcKey);
		listener.setJTextField2(cbcVector);
		listener.setJTextField3(cbcInput);
		listener.setJTextArea(cbcOutput);
		cbcEnBut.addActionListener(listener);
		
	}
	public void setMyCommandListenerCBCDe(MyCommandListener listener) {
		CBCDelistener=listener;
		listener.setJTextField1(cbcKey);
		listener.setJTextField2(cbcVector);
		listener.setJTextField3(cbcInput);
		listener.setJTextArea(cbcOutput);
		cbcDeBut.addActionListener(listener);
		
	}
}
