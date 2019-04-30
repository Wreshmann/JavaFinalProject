package Project;
import java.awt.Color;
import java.awt.geom.PathIterator;
public class Ellipse extends java.awt.geom.Ellipse2D.Double {
	static final long serialVersionUID = 1L;
	private Color color;
	public boolean fill = false;
	private String planet;
	
	public Ellipse(int x, int y, int w, int h, Color c){
		super(x, y, w, h);
		this.color = c;
	}
	
	public Ellipse(int x, int y, int w, int h, Color c, Boolean f){
		super(x, y, w, h);
		this.color = c;
		this.fill = f;
	}
	
	public Ellipse(int x, int y, int w, int h, Color c, String s){
		super(x, y, w, h);
		this.color = c;
		this.planet = s;
		
	}
	
	public String getPlanetName(){
		return planet;
	}
	
	public Ellipse(int x, int y, int w, int h){
		super(x, y, w, h);
	}
	
	public Ellipse(double x, double y, double w, double h, Color c, String s) {
		super(x, y, w, h);
		this.color = c;
		this.planet = s;
	}

	public Color getDrawColor() {
		return color;
	}
	public boolean isFill() {
		return fill;
	}
	
	public String toString() {
		return String.format("%s, %d, %d, %d, %d, %d%n", (fill ? "filledCircle" : "circle"), (int) x, (int) y,
			(int) width, (int) height, color.getRGB());
	}
	
	public void setCoordinates(double cx, double cy, double cw, double ch) {
		super.x = cx;
		super.y = cy;
		super.width = cw;
		super.height = ch;
	}
	
}
