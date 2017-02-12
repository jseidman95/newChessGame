package extras;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;

import gameBoard.ChessBoard;
import gameBoard.ChessFrame;
import gamePieces.Bishop;
import gamePieces.Castle;
import gamePieces.Knight;
import gamePieces.Piece;
import gamePieces.Queen;

public class MyTableImport
{
	public static Piece promotionPiece;
	
	public static void readImport(String fileName)
	{
		Scanner scan = null;
		ArrayList<String> moves = new ArrayList<>();
		
		try {scan = new Scanner(new File(fileName));} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		while(scan.hasNext())
		{
			scan.nextInt(); //this is called so moves doesnt add the turn numbers
			
			moves.add(scan.next());
			if(scan.hasNext()) moves.add(scan.next());
		}
		
		for (int i=0;i<moves.size();i++)
		{
			doMove(i,moves.get(i));
			ChessFrame.jpnl.paintImmediately(ChessFrame.jpnl.getVisibleRect());
			try
			{
				Thread.sleep(250);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void doMove(int turnNumber, String move)
	{
		String[] fromTo = null;
		int fromRow;
		int fromCol;
		int toRow;
		int toCol;
		
		//ADD CASTLE AND A WAY TO CONTINUE A GAME WHILE IN THE MIDDLE OF ANOTHER
		
		if(move.equals("O-O-O"))
		{
			fromCol = 4;
			toCol   = 2;
			
			if(turnNumber % 2 == 1) //if the turn number is odd, it is black's turn
			{
				fromRow = toRow = 0;
			}
			else fromRow = toRow = 7;
		}
		else if(move.equals("O-O"))
		{
			fromCol = 4;
			toCol   = 6;
			
			if(turnNumber % 2 == 1) //if the turn number is odd, it is black's turn
			{
				fromRow = toRow = 0;
			}
			else fromRow = toRow = 7;
		}
		else
		{
			fromTo = new String[2];
			
			if(move.contains("x")) fromTo = move.split("x");
			else fromTo = move.split("-");
			
			if(fromTo[0].length() == 3)
			{
				fromCol = getFile(fromTo[0].charAt(1));
				fromRow = 8 - Character.getNumericValue(fromTo[0].charAt(2));
			}
			else
			{
				fromCol = getFile(fromTo[0].charAt(0));
				fromRow = 8 - Character.getNumericValue(fromTo[0].charAt(1));
			}
		
			toCol = getFile(fromTo[1].charAt(0));
			toRow = 8 - Character.getNumericValue(fromTo[1].charAt(1));
		}
		JButton buttonFrom = ChessBoard.boardArray[fromRow][fromCol];
		JButton buttonTo   = ChessBoard.boardArray[toRow][toCol];
		Piece pieceFrom = (Piece) buttonFrom.getIcon();
		String pieceColor = pieceFrom.getColor();
		
		ChessBoard.boardArray[fromRow][fromCol].setIcon(null); //because in a normal game this icon would already be deleted
		Piece.firstButtonClicked = buttonFrom;
		Piece.firstPieceClicked  = pieceFrom;
		
		if(fromTo != null && fromTo[1].length() > 2 && !fromTo[1].contains("+") && !fromTo[1].contains("#")) promotionPiece = getPiece(pieceColor,fromTo[1].charAt(2));

		pieceFrom.placePieceAt(false, buttonTo);
	}
	
	public static int getFile(char file)
	{
		return file - 'a';
	}
	
	public static Piece getPiece(String color, char letter)
	{
		Piece promoPiece = null;
		
		switch(letter)
		{
			case 'B':
				promoPiece = new Bishop(color);
				break;
			case 'Q':
				promoPiece = new Queen(color);
				break;
			case 'R':
				promoPiece = new Castle(color);
				break;
			case 'N':
				promoPiece = new Knight(color);
				break;
		}
		
		return promoPiece;
	}
}
