package servertable.landscape.nvs.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import landscapeexplorer.nvs.com.SshRequest;
import landscapeexplorer.nvs.com.WindowsRequest;

public class ServerListTableMenu extends JPopupMenu implements ActionListener {

	ServerListTable parent;

	public ServerListTableMenu(ServerListTable parent) {
		this.parent = parent;

		addMenuItem("GET_REQUEST", "Выполнить запрос");
		addMenuItem("SAVE", "Сохранить");
		addMenuItem("CREATE_WIN_SCRIPT", "Скрипт для windows");
		addMenuItem("CREATE_LINUX_SCRIPT", "Скрипт для linux");
		addMenuItem("CREATE_WORD_LIST", "создать список Word");
		addMenuItem("UPLOAD_DATA_CPU", "загрузить данные по CPU");
		addMenuItem("UPLOAD_DATA_RAM", "загрузить данные по RAM");

	}

	public void addMenuItem(String actionCmd, String descr) {
		JMenuItem it = new JMenuItem(descr);
		it.addActionListener(this);
		it.setActionCommand(actionCmd);

		this.add(it);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int column = 0;
		int row = 0;
		String value = "";

		switch (arg0.getActionCommand()) {

		case "GET_REQUEST":

			row = parent.getSelectedRow();
			String remoteOs = parent.getModel().getValueAt(row, 5).toString();

			if (remoteOs.equals("LINUX") || remoteOs.equals("AIX")) {
				sendLinuxRequest();
			} else if (remoteOs.equals("WIN")) {
				sendWindowsRequest();
			}

			break;

		case "SAVE_TIME_VERSION":
			saveTimeVersionFile();
			break;
		case "CREATE_WIN_SCRIPT":
			createWinScript();
			break;
		case "CREATE_LINUX_SCRIPT":
			createLinuxScript();
			break;
		case "SAVE":
			saveAndRewriteFile();
			break;
		case "CREATE_WORD_LIST":
			WordListHelper wlh = new WordListHelper(parent);
			wlh.createWordList();

			break;
		case "UPLOAD_DATA_CPU":
			CpuDataUploader cdu = new CpuDataUploader(parent);
			cdu.upload_CPU();

			break;
		case "UPLOAD_DATA_RAM":
			RamDataUploader rdu = new RamDataUploader(parent);
			rdu.upload_RAM();

			break;

		default:
		}

//		System.out.println("Action performed");

	}

	public void createWinScript() {

		ScriptHelper sh = new ScriptHelper(parent);
		sh.createWinScript();

	}

	public void createLinuxScript() {

		ScriptHelper sh = new ScriptHelper(parent);
		sh.createLinuxScript();

	}

	public void sendLinuxRequest() {
		SshRequest req = new SshRequest(parent.gData, readConnectParams());

		int row = parent.getSelectedRow();
		int col = parent.getSelectedColumn();

		String header = parent.getColumnName(col);
		String result = req.getRemoteSshRequest(header);

		parent.getModel().setValueAt(result, row, col);

	}

	public void sendWindowsRequest() {
		WindowsRequest req = new WindowsRequest(parent.gData, readConnectParams());

		int row = parent.getSelectedRow();
		int col = parent.getSelectedColumn();

		String header = parent.getColumnName(col);
		String result = req.getRemoteWindowsRequest(header);

		parent.getModel().setValueAt(result, row, col);

	}

	public Map readConnectParams() {
		Map<String, String> out = new HashMap<String, String>();
		int row = 0;
		row = parent.getSelectedRow();
		out.put("ip", parent.getModel().getValueAt(row, 3).toString());
		String authKey = parent.getModel().getValueAt(row, 4).toString();
		out.put("os", parent.getModel().getValueAt(row, 5).toString());

		if (authKey.isEmpty())
			return out;
		String keyUser = authKey + "_USER";
		String keyPassword = authKey + "_PASSWORD";

		out.put("user", parent.gData.commonParams.get(keyUser));
		out.put("password", parent.gData.commonParams.get(keyPassword));

		return out;
	}

	public void saveAndRewriteFile() {

		String workDir = parent.gData.mainPath + File.separator + "data" + File.separator;
		String fileSource = workDir + parent.gData.commonParams.get("MainServersTableFile");

		saveFile(fileSource);

	}

	public void saveTimeVersionFile() {

		String outputFileName = parent.gData.mainPath + File.separator + "data" + File.separator;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmSS");
		Date now = new Date();
		String strDate = sdf.format(now);
		outputFileName += "servers_" + strDate + ".csv";

		saveFile(outputFileName);

//		List<String> lines = new ArrayList<String>();
//		String line = "";	
//
//		for (int i=0; i < parent.getColumnCount(); i++) {
//			line += parent.getColumnName(i) + ";";
//			
//		}
//		lines.add(line);
//		
//		for (int row = 0; row < parent.getRowCount(); row++) {
//
//			line = "";
//			for (int col = 0; col < parent.getColumnCount(); col++) {
//				String buffer = cleanInvalidCharacters((String)parent.getValueAt(row, col));
//				line += buffer + ";";
//			}
//			lines.add(line);
//
//		}
//
//		String outputFileName = parent.gData.mainPath + File.separator + "data" + File.separator;
//      	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmSS");
//      	Date now = new Date();
//        String strDate = sdf.format(now);
//        outputFileName += "servers_" + strDate + ".csv";
//		
//		
//		try {
//			PrintWriter pw = new PrintWriter(new FileOutputStream(outputFileName));
//			for (String l : lines)
//				pw.println(l);
//			pw.close();
//			System.out.println("Time version have been saved to " + outputFileName);
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public void saveFile(String outputFileName) {

		List<String> lines = new ArrayList<String>();
		String line = "";

		for (int i = 0; i < parent.getColumnCount(); i++) {
			line += parent.getColumnName(i) + ";";

		}
		lines.add(line);

		for (int row = 0; row < parent.getRowCount(); row++) {

			line = "";
			for (int col = 0; col < parent.getColumnCount(); col++) {
				String buffer = cleanInvalidCharacters((String) parent.getValueAt(row, col));
				line += buffer + ";";
			}
			lines.add(line);

		}

		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(outputFileName));
			for (String l : lines)
				pw.println(l);
			pw.close();
			System.out.println("Time version have been saved to " + outputFileName);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
}
