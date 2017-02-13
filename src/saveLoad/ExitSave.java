package saveLoad;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExitSave implements WindowListener
{
	private JFrame mainFrame;
	private ImportExportDialog ie; 
	
	public ExitSave(JFrame mainFrame,ImportExportDialog ie)
	{
		this.mainFrame = mainFrame;
		this.ie = ie;
	}

	@Override
	public void windowClosing(WindowEvent e)
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

	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

}
