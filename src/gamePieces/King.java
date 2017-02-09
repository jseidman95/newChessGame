package gamePieces;



import javax.swing.JButton;

import extras.NotationWriter;
import gameBoard.ButtonActions;
import gameBoard.ChessBoard;
import gameBoard.GridPoint;
import gameBoard.Move;


@SuppressWarnings("serial")
public class King extends Piece
{
	public boolean moved;
	public GridPoint position;
	
	public King(String pieceColor)
	{
		super("src/resources/" + pieceColor + "King.png");
		
		this.pieceColor = pieceColor;
		setColorMovements();	
		
		moved = false;
	}
	
	@Override
	public void enablePotentialMoves(boolean enabled, int row, int col) 
	{		
		if(row + forward_Row != top_Out_Of_Bounds && noAdjacentEnemyKing(row+forward_Row,col))
		{
			if(ChessBoard.boardArray[row + forward_Row][col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col].getIcon())) 
			{
				ChessBoard.boardArray[row + forward_Row][col].setEnabled(enabled);
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && noAdjacentEnemyKing(row+forward_Row,col+right_Col))
		{
			if(ChessBoard.boardArray[row + forward_Row][col + right_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + right_Col].getIcon())) 
			{
				ChessBoard.boardArray[row + forward_Row][col + right_Col].setEnabled(enabled);
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && noAdjacentEnemyKing(row+forward_Row,col+left_Col))
		{
			if(ChessBoard.boardArray[row + forward_Row][col + left_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + left_Col].getIcon())) 
			{
				ChessBoard.boardArray[row + forward_Row][col + left_Col].setEnabled(enabled);
			}
		}
		
		if(col + left_Col != left_Out_Of_Bounds && noAdjacentEnemyKing(row,col+left_Col))
		{
			if(ChessBoard.boardArray[row][col + left_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + left_Col].getIcon())) 
			{
				ChessBoard.boardArray[row][col + left_Col].setEnabled(enabled);
			}
		}
		
		if(col + right_Col != right_Out_Of_Bounds && noAdjacentEnemyKing(row,col+right_Col))
		{
			if(ChessBoard.boardArray[row][col + right_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + right_Col].getIcon())) 
			{
				ChessBoard.boardArray[row][col + right_Col].setEnabled(enabled);
			}
		}

		if(row + backward_Row != bottom_Out_Of_Bounds && noAdjacentEnemyKing(row+backward_Row,col))
		{
			if(ChessBoard.boardArray[row + backward_Row][col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + backward_Row][col].getIcon())) 
			{
				ChessBoard.boardArray[row + backward_Row][col].setEnabled(enabled);
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && noAdjacentEnemyKing(row+backward_Row,col+right_Col))
		{
			if(ChessBoard.boardArray[row + backward_Row][col + right_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + backward_Row][col + right_Col].getIcon()))
			{
				ChessBoard.boardArray[row + backward_Row][col + right_Col].setEnabled(enabled);
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && noAdjacentEnemyKing(row+backward_Row,col+left_Col))
		{
			if(ChessBoard.boardArray[row + backward_Row][col + left_Col].getIcon() == null || this.shouldHighlight((Piece) ChessBoard.boardArray[row + backward_Row][col + left_Col].getIcon()))
			{
				ChessBoard.boardArray[row + backward_Row][col + left_Col].setEnabled(enabled);
			}
		}	
		
		if(this.getColor().equals(Piece.BLACK)) ButtonActions.enableCheckPotential(false, Piece.WHITE, true);
		else ButtonActions.enableCheckPotential(false, Piece.BLACK, true);
		
		ChessBoard.boardArray[row][col].setEnabled(true);
	}
	
	public boolean noAdjacentEnemyKing(int row, int col)
	{
		boolean adjEnemyKing = true;
		
		if(row + forward_Row  != top_Out_Of_Bounds    &&  ChessBoard.boardArray[row + forward_Row][col].getIcon()  != null && ((Piece)ChessBoard.boardArray[row + forward_Row][col] .getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row + forward_Row][col]  .getIcon()))) adjEnemyKing = false;
		if(row + backward_Row != bottom_Out_Of_Bounds &&  ChessBoard.boardArray[row + backward_Row][col].getIcon() != null && ((Piece)ChessBoard.boardArray[row + backward_Row][col].getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row + backward_Row][col] .getIcon()))) adjEnemyKing = false;
		if(col + right_Col    != right_Out_Of_Bounds  &&  ChessBoard.boardArray[row][col + right_Col].getIcon()    != null && ((Piece)ChessBoard.boardArray[row][col + right_Col]   .getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row][col + right_Col]    .getIcon()))) adjEnemyKing = false;
		if(col + left_Col     != left_Out_Of_Bounds   &&  ChessBoard.boardArray[row ][col + left_Col].getIcon()    != null && ((Piece)ChessBoard.boardArray[row][col + left_Col]    .getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row][col + left_Col]     .getIcon()))) adjEnemyKing = false;
		
		if(row + forward_Row  != top_Out_Of_Bounds    && col + right_Col != right_Out_Of_Bounds &&  ChessBoard.boardArray[row + forward_Row][col + right_Col].getIcon()  != null && ((Piece)ChessBoard.boardArray[row + forward_Row][col + right_Col] .getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row + forward_Row][col + right_Col]  .getIcon()))) adjEnemyKing = false;
		if(row + forward_Row  != top_Out_Of_Bounds    && col + left_Col  != left_Out_Of_Bounds  &&  ChessBoard.boardArray[row + forward_Row][col + left_Col].getIcon()   != null && ((Piece)ChessBoard.boardArray[row + forward_Row][col + left_Col]  .getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row + forward_Row][col + left_Col]   .getIcon()))) adjEnemyKing = false;
		if(row + backward_Row != bottom_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds &&  ChessBoard.boardArray[row + backward_Row][col + right_Col].getIcon() != null && ((Piece)ChessBoard.boardArray[row + backward_Row][col + right_Col].getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row + backward_Row][col + right_Col] .getIcon()))) adjEnemyKing = false;
		if(row + backward_Row != bottom_Out_Of_Bounds && col + left_Col  != left_Out_Of_Bounds  &&  ChessBoard.boardArray[row + backward_Row][col + left_Col].getIcon()  != null && ((Piece)ChessBoard.boardArray[row + backward_Row][col + left_Col] .getIcon()).getClass().getSimpleName().equals("King")  && this.shouldHighlight(((Piece)ChessBoard.boardArray[row + backward_Row][col + left_Col] .getIcon()))) adjEnemyKing = false;

		return adjEnemyKing;
	}

	@Override
	public boolean canMoveWithoutCheck(boolean enabled,int row, int col)
	{
		GridPoint from = new GridPoint(row,col);
		int[][] holderArray = new int[8][8];
		boolean blockable = false;
		
		if(row + forward_Row != top_Out_Of_Bounds && noAdjacentEnemyKing(row+forward_Row,col))
		{
			if(ChessBoard.boardArray[row + forward_Row][col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + forward_Row,col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && noAdjacentEnemyKing(row+forward_Row,col+right_Col))
		{
			if(ChessBoard.boardArray[row + forward_Row][col + right_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + right_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + forward_Row,col + right_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + forward_Row != top_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && noAdjacentEnemyKing(row+forward_Row,col+left_Col))
		{
			if(ChessBoard.boardArray[row + forward_Row][col + left_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + forward_Row][col + left_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + forward_Row,col + left_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(col + left_Col != left_Out_Of_Bounds && noAdjacentEnemyKing(row,col+left_Col))
		{
			if(ChessBoard.boardArray[row][col + left_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + left_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row,col + left_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(col + right_Col != right_Out_Of_Bounds && noAdjacentEnemyKing(row,col+right_Col))
		{
			if(ChessBoard.boardArray[row][col + right_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row][col + right_Col].getIcon())) 
			{
				GridPoint to = new GridPoint(row,col + right_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}

		if(row + backward_Row != bottom_Out_Of_Bounds && noAdjacentEnemyKing(row+backward_Row,col))
		{
			if(ChessBoard.boardArray[row + backward_Row][col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + backward_Row][col].getIcon())) 
			{
				GridPoint to = new GridPoint(row + backward_Row,col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + right_Col != right_Out_Of_Bounds && noAdjacentEnemyKing(row+backward_Row,col+right_Col))
		{
			if(ChessBoard.boardArray[row + backward_Row][col + right_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + backward_Row][col + right_Col].getIcon()))
			{
				GridPoint to = new GridPoint(row + backward_Row,col + right_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}
		
		if(row + backward_Row != bottom_Out_Of_Bounds && col + left_Col != left_Out_Of_Bounds && noAdjacentEnemyKing(row+backward_Row,col+left_Col))
		{
			if(ChessBoard.boardArray[row + backward_Row][col + left_Col].getIcon() == null || this.shouldHighlight((Piece)ChessBoard.boardArray[row + backward_Row][col + left_Col].getIcon()))
			{
				GridPoint to = new GridPoint(row + backward_Row,col + left_Col);
				if(avoidsCheck(from,to,holderArray)) blockable = true;
			}
		}	
		
		if(!moved && !kingIsInCheck(this.getColor()) && this.getColor().equals(Piece.WHITE))
		{
			Piece maybeCastle = (Piece) ChessBoard.boardArray[row][col + (3*right_Col)].getIcon();
			if(maybeCastle != null && maybeCastle.getClass().getSimpleName().equals("Castle") && !((Castle)maybeCastle).moved)
			{
				if(ChessBoard.boardArray[row][col + right_Col].getIcon() == null && ChessBoard.boardArray[row][col + (2 * right_Col)].getIcon() == null)
				{
					ButtonActions.enableCheckPotential(true,this.getEnemyColor(),true);
					if(!ChessBoard.boardArray[row][col + right_Col].isEnabled() && !ChessBoard.boardArray[row][col + (2 * right_Col)].isEnabled())
					{
						holderArray[row][col + (2*right_Col)] = 1;
					}					
					ButtonActions.enableCheckPotential(false,this.getEnemyColor(),true);
				}
			}
		}
		
		if(!moved && !kingIsInCheck(this.getColor()) && this.getColor().equals(Piece.WHITE))
		{
			Piece maybeCastle = (Piece) ChessBoard.boardArray[row][col + (3*right_Col)].getIcon();
			if(maybeCastle != null && maybeCastle.getClass().getSimpleName().equals("Castle") && !((Castle)maybeCastle).moved)
			{
				if(ChessBoard.boardArray[row][col + right_Col].getIcon() == null && ChessBoard.boardArray[row][col + (2 * right_Col)].getIcon() == null)
				{
					ButtonActions.enableCheckPotential(true,this.getEnemyColor(),true);
					if(!ChessBoard.boardArray[row][col + right_Col].isEnabled() && !ChessBoard.boardArray[row][col + (2 * right_Col)].isEnabled())
					{
						holderArray[row][col + (2*right_Col)] = 1;
					}					
					ButtonActions.enableCheckPotential(false,this.getEnemyColor(),true);
				}
			}
		}
		
		if(!moved && !kingIsInCheck(this.getColor()) && this.getColor().equals(Piece.BLACK))
		{
			Piece maybeCastle = (Piece) ChessBoard.boardArray[row][col + (3*left_Col)].getIcon();
			if(maybeCastle != null && maybeCastle.getClass().getSimpleName().equals("Castle") && !((Castle)maybeCastle).moved)
			{
				if(ChessBoard.boardArray[row][col + left_Col].getIcon() == null && ChessBoard.boardArray[row][col + (2 * left_Col)].getIcon() == null)
				{
					ButtonActions.enableCheckPotential(true,this.getEnemyColor(),true);
					if(!ChessBoard.boardArray[row][col + left_Col].isEnabled() && !ChessBoard.boardArray[row][col + (2 * left_Col)].isEnabled())
					{
						holderArray[row][col + (2*left_Col)] = 1;
					}					
					ButtonActions.enableCheckPotential(false,this.getEnemyColor(),true);
				}
			}
		}
		
		if(!moved && !kingIsInCheck(this.getColor()) && this.getColor().equals(Piece.WHITE))
		{
			Piece maybeCastle = (Piece) ChessBoard.boardArray[row][col + (4*left_Col)].getIcon();
			if(maybeCastle != null && maybeCastle.getClass().getSimpleName().equals("Castle") && !((Castle)maybeCastle).moved)
			{
				if(ChessBoard.boardArray[row][col + left_Col].getIcon() == null && ChessBoard.boardArray[row][col + (2 * left_Col)].getIcon() == null && ChessBoard.boardArray[row][col + (3 * left_Col)].getIcon() == null)
				{
					ButtonActions.enableCheckPotential(true,this.getEnemyColor(),true);
					if(!ChessBoard.boardArray[row][col + left_Col].isEnabled() && !ChessBoard.boardArray[row][col + (2 * left_Col)].isEnabled())
					{
						holderArray[row][col + (2*left_Col)] = 1;
					}					
					ButtonActions.enableCheckPotential(false,this.getEnemyColor(),true);
				}
			}
		}
		
		if(!moved && !kingIsInCheck(this.getColor()) && this.getColor().equals(Piece.BLACK))
		{
			Piece maybeCastle = (Piece) ChessBoard.boardArray[row][col + (4*right_Col)].getIcon();
			if(maybeCastle != null && maybeCastle.getClass().getSimpleName().equals("Castle") && !((Castle)maybeCastle).moved)
			{
				if(ChessBoard.boardArray[row][col + right_Col].getIcon() == null && ChessBoard.boardArray[row][col + (2 * right_Col)].getIcon() == null && ChessBoard.boardArray[row][col + (3 * right_Col)].getIcon() == null)
				{
					ButtonActions.enableCheckPotential(true,this.getEnemyColor(),true);
					if(!ChessBoard.boardArray[row][col + right_Col].isEnabled() && !ChessBoard.boardArray[row][col + (2 * right_Col)].isEnabled())
					{
						holderArray[row][col + (2*right_Col)] = 1;
					}					
					ButtonActions.enableCheckPotential(false,this.getEnemyColor(),true);
				}
			}
		}
		
		ChessBoard.boardArray[row][col].setEnabled(true);
		ButtonActions.enableArrayOnes(enabled,holderArray);

		return blockable;
	}

	@Override
	public void enableOverride(boolean enabled, int row, int col) {}

	@Override
	public void placePieceAt(JButton secondButtonClicked)
	{
		GridPoint from  = new GridPoint(getRow(firstButtonClicked),getCol(firstButtonClicked));
		GridPoint to    = new GridPoint(getRow(secondButtonClicked),getCol(secondButtonClicked));
		
		if(getRow(secondButtonClicked) == getRow(Piece.firstButtonClicked) && getCol(secondButtonClicked) == getCol(Piece.firstButtonClicked) + 2)
		{
			Piece.undoStack.push(new Move(this,from,to,((Piece)ChessBoard.boardArray[getRow(Piece.firstButtonClicked)][ButtonActions.SEVEN_INDEX].getIcon()),'k'));
			movePiece(new GridPoint(getRow(Piece.firstButtonClicked), ButtonActions.SEVEN_INDEX), new GridPoint(getRow(secondButtonClicked), ButtonActions.FIVE_INDEX));
			NotationWriter.writeCastle(this.getColor(), 'k');
		}
		
		else if(getRow(secondButtonClicked) == getRow(Piece.firstButtonClicked) && getCol(secondButtonClicked) == getCol(Piece.firstButtonClicked) - 2)
		{
			Piece.undoStack.push(new Move(this,from,to,((Piece)ChessBoard.boardArray[getRow(Piece.firstButtonClicked)][ButtonActions.ZERO_INDEX].getIcon()),'q'));
			movePiece(new GridPoint(getRow(Piece.firstButtonClicked), ButtonActions.ZERO_INDEX), new GridPoint(getRow(secondButtonClicked), ButtonActions.THREE_INDEX));
			NotationWriter.writeCastle(this.getColor(), 'q');
		}
		else Piece.undoStack.push(new Move(this,from,to));
		
		moved = true;
		super.placePieceAt(secondButtonClicked);
	}
	
}
