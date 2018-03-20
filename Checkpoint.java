/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.shape.Circle;

/**
 *
 * @author peter
 */
public class Checkpoint extends Circle {
    private double x;
    private double y;
    public final double radius = 1.0;
    
    public Checkpoint()
    {
        this.setRadius(30);
    }
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
