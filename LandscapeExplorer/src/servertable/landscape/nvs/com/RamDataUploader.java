package servertable.landscape.nvs.com;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import landscapeexplorer.nvs.com.Utils;

public class RamDataUploader {
	public ServerListTable table;

	public RamDataUploader(ServerListTable table) {
		this.table = table;
	}

	public void upload_RAM() {
	
		
		upload_win_RAM();
//		upload_linux_RAM();
		
//		String hostname = "mlk-opsbi-bp";
//		String fileName = table.gData.mainPath + File.separator + "manual" + File.separator + "bigdata_win"
//				+ File.separator + hostname + "_sys.txt";
//		List<String> inputFile = readFile(fileName);
//		
//		for(String s: inputFile) {
//			
//			System.out.println(s);
//			
//			
//		}
//		
		
		
		
	}

	
	public void upload_linux_RAM() {
		
		int globalCounter = 0;
		
		for (int row = 0; row < table.getRowCount(); row++) {
			
			String os = (String) table.getValueAt(row, 5);
			String status = (String) table.getValueAt(row, 28);
			String hostname = (String) table.getValueAt(row, 2);
			
			if (os.equals("LINUX")) {
				String fileName = table.gData.mainPath + File.separator + "manual" + File.separator + "bigdata_linux"
						+ File.separator + hostname + "_ram.txt";
				
				File f = new File(fileName);
				if (f.exists() && (f.length() > 0)) {
					
					globalCounter++;
					
					System.out.println(globalCounter+ ") "+ fileName);
					List<String> inputFile = Utils.readFile(fileName);

					
					
					int counter = 0;
					String value = "";					
					for(String line: inputFile) {
						
//						System.out.println(line );	

						if (line.contains("Mem:")) {
							
							value = line;
							
							String modelParts[] = value.split("\\s+");

							if ( modelParts.length >=2 ) {
								value = modelParts[1];
								String SIZE_GB = String.format("%d",Long.valueOf(value) / 1024 /1024);
								System.out.println("SIZE_GB=" + SIZE_GB);
								table.setValueAt(SIZE_GB, row, 8);

							}			
							
							
						
						}
						
					}

					;
	
					
//					System.out.println(String.format("%d", Long.valueOf(value) / 1024));
//					table.setValueAt(counter + "", row, 6);
//					table.setValueAt(model.replace("GHz", ""), row, 7);
					
				}
				
				
				
				
			}
			
		}
		
		
	}
	
	
	public void upload_win_RAM() {



		for (int row = 0; row < table.getRowCount(); row++) {

			String os = (String) table.getValueAt(row, 5);
			String status = (String) table.getValueAt(row, 28);
			String hostname = (String) table.getValueAt(row, 2);

//			if (os.equals("WIN")) {

				String fileName = table.gData.mainPath + File.separator + "manual" + File.separator + "bigdata_win"
						+ File.separator + hostname + "_sys.txt";

				File f = new File(fileName);
				if (f.exists() && (f.length() > 0)) {

					List<String> inputFile = readFile(fileName);
					
					for(String line: inputFile) {
						
						if (line.contains("Total Physical Memory")) {
							
							String[] lineParts = line.split("\\s+");
							String RAM = lineParts[3].replaceAll("[^0-9.]", "");

							String RAM_GB = String.format("%d", Long.valueOf(RAM) / 1024);
							System.out.println("RAM=" + line + " " + RAM_GB);
							
							table.setValueAt(RAM_GB, row, 8);
							
						}
						
						
					}
					
//					try {
//
//						Scanner sc = new Scanner(f);
//						List<String> lines = new ArrayList<String>();
//						while (sc.hasNextLine()) {
//
//							String line = sc.nextLine();
//
//							System.out.println(line + " " + hostname);
//							
//							
//							
//							if (line.contains("Available")) {
//
//								String[] lineParts = line.split("\\s+");
//								System.out.println("RAM=" + line + " " + hostname);								


//								String cpu_params = sc.nextLine();
//								String[] cpu_paramsParts = cpu_params.split("\\s+");
//
//								String CPU_FREQ = cpu_paramsParts[10].replace("~", "");
//								Float freq = Float.valueOf(CPU_FREQ);
//
//								String CPU_FREQ_HGZ = String.format("%.02f", freq / 1024);
//								System.out.println("RAM=" + line);
//								System.out.println("CPU_FREQ=" + CPU_FREQ_HGZ);

//								table.setValueAt(lineParts[1], row, 6);
//								table.setValueAt(CPU_FREQ_HGZ, row, 7);
//
//							}
//
//						}
							System.out.println("END=" + fileName);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						System.out.println(f.getAbsolutePath());
//						e.printStackTrace();
//					}

				}

//			}
		}
	}

public List<String> readFile(String fileName){
	
	
	List<String> out = new  ArrayList<String>();
	

    try 
    { 
        FileInputStream fstream_school = new FileInputStream(fileName); 
        DataInputStream data_input = new DataInputStream(fstream_school); 
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input,StandardCharsets.UTF_8)); 
        String str_line; 

        while ((str_line = buffer.readLine()) != null) 
        { 
            str_line = str_line.trim(); 
            if ((str_line.length()!=0))  
            { 
                out.add(cleanInvalidCharacters(str_line));
            } 
        }
        
    } catch (Exception e) {
    	
    	System.out.println("Can't read file " + fileName);
    	e.printStackTrace();
    	
    }
	return out;
	
}
public static String cleanInvalidCharacters(String in) {
	StringBuilder out = new StringBuilder();
	char current;
	if (in == null || ("".equals(in))) {
		return "";
	}
	for (int i = 0; i < in.length(); i++) {
		current = in.charAt(i);
		if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
				|| ((current >= 0xE000) && (current <= 0xFFFD))
				|| ((current >= 0x10000) && (current <= 0x10FFFF))) {
			out.append(current);
		}

	}
	return out.toString().replaceAll("\\s", " ");
}

 public static boolean isInvalidSshCommand(String sshText) {
	 boolean out = false;
	 if (sshText == null) return true;
	 if (sshText.isEmpty()) return true;
	 if (sshText.length() < 4) return true;
	 
	 sshText = sshText.replace(";", " ");
	 sshText = sshText.replace("-", " ");
	 sshText = sshText.replace(".", " ");
	 
	 String parts[] = sshText.split("\\s+");
	 for (String s:parts) {
		 if (s.equals("rm")) return true;
		 if (s.equals("del")) return true;			 
		 if (s.equals("delete")) return true;
		 if (s.equals("mv")) return true;
		 if (s.equals("shutdown")) return true;
		 if (s.equals("reboot")) return true;
		 if (s.equals("cp")) return true;
	 }
	 
	 return out;
 }
}
