import java.util.ArrayList;



/**
 * This Class creates an ai opponent for a human to play against. 
 * The AI goes second and plays according to a minimax algorithm
 * set to a specific depth.
 * @author MUMAWBM1 & STAHLLR1
 *
 */
public class AIPlayer extends Player {
	private final int DEFAULT_DEPTH = 8;
	private int depth;
	
	/**
	 * Default constructor for an AI object.
	 * It sets the depth to a default value of 8.
	 */
	public AIPlayer(){
		color = SquareStatus.WHITE;
		depth = DEFAULT_DEPTH;
	}
	
	
	/**
	 * This is a constructor for an AI object
	 * that sets a specific depth.
	 * @param depth	How many moves into the future the AI should look.
	 */
	public AIPlayer(int depth){
		color = SquareStatus.WHITE;
		this.depth = depth;
	}
	
	/**
	 * This is a constructor for an AI object
	 * that sets a specific color.
	 * @param color The color of the AIPlayer
	 */
	public AIPlayer(SquareStatus color){
		this.color = color;
		depth = DEFAULT_DEPTH;
	}
	
	/**
	 * This is a constructor for an AI object
	 * that sets a specific depth and color.
	 * @param depth	How many moves into the future the AI should look.
	 * @param color 
	 */
	public AIPlayer(int depth, SquareStatus color){
		this.color = color;
		this.depth = depth;
	}
	
	/**
	 * A getter for depth.
	 * @return depth, how many moves into the future the ai is calculating.
	 */
	public int getDepth(){
		return depth;
	}
	
	/**
	 * A setter for depth.
	 * @param d, How many moves into the future the AI calculates
	 */
	public void setDepth(int d){
		depth=d;
	}
	
	/**
	 * Starts the minimax process, and then returns the final selected move to main
	 * @param current Takes in the current state of the board to figure out how many
	 * @return Returns the most optimal move to be played by the AI
	 */
	@Override
	public Move getMove(Board current){
		
		ArrayList<Move> list = new ArrayList<Move>();
		for(int r=0 ; r < current.BOARD_SIZE ; r++){
			for(int c = 0; c < current.BOARD_SIZE; c++){
				Move t = new Move(r,c,color);
				if(current.isLegalMove(t)){
					list.add(t);
				}
			}
		}
		Move result = new Move(list.get(max(current,0,list)));
		
		System.out.println("(" + color + ") Computer, please enter the row and column of your move.");
		System.out.println(result.getRow() + " " + result.getCol());
		
		return(result);	
	}

	/**
	 * This method is the max portion of minimax, and as such finds the best possible
	 * move the ai can make
	 * @param iterated	Board Object passed from max. It is a projected move possibility
	 * @param n		Iteration counter for recursion
	 * @param moves		ArrayList of legal moves to be tested in this method
	 * @return		Highest score to min, or index of best move at the top level
	 */
	public int max(Board iterated, int n , ArrayList<Move> moves){
		SquareStatus opposingColor;
		if(color == SquareStatus.BLACK){
			opposingColor = SquareStatus.WHITE;
		}
		else{
			opposingColor = SquareStatus.BLACK;
		}
		if(n==depth || iterated.isBoardFull()||!iterated.areAvailableMoves(opposingColor)){
			return iterated.getNumTiles(color);
		}

		int maxScore=0;
		int moveIndex = 0;
		for(int i=0;i<moves.size();i++){
			Board copy = new Board(iterated);
			try{
				copy.makeMove(moves.get(i));
			}
			catch(Exception e){
				System.out.println("NO!!!!!!!!!");
			}
			ArrayList<Move> list = new ArrayList<>();
			for(int r=0;r<copy.BOARD_SIZE;r++){
				for(int c=0;c<copy.BOARD_SIZE;c++){
					Move layer = new Move(r,c,opposingColor);
					if (copy.isLegalMove(layer)){
						list.add(layer);
					}
				}
			}
			int temp =min(copy, n+1, list);
			if(maxScore<temp){
				maxScore=temp;
				if(n==0){
					moveIndex=temp;
				}
			}
		}
		if(n==0){
			return moveIndex;
		}
		return maxScore;
		
	}	
	/**
	 * This method is the min portion of minimax, and as such finds the worst possible
	 * move(for the ai) that the human can make
	 * @param iterated 	Board Object passed from max. It is a projected move possibility
	 * @param n			Keeps track of recursion layer
	 * @param moves		ArrayList of moves to be tested in this method
	 * @return			Returns lowest score to max
	 */
	public int min(Board iterated, int n, ArrayList<Move> moves){
		
		if(n==depth || iterated.isBoardFull()|| !iterated.areAvailableMoves(color)){
			return iterated.getNumTiles(color);
		}
		
		int minScore=0;
		for(int i=0;i<moves.size();i++){
			Board copy = new Board(iterated);
			try{
				copy.makeMove(moves.get(i));				
			}
			catch(Exception e){
				System.out.println("NO!!!!!!!!!");
			}
			ArrayList<Move> list = new ArrayList<>();
			for(int r=0;r<copy.BOARD_SIZE;r++){
				for(int c=0;c<copy.BOARD_SIZE;c++){
					Move layer = new Move(r,c,color);
					if (copy.isLegalMove(layer)){
						list.add(layer);
					}
				}
			}
			int temp = max(copy, n+1, list);
			if(minScore>temp){
				minScore=temp;
			}
		}
		return minScore;		
	}
}