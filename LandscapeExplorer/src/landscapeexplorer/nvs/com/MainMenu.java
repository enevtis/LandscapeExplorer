package landscapeexplorer.nvs.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import serverlist.landscapeexplorer.nvs.com.ServerListPanel;


public class MainMenu implements ActionListener{

	globalData gData;

	public MainMenu(globalData gData)  {
		this.gData = gData;
	}

	
	
	public JMenuBar getMainMenu() {
		
	      ImageIcon icon1 = new ImageIcon(getClass().getResource(gData.resPrefix("nvs.png")));		

		  JMenuBar menuBar = new JMenuBar();

		  JMenu fileMenu = new JMenu(gData.translate("File"));
	      menuBar.add(fileMenu);
	      fileMenu.addActionListener(this);
	      
	      
	      JMenuItem openItem = new JMenuItem(gData.translate("open"));
	      openItem.setActionCommand("OPEN");
	      openItem.addActionListener(this);
	      fileMenu.add(openItem);

	      
	      JMenuItem saveItem = new JMenuItem(gData.translate("save"));
	      saveItem.setActionCommand("SAVE");
	      saveItem.addActionListener(this);
	      fileMenu.add(saveItem);

	      JMenuItem saveAsItem = new JMenuItem(gData.translate("save as"));
	      saveAsItem.setActionCommand("SAVE_AS");
	      saveAsItem.addActionListener(this);
	      fileMenu.add(saveAsItem);
	      
	      
	      JMenuItem importItem = new JMenuItem(gData.translate("import"));
	      importItem.setActionCommand("IMPORT");
	      importItem.addActionListener(this);
	      fileMenu.add(importItem);

	      JMenuItem newItem = new JMenuItem(gData.translate("new"));
	      newItem.setActionCommand("NEW");
	      newItem.addActionListener(this);
	      fileMenu.add(newItem);
	      
	      
	      JMenuItem exitItem = new JMenuItem(gData.translate("exit"));
	      exitItem.addActionListener(event -> System.exit(0));
	      fileMenu.add(exitItem);
	      
	      
	      
	      
	      /// Music Editor ///////////

		 JMenu musicEditorMenu = new JMenu("Справочники");

/*		 
		 JMenuItem newCompositionItem = new JMenuItem(gData.translate("composition"));
		 newCompositionItem.setActionCommand("COMPOSITION");
		 newCompositionItem.addActionListener(this);
		 musicEditorMenu.add(newCompositionItem);
*/		 
		 
		 JMenuItem commonViewItem = new JMenuItem(gData.translate("Server list"));
			
			commonViewItem.addActionListener(event ->
	         {
		            gData.mainFrame.createInternalFrame(new ServerListPanel(gData), gData.translate("Common view"));	        	 
		            gData.counter ++;
	         });	     
	     
	     
	     musicEditorMenu.add(commonViewItem);



	     
	     menuBar.add(musicEditorMenu);

	      
/// Windows ////	      
	      JMenu windowMenu = new JMenu(gData.translate("Window"));
	      menuBar.add(windowMenu);
	      JMenuItem nextItem = new JMenuItem(gData.translate("Next"));
	      nextItem.addActionListener(event -> selectNextWindow());
	      windowMenu.add(nextItem);
	      JMenuItem cascadeItem = new JMenuItem(gData.translate("Cascade"));
	      cascadeItem.addActionListener(event -> cascadeWindows());
	      windowMenu.add(cascadeItem);
	      JMenuItem tileItem = new JMenuItem(gData.translate("Tile"));
	      tileItem.addActionListener(event -> tileWindows());
	      windowMenu.add(tileItem);
	      final JCheckBoxMenuItem dragOutlineItem = new JCheckBoxMenuItem(gData.translate("Drag Outline"));
	      dragOutlineItem.addActionListener(event ->
	      
	      gData.desktop.setDragMode(dragOutlineItem.isSelected() ? JDesktopPane.OUTLINE_DRAG_MODE  : JDesktopPane.LIVE_DRAG_MODE));         
	      windowMenu.add(dragOutlineItem);
	      
	      
		
		return menuBar;
		
		
	}

private void selectNextWindow() {
	
}
private void cascadeWindows() {
	
}
private void tileWindows() {
	
}



@Override
public void actionPerformed(ActionEvent arg0) {
	

	
	switch (arg0.getActionCommand()) {

		case "OPEN":

			
			gData.fchooser.setCurrentDirectory(new File(gData.mainPath)); 
			int returnVal = gData.fchooser.showOpenDialog(gData.mainFrame);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            
	        	File file = gData.fchooser.getSelectedFile();

//	            gData.currentComposition = Composition.openFromDisk(file.getAbsolutePath());
//				
//	            gData.currentComposition.fileName = file.getAbsolutePath();
	            
	            gData.repainAllFrames();
	        
	        
	        } else {
	        	System.out.println("Cancel");
	        }
			
			
		
		break;
		case "SAVE":
	    	 
//	        gData.currentComposition.saveToDisk(gData.currentComposition.fileName);
			gData.repainAllFrames();
			
			
			break;

		case "IMPORT":

			importFileFromXml();
			
			break;
			
			
		case "SAVE_AS":

			savFileAs();
			
			break;

		case "COMPOSITION":

			createNewComposition();
			
			break;			

		case "NEW":

			createNewFile();
			
			break;			
		default:
			break;			
	}
	
	
	
}

private void createNewComposition() {
	
//    gData.mainFrame.createInternalFrame(new CompositionView(gData), gData.translate("composition"));	        	 
	
}

private void savFileAs() {
	
	gData.fchooser.setCurrentDirectory(new File(gData.mainPath)); 

	int returnVal = gData.fchooser.showSaveDialog(gData.mainFrame);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
        

    	File file = gData.fchooser.getSelectedFile();
 
//    	gData.currentComposition.fileName = file.getAbsolutePath();
//    	gData.currentComposition.saveToDisk(file.getAbsolutePath());
		gData.repainAllFrames();
    
    
    } 
}




private void importFileFromXml() {

	gData.fchooser.setCurrentDirectory(new File(gData.mainPath)); 
	int returnVal = gData.fchooser.showOpenDialog(gData.mainFrame);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = gData.fchooser.getSelectedFile();

//		SongXMLReader reader = new SongXMLReader(gData);       

//		reader.readSong(file.getAbsolutePath());
//        reader.fillNotes();
//        reader.renumAllTakts();
//        
		gData.repainAllFrames();
    
    
    } 
}
private void saveFile() {
	
	String outFileName = gData.mainPath + File.separator + "albums" + File.separator + "Koster.mbc";
//	if(gData.currentComposition!= null) {
//		
//		if (gData.currentComposition.fileName != null && !gData.currentComposition.fileName.isEmpty()) {
//			gData.currentComposition.saveToDisk(outFileName);
//			
//		}
//	} else {
//		
//		savFileAs();
//		
//	}
}
private void createNewFile() {
	
//	JInternalFrame iframe_new = gData.mainFrame.createInternalFrame(new SongPanel(gData), gData.translate("new"));
	
}
}
