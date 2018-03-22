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
    
    public Car(double x, double y, LinkedList<Checkpoint> LL)
    {   
        super(50,20);
        this.setManaged(false);
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 50, 20);
        this.checkpoints = (LinkedList<Checkpoint>) LL.clone();
        this.engine =  5000; 
        this.engineForce = Math.random() * 3000 + 2000; 
        this.frictionConstant = .0001 ;
        this.mass = 1000;
        this.acceleration = new Vector((engineForce - (frictionConstant * 9.8 * mass))/ mass, driveAngle);
        this.velocity = new Vector();
        this.position = new Vector(checkpoints.getLast().getTranslateX(),checkpoints.getLast().getTranslateY());
        this.driveAngle = 0;
    	position.addCartesian(velocity);

        
    }
    
    public void checkCollision()
    {
    	Checkpoint nextpt = checkpoints.getFirst();
    	if(nextpt.getBoundsInParent().intersects(this.getBoundsInParent()) && checkpointDistance() < 10)
    	{
    		acceleration.multiplyScaler(0);
    		velocity.multiplyScaler(0);
    		position.multiplyScaler(0);
    		position.addCartesian(nextpt.getTranslateX(), nextpt.getTranslateY());
    		checkpoints.removeFirst();
    	}
    }
    
    public double getSpeed(){
        return velocity.getSize();
    }
    
    public void drive(){
    	if(!checkpoints.isEmpty())
    	{
    		checkCollision();
    		//checkpointDistance();
    		turn(); 
    		move();
    	}
        
    }
    private double checkpointDistance() {
    	double xa = 0, ya = 0, xb = 0, yb = 0;
    	
    	if(!checkpoints.isEmpty())
    	{
    		xa = position.getx();
    		ya = position.gety();
    		xb = checkpoints.getFirst().getTranslateX();
    	    yb = checkpoints.getFirst().getTranslateY();
    	}
  	  
  	  return Math.sqrt(Math.pow(xb-xa,2) + Math.pow(yb-ya,2));
    }
    
    public void setCheckpoints (LinkedList<Checkpoint> LL){
        this.checkpoints = (LinkedList<Checkpoint>)LL.clone();
    }
    public void turn(){
    	
    	if(!checkpoints.isEmpty())
    	{
    		driveAngle = angleToCheckpt(checkpoints.getFirst());
    		this.setRotate(driveAngle);
    		//acceleration.multiplyScaler(0);
    	}
    	
    }
    public double angleToCheckpt(Checkpoint b)
  {
  	  double xa = position.getx();
  	  double ya = position.gety();
  	  double xb = b.getTranslateX();
  	  double yb = b.getTranslateY();
  	  
	  double ans =   Math.toDegrees(Math.atan2(yb-ya, xb-xa));
	  
	  return ans; 
  }
    
    private void move(){
    	checkpointDistance();
    	calcAcc();
    	calcVelocity();
    	calcPosition();
    
       // System.out.println(this.toString());
    }
    public void calcAcc()
    {
    	acceleration.addPolar(((engineForce-(frictionConstant*mass*9.8))/mass), driveAngle);    	
    	acceleration.multiplyScaler(1/100.0);
    }
    
    public void calcVelocity()
    {

    	velocity.addCartesian(acceleration);
        //System.out.println(velocity);
    }
    
    public void calcPosition()
    {
    	position.addCartesian(velocity);
    	this.setTranslateX(position.getx() - 25);
        this.setTranslateY(position.gety() - 10);
    }
    
    
    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
    
    //returns angle of car's velocity
    public double getAngle()
    {
    	return velocity.getAngle();
    }
    
   
    
    public String toString()
    {
    	return "x : " + position.getx() + " y : " + position.gety() +  " v Angle : " + driveAngle +  "  " +  "  DIST:  " + checkpointDistance();
    }
}
