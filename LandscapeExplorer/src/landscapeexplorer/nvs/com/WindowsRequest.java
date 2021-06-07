package landscapeexplorer.nvs.com;

import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WindowsRequest {
	public Map<String,String> params;
	globalData gData;	
	public WindowsRequest(globalData gData, Map<String,String> params) {
		this.params = params;
		this.gData = gData;
	}
	
	public String getRemoteWindowsRequest(String cmdText) {
		String out = "";
		String cmdKey = cmdText + "_" + params.get("os");
		String remoteCommand = gData.commonParams.get(cmdKey);

		if (remoteCommand == null) {
			System.out.println("Not found command for key " + cmdKey);
		}

		out += " ip=" + params.get("ip");		
		out += " user=" + params.get("user");
		out += " password=" + params.get("password");
		out += " os = " + params.get("os");
		
		out += "\n " + remoteCommand;
		

//		psexec -i \\172.16.170.56 -u MSK\USER_NAME -p Password123 -c "C:\111\PSTools\get_sn.bat"

		
		out = runCommand(remoteCommand);
		System.out.println(out);
		
		return out;
	}	
	public String runCommand(String command) {

		String result = "";

		List<String> commands = new ArrayList<String>();                

		String cmd1= gData.mainPath + File.separator + "PSTools" + File.separator
				+ "psexec.exe -i \\\\" + params.get("ip") + " -u " + params.get("user") 
				+ " -p " + params.get("password") + " cmd /c \"" + command + "\"";
		
/*		
		commands.add(gData.mainPath + File.separator + "PSTools" + File.separator
				+ "psexec.exe");
		commands.add("-i");
		commands.add("\\\\");
		commands.add(params.get("ip"));	
		commands.add("-u");
		commands.add(params.get("user"));			
		commands.add("-p");
		commands.add(params.get("password"));
		commands.add("-c");
		commands.add(command);		
*/		
		
		System.out.println(cmd1);
		commands.add(cmd1);
		
		try {

			ProcessBuilder pb = new ProcessBuilder(commands);
			pb.redirectErrorStream(true);

			Process p = pb.start();

			
			
//			p.waitFor(5, TimeUnit.SECONDS);
//			p.destroy();
			p.waitFor();

			Reader reader = new InputStreamReader(p.getInputStream());
			int ch;
			while ((ch = reader.read()) != -1) {
				result += ((char) ch);

			}
			reader.close();

		} catch (Exception e) {

			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));

		}

		return result;

	}

}
