package Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SolarSystem extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DisplayImage display;
	private JButton[] buttons = new JButton[5];
	
	
	SolarSystem(){
		display = new DisplayImage();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		setPreferredSize(new Dimension(500,250));
		//setPreferredSize(display.getSize());
		setLayout(new BorderLayout());
		setUpFrame();
		setVisible(true);
		pack();
		
	}
	
	private void setUpFrame() {
		int panelWidth = 80;
		int spacing = 10;
		Container c = getContentPane();
		Color panelColor = new Color(100,145,202);
		Color buttonColor = new Color(30,150,67);

		
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
			pane.add(Box.createVerticalStrut(15));
			pane.add(Box.createVerticalGlue());
			
			
			JButton button = new JButton("Help");
				button.setBackground(buttonColor);
				button.setOpaque(false);
				button.addActionListener(this);
				buttons[0] = button;
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
			

			button = new JButton("Data");
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
				
			
			button = new JButton("2");
				button.addActionListener(this);
				buttons[2] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("3");
				button.addActionListener(this);
				buttons[3] = button;
				button.setEnabled(false);
				pane.add(button);
				pane.add(Box.createVerticalStrut(spacing));
				
			
			button = new JButton("4");
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
			break;
		
		case "":
			break;
		
		default:
			break;
		}
		
	}
	
	public static void main(String[] args) {
		SolarSystem frame = new SolarSystem();
		frame.showBackground();

	}

	private void showBackground() {
		display.paint(display.getGraphics(), 0);
		
	}
}
