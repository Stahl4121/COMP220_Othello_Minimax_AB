/**
 * Position on board
 * @author MUMAWBM1
 *
 */
public class Move {
	private int row;
	private int col;
	private SquareStatus piece;


	
	public Move(int r,int c, SquareStatus piece){
	this.row=r;
	this.col=c;
	this.piece=piece;

	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public SquareStatus getColor(){
		return piece;
	}

}
