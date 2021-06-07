package servertable.landscape.nvs.com;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import landscapeexplorer.nvs.com.globalData;

public class ServerListTableListener extends MouseAdapter implements KeyListener {

	public globalData gData;
	public ServerListTable parent = null;
	
	public ServerListTableListener(globalData gData, ServerListTable parent) {
		this.gData = gData;
		this.parent = parent;
	}

@Override
public void mouseClicked(MouseEvent evt) {

	if (evt.getClickCount() == 1) {
		
	
	}else if (evt.getClickCount() == 2) {
		
	
	
	}
	
	
//	System.out.println("Mouse event");	
	
}

@Override
public void keyPressed(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}
}