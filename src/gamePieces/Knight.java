package gamePieces;

import gameBoard.ButtonActions;
import gameBoard.ChessBoard;
import gameBoard.GridPoint;

@SuppressWarnings("serial")
public class Knight extends Piece
{
	
	private int top_Out_Of_Bounds_Two_Spaces;
	private int bottom_Out_Of_Bounds_Two_Spaces;
	private int left_Out_Of_Bounds_Two_Spaces;
	private int right_Out_Of_Bounds_Two_Spaces;
	
	public Knight(String pieceColor)
	{
		super(pieceColor + "Knight.png");
		
		this.pieceColor = pieceColor;
		
		setColorMovements();
		
		if (pieceColor.equals(Piece.BLACK))
		{
			top_Out_Of_Bounds_Two_Spaces    = NINE_INDEX;
			bottom_Out_Of_Bounds_Two_Spaces = NEGATIVE_TWO_INDEX;
			left_Out_Of_Bounds_Two_Spaces   = NINE_INDEX;
			right_Out_Of_Bounds_Two_Spaces  = NEGATIVE_TWO_INDEX;
		}
		
		else
		{	
			top_Out_Of_Bounds_Two_Spaces    = NEGATIVE_TWO_INDEX;
			bottom_Out_Of_Bounds_Two_Spaces = NINE_INDEX;
			left_Out_Of_Bounds_Two_Spaces   = NEGATIVE_TWO_INDEX;
			right_Out_Of_Bounds_Two_Spaces  = NINE_INDEX;
		}
	}

	@Override
	public void enablePotentialMoves(boolean enabled, int row, int col) 
	{
		if(row + forward_Row != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds_Two_Spaces &&  col + right_Col != right_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*forward_Row)][col + right_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*forward_Row)][col + right_Col].getIcon())) 
			{
				ChessBoard.boardArray[row + (2*forward_Row)][col + right_Col].setEnabled(enabled);
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds_Two_Spaces &&  col + left_Col != left_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*forward_Row)][col + left_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*forward_Row)][col + left_Col].getIcon())) 
			{
				ChessBoard.boardArray[row + (2*forward_Row)][col + left_Col].setEnabled(enabled);
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + forward_Row][col + (2*left_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + forward_Row][col + (2*left_Col)].getIcon())) 
			{
				ChessBoard.boardArray[row + forward_Row][col + (2*left_Col)].setEnabled(enabled);
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + forward_Row][col + (2*right_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + forward_Row][col + (2*right_Col)].getIcon())) 
			{
				ChessBoard.boardArray[row + forward_Row][col + (2*right_Col)].setEnabled(enabled);
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds_Two_Spaces &&  col + right_Col != right_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*backward_Row)][col + right_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*backward_Row)][col + right_Col].getIcon())) 
			{
				ChessBoard.boardArray[row + (2*backward_Row)][col + right_Col].setEnabled(enabled);
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds_Two_Spaces &&  col + left_Col != left_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*backward_Row)][col + left_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*backward_Row)][col + left_Col].getIcon())) 
			{
				ChessBoard.boardArray[row + (2*backward_Row)][col + left_Col].setEnabled(enabled);
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + backward_Row][col + (2*left_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + backward_Row][col + (2*left_Col)].getIcon())) 
			{
				ChessBoard.boardArray[row + backward_Row][col + (2*left_Col)].setEnabled(enabled);
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + backward_Row][col + (2*right_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + backward_Row][col + (2*right_Col)].getIcon())) 
			{
				ChessBoard.boardArray[row + backward_Row][col + (2*right_Col)].setEnabled(enabled);
			}
		}
	}

	@Override
	public boolean canMoveWithoutCheck(boolean enabled,int row, int col)
	{
		GridPoint from = new GridPoint(row,col);
		int[][] holderArray = new int[8][8];
		boolean blockable = false;
		
		if(row + forward_Row != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds_Two_Spaces &&  col + right_Col != right_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*forward_Row)][col + right_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*forward_Row)][col + right_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + (2*forward_Row),col + right_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds_Two_Spaces &&  col + left_Col != left_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*forward_Row)][col + left_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*forward_Row)][col + left_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + (2*forward_Row),col + left_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + forward_Row][col + (2*left_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + forward_Row][col + (2*left_Col)].getIcon())) 
			{
				GridPoint to = new GridPoint(row + forward_Row,col + (2*left_Col));
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + forward_Row][col + (2*right_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + forward_Row][col + (2*right_Col)].getIcon())) 
			{
				GridPoint to = new GridPoint(row + forward_Row,col + (2*right_Col));
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds_Two_Spaces &&  col + right_Col != right_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*backward_Row)][col + right_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*backward_Row)][col + right_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + (2*backward_Row),col + right_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds_Two_Spaces &&  col + left_Col != left_Out_Of_Bounds)
		{
			if (ChessBoard.boardArray[row + (2*backward_Row)][col + left_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + (2*backward_Row)][col + left_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + (2*backward_Row),col + left_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + backward_Row][col + (2*left_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + backward_Row][col + (2*left_Col)].getIcon())) 
			{
				GridPoint to = new GridPoint(row + backward_Row,col + (2*left_Col));
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds_Two_Spaces)
		{
			if (ChessBoard.boardArray[row + backward_Row][col + (2*right_Col)].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + backward_Row][col + (2*right_Col)].getIcon())) 
			{
				GridPoint to = new GridPoint(row + backward_Row,col + (2*right_Col));
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		ChessBoard.boardArray[row][col].setEnabled(true);
		ButtonActions.enableArrayOnes(enabled,holderArray);

		return blockable;
	}

	@Override
	public void enableOverride(boolean enabled, int row, int col)
	{
		if(row + forward_Row != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds_Two_Spaces &&  col + right_Col != right_Out_Of_Bounds)
		{
			ChessBoard.boardArray[row + (2*forward_Row)][col + right_Col].setEnabled(enabled);
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds && row + (2*forward_Row) != top_Out_Of_Bounds_Two_Spaces &&  col + left_Col != left_Out_Of_Bounds)
		{
			ChessBoard.boardArray[row + (2*forward_Row)][col + left_Col].setEnabled(enabled);
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds_Two_Spaces)
		{
			ChessBoard.boardArray[row + forward_Row][col + (2*left_Col)].setEnabled(enabled);
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds_Two_Spaces)
		{
			ChessBoard.boardArray[row + forward_Row][col + (2*right_Col)].setEnabled(enabled);
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds_Two_Spaces &&  col + right_Col != right_Out_Of_Bounds)
		{
			ChessBoard.boardArray[row + (2*backward_Row)][col + right_Col].setEnabled(enabled);
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds && row + (2*backward_Row) != bottom_Out_Of_Bounds_Two_Spaces &&  col + left_Col != left_Out_Of_Bounds)
		{
			ChessBoard.boardArray[row + (2*backward_Row)][col + left_Col].setEnabled(enabled);
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds && col + (2*left_Col) != left_Out_Of_Bounds_Two_Spaces)
		{
			ChessBoard.boardArray[row + backward_Row][col + (2*left_Col)].setEnabled(enabled);
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds && col + (2*right_Col) != right_Out_Of_Bounds_Two_Spaces)
		{
			ChessBoard.boardArray[row + backward_Row][col + (2*right_Col)].setEnabled(enabled);
		}
	}
	
}
