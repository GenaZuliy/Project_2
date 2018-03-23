import javafx.scene.shape.Circle;

/**
 * Checkpoint is a drawn object in a location where the Car object passes through
 * @author Peter, Gena, Andrew
 */
public class Checkpoint extends Circle {
	private double x;
	private double y;

    public final double radius = 1.0;

	// ##### CONSTRUCTOR #####
    public Checkpoint()
    {
    	this(50,50,30);
    }

	/**
	 * Checkpoint is created with an x/y position and a radius
	 * @param xPos the x position
	 * @param yPos the y position
	 * @param radius the radius
	 */
	public Checkpoint(double xPos,double yPos,int radius)
    {
        this.setRadius(radius);
        this.setX(xPos);
        this.setY(yPos);
    }

	// ##### METHODS #####
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


   	/**
	 * Set Checkpoint object's y value to value specified
   	 * @param y the y to set
   	 */
   	public void setY(double y) {
   		this.y = y;
   		this.setTranslateY(y);
   	}

}
