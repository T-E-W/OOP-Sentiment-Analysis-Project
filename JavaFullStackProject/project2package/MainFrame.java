package project2package;

/**
 * 
 * @author Tim Webb
 * 10/15/2021
 * CSCI 3381
 * 
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.GridLayout;

public class MainFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Sentiment Analysis");
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/project2package/StaticBB.png")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		MainPanel panel = new MainPanel();
		frame.setSize(600, 500);
		frame.getContentPane().add(panel);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				panel.doWrite();
			}
		});

	
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
}
