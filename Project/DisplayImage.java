package Project;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class DisplayImage extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private BufferedImage imgResized;
	private BufferedImage defaultImage;
	private boolean drawBackground;
	private Dimension size;
	private int buffer;
	private int w;
	private int h;

	
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
			w = defaultImage.getWidth();
			h = defaultImage.getHeight();
		}
		else {
			if(img != null) {
				g.drawImage(img,null,0,0);
				w = img.getWidth();
				h = img.getHeight();
			}
		}
	}
	
	
	public BufferedImage getImage() {
		return img; 
	}
	
	
	public void drawBackground(Graphics g) {
		drawBackground = true;
		paint(g);
	}
	
	
	public void showImage(Graphics g) {
		drawBackground = false;
		paint(g);
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
//	private aspectRatio() {
//		
//	}
}
