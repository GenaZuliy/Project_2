/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/**
 *
 * @author peter
 */
public class Car extends Canvas {
    
    
    private Vector velocity;
    private Vector position;
    private Vector acceleration;
    private double engine; //max force
    private double tires; //max turn angle
    private int ID;
    private double frictionConstant;
    private double mass;
    private double xPos;
   // private Vector acceleration = new Vector();
    private double tireIncrement = 10;
    private double yPos;
    private double yTraveled;
    private double xTraveled;
    
    private double tireAngle;
    private double engineForce;
    private LinkedList<Checkpoint> checkpoints;
    private GraphicsContext gc = this.getGraphicsContext2D();
    private double driveAngle;

    /**
     * Initialize the car object with the given specifications
     */

    public Car()
    {   
        super(50,20);
        this.setManaged(false);
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 50, 20);
        this.engine =  5000;
        this.engineForce = Math.random() * 5000;
        this.frictionConstant = 0.9;
        this.mass = 1000;
        this.velocity = new Vector();
        this.driveAngle = 0;
        
        
        
    }

    /**
     * Initialize the car object at given x/y coordinate with the given specifications as well as creating a checkpoint at the same spot
     * @param x the x coordinate
     * @param y the y coordinate
     * @param LL the checkpoint
     */

    public Car(double x, double y, LinkedList<Checkpoint> LL)
    {   
        super(50,20);
        this.setManaged(false);
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 50, 20);
        this.checkpoints = (LinkedList<Checkpoint>) LL.clone();
        this.engine =  5000; 
        this.engineForce = Math.random() * 3000 + 2000;
        this.frictionConstant = .001 ;
        this.mass = 1000;
        this.acceleration = new Vector((engineForce - (frictionConstant * 9.8 * mass))/ mass, driveAngle);
        this.velocity = new Vector();
        this.position = new Vector(checkpoints.getFirst().getTranslateX(),checkpoints.getFirst().getTranslateY());
        this.driveAngle = 0;
    	position.addCartesian(velocity);

        
    }

    /**
     *Returns the size of the velocity vector
     *@return velocity.getSize()
     */
    public double getSpeed(){
        return velocity.getSize();
    }

    /**
     * Calls the turn and move methods to turn and move the car in a certain direction
     */
    public void drive(){
        checkpointDistance();
        turn(); 
        move();
    }

    /**
     * Calculates the distance between two given checkpoints
     * @return dist The distance between the two given checkpoints
     */
    private double checkpointDistance() {
        double dist = Math.sqrt(Math.pow(checkpoints.getFirst().getTranslateX() - position.getx(), 2) + Math.pow(checkpoints.getFirst().getTranslateY() - position.gety(), 2));
        if(dist < 25)
        {
        	acceleration.multiplyScaler(0);
        	checkpoints.removeFirst();
        }
        return dist;
    }

    /**
     * Creates and returns a copy of Checkpoint
     * @param LL
     */
    public void setCheckpoints (LinkedList<Checkpoint> LL){
        this.checkpoints = (LinkedList<Checkpoint>)LL.clone();
    }

    /**
     * Rotates the car by a certain degree to face the next checkpoint
     */
    public void turn(){
    	

    		driveAngle = angleToCheckpt(checkpoints.get(2));
    		//acceleration.multiplyScaler(0);
    	
    }

    /**
     * Finds the angle in which to rotate at to get to the next checkpoint
     * @param b the checkpoint to rotate to
     * @return ans the angle to rotate
     */
    public double angleToCheckpt(Checkpoint b)
  {
  	  double xb = position.getx();
  	  double yb = position.gety();
  	  double xa = b.getTranslateX();
  	  double ya = b.getTranslateY();
  	  
	  double ans =  Math.toDegrees(Math.atan2(yb-ya, xb-xa));
	  
	  return ans; 
  }

    /**
     * Finds the distance to next checkpoint and accelerates accordingly to the checkpoint
     */
    private void move(){
    	checkpointDistance();
    	acceleration.addPolar((engineForce/mass)-(frictionConstant*mass*9.8), driveAngle);    	
    	acceleration.multiplyScaler(1/100.0);
        velocity.addCartesian(acceleration);
    	position.addCartesian(velocity);
        this.setTranslateX(position.getx());
        this.setTranslateY(position.gety());
        System.out.println(this.toString());
    }
    public void calcAcc()
    {
       // acceleration = new Vector );
        //acceleration.addPolar((engineForce/mass)-(frictionConstant*mass*9.8), (tireAngle/2) + velocity.getAngle());
    }

    /**
     * Calculates the velocity of the car using the velocity vector's size and angle
     */
    public void calcVelocity()
    {
       // velocity.addCartesian(acceleration);
        velocity = new Vector(velocity.getSize(), driveAngle);
        //System.out.println(velocity);
    }

    public void calcPosition()
    {
    	position.addCartesian(velocity);
    }

    /**
     * Returns cars x position
     * @return xPos
     */
    public double getxPos() {
        return xPos;
    }

    /**
     * Returns cars y position
     * @return yPos
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * Returns angle of car's velocity
     * @return velocity.getAngle()
     */
    public double getAngle()
    {
    	return velocity.getAngle();
    }
    
   
   @Override
   /**
    * Returns the x/y positions of the car as well as angle it is facing and its acceleration
    */
    public String toString()
    {
    	return "x : " + position.getx() + " y : " + position.gety() +  " v Angle : " + driveAngle +  "  " +  "  AccL: " + acceleration.getSize();
    }
}
