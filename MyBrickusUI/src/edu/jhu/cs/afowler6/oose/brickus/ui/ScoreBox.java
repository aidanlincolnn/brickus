/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This Class is a Jpanel that holds Jlabels that represent
 * the scores of player 1 and player 2 and updates them on a 
 * Brickus model change event
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class ScoreBox extends JPanel{
	
	private static final long serialVersionUID = 1L;
	BrickusModel model;
	JLabel score2 = new JLabel("0");
	JLabel score1 = new JLabel("0");
	JLabel breaker = new JLabel(" : ");
	
	/**
	 * Creates the score box and implements BrickusEvent model changed
	 * @param model the brickus model
	 */
	public ScoreBox(BrickusModel model){
		this.model = model;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		score2.setForeground(Color.BLUE);
		score1.setForeground(Color.RED);
		this.add(score1);
		this.add(breaker);
		this.add(score2);
		
		//add a brickus listener that will update the score when the model changes 
		this.model.addBrickusListener(new BrickusListener()
	    {
	    	public void modelChanged(BrickusEvent event)
	    	{
	    		score2.setText("Score:"+String.valueOf(ScoreBox.this.model.calculateScore(Player.PLAYER2)));
	    		score1.setText("Score:"+String.valueOf(ScoreBox.this.model.calculateScore(Player.PLAYER1)));
	    		ScoreBox.this.repaint();  
	    	}

			public void illegalMove(BrickusIllegalMoveEvent event) {
			}
	    	
	    }
		);

	}
}
