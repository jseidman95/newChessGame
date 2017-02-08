package gamePieces;

import javax.swing.JButton;

import extras.NotationWriter;
import gameBoard.ButtonActions;
import gameBoard.ChessBoard;
import gameBoard.ChessFrame;
import gameBoard.GridPoint;
import gameBoard.Move;

@SuppressWarnings("serial")
public class Pawn extends Piece
{
	private int start_Line;
	public boolean justJumpedTwo;
	
	public Pawn(String pieceColor)
	{
		super(pieceColor + "Pawn.png");
		this.pieceColor = pieceColor;
		
		setColorMovements();
		setStartLine();
		justJumpedTwo = false;
	}

	private void setStartLine()
	{
		if(pieceColor == Piece.BLACK) start_Line = ButtonActions.ONE_INDEX;
		else start_Line = ButtonActions.SIX_INDEX;
	}
	
	@Override
	public void enablePotentialMoves(boolean enabled, int row, int col) 
	{	
		//if pawn is in the starting row, it can move two spaces (if there is no piece in front of it and two spaces in front)
		if(row == start_Line && ChessBoard.boardArray[row + forward_Row][col].getIcon() == null && ChessBoard.boardArray[row + (forward_Row * 2)][col].getIcon() == null) 
		{	
			ChessBoard.boardArray[row + (forward_Row * 2)][col].setEnabled(enabled);
		}
	
		//if there is no piece in front of it, the pawn can move ahead one piece
		if (ChessBoard.boardArray[row + forward_Row][col].getIcon() == null) 
		{
			ChessBoard.boardArray[row + forward_Row][col].setEnabled(enabled);	
		}
		//if there is a piece adjacent (on the left side) the pawn can take it off
		if ((col + left_Col != left_Out_Of_Bounds) && this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + left_Col].getIcon())) 
		{
			ChessBoard.boardArray[row + forward_Row][col + left_Col].setEnabled(enabled);
		}

		//if there is a piece adjacent (on the right side) the pawn can take it off
		if ((col + right_Col != right_Out_Of_Bounds) && this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + right_Col].getIcon())) 
		{
			ChessBoard.boardArray[row + forward_Row][col + right_Col].setEnabled(enabled);
		}
	}
	
	@Override
	public void enableOverride(boolean enabled, int row, int col)
	{
		if(col + left_Col  != left_Out_Of_Bounds)  ChessBoard.boardArray[row + forward_Row][col + left_Col].setEnabled(enabled);
		if(col + right_Col != right_Out_Of_Bounds) ChessBoard.boardArray[row + forward_Row][col + right_Col].setEnabled(enabled);
	}

	@Override
	public boolean canMoveWithoutCheck(boolean enabled,int row, int col)
	{
		GridPoint from = new GridPoint(row,col);
		int[][] holderArray = new int[8][8];
		boolean blockable = false;
		
		//if pawn is in the starting row, it can move two spaces (if there is no piece in front of it and two spaces in front)
		if(row == start_Line && ChessBoard.boardArray[row + forward_Row][col].getIcon() == null && ChessBoard.boardArray[row + (forward_Row * 2)][col].getIcon() == null) 
		{	
			GridPoint to = new GridPoint(row + (forward_Row * 2),col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
	
		//if there is no piece in front of it, the pawn can move ahead one piece
		if (ChessBoard.boardArray[row + forward_Row][col].getIcon() == null) 
		{
			GridPoint to = new GridPoint(row + forward_Row,col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		//if there is a piece adjacent (on the left side) the pawn can take it off
		if ((col + left_Col != left_Out_Of_Bounds) && this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + left_Col].getIcon())) 
		{
			GridPoint to = new GridPoint(row + forward_Row,col + left_Col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}

		//if there is a piece adjacent (on the right side) the pawn can take it off
		if ((col + right_Col != right_Out_Of_Bounds) && this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + right_Col].getIcon())) 
		{
			GridPoint to = new GridPoint(row + forward_Row,col + right_Col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		//en passant
		if ((col + left_Col != left_Out_Of_Bounds) && this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + left_Col].getIcon())) 
		{
			Piece maybePawn = (Piece)ChessBoard.boardArray[row][col + left_Col].getIcon();
			if(maybePawn.getClass().getSimpleName().equals("Pawn"))
			{
				if(((Pawn)maybePawn).justJumpedTwo)
				{
					GridPoint to = new GridPoint(row + forward_Row,col + left_Col);
					if(avoidsCheck(from,to,holderArray)) blockable = true;
				}
			}
			
		}

		//en passant		
		if ((col + right_Col != right_Out_Of_Bounds) && this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + right_Col].getIcon())) 
		{
			Piece maybePawn = (Piece)ChessBoard.boardArray[row][col + right_Col].getIcon();
			if(maybePawn.getClass().getSimpleName().equals("Pawn"))
			{
				if(((Pawn)maybePawn).justJumpedTwo)
				{
					GridPoint to = new GridPoint(row + forward_Row,col + right_Col);
					if(avoidsCheck(from,to,holderArray)) blockable = true;
				}
			}
		}
		
		ChessBoard.boardArray[row][col].setEnabled(true);
		ButtonActions.enableArrayOnes(enabled,holderArray);

		return blockable;
	}

	@Override
	public void placePieceAt(JButton secondButtonClicked)
	{
		GridPoint from  = new GridPoint(getRow(firstButtonClicked),getCol(firstButtonClicked));
		GridPoint to    = new GridPoint(getRow(secondButtonClicked),getCol(secondButtonClicked));
		
		if((this.getColor().equals(Piece.WHITE) && (getRow(Piece.firstButtonClicked) == ButtonActions.ONE_INDEX)) || (this.getColor().equals(Piece.BLACK) && (getRow(Piece.firstButtonClicked) == ButtonActions.SIX_INDEX))) 
		{	
			Piece.undoStack.push(new Move(from,to));
			NotationWriter.write(firstPieceClicked,from,to);
			
			ChessFrame.pcd.setPieceColors(firstPieceClicked);
			ChessFrame.pcd.setSecondButtonPosition(secondButtonClicked.getLocationOnScreen());
			ChessFrame.pcd.setVisible(true);
			
			NotationWriter.writePromotion(firstPieceClicked);
		}	
		else
		{
			justJumpedTwo = getCol(Piece.firstButtonClicked) == getCol(secondButtonClicked) && (getRow(Piece.firstButtonClicked) + (2 * forward_Row)) == getRow(secondButtonClicked);
			
			if(getCol(Piece.firstButtonClicked) + right_Col == getCol(secondButtonClicked) && getRow(Piece.firstButtonClicked) + forward_Row == getRow(secondButtonClicked))
			{
				if(ChessBoard.boardArray[getRow(secondButtonClicked)][getCol(secondButtonClicked)].getIcon() == null) 
				{
					Pawn taken = (Pawn)ChessBoard.boardArray[getRow(Piece.firstButtonClicked)][getCol(Piece.firstButtonClicked) + right_Col].getIcon();
					Piece.undoStack.push(new Move(from,to,taken,new GridPoint(getRow(Piece.firstButtonClicked),getCol(secondButtonClicked))));
					ChessBoard.boardArray[getRow(Piece.firstButtonClicked)][getCol(Piece.firstButtonClicked) + right_Col].setIcon(null);
				}
			}
			else if(getCol(Piece.firstButtonClicked) + left_Col == getCol(secondButtonClicked) && getRow(Piece.firstButtonClicked) + forward_Row == getRow(secondButtonClicked))
			{
				if(ChessBoard.boardArray[getRow(secondButtonClicked)][getCol(secondButtonClicked)].getIcon() == null) 
				{
					Pawn taken = (Pawn)ChessBoard.boardArray[getRow(Piece.firstButtonClicked)][getCol(Piece.firstButtonClicked) + left_Col].getIcon();
					Piece.undoStack.push(new Move(from,to,taken,new GridPoint(getRow(Piece.firstButtonClicked),getCol(secondButtonClicked))));
					ChessBoard.boardArray[getRow(Piece.firstButtonClicked)][getCol(Piece.firstButtonClicked) + left_Col].setIcon(null);
				}
			}
		}
		
		super.placePieceAt(secondButtonClicked);
	}
	
}
