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
	private int id;
	
	
    public final double radius = 1.0;
    
    public Checkpoint()
    {	
    	this(50,50,30);
    }    
    
    
    public Checkpoint(int xPos,int yPos,int radius)
    {	
        this.setRadius(radius);
        this.setX(xPos);
        this.setY(yPos);
        
    }
    
    
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    /**
   	 * @param x the x to set
   	 */
   	public void setX(double x) {
   		this.x = x;
   		this.setTranslateX(x);
   	}


   	/**
   	 * @param y the y to set
   	 */
   	public void setY(double y) {
   		this.y = y;
   		this.setTranslateY(y);
   	}
   	
   	public int getID()
   	{
   		return id;
   	}
}
