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
public abstract class SaveFrame extends JFrame
{
	private JPanel titleBar;
	public ImportExportDialog ieDialog;
	
	public static final URL redResource       = SaveFrame.class.getResource("redX.png");
	public static final URL redGrayResource   = SaveFrame.class.getResource("greyRedX.png");
	public static final URL yellowResource    = SaveFrame.class.getResource("yellowCircle.png");
	public static final URL greenResource     = SaveFrame.class.getResource("greenCircle.png");
	public static final URL redXHover         = SaveFrame.class.getResource("redXHover.png");
	public static final URL yellowCircleHover = SaveFrame.class.getResource("yellowCircleHover.png");
	public static final URL greenCircleHover  = SaveFrame.class.getResource("greenCircleHover.png");
	
	public static final ImageIcon redIcon     = new ImageIcon(redResource);
	public static final ImageIcon redGrayIcon = new ImageIcon(redGrayResource);
	public static final ImageIcon yellowIcon  = new ImageIcon(yellowResource);
	public static final ImageIcon greenIcon   = new ImageIcon(greenResource);
	public static final ImageIcon redIconHover    = new ImageIcon(redXHover);
	public static final ImageIcon greenIconHover  = new ImageIcon(greenCircleHover);
	public static final ImageIcon yellowIconHover = new ImageIcon(yellowCircleHover);
	
	public JButton currentCloseButton;
	public JButton maxButton = new JButton(greenIcon);
	public JButton minButton = new JButton(yellowIcon);
	
	public SaveFrame()
	{
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		
		minButton.setBorderPainted(false);
		maxButton.setBorderPainted(false);
		minButton.setFocusable(false);
		maxButton.setFocusable(false);
		minButton.setRolloverIcon(yellowIconHover);
		maxButton.setRolloverIcon(greenIconHover);
		
		minButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					setState(JFrame.ICONIFIED);
				}
			});
		
		this.currentCloseButton = new JButton(redIcon);
		this.currentCloseButton.setRolloverIcon(redIconHover);
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
		
		ieDialog = new ImportExportDialog(this,"Untitled.txt");
		
		this.add(titleBar,BorderLayout.PAGE_START);
		this.setVisible(true);
	}
	
	public SaveFrame(String fileName)
	{
		this();
		ieDialog.setDefaultFile(fileName);
	}
	
	public void setButtonEdited()
	{
		currentCloseButton.setIcon(redGrayIcon);
		currentCloseButton.setRolloverEnabled(false);
	}
	
	public void setButtonSaved()
	{
		currentCloseButton.setIcon(redIcon);
		currentCloseButton.setRolloverEnabled(true);
	}

	public abstract void save(String fileName);
	public abstract void load(String fileName);
}
