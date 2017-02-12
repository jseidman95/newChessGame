package gameBoard;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JButton;
import gamePieces.Piece;

/*
 * This class contains all methods that relate to actions that involve the buttons in the board array in ChessFrame.  The first few methods involve enabling
 * and disabling the buttons.  THe mouse movement methods at the end allow the user to select and "hold" a piece and move it.
 */
public class ButtonActions implements MouseListener
{
	//static integers for referencing purposes
	public final static int ZERO_INDEX   = 0;
	public final static int ONE_INDEX    = 1;
	public final static int TWO_INDEX    = 2;
	public final static int THREE_INDEX  = 3;
	public final static int FOUR_INDEX   = 4;
	public final static int FIVE_INDEX   = 5;
	public final static int SIX_INDEX    = 6;
	public final static int SEVEN_INDEX  = 7;
	public final static int EIGHT_INDEX  = 8;

	//disables all buttons but the one given
	public static void disableAllBut(JButton selected)
	{
		for (int i=ZERO_INDEX;i<EIGHT_INDEX;i++)
		{
			for (int j=ZERO_INDEX;j<EIGHT_INDEX;j++)
			{
				if(ChessBoard.boardArray[i][j] != selected) disableKeepIcon(ChessBoard.boardArray[i][j]);
			}
		}
	}
	
	//since disabling button usually greys out their icons, this disables the button and keeps the icon
	public static void disableKeepIcon(JButton jbtn)
	{
		jbtn.setDisabledIcon(jbtn.getIcon());
		jbtn.setEnabled(false);
	}
	
	public static void disableAll()
	{
		for (int i=ZERO_INDEX;i<EIGHT_INDEX;i++)
		{
			for (int j=ZERO_INDEX;j<EIGHT_INDEX;j++)
			{
				ChessBoard.boardArray[i][j].setEnabled(false);
			}
		}
	}
	
	//enables all of the given color
	public static void enableAllColor(String pieceColor)
	{
		for (int i=ZERO_INDEX;i<EIGHT_INDEX;i++)
		{
			for (int j=ZERO_INDEX;j<EIGHT_INDEX;j++)
			{
				if(ChessBoard.boardArray[i][j].getIcon() == null) ChessBoard.boardArray[i][j].setEnabled(false);
				else if(((Piece)ChessBoard.boardArray[i][j].getIcon()).getColor().equals(pieceColor))
				{
					ChessBoard.boardArray[i][j].setEnabled(true);
				}
				else
				{
					disableKeepIcon(ChessBoard.boardArray[i][j]);
				}
			}
		}
	}
	
	/*
	 * This method enables or disables all spots that the pieces of a certain color touch to see if the pieces have put a king in check.  The method 
	 * also contains a boolean value to see if the potential spots should include override or not.  Override enables all the potential spots of 
	 * the pieces of a given color BUT also includes all attack spots (even if the piece can't explicitly move there) and if the piece is stopped 
	 * by a piece of its own color, that piece is also highlighted.  This override is used to prevent the king from moving into spots where it 
	 * would be in attack.  
	 */
	public static void enableCheckPotential(boolean enabled,String color,boolean override)
	{
		for (int i=ZERO_INDEX;i<EIGHT_INDEX;i++)
		{
			for (int j=ZERO_INDEX;j<EIGHT_INDEX;j++)
			{	
				Piece currentPiece = (Piece)ChessBoard.boardArray[i][j].getIcon();
				//we don't take a king into account because it cannot put another king in check
				if(currentPiece != null && currentPiece.getColor().equals(color) && !currentPiece.getClass().getSimpleName().equals("King")) 
				{
					if(override) currentPiece.enableOverride(enabled, i, j); 
					else currentPiece.enablePotentialMoves(enabled, i, j);
				}
			}
		}
	}
	
	/*
	 * To keep track of the pieces it must highlight, pieces make an array of the board with 0 being the squares
	 * that should not be highlighted and 1 being a square to highlight.  This method enables all squares based on this array.
	 */
	public static void enableArrayOnes(boolean enabled, int[][] holderArray)
	{
		for (int i=ButtonActions.ZERO_INDEX;i<ButtonActions.EIGHT_INDEX;i++)
		{
			for (int j=ButtonActions.ZERO_INDEX;j<ButtonActions.EIGHT_INDEX;j++)
			{
				if(holderArray[i][j] == 1) ChessBoard.boardArray[i][j].setEnabled(enabled);
			}
		}
	}

	/*
	 * This method changes the mouse cursor to the piece the user has clicked on, so the user is "holding the piece".  It also highlights
	 * all the spots that the piece can move to and disables all illegal spots for the piece.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		JButton buttonPressed = (JButton)(ChessFrame.jpnl.getComponentAt(ChessFrame.jpnl.getMousePosition()));
		if(buttonPressed.isEnabled()) //if the user pressed the mouse on a usable piece perform these actions
		{
			Piece.firstButtonClicked = buttonPressed;
			Piece.firstPieceClicked  = (Piece) buttonPressed.getIcon();
			
			//change the cursor to the image of the piece to move
			ChessFrame.jpnl.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Piece.firstPieceClicked.getImage(), new Point(0,0), "custom cursor"));
			
			//disable all but the first piece pressed in preparation for its highlighting potential moves
			disableAllBut(Piece.firstButtonClicked); 
			
			//highlight where the piece can actually move
			Piece.firstPieceClicked.canMoveWithoutCheck(true, Piece.getRow(Piece.firstButtonClicked), Piece.getCol(Piece.firstButtonClicked));
			
			//remove icon from the original square and enable the square where the piece is being moved from
			ChessBoard.boardArray[Piece.getRow(Piece.firstButtonClicked)][Piece.getCol(Piece.firstButtonClicked)].setIcon(null);
		}
	}

	/*
	 * This method places the piece in the square where the piece has been dropped.  However, if the user has dropped the piece in
	 * an illegal location, the piece is returned to its original spot and a system beep is played.
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(Piece.firstButtonClicked != null) // if the user is holding a piece
		{
			if(ChessFrame.jpnl.getMousePosition() != null) //if the mouse is in frame
			{
				JButton buttonDropped = (JButton)(ChessFrame.jpnl.getComponentAt(ChessFrame.jpnl.getMousePosition()));
				
				if(buttonDropped.isEnabled()) // if the user dropped a piece in a legal location
				{
					//if the user moved the piece, place it at the correct location
					if(buttonDropped != Piece.firstButtonClicked) 
					{
						Piece.firstPieceClicked.placePieceAt(true,buttonDropped);
						ChessFrame.jpnl.repaint();
					}
					else //if the user dropped the piece back to its original spot, reset the user turn
					{
						rePlacePiece();
					}
				}
				else //if the user dropped the piece in an illegal location, reset turn
				{
					rePlacePiece();
					Toolkit.getDefaultToolkit().beep();
				}
			} 
			else //if the user drops the piece out of frame, reset turn
			{
				rePlacePiece();
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
	
	public void rePlacePiece()
	{
		ChessFrame.jpnl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		ChessBoard.boardArray[Piece.getRow(Piece.firstButtonClicked)][Piece.getCol(Piece.firstButtonClicked)].setIcon(Piece.firstPieceClicked);
		Piece.firstPieceClicked.canMoveWithoutCheck(false, Piece.getRow(Piece.firstButtonClicked), Piece.getCol(Piece.firstButtonClicked));			
		enableAllColor(Piece.firstPieceClicked.getColor());
		
		ChessFrame.jpnl.repaint();
		Piece.firstButtonClicked = null;
	}
	
	//unused mouse listener methods
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
