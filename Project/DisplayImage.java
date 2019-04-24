package Project;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DisplayImage extends Canvas {
	private BufferedImage img;
	private BufferedImage defaultImage;
	private int[] planetCors; //tbd.. may not actually use it
	private boolean drawBackground;
	private Dimension size;
	private int buffer;

	
	DisplayImage(){
		File f = new File("background.png"); 
		try {
			defaultImage = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setImage(BufferedImage i) {
		img = i;
	}
	
	public void paint(Graphics gp) {
		Graphics2D g = (Graphics2D) gp;
		if(drawBackground) {
			g.drawImage(defaultImage,null,0,0);
			size = new Dimension(
					defaultImage.getWidth()+buffer, defaultImage.getHeight());
		}
		else {
			if(img != null) {
				g.drawImage(img,null,0,0);
				size = new Dimension(img.getWidth()+buffer, img.getHeight());
			}
		}
	}
	
	
	public Object getImageInfo() {
		return null; //should return any relative info about it
	}
	
	
	public void drawBackground(Graphics g) {
		drawBackground = true;
		paint(g);
	}
	
	
	public void showImage(Graphics g) {
		drawBackground = false;
		paint(g);
	}
	
	
	public Dimension getSize() {
		return size;
		
	}
	
	public void addBuffer(int b) {
		buffer = b;
	}
	
}
