package gamePieces;

import gameBoard.ButtonActions;
import gameBoard.ChessBoard;
import gameBoard.GridPoint;

@SuppressWarnings("serial")
public class Bishop extends Piece
{
	public Bishop(String pieceColor)
	{
		super(pieceColor + "Bishop.png");
		this.pieceColor = pieceColor;
		
		setColorMovements();
	}

	@Override
	public void enablePotentialMoves(boolean enabled, int row, int col)
	{
		enableUpRight(enabled,row,col);
		enableUpLeft(enabled,row,col);
		enableDownLeft(enabled,row,col);
		enableDownRight(enabled,row,col);
	}

	private void enableUpRight(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(row+(forward_Row * i) != top_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row+(forward_Row * i)][col + (right_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col + (right_Col*i)].setEnabled(enabled);
			i++;
		}
		
		if(row+(forward_Row * i) != top_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (forward_Row * i)][col + (right_Col*i)].getIcon())) 
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col + (right_Col*i)].setEnabled(enabled);
		}
	}
	
	private void enableUpLeft(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(row+(forward_Row * i) != top_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row+(forward_Row * i)][col + (left_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
			i++;
		}
		
		if(row+(forward_Row * i) != top_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (forward_Row * i)][col + (left_Col*i)].getIcon())) 
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
		}
	}
	
	private void enableDownRight(boolean enabled, int row, int col)
	{

		int i = ButtonActions.ONE_INDEX;
		
		while(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row+(backward_Row * i)][col + (right_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col + (right_Col*i)].setEnabled(enabled);
			i++;
		}
		if(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && this.shouldHighlight((Piece) ChessBoard.boardArray[row + (backward_Row * i)][col + (right_Col*i)].getIcon())) 
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col + (right_Col*i)].setEnabled(enabled);
		}
	}
	
	private void enableDownLeft(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row+(backward_Row * i)][col + (left_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
			i++;
		}
		
		if(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (backward_Row * i)][col + (left_Col*i)].getIcon())) 
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
		}
	}
	
	@Override
	public void enableOverride(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(row+(forward_Row * i) != top_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row+(forward_Row * i)][col + (right_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col + (right_Col*i)].setEnabled(enabled);
			i++;
		}
		
		if(row+(forward_Row * i) != top_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds) ChessBoard.boardArray[row + (forward_Row * i)][col + (right_Col*i)].setEnabled(enabled);
		
		i = ButtonActions.ONE_INDEX;
		
		while(row+(forward_Row * i) != top_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row+(forward_Row * i)][col + (left_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
			i++;
		}
		
		if(row+(forward_Row * i) != top_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds) ChessBoard.boardArray[row + (forward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
		
		i = ButtonActions.ONE_INDEX;
		
		while(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row+(backward_Row * i)][col + (right_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col + (right_Col*i)].setEnabled(enabled);
			i++;
		}
		if(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds) ChessBoard.boardArray[row + (backward_Row * i)][col + (right_Col*i)].setEnabled(enabled);

		i = ButtonActions.ONE_INDEX;
		
		while(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row+(backward_Row * i)][col + (left_Col * i)].getIcon() ==  null)
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
			i++;
		}
		
		if(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds) ChessBoard.boardArray[row + (backward_Row * i)][col + (left_Col*i)].setEnabled(enabled);
	}

	@Override
	public boolean canMoveWithoutCheck(boolean enabled,int row, int col)
	{
		GridPoint from = new GridPoint(row,col);
		int[][] holderArray = new int[8][8];
		boolean blockable = false;
		
		int i = ButtonActions.ONE_INDEX;
		
		while(row+(forward_Row * i) != top_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row+(forward_Row * i)][col + (right_Col * i)].getIcon() ==  null)
		{
			GridPoint to = new GridPoint(row + (forward_Row * i),col + (right_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
			i++;
		}
		if(row+(forward_Row * i) != top_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (forward_Row * i)][col + (right_Col*i)].getIcon())) 
		{
			GridPoint to = new GridPoint(row + (forward_Row * i),col + (right_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		i = ButtonActions.ONE_INDEX;
		
		while(row+(forward_Row * i) != top_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row+(forward_Row * i)][col + (left_Col * i)].getIcon() ==  null)
		{
			GridPoint to = new GridPoint(row + (forward_Row * i),col + (left_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;			
			i++;
		}
		if(row+(forward_Row * i) != top_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (forward_Row * i)][col + (left_Col*i)].getIcon())) 
		{
			GridPoint to = new GridPoint(row + (forward_Row * i),col + (left_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;	
		}
		
		i = ButtonActions.ONE_INDEX;
		
		while(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row+(backward_Row * i)][col + (right_Col * i)].getIcon() ==  null)
		{
			GridPoint to = new GridPoint(row + (backward_Row * i),col + (right_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
			i++;
		}
		if(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (right_Col * i) != right_Out_Of_Bounds && this.shouldHighlight((Piece) ChessBoard.boardArray[row + (backward_Row * i)][col + (right_Col*i)].getIcon())) 
		{
			GridPoint to = new GridPoint(row + (backward_Row * i),col + (right_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		i = ButtonActions.ONE_INDEX;
		
		while(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row+(backward_Row * i)][col + (left_Col * i)].getIcon() ==  null)
		{
			GridPoint to = new GridPoint(row + (backward_Row * i),col + (left_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
			i++;

		}
		if(row+(backward_Row * i) != bottom_Out_Of_Bounds && col + (left_Col * i) != left_Out_Of_Bounds && this.shouldHighlight((Piece) ChessBoard.boardArray[row + (backward_Row * i)][col + (left_Col*i)].getIcon())) 
		{
			GridPoint to = new GridPoint(row + (backward_Row * i),col + (left_Col*i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		ChessBoard.boardArray[row][col].setEnabled(true);
		ButtonActions.enableArrayOnes(enabled,holderArray);

		return blockable;
	}

	
}
