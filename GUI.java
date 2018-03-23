//##### IMPORTS ######
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Creates the Track object and adds it to the Stage
 * @author Gena, Andrew, Peter
 */
public class GUI extends Application {

	// ###### INSTANCE VARIABLES ######
	private Track track;
	private VBox root;
	private HBox bottom;
	
	
	@Override
	public void start(Stage primaryStage) {

		root = new VBox();
		bottom = new HBox();
		bottom.setSpacing(360);
		root.setPadding(new Insets(50.0));
		track = new Track();
		root.getChildren().add(0,track);
		Button start = new Button("  START   RACE   ");
		Button reset = new Button("  RESET   RACE   ");
 
        start.setOnAction(event -> track.startRace());
        
        reset.setOnAction(event -> reset());
        
        bottom.getChildren().add(start);
        bottom.getChildren().add(reset);
        root.getChildren().add(bottom);

		Scene scene = new Scene(root, 700, 600);

		primaryStage.setTitle("Racing Game Prototype v1.0");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	
	public void reset()
	{
		track.stopRace();
    	track = new Track();
    	root.getChildren().remove(0);
    	root.getChildren().add(0,track);
	}

	public void init() {

	}

}
