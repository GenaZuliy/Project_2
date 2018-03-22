
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

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
	private boolean finished;
	private double totalTime;
	private Timeline timer = new Timeline();
	private Color myFill;
	private int ID;
	

	

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
		this.finished = false;
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

	public Car(LinkedList<Checkpoint> LL, int ID) {
		super(50, 20);
		this.setManaged(false);
		Random rand = new Random();
		this.myFill = Color.rgb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
		gc.setFill(myFill);
		gc.setFont(new Font("Georgia", 20));
		gc.fillRect(0, 0, 50, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(String.valueOf(ID), 19, 15);
		this.ID = ID;
		this.checkpoints = (LinkedList<Checkpoint>) LL.clone();
		this.randomizeStart();
		this.engineForce = Math.random() * 3000 + 2000;
		this.frictionConstant = .0001;
		this.mass = 1000;
		this.acceleration = new Vector((engineForce - (frictionConstant * 9.8 * mass)) / mass, driveAngle);
		this.velocity = new Vector();
		
		this.driveAngle = 0;
		this.finished = false;
		this.position = new Vector(checkpoints.getLast().getTranslateX(), checkpoints.getLast().getTranslateY());
		position.addCartesian(velocity);
		this.move();
		this.velocity = new Vector();
	    timer.setCycleCount(Timeline.INDEFINITE);
			timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1/10.0), e -> {
				
				this.totalTime += .1;

			}));
			timer.setAutoReverse(false);	        
	}

	public void checkCollision() {
		Checkpoint nextpt = checkpoints.getFirst();
		if (nextpt.getBoundsInParent().intersects(this.getBoundsInParent()) && Math.abs(checkpointDistance()) <= 7 ) {
			acceleration.multiplyScaler(0);
			velocity.multiplyScaler(0);
			position.multiplyScaler(0);
			position.addCartesian(nextpt.getTranslateX(), nextpt.getTranslateY());
			checkpoints.removeFirst();
		}
		if(checkpoints.isEmpty())
		{
			this.finished = true;
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
		if (!checkpoints.isEmpty() && !this.isFinished()) {
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
	
	public void randomizeStart()
	{
		Random rand = new Random();
		for(int i = 0; i < rand.nextInt(4); i++)
		{
			Checkpoint c = checkpoints.removeFirst();
			checkpoints.addLast(c);
		}
	}
	
	public boolean isFinished()
	{
		return finished;
	}
	
	public double getTotalTime() {
		return totalTime;
	}
	
	public void startTimer()
	{
		timer.play();
	}
	
	public void stopTimer()
	{
		timer.stop();
	}
	

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	
	public Color getColor()
	{
		return this.myFill;
	}
	public int getID()
	{
		return ID;
	}
	

	@Override
	/**
	 * Returns the x/y positions of the car as well as angle it is facing and its
	 * acceleration
	 */
	public String toString() {
		return "Car " + ID + "   " + String.valueOf(totalTime).substring(0, 5) + " Seconds";
	}
}
