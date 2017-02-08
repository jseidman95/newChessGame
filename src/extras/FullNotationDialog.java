package extras;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gameBoard.ChessFrame;

/*
 * This is a simple dialog that displays the entire algebraic notation game log in a non-modal window.
 */
@SuppressWarnings("serial")
public class FullNotationDialog extends JDialog
{
	private JScrollPane jsp;
	private JTable jtbl;
	@SuppressWarnings("unused")
	private DefaultTableModel jtblModel; 
	
	public FullNotationDialog(DefaultTableModel jtblModel) 
	{
		this.setSize(300,400);
		this.setLocationRelativeTo(ChessFrame.jpnl);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //to avoid destroying and creating the dialog everytime the user closes and opens it
		
		this.jtblModel = jtblModel; //we use the table model from the Main frame so updates to the table there occur in this dialog
		
		this.jtbl = new JTable(jtblModel) 
			{
				@Override
				public boolean isCellEditable(int row,int col)
				{
					return false;
				}
			};
			
		this.jtbl.setRowSelectionAllowed(false);
		this.jtbl.setCellSelectionEnabled(false);
		this.jtbl.setColumnSelectionAllowed(false);
			
		this.jtbl.setGridColor(Color.BLACK);
		this.jtbl.setShowGrid(true);
		this.jtbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		jsp = new JScrollPane(jtbl);
		this.add(jsp,BorderLayout.CENTER);
	}
}
