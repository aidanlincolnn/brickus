/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class handels mouse clicks inside the piece container 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import java.awt.event.MouseEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

public class BrickusPiecesMouseListener extends BrickusMouseListener{
	
	public BrickusPiecesMouseListener(BrickusModel model, BrickusPieceModel pieceModel)
	{
		super(model, pieceModel);
	} 
	
	/**
	 * sets the selected piece to the piece clicked on in the piece container 
	 * @param clickEvent a mouse click
	 */
	public void mouseClicked(MouseEvent clickEvent)
	{
		super.mouseClicked(clickEvent);
		//necessary to have such a long call because we need to get a piece from a jcomponent in a jpanel 
		getPieceModel().setSelectedPiece(((IndividualBrickusPiece) clickEvent.getComponent().getComponentAt(clickEvent.getPoint())).getPiece());
		for (BrickusPieceModelListener l: getPieceModel().getListeners())
		{
			l.pieceChanged();
		}
	}
}
