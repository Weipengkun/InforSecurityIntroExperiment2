package UI;

import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public interface MyCommandListener extends ActionListener{
	public void setJTextField1(JTextField text);
	public void setJTextField2(JTextField text);
	public void setJTextField3(JTextField text);
	public void setJTextField4(JTextField text);
	public void setJTextArea(JTextArea area);
}
