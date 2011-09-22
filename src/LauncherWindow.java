import java.awt.Color;

import javax.swing.JFrame;

//################################################################################## LauncherWindow
public class LauncherWindow extends JFrame
{

	private static final long serialVersionUID = -6822599785389987014L;

	//-------------------------------------------------------------------------------- LauncherWindow
	public LauncherWindow()
	{
		this.setTitle("Minecraft Version Chooser");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new LauncherWindowContent());
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.GRAY);
		this.pack();
	}

	//-------------------------------------------------------------------------------- getWindowWidth
	public int getWindowWidth()
	{
		return this.getWidth();
	}

	//------------------------------------------------------------------------------- getWindowHeight
	public int getWindowHeight()
	{
		return this.getHeight();
	}

	//------------------------------------------------------------------------------- getWindowHeight
	public static void main(String args[])
  {
  	new LauncherWindow();
  }

}