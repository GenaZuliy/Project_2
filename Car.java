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
        gc.fillRect(0, 0, 80, 20);
        this.engine =  5000;
        this.engineForce = Math.random() * 5000;
        this.frictionConstant = 0.0;
        this.mass = 100;
        this.velocity = new Vector();
        this.xPos = 0;
        this.yPos = 50;
        this.driveAngle = 0;
        
        
    }
    
    public Car(double x, double y, int startCheckpt)
    {   
        super(50,20);
        this.setManaged(false);
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 80, 20);
        this.engine =  5000;
        this.engineForce = Math.random() * 5000;
        this.frictionConstant = 0.0;
        this.mass = 100;
        this.velocity = new Vector();
        this.xPos = x;
        this.yPos = y;
        this.driveAngle = 0;
        this.lastCheckpt = startCheckpt;
        
        
    }
    
    public double getSpeed(){
        return velocity.getSize();
    }
    
    public void drive(double distance){
        //checkpointDistance();
    	if(displacement < distance)
    	{	
            move();
    	}

    }
    
    public void setCheckpoints(LinkedList<Checkpoint> LL){
        checkpoints = (LinkedList<Checkpoint>)LL.clone();
    }
    
    public double turn(double angle) {
    	
    	
    	this.driveAngle = angle;
    	this.xTraveled = 0;
    	this.yTraveled = 0;
    	this.velocity = new Vector(0,0);
    	//displacement = 0.0;
    	return angle;
    }
 
    
    private void checkpointDistance(){
        double dist = Math.sqrt(xPos*xPos + checkpoints.getFirst().getX()*checkpoints.getFirst().getX());
        if (dist <= checkpoints.getFirst().radius){
            checkpoints.removeFirst();
        }
    } 
    private void move(){
    	arrived = false;
    	calcAcc();
        acceleration.multiplyScaler(1/30.0);
        velocity.addCartesian(acceleration);
        calcVelocity();
        calcPosition();
        xTraveled += velocity.getx()/30.0;
        yTraveled += velocity.gety()/30.0; 
        calcDisplacement();
        this.setTranslateX(getxPos());
		this.setTranslateY(getyPos());
        System.out.println(displacement);
        
    }
    public void calcAcc()
    {
        acceleration.addPolar((engineForce/mass)-(frictionConstant*mass*9.8), driveAngle);
    }
    
    public void calcVelocity()
    {
        velocity.addCartesian(acceleration);
    }
    
    public void calcPosition()
    {
    	xPos += velocity.getx()/30.0;
        yPos += velocity.gety()/30.0; 
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
