package servertable.landscape.nvs.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ScriptHelper {

	public ServerListTable table;
	
	public ScriptHelper(ServerListTable table) {
		this.table = table;
	}
	
	
	public void createWinScript() {
		
	String outputFileName = table.gData.mainPath + File.separator + "data"
			+ File.separator + "win_script.txt";
		
	List<String> lines = new ArrayList<String>();
	String line = "";

	for (int i = 0; i < table.getColumnCount(); i++) {
		line += table.getColumnName(i) + ";";

	}
	lines.add(line);

	for (int row = 0; row < table.getRowCount(); row++) {

		String os = (String) table.getValueAt(row, 5);
		String status = (String) table.getValueAt(row, 28);
		
		if (os.equals("WIN") && status.equals("ACTIVE")) {
		
			
			String authKey = (String) table.getValueAt(row, 4);
			String keyUser = authKey + "_USER";
			String keyPassword = authKey + "_PASSWORD";

			
			
				line = "call psexec_win.bat " 
				+ (String) table.getValueAt(row, 2) + " " + (String) table.getValueAt(row,3) + " "
				+ table.gData.commonParams.get(keyUser) + " " + table.gData.commonParams.get(keyPassword) ;

				lines.add(line);
	}
	}


	try {
		PrintWriter pw = new PrintWriter(new FileOutputStream(outputFileName));
		for (String l : lines)
			pw.println(l);
		pw.close();

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
		
	}
	
	public void createLinuxScript() {
		
	String outputFileName = table.gData.mainPath + File.separator + "data"
			+ File.separator + "aix_script.txt";
		
	List<String> lines = new ArrayList<String>();
	String line = "";

	for (int i = 0; i < table.getColumnCount(); i++) {
		line += table.getColumnName(i) + ";";

	}
	lines.add(line);

	for (int row = 0; row < table.getRowCount(); row++) {

		String os = (String) table.getValueAt(row, 5);
		String status = (String) table.getValueAt(row, 28);
		
		if (os.equals("AIX") && status.equals("ACTIVE")) {
		
			
			String authKey = (String) table.getValueAt(row, 4);
			String keyUser = authKey + "_USER";
			String keyPassword = authKey + "_PASSWORD";

			
			
				line = "call plink_linux.bat " 
				+ (String) table.getValueAt(row, 2) + " " + (String) table.getValueAt(row,3) + " "
				+ table.gData.commonParams.get(keyUser) + " " + table.gData.commonParams.get(keyPassword) ;

				lines.add(line);
	}
	}


	try {
		PrintWriter pw = new PrintWriter(new FileOutputStream(outputFileName));
		for (String l : lines)
			pw.println(l);
		pw.close();

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
		
	}
	
	
}
