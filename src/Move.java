import java.util.ArrayList;

/**
 * Position on board
 * @author MUMAWBM1
 *
 */
public class Move {
	private int row;
	private int col;
	private ArrayList<Integer> coor;//coor(0)=row; coor(1)=col;
	
	int Foo;
	
	public Move(int r,int c){
	this.row=r;
	this.col=c;
	coor=new ArrayList<>();
	coor.add(row);
	coor.add(col);
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public ArrayList<Integer> getCoor(){
		return coor;
	}
}
