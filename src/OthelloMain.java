import java.util.Scanner;

public class OthelloMain {
	public static void main (String[] args){
		Board othelloBoard = new Board();
		Scanner scnr = new Scanner(System.in);

		while (othelloBoard.getNumTiles(SquareStatus.EMPTY)!=0){
			System.out.print(othelloBoard.toString());

			System.out.print("Player 1 (" + SquareStatus.BLACK + "): ");
			try{
				othelloBoard.makeMove(new Move(scnr.nextInt(), scnr.nextInt(), SquareStatus.BLACK));
			}
			catch(Exception exc){
				System.out.println(exc.getMessage());
			}
			
			System.out.print(othelloBoard.toString());

			System.out.print("Player 2 (" + SquareStatus.WHITE + "): ");
			try{
				othelloBoard.makeMove(new Move(scnr.nextInt(), scnr.nextInt(), SquareStatus.WHITE));
			}
			catch(Exception exc){
				System.out.println(exc.getMessage());
			}

		}

		//scnr.close();
	}
}
