package extras;


import gameBoard.ChessBoard;
import gameBoard.ChessFrame;
import gameBoard.GridPoint;
import gamePieces.Piece;

/*
 * This class contains all the tools necessary for keeping a game log written in complete algebraic notation.  The main writer method contains the tools 
 * to write a regular move from one square to the other.  When the user castles, the writeCastle method is used instead of the regular write method because
 * in algebraic notation, the convention for writing castle is "O-O" for kingside and "O-O-O" for queenside.  There are also two additional methods used
 * to append certain characters onto the end of the notation.  The first one, writeCheck deals with the addition of "+" for check and "#" for checkmate.
 * The second one addresses the special case where a pawn is promoted to another piece and in algebraic notation, this is shown by appending the letter 
 * which represents the piece to the end of the notation.
 */
public class NotationWriter
{
	/*
	 * the piece to move is used as a parameter in the method and not simply taken from the from position because when the user "holds" the piece,
	 * the piece is removed from the from GridPoint and is stored in the firstPieceClicked variable.
	 */
	public static void write(Piece pieceToMove,GridPoint from, GridPoint to)
	{		
		int rowToWrite = ChessFrame.jtbl.getRowCount() - 1; 
		
		//if it is the first row OR notation has not been written already (which happens in the case of castling) then write the notation
		if((ChessFrame.jtbl.getRowCount() == 0) || 
		(pieceToMove.getColor().equals(Piece.WHITE) && !ChessFrame.jtbl.getValueAt(rowToWrite, 2).equals("")) || 
		(pieceToMove.getColor().equals(Piece.BLACK) && ChessFrame.jtbl.getValueAt(rowToWrite, 2).equals("")))
		{
			if(pieceToMove.getColor().equals(Piece.WHITE)) //since the white notation is the first in the row, a new row needs to be added for a new white notation
			{
				Object[] newRow = {ChessFrame.jtbl.getRowCount() + 1,"",""};
				ChessFrame.jtblModel.addRow(newRow);
			}
			
			int colToWrite;
			if(pieceToMove.getColor().equals(Piece.WHITE)) colToWrite = 1;
			else colToWrite = 2;
			
			//update the view of the scroll pane to be the current turn
			ChessFrame.jtbl.scrollRectToVisible(ChessFrame.jtbl.getCellRect(ChessFrame.jtbl.getRowCount()-1, 0, true));
			
			//get the letter that represents the piece that will be moved
			char pieceLetter = getPieceLetter(pieceToMove);
			
			//get the initial position of the piece 
			char fileFrom = (char)('a' + from.getY());
			int rankFrom  = 8 - from.getX();
			String fromPos = "" + fileFrom + rankFrom;
			
			//get the separating character ('-' if simple move, 'x' if capture)
			char sep;
			if(ChessBoard.boardArray[to.getX()][to.getY()].getIcon() != null) sep = 'x';
			else sep = '-';
			
			//get the final position of the piece
			char fileTo = (char)('a' + to.getY());
			int rankTo  = 8 - to.getX();
			String toPos = "" + fileTo + rankTo;
			
			//construct the notation
			String notation = pieceLetter + fromPos + sep + toPos;
			
			//write the notation to the table model
			ChessFrame.jtblModel.setValueAt(notation, ChessFrame.jtbl.getRowCount()-1, colToWrite);
		}
	}
	
	/*
	 * castling is simple in that the notation writer simply needs to know if the move is king or queen side and what color is being castled (for the JTable).
	 * In the parameter "side", 'k' represents a kingside castle and 'q' represents a queenside castle;
	 */
	public static void writeCastle(String color,char side)
	{
		if(color.equals(Piece.WHITE)) //since the white notation is the first in the row, a new row needs to be added for a new white notation
		{
			Object[] newRow = {ChessFrame.jtbl.getRowCount() + 1,"",""};
			ChessFrame.jtblModel.addRow(newRow);
		}
		
		//update the view of the scroll pane to be the current turn
		ChessFrame.jtbl.scrollRectToVisible(ChessFrame.jtbl.getCellRect(ChessFrame.jtbl.getRowCount()-1, 0, true));
		
		int colToWrite;
		if(color.equals(Piece.WHITE)) colToWrite = 1;
		else colToWrite = 2;
		
		if(side == 'q') ChessFrame.jtbl.setValueAt("O-O-O", ChessFrame.jtblModel.getRowCount()-1, colToWrite);
		else ChessFrame.jtbl.setValueAt("O-O", ChessFrame.jtblModel.getRowCount()-1, colToWrite);
	}
	
	/*
	 * This appends a "+" or "#" to the notation depending on whether it is check or mate, respectively.
	 * In the parameter, 'c' represents a check and 'm' represents mate.
	 */
	public static void writeCheck(String color,char checkOrMate)
	{
		int colToWrite;
		if(color.equals(Piece.WHITE)) colToWrite = 1;
		else colToWrite = 2;
		
		int rowToWrite = ChessFrame.jtblModel.getRowCount()-1;
		
		if(checkOrMate == 'c') ChessFrame.jtblModel.setValueAt(ChessFrame.jtblModel.getValueAt(rowToWrite, colToWrite) + "+", rowToWrite, colToWrite);
		else ChessFrame.jtblModel.setValueAt(ChessFrame.jtblModel.getValueAt(rowToWrite, colToWrite) + "#", rowToWrite, colToWrite);
	}
	
	/*
	 * This appends the appropriate piece letter to the notation, depending on the given promotion piece.
	 */
	public static void writePromotion(Piece promotionPiece)
	{
		int colToWrite;
		if(promotionPiece.getColor().equals(Piece.WHITE)) colToWrite = 1;
		else colToWrite = 2;
		
		int rowToWrite = ChessFrame.jtblModel.getRowCount()-1;
		
		ChessFrame.jtblModel.setValueAt(ChessFrame.jtblModel.getValueAt(rowToWrite, colToWrite) + ("" + getPieceLetter(promotionPiece)), rowToWrite, colToWrite);
	}
	
	/*
	 * This method contains the letters that represent certain pieces based on algebraic notation convention.  The switch/case contains every piece except for
	 * the pawn, so if the piece given is a pawn, an empty character is returned.  This works well for algebraic notation because by convention, the
	 * pawn has no letter.  For example moving a pawn one space forward would be " c2-c3". 
	 */
	public static char getPieceLetter(Piece pieceToMove)
	{
		char letter = 0;
		
		switch(pieceToMove.getClass().getSimpleName())
		{
			case "King":
				letter = 'K';
				break;
			case "Bishop":
				letter = 'B';
				break;
			case "Queen":
				letter = 'Q';
				break;
			case "Castle":
				letter = 'R';
				break;
			case "Knight":
				letter = 'N';
				break;
		}
		
		return letter;
	}
}
