/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;




/**
 *
 * @author g_zuliy
 */
public class Project2 extends Application {
    
private Track track = new Track();
	
    @Override
    public void start(Stage primaryStage) {

        
         
 

        
        
        Pane root = new Pane();
    
        root.getChildren().add(track);
        
        Scene scene = new Scene(root, 700, 600);
        
        
        primaryStage.setTitle("Race Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
    public void init()
    {
    	
    }
    

}
