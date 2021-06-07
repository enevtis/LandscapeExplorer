package landscapeexplorer.nvs.com;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;

import serverlist.landscapeexplorer.nvs.com.ServerListPanel;


public class LandscapeExplorerMain {

	public static void main(String[] args) {
	      EventQueue.invokeLater(() ->
	         {

	        	    try {
	        	        
	                    UIManager.setLookAndFeel(
	                        UIManager.getSystemLookAndFeelClassName());						// Like Windows
	        	    
	        	    } 
	                catch (Exception e) {
	                   
	                }

	        	 LoadLibrary();
	        	    
	        	LandscapeExplorerFrame frame = new LandscapeExplorerFrame();
	            frame.setTitle("Landscape Explorer. v1.0"); 
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);

	            // Common view all takts ///


	            

	            
	            JInternalFrame iframe1=frame.createInternalFrame(new ServerListPanel(frame.gData), frame.gData.translate("Server list"));	        	 
//	            JInternalFrame iframe1=frame.createInternalFrame(new ServerListTable(frame.gData), frame.gData.translate("Server list"));	
	            Dimension desktopSize = frame.getSize();
	            Dimension jInternalFrameSize = iframe1.getSize();
	            iframe1.setLocation(0,0);
	            iframe1.setSize(new Dimension((int) (desktopSize.width * 0.9), (int) (desktopSize.height * 0.8)));


	            
	            
	            
	         
	         });
	      
	      
	      


	}

	private static void LoadLibrary() {
		String currentLibraryPath = System.getProperty("user.dir") + File.separator + "lib";
		String fileName = "";
		File folder = new File(currentLibraryPath);

		String[] files = folder.list();

		for (String file : files) {
			try {
				fileName = currentLibraryPath + File.separator + file;

				if (fileName.endsWith("jar")) {
					addSoftwareLibrary(new File(fileName));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	private static void addSoftwareLibrary(File file) throws Exception {
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { file.toURI().toURL() });
	}	
	
}
