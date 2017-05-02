import java.util.Scanner;
/**
 * This is where the game is actually run, where all the primary objects are
 * created and where the first method calls are made. 
 * @author MUMAWBM1 & STAHLLR1
 *
 */
public class OthelloMain {
	//Eliminates some nasty bugs from having multiple System.in scanners
	public static Scanner scnr = new Scanner(System.in);
	
	/**
	 * Prints an error if the user has entered an invalid integer choice.
	 */
	public static void printSelectionError(){
		System.out.println("--------------------------------------------");
		System.out.println("ERROR: Please enter an integer from 1 to 3.");
		System.out.println("--------------------------------------------");
	}
	
	/**
	 * Prints a message when there are no available moves.
	 * @param p The player that has no possible moves.
	 */
	public static void printNoMoves(Player p){
		System.out.println("-----------------------------------------------------------------");
		System.out.println("(" + p.getColor() + ")" + " There are no legal moves available. Skipping turn.");
		System.out.println("-----------------------------------------------------------------");
	}

	public static void main (String[] args){

		Board othelloBoard = new Board();
		
		//Player references are made, but not initialized to types
		Player player1;
		Player player2;

		
		//This loop handles the user's game choice
		while(true){

			int choice = 0;

			while(true){
				System.out.println("Enter 1 for Human vs. Computer");
				System.out.println("Enter 2 for Human vs. Human");
				System.out.println("Enter 3 for Computer vs. Computer");

				if(scnr.hasNextInt()){
					choice = scnr.nextInt();
					break;
				}
				else{
					scnr.next();
					printSelectionError();
				}
			}


			if (choice == 1){
				player1 = new HumanPlayer();
				player2 = new AIPlayer();
				break;
			}
			else if (choice == 2){
				player1 = new HumanPlayer();
				player2 = new HumanPlayer(SquareStatus.WHITE);
				break;
			}
			else if (choice == 3){
				player1 = new AIPlayer(SquareStatus.BLACK);
				player2 = new AIPlayer();
				break;
			}
			else{
				printSelectionError();
			}

			scnr.hasNext();
		}
		
		boolean isValidMove;
		boolean isPlayerOne = true;
		boolean noMovesPlayerOne = false;

		//Game Loop
		while(! othelloBoard.isBoardFull())
		{
			System.out.println("----------------------------------------------------------------------------");
			System.out.print(othelloBoard.toString());
			System.out.println("----------------------------------------------------------------------------");

			
			isValidMove = false;

			if(isPlayerOne && !othelloBoard.areAvailableMoves(player1.getColor())){
				printNoMoves(player1);
				isPlayerOne = !isPlayerOne;
				noMovesPlayerOne = true;
			}
			
			if(!isPlayerOne && !othelloBoard.areAvailableMoves(player2.getColor())){
				printNoMoves(player2);
				isPlayerOne = !isPlayerOne;
				
				//This will exit the loop and end the game, bc both players have no possible moves
				if(noMovesPlayerOne){
					break;
				}
			}
			noMovesPlayerOne = false;

			//Loop ensures a valid move is always provided
			while ( !isValidMove){
				try{
					if(isPlayerOne){
						othelloBoard.makeMove(player1.getMove(othelloBoard));
					}
					else{
						othelloBoard.makeMove(player2.getMove(othelloBoard));
					}

					isPlayerOne = !isPlayerOne;
					isValidMove = true;
				}
				catch(Exception e){
					System.out.println(e);
				}
				
			}

		}

		System.out.println("----------------------------------------------------------------------------");
		othelloBoard.endGame();
		System.out.println("----------------------------------------------------------------------------");


		scnr.close();
	}
}
