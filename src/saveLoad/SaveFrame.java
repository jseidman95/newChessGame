package saveLoad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SaveFrame extends JFrame
{
	private JPanel titleBar;
	
	public static final URL redResource      = SaveFrame.class.getResource("redX.png");
	public static final URL redGrayResource  = SaveFrame.class.getResource("greyRedX.png");
	public static final URL yellowResource   = SaveFrame.class.getResource("yellowCircle.png");
	public static final URL greenResource    = SaveFrame.class.getResource("greenCircle.png");
	
	public static final ImageIcon redIcon     = new ImageIcon(redResource);
	public static final ImageIcon redGrayIcon = new ImageIcon(redGrayResource);
	public static final ImageIcon yellowIcon  = new ImageIcon(yellowResource);
	public static final ImageIcon greenIcon   = new ImageIcon(greenResource);
	
	public JButton currentCloseButton;
	public JButton maxButton = new JButton(greenIcon);
	public JButton minButton = new JButton(yellowIcon);
	
	public SaveFrame(ImportExportDialog ieDialog)
	{
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		
		minButton.setBorderPainted(false);
		maxButton.setBorderPainted(false);
		minButton.setFocusable(false);
		maxButton.setFocusable(false);
		
		minButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					setState(JFrame.ICONIFIED);
				}
			});
		
		this.currentCloseButton = new JButton(redIcon);
		this.currentCloseButton.setFocusable(false);
		this.currentCloseButton.setBorderPainted(false);
		this.currentCloseButton.setBorder(new EmptyBorder(2,2,2,8));
		this.currentCloseButton.addActionListener(new ExitSave(this,ieDialog));
		
		Box myBox = Box.createHorizontalBox();
		myBox.add(currentCloseButton);
		myBox.add(minButton);
		myBox.add(maxButton);
		
		titleBar = new JPanel(new BorderLayout());
		MouseScreenMove msm = new MouseScreenMove(this);
		
		titleBar.addMouseListener(msm);
		titleBar.addMouseMotionListener(msm);
		
		titleBar.setPreferredSize(new Dimension(this.getWidth(), 19));
		titleBar.add(myBox, BorderLayout.WEST);
		titleBar.setBackground(new Color(241,241,241));
		
		this.add(titleBar,BorderLayout.PAGE_START);
		this.setVisible(true);
	}
	
	public void setButtonEdited()
	{
		currentCloseButton.setIcon(redGrayIcon);
	}
	
	public void setButtonSaved()
	{
		currentCloseButton.setIcon(redIcon);
	}
}
