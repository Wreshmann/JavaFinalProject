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
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SolarSystem extends JFrame implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DisplayImage display;
	private JButton[] buttons;
	private int panelWidth;
	private Ellipse[] planetCors;;
	//private Queries searcher;
	
	
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
		setPreferredSize(new Dimension(500,250));
		setLayout(new BorderLayout());
		setUpFrame();
		setVisible(true);
		pack();
		
	}
	
	private void setUpFrame() {
		buttons = new JButton[5];
		panelWidth = 80;
		int spacing = 10;
		Container c = getContentPane();
		Color panelColor = new Color(100,145,202);
		Color buttonColor = new Color(30,150,67);

		
		//center panel/canvas area
		JPanel pane = new JPanel();
			pane.setBackground(Color.black);
			pane.setLayout(new BorderLayout());
			pane.add(display, BorderLayout.CENTER);
			display.addBuffer(panelWidth);
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
//				JLabel label = new JLabel(" second ");
//				button.add(label);
//				button.setActionCommand("second button");
//				label = new JLabel(" button");
//				button.add(label);
				button.setEnabled(false);
				buttons[1] = button;
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Save");
				button.addActionListener(this);
				buttons[2] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Next");
				button.addActionListener(this);
				buttons[3] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("Back");
				button.addActionListener(this);
				buttons[4] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
			
			pane.add(Box.createVerticalGlue());
			
			c.add(pane, BorderLayout.LINE_START);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		
		//add cases and methods 
		switch(command) {
		
		case "Help":
			showHelp();
			createClickableRegions();
			break;
		
		case "Menu":
			showBackground();
			break;
		
		case "Save":
			break;
			
		default:
			break;
		}
		
	}
	
	private void showHelp() {		
        JOptionPane.showMessageDialog(this, "Click a planet to see a NASA image of it!", null, -1);
	}

	
	private void showBackground() {
		display.drawBackground(display.getGraphics());
		buttonsOn(false);
		createClickableRegions();
		
		setPreferredSize(display.getSize());
		setResizable(false);
		pack();
		
	}
	
	
	private void enableClickableRegions(boolean b) {
		
	}

	private void buttonsOn(boolean b) {
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
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if(!buttons[1].isEnabled()) {
			for(Ellipse e:planetCors) {
				if(e.contains(me.getPoint())) {
					//searcher.searchPlanet(e.getPlanetName());
					buttonsOn(true);
				}
			}
		}
		
	}

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
}
