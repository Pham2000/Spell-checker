import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;

/**
 * Create a stage to transport over Mainframe
 *
 *
 */
public class Main extends Application {
	 
    @Override
    public void start(Stage stage) {
    	Mainframe MenuBar = new Mainframe(); 
    	MenuBar.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
        
    }
 
}
