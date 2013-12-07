package se.kehe.logging;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import se.kehe.logging.SwingAppender;


public class TestLog {
	
	static Logger logger = Logger.getLogger(TestLog.class);
	JFrame frame = null;
	JPanel panel = null;
	JTextField textArea = null;
	JButton button = null;
	SwingAppender logAppender = null;
	JTextPane textPane = null;
	
	
	public TestLog() {
		init();
	}

	void init () {
				
		frame = new JFrame();
		panel = new JPanel();
		button = new JButton();
		textPane = new JTextPane();
		
		Properties logProperties = new Properties();
		try {
			logProperties.load(getClass().getResourceAsStream("logging.properties"));
		} catch (FileNotFoundException e1) {
			 throw new RuntimeException("Unable to load logging property " + e1);
		} catch (IOException e1) {
			 throw new RuntimeException("Unable to load logging property " + e1);
		}
		
		PropertyConfigurator.configure(logProperties);
		logAppender = new SwingAppender();
		logAppender.setTextArea(textPane);
					
		textPane.setPreferredSize(new Dimension(470, 120));
		button.setText("Log something!");
		button.setPreferredSize(new Dimension(130, 25));
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Write something to log
				logger.fatal("fatal message");
		  }
		});
		
		panel.add(textPane, BorderLayout.NORTH);
		panel.add(button, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setPreferredSize(new Dimension(500, 200));
		frame.setTitle("Swing component appender");
		frame.add(panel);
				
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TestLog();
			}
		});
	}

}
