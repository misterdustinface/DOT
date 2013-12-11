
/**
 * A Minimalist Web Browser
 * 12/10/2013
 * @author dustin
 */
public class DOT {

	private Model model;
	private View  view;
	
	public DOT(){
		view  = new View();
		model = new Model(view);
	}
	
}
