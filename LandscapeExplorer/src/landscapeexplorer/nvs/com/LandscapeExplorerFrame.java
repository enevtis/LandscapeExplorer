package landscapeexplorer.nvs.com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class LandscapeExplorerFrame extends JFrame{

	   private static final int DEFAULT_WIDTH = 1500;
	   private static final int DEFAULT_HEIGHT = 1000;
	   
	   //////  *********** for Drag and Drop operations *************  ///////
	    public DragSource dragSource = new DragSource();
	    public DropTarget sourceDropTarget ;
	    public DropTarget targetDropTarget ;
	    public DragGestureRecognizer draggesturerecognizer ;
	   
	   
	   
	   
	   private int nextFrameX;
	   private int nextFrameY;
	   private int frameDistance;
	   private int counter;
	   
	   public globalData gData = new globalData();
//	   public List<JInternalFrame> frames = new ArrayList<>();	

	   public HashMap<String,JInternalFrame> frames=new HashMap<String,JInternalFrame>(); 
	   
	   

	   public LandscapeExplorerFrame()
	   {
	      
		   
		   setIconImage(gData.nvsIcon.getImage());
		   setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		   gData.mainFrame = this;
		   
		   gData.desktop = new JDesktopPane();
		   add(gData.desktop, BorderLayout.CENTER);
	      
		   gData.desktop.setBackground(new Color(240,240,240));

	      JMenuBar menuBar = gData.menu.getMainMenu();
	      setJMenuBar(menuBar);


//	        setFocusTraversalKeysEnabled(false);

/*	      
	      
//		    dragSource = new DragSource();
		    sourceDropTarget = new DropTarget(panel1, DnDConstants.ACTION_MOVE, panel1);
		    targetDropTarget = new DropTarget(panel2, DnDConstants.ACTION_MOVE, panel2);

		    draggesturerecognizer = dragSource.createDefaultDragGestureRecognizer(panel1, DnDConstants.ACTION_MOVE, panel1);
*/	      
	      
	      
	      


	   }

	   public JInternalFrame createInternalFrame(Component c, String t)
	   {
	      
		   
		   final JInternalFrame iframe = new JInternalFrame(t, true, // resizable
	            true, // closable
	            true, // maximizable
	            true); // iconifiable

	      iframe.add(c, BorderLayout.CENTER);

	      gData.desktop.add(iframe);
      
	      
	      iframe.setFrameIcon(gData.appIcon);

	      // add listener to confirm frame closing
	      iframe.addVetoableChangeListener(event ->
	         {
	            String name = event.getPropertyName();
	            Object value = event.getNewValue();

	            // we only want to check attempts to close a frame
	            if (name.equals("closed") && value.equals(true))
	            {
	               // ask user if it is ok to close
	               int result = JOptionPane.showInternalConfirmDialog(iframe, "OK to close?",
	                     "Select an Option", JOptionPane.YES_NO_OPTION);

	               // if the user doesn't agree, veto the close
	               if (result != JOptionPane.YES_OPTION) throw new PropertyVetoException(
	                     "User canceled close", event);
	            }
	         });


	      int width = gData.desktop.getWidth() / 2;
	      int height = gData.desktop.getHeight() / 2;
	      iframe.reshape(nextFrameX, nextFrameY, width, height);

	      iframe.show();


	      try
	      {
	         iframe.setSelected(true);
	      }
	      catch (PropertyVetoException ex)
	      {
	      }

	      frameDistance = iframe.getHeight() - iframe.getContentPane().getHeight();



	      nextFrameX += frameDistance;
	      nextFrameY += frameDistance;
	      if (nextFrameX + width > gData.desktop.getWidth()) nextFrameX = 0;
	      if (nextFrameY + height > gData.desktop.getHeight()) nextFrameY = 0;
	   
	      this.frames.put(t,iframe);
	      
	      
	      return iframe;
	   }

}
