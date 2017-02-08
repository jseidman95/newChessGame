package gameBoard;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gamePieces.Bishop;
import gamePieces.Castle;
import gamePieces.Knight;
import gamePieces.Piece;
import gamePieces.Queen;

/*
 * This window is the window which allows the user to choose a piece to promote its pawn to. 
 */
@SuppressWarnings("serial")
public class PromotionChooserDialog extends JDialog implements ActionListener
{
	private JButton[] pieceArray;
	private JLabel jlbl;
	private Point to;
	
	public PromotionChooserDialog()
	{
		this.setSize(350, 120);
		this.setLocationRelativeTo(ChessFrame.jfrm);
		this.setResizable(false);
		this.setModal(true);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		pieceArray = new JButton[4];
		createButtons();
		
		addListeners();
		addToDialog();
		
		jlbl = new JLabel("Choose a piece to promote to");
		this.add(jlbl);
		
		this.setVisible(false);
	}
	
	private void createButtons()
	{
		for (int i=0;i<pieceArray.length;i++) pieceArray[i] = new JButton();
	}
	
	private void addListeners()
	{
		for (int i=0;i<pieceArray.length;i++) pieceArray[i].addActionListener(this);
	}
	
	private void addToDialog()
	{
		for (int i=0;i<pieceArray.length;i++)
		{
			this.add(pieceArray[i]);
		}
	}
	
	public void setPieceColors(Piece firstPieceClicked)
	{
		pieceArray[0].setIcon(new Queen(firstPieceClicked.getColor()));
		pieceArray[1].setIcon(new Castle(firstPieceClicked.getColor()));
		pieceArray[2].setIcon(new Bishop(firstPieceClicked.getColor()));
		pieceArray[3].setIcon(new Knight(firstPieceClicked.getColor()));
	}
	
	public void setSecondButtonPosition(Point to)
	{
		this.to = to;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton promotionButton = (JButton) e.getSource();
		Piece promotionPiece = (Piece) promotionButton.getIcon();
		
		Piece.firstPieceClicked = promotionPiece;
		this.setVisible(false);
		
		try
		{
			new Robot().mouseMove(0,0);
			new Robot().mouseMove(to.x,to.y);
		} catch (Exception e1) {} 	
	}
}
