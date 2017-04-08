import java.util.HashMap;
import java.util.Map;

public class AI {

	int depth=8;
	
	public int getDepth(){
		return depth;
	}
	public void setDepth(int d){
		depth=d;
	}
	
	public Move aiMove(Board current){
		Move m= new Move();
		HashMap<Move, Integer> move = new HashMap<Move, Integer>();
		move.putAll(max(current,0,m));		
		for(int r=0; r<current.BOARD_SIZE; r++){
			for(int c=0; c<current.BOARD_SIZE;c++){
				Move query = new Move(r, c, SquareStatus.WHITE);
				if(move.containsKey(query)){
					m=new Move(query);
				}
			}
		}
		return m;
		
	}

	public Map<Move, Integer> max(Board iter, int n , Move m){
		if(n==depth){
			HashMap<Move, Integer> score = new HashMap<Move,Integer>();
			score.put(m, iter.getNumTiles(SquareStatus.WHITE));
			return score;
		}
		Board copy = new Board(iter);
		Map<Move, Integer> currentMax = new HashMap<Move, Integer>();
		if(n==0){
			currentMax.put(m, -9999999);
		}
		Move currentMaxMove;
		if(m.getColor()==SquareStatus.EMPTY){
			currentMaxMove=null;
		}
		else{
			currentMaxMove= new Move(m);
		}
		for(int r=0;r<copy.BOARD_SIZE;r++){
			for(int c=0;c<copy.BOARD_SIZE;c++){
				if (copy.getBoard(r,c)==SquareStatus.EMPTY){
					Move move = new Move(r,c,SquareStatus.WHITE);
					if(copy.isLegalMove(move)){
						try{
							copy.makeMove(move);
						}
						catch(Exception e){
							e.getMessage();
							break;
						}
						if(n==0){
							currentMax.clear();
							currentMaxMove = new Move(move);
						}
						if(currentMax.isEmpty()){
							Map<Move, Integer> tempMax = new HashMap<Move, Integer>();
							tempMax.putAll(min(copy, n+1, move)); 
							currentMax.replace(currentMaxMove, tempMax.get(move));
						}
						else{
							Map<Move, Integer> tempMax = new HashMap<Move, Integer>();
							tempMax.putAll(min(copy, n+1, m));
							if(currentMax.get(currentMaxMove)<tempMax.get(move)){
								currentMax.replace(currentMaxMove, tempMax.get(move));
							}
						}
					}
				}
			}
		}
		return currentMax;
	}

	public Map<Move, Integer> min(Board iter, int n, Move m){
		if(n==depth){
			HashMap<Move, Integer> score = new HashMap<Move, Integer>();
			score.put(m, iter.getNumTiles(SquareStatus.WHITE));
			return score;
		}
		Board copy = new Board(iter);
		Map<Move, Integer> currentMin = new HashMap<Move, Integer>();
		Move currentMinMove=new Move(m);
		for(int r=0;r<copy.BOARD_SIZE;r++){
			for(int c=0;c<copy.BOARD_SIZE;c++){
				if (copy.getBoard(r,c)==SquareStatus.EMPTY){
					Move move = new Move(r,c,SquareStatus.WHITE);
					if(copy.isLegalMove(move)){
						try{
							copy.makeMove(move);
						}
						catch(Exception e){
							e.getMessage();
							break;
						}
						if(currentMin.isEmpty()){
							Map<Move, Integer> tempMax = new HashMap<Move, Integer>();
							tempMax.putAll(min(copy, n+1, move)); 
							currentMin.replace(currentMinMove, tempMax.get(move));
						}
						else{
							Map<Move, Integer> tempMax = new HashMap<Move, Integer>();
							tempMax.putAll(min(copy, n+1, m));
							if(currentMin.get(currentMinMove)>tempMax.get(m)){
								currentMin.replace(currentMinMove, tempMax.get(move));
							}
						}
					}
				}
			}
		}
		return currentMin;
	}
}
