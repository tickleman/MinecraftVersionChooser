import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JLabel;

//######################################################################################## Launcher
public class Launcher
{

	// ------------------------------------------------------------------------------------- download
	public static boolean download(String urlOfFile, JLabel label)
	{
		String fileName = urlOfFile.substring(urlOfFile.lastIndexOf("/") + 1);
		label.setText("Please wait while downloading " + fileName + " :) ...");
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
				if (writeFile != null)
					try {
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

}
