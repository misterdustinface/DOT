import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 * 12/10/2013
 * @author dustin
 */
public class View{
	
    final public static int DEFAULT_WIDTH  = 400;
    final public static int DEFAULT_HEIGHT = 600;
    final public static int DEFAULT_FONT_SIZE = 14;
    
    final public static String DEFAULT_SEARCH_BAR_TEXT = "[Welcome]";
    
    
    // FONT STUFF ///////////////////////
    private Font			font;
    private int			fontStyle;
    private int			fontSize;
    /////////////////////////////////////
    
    // DISPLAY STUFF /////////////////
    private JFrame			frame;
    private JEditorPane		htmlDisplay;
    private JScrollPane		sp;        // Scrolling
    //////////////////////////////////
    
    // HYPERLINK PANEL ///////////////
    private JTextField searchBar;
    //////////////////////////////////
    
    
    // THE TOP MENU BAR //////////////
    private MenuBar			menuBar;  // The menu bar
    private Menu			fileMenu; // The "File" section
    private Menu			bookmarksMenu;
    private MenuItem		miSave;   // save option
    private MenuItem		miOpen;   // open option
    private MenuItem		miExit;   // exit option
    //////////////////////////////////
	
	public View(){
	    fontStyle 	= Font.PLAIN;
	    fontSize  	= DEFAULT_FONT_SIZE;
	    adjustFont();
		
	    // CREATING FRAME WITH ALL OF IT'S GOODIES [DISPLAY]
	    frame = new JFrame(Model.PROGRAM_TITLE + Model.DEFAULT_PAGE_NAME);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	    frame.setLocation((int)(Math.random() * 800), (int)(Math.random() * 400));

	    ////////////////////////////////////////////////////////////////////////
	    
	    // INITIALIZING THE MENU BAR
	    menuBar  = new MenuBar();
	    menuBar.setFont(font);
	    
	    // INITIALIZING THE FILE SECTION OF THE MENU BAR
	    fileMenu = new Menu(); 
	    fileMenu.setLabel("File"); // setting the name
	    
	    // INITIALIZING THE OPTIONS IN THE FILE SECTION
	    miSave = new MenuItem("Save As");
	    miSave.setEnabled(false);
	    miOpen = new MenuItem("Open");
	    miOpen.setEnabled(false);
	    miExit = new MenuItem("Exit");
	    
	    // ADDING THE OPTIONS TO THE FILE SECTION
	    fileMenu.add(miSave);
	    fileMenu.add(miOpen);
	    fileMenu.add(miExit);
	    
	    bookmarksMenu = new Menu();
	    bookmarksMenu.setLabel("Bookmarks");
	    
	    
	    // ADDING THE FILE SECTION TO THE MENU BAR
	    menuBar.add(fileMenu);
	    menuBar.add(bookmarksMenu);
	    
	    // ADD THE MenuBar to the display
	    frame.setMenuBar(menuBar);
	    
	    ////////////////////////////////////////////////////////////////////////
	    
	    // INITIALIZE SEARCH BAR
	    searchBar = new JTextField();
	    searchBar.setFont(font);
	    searchBar.setText(DEFAULT_SEARCH_BAR_TEXT);
	    
	    // INITIALIZING THE HTML AREA (area we primarily interact with)
	    htmlDisplay = new JEditorPane();
	    htmlDisplay.setFont(font);
	    
	    // INITIALIZING THE SCROLLING STUFF (having it hold the text area) 
	    sp = new JScrollPane(htmlDisplay);
	    
	    // ADD THE Notepad to the display
	    frame.add(sp, BorderLayout.CENTER);
	    frame.add(searchBar, BorderLayout.NORTH);
	    
	    // REFRESH
	    frame.validate();
	    frame.repaint();
	}
	
	public void changeFontSize(int amount){
		fontSize += amount;
	}
	
	public void changeFontStyle(int style){
		fontStyle = style;
	}
	
	public void adjustFont(){
		font = new Font(Font.MONOSPACED, fontStyle, fontSize);
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public void setDisplay(String page){
		try {
			setDisplay(new URL(page.trim()));
		} catch (MalformedURLException e) {
			showStandardWarningMessage("Malformed URL Exception: \"" + page + "\"", "Error Occured");
		}
	}
	
	public void setDisplay(URL page){
		try {
			htmlDisplay.setPage(page);
		} catch (IOException e) {
			showStandardWarningMessage("IO Exception: \"" + page.toString() + "\"", "Error Occured");
		}
	}
	
	public String getSearchBarText(){
		return searchBar.getText();
	}
	
	public void replaceSearchBarText(String text){
		searchBar.setText(text);
	}
	
    public void initActionListeners(Model model){
        // Add a reaction to the SAVE button
        miSave.addActionListener(model.getSaveActionListener());
        
        // Add an action to the OPEN button
        miOpen.addActionListener(model.getOpenActionListener());
      
        // Add an action to the EXIT button
        miExit.addActionListener(model.getExitActionListener());
        
        // Add "special keyboard commands"
        frame.addKeyListener(model.getSpecialKeyboardCommandListener());
        
        // Adds abilities to html display
        htmlDisplay.addKeyListener(model.getSpecialKeyboardCommandListener());
        htmlDisplay.addHyperlinkListener(model.getHyperlinkListener());
        
        // Adds hyperlink functionality for search bar
        searchBar.addKeyListener(model.getSpecialKeyboardCommandListener());
        searchBar.addActionListener(model.getSearchBarListener());
        
        ////////////////////////////////////////////////////////////////////////
        
        // Sketchy little addition that should probably be done in model or something.
    	for(Bookmark current: DOTprofile.getBookmarks()){
    		MenuItem temp = new MenuItem(current.getNickname());
    		temp.addActionListener(model.getBookmarkClickedListener(current));
    		bookmarksMenu.add(temp);
    	}

    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    public void showStandardWarningMessage(String message, String title){
    	JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    public boolean showStandardConfirmDialouge(String message, String title){
    	return JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }
    
}
