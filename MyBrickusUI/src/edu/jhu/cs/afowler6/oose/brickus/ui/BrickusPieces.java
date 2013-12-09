/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class is a jpanel that displays all of the current players brickus pieces 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class BrickusPieces extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static int PIECES_WIDTH = 7;
	private static int PIECES_HEIGHT = 3;
	private BrickusModel model;
	private BrickusPieceModel pieceModel;
	private boolean gameOver = false;
	
	/**
	 * creates a new brickus pieces container
	 * @param model the brickus model
	 * @param pieceModel the piece model
	 */
	public BrickusPieces(BrickusModel model, BrickusPieceModel pieceModel)
	{
		this.model =  model;
		this.pieceModel = pieceModel;

		setLayout(new GridLayout(PIECES_HEIGHT,PIECES_WIDTH,(int) (this.getWidth()*.1),(int) (this.getHeight()*.1)));
		updatePieces();
		pieceModel.setSelectedPiece(null);
    
		//add a brickus listener to the model that updates the brickus pieces container
	    this.model.addBrickusListener(new BrickusListener()
	    {
	    	public void modelChanged(BrickusEvent event)
	    	{
	    		if (event.isGameOver()){
	    			updateGameOver();
	    			gameOver = true;
	    		}
	    		updatePieces();
	    		BrickusPieces.this.pieceModel.setSelectedPiece(null);
	    		BrickusPieces.this.repaint();  
	    	}
	    	
	    	public void illegalMove(BrickusIllegalMoveEvent illegalMove)
	    	{
	    		BrickusPieces.this.repaint();  
	    	}
	    }
	    );
	    
	    //add a piece model listener that updates the brickus piece container when a selected piece changes
	    this.pieceModel.addListener(new BrickusPieceModelListener()
	    {
	    	public void pieceChanged()
	    	{ 	
	    		BrickusPieces.this.repaint();
	    	}
	    }
	    );
	    
	    //add mouse listeners 
	    BrickusPiecesMouseListener mouseListener = new BrickusPiecesMouseListener(this.model, this.pieceModel);
	    this.addMouseListener(mouseListener);
	    this.addMouseWheelListener(mouseListener);
	    this.addMouseMotionListener(mouseListener);
    
	}
	
	/**
	 * removes all the pieces from the pieces container when the game is over 
	 */
	private void updateGameOver()
	{
		removeAll();
	}
	
	/**
	 * updates the piece container with all of a players pieces when the actuve player changes 
	 */
	private void updatePieces()
	{		
		removeAll();
		Player activePlayer = model.getActivePlayer();
		if (activePlayer != null)
		{
			for (BrickusPiece piece: model.getPieces(activePlayer))
			{
				IndividualBrickusPiece addPiece = new IndividualBrickusPiece(piece,BrickusColors.getColor(activePlayer,true),pieceModel,this);
				this.add(addPiece);
			}
			this.revalidate();
		}
	}
	
	/**
	 * @return true if the game is over false if it is not 
	 */
	public boolean isGameOver(){
		return gameOver;
	}
}