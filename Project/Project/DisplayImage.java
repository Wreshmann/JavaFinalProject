package Project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DisplayImage extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private BufferedImage displayImage;
	private BufferedImage defaultImage;
	private int width;
	private int height;

	
	DisplayImage(){
		File f = new File("background.png"); 
		try {
			defaultImage = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics gp) {
		Graphics2D g = (Graphics2D) gp;
		if(displayImage != null) {
			g.drawImage(displayImage,null,0,0);
			width = displayImage.getWidth();
			height = displayImage.getHeight();
		}
		
	}
	
	
	public BufferedImage getImage() {
		return image; 
	}
	
	
	public void showImage(BufferedImage img, Dimension d, Graphics g) {
		image = img;
		displayImage = adjustImage(img,d);
		paint(g);
	}
	

	private BufferedImage adjustImage(BufferedImage img, Dimension d) {
		int w = d.width;
		int h = d.height;
		BufferedImage bi = aspectRatio(img,(float)w,(float)h); //produce resized image
		
		//point to place image evenly in field
		int x = (w - bi.getWidth())/2;
		int y = (h - bi.getHeight())/2;
		
		//draw black background, then draw image on it
		BufferedImage tempImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) tempImage.createGraphics();
			g2d.setColor(Color.darkGray);
			g2d.fillRect(0,0, w, h);
			g2d.drawImage(bi, null, x, y);
			g2d.dispose();
		return tempImage;
	}

	
	private BufferedImage aspectRatio(BufferedImage img, float w, float h) {
		int width;
		int height;
		int drawColor;
		BufferedImage tempImg;
		
		//determines max dimension to make new aspect ratio image
		float ratio = ((float)(img.getWidth()))/((float)(img.getHeight()));
		if(ratio >= 1.0) {
			height = (int) h;
			width = (int)(ratio*h);
			
			if(width > w) {
				width = (int) w;
				height = (int)(w/ratio);
			}
			
		}else {
			width = (int) w;
			height = (int)(w/ratio);
			
			if(height > h) {
				height = (int) h;
				width = (int)(ratio*h);
			}
			
		}
		
		//determines right color values for new image
		float xr = ((float)img.getWidth())/((float)width);
		float yr = ((float)img.getHeight())/((float)height);
		tempImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);	
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				
				drawColor = img.getRGB((int)(x*xr), (int)(y*yr));
				tempImg.setRGB(x, y, drawColor);
			}
		}
		
		return tempImg;
	}
	
	
	public Ellipse[] drawBackground(Graphics g, Dimension d, Ellipse[] c) {
		Object[] returnValues = adjustBackground(defaultImage,d,c);
		displayImage = (BufferedImage) returnValues[0];
		paint(g);
		
		return (Ellipse[]) returnValues[1];
	}

	
	private Object[] adjustBackground(BufferedImage img, Dimension d, Ellipse[] circles) {
		int w = d.width;
		int h = d.height;
		BufferedImage tempImg;
		
		float ratio = ((float)(img.getWidth()))/((float)(img.getHeight()));
		if(ratio >= 1.0) {
			height = (int) h;
			width = (int)(ratio*h);
			
			if(width > w) {
				width = (int) w;
				height = (int)(w/ratio);
			}
			
		}else {
			width = (int) w;
			height = (int)(w/ratio);
			
			if(height > h) {
				height = (int) h;
				width = (int)(ratio*h);
			}
			
		}
		
		
		int drawColor;
		float xr = ((float)img.getWidth())/((float)width);
		float yr = ((float)img.getHeight())/((float)height);
		tempImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);	
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				
				drawColor = img.getRGB((int)(x*xr), (int)(y*yr));
				tempImg.setRGB(x, y, drawColor);
			}
		}
		int anchorX = (w - tempImg.getWidth())/2;
		int anchorY = (h-tempImg.getHeight())/2;
		
		BufferedImage backDrop = 
				new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) backDrop.createGraphics();
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, d.width, d.height);
		g.drawImage(tempImg, anchorX, anchorY, null);
		g.dispose();
		
		
		xr = ((float)width)/(float)img.getWidth();
		yr = ((float)height)/(float)img.getHeight();
		for(Ellipse c: circles) {
			double cx = c.x*xr;
			double cy = c.y*yr;
			double cw = (xr*(c.x+c.width))-cx;
			double ch = (yr*(c.y+c.height))-cy;
			
			cx += anchorX;
			cy += anchorY;
			
	
			c.setCoordinates(cx, cy, cw, ch);
		}
		
		return new Object[] {backDrop,circles};
	}

	
	public int getWidth() {
		return width;
	}
	
	
	public int getHeight() {
		return height;
	}

}
