import java.util.LinkedList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Track extends Pane{
  
  private String name;
  private Car[] cars;
  private Checkpoint[] checkpoints;
  private int numCars;
  private int numCheckpt;
  private Timeline timer = new Timeline();
  
  public Track(){
	//old:
    //name = "Unnamed";
    //cars.setNumberOfCars(1);
    //checkpoints.setAmount(2);
	  
	  this("Unnamed", 2, 4);
  }
  
  
  
  public Track(String trackName, int numCars, int numCheckpt){
    this.name = trackName;
    // old : cars.setNumberOfCars(numberOfCars);
    this.numCars = numCars;
    cars = new Car[numCars];
    // old : checkpoints.setAmount(numberOfCheckpoints);
    this.numCheckpt = numCheckpt;
    checkpoints = new Checkpoint[numCheckpt];

    
    timer.setCycleCount(Timeline.INDEFINITE);
		timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1/30.0), e -> {
			
			this.moveCars();

		}));
		timer.setAutoReverse(false);
		timer.play();
		
		Button btn = new Button();
        btn.setText("Click to change direction");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            	Random r = new Random();
            	for(Car c : cars)
            	{
            		
            		c.setRotate(c.turn(r.nextInt(360)));
            		c.drive(400);
           
            	}

            }
        });
        this.getChildren().add(btn);
        
        initCheckpts();
        initCars();

  }
 

//  public String checkWinner(){
//    for (Car c : cars){
//      if (c.getCheckpoints().getFirst() == null)
//        return c.getName();
//    }
//  }
  
//^^^should really do something else
// if all cars done: search for car with lowest time
// return the actual car the won, not a string. 
  
  
  public String getName(){
    return name; 
  }
  
//  public Car setNumberOfCars(int numCars){
//    for (int i = 0; i < numberOfCars; i++){
//      Car = new Car("Car: " + i);
//    }
//  }
  
  //^^^ should not return Car --> void
  //should rewrite into an initCars() method

  public void initCars()
  {
	  for(int i = 0; i < numCars; i++)
	  {
		  
		  Car c = new Car(checkpoints[0].getTranslateX(),checkpoints[0].getTranslateY(),0);
		  double angle = angleBetweenCheckpt(checkpoints[c.getLastCheckpt()],checkpoints[getNextCheckpt(c)]);
		  c.setRotate(angle);
		  c.turn(angle);
		  
		  cars[i] = c;
		  
		  this.getChildren().add(c);
	  }
  }
  
  
//   public Checkpoint setNumberOfCheckpoints(int numberOfCheckpoints){
//    for (int i = 0; i < numberOfCheckpoints; i++){
//     Checkpoint = new Checkpoint("Checkpoint: " + i);
//    }
//  }
  
  // ^^^ more like an initCheckpoints() method
  // should return void, and use global variable 'numCheckpt' not method parameter. 
  
  public void initCheckpts()
  {
	  for(int i = 0; i < numCheckpt; i++)
	  {	
		  Random rand = new Random();
		  Checkpoint c = new Checkpoint();
		  c.setTranslateX((rand.nextInt(100) * 6));
		  c.setTranslateY(i * 100 + rand.nextInt(100) );
		  checkpoints[i] = c;
		  this.getChildren().add(c);
	  }
  }
  
  public void moveCars()
  {
	  for(Car c : cars)
	  {

		  c.drive(distBetweenCheckpt(checkpoints[c.getLastCheckpt()],checkpoints[getNextCheckpt(c)]));

		  Checkpoint dest = checkpoints[getNextCheckpt(c)];
		  
	  }
  }
  
  public double distBetweenCheckpt(Checkpoint a, Checkpoint b)
  {
	  double xa = a.getTranslateX();
	  double ya = a.getTranslateY();
	  double xb = b.getTranslateX();
	  double yb = b.getTranslateY();
	  
	  return Math.sqrt(Math.pow(xb-xa,2) + Math.pow(yb-ya,2));
  }
  
  public double angleBetweenCheckpt(Checkpoint a, Checkpoint b)
  {
	  double xa = a.getTranslateX();
	  double ya = a.getTranslateY();
	  double xb = b.getTranslateX();
	  double yb = b.getTranslateY();
	  
	  return Math.toDegrees(Math.atan2(yb-ya, xb-xa));
  }
  
  private int getNextCheckpt(Car c)
  {
	  return (c.getLastCheckpt() + 1) % numCheckpt;
  }
  
 
   public void startRace()
   {
      //start timer
   }
  
}