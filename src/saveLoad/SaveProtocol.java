package saveLoad;

public class SaveProtocol
{
	public static boolean saved;
	public static String savedFile;
	
	public static void setSaved(String fileName)
	{
		saved = true;
		savedFile = fileName;
	}
	
	public static void setEdited()
	{
		saved = false;
	}
	
	public static void reset()
	{
		saved = false;
		savedFile = null;
	}
}
