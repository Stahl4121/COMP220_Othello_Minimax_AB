import java.io.IOException;
import java.util.Scanner;

/**
 * This is A stub for a human player class which may or may not be used.
 * @author MUMAWBM1
 *
 */
public class HumanPlayer extends Player {

	public HumanPlayer(){
		this.color = SquareStatus.BLACK;
	}

	public HumanPlayer(SquareStatus color){
		this.color = color;
	}

	/**
	 * creates scanner to read in player's move
	 */
	@Override
	public Move getMove(Board current){
		int row = 0;
		int column = 0;

		while(true){
			System.out.println("(" + color + ") Please enter the row and column of your move, separated by a space.");

			if(OthelloMain.scnr.hasNextInt()){
				row = OthelloMain.scnr.nextInt();
				
				while(true){
					if(OthelloMain.scnr.hasNextInt()){
						column = OthelloMain.scnr.nextInt();
						
						return (new Move(row, column, color));
					}
					else{
						OthelloMain.scnr.next();
						System.out.println("ERROR: Non-integer value.");
						break;
					}
				}
			}
			else{
				OthelloMain.scnr.next();
				System.out.println("ERROR: Non-integer value.");
			}
		}
	
	}

	/**
	 * Allows player to start/stop/pause game and/or reset
	 */
	public void commands(){

	}


}
