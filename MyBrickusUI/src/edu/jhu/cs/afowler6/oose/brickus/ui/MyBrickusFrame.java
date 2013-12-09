/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class is a jframe that controls all of the components on the jpanel
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MyBrickusFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private BrickusModel model;
	private BrickusPieceModel pieceModel;
	private BrickusBoard board;
	private BrickusPieces pieces;
	private JButton passButton;
	private BrickusMessageBar messages;
	
	/**
	 * Creates the board, piece section, pass button and bottom message label
	 * @param model the brickus model
	 */
	public MyBrickusFrame(BrickusModel model)
	{
		this.model = model;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(635, 735);
		this.setLayout(new BorderLayout());

		int frameWidth = this.getWidth();
		int frameHeight = this.getHeight();
		 
		pieceModel = new BrickusPieceModel(null);
		board = new BrickusBoard(model,pieceModel);
		pieces = new BrickusPieces(model,pieceModel);
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener()); 
		messages = new BrickusMessageBar(model);
		
		this.getContentPane().setSize(frameWidth, frameHeight);
		this.getContentPane().add(BorderLayout.PAGE_START,board);
		this.getContentPane().add(BorderLayout.LINE_START,pieces);
		this.getContentPane().add(BorderLayout.LINE_END,passButton);
		this.getContentPane().add(BorderLayout.PAGE_END,messages);
		this.paint(this.getGraphics()); 
	 }
	
	 /**
	  * sets the sizes of the components on a window resize
	  * @param Graphics g the graphics object to paint with
	  */
	 public void paint(Graphics g){
		 super.paint(g);
		 int frameWidth = this.getWidth();
		 int frameHeight = this.getHeight();
		 board.setPreferredSize(new Dimension(frameWidth,(int)(.6*frameHeight)));
		 pieces.setPreferredSize(new Dimension((int)(.85*frameWidth),(int) (.45*frameHeight)));
		 passButton.setPreferredSize(new Dimension((int)(.15*frameWidth),(int)(.45*frameHeight)));
		 messages.setPreferredSize(new Dimension(frameWidth,(int)(.05*frameHeight)));
	 }
	 
	 /**
	  * Action listener for the pass button,
	  * calls pass on the model, if the game is over
	  * the message bar gets updated and the pieces and 
	  * button components get removed from the frame
	  */
	 class PassButtonListener implements ActionListener {
		 /**
		  * This updates the frame on a pass button press
		  */
		 public void actionPerformed(ActionEvent event) 
		 {
			 model.pass(model.getActivePlayer());
		
			 if (pieces.isGameOver())
			 {
				 String winner = "";
				 
				 if (model.calculateScore(Player.PLAYER1) == model.calculateScore(Player.PLAYER2)){
					 winner = "The Game Is A Tie";
				 }
				 
				 else if (model.calculateScore(Player.PLAYER1) > model.calculateScore(Player.PLAYER2)){
					 winner = "Winner is Player 1";
				 }
				 else{
					 winner = "Winner is Player 2";
				 }
				 
				 MyBrickusFrame.this.messages.setMessage("Game Over, "+winner);
				 MyBrickusFrame.this.getContentPane().remove(pieces);
			     MyBrickusFrame.this.getContentPane().remove(passButton);
			     board.setPreferredSize(new Dimension((int)(MyBrickusFrame.this.getWidth()*.9),(int)( MyBrickusFrame.this.getHeight()*.9)));
				 MyBrickusFrame.this.repaint();
			 }
	     }
	 }
}
