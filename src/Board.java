
public class Board {
	public char[][] board;
	
	public Board(){			//original
		board=new char[8][8];
	}
	
	public Board(Board board){
		for(int r=0;r<8;r++){
			for(int c=0;c<8;c++){
				this.board[r][c]= other.board[r][c];
			}
		}
	}
}
