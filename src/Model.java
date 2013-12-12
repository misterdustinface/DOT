import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * 12/10/2013
 * @author Dustin
 */
public class Model {

    /////////////////////
    //    CONSTANTS    //
    /////////////////////
    
    final public static String PROGRAM_TITLE     = "DOT";
    final public static String DEFAULT_PAGE_NAME = "";
    
    private int keyCode;
    private View view;
    
    public Model(View view){
    	this.view = view;
    	
        DOTprofile.addTestBookmark(); // little sketchy addition to test
    	
    	view.initActionListeners(this);
    } 
  
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    //////////////////////////////
    //		  SAVE ACTION		//
    //////////////////////////////
    
    
    public ActionListener getSaveActionListener(){
    	return saveAction;
    }
    
    private static void saveOp(File f)
    {
//        // Attempt to do something
//        try {
//            // Make a bunch of shit that will allow us to write to the file
//            FileWriter     fw = new FileWriter(f.getAbsoluteFile());
//            BufferedWriter bw = new BufferedWriter(fw);
//
//            // Write the text that's in our notepad to the file
//            bw.write(textArea.getText());
//
//            // Close the "stream"
//            bw.close();
//
//        // If the attempt fails, display the error message - then give up.
//        } catch (IOException ioe) {
//            JOptionPane.showMessageDialog(frame, "PROBLEM SAVING");
//        }
    }
    
    private ActionListener saveAction = new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {

//            // Open up the Save file screen, if "Save" is pressed
//            if(jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
//            {
//               
//                // Get the selected file
//                currentFile = jfc.getSelectedFile();
//
//                // if file doesn't exist, create it
//                if (!currentFile.exists()) {
//                    try {
//                        currentFile.createNewFile();
//                    } catch (IOException ex) {
//                         JOptionPane.showMessageDialog(frame, "PROBLEM CREATING FILE");
//                    }
//                }
//
//                // Saves the file
//                saveOp(currentFile);
//
//            }
//            
//            // Set the top title to the name of the document
//            frame.setTitle(PROGRAM_TITLE + currentFile.getName());
        }
    };
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    //////////////////////////////
    //		  OPEN ACTION		//
    //////////////////////////////
    
    public ActionListener getOpenActionListener(){
    	return openAction;
    }
    
    private static void openOp(){

    }
    
    private ActionListener openAction = new ActionListener(){
    	 @Override
         public void actionPerformed(ActionEvent e) {
             openOp();
    	 }
    };
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //////////////////////////////
    //		  EXIT ACTION		//
    //////////////////////////////
    
    public ActionListener getExitActionListener(){
    	return exitAction;
    }
    
	private ActionListener exitAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			exitOp();
		}
	};
	
	private void exitOp(){
		
		if(view.showStandardConfirmDialouge("Are you sure you want to exit?", "Confirm Exit")){
			view.getFrame().setFocusable(false);
			view.getFrame().setVisible(false);
			view.getFrame().setEnabled(false);
			view.getFrame().dispose();
		}
	}
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    //////////////////////////////
    // SPECIAL KEYBOARD ACTION	//
    //////////////////////////////
    
    public KeyListener getSpecialKeyboardCommandListener(){
    	return keyboardSpecialCommands;
    }
    
    private KeyListener keyboardSpecialCommands = new KeyListener(){

        @Override
        public void keyTyped(KeyEvent e) {    }

        @Override
        public void keyPressed(KeyEvent e) {
                   
            keyCode = e.getKeyCode();
            
            if(e.isControlDown()){
            	if(keyCode == KeyEvent.VK_S){
            		//saveOp(currentFile);
            	}else if(keyCode == KeyEvent.VK_O){
            		openOp();
            	}
            }
            
            if(keyCode == KeyEvent.VK_ESCAPE){
            	exitOp();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {    }
    	
    };
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////

    public HyperlinkListener getHyperlinkListener(){
    	return hyperlinkCommand;
    }
    
    private HyperlinkListener hyperlinkCommand = new HyperlinkListener(){
		@Override
		public void hyperlinkUpdate(HyperlinkEvent event) {
			view.setDisplay(event.getURL());
		}
    };
    
	/////////////////////////////////////////////////////////////////////////////////////////////////////    
    public ActionListener getSearchBarListener(){
    	return searchBarListener;
    }
    
    private ActionListener searchBarListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent event) {
			if(! view.getSearchBarText().contains("http://")
			&& ! view.getSearchBarText().contains("https://")){
				view.replaceSearchBarText("http://" + view.getSearchBarText().trim());
			}
			
			view.setDisplay(view.getSearchBarText());
		}
    };
    
	/////////////////////////////////////////////////////////////////////////////////////////////////////  
    
    public ActionListener getBookmarkClickedListener(final Bookmark b){
    	return new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			view.replaceSearchBarText(b.getURL().toString());
    			view.setDisplay(b.getURL());
    		}
        	
        };
    }

}
