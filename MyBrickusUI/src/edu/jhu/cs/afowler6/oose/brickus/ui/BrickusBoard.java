/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This is a jpanel that displays the brickus board 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class BrickusBoard extends JPanel
{
	private static final long serialVersionUID = 1L;
	private BrickusModel model;
	private BrickusPieceModel pieceModel;
	private int mouseX;
	private int mouseY;
	private boolean onBoard;
	private BrickusBoardMouseListener mouseListener;

	/**
	 * create the brickus board 
	 * @param brickusModel the brickus model
	 * @param pieceModel the piece model 
	 */
	public BrickusBoard(BrickusModel brickusModel, BrickusPieceModel pieceModel)
	{
		this.model = brickusModel;
		this.pieceModel = pieceModel;
		this.onBoard = false;
		
		mouseListener = new BrickusBoardMouseListener(model, pieceModel,this);
		
		//add mouse listeners 
		this.addMouseListener(mouseListener);
		this.addMouseWheelListener(mouseListener);
		this.addMouseMotionListener(mouseListener);

		//add a brickus listener to update the board when the model changes 
		this.model.addBrickusListener(new BrickusListener()
		{
			public void modelChanged(BrickusEvent brickusEvent)
			{
				BrickusBoard.this.update();
				BrickusBoard.this.repaint();
				BrickusBoard.this.pieceModel.setSelectedPiece(null);
				//if the game is over remove the mouse listeners 
				if (brickusEvent.isGameOver()){
					removeMouseListener(mouseListener);
					removeMouseMotionListener(mouseListener);
					removeMouseWheelListener(mouseListener);
				}
			}
			
			public void illegalMove(BrickusIllegalMoveEvent illegalMove)
			{
				BrickusBoard.this.update();
			}
		}
		);
		
		//add a piece model listener to update the board when a piece changes 
		this.pieceModel.addListener(new BrickusPieceModelListener()
		{
			public void pieceChanged()
			{
				BrickusBoard.this.update();	
				BrickusBoard.this.repaint();
			}
		}
		);
	}
	
	/**
	 * update the current mouse location and if the mouse is on the board or not 
	 */
	private void update(){
		mouseX = mouseListener.getMouse().x;
		mouseY = mouseListener.getMouse().y;
		onBoard = mouseListener.onBoard();
	}

	/**
	 * paint the pieces on the board 
	 * @param g the graphics object used to paint 
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		int boardWidth = this.getWidth();
		int boardHeight = this.getHeight();
		int rectWidth = (int)(boardWidth/14);
		int rectHeight = (int)(boardHeight/14);
		Graphics2D g2 = (Graphics2D) g;
		
		for (int i = 0; i < model.getHeight(); i++)
		{
			for (int j=0; j< model.getWidth();j++)
			{
				//create a rectangle for each spot on the board 
				Rectangle rect = new Rectangle(new Point((int)(j*rectWidth),(int)(i*rectHeight)),new Dimension(rectWidth,rectHeight));
				//if the board position that corresponds to the rectangle is occupied by a player fill it in with that players color 
				if(model.getContents(j, i)==Player.PLAYER2)
				{
					g2.setColor(BrickusColors.getColor(Player.PLAYER2, true));
					g2.fill(rect);
				}
				
				else if (model.getContents(j,i)==Player.PLAYER1)
				{
					g2.setColor(BrickusColors.getColor(Player.PLAYER1, true));
					g2.fill(rect);
				}
				else //if not fill it in with white 
				{
					g2.setColor(Color.WHITE);
					g2.fill(rect);
				}
				
				if (pieceModel.getSelectedPiece()!=null && onBoard)
				{
					for (int k=0;k<pieceModel.getSelectedPiece().getHeight();k++)
					{
						for (int l=0;l<pieceModel.getSelectedPiece().getWidth();l++)
						{
							//check if the piece the player has selected is on the board and fill in the rectangle with
							//a slightly transparent color if it is
							if (pieceModel.getSelectedPiece().isOccupied(l, k) && rect.contains(new Point(mouseX+(l*rectWidth),mouseY+(k*rectHeight))))
							{
								g2.setColor(BrickusColors.getColor(model.getActivePlayer(), false));
								g2.fill(rect);
							}	
						}
					}
				}
				//draw black lines around the rectangle to give the board a grid look
				g2.setColor(Color.BLACK);
				g2.draw(rect);
			}
		}
	}
}