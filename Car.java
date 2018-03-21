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
    private double engine; //max force
    private double tires; //max turn angle
    private int ID;
    private double frictionConstant;
    private double mass;
    private double xPos;
    private Vector acceleration = new Vector();
    private double tireIncrement = 5.0;
    private double yPos;
    private double yTraveled;
    private double xTraveled;
    
    private double tireAngle;
    private double engineForce;
    private LinkedList<Checkpoint> checkpoints;
    private GraphicsContext gc = this.getGraphicsContext2D();
    private double driveAngle;
    
    private double displacement = 0;
    public boolean arrived = false;
    
    private int lastCheckpt = 0;
    
    public Car()
    {   
        super(50,20);
        this.setManaged(false);
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 50, 20);
        this.engine =  5000;
        this.engineForce = Math.random() * 5000;
        this.frictionConstant = 0.02;
        this.mass = 100;
        this.velocity = new Vector();
        this.xPos = 0;
        this.yPos = 50;
        this.driveAngle = 0;
        
        
        
    }
    
    public Car(double x, double y, LinkedList<Checkpoint> LL)
    {   
        super(50,20);
        this.setManaged(false);
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 50, 20);
        this.engine =  5000;
        this.engineForce = Math.random() * 3000 + 2000;
        this.frictionConstant = 0.0;
        this.mass = 100;
        this.velocity = new Vector();
        this.xPos = x - 25;
        this.yPos = y - 10;
        this.driveAngle = 0;
        this.checkpoints = (LinkedList<Checkpoint>)LL.clone();
        
        
    }
    
    public double getSpeed(){
        return velocity.getSize();
    }
    
    public void drive(double distance){
        checkpointDistance();
        turn();
        move();
        
    	/*if(displacement < distance)
    	{	
    		move();
    	}*/

    }
    private void checkpointDistance() {
        double dist = Math.sqrt(Math.pow(xPos-checkpoints.getFirst().getX(), 2) + Math.pow(yPos-checkpoints.getFirst().getY(), 2));
        if (dist <= checkpoints.getFirst().radius){
            checkpoints.removeFirst();
            System.out.println("HIT, car x: " + xPos + " y:" + yPos);
        }
    }
    
    public void setCheckpoints (LinkedList<Checkpoint> LL){
        this.checkpoints = (LinkedList<Checkpoint>)LL.clone();
    }
    public double turn(){//double angle) {
        double angle = angleToCheckpt(checkpoints.getFirst());
        if ((driveAngle - angle) >= tireIncrement){
            driveAngle -= tireIncrement;
        } else if ((driveAngle - angle) <= -1*tireIncrement) {
            driveAngle += tireIncrement;
        } else if (Math.abs(driveAngle - angle) <= tireIncrement){
            driveAngle = angle;
        }
        
        /*
    	this.driveAngle = angle;
    	this.xTraveled = 0;
    	this.yTraveled = 0;
    	this.velocity = new Vector(0,0);
    	//displacement = 0.0;
*/
    	return driveAngle;
    }
    public double angleToCheckpt( Checkpoint b)
  {
	  double xa = xPos;
	  double ya = yPos;
	  double xb = b.getTranslateX();
	  double yb = b.getTranslateY();
	  
	  return Math.toDegrees(Math.atan2(yb-ya, xb-xa));
  }
    
    private void move(){
        
        Vector acceleration = new Vector();
        acceleration.addPolar((engineForce/mass)-(frictionConstant*mass*9.8), (tireAngle/2)+velocity.getAngle());
        acceleration.multiplyScaler(1/100);
        velocity.addCartesian(acceleration);
        xPos += velocity.getx()/100;
        yPos += velocity.gety()/100;
        this.setRotate(velocity.getAngle());
        this.setTranslateX(xPos);
        this.setTranslateY(yPos);
    	/*arrived = false;
    	calcAcc();
        acceleration.multiplyScaler(1/100.0);
        velocity.addCartesian(acceleration);
        velocity.multiplyScaler(1/100.0);
        calcVelocity();
        calcPosition();
        xTraveled += velocity.getx();///100.0;
        yTraveled += velocity.gety();///100.0; 
        this.setRotate(driveAngle);
        calcDisplacement();
        this.setTranslateX(getxPos());
        this.setTranslateY(getyPos());
        //System.out.println("Car: x: "+ this.xPos + "y: " + this.yPos);
        //System.out.println(displacement);
*/
        
    }
    public void calcAcc()
    {
        acceleration = new Vector();
        acceleration.addPolar((engineForce/mass)-(frictionConstant*mass*9.8), (tireAngle/2) + velocity.getAngle());
    }
    
    public void calcVelocity()
    {
        velocity.addCartesian(acceleration);
        velocity = new Vector(velocity.getSize(), driveAngle);
        System.out.println(velocity);
    }
    
    public void calcPosition()
    {
    	xPos += velocity.getx();///100.0;
        yPos += velocity.gety();///100.0; 
    }
    
    public void calcDisplacement()
    {
        displacement = Math.sqrt(Math.pow(xTraveled,2) + Math.pow(yTraveled,2));
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
    
    public int getLastCheckpt()
    {
    	return lastCheckpt;
    }
}
