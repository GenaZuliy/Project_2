
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
 * Creates a Car object with a velocity, position, accerlation, engine, tires, identification, friction constant,
 * mass, x position, y position, x and y travel distances, tire angle, engine force, drive angle. The car can move
 * forward and turn.
 * @author peter
 */
public class Car extends Canvas {

	private Vector velocity;
	private Vector position;
	private Vector acceleration;
	private double frictionConstant;
	private double mass;
	private double engineForce;
	private LinkedList<Checkpoint> checkpoints;
	private GraphicsContext gc = this.getGraphicsContext2D();
	private double driveAngle;

	/**
	 * Initialize the car object with the given specifications
	 */

	public Car() {
		super(50, 20);
		this.setManaged(false);
		gc.setFill(Color.RED);
		gc.fillRect(0, 0, 50, 20);
		this.engineForce = Math.random() * 5000;
		this.frictionConstant = 0.9;
		this.mass = 1000;
		this.velocity = new Vector();
		this.driveAngle = 0;
	}

	/**
	 * Initialize the car object at given x/y coordinate with the given
	 * specifications as well as creating a checkpoint at the same spot
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param LL
	 *            the checkpoint
	 */

	public Car(double x, double y, LinkedList<Checkpoint> LL) {
		super(50, 20);
		this.setManaged(false);
		gc.setFill(Color.RED);
		gc.fillRect(0, 0, 50, 20);

		this.checkpoints = (LinkedList<Checkpoint>) LL.clone();
		this.engineForce = Math.random() * 3000 + 2000;
		this.frictionConstant = .0001;
		this.mass = 1000;
		this.acceleration = new Vector((engineForce - (frictionConstant * 9.8 * mass)) / mass, driveAngle);
		this.velocity = new Vector();
		this.position = new Vector(checkpoints.getLast().getTranslateX(), checkpoints.getLast().getTranslateY());
		this.driveAngle = 0;
		position.addCartesian(velocity);

	}

	public void checkCollision() {
		Checkpoint nextpt = checkpoints.getFirst();
		if (nextpt.getBoundsInParent().intersects(this.getBoundsInParent()) && checkpointDistance() < 10) {
			acceleration.multiplyScaler(0);
			velocity.multiplyScaler(0);
			position.multiplyScaler(0);
			position.addCartesian(nextpt.getTranslateX(), nextpt.getTranslateY());
			checkpoints.removeFirst();
		}
	}

	/**
	 * Returns the size of the velocity vector
	 * 
	 * @return velocity.getSize()
	 */

	public double getSpeed() {
		return velocity.getSize();
	}

	/**
	 * Calls the turn and move methods to turn and move the car in a certain
	 * direction
	 */
	public void drive() {
		if (!checkpoints.isEmpty()) {
			checkCollision();
			// checkpointDistance();
			turn();
			move();
		}

	}

	/**
	 * Calculates the distance between two given checkpoints
	 * 
	 * @return dist The distance between the two given checkpoints
	 */
	private double checkpointDistance() {
		double xa = 0, ya = 0, xb = 0, yb = 0;

		if (!checkpoints.isEmpty()) {
			xa = position.getx();
			ya = position.gety();
			xb = checkpoints.getFirst().getTranslateX();
			yb = checkpoints.getFirst().getTranslateY();
		}

		return Math.sqrt(Math.pow(xb - xa, 2) + Math.pow(yb - ya, 2));
	}

	/**
	 * Creates and returns a copy of Checkpoint
	 * 
	 * @param LL
	 */
	public void setCheckpoints(LinkedList<Checkpoint> LL) {
		this.checkpoints = (LinkedList<Checkpoint>) LL.clone();
	}

	/**
	 * Rotates the car by a certain degree to face the next checkpoint
	 */
	public void turn() {

		if (!checkpoints.isEmpty()) {
			driveAngle = angleToCheckpt(checkpoints.getFirst());
			this.setRotate(driveAngle);
			// acceleration.multiplyScaler(0);
		}

	}

	/**
	 * Finds the angle in which to rotate at to get to the next checkpoint
	 * 
	 * @param b
	 *            the checkpoint to rotate to
	 * @return ans the angle to rotate
	 */
	public double angleToCheckpt(Checkpoint b) {
		double xa = position.getx();
		double ya = position.gety();
		double xb = b.getTranslateX();
		double yb = b.getTranslateY();

		double ans = Math.toDegrees(Math.atan2(yb - ya, xb - xa));

		return ans;
	}

	/**
	 * Finds the distance to next checkpoint and accelerates accordingly to the
	 * checkpoint
	 */
	private void move() {
		checkpointDistance();
		calcAcc();
		calcVelocity();
		calcPosition();

		// System.out.println(this.toString());
	}

	public void calcAcc() {
		acceleration.addPolar(((engineForce - (frictionConstant * mass * 9.8)) / mass), driveAngle);
		acceleration.multiplyScaler(1 / 100.0);
	}

	/**
	 * Calculates the velocity of the car using the velocity vector's size and angle
	 */
	public void calcVelocity() {

		velocity.addCartesian(acceleration);
		// System.out.println(velocity);
	}

	public void calcPosition() {
		position.addCartesian(velocity);
		this.setTranslateX(position.getx() - 25);
		this.setTranslateY(position.gety() - 10);
	}

	/**
	 * Returns cars x position
	 * 
	 * @return xPos
	 */
	public double getxPos() {
		return position.getx();
	}

	/**
	 * Returns cars y position
	 * 
	 * @return yPos
	 */
	public double getyPos() {
		return position.gety();
	}

	/**
	 * Returns angle of car's velocity
	 * 
	 * @return velocity.getAngle()
	 */
	public double getAngle() {
		return velocity.getAngle();
	}

	@Override
	/**
	 * Returns the x/y positions of the car as well as angle it is facing and its
	 * acceleration
	 */
	public String toString() {
		return "x : " + position.getx() + " y : " + position.gety() + " v Angle : " + driveAngle + "  " + "  DIST:  "
				+ checkpointDistance();
	}
}
