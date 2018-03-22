/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;

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
 * @author g_zuliy
 */
public class Project2 extends Application {

	private Track track;

	@Override
	public void start(Stage primaryStage) {

		VBox root = new VBox();
		HBox bottom = new HBox();
		bottom.setSpacing(360);
		root.setPadding(new Insets(50.0));
		track = new Track();
		root.getChildren().add(0,track);
		Button start = new Button("  START   RACE   ");
		Button reset = new Button("  RESET   RACE   ");
 
        start.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            	track.startRace();
            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            	track.stopRace();
            	track.removeCars();
            	track = new Track();
            	root.getChildren().remove(0);
            	System.out.println("NEW");
            	root.getChildren().add(0,track);
            }
        });
        bottom.getChildren().add(start);
        bottom.getChildren().add(reset);
        root.getChildren().add(bottom);

		Scene scene = new Scene(root, 700, 600);

		primaryStage.setTitle("Race Game");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);

	}
	
	public void reset()
	{
		
	}

	public void init() {

	}

}
