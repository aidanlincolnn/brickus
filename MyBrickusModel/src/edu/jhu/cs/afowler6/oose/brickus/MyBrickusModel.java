package edu.jhu.cs.afowler6.oose.brickus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

/**
 * @author Aidan Fowler
 * @since 2013-09-12
 * worked alone
 */
public class MyBrickusModel implements BrickusModel {
	
	private Player[][] gameGrid;
	private Map<Player, List<BrickusPiece>> playerPieces;
	private Player activePlayer;
	private Set<BrickusListener> listeners ;
	private boolean[] passTracker;
	
	/**
	 * Sets up the brickus model
	 * initializes all variables
	 * creates the pieces for each player
	 */
	public MyBrickusModel(){
		gameGrid = new Player[getHeight()][getWidth()];
		playerPieces = new HashMap<Player,List<BrickusPiece>>();
		activePlayer = Player.PLAYER1;
		listeners = new HashSet<BrickusListener>();
		passTracker = new boolean[2];
		Arrays.fill(passTracker, Boolean.FALSE);
		playerPieces.put(Player.PLAYER1, createPieceList());
		playerPieces.put(Player.PLAYER2, createPieceList());	
	}
	
	/**
	 * Adds a BrickusListener to the list of listeners
	 */
	public void addBrickusListener(BrickusListener listener){
		this.listeners.add(listener);
	}
	
	/**
	 * Calculates the players score
	 * Iterates through each box on the grid and accumulates 
	 * the score for a player
	 * @param Player the player whose score you want
	 * @return int the score of the player
	 */
	public int calculateScore(Player player){
		int score=0;
		for(int r=0; r<getHeight(); r++){
			for (int c=0; c<getWidth(); c++){
					if (gameGrid[r][c]== player){
						score++;
					}
				}
		}
		return score;
	}
	
	/**
	 * Get the current active player
	 * @return Player the active player
	 */
	public Player getActivePlayer(){
		return this.activePlayer;
	}
	
	/**
	 * Get the player at a specified location in the game grid
	 * @param x the location on the x axis
	 * @param y the location on the y axis
	 * @return Player the player occupying the game grid at (x,y)
	 */
	public Player getContents(int x, int y) throws java.lang.IndexOutOfBoundsException {
		if ((x > getWidth()) || (x<0) || (y > getHeight()) || y<0){
			throw new java.lang.IndexOutOfBoundsException();
		}
		else {
			return gameGrid[x][y];
		}
	}
	
	/**
	 * @return the height of the game board
	 */
	public int getHeight(){
		return 14;
	}
	
	/**
	 * @param player the player whose piece list we want 
	 * @return the list of brickus pieces 
	 */
	public List<BrickusPiece> getPieces(Player player){
		return playerPieces.get(player);
	}
	
	/**
	 * @return the width of the game board
	 */
	public int getWidth(){
		return 14;
	}
	
	/**
	 * pass a player
	 * this method checks to see if both players have passed consecutively 
	 * and ends the game if they have, otherwise it sets the non active player
	 * to the current active player
	 * @param Player the player who is passing
	 */
	public void pass(Player player){
		//passTracker stores a boolean that corresponds to each player [player1,player2] passing on the previous turn
		//if both players pass in a row the game ends
		boolean endgame = false;
		if (player == Player.PLAYER1){
			if (passTracker[1] == true) {
				endgame = true;
			}
			else {
				passTracker[0] = true;
			}
		}
		else if (player == Player.PLAYER2){
			if (passTracker[0] == true) {
				endgame = true;
			}
			else {
				passTracker[1] = true;
			}
		}	
		updatePlayer(player,false);
		changeEvent(new BrickusEvent(this, true, endgame));
	}
	
	/**
	 * this method calls methods to check if a position is a valid location
	 * and places a piece in that location if it is
	 * @param player the current player
	 * @param x the grid location on the x axis
	 * @param y the grid location on the y axis
	 * @param piece the current brickus piece that a player is trying to place
	 */
	public void placePiece(Player player, int x, int y , BrickusPiece piece){
		
		boolean placePiece = false;
		
		//Must Place In A Corner On First Placement
		if (playerPieces.get(player).size() == 21){ 
			placePiece = checkFirstPiece(x,y,piece);
		}
		
		//Normal placement for all other pieces
		else if (playerPieces.get(player).size() > 0){
			placePiece = checkNormalPiece(player,x,y,piece);
		}
		
		if (placePiece) {//place the piece, remove the piece from the lsit, update active player
			placeThePiece(player,x,y,piece);
			playerPieces.get(player).remove(piece);
			updatePlayer(player,true);
		}
	}
	
	/**
	 * This method removes a BrickusListener from the list of listeners
	 * @param listner the listener to remove
	 */
	public void removeBrickusListener(BrickusListener listener){
		this.listeners.remove(listener);
	}
	
	/**
	 * this method creates a list of brickus pieces
	 * @return pieces the list of pieces 
	 */
	private List<BrickusPiece> createPieceList(){
		List<BrickusPiece> pieces = new ArrayList<BrickusPiece>();
		for(int i=0; i<21; i++){
			pieces.add(new MyBrickusPiece(this,i));
		}
		return pieces;
	}
	
	/**
	 * this method places a piece on the game grid
	 * @param player the active player
	 * @param x the x location on the game grid
	 * @param y the y location on the game grid
	 * @param piece the piece to place
	 */
	private void placeThePiece(Player player, int x, int y, BrickusPiece piece){
		for(int r=0; r<piece.getHeight(); r++){
			for (int c=0; c<piece.getWidth(); c++){
				if (piece.isOccupied(c, r)){
					gameGrid[x+c][y+r] = player;
				}
			}
		}
		changeEvent(new BrickusEvent(this,true,false));
	}
	
	/**
	 * This method updates the current active player and the array 
	 * containing booleans corresponding to if the player most recently passed
	 * @param player the current active player
	 * @param placed if the piece was placed or if the turn was a pass
	 */
	private void updatePlayer(Player player, boolean placed){
		if (player == Player.PLAYER1 && placed == true){ //change player after placing piece
			passTracker[1] = false;
			activePlayer = Player.PLAYER2;
		}
		else if (player == Player.PLAYER2 && placed == true){
			passTracker[0] = false; 
			activePlayer = Player.PLAYER1;
		}
		else if (placed == false){ //change player without placing piece (a pass)
			if (player == Player.PLAYER1){
				activePlayer = Player.PLAYER2;
			}
			else{
				activePlayer = Player.PLAYER1;
			}
		}
	}
	
	/**
	 * This method checks if the placement of a players first piece is valid
	 * it checks to see if the piece is being placed in a corner and makes sure the corner is empty
	 * @param x the x location on the game grid
	 * @param y the y location on the game grid
	 * @param piece the piece that the player is trying to place
	 * @return true if the placement is valid, false otherwise
	 */
	private boolean checkFirstPiece(int x, int y, BrickusPiece piece){
		
		boolean cornerEmpty = false;
		boolean validFirst = false; 
		boolean onTop = false;
		boolean outOfBounds = false;
		
		if (x==0 && y==0 && piece.isOccupied(0, 0)){//top left
			validFirst = true;
			cornerEmpty = (getContents(0,0) == null);
		}
		else if (x+piece.getWidth()-1==getWidth()-1 && y==0 && piece.isOccupied(piece.getWidth()-1,0 )){//top right
			validFirst = true;
			cornerEmpty = (getContents(getWidth()-1,0) == null);
		}
		else if (x == 0 && y+piece.getHeight()-1==getHeight()-1 && piece.isOccupied(0,piece.getHeight()-1)){//bottom  left
			validFirst = true;
			cornerEmpty = (getContents(0,getHeight()-1) == null);
		}
		else if (x+piece.getWidth()-1 == getWidth()-1 && y+piece.getHeight()-1 ==getHeight()-1 && piece.isOccupied( piece.getWidth()-1,piece.getHeight()-1)){//bottom right
			validFirst = true;
			cornerEmpty = (getContents(getWidth()-1,getHeight()-1) == null);
		}
		
		for (int r=0;r<piece.getHeight();r++){
			for (int c=0;c<piece.getWidth();c++){
				try {
					if (getContents(x+c,y+r) != null && piece.isOccupied(c,r)){
						onTop = true;
					}
				}
				catch(IndexOutOfBoundsException e){
					outOfBounds = true;
				}
			}
		}
		if (outOfBounds){//print the piece does not fit on the board
			illegalEvent(new BrickusIllegalMoveEvent(ERROR.OUT_OF_BOUNDS.toString()));
		}
		else if (onTop){//print the piece is on top of another piece
			illegalEvent(new BrickusIllegalMoveEvent(ERROR.ON_TOP.toString()));
		}
		else if (!validFirst){//print that the piece is not in a corner
			illegalEvent(new BrickusIllegalMoveEvent(ERROR.NOT_IN_CORNER.toString()));
		}
		return (validFirst && cornerEmpty);
	}
	
	/**
	 * THis method checks to see if any placement besides the first is valid
	 * @param player the current player
	 * @param x the x location on the game grid
	 * @param y the y location on the game grid
	 * @param piece the piece the player is trying to place
	 * @return true if the placement is valid, false otherwise 
	 */
	private boolean checkNormalPiece(Player player, int x, int y, BrickusPiece piece){
		
		boolean onTop = false;
		boolean foundDiagonal = false;	
		boolean outOfBounds = false;
		boolean foundAdjacentSameColor = false;
		
		try{
			for(int r=0; r<piece.getHeight(); r++){
				for (int c=0; c<piece.getWidth(); c++){
					getContents(x+c,y+r);// check for index out of bounds 
					if (piece.isOccupied(c, r)){ 
						if (gameGrid[x+c][y+r] == null){//check if the piece is on top of another
							//for each square of the piece
							//check the squares above, below, to the left and to the right for the same player
							//if we find the same color square we can not place the new piece
							//we want to ignore index out of bounds errors when checking around the piece
							//because we check all 8 surrounding squares and they can possibly be off of the game grid
							if (checkRange(x+c-1,y+r)){
								if (getContents(x+c-1,y+r) != player){
								}
								else{
									foundAdjacentSameColor = true;
								}
							}
							if (checkRange(x+c+1,y+r)){
								if (getContents(x+c+1,y+r) != player){
								}
								else{
									foundAdjacentSameColor = true;
								}
							}
							if (checkRange(x+c,y+r-1)){
								if (getContents(x+c,y+r-1) != player){
								}
								else{
									foundAdjacentSameColor = true;
								}
							}
							if (checkRange(x+c,y+r+1)){
								if (getContents(x+c,y+r+1) != player){
								}
								else{
									foundAdjacentSameColor = true;
								}
							}
							//check the four diagonals from the piece for the same player color
							//if we find a diagonal match, the piece might be able to be placed
							//save the fact that there is a diagonal in foundDiagonal
							if (checkRange(x+c-1,y+r-1)){
								if (getContents(x+c-1,y+r-1) == player){
									foundDiagonal = true;
								}
							}
							if (checkRange(x+c+1,y+r-1)){
								if (getContents(x+c+1,y+r-1) == player){
									foundDiagonal = true;
								}
							}
							if (checkRange(x+c-1,y+r+1)){
								if (getContents(x+c-1,y+r+1) == player){
									foundDiagonal = true;
								}
							}
							if (checkRange(x+c+1,y+r+1)){
								if (getContents(x+c+1,y+r+1) == player){
									foundDiagonal = true;
								}
							}
		
						}
						else{ //the square is already occupied
							onTop = true;
						}
					}
				}
			}
		}
		catch(IndexOutOfBoundsException e){ 
			outOfBounds = true;
		}
		if (outOfBounds){ //print that the piece does not fit on the grid
			illegalEvent(new BrickusIllegalMoveEvent(ERROR.OUT_OF_BOUNDS.toString()));
			return false;
		}
		else if (onTop){ // print that the piece is on top of another piece
			illegalEvent(new BrickusIllegalMoveEvent(ERROR.ON_TOP.toString()));
		}
		else if (foundAdjacentSameColor){ //print that the piece is adjacent to a same colored piece
			illegalEvent(new BrickusIllegalMoveEvent(ERROR.SAME_ADJACENT.toString()));
		}
		else if (!foundDiagonal){//print that the piece is not touching a same colored piece diagonally
			illegalEvent(new BrickusIllegalMoveEvent(ERROR.NO_DIAGONAL.toString()));
		}
		return (foundDiagonal && !onTop && !foundAdjacentSameColor);
	}
	
	/**
	 * check if the current values for x and y are in the range of the game grid
	 * @param x the x location in the grid
	 * @param y the y location in the grid
	 * @return true if the valus is on the grid, false otherwise
	 */
	private boolean checkRange(int x, int y){
		return (x>=0 && x<=13 && y>=0 && y<=13);
	}
	
	/**
	 * for each event in the listener list update the model
	 * @param event the even to update the model with
	 */
	void changeEvent(BrickusEvent event){
		for(BrickusListener l :listeners){
			l.modelChanged(event);
		}
	}
	
	/**
	 * for each even in the listener listener list update the model with an illegal move
	 * @param event
	 */
	void illegalEvent(BrickusIllegalMoveEvent event){
		for(BrickusListener l: listeners){
			l.illegalMove(event);
		}
	}
	
	/**
	 * An enum to store the error messages for easier access
	 */
	private enum ERROR {
		OUT_OF_BOUNDS("A placed piece must be entirely on game board."),
		ON_TOP("Pieces may not be placed on top of other pieces."),
		SAME_ADJACENT("Pieces may not be placed with sides touching pieces of the same color."),
		NO_DIAGONAL("Pieces must touch pieces of the same color diagonally."),
		NOT_IN_CORNER("A player's first piece must be placed in a corner");
		
		private String description; //Error message to print
		
		private ERROR(String description){
			this.description = description;
		}
		
		public String toString(){
			return this.description;
		}
	}
}
