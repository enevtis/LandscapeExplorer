package servertable.landscape.nvs.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;

public class WordListHelper {
	public ServerListTable table;
	
	public WordListHelper(ServerListTable table) {
		this.table = table;
	}

	public void createWordList() {
	
		String workDir = table.gData.mainPath + File.separator + "data" + File.separator;
		String fileTemplate = table.gData.commonParams.get("wordTemplateFile");
		String fileOutput = table.gData.commonParams.get("wordOutputFile");
		Map<String,String> replVal = new HashMap<String,String>();
		
		try {
		
		       XWPFDocument doc = new XWPFDocument(new FileInputStream(workDir + fileTemplate));

		       XWPFDocument destDoc = new XWPFDocument();
			
				XWPFStyles newStyles = destDoc.createStyles();
				newStyles.setStyles(doc.getStyle());  

				String strStyleId = "ownstyle1";

		   		addCustomHeadingStyle(destDoc, strStyleId, 1);
			
		   		OutputStream out = new FileOutputStream(workDir + fileOutput);
			
				for (int row = 0; row <  table.getRowCount(); row++) {
				String status = (String) table.getValueAt(row, 28);

				if (status.equals("ACTIVE")) {
				
				Map<String,String> replMap = fillReplaveValue(row);
				
				addOneServerToWordDocument(destDoc, fillReplaveValue(row));

				}
			}
	
			destDoc.write(out);
			destDoc.close();
			doc.close();
			
			String message = "Word file has been written to "+ workDir + fileOutput;
			JOptionPane.showMessageDialog(table.gData.mainFrame, message);

			System.out.println("Doc has been created to " + workDir + fileOutput);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public Map<String,String> fillReplaveValue(int row) {
		
	
		/*
		nn	0
		guid	1
		short	2
		def_ip	3
		authorization	4
		os	5
		CPU_COUNT	6
		CPU_FREQ	7
		RAM_GB	8
		LOCAL_HDD	9
		LAN_HDD	10
		CONS_IP	11
		IP_ADDR	12
		DNS_NAME	13
		SOFT_NAME	14
		CLUSTER_NAME	15
		OS_NAME	16
		DB_NAME	17
		WEB_NAME	18
		OTHER_NAME	19
		REGLAM	20
		SN	21
		model	22
		def_hostname	23
		role_typ	24
		zod	25
		os_typ	26
		short	27
		status	28
		 
		 */
		
		Map<String,String> out = new HashMap();

		out.put("SER_NUM", (String)table.getValueAt(row, 21));	
		out.put("CPU_COUNT", (String)table.getValueAt(row, 6));		
		out.put("CPU_FREQ", (String)table.getValueAt(row, 7));	
		out.put("RAM_GB", (String)table.getValueAt(row, 8));	
		out.put("LOCAL_HDD", (String)table.getValueAt(row, 9));		
		out.put("LAN_HDD", (String)table.getValueAt(row, 10));	
		out.put("CONS_IP", (String)table.getValueAt(row, 11));			
		out.put("IP_ADDR", (String)table.getValueAt(row, 3));
		out.put("DNS_NAME", (String)table.getValueAt(row, 23));			
		out.put("SOFT_NAME", (String)table.getValueAt(row, 14));			
		out.put("CLUSTER_NAME", (String)table.getValueAt(row, 15));		
		out.put("OS_NAME", (String)table.getValueAt(row, 26));		
		out.put("DB_NAME", (String)table.getValueAt(row, 17));
		out.put("WEB_NAME", (String)table.getValueAt(row, 18));		
		out.put("OTHER_NAME", (String)table.getValueAt(row, 19));			
		out.put("REGLAM", (String)table.getValueAt(row, 20));
		String location = (String)table.getValueAt(row, 25);
		out.put("LOCATION", location.toUpperCase().contains("M") ? " ЦОД-М" : "ЦОД-Р");		
		return out;
	}
	
	
	
	public void addOneServerToWordDocument(XWPFDocument destDoc, Map<String,String> replVal) {
		
		String workDir = table.gData.mainPath + File.separator + "data" + File.separator;
		String fileTemplate = "template_1.docx";
		String fileOutput = "output.docx";
	
		
		try {
		
		XWPFDocument doc = new XWPFDocument(new FileInputStream(workDir + fileTemplate));

	
		for (Map.Entry<String, String> entry : replVal.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();

		    	replaceText (doc,key,value);
		    
		}	
		
		
		
		
		for (IBodyElement bodyElement : doc.getBodyElements()) {

            BodyElementType elementType = bodyElement.getElementType();

            if (elementType.name().equals("PARAGRAPH")) {

                XWPFParagraph pr = (XWPFParagraph) bodyElement;

					destDoc.createParagraph();

                int pos = destDoc.getParagraphs().size() - 1;

                destDoc.setParagraph(pr, pos);

            } else if( elementType.name().equals("TABLE") ) {

                XWPFTable table = (XWPFTable) bodyElement;

				table.setStyleID("");
                destDoc.createTable();
      

                int pos = destDoc.getTables().size() - 1;

                destDoc.setTable(pos, table);
            }
        }
		
		
		
		doc.close();

		
		
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
		}

	
	
	
	}
	
public void replaceText(XWPFDocument doc,  String word1, String word2) {
	

	
	
	for (XWPFParagraph p : doc.getParagraphs()) {
	    List<XWPFRun> runs = p.getRuns();
	    if (runs != null) {
	        for (XWPFRun r : runs) {
	            String text = r.getText(0);
	            if (text != null && text.contains(word1)) {
	                text = text.replace(word1, word2);
	                r.setText(text, 0);
	            }
	        }
	    }
	}
	for (XWPFTable tbl : doc.getTables()) {
	   for (XWPFTableRow row : tbl.getRows()) {
	      for (XWPFTableCell cell : row.getTableCells()) {
	         for (XWPFParagraph p : cell.getParagraphs()) {
	            for (XWPFRun r : p.getRuns()) {
	              String text = r.getText(0);
	              if (text != null && text.contains(word1)) {
	                text = text.replace(word1, word2);
	                r.setText(text,0);
	              }
	            }
	         }
	      }
	   }
	}
	
}
	

private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

    CTStyle ctStyle = CTStyle.Factory.newInstance();
    ctStyle.setStyleId(strStyleId);

    CTString styleName = CTString.Factory.newInstance();
    styleName.setVal(strStyleId);
    ctStyle.setName(styleName);

    CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
    indentNumber.setVal(BigInteger.valueOf(headingLevel));

    // lower number > style is more prominent in the formats bar
    ctStyle.setUiPriority(indentNumber);

    CTOnOff onoffnull = CTOnOff.Factory.newInstance();
    ctStyle.setUnhideWhenUsed(onoffnull);

    // style shows up in the formats bar
    ctStyle.setQFormat(onoffnull);

    // style defines a heading of the given level
    CTPPr ppr = CTPPr.Factory.newInstance();
    ppr.setOutlineLvl(indentNumber);
 //   ctStyle.setPPr(ppr);

    XWPFStyle style = new XWPFStyle(ctStyle);

    // is a null op if already defined
    XWPFStyles styles = docxDocument.createStyles();

    style.setType(STStyleType.PARAGRAPH);
    styles.addStyle(style);

}
}



