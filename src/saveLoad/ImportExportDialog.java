package saveLoad;

import java.awt.FileDialog;

@SuppressWarnings("serial")
public class ImportExportDialog extends FileDialog 
{
	private String defaultFile;
	private SaveFrame parent;
	
	public ImportExportDialog (SaveFrame parent,String defaultFile)
	{
		super(parent);
		this.parent = parent;
		this.defaultFile = defaultFile;
	}

	public void exportToFile()
	{		
		if(SaveProtocol.saved) parent.save(SaveProtocol.savedFile);
		else
		{		
			this.setMode(FileDialog.SAVE);
			this.setFile(defaultFile);
			
			this.setVisible(true);
			
			String fileName = this.getDirectory() + this.getFile();
			
			if(this.getFile() != null) 
			{
				parent.save(fileName);
				SaveProtocol.setSaved(parent,fileName);
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
			parent.load(fileName);
			SaveProtocol.setSaved(parent,fileName);
			
		}
		
		this.setVisible(false);
	}
	
	public void setDefaultFile(String fileName)
	{
		this.defaultFile = fileName;
	}
}
