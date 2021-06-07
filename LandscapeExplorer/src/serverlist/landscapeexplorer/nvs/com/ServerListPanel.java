package serverlist.landscapeexplorer.nvs.com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import landscapeexplorer.nvs.com.globalData;
import obj.landscapeexplorer.nvs.com.GraphPointer;
import servertable.landscape.nvs.com.ServerListTable;

public class ServerListPanel extends JPanel {

	public globalData gData;
	public ServerListMenu menu;
	public ServerListInputText txtInput;
	public ServerListListener listener;

	public ServerListTable serverListTable = null;
	public JScrollPane scrollPanel = null;

	public List<GraphPointer> pixels = new ArrayList<>();
	public GraphPointer selectedPixel = null;
	public Rectangle selectedRect = null;

	public ServerListPanel(globalData gData) {
		this.gData = gData;

//		this.ctrl = new CommonViewController(gData, this);
//		
		this.listener = new ServerListListener(gData, this);
		this.addMouseListener(this.listener);

		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this.listener);

		this.setComponentPopupMenu(this.menu);
		this.menu = new ServerListMenu(this);

		this.setBackground(Color.white);

//		this.gData.mainFrame.targetDropTarget = new DropTarget(this, DnDConstants.ACTION_MOVE, this);

		txtInput = new ServerListInputText(this);
		txtInput.setVisible(false);
		this.add(txtInput);

		this.setBackground(Color.white);

		this.serverListTable = new ServerListTable(gData);
		serverListTable.setUpTableData();

		scrollPanel = new JScrollPane(this.serverListTable);
		
//		sp.setPreferredSize(new Dimension(this.getWidth(), 600));

		this.add(scrollPanel);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintCompositionParts(g);

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		Date now = new Date();
//		String strDate = sdf.format(now);
//		g.drawString(strDate, 10, 400);

	}

	public void paintCompositionParts(Graphics g) {

		scrollPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));

		
	}

	public GraphPointer getSelectedPixel(Point p) {

		GraphPointer out = null;

		for (GraphPointer gp : this.pixels) {

			if (gp.rec.contains(p)) {

				out = gp;

			}

		}

		return out;

	}
//  protected void loadServerList(){
//	  
//	  String[][] rec = {
//		         { "1", "Steve", "AUS" },
//		         { "2", "Virat", "IND" },
//		         { "3", "Kane", "NZ" },
//		         { "4", "David", "AUS" },
//		         { "5", "Ben", "ENG" },
//		         { "6", "Eion", "ENG" },
//		      };     
//	  
//	  String[] header = { "Rank", "Player", "Country" };
//	  
//	  this.serverListTable = new JTable(rec, header);
//	  this.add(new JScrollPane(this.serverListTable));
//
//  }

//  public void setUpTableData() {
//	    DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
//	    ArrayList<String> headerList = new ArrayList<String>();
//	    if (!con.equals(""))
//	        list = sql.getContactListsByGroup(con);
//	    else
//	        list = sql.getContactLists();
//	    for (int i = 0; i < list.size(); i++) {
//	        String[] data = new String[7];
//
//	            data[0] = list.get(i).getName();
//	            data[1] = list.get(i).getEmail();
//	            data[2] = list.get(i).getPhone1();
//	            data[3] = list.get(i).getPhone2();
//	            data[4] = list.get(i).getGroup();
//	            data[5] = list.get(i).getId();
//
//	        tableModel.addRow(data);
//	    }
//	    jTable.setModel(tableModel);
//	}

//  protected void setUpTableData1() {
//	  this.serverListTable = new JTable();
//	  DefaultTableModel tableModel = (DefaultTableModel) this.serverListTable.getModel();
//	  tableModel.setRowCount(0);
//	  String[] columns = new String[2];
//	  columns[0] = "AA";
//	  columns[1] = "BB";
//	  tableModel.setColumnIdentifiers(columns);
//	  
//	  Object[] data1= new Object[2];
//	  data1[0] = "Евгений";
//	  data1[1] = "Невтис";
//			  
//	 tableModel.addRow(data1);
//	  
//	  
//	  
//	  
//	  this.serverListTable.setModel(tableModel);
//	  
//		JScrollPane sp = new JScrollPane(this.serverListTable);
//		sp.setPreferredSize(new Dimension(gData.mainFrame.getWidth(),gData.mainFrame.getHeight()));
//
//		this.add(sp);
//  }

}
