/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class represents an individual brickus piece to be displayed
 * in the brickus piece selection component
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class IndividualBrickusPiece extends JComponent
{
	private static final long serialVersionUID = 1L;
	private static final int DISPLAY_GRID_SIZE = 5;
	private BrickusPiece piece;
	private Color color;
	private BrickusPieceModel pieceModel;
	private BrickusPieces pieces;

	/**
	 * Creates a new individualBrickus piece to be painted on the brickus piece container
	 * @param piece the brickus piece to paint
	 * @param color the color of the piece
	 * @param pieceModel the piece model
	 * @param pieces the brickus pieces continer
	 */
	public IndividualBrickusPiece(BrickusPiece piece, Color color, BrickusPieceModel pieceModel,BrickusPieces pieces)
	{
		this.piece = piece;
		this.color = color;
		this.pieces = pieces;
		this.pieceModel = pieceModel;
		pieceModel.setSelectedPiece(piece);
		this.setOpaque(true);
		
	}
	
	/**
	 * paints the background of the individual piece if it is the current 
	 * selected piece, creates a visual representation of each other piece the player has left
	 * @param g the graphics object used to paint
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		int sideLength = pieces.getWidth()/7;
		int squareSide = (int) (sideLength/(DISPLAY_GRID_SIZE+1));
		Graphics2D g2 = (Graphics2D) g;
		
		//first paint the background of the selected piece 
		if (getPiece() == pieceModel.getSelectedPiece()){
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		//then paint the individual pieces on the brickuspieces jpanel
	  	for (int i=0;i<DISPLAY_GRID_SIZE;i++)
	  	{
	  		for (int j=0;j<DISPLAY_GRID_SIZE;j++)
	  		{
	  			if (piece.isOccupied(i, j))
	  			{
	  				Rectangle rect = new Rectangle(new Point((int)(i*squareSide),(int)(j*squareSide)),new Dimension(squareSide,squareSide));
	  				g2.setColor(color);
	  				g2.fill(rect);
	  				g2.setColor(Color.BLACK);
	  				g2.draw(rect);
	  			}	
	  		}
	  	}	
	}

	/**
	 * @return piece the brickus piece that this individual brickus piece is based on
	 */
	public BrickusPiece getPiece()
	{
		return piece;
	}	
	
}