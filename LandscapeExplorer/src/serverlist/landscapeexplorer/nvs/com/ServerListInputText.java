package serverlist.landscapeexplorer.nvs.com;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import obj.landscapeexplorer.nvs.com.GraphPointer;


public class ServerListInputText extends JTextField implements KeyListener,FocusListener{

	private ServerListPanel parent = null;
	public String typeOfText = "";
	

	public ServerListInputText(ServerListPanel parent) {	
		this.addKeyListener(this);
		this.parent = parent;
		
	}

	
	@Override
	public void keyPressed(KeyEvent arg0) {

		int code = arg0.getKeyCode();
		
		if (code == KeyEvent.VK_ENTER) {
			
			setVisible(false);
			
			if (parent.selectedPixel!=null) System.out.println("parent.selectedPixel.type=" + parent.selectedPixel.type);
			else System.out.println("parent.selectedPixel is null");
			
			
			switch (parent.selectedPixel.type) {
			
			case "param":
					
//					parent.gData.currentComposition.commonParams.put(this.typeOfText, this.getText());
				
				break;
			
			
			case "songPart":
				
				GraphPointer gp = parent.selectedPixel;
//				SongPart sp = (SongPart)gp.obj;
//				sp.partDescr = this.getText();
				
				break;
				

			default:
				
				
				break;
			
			
			}
						
		}

		parent.gData.repainAllFrames();
	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void focusLost(FocusEvent e) {

		System.out.println("AAA");
		
	}


}
