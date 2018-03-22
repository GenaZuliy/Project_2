/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author peter
 */
public class Vector {
    
    private double x;
    private double y;
    
    public Vector(){
        x = 0;
        y = 0;
    }

    /**
     * The x and y coordinates of the vector
     * @param ax
     * @param ay
     */
    public Vector(double ax, double ay){
        x = ax;
        y = ay;
    }

    /**
     * Get x
     * @return x
     */
    public double getx(){
        return x;
    }

    /**
     * Get y
     * @return y
     */
    public double gety(){
        return y;
    }

    /**
     * Gives magnitude and direction to the Vector in radians
     * @param size
     * @param angle
     */
    public void addPolar(double size, double angle){
        y += Math.sin(Math.toRadians(angle))*size;
        x += Math.cos(Math.toRadians(angle))*size;
        }


    public void addCartesian(double ax, double ay){
        x += ax;
        y += ay;
    }
    
    public void addCartesian(Vector v){
        x += v.getx();
        y += v.gety();
    }

    /**
     * Gets the angle of the vector
     * @return angle in degrees
     */
    public double getAngle(){
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Gets the size of the vector
     * @return size of vector
     */
    public double getSize(){
        return Math.sqrt(x*x+y*y);
    }
    
    public void multiplyScaler(double scaler){
        x *= scaler;
        y *= scaler;
    }
}
