import java.util.LinkedList;

public class Track{
  
  private String name;
  private Car[] cars;
  private Checkpoint[] checkpoints;
  
  public Track(){
	//old:
    //name = "Unnamed";
    //cars.setNumberOfCars(1);
    //checkpoints.setAmount(2);
	  
	
  }
  
  public Track(String trackName, int numCars, int numCheckpt){
    this.name = trackName;
    // old : cars.setNumberOfCars(numberOfCars);
    cars = new Car[numCars];
    // old : checkpoints.setAmount(numberOfCheckpoints);
    checkpoints = new Checkpoint[numCheckpt];
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
  
  
//   public Checkpoint setNumberOfCheckpoints(int numberOfCheckpoints){
//    for (int i = 0; i < numberOfCheckpoints; i++){
//     Checkpoint = new Checkpoint("Checkpoint: " + i);
//    }
//  }
  
  // ^^^ more like an initCheckpoints() method
  // should return void, and use global variable 'numCheckpt' not method parameter. 
   
   public void startRace(){
     
   }
  
}