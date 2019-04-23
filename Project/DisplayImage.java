package Project;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DisplayImage extends Canvas {
	private BufferedImage img;
	private BufferedImage defaultImage;
	private final int DEFAULT = 0;
	private int[] planetCors; //tbd.. may not actually use it
	private int w;
	private int h;

	
	DisplayImage(){
		File f = new File("Solar_System.jpg"); //put default image file here
		try {
			defaultImage = ImageIO.read(f);
//			w = defaultImage.getWidth();
//			h = defaultImage.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setImage(BufferedImage i) {
		img = i;
//		w = i.getWidth();
//		h = i.getHeight();
	}
	
	public void paint(Graphics gp) {
		Graphics2D g = (Graphics2D) gp;
		g.drawImage(img,null,0,0);
	}
	
	public void paint(Graphics gp, int zero) {
		Graphics2D g = (Graphics2D) gp;
		if(zero == 0 && defaultImage != null) {
			g.drawImage(defaultImage,null,0,0);
//			w = defaultImage.getWidth();
//			h = defaultImage.getHeight();
		}
	}
	
	public Object getImageInfo() {
		return null; //should return any relative info about it
	}
	
	
}
