package saveLoad;

public class SaveProtocol
{
	public static boolean saved;
	public static String savedFile;
	
	public static void setSaved(SaveFrame jfrm, String fileName)
	{
		saved = true;
		savedFile = fileName;
		jfrm.setButtonSaved();
	}
	
	public static void setEdited(SaveFrame jfrm)
	{
		saved = false;
		jfrm.setButtonEdited();
	}
	
	public static void reset()
	{
		saved = false;
		savedFile = null;
	}
}
