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
    public Vector(double ax, double ay){
        x = ax;
        y = ay;
    }
    
    public double getx(){
        return x;
    }
    
    public double gety(){
        return y;
    }
    public void addPolar(double angle, double size){
        y += Math.sin(angle)*size;
        x += Math.cos(angle)*size;
    }
    
    public void addCartesian(double ax, double ay){
        x += ax;
        y += ay;
    }
    
    public void addCartesian(Vector v){
        x += v.getx();
        y += v.gety();
    }
    
    public double getAngle(){
        return Math.atan2(y, x);
    }
    
    public double getSize(){
        return Math.sqrt(x*x+y*y);
    }
    
    public void multiplyScaler(double scaler){
        x*=scaler;
        y*=scaler;
    }
}
