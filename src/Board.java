
public class Board {
	public char[][] board;
	
	public Board(){			//original
		board=new char[8][8];
	}
	
	public Board(Board other){
		for(int r=0;r<8;r++){
			for(int c=0;c<8;c++){
				this.board[r][c]= other.board[r][c];
			}
		}
	}
	
	public void flipPieces(Board current, Move move){
		for(int r=move.getRow()-1;r<=(move.getRow()+1);r++){
			for(int c=move.getCol()-1;c<=(move.getCol()+1);c++){
				//Im in my room if'n you wanna come and work in the same room.
				if()
			}
		}
	}
}
