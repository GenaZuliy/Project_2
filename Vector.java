/**
 * A 2D Vector with methods for adding vectors, getting angle, magnitude, and multiplication by a scalar;
 * 
 * @author Peter, Gena, Andrew
 */
public class Vector {

    // ###### INSTANCE VARIABLES ######
    private double x;
    private double y;

    // ##### CONSTRUCTORS #####
    /**
     * Create an empty Vector
     */
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

    public void multiplyScalar(double scalar){
        x *= scalar;
        y *= scalar;
    }
}
