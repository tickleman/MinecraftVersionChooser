import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

//######################################################################################## Launcher
public class Launcher
{

	//-------------------------------------------------------------------------------------- download
	private static boolean download(String urlOfFile)
	{
		String fileName = urlOfFile.substring(urlOfFile.lastIndexOf("/") + 1);
		File file = new File(fileName);
		// a new version of minecraft ? no problem, try to download it !
		if (!file.exists()) {
			FileOutputStream writeFile = null;
			try {
				// open url
				URL url = new URL(urlOfFile);
				URLConnection connection = url.openConnection();
				if (connection.getContentLength() < 100000) {
					throw new Exception();
				}
				// copy file
				System.out.println("Please wait while downloading " + fileName + " :) ...");
				writeFile = new FileOutputStream(file);
				InputStream input = connection.getInputStream();
				byte[] buffer = new byte[10240];
				int read;
				while ((read = input.read(buffer)) > 0) {
					writeFile.write(buffer, 0, read);
				}
				// close file
				writeFile.flush();
				writeFile.close();
			} catch (Exception e) {
				// close written file
				if (writeFile != null) try {
					writeFile.close();
				} catch (Exception e2) {
				}
				// delete file
				if (file.exists()) {
					file.delete();
				}
			}
		}
		return file.exists();
	}

	//------------------------------------------------------------------------------------------ main
	public static void main(String [] args)
	{
		boolean done = false;
		System.out.println("Minecraft Version Chooser version 1.0 by Cannabeuh & Tickleman");
		System.out.println("-------------------------");
		System.out.println("");
		System.out.println("7 : Minecraft 1.7.3");
		System.out.println("");
		System.out.println("8 : Minecraft 1.8.1");
		System.out.println("");
		System.out.println("Your choice ?");
		String choice = new Scanner(System.in).nextLine();
		if (download("http://plugins.crafter.fr/depot/minecraft/minecraft-1." + choice + ".jar")) {
			// copy the file into the user's home directory
			System.out.println("Copying minecraft.jar into Minecraft's directory ...");
			// windows copy
			File file = new File("minecraft-1." + choice + ".jar");
			File dest = new File(System.getenv("APPDATA") + "\\.minecraft\\bin\\minecraft.jar");
			if (dest.exists()) {
				dest.delete();
				file.renameTo(dest);
			}
			if (download("http://plugins.crafter.fr/depot/minecraft/Minecraft.exe")) {
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("Minecraft.exe");
					done = true;
				} catch (Exception e) {
					System.out.println("Could not launch Minecraft, sorry !");
				}
			} else {
				System.out.println("Could not get Minecraft's launcher sorry !");
			}
		} else {
			System.out.println("Could not get Minecraft version 1." + choice + " sorry !");
		}
		if (!done) {
			System.out.println("Press enter to exit.");
			new Scanner(System.in).nextLine();
		}
	}

}
