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
    private double tireAngle;
    private double engineForce;
    private LinkedList<Checkpoint> checkpoints;
    private GraphicsContext gc = this.getGraphicsContext2D();
    private double driveAngle;
    
    public Car()
    {   
        super(80,20);
        this.setManaged(false);
        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, 80, 20);
        this.engine =  5000;
        this.engineForce = 4999;
        this.frictionConstant = 0.0;
        this.mass = 100;
        this.velocity = new Vector();
        this.xPos = 20;
        this.yPos = 20;
        this.driveAngle = 0;
    }
    
    public double getSpeed(){
        return velocity.getSize();
    }
    
    public void drive(){
        move();
    }
    
    public double turn(double angle) {
        
    	this.driveAngle = angle;
    	this.velocity = new Vector();
    	return angle;
    }
    
    private void move(){

        acceleration.addPolar((engineForce/mass)-(frictionConstant*mass*9.8), driveAngle);
        //acceleration.addPolar(1, 0);
        //System.out.println(acceleration.getx());
        acceleration.multiplyScaler(1/30.0);
        velocity.addCartesian(acceleration);
        xPos += velocity.getx()/30.0;
        yPos += velocity.gety()/30.0; 
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
}
