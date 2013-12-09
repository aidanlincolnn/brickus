/**
 * 
 * @author Aidan Fowler <alf@ameritech.net>
 * @version 1.0
 * @since 2013-09-20
 */
package edu.jhu.cs.afowler6.oose.brickus.ui;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;


public class MyBrickusMain {
	
	/**
	 * Main method to start brickus UI
	 * Creates a new brickus frame and sets it to visible
	 */
	public static void main(String[] args)
	{
		BrickusModel model = new edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel();
		MyBrickusFrame gui = new MyBrickusFrame(model);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
	}
}