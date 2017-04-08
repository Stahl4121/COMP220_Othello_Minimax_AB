import java.util.ArrayList;
import java.util.Stack;

public class Board {
	public SquareStatus[][] board;	
	final int BOARD_SIZE = 8;
	ArrayList<int[]> criticalPieces;

	
	public Board(){	
		board=new SquareStatus[BOARD_SIZE][BOARD_SIZE];
		criticalPieces = new ArrayList<int[]>();
	}
	
	public Board(Board other){
		for(int r=0;r<BOARD_SIZE;r++){
			for(int c=0;c<BOARD_SIZE;c++){
				this.board[r][c]= other.board[r][c];
			}
		}
		
		criticalPieces = new ArrayList<int[]>();
	}
	
	public void makeMove(Move move) throws Exception{
		if(! (isLegalMove(move))){
			throw new Exception ("Invalid move.");
		}
		
		board[move.getRow()][move.getCol()] = move.getColor();	
		
		for(int[] coords : criticalPieces){
			flipPieces(coords[0]-move.getRow(), coords[1]-move.getCol(), coords[0], coords[1]);
		}
	}
	
	
	public void findCriticalPieces(Move move){
		
		//ArrayList<int[]> criticalPieces = new ArrayList<int[]>();
		criticalPieces = new ArrayList<int[]>();
		
		for(int r = move.getRow()-1; r <= (move.getRow() + 1); r++){
			for(int c = move.getCol()-1; c <= (move.getCol() + 1); c++){
				
				if(r == move.getRow() && c == move.getCol() ){
					c++;
				}
				
				if(isInBoard(r,c) && board[r][c]!=SquareStatus.EMPTY && board[r][c]!=board[move.getRow()][move.getCol()]){
					boolean isCritPiece = isEndCapped(r-move.getRow(), c-move.getCol(), r, c);
					if (isCritPiece){
						criticalPieces.add(new int[]{r, c});
						//board[move.getRow()][move.getCol()] = move.getColor();
						//flipPieces(r-move.getRow(), c-move.getCol(), r, c);
					}
				}
			}
		}
	}
	
	public void flipPieces(int rr, int cr, int r, int c){
		if(board[r][c] == board[r+rr][c+cr] ){
			board[r][c] = board[r-rr][c-cr];
			flipPieces(rr, cr, r+rr, c+cr);
		}
		
		board[r][c] = board[r-rr][c-cr];
	}
	
	public boolean isEndCapped(int rr, int cr, int r, int c){		//rr=row relative; cr= column relative
		if(!isInBoard(r,c)){
			return false;
		}
		else if(board[r][c]==board[r+rr][c+cr]){
			return(isEndCapped(rr,cr,r+rr,c+cr));
		}
		else if(board[r][c]==SquareStatus.EMPTY){
			return false;
		}
		
		return true;
	}
	
	public boolean isLegalMove(Move move){
		
		if( !(isInBoard(move.getRow(), move.getCol())) ){
			return false;
		}
		
		if(board[move.getRow()][move.getCol()] != SquareStatus.EMPTY){
			return false;
		}
		
		findCriticalPieces(move);
		
		if (criticalPieces.size() == 0){
			return false;
		}
		
		return true;
	}
	
	public int getNumTiles(SquareStatus s){
		int sum = 0;
		
		for(int r=0;r<BOARD_SIZE;r++){
			for(int c=0;c<BOARD_SIZE;c++){
				if(this.board[r][c] == s){
					sum++;
				}
			}
		}
		
		return sum;
	}
	
	public boolean isInBoard(int r, int c) {
		//Check if a position is valid in the board
		if(c < 0 || r < 0 || c >= BOARD_SIZE || r >= BOARD_SIZE){
			return false;
		}
		return true;
	}
	
}
