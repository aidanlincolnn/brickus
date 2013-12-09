/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class handels the mouse moving and clicking on the brickus board 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

public class BrickusBoardMouseListener extends BrickusMouseListener
{
	private Point lastMouse;
	private boolean onBoard = false; 
	private BrickusBoard board;
	
	public BrickusBoardMouseListener(BrickusModel model, BrickusPieceModel pieceSelectionModel,BrickusBoard board)
	{
		super(model, pieceSelectionModel); 
		this.board = board;
	} 
	
	/**
	 *set onBoard to false if the mouse exits the board 
	 */
	public void mouseExited(MouseEvent mouseEvent) 
	{ 
		onBoard = false;
	}
	
	/**
	 * if the mouse moves set the point where it is and update the board listeners 
	 */
	public void mouseMoved(MouseEvent mouseEvent) 
	{ 
		onBoard = true;
		lastMouse = mouseEvent.getPoint();
		for (BrickusPieceModelListener listener: getPieceModel().getListeners())
		{
			listener.pieceChanged();
		}
	}
	
	/**
	 * if the mouse clicks on the board set the point, call place piece in the model and update the board listeners 
	 */
	public void mouseClicked(java.awt.event.MouseEvent mouseEvent) 
	{
		super.mouseClicked(mouseEvent);
		onBoard = true;
		
		if (SwingUtilities.isLeftMouseButton(mouseEvent) && getPieceModel().getSelectedPiece() != null)
		{
			lastMouse = mouseEvent.getPoint();
			int x = (int) Math.floor(mouseEvent.getX()/(board.getWidth()/14));
			int y = (int) Math.floor(mouseEvent.getY()/(board.getHeight()/14));
			getBrickusModel().placePiece(getBrickusModel().getActivePlayer(), x, y, getPieceModel().getSelectedPiece());
		}
		for (BrickusPieceModelListener listener: getPieceModel().getListeners())
		{
			listener.pieceChanged();
		}
	}
	
	/**
	 * @return the most recent mouse position on the board 
	 */
	public Point getMouse()
	{
		return lastMouse;
	}
	
	/**
	 * @return true if the mouse is currently on the board false otherwise 
	 */
	public boolean onBoard()
	{
		return onBoard; 
	}
}

