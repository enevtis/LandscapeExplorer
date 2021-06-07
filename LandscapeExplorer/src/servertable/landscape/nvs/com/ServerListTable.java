package servertable.landscape.nvs.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;

import landscapeexplorer.nvs.com.Utils;
import landscapeexplorer.nvs.com.globalData;

public class ServerListTable extends JTable {

	public globalData gData;
	public ServerListTableListener listener;
	public ServerListTableMenu menu;

	public ServerListTable(globalData gData) {
		this.gData = gData;
		this.listener = new ServerListTableListener(gData, this);
		this.addMouseListener(this.listener);

		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this.listener);

		this.menu = new ServerListTableMenu(this);
		this.setComponentPopupMenu(this.menu);

	}

	public void setUpTableData0() {

		String workDir = gData.mainPath + File.separator + "data" + File.separator;
		String fileSource = workDir + gData.commonParams.get("MainServersTableFile");
		String fileDest = workDir + "text.txt";

		Scanner sc;
		try {
			sc = new Scanner(new File(fileSource));
			List<String> lines = new ArrayList<String>();
			while (sc.hasNextLine()) {
				lines.add(Utils.cleanInvalidCharacters(sc.nextLine()));
			}

			String[] arr = lines.toArray(new String[0]);

			System.out.println(fileSource + " rowCounter=" + arr.length);

			FileWriter writer = new FileWriter(fileDest);
			for (String str : arr) {
				writer.write(str + System.lineSeparator());
			}
			writer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setUpTableData() {

		String workDir = gData.mainPath + File.separator + "data" + File.separator;
		String fileSource = workDir + gData.commonParams.get("MainServersTableFile");

//		  this.serverListTable = new JTable();
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		tableModel.setRowCount(0);

		List<String> lines = new ArrayList<String>();

		try {

			FileInputStream fs = new FileInputStream(fileSource);
			DataInputStream dis = new DataInputStream(fs);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(dis,"Cp1251"));
			String line;
			int rowCounter = 0;
			int colCounter = 0;

			while ((line = buffer.readLine()) != null) {
				line = line.trim();

				if ((line.length() != 0)) {

					String[] parts = line.split(";");

					if (rowCounter == 0) {

						colCounter = parts.length;
						String[] columns = new String[colCounter];

						for (int k = 0; k < colCounter; k++) {
							columns[k] = parts[k];

						}
						tableModel.setColumnIdentifiers(columns);

					} else {

						Object[] data = new Object[colCounter];

						for (int l = 0; l < colCounter; l++) {

							data[l] = (l < parts.length) ? parts[l] : "";

						}

						tableModel.addRow(data);

					}

					rowCounter++;

				}
			}

			this.setModel(tableModel);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setUpTableDataXLS() {

		String workDir = gData.mainPath + File.separator + "data" + File.separator;
		String fileSource = workDir + gData.commonParams.get("MainServersTableFile");
		String fileDest = workDir + gData.commonParams.get("MainServersTableFileOutput");

//		  this.serverListTable = new JTable();
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		tableModel.setRowCount(0);

		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(fileSource)));

			XSSFSheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);

			String[] columns = new String[row.getLastCellNum()];

			for (int k = 0; k < row.getLastCellNum(); k++) {
				final DataFormatter df = new DataFormatter();
				final XSSFCell cell = (XSSFCell) row.getCell(k);
				columns[k] = df.formatCellValue(cell);
			}
			tableModel.setColumnIdentifiers(columns);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);

				int lastCol = (row != null) ? row.getLastCellNum() : 0;

				if (lastCol > 0) {

					Object[] data = new Object[lastCol];

					for (int y = 0; y < lastCol; y++) {
						final DataFormatter df = new DataFormatter();
						final XSSFCell cell = (XSSFCell) row.getCell(y);
						data[y] = df.formatCellValue(cell);

					}

					tableModel.addRow(data);

				}

			}

			this.setModel(tableModel);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
