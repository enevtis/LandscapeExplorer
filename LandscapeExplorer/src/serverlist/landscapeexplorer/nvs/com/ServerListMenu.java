package serverlist.landscapeexplorer.nvs.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import obj.landscapeexplorer.nvs.com.GraphPointer;

public class ServerListMenu extends JPopupMenu implements ActionListener{
    
	ServerListPanel parent;
    
 
 	JMenuItem itemPlaySequence;

    JMenuItem itemAddNewSong;
    
 	JMenuItem itemAddNewSongPart;
    JMenuItem itemAddNewTakt; 
    JMenuItem itemAddMultipleNewTakts;
    
    
    JMenuItem itemDelete;
    JMenuItem itemRename;
    JMenuItem itemMoveUp;
    JMenuItem itemMoveDown;
 
    JMenuItem itemAddToSequence;
    
    JMenuItem itemRepeatDrums;
    JMenuItem itemClearSequence;    
    

    public ServerListMenu(ServerListPanel parent) {
        
    	this.parent = parent;

    	itemPlaySequence = new JMenuItem(parent.gData.translate("play from the beginning"));
    	itemPlaySequence.setActionCommand("PLAY_SEQUENCE");
    	itemPlaySequence.addActionListener(this);


    	itemAddNewSong = new JMenuItem(parent.gData.translate("add new song"));
    	itemAddNewSong.setActionCommand("ADD_NEW_SONG");
    	itemAddNewSong.addActionListener(this);
    	
    	
    	itemAddNewSongPart = new JMenuItem(parent.gData.translate("insert new song part"));
    	itemAddNewSongPart.setActionCommand("ADD_SONG_PART");
    	itemAddNewSongPart.addActionListener(this);

    	
    	itemDelete = new JMenuItem(parent.gData.translate("delete"));
    	itemDelete.setActionCommand("DELETE");
    	itemDelete.addActionListener(this);

    	itemRename = new JMenuItem(parent.gData.translate("rename"));
    	itemRename.setActionCommand("RENAME_SONG_PART");
    	itemRename.addActionListener(this);    	
 
    	itemMoveUp = new JMenuItem(parent.gData.translate("move up"));
    	itemMoveUp.setActionCommand("MOVE_UP_SONG_PART");
    	itemMoveUp.addActionListener(this);
    	
    	itemMoveDown = new JMenuItem(parent.gData.translate("move down"));
    	itemMoveDown.setActionCommand("MOVE_DOWN_SONG_PART");
    	itemMoveDown.addActionListener(this);    	
    	
    	itemAddNewTakt = new JMenuItem(parent.gData.translate("add beat"));
    	itemAddNewTakt.setActionCommand("ADD_TAKT");
    	itemAddNewTakt.addActionListener(this);     	

    	itemAddMultipleNewTakts = new JMenuItem(parent.gData.translate("add multiple beats"));
    	itemAddMultipleNewTakts.setActionCommand("ADD_MULTIPLE_TAKTS");
    	itemAddMultipleNewTakts.addActionListener(this);   
    	
 
    	itemAddToSequence = new JMenuItem(parent.gData.translate("add to sequence"));
    	itemAddToSequence.setActionCommand("ADD_TO_SEQUENCE");
    	itemAddToSequence.addActionListener(this);     
    	
    	itemRepeatDrums = new JMenuItem(parent.gData.translate("repeat drums"));
    	itemRepeatDrums.setActionCommand("REPEAT_DRUMS");
    	itemRepeatDrums.addActionListener(this);
    	
    	itemClearSequence = new JMenuItem(parent.gData.translate("clear sequence"));
    	itemClearSequence.setActionCommand("CLEAR_SEQUENCE");
    	itemClearSequence.addActionListener(this);
    	
    	
    	add(itemPlaySequence);
    	add(itemAddNewSongPart);
    	add(itemDelete);    	
    	add(itemRename);    	
    	add(itemMoveUp);
    	add(itemMoveDown);    	
    	add(itemAddNewTakt);
    	add(itemAddMultipleNewTakts);
    	add(itemAddNewSong);
    	add(itemAddToSequence);
    	add(itemRepeatDrums);
    	add(itemClearSequence);
    	

    }
    @Override
    public void firePopupMenuWillBecomeVisible() {
    	
		
    	  for (int i = 0 ; i <  this.getComponentCount(); i++) {
    	      JMenuItem item = (JMenuItem) this.getComponent(i);
    	      item.setVisible(false);
    	      
    	   }
    	
    	
    	GraphPointer gp = parent.selectedPixel;
		
		if (gp != null) {
			
			
			switch (gp.type) {

			case "songPart":			

				itemDelete.setVisible(true);
				itemRename.setVisible(true);
				itemMoveUp.setVisible(true);
				itemMoveDown.setVisible(true);
				itemAddNewTakt.setVisible(true);
				itemAddMultipleNewTakts.setVisible(true);
				itemAddToSequence.setVisible(true);
				break;
			
			
			case "takt":
				itemDelete.setVisible(true);
				itemAddNewTakt.setVisible(true);
				itemAddMultipleNewTakts.setVisible(true);
				itemRepeatDrums.setVisible(true);
				break;
			
			case "sequence":				
				itemClearSequence.setVisible(true);
				itemPlaySequence.setVisible(true);
				break;
			
			}
			
			
		} else {
			
			itemDelete.setVisible(false);
    		itemAddNewSongPart.setVisible(true);
    		itemAddNewSong.setVisible(true);
			
		}
    	
    }
    
    @Override
	public void actionPerformed(ActionEvent arg0) {

		GraphPointer gp = parent.selectedPixel;
		

//		if (gp != null) 	System.out.println(" selected=" + arg0.getActionCommand()); 	
			
		
		switch (arg0.getActionCommand()) {

			case "DEL":
				System.out.println("DELETE!");
			break;
			case "PLAY_SEQUENCE":
			break;
			case "ADD_SONG_PART":
				
//				parent.ctrl.addNewPart();

			break;			

			case "RENAME_SONG_PART":
				
//				parent.ctrl.renamePart();

			break;

			case "DELETE":
				
//				parent.ctrl.deletePartOrTakt();

			break;

			case "MOVE_UP_SONG_PART":
				
//				parent.ctrl.movePartUp();;

			break;			

			case "MOVE_DOWN_SONG_PART":
				
//				parent.ctrl.movePartDown();

			break;				

			case "ADD_NEW_SONG":
				
//				parent.ctrl.addNewSong();

			break;
			
			case "ADD_TAKT":				
//				parent.ctrl.AddNewTakt();
			break;

			case "ADD_MULTIPLE_TAKTS":				
//				parent.ctrl.AddNewMultipeTakts();
			break;
			
			case "ADD_TO_SEQUENCE":				
//				parent.ctrl.AddToSequence();
			break;			

			
			case "REPEAT_DRUMS":				
//				parent.ctrl.RepeatDrums();
			break;

			case "CLEAR_SEQUENCE":				
//				parent.ctrl.clearSequence();

			break;
			
			default:
				break;			
		}
		

		

		
	}

}
