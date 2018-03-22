import java.util.LinkedList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Track initializes Car and Checkpoint. It also refreshes to show the cars moving along the track.
 */
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
	  
	  this("Unnamed", 1, 4);
  }


    /**
     * Creates a Track object with specified name, amount of cars and checkpoints to spawn
     * @param trackName
     * @param numCars
     * @param numCheckpt
     */
  public Track(String trackName, int numCars, int numCheckpt){
    this.name = trackName;
    // old : cars.setNumberOfCars(numberOfCars);
    this.numCars = numCars;
    cars = new Car[numCars];
    // old : checkpoints.setAmount(numberOfCheckpoints);
    this.numCheckpt = numCheckpt;
    checkpoints = new Checkpoint[numCheckpt];
    initCheckpts();
    initCars();

    //timer refreshes the track ever 1/60th of a second and updates the locations of the cars
    timer.setCycleCount(Timeline.INDEFINITE);
		timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1/60.0), e -> {
			
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
            		
            		//c.setRotate(c.turn(r.nextInt(360)));
            		c.drive();
           
            	}

            }
        });
        this.getChildren().add(btn);
        
      
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

    /**
     * Returns the name of the track
     * @return name
     */
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

    /**
     * Initializes the cars to the given checkpoints location facing the direction of their next checkpoint
     */
  public void initCars()
  {
	  for(int i = 0; i < numCars; i++)
	  {
		  
		  Car c = new Car(50,50,asList(checkpoints));
		  //double angle = angleBetweenCheckpt(checkpoints[c.getLastCheckpt()],checkpoints[getNextCheckpt(c)]);
		  //c.setRotate(angle);
		  c.turn();
		  
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

    /**
     * Initializes the Checkpoints to a random location
     */
    public void initCheckpts()
  {
	  for(int i = 0; i < numCheckpt; i++)
	  {	
		  Random rand = new Random();
		  Checkpoint c = new Checkpoint(rand.nextDouble() * 600,i * 100 + rand.nextDouble() * 100, 30);
		  c.setTranslateX(c.getX());
		  c.setTranslateY(c.getY());
		  checkpoints[i] = c;
		  this.getChildren().add(c);
	  }
  }

    /**
     * Moves each car to it's next checkpoint
     */
  public void moveCars()
  {
	  
	  for(Car c : cars)
	  {
		  c.drive();
		  //c.drive(distBetweenCheckpt(checkpoints[c.getLastCheckpt()],checkpoints[getNextCheckpt(c)]));
		  
	  }
  }

    /**
     * Finds the distance between two checkpoints
     * @param a checkpoint 1
     * @param b checkpoint 2
     * @return distance between the two checkpoints
     */
  public double distBetweenCheckpt(Checkpoint a, Checkpoint b)
  {
	  double xa = a.getTranslateX();
	  double ya = a.getTranslateY();
	  double xb = b.getTranslateX();
	  double yb = b.getTranslateY();
	  
	  return Math.sqrt(Math.pow(xb-xa,2) + Math.pow(yb-ya,2));
  }

    /**
     * Finds the angle between two checkpoints
     *@param a checkpoint 1
     * @param b checkpoint 2
     * @return angle between the two checkpoints
     */
  public double angleBetweenCheckpt(Checkpoint a, Checkpoint b)
  {
	  double xa = a.getTranslateX();
	  double ya = a.getTranslateY();
	  double xb = b.getTranslateX();
	  double yb = b.getTranslateY();
	  
	  return Math.toDegrees(Math.atan2(yb-ya, xb-xa));
  }


    /**
     * Starts timer
     */
   public void startRace()
   {
      //start timer
   }

    /**
     * Adds Checkpoint objects to the LinkedList
     * @param array of Checkpoints
     * @return LL the LinkedList
     */
   public LinkedList<Checkpoint> asList(Checkpoint[] array)
   {
	   
	   LinkedList<Checkpoint> LL = new LinkedList<>();
	   for(Checkpoint c : checkpoints)
	   {
		   LL.add(c);
	   }
	   return LL;
   }
  
}