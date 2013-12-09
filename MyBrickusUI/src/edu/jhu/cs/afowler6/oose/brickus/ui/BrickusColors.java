/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 * This class returns the correct color based on player and if the piece is placed 
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import java.awt.Color;

import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class BrickusColors 
{	
	private static Color red = Color.RED;
	private static Color blue = Color.BLUE;
	private static Color lightRed = new Color(1,0,0,(float) .5);
	private static Color lightBlue = new Color(0,0,1,(float) .5);
	
	/**
	 * Get the color of a player 
	 * @param player The player who is active
	 * @param placed If the piece has been placed or is being moved on the board
	 * @return red (or light red) if player 1 is active, blue (or light blue) if player 2 is active 
	 */
	public static Color getColor(Player player,Boolean placed)
	{
		if (player == Player.PLAYER1)
		{
			if (placed)
			{
				return red;
			}
			else
			{
				return lightRed;
			}
		}
		else if (player == Player.PLAYER2)
		{
			if (placed)
			{
				return blue;
			}
			else
			{
				return lightBlue;
			}
		}
		else
		{
			return Color.GRAY;
		}
	}
}
