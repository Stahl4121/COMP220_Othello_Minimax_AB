import java.util.HashSet;
import java.util.Set;

/**
 * This class contains the large majority
 * of the Othello program. It provides the code
 * for the creation and modification of the board 
 * (using Othello rules), including score tracking 
 * methods, move validation, and a toString() method 
 * to print the board.
 * 
 * @authors STAHLLR1 & MUMAWBM1
 *
 */
public class Board {
	final int BOARD_SIZE = 8;

	private SquareStatus[][] board;	
	//private ArrayList<int[]> criticalPieces;

	/**
	 * The default constructor for a Board object.
	 * It creates the 2D board array, initializing every
	 * square to empty except for the four middle squares
	 * that begin filled in the game Othello. It also 
	 * initializes the other member variables.
	 */
	public Board(){	
		board = new SquareStatus[BOARD_SIZE][BOARD_SIZE];

		for(int r = 0; r < BOARD_SIZE; r++){
			for(int c = 0; c < BOARD_SIZE; c++){
				this.board[r][c] = SquareStatus.EMPTY;
			}
		}

		//Sets the initial pieces of the board
		this.board[3][3] = SquareStatus.WHITE;
		this.board[4][4] = SquareStatus.WHITE;
		this.board[3][4] = SquareStatus.BLACK;
		this.board[4][3] = SquareStatus.BLACK;


		//criticalPieces = new ArrayList<int[]>();
	}

	/**
	 * This copy constructor for Board copies all 
	 * of the information from an existing board
	 * into a new board. It creates a deep copy
	 * of the member variables, iterating through
	 * the board array and copying each square.
	 * 
	 * @param Board other, the Board to be copied.
	 */
	public Board(Board other){
		board = new SquareStatus[BOARD_SIZE][BOARD_SIZE];

		for(int r = 0; r < BOARD_SIZE; r++){
			for(int c = 0; c < BOARD_SIZE; c++){
				this.board[r][c] = other.board[r][c];
			}
		}

	}


	/**
	 * This method returns the status of the 
	 * specific square provided in the parameters.:
	 * @param r, an int for the row position of the desired square
	 * @param c, an int for the column position of the desired square
	 * @return the enum SquareStatus: white, black, or empty.
	 */
	public SquareStatus getSquareStatus(int r, int c){
		return board[r][c];
	}	

	/**
	 * This method calls isLegalMove() and throws an exception if illegal. 
	 * If legal, the method continues to place the piece, and then call
	 * the necessary method to flip the affected tiles.
	 * 
	 * @param move, a Move object containing the AI or Human's move.
	 * @throws Exception, an error that the move is illegal
	 */
	public void makeMove(Move move) throws Exception{
		
		if(! (isLegalMove(move))){
			throw new Exception ("Invalid move.");
		}

		Set<int[]> critPieces = findCriticalPieces(move);

		board[move.getRow()][move.getCol()] = move.getColor();	

		for(int[] coords : critPieces){
			flipPieces(coords[0]-move.getRow(), coords[1]-move.getCol(), coords[0], coords[1]);
		}
	}


	/**
	 * THis method finds the locations of"critical pieces"
	 * and adds them to the corresponding set. Critical 
	 * pieces are pieces which begin the flipping process from
	 * a new piece. There are 8 possible positions of a critical
	 * point around a given position.
	 * @param move The move being attempted
	 * @return A set containing the coords of critical pieces
	 */
	private Set<int[]> findCriticalPieces(Move move){

		Set<int[]> criticalPieces = new HashSet<int[]>();

		for(int r = move.getRow()-1; r <= (move.getRow() + 1); r++){
			for(int c = move.getCol()-1; c <= (move.getCol() + 1); c++){

				//Could combine both if statements, but this is better for readability
				if(isInBoard(r,c) && board[r][c]!=SquareStatus.EMPTY && board[r][c] != move.getColor()){
					if (isEndCapped(r-move.getRow(), c-move.getCol(), r, c)){
						criticalPieces.add(new int[]{r, c});
					}
				}
			}
		}

		return criticalPieces;

	}

	/**
	 * This method flips all of the 
	 * pieces affected by a new move. 
	 * 
	 * @param rr The row relative, vector path in direction that's being flipped
	 * @param cr The column relative, vector path in direction that's being flipped
	 * @param row  The row position of the critical piece.
	 * @param column	 The column position of the critical piece.
	 */
	public void flipPieces(int rr, int cr, int row, int column){

		int r = row;
		int c = column;

		while (true){

			if(board[r][c] != board[r+rr][c+cr] ){
				board[r][c] = board[r-rr][c-cr];
				return;
			}

			board[r][c] = board[r-rr][c-cr];

			r += rr;
			c += cr;
		}

	}

	/**
	 * This checks if there is a valid path from the new piece to another
	 * piece of the same color. It fulfills the primary condition
	 * for being a critical piece.
	 * 
	 * @param rr The row relative, vector path in direction that's being checked
	 * @param cr The column relative, vector path in direction that's being checked
	 * @param row  The row position of the critical piece.
	 * @param column The column position of the critical piece.
	 * @return a boolean of whether the path is capped by another piece of the same color
	 */
	private boolean isEndCapped(int rr, int cr, int row, int column){		//rr=row relative; cr= column relative

		int r = row;
		int c = column;

		while(isInBoard(r,c)){
			if(board[r][c] == SquareStatus.EMPTY){
				return false;
			}
			else if(board[r][c] != board[row][column]){
				return true;
			}

			r += rr;
			c += cr;
		}

		return false;
	}

	/**
	 * Checks if the attempted move is valid.
	 * Check isAvailableMove and checks if move
	 * will lead to flipped tiles, i.e.,
	 * has critical pieces.
	 * 
	 * @param move The move being attempted.
	 * @return A boolean of whether the move is valid.
	 */
	public boolean isLegalMove(Move move){

		if( ! isAvalableMove(move) ){
			return false;
		}
		if (findCriticalPieces(move).size() == 0){
			return false;
		}

		return true;
	}

	/**
	 * Checks that the move is within the board and
	 * filling an empty space.
	 * @param move The move being attempted.
	 * @return A boolean of whether the move is available
	 */
	private boolean isAvalableMove(Move move){

		if( !(isInBoard(move.getRow(), move.getCol())) ){
			return false;
		}

		if(board[move.getRow()][move.getCol()] != SquareStatus.EMPTY){
			return false;
		}

		return true;
	}
	/**
	 * Counts the number of a certain
	 * type of tile in the board.
	 * @param s The SquareStatus being counted: white/black/empty.
	 * @return An integer sum of the number of certain tiles.
	 */
	public int getNumTiles(SquareStatus s){
		int sum = 0;

		for(int r = 0; r < BOARD_SIZE; r++){
			for(int c = 0; c < BOARD_SIZE; c++){
				if(this.board[r][c] == s){
					sum++;
				}
			}
		}

		return sum;
	}

	/**
	 * Checks whether the position is within the board.
	 * 
	 * @param r The row position in the board.
	 * @param c The column position in the board.
	 * @return A boolean whether the position is within the board.
	 */
	private boolean isInBoard(int r, int c) {
		//Check if a position is valid in the board
		if(c < 0 || r < 0 || c >= BOARD_SIZE || r >= BOARD_SIZE){
			return false;
		}
		return true;
	}

	/**
	 * Checks whether the board is full
	 * 
	 * @return A boolean of whether the board is full
	 */
	public boolean isBoardFull(){
		for(int r = 0; r < BOARD_SIZE; r++){
			for(int c = 0; c < BOARD_SIZE; c++){
				if(this.board[r][c] == SquareStatus.EMPTY){
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Checks if there are any available moves in the
	 * board for the given color.
	 * 
	 * @param color The color being checked for possible moves.
	 * @return A boolean of whether there are any possible moves.
	 */
	public boolean areAvailableMoves(SquareStatus color){

		for(int r = 0 ; r < BOARD_SIZE ; r++){
			for(int c = 0; c < BOARD_SIZE; c++){
				Move m = new Move(r,c,color);
				if(isLegalMove(m)){
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Prints the results for the end of the game
	 * , including the final board, winner, and score.
	 *  
	 */
	public void endGame(){
		System.out.println("Final Board: ");
		System.out.println(toString());

		int blackTiles = getNumTiles(SquareStatus.BLACK);
		int whiteTiles = getNumTiles(SquareStatus.WHITE);

		if(blackTiles > whiteTiles){
			//Convention states empty tiles go towards winner
			blackTiles += (64 - blackTiles - whiteTiles);
			System.out.println("Black wins " + blackTiles + " - " + whiteTiles + " !");
		}
		else if(whiteTiles > blackTiles){
			//Convention states empty tiles go towards winner
			whiteTiles += (64 - blackTiles - whiteTiles);
			System.out.println("White wins " + whiteTiles + " - " + blackTiles + " !");
		}
		else{
			System.out.println("White and black tied " + whiteTiles + " - " + blackTiles + " !");
		}

	}

	/**
	 * Builds and returns a string containing
	 * the formatted Othello board with grid coordinates
	 * labeled on the side.
	 * 
	 * @return A String containing the board and its formatting.
	 */
	public String toString(){

		StringBuilder sb = new StringBuilder();

		sb.append("# 0 1 2 3 4 5 6 7\n");
		for(int r = 0; r < BOARD_SIZE; r++){
			sb.append(r + " ");
			for(int c = 0; c < BOARD_SIZE; c++){

				if (board[r][c] == SquareStatus.BLACK){
					sb.append("B ");
				}
				else if (board[r][c] == SquareStatus.WHITE){
					sb.append("W ");
				}
				else{
					sb.append("- ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();		
	}
}
