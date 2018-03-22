/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.shape.Circle;

/**
 * Checkpoint is a drawn object in a location where the Car object passes through
 * @author peter
 */
public class Checkpoint extends Circle {
	private double x;
	private double y;
	private int id;
<<<<<<< HEAD
=======
	
	
    public final double radius = 1.0;
    
    public Checkpoint()
    {	
    	this(50,50,30);
    }

	/**
	 * Checkpoint is created with an x/y position and a radius
	 * @param xPos
	 * @param yPos
	 * @param radius
	 */
	public Checkpoint(double xPos,double yPos,int radius)
    {	
        this.setRadius(radius);
        this.setX(xPos);
        this.setY(yPos);
        
    }


	/**
	 * Retrieves the Checkpoint object's x value
	 * @return x
	 */
	public double getX() {
        return x;
    }
	/**
	 * Retrieves the Checkpoint object's y value
	 * @return y
	 */
    public double getY() {
        return y;
    }
    /**
	 * Set Checkpoint object's x value to value specified
   	 * @param x the x to set
   	 */
   	public void setX(double x) {
   		this.x = x;
   		this.setTranslateX(x);
   	}
>>>>>>> 5e98e6686ed0171290253f4f16fd8132531f4157

	public final double radius = 1.0;

	public Checkpoint() {
		this(50, 50, 30);
	}

	public Checkpoint(double xPos, double yPos, int radius) {
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
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = x;
		this.setTranslateX(x);
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = y;
		this.setTranslateY(y);
	}

<<<<<<< HEAD
	public int getID() {
		return id;
	}
=======
   	/**
	 * Set Checkpoint object's y value to value specified
   	 * @param y the y to set
   	 */
   	public void setY(double y) {
   		this.y = y;
   		this.setTranslateY(y);
   	}

	/**
	 * Get's the name of the Checkpoint
	 * @return id
	 */
	public int getID()
   	{
   		return id;
   	}
>>>>>>> 5e98e6686ed0171290253f4f16fd8132531f4157
}
