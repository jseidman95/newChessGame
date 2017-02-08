package gameBoard;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import gamePieces.Bishop;
import gamePieces.Castle;
import gamePieces.King;
import gamePieces.Knight;
import gamePieces.Pawn;
import gamePieces.Piece;
import gamePieces.Queen;

/*
 * This class contains the methods that involve creating the actual board as well as the methods that create new Piece objects
 * and put them onto the newly created board.
 */
public class ChessBoard
{
	public static JButton boardArray[][];
	
	public static void setup()
	{
		//create the actual board
		boardArray = new JButton[ButtonActions.EIGHT_INDEX][ButtonActions.EIGHT_INDEX];
		
		for (int i=ButtonActions.ZERO_INDEX;i<ButtonActions.EIGHT_INDEX;i++)
		{
			for (int j=ButtonActions.ZERO_INDEX;j<ButtonActions.EIGHT_INDEX;j++)
			{
				//create buttons
				boardArray[i][j] = new JButton();
				
				//set background colors
				if((i+j) % 2 == 0) boardArray[i][j].setBackground(Color.WHITE);
				else boardArray[i][j].setBackground(Color.GRAY);
				boardArray[i][j].setOpaque(true); //this enables the background to be seen
				
				//create border color
				boardArray[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
				
				//set action command labels to be two numbers, the first one being the row and the second one being the column
				boardArray[i][j].setActionCommand("" + i + j);

				//add the mouse listener for moving the pieces
				boardArray[i][j].addMouseListener(new ButtonActions());
				
				//add buttons to panel
				ChessFrame.jpnl.add(boardArray[i][j]);
			}
			
		}
	}
	
	public static void placePieces()
	{
		placePawns();
		placeCastles();
		placeHorses();
		placeBishops();
		placeQueens();
		placeKings();
	}
	
	public static void placePawns()
	{
		for (int i=0;i<ButtonActions.EIGHT_INDEX;i++)
		{
			boardArray[ButtonActions.ONE_INDEX][i].setIcon(new Pawn(Piece.BLACK));
			boardArray[ButtonActions.SIX_INDEX][i].setIcon(new Pawn(Piece.WHITE));
		}
	}
	
	public static void placeBishops()
	{
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.TWO_INDEX].setIcon(new Bishop(Piece.BLACK));
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.FIVE_INDEX].setIcon(new Bishop(Piece.BLACK));
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.TWO_INDEX].setIcon(new Bishop(Piece.WHITE));
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.FIVE_INDEX].setIcon(new Bishop(Piece.WHITE));
	}
	
	public static void placeCastles()
	{
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.ZERO_INDEX].setIcon(new Castle(Piece.BLACK));
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.SEVEN_INDEX].setIcon(new Castle(Piece.BLACK));
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.ZERO_INDEX].setIcon(new Castle(Piece.WHITE));
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.SEVEN_INDEX].setIcon(new Castle(Piece.WHITE));
	}
	
	public static void placeHorses()
	{
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.ONE_INDEX].setIcon(new Knight(Piece.BLACK));
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.SIX_INDEX].setIcon(new Knight(Piece.BLACK));
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.ONE_INDEX].setIcon(new Knight(Piece.WHITE));
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.SIX_INDEX].setIcon(new Knight(Piece.WHITE));
	}

	public static void placeQueens()
	{
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.THREE_INDEX].setIcon(new Queen(Piece.BLACK));
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.THREE_INDEX].setIcon(new Queen(Piece.WHITE));
	}
	
	public static void placeKings()
	{
		boardArray[ButtonActions.ZERO_INDEX][ButtonActions.FOUR_INDEX].setIcon(new King(Piece.BLACK));
		Piece.blackKingPosition = new GridPoint(ButtonActions.ZERO_INDEX,ButtonActions.FOUR_INDEX);
		
		boardArray[ButtonActions.SEVEN_INDEX][ButtonActions.FOUR_INDEX].setIcon(new King(Piece.WHITE));
		Piece.whiteKingPosition = new GridPoint(ButtonActions.SEVEN_INDEX,ButtonActions.FOUR_INDEX);
	}

	public static void removeAllIcons()
	{
		for (int i=ButtonActions.ZERO_INDEX;i<ButtonActions.EIGHT_INDEX;i++)
		{
			for (int j=ButtonActions.ZERO_INDEX;j<ButtonActions.EIGHT_INDEX;j++)
			{
				ChessBoard.boardArray[i][j].setIcon(null);
			}
		}
	}
	
}
