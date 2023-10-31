package UI;

public class S_AES {

	public static void main(String[] args) {
		try { for (javax.swing.UIManager.LookAndFeelInfo info :
			 javax.swing.UIManager.getInstalledLookAndFeels()) { if
			 ("Nimbus".equals(info.getName())) {
			 javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; } }
			 }catch(Exception e) { System.out.println(e); }
			 
			
			
		InterfaceDesign win=new InterfaceDesign();
	    win.displayWindow();
	    BitEnListen policeEn=new BitEnListen();
	    BitDeListen policeDe=new BitDeListen();
	    multEnListen policeMultEn=new multEnListen();
	    multDeListen policeMultDe=new multDeListen();
	    CBCEnListen policeCBCEn=new CBCEnListen();
	    CBCDeListen policeCBCDe=new CBCDeListen();

	    
		win.setMyCommandListenerEn(policeEn);
		win.setMyCommandListenerDe(policeDe);
		win.setMyCommandListenerMultEn(policeMultEn);
		win.setMyCommandListenerMultDe(policeMultDe);
		win.setMyCommandListenerCBCEn(policeCBCEn);
		win.setMyCommandListenerCBCDe(policeCBCDe);
		

	}

}
