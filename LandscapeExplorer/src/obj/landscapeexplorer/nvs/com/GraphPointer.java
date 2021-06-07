package obj.landscapeexplorer.nvs.com;

import java.awt.Rectangle;

public class GraphPointer {
	public String type = "";
	public String name  = "";
	public Rectangle rec;
	public Object obj;
	public String details = "";
	public boolean isSelected = false;
	
	public GraphPointer (Rectangle r, String details) {
		this.rec = r;
		this.details = details;
		
	}
	public GraphPointer (Rectangle r, Object obj) {
		this.rec = r;
		this.obj = obj;
		
	}	
}
