/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.LinkedList;
/**
 *
 * @author peter
 */
public class Car {
    private Vector velocity;
    private double engine; //max force
    private double tires; //max turn angle
    private int ID;
    private double frictionConstant;
    private double mass;
    private double xPos;
    private double yPos;
    private double tireAngle;
    private double engineForce;
    private LinkedList<Checkpoint> checkpoints;
    
    public double getSpeed(){
        return velocity.getSize();
    }
    
    public void drive(){
        turn();
        move();
    }
    
    private void turn() {
        
    }
    
    private void move(){
        Vector acceleration = new Vector();
        acceleration.addPolar((engineForce/mass)-(frictionConstant*mass*9.8), (tireAngle/2)+velocity.getAngle());
        acceleration.multiplyScaler(1/30);
        velocity.addCartesian(acceleration);
        xPos += velocity.getx()/30;
        yPos += velocity.gety()/30;
    }
}
