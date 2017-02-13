package extras;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;

public class MyTableExport
{	
	public static void writeTableToFile(JTable dataTable, String fileName)
	{
		FileOutputStream writer;
		try
		{
			writer = new FileOutputStream(fileName);
	
			for (int i=0;i<dataTable.getRowCount();i++)
			{
				for (int j=0;j<dataTable.getColumnCount();j++) //start at one because we dont need to write the move number
				{ 		
					writer.write(dataTable.getValueAt(i, j).toString().getBytes());
					writer.write(" ".getBytes());
				}
				writer.write("\n".getBytes());
			}
			writer.flush();
			writer.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
