import java.util.LinkedList;
import java.util.Random;
import javafx.scene.control.Alert;
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
	  
	  this("Unnamed", 6, 5);
  }


    /**
     * Creates a Track object with specified name, amount of cars and checkpoints to spawn
     * @param trackName
     * @param numCars
     * @param numCheckpt
     */
  public Track(String trackName, int numCars, int numCheckpt){
	this.setMaxSize(600, 600);
	this.setPrefSize(600,600);
    this.name = trackName;
    // old : cars.setNumberOfCars(numberOfCars);
    this.numCars = numCars;
    cars = new Car[numCars];
    // old : checkpoints.setAmount(numberOfCheckpoints);
    this.numCheckpt = numCheckpt;
    checkpoints = new Checkpoint[numCheckpt];
    this.initCheckpts();
    this.initCars();
    //timer refreshes the track ever 1/60th of a second and updates the locations of the cars
    timer.setCycleCount(Timeline.INDEFINITE);
		timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1/60.0), e -> {
			
			this.moveCars();

		}));
		timer.setAutoReverse(false);
		//timer.play();
		
		
        
      
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
		  
		  Car c = new Car(asList(checkpoints), i + 1);
		  c.startTimer();
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
		  Checkpoint c = new Checkpoint(0,0,25);
		  Random rand = new Random();
		  if(i == 0)
			  c = new Checkpoint(rand.nextDouble() * 300,i * 100 + rand.nextDouble() * 100, 25);
		  else if(i == 1)
			  c = new Checkpoint(rand.nextDouble() * 300 + 300,i * 100 + rand.nextDouble() * 100, 25);
		  else if(i == 2)
			  c = new Checkpoint(rand.nextDouble() * 300 + 300 ,i * 100 + rand.nextDouble() * 100 + 100, 25);
		  else
			  c = new Checkpoint(rand.nextDouble() * 300 ,i * 100 + rand.nextDouble() * 100, 25);
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
	  if(!checkDone())
	  {
		  for(Car c : cars)
		  {
			  if(!c.isFinished())
			  {
				  c.drive();
			  }
			  else
			  {
				  c.stopTimer();
			  }
		  }  
	  }

  }
  
  public boolean checkDone()
  {
	  boolean done = true;
	  for(Car c : cars)
	  {
		  if(!c.isFinished())
		  {
			  done = false;
		  }
		  
	  }
	  if(done == true)
	  {
		  timer.stop();
		  System.out.println("GAME OVER!");
		  Alert alert = new Alert(Alert.AlertType.INFORMATION);
		  String s = "";
		  Car fastest = cars[0];
		  for(Car c : cars)
		  {	
			  if(c.getTotalTime() < fastest.getTotalTime())
			  {
				  fastest = c;
			  }
			  c.stopTimer();
			  s += c.toString() + '\n';
			  
		  }
		  alert.setHeaderText("CAR " + fastest.getID() + " WINS WITH A TIME OF " + String.valueOf(fastest.getTotalTime()).substring(0, 5) + "  Seconds");
		  alert.setTitle("GAME OVER");
		  alert.setWidth(400);
		  alert.setWidth(600);
		  alert.setContentText(s);
		  alert.show();
		  
	}
	 
	  return done;
  }


    /**
     * Starts timer
     */
   public void startRace()
   {
      timer.playFromStart();
   }
   
   public void stopRace()
   {
	   timer.stop();
   }

    /**
     * returns array of checkpoints as linkedlist
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
   
   public void removeCars()
   {
	   for(Car c : cars)
		   c = null;
   }
  
}