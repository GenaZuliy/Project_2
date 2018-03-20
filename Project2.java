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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


/**
 *
 * @author g_zuliy
 */
public class Project2 extends Application {
    
    private Checkpoint check = new Checkpoint();
    private Car car = new Car();
    private Timeline timer = new Timeline();
    
    @Override
    public void start(Stage primaryStage) {
        updateElements();
        Button btn = new Button();
        btn.setText("button");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
               
                car.drive();
                
                System.out.println(car.getxPos());
                System.out.println(car.getyPos());
            }
        });
        
         
        timer.setCycleCount(Timeline.INDEFINITE);
		timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1/30.0), e -> {
			
			car.drive();
			updateElements();
		}));
		timer.setAutoReverse(false);
		timer.play();

        
        
        Pane root = new Pane();
        root.getChildren().add(btn);
        //root.getChildren().add(check);
        root.getChildren().add(car);
        Scene scene = new Scene(root, 700, 600);
        
        
        primaryStage.setTitle("Hello World!");
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
    
    private void updateElements()
    {
        car.setTranslateX(car.getxPos());
        car.setTranslateY(car.getyPos());
    }
}
