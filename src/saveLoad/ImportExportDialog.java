package saveLoad;

import java.awt.FileDialog;
import java.awt.Frame;

@SuppressWarnings("serial")
public abstract class ImportExportDialog extends FileDialog 
{
	private String defaultFile;
	
	public ImportExportDialog (Frame parent,String defaultFile)
	{
		super(parent);
		this.defaultFile = defaultFile;
	}

	public void exportToFile()
	{		
		if(SaveProtocol.saved) save(SaveProtocol.savedFile);
		else
		{		
			this.setMode(FileDialog.SAVE);
			this.setFile(defaultFile);
			
			this.setVisible(true);
			
			String fileName = this.getDirectory() + this.getFile();
			
			if(this.getFile() != null) 
			{
				save(fileName);
				SaveProtocol.setSaved(fileName);
			}
			
			
			this.setVisible(false);
		}
	}
	
	public void importFromFile()
	{
		this.setMode(FileDialog.LOAD);
		this.setVisible(true);
		
		String fileName = this.getDirectory() + this.getFile();
		if(this.getFile() != null) 
		{
			load(fileName);
			SaveProtocol.setSaved(fileName);
		}
		
		this.setVisible(false);
	}
	
	public abstract void save(String fileName);
	public abstract void load(String fileName);
}
