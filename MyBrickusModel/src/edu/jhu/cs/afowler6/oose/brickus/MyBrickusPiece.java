package edu.jhu.cs.afowler6.oose.brickus;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

/**
 * @author Aidan Fowler
 * @since 2013-09-12
 * worked alone
 */
public class MyBrickusPiece implements BrickusPiece{
	
	private MyBrickusModel model;
	private boolean[][] grid = new boolean[5][5];
	
	/**
	 * Create a brickus piece
	 * @param myModel the model that the piece is in
	 * @param inputGrid the array containing the peice
	 */
	public MyBrickusPiece(MyBrickusModel myModel, boolean[][] inputGrid){
		this.model = myModel;
		this.grid = inputGrid;
	}
	
	/**
	 * Create a brickus piece
	 * @param myModel the model that the piece is in
	 * @param picenum the number of a piece sent to the makeGrid method to make 1 of 21 peices
	 */
	public MyBrickusPiece(MyBrickusModel myModel, int pieceNum){
		this(myModel, makeGrid(pieceNum));
	}
	
	/**
	 * Flips a piece across it's x axis
	 */
	public void flipHorizontally(){
		boolean[][] newGrid = new boolean[grid.length][grid[0].length];
		for (int r=0;r<grid.length;r++){
			for (int c=0;c<grid[0].length;c++){
				newGrid[r][c] = grid[r][grid[0].length-1-c];
			}
		}
		this.grid = newGrid;
		model.changeEvent(new BrickusEvent(model,false,false));
	}
	
	/**
	 * Flips a piece across it's y axis
	 */
	public void flipVertically(){
		boolean[][] newGrid = new boolean[grid.length][grid[0].length];
		for (int r=0;r<grid.length;r++){
			for (int c=0;c<grid[0].length;c++){
				newGrid[r][c] = grid[grid.length-1-r][c];
			}
		}
		this.grid = newGrid;
		model.changeEvent(new BrickusEvent(model,false,false));
	}
	
	/**
	 * @return the height of a piece
	 */
	public int getHeight(){
		return this.grid.length; //rows
	}
	
	/**
	 * @return the width of a piece
	 */
	public int getWidth(){
		return this.grid[0].length; //columns
	}
	
	/**
	 * Checks to see if a cell in the current piece is occupied
	 * @param x the x location of the cell
	 * @param y the y location of the cell
	 * @return true if the cell is occupied, false otherwise
	 */
	public boolean isOccupied(int x, int y){
		return grid[y][x];
	}
	
	/**
	 * rotates a piece clockwise
	 */
	public void rotateClockwise(){
		boolean[][] newGrid = new boolean[grid[0].length][grid.length];
		for(int r=0; r<grid.length; r++){
	        for(int c=0;c<grid[0].length; c++){
	            newGrid[c][grid.length-1-r] = grid[r][c];
	        }
	    }
		this.grid= newGrid;
		model.changeEvent(new BrickusEvent(model,false,false));
	}
	
	/**
	 * rotates a piece counterclockwise
	 */
	public void rotateCounterClockwise(){
		boolean[][] newGrid = new boolean[grid[0].length][grid.length];
		for(int r=0; r<grid.length; r++){
	        for(int c=0;c<grid[0].length; c++){
	            newGrid[c][r] = grid[r][grid[0].length-1-c];
	        }
	    }
		this.grid= newGrid;
		model.changeEvent(new BrickusEvent(model,false,false));
	}
	
	/**
	 * creates a piece based on its piece number 
	 * @param x the piece number
	 * @return the piece stored in a 2d array
	 * @throws java.lang.IndexOutOfBoundsException if we try to create a piece that is not allowed (over 21)
	 */
	private static boolean[][] makeGrid(int x) throws java.lang.IndexOutOfBoundsException{
		boolean[][] array;
		if (x==20){
			array= new boolean[1][1];
			array[0][0] = true;
		}
		else if (x==19){
			array= new boolean[1][2];
			array[0][0] = true;
			array[0][1] = true;
		}
		else if (x==18){
			array= new boolean[2][2];
			array[0][0] = true;
			array[0][1] = true;
			array[1][0] = false;
			array[1][1] = true;
		}
		else if (x==17){
			array= new boolean[1][3];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
		}
		else if (x==16){
			array= new boolean[2][2];
			array[0][0] = true;
			array[0][1] = true;
			array[1][0] = true;
			array[1][1] = true;
		}
		else if (x==15){
			array= new boolean[2][3];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = false;
			array[1][0] = false;
			array[1][1] = true;
			array[1][2] = true;
		}
		else if (x==14){
			array= new boolean[2][3];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[1][0] = false;
			array[1][1] = true;
			array[1][2] = false;
		}
		else if (x==13){
			array= new boolean[2][3];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[1][0] = false;
			array[1][1] = false;
			array[1][2] = true;
		}
		else if (x==12){
			array= new boolean[1][4];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[0][3] = true;
		}
		else if (x==11){
			array= new boolean[3][3];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[1][0] = false;
			array[1][1] = false;
			array[1][2] = true;
			array[2][0] = false;
			array[2][1] = false;
			array[2][2] = true;
		}
		else if (x==10){
			array= new boolean[2][4];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[0][3] = true;
			array[1][0] = false;
			array[1][1] = true;
			array[1][2] = false;
			array[1][3] = false;
		}
		else if (x==9){
			array= new boolean[2][4];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = false;
			array[0][3] = false;
			array[1][0] = false;
			array[1][1] = true;
			array[1][2] = true;
			array[1][3] = true;
		}
		else if (x==8){
			array= new boolean[3][3];
			array[0][0] = true;
			array[0][1] = false;
			array[0][2] = false;
			array[1][0] = true;
			array[1][1] = true;
			array[1][2] = false;
			array[2][0] = false;
			array[2][1] = true;
			array[2][2] = true;
		}
		else if (x==7){
			array= new boolean[3][2];
			array[0][0] = true;
			array[0][1] = true;
			array[1][0] = true;
			array[1][1] = true;
			array[2][0] = false;
			array[2][1] = true;
		}
		else if (x==6){
			array= new boolean[3][2];
			array[0][0] = true;
			array[0][1] = true;
			array[1][0] = false;
			array[1][1] = true;
			array[2][0] = true;
			array[2][1] = true;
		}
		else if (x==5){
			array= new boolean[3][3];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = false;
			array[1][0] = false;
			array[1][1] = true;
			array[1][2] = false;
			array[2][0] = false;
			array[2][1] = true;
			array[2][2] = true;
		}
		else if (x==4){
			array= new boolean[1][5];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[0][3] = true;
			array[0][4] = true;
		}
		else if (x==3){
			array= new boolean[2][4];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[0][3] = true;
			array[1][0] = false;
			array[1][1] = false;
			array[1][2] = false;
			array[1][3] = true;
		}
		else if (x==2){
			array= new boolean[3][3];
			array[0][0] = true;
			array[0][1] = true;
			array[0][2] = true;
			array[1][0] = false;
			array[1][1] = true;
			array[1][2] = false;
			array[2][0] = false;
			array[2][1] = true;
			array[2][2] = false;
		}
		else if (x==1){
			array= new boolean[3][3];
			array[0][0] = true;
			array[0][1] = false;
			array[0][2] = false;
			array[1][0] = true;
			array[1][1] = true;
			array[1][2] = true;
			array[2][0] = false;
			array[2][1] = true;
			array[2][2] = false;
		}
		else if (x==0){
			array= new boolean[3][3];
			array[0][0] = false;
			array[0][1] = true;
			array[0][2] = false;
			array[1][0] = true;
			array[1][1] = true;
			array[1][2] = true;
			array[2][0] = false;
			array[2][1] = true;
			array[2][2] = false;
		}
		else {
			throw new IndexOutOfBoundsException();
		}
		return array;
	}
}
