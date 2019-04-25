package Project;
import java.awt.Color;
import java.awt.geom.PathIterator;
public class Ellipse extends java.awt.geom.Ellipse2D.Double implements PathIterator{
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
	
	public Ellipse(int x, int y, int w, int h){
		super(x, y, w, h);
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

	@Override
	public int getWindingRule() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int currentSegment(float[] coords) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int currentSegment(double[] coords) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
