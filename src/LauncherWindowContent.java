import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//########################################################################### LauncherWindowContent
public class LauncherWindowContent extends JPanel implements ActionListener
{

	private static final long serialVersionUID = -7378373415982170213L;

	private JButton button_173      = new JButton("Launch Minecraft 1.7.3");
	private JButton button_181      = new JButton("Launch Minecraft 1.8.1");
	private JButton button_19p      = new JButton("Launch Minecraft 1.9-pre");
	private JButton button_173_chat = new JButton("Launch Minecraft 1.7.3");
	private JButton button_181_chat = new JButton("Launch Minecraft 1.8.1");
	private JButton button_about    = new JButton("About");
	private JLabel  labelFooter     = new JLabel(
		"You MUST close running Minecraft before clicking any button !"
	);

	//------------------------------------------------------------------------- LauncherWindowContent
	public LauncherWindowContent()
	{
		// action listeners
		button_173.addActionListener(this);
		button_181.addActionListener(this);
		button_19p.addActionListener(this);
		button_173_chat.addActionListener(this);
		button_181_chat.addActionListener(this);
		button_about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				labelFooter.setText("(c) Cannabeuh & Tickleman");
			}
		});

		// gui

		JPanel westnorth = new JPanel(new BorderLayout(5, 5));
		westnorth.add(new JLabel("Raw Minecraft"), BorderLayout.NORTH);

		JPanel westsouth = new JPanel(new BorderLayout(5, 5));
		westsouth.add(button_173, BorderLayout.NORTH);
		westsouth.add(button_181, BorderLayout.CENTER);
		westsouth.add(button_19p, BorderLayout.SOUTH);

		JPanel west      = new JPanel(new BorderLayout());
		west.add(westnorth, BorderLayout.NORTH);
		west.add(westsouth, BorderLayout.SOUTH);

		JPanel eastnorth = new JPanel(new BorderLayout(5, 5));
		eastnorth.add(new JLabel("MC + Advanced Chat"), BorderLayout.NORTH);

		JPanel eastsouth = new JPanel(new BorderLayout(5, 5));
		eastsouth.add(button_173_chat, BorderLayout.NORTH);
		eastsouth.add(button_181_chat, BorderLayout.CENTER);
		eastsouth.add(button_about,    BorderLayout.SOUTH);

		JPanel east      = new JPanel(new BorderLayout());
		east.add(eastnorth, BorderLayout.NORTH);
		east.add(eastsouth, BorderLayout.SOUTH);

		JPanel header    = new JPanel(new BorderLayout(5, 5));
		header.add(new JLabel("Choose your version of minecraft"), BorderLayout.CENTER);

		JPanel center    = new JPanel(new BorderLayout(5, 5));
		center.add(west, BorderLayout.WEST);
		center.add(east, BorderLayout.EAST);

		JPanel south     = new JPanel(new BorderLayout(5, 5));
		south.add(labelFooter, BorderLayout.CENTER);

		this.setLayout(new BorderLayout(5, 5));
		this.add(header, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south,  BorderLayout.SOUTH);
	}

	//------------------------------------------------------------------------------- actionPerformed
	@Override
	public void actionPerformed(ActionEvent event)
	{
		// choose version
		String version = "7";
		if (event.getSource().equals(button_173)) {
			version = "7";
		} else if (event.getSource().equals(button_181)) {
			version = "8";
		} else if (event.getSource().equals(button_19p)) {
			version = "9";
		} else if (event.getSource().equals(button_173_chat)) {
			version = "7-chat";
		} else if (event.getSource().equals(button_181_chat)) {
			version = "8-chat";
		}
		Thread t = new Thread() {
			public void run() {
				// download
				if (Launcher.download(
					"http://plugins.crafter.fr/depot/minecraft/minecraft-1." + this.getName() + ".jar",
					labelFooter
				)) {
					// copy the file into the user's home directory
					labelFooter.setText("Copying minecraft.jar into Minecraft's directory ...");
					// windows copy
					File file = new File("minecraft-1." + this.getName() + ".jar");
					File dest = new File(System.getenv("APPDATA") + "\\.minecraft\\bin\\minecraft.jar");
					if (dest.exists()) {
						dest.delete();
						file.renameTo(dest);
					}
					if (Launcher.download(
						"http://plugins.crafter.fr/depot/minecraft/Minecraft.exe",
						labelFooter
					)) {
						Runtime runtime = Runtime.getRuntime();
						try {
							runtime.exec("Minecraft.exe");
							labelFooter.setText("You MUST close running Minecraft before clicking any button !");
						} catch (Exception e) {
							labelFooter.setText("Could not launch Minecraft, sorry !");
						}
					} else {
						labelFooter.setText("Could not get Minecraft's launcher sorry !");
					}
				} else {
					labelFooter.setText("Could not get Minecraft version 1." + this.getName() + " sorry !");
				}
			}
		};
		t.setName(version);
		t.start();
	}

}
