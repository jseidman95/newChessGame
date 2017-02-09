package gamePieces;

import gameBoard.ButtonActions;
import gameBoard.ChessBoard;

@SuppressWarnings("serial")
public class Queen extends Piece
{
	private Bishop bishopMovements;
	private Castle castleMovements;
	
	public Queen(String pieceColor)
	{	
		super("src/resources/" + pieceColor + "Queen.png");
		this.pieceColor = pieceColor;
		
		bishopMovements = new Bishop(pieceColor);
		castleMovements = new Castle(pieceColor);
	}

	@Override
	public void enablePotentialMoves(boolean enabled, int row, int col)
	{
		bishopMovements.enablePotentialMoves(enabled, row, col);
		castleMovements.enablePotentialMoves(enabled, row, col);
	}

	@Override
	public boolean canMoveWithoutCheck(boolean enabled,int row, int col)
	{
		int[][] holderArray = new int[8][8];
		boolean strikeOne = false;
		boolean strikeTwo = false;
		
		strikeOne = bishopMovements.canMoveWithoutCheck(enabled, row, col);
		
		for (int i=ButtonActions.ZERO_INDEX;i<ButtonActions.EIGHT_INDEX;i++)
		{
			for (int j=ButtonActions.ZERO_INDEX;j<ButtonActions.EIGHT_INDEX;j++)
			{
				if(ChessBoard.boardArray[i][j].isEnabled()) holderArray[i][j] = 1;
			}
		}
		
		strikeTwo = castleMovements.canMoveWithoutCheck(enabled, row, col);
		ButtonActions.enableArrayOnes(enabled, holderArray);
		
		return strikeOne || strikeTwo;	
	}

	@Override
	public void enableOverride(boolean enabled, int row, int col)
	{
		bishopMovements.enableOverride(enabled, row, col);
		castleMovements.enableOverride(enabled, row, col);
	}
	
}
