/**
 * Position on board
 * @author MUMAWBM1
 *
 */
public class Move {
	private int row;
	private int col;
	private SquareStatus sqStat;


	
	public Move(int r,int c, SquareStatus piece){
	row=r;
	col=c;
	sqStat=piece;

	}
	
	public Move(Move m){
		this.row=m.row;
		this.col=m.col;
		this.sqStat=m.sqStat;
		
	}
	
	public Move(){
		row=-1;
		col=-1;
		sqStat=SquareStatus.EMPTY;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public SquareStatus getColor(){
		return sqStat;
	}

}
