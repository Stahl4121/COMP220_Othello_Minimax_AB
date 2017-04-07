import java.util.ArrayList;
import java.util.Collections;

public class AI {

	int depth=8;
	
	public Move aiMove(Board current){
		max(current,0);
	}

	public int max(Board iter, int n){
		if(n==depth){
			return iter.numTilesOfColor(SquareStatus.WHITE);
		}
		Board copy = new Board(iter);
		ArrayList<Integer> moves = new ArrayList<>();
		for(int r=0;r<8;r++){
			for(int c=0;c<8;c++){
				if (copy.board[r][c]==SquareStatus.EMPTY){
					Move move = new Move(r,c,SquareStatus.WHITE);
					if(copy.isLegalMove(move)){
						copy.makeMove(move);
						moves.add(min(copy,n++));
					}
				}
			}
		}
		Collections.sort(moves);
		return moves.get(moves.size()-1);
	}
	
	public int min(Board iter, int n){
		if(n==depth){
			return iter.numTilesOfColor(SquareStatus.EMPTY);
		}
		Board copy = new Board(iter);
		ArrayList<Integer> moves = new ArrayList<>();
		for(int r=0;r<8;r++){
			for(int c=0;c<8;c++){
				if (copy.board[r][c]==SquareStatus.EMPTY){
					Move move = new Move(r,c,SquareStatus.WHITE);
					if(copy.isLegalMove(move)){
						copy.makeMove(move);
						return(min(copy,n++));
					}
				}
			}
		}
		Collections.sort(moves);
		return moves.get(0);
	}
	
}
