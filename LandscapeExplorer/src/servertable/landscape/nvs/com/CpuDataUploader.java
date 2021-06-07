package servertable.landscape.nvs.com;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import landscapeexplorer.nvs.com.Utils;

public class CpuDataUploader {
	public ServerListTable table;

	public CpuDataUploader(ServerListTable table) {
		this.table = table;
	}

	public void upload_CPU() {

//		upload_win_CPU();
		upload_linux_CPU();

	}

	public void upload_linux_CPU() {
		
		int globalCounter = 0;
		
		for (int row = 0; row < table.getRowCount(); row++) {
			
			String os = (String) table.getValueAt(row, 5);
			String status = (String) table.getValueAt(row, 28);
			String hostname = (String) table.getValueAt(row, 2);
			
			if (os.equals("LINUX")) {
				String fileName = table.gData.mainPath + File.separator + "manual" + File.separator + "bigdata_linux"
						+ File.separator + hostname + "_cpu.txt";
				
				File f = new File(fileName);
				if (f.exists() && (f.length() > 0)) {
					
					globalCounter++;
					
					System.out.println(globalCounter+ ") "+ fileName);
					List<String> inputFile = Utils.readFile(fileName);

					
					
					int counter = 0;
					String model = "";					
					for(String line: inputFile) {
						


						if (line.contains("model")) {
							
							model = line;
							counter++;
						
						}
						
					}
					System.out.println(model + " " + counter + " " +hostname);	

					String modelParts[] = model.split("\\s+");
					if ( modelParts.length >=10 ) {
						model = modelParts[9];							
					}	
					
					System.out.println(model + " " + counter + " " +hostname);
					table.setValueAt(counter + "", row, 6);
					table.setValueAt(model.replace("GHz", ""), row, 7);
					
				}
				
				
				
				
			}
			
		}
		
		
	}
	
	
	
	
	
	public void upload_win_CPU() {

		System.out.println("upload_CPU");

		for (int row = 0; row < table.getRowCount(); row++) {

			String os = (String) table.getValueAt(row, 5);
			String status = (String) table.getValueAt(row, 28);
			String hostname = (String) table.getValueAt(row, 2);

			if (os.equals("WIN")) {

				String fileName = table.gData.mainPath + File.separator + "manual" + File.separator + "bigdata_win"
						+ File.separator + hostname + "_sys.txt";

				File f = new File(fileName);
				if (f.exists() && (f.length() > 0)) {

					try {

						Scanner sc = new Scanner(f, "UTF-8");
						List<String> lines = new ArrayList<String>();
						while (sc.hasNextLine()) {

							String line = sc.nextLine();

							if (line.contains("Processor(s)")) {

								String[] lineParts = line.split("\\s+");

								String cpu_params = sc.nextLine();
								String[] cpu_paramsParts = cpu_params.split("\\s+");

								String CPU_FREQ = cpu_paramsParts[10].replace("~", "");
								Float freq = Float.valueOf(CPU_FREQ);

								String CPU_FREQ_HGZ = String.format("%.02f", freq / 1024);
								System.out.println("CPU=" + lineParts[1]);
								System.out.println("CPU_FREQ=" + CPU_FREQ_HGZ);

								table.setValueAt(lineParts[1], row, 6);
								table.setValueAt(CPU_FREQ_HGZ, row, 7);

							}

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(f.getAbsolutePath());
						e.printStackTrace();
					}

				}

			}
		}
	}
}
