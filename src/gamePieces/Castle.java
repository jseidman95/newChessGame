package gamePieces;


import javax.swing.JButton;

import gameBoard.ButtonActions;
import gameBoard.ChessBoard;
import gameBoard.GridPoint;


@SuppressWarnings("serial")
public class Castle extends Piece
{
	public boolean moved;
	
	public Castle(String pieceColor)
	{
		super("src/resources/" + pieceColor + "Castle.png");
		
		this.pieceColor = pieceColor;
		
		setColorMovements();
		
		moved = false;
	}


	@Override
	public void enablePotentialMoves(boolean enabled, int row, int col) 
	{
		enableUp(enabled,row,col);
		enableDown(enabled,row,col);
		enableLeft(enabled,row,col);
		enableRight(enabled,row,col);
	}

	private void enableUp(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(row + (forward_Row * i) != top_Out_Of_Bounds && ChessBoard.boardArray[row + (forward_Row * i)][col].getIcon() == null) 
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col].setEnabled(enabled);
			i++;
		}
		if(row + (forward_Row * i) != top_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (forward_Row * i)][col].getIcon())) 
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col].setEnabled(enabled);
		}
	}
	
	private void enableDown(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(row + (backward_Row * i) != bottom_Out_Of_Bounds && ChessBoard.boardArray[row + (backward_Row * i)][col].getIcon() == null) 
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col].setEnabled(enabled);
			i++;
		}
		if(row + (backward_Row * i) != bottom_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (backward_Row * i)][col].getIcon())) 
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col].setEnabled(enabled);
		}
	}
	
	private void enableRight(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row][col + (right_Col * i)].getIcon() == null)
		{
			ChessBoard.boardArray[row][col + (right_Col * i)].setEnabled(enabled);
			i++;
		}
		if(col + (right_Col * i) != right_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + (right_Col * i)].getIcon())) 
		{
			ChessBoard.boardArray[row][col + (right_Col * i)].setEnabled(enabled);
		}
	}
	
	private void enableLeft(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row][col + (left_Col * i)].getIcon() == null)
		{
			ChessBoard.boardArray[row][col + (left_Col * i)].setEnabled(enabled);
			i++;
		}
		if(col + (left_Col * i) != left_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + (left_Col * i)].getIcon())) 
		{
			ChessBoard.boardArray[row][col + (left_Col * i)].setEnabled(enabled);
		}
	}
	
	@Override
	public void enableOverride(boolean enabled, int row, int col)
	{
		int i = ButtonActions.ONE_INDEX;
		
		while(row + (forward_Row * i) != top_Out_Of_Bounds && ChessBoard.boardArray[row + (forward_Row * i)][col].getIcon() == null) 
		{
			ChessBoard.boardArray[row + (forward_Row * i)][col].setEnabled(enabled);
			i++;
		}
		
		if(row + (forward_Row * i) != top_Out_Of_Bounds) ChessBoard.boardArray[row + (forward_Row * i)][col].setEnabled(enabled);

		i = ButtonActions.ONE_INDEX;
		
		while(row + (backward_Row * i) != bottom_Out_Of_Bounds && ChessBoard.boardArray[row + (backward_Row * i)][col].getIcon() == null) 
		{
			ChessBoard.boardArray[row + (backward_Row * i)][col].setEnabled(enabled);
			i++;
		}
		
		if(row + (backward_Row * i) != bottom_Out_Of_Bounds) ChessBoard.boardArray[row + (backward_Row * i)][col].setEnabled(enabled);
	
		i = ButtonActions.ONE_INDEX;
		
		while(col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row][col + (right_Col * i)].getIcon() == null)
		{
			ChessBoard.boardArray[row][col + (right_Col * i)].setEnabled(enabled);
			i++;
		}
		if(col + (right_Col * i) != right_Out_Of_Bounds) ChessBoard.boardArray[row][col + (right_Col * i)].setEnabled(enabled);

		i = ButtonActions.ONE_INDEX;
		
		while(col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row][col + (left_Col * i)].getIcon() == null)
		{
			ChessBoard.boardArray[row][col + (left_Col * i)].setEnabled(enabled);
			i++;
		}
		if(col + (left_Col * i) != left_Out_Of_Bounds) ChessBoard.boardArray[row][col + (left_Col * i)].setEnabled(enabled);
	}

	@Override
	public boolean canMoveWithoutCheck(boolean enabled,int row, int col)
	{
		GridPoint from = new GridPoint(row,col);
		int[][] holderArray = new int[8][8];
		boolean blockable = false;
		
		int i = ButtonActions.ONE_INDEX;
		
		while(row + (forward_Row * i) != top_Out_Of_Bounds && ChessBoard.boardArray[row + (forward_Row * i)][col].getIcon() == null) 
		{
			GridPoint to = new GridPoint(row + (forward_Row * i),col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
			i++;
		}
		if(row + (forward_Row * i) != top_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (forward_Row * i)][col].getIcon())) 
		{
			GridPoint to = new GridPoint(row + (forward_Row * i),col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		i = ButtonActions.ONE_INDEX;
		
		while(row + (backward_Row * i) != bottom_Out_Of_Bounds && ChessBoard.boardArray[row + (backward_Row * i)][col].getIcon() == null) 
		{
			GridPoint to = new GridPoint(row + (backward_Row * i),col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
			i++;
		}
		if(row + (backward_Row * i) != bottom_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row + (backward_Row * i)][col].getIcon())) 
		{
			GridPoint to = new GridPoint(row + (backward_Row * i),col);
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		i = ButtonActions.ONE_INDEX;
		
		while(col + (right_Col * i) != right_Out_Of_Bounds && ChessBoard.boardArray[row][col + (right_Col * i)].getIcon() == null)
		{			
			GridPoint to = new GridPoint(row,col + (right_Col * i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
			i++;
		}
		if(col + (right_Col * i) != right_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + (right_Col * i)].getIcon())) 
		{
			GridPoint to = new GridPoint(row,col + (right_Col * i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}
		
		i = ButtonActions.ONE_INDEX;
		
		while(col + (left_Col * i) != left_Out_Of_Bounds && ChessBoard.boardArray[row][col + (left_Col * i)].getIcon() == null)
		{			
			GridPoint to = new GridPoint(row,col + (left_Col * i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
			i++;
		}
		if(col + (left_Col * i) != left_Out_Of_Bounds && this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + (left_Col * i)].getIcon())) 
		{
			GridPoint to = new GridPoint(row,col + (left_Col * i));
			if(avoidsCheck(from,to,holderArray)) blockable = true;
		}

		ChessBoard.boardArray[row][col].setEnabled(true);
		ButtonActions.enableArrayOnes(enabled,holderArray);

		return blockable;
	}

	@Override
	public void placePieceAt(boolean showDialogs, JButton secondButtonClicked)
	{
		if(moved == false) 
		{
			moved = true;
			
		}
		super.placePieceAt(true,secondButtonClicked);
	}
}
