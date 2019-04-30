package Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;


public class SolarSystem extends JFrame implements ActionListener, MouseListener, ComponentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DisplayImage display;
	private JButton[] buttons;
	private int panelWidth;
	private int panelHeight;
	private JPanel bottomPane;
	private JTextArea text;
	private JPanel window;
	private Object[] title;
	private Ellipse[] planets;
	
	
	
	public static void main(String[] args) {
		SolarSystem frame = new SolarSystem();
		frame.bottomPane.setVisible(false);
		frame.showBackground();
		
	}
	
	
	SolarSystem(){
		display = new DisplayImage();
		display.addMouseListener(this);
		title = new Object[3];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.darkGray);
		setLayout(new BorderLayout());
		setUpFrame();
		setPreferredSize(new Dimension(1048,492)); //actual size of background image = 968x470
		pack();
		setVisible(true);
		setMinimumSize(new Dimension(522,400));
	}
	
	
	private void setUpFrame() {
		buttons = new JButton[6];
		panelWidth = 80;
		panelHeight = 60;
		int spacing = 10;
		int buttonHeight = 25;
		int buttonWidth = panelWidth-1;
		Color panelColor = new Color(100,145,202);
		Color buttonColor = Color.white;
		
		window = new JPanel();
		window.setBackground(Color.darkGray);
		window.setLayout(new BorderLayout());
		window.addComponentListener(this); //send signal to adjust image size
		
		//center panel/canvas area
		JPanel pane = new JPanel();
		pane.setBackground(Color.darkGray);
		pane.setLayout(new BorderLayout());
		pane.add(display, BorderLayout.CENTER);
		window.add(display, BorderLayout.CENTER);
		
		
		//left panel
		pane = new JPanel();
			pane.setBackground(panelColor);
			pane.setPreferredSize(new Dimension(panelWidth,0));
			pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
			pane.add(Box.createVerticalStrut(25));
			pane.add(Box.createVerticalGlue());
			
			
			JButton button = new JButton("Help");
				
				button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
				button.setBackground(buttonColor);
				button.setOpaque(false);
				button.addActionListener(this);
				buttons[0] = button;
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
			

			button = new JButton("Menu");
				button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
				button.setBackground(buttonColor);
				button.addActionListener(this);
				button.setEnabled(false);
				buttons[1] = button;
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Save");
				button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
				button.addActionListener(this);
				button.setBackground(buttonColor);
				buttons[2] = button;
				button.setEnabled(false);
				//button.setVisible(false); //TESTING
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Next");
				button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
				button.addActionListener(this);
				button.setBackground(buttonColor);
				buttons[3] = button;
				button.setEnabled(false);
				//button.setVisible(false); //TESTING
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Previous");
				button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
				button.addActionListener(this);
				button.setBackground(buttonColor);
				buttons[4] = button;
				button.setEnabled(false);
				//button.setVisible(false); //TESTING
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			button = new JButton("Original");
				button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
				button.addActionListener(this);
				button.setBackground(buttonColor);
				buttons[5] = button;
				button.setEnabled(false);
				//button.setVisible(false); //TESTING
				
				
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
			
		pane.add(Box.createVerticalGlue());
		window.add(pane, BorderLayout.LINE_START);
			
			
		//bottom panel
		bottomPane = new JPanel();
			bottomPane.setBackground(Color.white);
			bottomPane.setPreferredSize(new Dimension(0,panelHeight));
			bottomPane.setLayout(new BorderLayout());
			
			text = new JTextArea();
				text.setEditable(false);
				text.setBackground(Color.white);
				text.addMouseListener(this);
				text.setBounds(0, 0, display.getWidth(), panelHeight);
				text.setLineWrap(true);
				text.setWrapStyleWord(true);
				
				Font font = new Font("Times Roman", Font.PLAIN, 16);
				text.setFont(font);
			
			bottomPane.add(Box.createHorizontalStrut((int)panelWidth/3), BorderLayout.LINE_START);
			bottomPane.add(text, BorderLayout.CENTER);
			
		window.add(bottomPane, BorderLayout.PAGE_END);
		getContentPane().add(window);
	}

	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		
		//add cases and methods 
		switch(command) {
		
		case "Help":
			showHelp();
			break;
		
		case "Menu":
			showBackground();
			break;
		
		case "Save":
			save();
			break;
			
		case "Next":
			changeImage(command);
			break;
			
		case "Previous":
			changeImage(command);
			break;
			
		case "Original":
			original();
			break;
			
		default:
			break;
		}
	}


	private void original() {
		BufferedImage bi = display.getImage();
		int w = bi.getWidth();
		int h = bi.getHeight();
		
		setPreferredSize(new Dimension(w+panelWidth,h+panelHeight+22));
		pack();
		
	}


	private void changeImage(String s) {
		if(s.equals("Next")) {
			showImage(Queries.nextImage());
			
		}
		else {
			showImage(Queries.previousImage());
		}
		
		showImageDescription(Queries.getImageInfo());
		title[3] = Queries.getCurrentIndex();
		setTitle(makeTitle());
	}

	
	
	private void showImageDescription(String s) {
			bottomPane.setVisible(true);
			text.setText(s);
	}
	
	
	private void showHelp() {	
		JPanel pane = new JPanel();
		JLabel message = new JLabel("Click a planet to see a NASA image associated with it!");
		pane.setLayout(new BorderLayout());
		pane.add(message, BorderLayout.CENTER);
		
        JOptionPane.showMessageDialog(this, pane, null, -1);
		
	}

	
	private void showBackground() {
		bottomPane.setVisible(false);
		buttonsOn(false);
		setTitle("Menu");
		createClickableRegions();
		
		display.drawBackground(
				display.getGraphics(), 
				configureSize(),
				planets);
	}

	
	private void buttonsOn(boolean b) {
		buttons[0].setEnabled(!b);
		for(int k = 1; k < buttons.length; k++) {
			buttons[k].setEnabled(b);
		}
	}
	
	
	private void createClickableRegions() {
		planets = new Ellipse[8];
		planets[0] = new Ellipse(16, 212, 38, 37, Color.black,"Mercury");
		planets[1] = new Ellipse(68, 199, 71, 71, Color.black, "Venus");
		planets[2] = new Ellipse(153, 192, 85, 85, Color.black, "Earth");
		planets[3] = new Ellipse(252, 205, 60, 60, Color.black, "Mars");
		planets[4] = new Ellipse(326, 159, 151, 151, Color.black, "Jupiter");
		planets[5] = new Ellipse(522, 174, 112, 113, Color.black, "Saturn");
		planets[6] = new Ellipse(685, 173, 124, 124, Color.black, "Uranus");
		planets[7] = new Ellipse(823, 171, 127, 127, Color.black, "Neptune");
		
		
		
//		//Rectangle2D rect = new Rectangle2D();
//		planets = new Ellipse[8];
//		int k = 0;
//		for(Ellipse p:planetsTemplate) {
//			planets[k] = 
//				new Ellipse(
//					p.x,
//					p.y,
//					p.width,
//					p.height,
//					p.getDrawColor(),
//					p.getPlanetName());
//			k++;
//		}
	}

	
	@Override
	public void mouseClicked(MouseEvent me) {
		//if background image is showing, then allow planet area to be clicked
		if(!buttons[1].isEnabled()) {
			for(Ellipse e:planets) {
				if(e.contains(me.getPoint())) {
					Queries.searchPlanet(e.getPlanetName());
					buttonsOn(true);
					
//					setResizable(true);
//					setPreferredSize(new Dimension(722,600));
//					pack();
					
					showImage(Queries.getRandomImage());
					showImageDescription(Queries.getImageInfo());
					
					title = new Object[] {
							e.getPlanetName(),
							" - ",
							"image ",
							Queries.getCurrentIndex(),
							" out of ",
							Queries.getImageTotal()};
					
					setTitle(makeTitle());
				}
			}
		}
	}
		
	
	private String makeTitle() {
		title[3] = (int)title[3] +1;
		String rs = "";
		for(Object k: title) { 
			rs += k;
		}
		return rs;
	}


	private void showImage(BufferedImage img) {
		display.showImage(img,
				configureSize(),
				display.getGraphics());
	}

	
	private Dimension configureSize() {
		//returns current JFrame size so that the 
		//display image is the required size
		int w = getSize().width;
		w -= panelWidth;
		
		int h = getSize().height;
		h -= 22;
		if(bottomPane.isVisible()) {
			h -= panelHeight;
		}
		
		return new Dimension(w,h);
	}
	
	
	private void save() {
		JFileChooser chooser = new JFileChooser();
		int option = chooser.showSaveDialog(this);
		if(option == chooser.APPROVE_OPTION) {
			String name = chooser.getSelectedFile().getName();
			File file = chooser.getSelectedFile();
			if(!name.contains(".jpg")) {
				String fileName = chooser.getSelectedFile().getPath()+".jpg";
				file = new File(fileName);
			}
			try {				
				ImageIO.write(display.getImage(), "jpg", file);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		if(buttons[1].isEnabled()) {
			showImage(display.getImage());
			
		}
		else {
			showBackground();
		}
		
	}
	
	
	//-----------------------------------------------------------------
	//----------------  NO FUNCTIONING CODE BELOW  --------------------
	//-----------------------------------------------------------------
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
