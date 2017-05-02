import java.io.IOException;

/**
 * A Player class for HumanPlayer and 
 * AIPlayer to inherit from. This simplifies 
 * OthelloMain, since it can have two Player
 * references, regardless of type.
 * 
 * @author STAHLLR1 & MUMAWBM1
 *
 */
public abstract class Player {
	protected SquareStatus color;
	
	/**
	 * A method overwritten in both child classes,
	 * allowing child classes to determine and
	 * return the Move.
	 * 
	 * @return A Move object, the player's move
	 */
	abstract public Move getMove(Board current);
	
	/**
	 * A simple getter for the 
	 * player's color.
	 * 
	 * @return The player's color
	 */
	public SquareStatus getColor(){
		return color;
	}

}
