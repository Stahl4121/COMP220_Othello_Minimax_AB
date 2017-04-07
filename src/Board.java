
public class Board {
	public SquareStatus[][] board;
	final int BOARD_SIZE = 8;
	
	public Board(){			//original
		board=new SquareStatus[BOARD_SIZE][BOARD_SIZE];
	}
	
	public Board(Board other){
		for(int r=0;r<BOARD_SIZE;r++){
			for(int c=0;c<BOARD_SIZE;c++){
				this.board[r][c]= other.board[r][c];
			}
		}
	}
	
	public void flipPieces(Board current, Move move){
		for(int r= move.getRow()-1;r<=(move.getRow()+1);r++){
			for(int c=move.getCol()-1;c<=(move.getCol()+1);c++){
				if(isInBoard(r,c)&&current.board[r][c]!=SquareStatus.EMPTY
						&& current.board[r][c]!=current.board[move.getRow()][move.getCol()]){
					boolean flip = isEndCapped(current, r-1, c-1, r, c);
					//TODO
				}
			}
		}
	}
	
	public boolean isInBoard(int r, int c) {
		//Check if a position is valid in the board
		if(c < 0 || r < 0 || c >= BOARD_SIZE || r >= BOARD_SIZE){
			return false;
		}
		return true;
	}
	
	public boolean isEndCapped(Board current, int rr, int cr, int r, int c){		//rr=row relative; cr= column relative
		if(!isInBoard(r,c)){
			return false;
		}
		else if(current.board[r][c]==current.board[r+rr][c+cr]){
			return(isEndCapped(current,rr,cr,r+rr,c+cr));
		}
		else if(current.board[r][c]==SquareStatus.EMPTY){
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
		
		
		
		
		return false;
	}
}
