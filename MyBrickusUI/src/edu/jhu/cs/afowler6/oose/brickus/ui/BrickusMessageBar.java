/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class is a jpanel that holds the error messages and score box 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

public class BrickusMessageBar extends JPanel
{
	
	private BrickusModel model;
	private static final long serialVersionUID = 1L;
	private ScoreBox score;
	private JLabel errorMessage;
	
	public BrickusMessageBar(BrickusModel model)
	{
		this.model = model;
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		this.setBorder(raisedetched);
		this.setLayout(new BorderLayout());
		
		errorMessage = new JLabel("");
		score = new ScoreBox(model);
		this.add(score,BorderLayout.EAST);
		this.add(errorMessage,BorderLayout.WEST);
		
		//add a brickus listener that will update the message bar when there is an illegal move 
		this.model.addBrickusListener(new BrickusListener()
	    {
	    	public void modelChanged(BrickusEvent event)
	    	{
	    	}
	    	
	    	public void illegalMove(BrickusIllegalMoveEvent illegalMove)
	    	{
	    		errorMessage.setText(illegalMove.getMessage()+"                      ");
	    		BrickusMessageBar.this.repaint();
	    	}
	    }
		);
	}
	/**
	 * set the error message (for when the game is over)
	 * @param message the message to set 
	 */
	public void setMessage(String message){
		errorMessage.setText(message+"                      ");
	}
}
