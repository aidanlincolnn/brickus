/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This is an interface that is implemented by brickus board and brickus pieces container
 * So that they get updated when the current selected piece changes 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

public abstract interface BrickusPieceModelListener
{
  public abstract void pieceChanged();
}