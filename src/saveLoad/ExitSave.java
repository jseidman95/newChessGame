package saveLoad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ExitSave implements ActionListener
{
	private SaveFrame mainFrame;
	private ImportExportDialog ie; 
	
	public ExitSave(SaveFrame mainFrame,ImportExportDialog ie)
	{
		this.mainFrame = mainFrame;
		this.ie = ie;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(SaveProtocol.saved) System.exit(0);
		else
		{
			int answer = JOptionPane.showConfirmDialog(mainFrame, "Exit without saving?");
			
			if(answer == JOptionPane.YES_OPTION) System.exit(0);
			else if(answer == JOptionPane.NO_OPTION) 
			{
				ie.exportToFile();
				System.exit(0);
			}
		}
	}


}
