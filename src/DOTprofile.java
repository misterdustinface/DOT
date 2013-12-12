import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;


/**
 * DATA CONTAINER
 * 12/10/2013
 * @author dustin
 */
public class DOTprofile {

	private static LinkedList<Bookmark> bookmarks = new LinkedList<Bookmark>();
	
	public DOTprofile(){
		
	}
	
	public static void addTestBookmark(){
		try {
			Bookmark temp = new Bookmark(new URL("https://www.google.com"));
			temp.setNickname("Google");
			bookmarks.add(temp);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void readBookmarksFromFile(File file){
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = reader.readLine();
			
			while(line != null)
			{
				Bookmark temp = new Bookmark(new URL(line));
				temp.setNickname(line);
				bookmarks.add(temp);
				
				line = reader.readLine();
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static LinkedList<Bookmark> getBookmarks(){
		return bookmarks;
	}
}
