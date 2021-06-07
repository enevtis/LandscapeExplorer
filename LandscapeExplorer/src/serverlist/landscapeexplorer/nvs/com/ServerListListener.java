package serverlist.landscapeexplorer.nvs.com;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import landscapeexplorer.nvs.com.globalData;
import obj.landscapeexplorer.nvs.com.GraphPointer;


public class ServerListListener extends MouseAdapter implements KeyListener {

	public globalData gData;
	public ServerListPanel parent = null;
	public ServerListListener listener;
	public ServerListMenu menu;

	public ServerListListener(globalData gData, ServerListPanel panel) {
		this.gData = gData;
		this.parent = panel;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

		
		if (evt.getClickCount() == 1) {

			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, parent);

			GraphPointer gp = getSelectedPixel(p);

			if (gp != null) {

				parent.selectedPixel = gp;
//				gData.setSelectedTakt(gp.details);

			} else {

				parent.selectedPixel = null;
				parent.txtInput.setVisible(false);

			}

		} else if (evt.getClickCount() == 2) {

			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, parent);

			GraphPointer gp = getSelectedPixel(p);

			if (gp != null) {

				this.parent.selectedPixel = gp;
				this.parent.selectedRect = gp.rec;

				String line = gData.parseVar(gp.details, "line");

				switch (line) {

				case "param":

					String paramName = gData.parseVar(gp.details, "param");
					parent.txtInput.typeOfText = paramName;
//					parent.txtInput.setText(parent.gData.currentComposition.commonParams.get(paramName));
					parent.txtInput.setBounds(gp.rec.x, gp.rec.y, 200, 40);
					parent.txtInput.setSize(gp.rec.width, gp.rec.height + 5);
					parent.txtInput.setVisible(true);

					break;

				case "part":

					String partName = gData.parseVar(gp.details, "songPart");
					parent.txtInput.typeOfText = "songPart";
					parent.txtInput.setText(partName);
					parent.txtInput.setBounds(gp.rec.x, gp.rec.y, 200, 40);
					parent.txtInput.setSize(gp.rec.width, gp.rec.height + 5);
					parent.txtInput.setVisible(true);

					break;

				default:

					break;

				}

//				gData.setSelectedTakt(gp.details);

			} else {

				this.parent.selectedPixel = null;
				this.parent.selectedRect = null;

				parent.txtInput.setVisible(false);

			}

		}



	}

	protected void showTextForParams() {

		GraphPointer gp = parent.selectedPixel;
		if (gp == null) {
			JOptionPane.showMessageDialog(parent, parent.gData.translate("Select beat at chords timeline"));
			return;
		}

	}

	public GraphPointer getSelectedPixel(Point p) {

		GraphPointer out = null;

		for (GraphPointer gp : parent.pixels) {

			if (gp.rec.contains(p)) {

				out = gp;

			}

		}

		return out;

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (e.isPopupTrigger()) {
			parent.menu.show(e.getComponent(), e.getX(), e.getY());
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();


	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();


	}

	@Override
	public void keyTyped(KeyEvent e) {


	}

}
