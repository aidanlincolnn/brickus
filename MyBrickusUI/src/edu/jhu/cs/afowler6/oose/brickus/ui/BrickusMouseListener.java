/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class handels mouse moving around and clicks in the brickus game 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
//class to handle the mouse moving around the brickus game 

public class BrickusMouseListener implements MouseListener, MouseInputListener, MouseMotionListener, MouseWheelListener{
	
	private BrickusModel model;
	private BrickusPieceModel pieceModel;
	
	public BrickusMouseListener(BrickusModel model, BrickusPieceModel pieceModel)
	{
		this.model = model;
		this.pieceModel = pieceModel;
	}
	
	/**
	 * @return the brickus model
	 */
	public BrickusModel getBrickusModel(){
		return model;
	}
	
	/**
	 * @return the piece model
	 */
	public BrickusPieceModel getPieceModel()
	{
		return pieceModel;
	}
	
	/**
	 * rotate the piece if the mouse wheel moves 
	 * up is clockwise
	 * down is counterclockwise 
	 */
	public void mouseWheelMoved(MouseWheelEvent wheelEvent)
	{
		int notches = wheelEvent.getWheelRotation();
		BrickusPiece piece = pieceModel.getSelectedPiece();
		if (piece != null)
		{
			if (notches < 0) 
			{
				piece.rotateClockwise();
			} 
			else 
			{
				piece.rotateCounterClockwise();
			}
			
			pieceModel.setSelectedPiece(piece);
			
			for (BrickusPieceModelListener l: getPieceModel().getListeners())
			{
				l.pieceChanged();
			}
		}
	}
	
	/**
	 * flip the piece if the right mouse button is clicked
	 * flip horizontally if the shift button is also pressed
	 * flip vertically if the shift button isnt pressed 
	 */
	public void mouseClicked(MouseEvent clickEvent)
	{	
		BrickusPiece piece = pieceModel.getSelectedPiece();
		
		if (SwingUtilities.isRightMouseButton(clickEvent) && pieceModel.getSelectedPiece() != null)
		{
			if ((clickEvent.getModifiers() & ActionEvent.SHIFT_MASK) == ActionEvent.SHIFT_MASK)
			{
				piece.flipHorizontally();
			}
			else
			{
				piece.flipVertically();
			}
			
			pieceModel.setSelectedPiece(piece);
			
			for (BrickusPieceModelListener l: getPieceModel().getListeners())
			{
				l.pieceChanged();
			}
		}

	}
	
	public void mouseDragged(MouseEvent paramMouseEvent){}
	public void mouseEntered(MouseEvent paramMouseEvent){}
	public void mouseExited(MouseEvent paramMouseEvent){}
	public void mouseMoved(MouseEvent paramMouseEvent){}
	public void mousePressed(MouseEvent paramMouseEvent){}
	public void mouseReleased(MouseEvent paramMouseEvent){}
}