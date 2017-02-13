package gameBoard;

import gamePieces.Castle;
import gamePieces.King;
import gamePieces.Pawn;
import gamePieces.Piece;
import saveLoad.SaveProtocol;

/*
 * This class creates chess move objects.  This means that it packages information that would otherwise be lost 
 * into objects which will be stored in a Stack.
 */
public class Move
{
	//the variables that track a regular move
	private GridPoint from;
	private GridPoint to;
	private Piece pieceMoved;
	private Piece pieceTaken;
	
	//the variables that help track an en passant move
	private Pawn ep;
	private GridPoint epPos;
	
	//the variables that help track moves for castling
	private Boolean moved;
	private Castle castle;
	private char side;
	
	//This constructor stores a simple move or attack
	public Move(GridPoint from, GridPoint to)
	{
		pieceMoved = Piece.firstPieceClicked;
		pieceTaken = (Piece) ChessBoard.boardArray[to.getX()][to.getY()].getIcon();
		
		this.from = from;
		this.to   = to; 
	}
	
	//This constructor stores an pawn move in which it performed an en passant
	public Move(GridPoint from, GridPoint to,Pawn pawn,GridPoint pawnPosition)
	{
		pieceMoved = Piece.firstPieceClicked;
		pieceTaken = (Piece) ChessBoard.boardArray[to.getX()][to.getY()].getIcon();
		
		this.from = from;
		this.to   = to; 
		
		this.ep    = pawn;
		this.epPos = pawnPosition;
	}
	
	//This constructor stores a king move (given its own constructor for castling)
	public Move(King king,GridPoint from, GridPoint to)
	{
		this(from,to);
		this.moved = king.moved;
	}
	
	//This constructor stores a castle move (given its own constructor for castling)
	public Move(Castle castle,GridPoint from, GridPoint to)
	{
		this(from,to);
		this.moved = castle.moved;
	}
	
	//This constructor stores a castle move
	public Move(King king,GridPoint from, GridPoint to,Piece castle,char side)
	{
		this(from,to);
		this.moved = king.moved;
		
		this.castle    = (Castle)castle;
		this.side = side;
	}
	
	//This all-in-one method undoes any previous move by utilizing the move stack in the ChessBoard class
	public void undo()
	{
		SaveProtocol.setEdited();
		
		ChessBoard.boardArray[from.getX()][from.getY()].setIcon(pieceMoved); 
		ChessBoard.boardArray[to.getX()][to.getY()].setIcon(pieceTaken);
		
		//return the piece that was taken off in the en passant
		if(ep != null) ChessBoard.boardArray[epPos.getX()][epPos.getY()].setIcon(ep);
		
		if(moved != null) //restore the piece to its original moved status
		{
			if(pieceMoved.getClass().getSimpleName().equals("King")) 
			{
				((King)pieceMoved).moved = moved;
				//update king position
				if(pieceMoved.getColor().equals(Piece.BLACK)) Piece.blackKingPosition.updatePosition(to.getX(), to.getY());
				else Piece.whiteKingPosition.updatePosition(to.getX(), to.getY());
			}
			if(pieceMoved.getClass().getSimpleName().equals("Castle")) ((Castle)pieceMoved).moved = moved;
		}
		
		if(castle != null) //restore the castle to its original position
		{
			int row;
			if(castle.getColor().equals(Piece.WHITE)) row = ButtonActions.SEVEN_INDEX;
			else row = ButtonActions.ZERO_INDEX;
			
			if(side == 'k') 
			{
				ChessBoard.boardArray[row][ButtonActions.FIVE_INDEX].setIcon(null);
				ChessBoard.boardArray[row][ButtonActions.SEVEN_INDEX].setIcon(castle);
			}
			else
			{
				ChessBoard.boardArray[row][ButtonActions.TWO_INDEX].setIcon(null);
				ChessBoard.boardArray[row][ButtonActions.ZERO_INDEX].setIcon(castle);
			}
		}
		
		//update the notation JTable
		if(pieceMoved.getColor().equals(Piece.BLACK)) ChessFrame.jtbl.setValueAt("", ChessFrame.jtbl.getRowCount()-1, 2);
		else ChessFrame.jtblModel.removeRow(ChessFrame.jtbl.getRowCount()-1);
		
		//update the title
		if(Piece.kingIsInCheck(pieceMoved.getColor())) 
		{
			ChessFrame.jfrm.setTitle("My Chess Game (" + pieceMoved.getColor() + " in CHECK to move...) -Edited");
		}
		else ChessFrame.jfrm.setTitle("My Chess Game (" + pieceMoved.getColor() + " to move...) -Edited");
		
		//reset the turn
		ButtonActions.enableAllColor(pieceMoved.getColor());
		ChessFrame.jpnl.repaint();
	}
}
