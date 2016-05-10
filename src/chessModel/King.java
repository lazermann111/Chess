package chessModel;

public class King extends Piece
{
	boolean canCastle;
    public King(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        this.side = side;
        canCastle = true;
    }
    public String getChar(){
        if(side==0)
            return "K";
        return "k";
    }
    public boolean move(int x, int y, SquareStatus status){
    	if(validMove(x, y, status)){
    		this.x = x;
    		this.y = y;
    		canCastle = false;
    		return true;
    	}
    	return false;
    }
    public boolean validMove(int x, int y, SquareStatus status){
        return (Math.abs(this.x-x)<=1 && Math.abs(this.y-y)<=1);
    }
    public int getValue(){
    	return 1000;
    }
}
