package saveLoad;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MouseScreenMove extends MouseAdapter
{
	private SaveFrame jfrm;
	private Point initPoint;
	
	public MouseScreenMove(SaveFrame jfrm)
	{
		this.jfrm = jfrm;
	}
	public void mousePressed(MouseEvent e)
	{
		this.initPoint = e.getPoint();
	}
	
	public void mouseDragged(MouseEvent e)
	{	
		Point currCords = e.getLocationOnScreen();
        
        jfrm.setLocation(currCords.x - initPoint.x,currCords.y - initPoint.y);
	}
}
