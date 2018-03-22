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
        this.frictionConstant = .001 ;
        this.mass = 1000;
        this.acceleration = new Vector((engineForce - (frictionConstant * 9.8 * mass))/ mass, driveAngle);
        this.velocity = new Vector();
        this.position = new Vector(checkpoints.getFirst().getTranslateX(),checkpoints.getFirst().getTranslateY());
        this.driveAngle = 0;
    	position.addCartesian(velocity);

        
    }
    
    public double getSpeed(){
        return velocity.getSize();
    }
    
    public void drive(){
        checkpointDistance();
        turn(); 
        move();
    }
    private double checkpointDistance() {
        double dist = Math.sqrt(Math.pow(checkpoints.getFirst().getTranslateX() - position.getx(), 2) + Math.pow(checkpoints.getFirst().getTranslateY() - position.gety(), 2));
        if(dist < 25)
        {
        	acceleration.multiplyScaler(0);
        	checkpoints.removeFirst();
        }
        return dist;
    }
    
    public void setCheckpoints (LinkedList<Checkpoint> LL){
        this.checkpoints = (LinkedList<Checkpoint>)LL.clone();
    }
    public void turn(){
    	

    		driveAngle = angleToCheckpt(checkpoints.get(2));
    		//acceleration.multiplyScaler(0);
    	
    }
    public double angleToCheckpt(Checkpoint b)
  {
  	  double xb = position.getx();
  	  double yb = position.gety();
  	  double xa = b.getTranslateX();
  	  double ya = b.getTranslateY();
  	  
	  double ans =   Math.toDegrees(Math.atan2(yb-ya, xb-xa));
	  
	  return ans; 
  }
    
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
    	return "x : " + position.getx() + " y : " + position.gety() +  " v Angle : " + driveAngle +  "  " +  "  AccL: " + acceleration.getSize();
    }
}
