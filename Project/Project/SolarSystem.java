package Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SolarSystem extends JFrame implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DisplayImage display;
	private JButton[] buttons;
	private int panelWidth;
	private int panelHeight;
	private Ellipse[] planetCors;
	private JPanel bottomPane;
	private JLabel[] planetLabels;
	private JTextArea text;
	
	
	public static void main(String[] args) {
		SolarSystem frame = new SolarSystem();
		frame.showBackground();

	}
	
	SolarSystem(){
		display = new DisplayImage();
		display.setVisible(true);
		display.addMouseListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		setLayout(new BorderLayout());
		setUpFrame();
		setVisible(true);
		configureSize();
		
	}
	
	
	private void setUpFrame() {
		buttons = new JButton[5];
		panelWidth = 80;
		panelHeight = 60;
		int spacing = 10;
		Container c = getContentPane();
		Color panelColor = new Color(100,145,202);
		Color buttonColor = Color.white;

		
		//center panel/canvas area
		JPanel pane = new JPanel();
			pane.setBackground(Color.black);
			pane.setLayout(new BorderLayout());
			pane.add(display, BorderLayout.CENTER);
			c.add(pane, BorderLayout.CENTER);
		
		
		//left panel
		pane = new JPanel();
			pane.setBackground(panelColor);
			pane.setPreferredSize(new Dimension(panelWidth,0));
			pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
			pane.add(Box.createVerticalStrut(25));
			pane.add(Box.createVerticalGlue());
			
			
			JButton button = new JButton("Help");
				button.setBackground(buttonColor);
				button.setOpaque(false);
				button.addActionListener(this);
				buttons[0] = button;
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
			

			button = new JButton("Menu");
				button.setLayout(new BoxLayout(button, BoxLayout.Y_AXIS));
				button.setBackground(buttonColor);
				button.addActionListener(this);
				button.setEnabled(false);
				buttons[1] = button;
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Save");
				button.addActionListener(this);
				button.setBackground(buttonColor);
				buttons[2] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Next");
				button.addActionListener(this);
				button.setBackground(buttonColor);
				buttons[3] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Previous");
				button.addActionListener(this);
				button.setBackground(buttonColor);
				buttons[4] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
			
			pane.add(Box.createVerticalGlue());
			c.add(pane, BorderLayout.LINE_START);
			
			
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
			
			bottomPane.add(Box.createHorizontalStrut((int)panelWidth/3), BorderLayout.LINE_START);
			bottomPane.add(text, BorderLayout.CENTER);
			c.add(bottomPane, BorderLayout.PAGE_END);
			//System.out.println(text);
		
		
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
			setTitle(null);
			break;
		
		case "Save":
			save();
			break;
			
		case "Next":
			next();
			configureSize();
			break;
			
		case "Previous":
			previous();
			configureSize();
			
		default:
			break;
		}
		
	}
	
	private void previous() {
		Object[] d = Queries.previousImage();
		display.setImage((BufferedImage) d[0]);
		display.showImage(display.getGraphics());
		showImageDescription((String) d[1]);
		
	}

	private void showImageDescription(String s) {
		bottomPane.setVisible(true);
		text.setText(s);
	}

	private void next() {
		Object[] d = Queries.nextImage();
		display.setImage((BufferedImage) d[0]);
		display.showImage(display.getGraphics());
		showImageDescription((String) d[1]);
	}

	private void showHelp() {	
		configureSize();
		JPanel pane = new JPanel();
		JLabel message = new JLabel("Click a planet to see a NASA image associated with it!");
		pane.setLayout(new BorderLayout());
		pane.add(message, BorderLayout.CENTER);
		
		
        JOptionPane.showMessageDialog(this, pane, null, -1);
	}

	
	private void showBackground() {
		display.drawBackground(display.getGraphics());
		buttonsOn(false);
		if(planetCors == null) createClickableRegions();
		
		bottomPane.setVisible(false);
		configureSize();
		setResizable(false);
		
	}

	private void buttonsOn(boolean b) {
		buttons[0].setEnabled(!b);
		for(int k = 1; k < buttons.length; k++) {
			buttons[k].setEnabled(b);
		}
	}
	
	private void createClickableRegions() {
		planetCors = new Ellipse[8];
		planetCors[0] = new Ellipse(16, 212, 38, 37, Color.black,"Mercury");
		planetCors[1] = new Ellipse(68, 199, 71, 71, Color.black, "Venus");
		planetCors[2] = new Ellipse(153, 192, 85, 85, Color.black, "Earth");
		planetCors[3] = new Ellipse(252, 205, 60, 60, Color.black, "Mars");
		planetCors[4] = new Ellipse(326, 159, 151, 151, Color.black, "Jupiter");
		planetCors[5] = new Ellipse(522, 174, 112, 113, Color.black, "Saturn");
		planetCors[6] = new Ellipse(685, 173, 124, 124, Color.black, "Uranus");
		planetCors[7] = new Ellipse(823, 171, 127, 127, Color.black, "Neptune");
		
		//Rectangle2D rect = new Rectangle2D();
		planetLabels = new JLabel[8];
		for(int k = 0; k < planetCors.length; k++) {
			planetLabels[k] = new JLabel(planetCors[k].getPlanetName());
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		//if background image is showing, then allow planet area to be clicked
		if(!buttons[1].isEnabled()) {
			for(Ellipse e:planetCors) {
				if(e.contains(me.getPoint())) {
					Queries.searchPlanet(e.getPlanetName());
					buttonsOn(true);
					display.setImage(Queries.getRandomImage());
					display.showImage(display.getGraphics());
					showImageDescription(Queries.getImageInfo());
					
					configureSize();
					setTitle(e.getPlanetName());
				}
			}
		}
		
	}

	private void configureSize() {
		int width = 0;
		width += display.getWidth(); //image width
		width += panelWidth; //menu width
		
		int height = 0;
		height += 22; //top bar of JFrame
		height += display.getHeight();
		if(bottomPane.isVisible()) {
			height += panelHeight;
		}
		
		setResizable(true);
		setPreferredSize(new Dimension(width,height));
		pack();
		
		
		//-------TESTING----------------
		
		//display.setImage(aspectRatio(display.getImage()));
	}

	
	private BufferedImage aspectRatio(BufferedImage image) {
		// TODO Auto-generated method stub
		return null;
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
	public void mousePressed(MouseEvent e) {
		//System.out.println("mouse pressed: "+e.);
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
}
