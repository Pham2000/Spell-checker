/**
 * @name Tri Pham
 * @Filename Mainframe.java
 * @Assigment Project 2 ACP
 * @about all buttons and menu are access here
 */

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Mainframe extends Application {
	
	/*
	 * Private members of of what will be used in the program
	 */
	//Dictionary
	private Dictionary dictionary = new Dictionary();
	
	
	//MenuBar
	private MenuBar menuBar;
	//menus
	private Menu fileMenu;
	private Menu editMenu;
	//MenuItems
	private MenuItem openItem;
	private MenuItem SaveItem;
	private MenuItem exitItem;
	private MenuItem spellChecker;
	
	//TextField
	private TextArea textArea = new TextArea();
	
	//Vbox
	private Stage stage = new Stage();
	
	//Reader	
	private BufferedReader bufferRead;
	private Scanner scan;
	
	//writer
	private BufferedWriter out;
	
	
	//Pane
	private BorderPane root;
	private StackPane text;
	
	//String word dictionary
	private String add;
	private String missing;
	private String reverse;
	
	
	//create addWord for correct spelling
	private String addWord = "";
	
	//String ok
	private String ok = "ok";
	private String cancel = "cancel";
	
	
	/*
	 * Create Main Stage that include everything
	 */
	@Override
    public void start(Stage stage) {
		
		this.stage = stage;
 
        // Create MenuBar
        menuBar = new MenuBar();
 
        // Create menus
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");
 
        // Create MenuItems
        openItem = createOpenItem();
        SaveItem = createSaveItem();  
        exitItem = createExitItem();
        
        //Menu item SpellChecker
        spellChecker = createSpellCheckerItem();
  
        // Add menuItems to the Menus
        fileMenu.getItems().addAll(openItem, SaveItem, exitItem);
        
        // Add menuItem to Edit
        editMenu.getItems().addAll(spellChecker);
 
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu);
 
        // Add BorderPane
        root = new BorderPane();
        //Add StackPane
        text = new StackPane();
        
        //add TextArea to StackPane
        text.getChildren().setAll(textArea);
        
        
        // comment this to see that menu bar actually goes in the scene space
        root.setTop(menuBar);
        root.setCenter(text);
        
        Scene scene = new Scene(root, 400, 400);
        
        this.stage.setTitle("Editor and Spelling Checker PhamT");
        this.stage.setScene(scene);
        this.stage.show();
    }
    
	/*
	 * Button to be able to spell check all words
	 */
    private MenuItem createSpellCheckerItem()
    {
        MenuItem newItem = new MenuItem("Spell Check");
        
        newItem.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	String current = textArea.getText();
            	//split words but also not contain any punctuation
            	String[] words = current.replaceAll("\\p{Punct}", "").toLowerCase().split("\\s+");
            	
            	
            	for(int i = words.length - 1; i > 0; i--) {
            		Alert a = new Alert(AlertType.CONFIRMATION);
            		if(!dictionary.contains(words[i])) {
            			
            			addWord = "Misspelling: " + words[i] + "\n";
            			
            			//Calling methods in dictionary to check if word is correct
            			if(dictionary.Missing(words[i]) != "0") {
            				missing = dictionary.Missing(words[i]);
            				addWord += missing + "\n";
            			}
            			if(dictionary.Add(words[i]) != "0") {
            				add = dictionary.Add(words[i]);
            				addWord += add + "\n";
            			}
            			if(dictionary.Reverse(words[i]) != "0") {
            				reverse = dictionary.Reverse(words[i]);
            				addWord += reverse;
            			}
            			
            			a.setContentText(addWord);
            			a.show();

            		}
            	}
            }
        });     
        return newItem;
    }

    /*
     * Button to open files in Current Directory
     */
    private MenuItem createOpenItem()
    {
        MenuItem newItem = new MenuItem("Open");
        
        newItem.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	FileChooser chooser = new FileChooser();
            	//FileChooser allow direct access to user directory
            	chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                File selectedFile = chooser.showOpenDialog(stage);
                if(selectedFile != null) {
                	selectedFile.getAbsoluteFile();
                }
                if(selectedFile == null) {
                	return ; //Do nothing to prevent NULL error
                }
               
        		try {
        			//Read in the selected File to textArea
					bufferRead = new BufferedReader(new FileReader(selectedFile));
					scan = new Scanner(bufferRead);
					String current = "";
					while(scan.hasNextLine()) {
						current += scan.nextLine() + "\n";
						textArea.setText(current);
    
					}
					scan.close();
        		}
        		catch(IOException error) {
        			error.printStackTrace();
        		}
        	
            }
        });     
        return newItem;
        
     }
    
     /*
      * Button to Save files to current Directory
      */
     private MenuItem createSaveItem()
     {
        MenuItem openFileItem = new MenuItem("Save");
        openFileItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	//FileChooser allow direct access to user directory
            	FileChooser chooser = new FileChooser();
            	chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                File selectedFile = chooser.showSaveDialog(stage);
            	if(selectedFile != null) {
            		try {
            			//Save the TextArea to file of desire
            			out = new BufferedWriter(new FileWriter(selectedFile));
            			out.write(textArea.getText());
            			out.close();
            		}
            		catch(IOException error) {
            			error.printStackTrace();
            		}
            	}
            	
            	if(selectedFile == null) {
                	return ; //Do nothing
                }
                
            }
        });       
        return openFileItem;
     }
     
     /*
      * Exit button to exit the file
      */
     private MenuItem createExitItem()
     {
        MenuItem exitItem = new MenuItem("Exit");

        // When user click on the Exit item
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        return exitItem;
     }
     

     
     
	
}
