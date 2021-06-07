package landscapeexplorer.nvs.com;

import java.io.InputStream;
import java.util.Map;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshRequest {

	public Map<String,String> params;
	globalData gData;
	
	
	public SshRequest(globalData gData, Map<String,String> params) {
		this.params = params;
		this.gData = gData;
	}
	
	public String getRemoteSshRequest(String cmdText) {
		String out = "";
		String cmdKey = cmdText + "_" + params.get("os");
		String remoteCommand = gData.commonParams.get(cmdKey);
		
		
		if (remoteCommand == null) {

			return "ERR: Not found command for key " + cmdKey;
		}
		if (isInvalidSshCommand(remoteCommand)) {

			return "ERR: wrong command! Ignored";			
			
		}
		
		
		
		try {
			out = getSsh(params.get("ip"), params.get("user"), params.get("password"), remoteCommand);
			System.out.println(params.get("ip") + " " + cmdText + " " + remoteCommand + " executed successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}
	public String getSsh(String ip, String user, String password, String strCommand) throws Exception {

		String out = "";

		int exitStatus = -100;

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		JSch jsch = new JSch();
		Session session = jsch.getSession(user, ip, 22);
		session.setPassword(password);
		session.setConfig(config);
		session.setTimeout(Integer.valueOf(gData.commonParams.get("SshTimeoutSec")) * 1000);
		session.connect();

		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(strCommand);
		channel.setInputStream(null);
		((ChannelExec) channel).setErrStream(System.err);

		InputStream in = channel.getInputStream();
		channel.connect();
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				out += new String(tmp, 0, i);
			}

			if (channel.isClosed()) {
				exitStatus = channel.getExitStatus();

				break;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception ee) {
			}
		}
		channel.disconnect();
		session.disconnect();

		return out;

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
