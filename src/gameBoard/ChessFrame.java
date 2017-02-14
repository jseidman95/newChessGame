package gameBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import extras.FullNotationDialog;
import extras.MyTableExport;
import extras.MyTableImport;
import gamePieces.Piece;
import saveLoad.SaveFrame;

/*
 * This class contains all objects and methods that involve the main frame.  Therefore it contains methods having to do with the board,
 * the JTable that keeps track of chess notation, and the JPanel on which the board is placed.
 */
public class ChessFrame
{
	//frames and dialogs
	public static PromotionChooserDialog pcd;
	public static FullNotationDialog nld;
	public static SaveFrame jfrm;
	public static JPanel jpnl;
	
	//components
	public static JTable jtbl;
	public static DefaultTableModel jtblModel;
	public static JScrollPane jsp;
	
	public static JMenuBar jmb;
	public static JMenu jmGame;
	public static JMenu jmMoves;
	public static JMenuItem undo;
	public static JMenuItem newGame;
	public static JMenuItem saveGame;
	public static JMenuItem openGame;
	
	@SuppressWarnings({ "serial", "static-access" })
	public ChessFrame()
	{		
		if(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) 
		{
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			//System.setProperty("com.apple.mrj.application.apple.menu.about.name", "My Chess Game");
		}
		
		//make the main frame
		jfrm = new SaveFrame("MyChessGame.txt")
			{
				@Override
				public void save(String fileName)
				{
					MyTableExport.writeTableToFile(jtbl, fileName);
				}
				
				@Override
				public void load(String fileName)
				{
					MyTableImport.readImport(fileName);
				}
			};
		jfrm.setSize(600, 600);
	
		jfrm.maxButton.setEnabled(false);
		//jfrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//jfrm.setLayout(new BorderLayout(0,-2)); //eliminate the space in between the components for a tight fit
		jfrm.setLocationRelativeTo(null);
		
		//make the panel that will hold the game board
		jpnl = new JPanel(new GridLayout(ButtonActions.EIGHT_INDEX,ButtonActions.EIGHT_INDEX));
		
		//the promotion chooser dialog for when a pawn reaches the end of the board
		pcd = new PromotionChooserDialog();
		
		//make the JMenuBar and its components
		jmb = new JMenuBar();
		jmMoves = new JMenu("Moves");
		jmGame  = new JMenu("Game"); 

		newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						newGame();
					}
				});
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		jmGame.add(newGame);
		
		
		saveGame = new JMenuItem("Save");
		saveGame.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						jfrm.ieDialog.exportToFile();
					}
				});
		saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Toolkit.getDefaultToolkit().getDefaultToolkit().getMenuShortcutKeyMask()));
		jmGame.add(saveGame);
		
		openGame = new JMenuItem("Open");
		openGame.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						jfrm.ieDialog.importFromFile();
					}
				});
		openGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		jmGame.add(openGame);
		jmb.add(jmGame);
		
		undo = new JMenuItem("undo");
		undo.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(ChessFrame.jtbl.getRowCount() > 0) Piece.undoStack.pop().undo();
					else Toolkit.getDefaultToolkit().beep();
				}	
			});
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		jmMoves.add(undo);
		jmb.add(jmMoves);
		
		jfrm.setJMenuBar(jmb);
		
		//make the text area where the algebraic notation will be written
		jtbl = new JTable()
			{
				@Override
				public boolean isCellEditable(int row,int col)
				{
					return false;
				}
			};
		
		//dont allow the user to select items from the table
		jtbl.setRowSelectionAllowed(false);
		jtbl.setCellSelectionEnabled(false);
		jtbl.setColumnSelectionAllowed(false);
		
		//add the mouse listener that brings up the full notation dialog when the main frame table is double clicked
		jtbl.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() == 2) 
				{
					nld.setLocationRelativeTo(jfrm);
					nld.setVisible(true);
				}
			}
			public void mousePressed(MouseEvent e)  {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e)  {}
			public void mouseExited(MouseEvent e)   {}	
		});
			
		//make the scroll pane for the JTable
		jsp = new JScrollPane(jtbl);
		jsp.setPreferredSize(new Dimension(600,35));
		jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		jfrm.add(jsp,BorderLayout.AFTER_LAST_LINE);
		jfrm.add(jpnl,BorderLayout.CENTER);
		
		ChessBoard.setup();
		newGame();
		
		jfrm.setVisible(true);
	}
	
	public static void newGame()
	{					
		//make the table model for the algebraic notation table	
		jtblModel = new DefaultTableModel();
		jtbl.setModel(jtblModel);
		
		jtblModel.addColumn("Turn Number");
		jtblModel.addColumn("White Turn");
		jtblModel.addColumn("Black Turn");
		
		//make the notation window
		nld = new FullNotationDialog(jtblModel);
				
		//create the undo stack
		Piece.undoStack = new Stack<>();
		
		//setup the new board and its listeners
		ChessBoard.removeAllIcons();
		ChessBoard.placePieces();
		ButtonActions.enableAllColor(Piece.WHITE); //white always starts
	}
}
