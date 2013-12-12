import java.net.URL;


public class Bookmark{

	private String nickname;
	private URL	 	url;
	
	public Bookmark(URL url){
		this.url = url;
		nickname = url.toString();
	}
	
	public void setNickname(String name){
		nickname = name;
	}
	
	public String getNickname(){
		return nickname;
	}
	
	public URL getURL(){
		return url;
	}

}
