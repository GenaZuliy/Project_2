//##### IMPORTS ######
import java.util.LinkedList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Creates a Car object with an acceleration vector, velocity vector, position
 * vector, engine, tires, identification, friction constant, mass, engine force,
 * drive angle. The car can move in any direction and turn itself.
 * 
 * The car takes a list of checkpoints as a parameter. It shifts the order of
 * its list of checkpoints by a random int 0-4, for a random start each time.
 * 
 * 
 * @authors Peter, Gena, Andrew
 */
public class Car extends Canvas {

    // ###### INSTANCE VARIABLES ######
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
	private int ID;

	// ##### CONSTRUCTORS #####
	/**
	 * Initialize the car object with random engine force (2000N - 5000N) Position
	 * based on velocity based on acceleration Default Values: Mass: 1000kg;
	 * Friction = .001;
	 * 
	 * @param LL       Linked list of checkpoints
	 * @param ID	   ID of the car (int)
	 */

	public Car(LinkedList<Checkpoint> LL, int ID) {
		super(50, 20);
		this.setManaged(false);
		Random rand = new Random();
		Color myFill = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
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
		timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1 / 10.0), e -> {

			this.totalTime += .1;

		}));
		timer.setAutoReverse(false);
	}

	// ##### NO ARG CONSTRUCTOR
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
	 * Checks for collision with a destination checkpoint (first in list)
	 * removes checkpoint upon collision and resets acceleration, velocity, position.
	 * Snaps car to center of checkpoint upon reaching within 7px of checkpt (stops overshoot at high speed)
	 */
	public void checkCollision() {
		Checkpoint nextpt = checkpoints.getFirst();
		if (nextpt.getBoundsInParent().intersects(this.getBoundsInParent()) && Math.abs(checkpointDistance()) <= 7) {
			acceleration.multiplyScalar(0);
			velocity.multiplyScalar(0);
			position.multiplyScalar(0);
			position.addCartesian(nextpt.getTranslateX(), nextpt.getTranslateY());
			checkpoints.removeFirst();
		}
		if (checkpoints.isEmpty()) {
			this.finished = true;
		}
	}

	/**
	 * If the car hasn't visited all checkpoints and there are checkpoints to visit,
	 * check for collision, turn if needed, and move to new position
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
	 * Rotates the car by a certain degree to face the next checkpoint
	 */
	public void turn() {
		if (!checkpoints.isEmpty()) {
			driveAngle = angleToCheckpt(checkpoints.getFirst());
			this.setRotate(driveAngle);
		}
	}
	
	/**
	 * Calculates acceleration, then velocity, then position
	 * 
	 */
	private void move() {
		calcAcc();
		calcVelocity();
		calcPosition();
	}

	/**
	 * returns the angle from current position to next checkpoint in  the list
	 * @param b the next checkpoint
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
	 * Calculates acceleration based on Engine force, friction, and mass. 
	 * Sets angle of acceleration to driveAngle
	 */
	public void calcAcc() {
		acceleration.addPolar(((engineForce - (frictionConstant * mass * 9.8)) / mass), driveAngle);
		acceleration.multiplyScalar(1 / 100.0);
	}

	/**
	 * Calculates velocity by adding acceleration vector to velocity vector
	 */
	public void calcVelocity() {

		velocity.addCartesian(acceleration);
	}

	/**
	 * Calculates position by adding velocity vector to position vector
	 * 25 and 10 px adjustment for center of car 
	 */
	public void calcPosition() {
		position.addCartesian(velocity);
		this.setTranslateX(position.getx() - this.getWidth()/2);
		this.setTranslateY(position.gety() - this.getHeight()/2);
	}

	/**
	 * 
	 */
	private void randomizeStart() {
		Random rand = new Random();
		for (int i = 0; i < rand.nextInt(4); i++) {
			Checkpoint c = checkpoints.removeFirst();
			checkpoints.addLast(c);
		}
	}

	/**
	 * returns true if car has visited all checkpoints
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * returns the total time the car took to visit all checkpoints
	 */
	public double getTotalTime() {
		return totalTime;
	}

	/**
	 * Starts the car's own timer
	 */
	public void startTimer() {
		timer.play();
	}

	/**
	 * Stops the car's timer
	 */
	public void stopTimer() {
		timer.stop();
	}

	/**
	 * returns the car's ID
	 */
	public int getID() {
		return ID;
	}

	@Override
	/**
	 * Returns a string representation of the Car's ID and total time
	 */
	public String toString() {
		return "Car " + ID + "   " + String.valueOf(totalTime).substring(0, 5) + " Seconds";
	}
}
