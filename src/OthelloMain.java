import java.util.Scanner;
/**
 * This is where the game is actually run, where all the primary objects are
 * created and where the first method calls are made. 
 * @author MUMAWBM1 & STAHLLR1
 *
 */
public class OthelloMain {
	public static Scanner scnr = new Scanner(System.in);
	
	public static void printSelectionError(){
		System.out.println("--------------------------------------------");
		System.out.println("ERROR: Please enter an integer from 1 to 3.");
		System.out.println("--------------------------------------------");
	}

	public static void main (String[] args){

		Board othelloBoard = new Board();
		

		Player player1;
		Player player2;

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
				player2 = new AIPlayer(2);
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

		while(! othelloBoard.isBoardFull())
		{
			System.out.print(othelloBoard.toString());
			
			isValidMove = false;

			while ( !isValidMove){
				try{

					if(isPlayerOne){
						Move tbp = new Move(player1.getMove(othelloBoard));
						if(player1.isAI()){
							if (tbp.getRow()==-1&&tbp.getCol()==-1){
								System.out.println("AI has no valid Moves.");
								isValidMove = true;
								isPlayerOne = false;
							}
						}
						othelloBoard.makeMove(tbp);
						isPlayerOne = false;
					}
					else{
						Move tbp = new Move(player2.getMove(othelloBoard));
						if(player2.isAI()){
							if (tbp.getRow()==-1&&tbp.getCol()==-1){
								System.out.println("AI has no valid Moves.");
								isValidMove = true;
								isPlayerOne = true;
							}
						}
						othelloBoard.makeMove(tbp);
						isPlayerOne = true;
					}

					isValidMove = true;
				}
				catch(Exception e){
					System.out.println(e);
				}
			}

		}


		scnr.close();
	}
}
