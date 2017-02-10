package extras;

import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class ImportExportDialog extends FileDialog 
{
	public ImportExportDialog(Frame parent)
	{
		super(parent);
	}

	public void export(JTable jtbl)
	{
		this.setMode(FileDialog.SAVE);
		this.setFile("MyChessGame.txt");
		this.setVisible(true);
		
		String dir  = this.getDirectory();
		String file = this.getFile();
		
		MyTableExport.writeTableToFile(jtbl, dir+file);
		
		this.setVisible(false);
	}
	
	public void importFromFile()
	{
		this.setMode(FileDialog.LOAD);
		this.setVisible(true);
		
		String dir  = this.getDirectory();
		String file = this.getFile();
		
		MyTableImport.readImport(dir+file);
		this.setVisible(false);
	}
}
