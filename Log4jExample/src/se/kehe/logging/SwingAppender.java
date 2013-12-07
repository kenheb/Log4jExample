package se.kehe.logging;

import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.text.StyleConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;


public class SwingAppender extends WriterAppender {
	
	static Logger logger = Logger.getLogger(SwingAppender.class);
	private static javax.swing.JTextPane logTextPane = null;
	
	
	/** Set the target JTextPane for the logging information to appear. */
	public void setTextArea(javax.swing.JTextPane logTextPane) {
		SwingAppender.logTextPane = logTextPane;
	}

	
	public void append(LoggingEvent loggingEvent) {
		
		final String message = this.layout.format(loggingEvent);
		final LoggingEvent currentEvent = loggingEvent;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				StyledDocument styledDocMainLowerText = (StyledDocument)logTextPane.getDocument();
				Style style = styledDocMainLowerText.addStyle("StyledDocument", null);
				
				// Font family
			    StyleConstants.setFontFamily(style, "Cursive");
			    // Font size
			    StyleConstants.setFontSize(style, 12);
			    // Foreground color
			    
			    if (currentEvent.getLevel().toString() == "FATAL") {
				    StyleConstants.setForeground(style, Color.red);
			    } else {
			    	StyleConstants.setForeground(style, Color.blue);
			    }
		
				try {
					styledDocMainLowerText.insertString(styledDocMainLowerText.getLength(), message, style);
				} catch (BadLocationException e) {
					logger.fatal(e);
				}

			}
		});
	}

}
