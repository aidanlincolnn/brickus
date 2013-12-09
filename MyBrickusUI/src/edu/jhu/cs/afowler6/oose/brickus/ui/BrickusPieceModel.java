/**
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class holds the current selected piece and a set of listeners 
 * for the board and pieces container so that the piece can move between them
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import java.util.HashSet;
import java.util.Set;

public class BrickusPieceModel
{
	private BrickusPiece selectedPiece;
	private Set<BrickusPieceModelListener> listeners;

	public BrickusPieceModel(BrickusPiece piece)
	{
		selectedPiece = piece;
		listeners = new HashSet<BrickusPieceModelListener>();
	}
	
	/**
	 * sets the selected piece 
	 * @param piece the current piece 
	 */
	public void setSelectedPiece(BrickusPiece piece)
	{
		selectedPiece = piece;
	}
	
	/**
	 * @return the selected piece 
	 */
	public BrickusPiece getSelectedPiece()
	{
		return selectedPiece;
	}
	
	/**
	 * add a listener to the set of listeners 
	 * @param pieceModelListener the listener to add
	 */
	public void addListener(BrickusPieceModelListener pieceModelListener)
	{
	    listeners.add(pieceModelListener);
	}
	
	/**
	 * @return the model listeners 
	 */
	public Set<BrickusPieceModelListener> getListeners()
	{
		return listeners;
	}
}
