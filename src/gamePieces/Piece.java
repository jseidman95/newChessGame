package gamePieces;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import extras.NotationWriter;
import gameBoard.ChessBoard;
import gameBoard.ChessFrame;
import gameBoard.GridPoint;
import gameBoard.Move;
import saveLoad.SaveProtocol;
import gameBoard.ButtonActions;


@SuppressWarnings("serial")
public abstract class Piece extends ImageIcon
{
	//the color variables       									//set variables for indexes off the board for test purposes
	public static final String WHITE = "white";						public static final int NEGATIVE_ONE_INDEX = -1;
	public static final String BLACK = "black";						public static final int NEGATIVE_TWO_INDEX = -2;
	protected String pieceColor; 									public static final int EIGHT_INDEX = 8;					
																	public static final int NINE_INDEX  = 9;
	
																	
	//these values are not initialized because they are different for black and white pieces
	protected int forward_Row;     protected int left_Out_Of_Bounds;     protected int top_Out_Of_Bounds_Two_Spaces;
	protected int backward_Row;    protected int right_Out_Of_Bounds;    protected int bottom_Out_Of_Bounds_Two_Spaces;
	protected int right_Col;	   protected int top_Out_Of_Bounds;      protected int left_Out_Of_Bounds_Two_Spaces;
	protected int left_Col; 	   protected int bottom_Out_Of_Bounds;   protected int right_Out_Of_Bounds_Two_Spaces;
																		 protected int right_Out_Of_Bounds_Three_Spaces;
					
	//The variables which hold the data of the first button that was clicked and its icon	
	public static JButton firstButtonClicked = null;
	public static Piece firstPieceClicked    = null;
	public static ActionEvent firstAction    = null;
	public static Color firstBackGroundColor = null;
	//The variables which keep track of the respective positions of both kings
	public static GridPoint whiteKingPosition;
	public static GridPoint blackKingPosition;

	//The undo-move stack that allows the user to take back a move
	public static Stack<Move> undoStack;
	
	//This constructor may only be called by a subclass
	public Piece(String fileName)
	{
		super(fileName);		
	}
	
	public String getColor() 
	{
		return pieceColor;
	}

	public String getEnemyColor()
	{
		if(this.pieceColor.equals(Piece.WHITE)) return BLACK;
		else return WHITE;
	}
	
	//every piece has different rules for its movements
	public abstract void enablePotentialMoves(boolean enabled, int row, int col);
	public abstract boolean canMoveWithoutCheck(boolean enabled,int row, int col);
	public abstract void enableOverride(boolean enabled, int row, int col);
	
	//set the movement pattern based on the piece color
	protected void setColorMovements()
	{
		if(pieceColor.equals(BLACK)) setBlackMovements();
		else setWhiteMovements();
	}
	
	private void setBlackMovements()
	{
		//since the black pieces are opposite to the user, the numbers are as if the black user is playing from the opposite side
		forward_Row   =  1;
		backward_Row  = -1;
		left_Col      =  1;	
		right_Col     = -1;
		
		left_Out_Of_Bounds   = EIGHT_INDEX;
		right_Out_Of_Bounds  = NEGATIVE_ONE_INDEX;
		top_Out_Of_Bounds    = EIGHT_INDEX;
		bottom_Out_Of_Bounds = NEGATIVE_ONE_INDEX;
	}
	
	private void setWhiteMovements()
	{
		//since the white pieces are at the bottom of the board (and array), forward would be a negative index change and vice versa
		forward_Row   = -1;
		backward_Row  =  1;
		left_Col      = -1;	
		right_Col     =  1;
		
		left_Out_Of_Bounds   = NEGATIVE_ONE_INDEX;
		right_Out_Of_Bounds  = EIGHT_INDEX;
		top_Out_Of_Bounds    = NEGATIVE_ONE_INDEX;
		bottom_Out_Of_Bounds = EIGHT_INDEX;
	}
	
	//These methods translate the two character strings from the action commands to usable integers for row and col
	public static int getRow(JButton jbtn)
	{
		return jbtn.getActionCommand().charAt(0) - '0'; 
	}
	public static int getCol(JButton jbtn)
	{
		return jbtn.getActionCommand().charAt(1) - '0'; 
	}
	
	//if the given piece is the opposite color of this piece, it is an enemy
	protected boolean shouldHighlight(Piece otherPiece)
	{
		boolean highLight = false;
		
		if(otherPiece != null && otherPiece.getColor().equals(getEnemyColor())) 
		{
			highLight = true;
		}
		
		return highLight;
	}
	
	//if this piece is moved, the king will be put in check
	public boolean isBlockingCheck(int row, int col)
	{
		boolean blocking = false;
		
		if(!kingIsInCheck(this.getColor())) //if the king is not in check originally,check if it will be if removed
		{
			ChessBoard.boardArray[row][col].setIcon(null);
			if(kingIsInCheck(this.getColor())) blocking = true;
		}
		
		ChessBoard.boardArray[row][col].setIcon(this);	
		if(this.getClass().getSimpleName().equals("King")) blocking = false; //a king blocking check is a contradiction
		
		return blocking;
	}
	
	//check if the king of given color is in check
	public static boolean kingIsInCheck(String pieceColor)
	{
		ButtonActions.disableAll(); 
		int kingRow = 0;
		int kingCol = 0;
		boolean inCheck = false;
		
		if(pieceColor.equals(Piece.WHITE))
		{
			kingRow = whiteKingPosition.getX();
			kingCol = whiteKingPosition.getY();
		}
		else
		{
			kingRow = blackKingPosition.getX();
			kingCol = blackKingPosition.getY();
		}	
		
		//enable all of the enemy color and see if the king is in check from any of them
		if(pieceColor.equals(Piece.WHITE)) ButtonActions.enableCheckPotential(true,Piece.BLACK,false);
		else ButtonActions.enableCheckPotential(true,Piece.WHITE,false);
		
		if(ChessBoard.boardArray[kingRow][kingCol].isEnabled()) inCheck = true;

		if(pieceColor.equals(Piece.WHITE)) ButtonActions.enableCheckPotential(false,Piece.BLACK,false);
		else ButtonActions.enableCheckPotential(false,Piece.WHITE,false);
		
		return inCheck;
	}
	
	//check if the king of given color is checkmated
	public static boolean noneCanMove(String pieceColor)
	{
		boolean unmovable = true;
		
		for (int i=ButtonActions.ZERO_INDEX;i<ButtonActions.EIGHT_INDEX && unmovable;i++)
		{
			for (int j=ButtonActions.ZERO_INDEX;j<ButtonActions.EIGHT_INDEX;j++)
			{
				//if the piece is the right color and is not a king, see if it can block check
				if(ChessBoard.boardArray[i][j].getIcon() != null && ((Piece)ChessBoard.boardArray[i][j].getIcon()).getColor().equals(pieceColor))
				{
					if(!pieceCantMove(((Piece)ChessBoard.boardArray[i][j].getIcon()),i,j)) 
					{
						unmovable = false;
						break;
					}
				}
			}
		}	
		return unmovable;
	}
	
	public static boolean pieceCantMove(Piece checkPiece, int row, int col)
	{
		boolean cantMove = true;
		if(checkPiece.canMoveWithoutCheck(true, row, col)) cantMove = false;
		checkPiece.canMoveWithoutCheck(false, row, col);
		return cantMove;
	}
	
	//see if the given movement would either block or avoid check, and if so update the array that keeps track of legal movements
	public boolean avoidsCheck(GridPoint from, GridPoint to, int[][] holderArray)
	{
		boolean blocks = false;
		Piece holder = (Piece)ChessBoard.boardArray[to.getX()][to.getY()].getIcon();
		
		Piece.movePiece(from,to);
		if(!Piece.kingIsInCheck(this.getColor())) 
		{
			holderArray[to.getX()][to.getY()] = 1;
			blocks = true;
		}
		
		Piece.movePiece(to,from);
		ChessBoard.boardArray[to.getX()][to.getY()].setIcon(holder);
		
		return blocks;
	}
	
	//move the piece and update the king position if appropriate
	public static void movePiece(GridPoint from, GridPoint to)
	{
		Piece fromPiece = (Piece) ChessBoard.boardArray[from.getX()][from.getY()].getIcon();

		//if the king was moved, record its location
		if(fromPiece.getClass().getSimpleName().equals("King"))
		{
			if(fromPiece.getColor().equals(Piece.BLACK)) blackKingPosition.updatePosition(to.getX(), to.getY());
			else whiteKingPosition.updatePosition(to.getX(), to.getY());
		}
		
		ChessBoard.boardArray[to.getX()][to.getY()].setIcon(fromPiece);
		ChessBoard.boardArray[from.getX()][from.getY()].setIcon(null);	
		ChessFrame.jpnl.repaint();
	}

	public void placePiece(GridPoint from, GridPoint to)
	{		
		//if the king was moved, record its location
		if(Piece.firstPieceClicked.getClass().getSimpleName().equals("King"))
		{
			if(Piece.firstPieceClicked.getColor().equals(Piece.BLACK)) blackKingPosition.updatePosition(to.getX(), to.getY());
			else whiteKingPosition.updatePosition(to.getX(), to.getY());
		}
		
		ChessBoard.boardArray[to.getX()][to.getY()].setIcon(Piece.firstPieceClicked);
		ChessFrame.jpnl.repaint();
	}
	
	//place the piece at the given button/location and check if the enemy king has been checked or mated
	public void placePieceAt(boolean showDialogs, JButton buttonDropped)
	{
		SaveProtocol.setEdited();
		
		boolean inCheck = false;
		GridPoint from  = new GridPoint(getRow(firstButtonClicked),getCol(firstButtonClicked));
		GridPoint to    = new GridPoint(getRow(buttonDropped),getCol(buttonDropped));
		
		NotationWriter.write(firstPieceClicked,from, to);
		
		if(this.getColor().equals(Piece.BLACK) && undoStack.size() < (ChessFrame.jtbl.getRowCount() * 2)) undoStack.push(new Move(from,to));
		else if(this.getColor().equals(Piece.WHITE) && undoStack.size() < (ChessFrame.jtbl.getRowCount() * 2)-1) undoStack.push(new Move(from,to));

		placePiece(from,to);
		
		ChessFrame.jpnl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		ChessFrame.jpnl.repaint();
		
		//if the enemy king is in check after this move, see if it is mated
		if(kingIsInCheck(firstPieceClicked.getEnemyColor())) 
		{
			if(noneCanMove(firstPieceClicked.getEnemyColor()))
			{
				NotationWriter.writeCheck(this.getColor(), 'm');
				if(showDialogs) JOptionPane.showMessageDialog(ChessFrame.jfrm, "Checkmate!");
			}
			else 
			{
				NotationWriter.writeCheck(this.getColor(), 'c');
				inCheck = true;
			}
		}
		else if(noneCanMove(firstPieceClicked.getEnemyColor()))
		{
			if(showDialogs) JOptionPane.showMessageDialog(ChessFrame.jfrm, "Stalemate!");
		}
				
		//switch turns
		firstButtonClicked = null;
		ButtonActions.enableAllColor(firstPieceClicked.getEnemyColor());
		
		if(!inCheck) ChessFrame.jfrm.setTitle("My Chess Game (" + firstPieceClicked.getEnemyColor() + " to move...) -Edited");
		else ChessFrame.jfrm.setTitle("My Chess Game (" + firstPieceClicked.getEnemyColor() + " in CHECK to move...) -Edited");
	}
}
