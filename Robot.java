/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {

	//Light levels of a black line for each sensor.
	final static int LEFT_LINE_CUTOFF = 550;
	final static int RIGHT_LINE_CUTOFF = 480;

	//Distances for obstacle detection.
	final static int CLOSE_OBSTACLE_DISTANCE = 15;
	final static int OBSTACLE_DISTANCE = 30;

	//Robot speed settings.
	final static int FORWARD_SPEED = 100;
	final static int NORMAL_TURN_SPEED = 25;
	final static int HARD_TURN_SPEED = 200;

	static LightSensor leftSensor;
	static LightSensor rightSensor;

	public static void main(String[] args) throws InterruptedException {
		boolean reachedSpot = false;

		NXTCommand.open();
		NXTCommand.setVerify(true);
		
		leftSensor = new LightSensor(SensorPort.S3);
		rightSensor = new LightSensor(SensorPort.S1);
		UltrasonicSensor distSensor = new UltrasonicSensor(SensorPort.S4);


		//Move forwards until the first line is found.
		while(!lineDetected(leftSensor) && !lineDetected(rightSensor)) {
			forward();
		}		

		//once the first line is found, sleep and then turn to the left to follow the line.
		Thread.sleep(750);
		hardTurnLeft();

		/*
		* Until the robot reaches the spot, follow the line (adjusting left if the left sensor detects the line, 
		* and right if the right sensor does the same) and hard turn at obstacles. Once the spot is found, set a flag to terminate
		* the loop, and call the dance method. 
		*/
		while (!reachedSpot) {
			System.out.println("Left: " + getLight(leftSensor));
			System.out.println("Right: " + getLight(rightSensor));
			System.out.println("Distance: " + distSensor.getDistance());

			if (lineDetected(leftSensor) && !lineDetected(rightSensor) && !obstacleDetected(distSensor)) {
				turnLeft();
			} else if (!lineDetected(leftSensor) && lineDetected(rightSensor) && !obstacleDetected(distSensor)) {
				turnRight();
			} else if (obstacleDetectedClose(distSensor)) {
				hardTurnRight();
				// Thread.sleep(500);
				// forward();
			} else if (lineDetected(leftSensor) && lineDetected(rightSensor) && !obstacleDetected(distSensor)) {
				System.out.println("Spot");
				reachedSpot = true;
				dance();
			} else {
				forward();
			}

			Thread.sleep(INTERVAL);
		}

		NXTCommand.close();
	}

	/**
	* Accessor, gets the light value from the Sensor.
	* @param sensor LightSensor Sensor to get the light values from.
	* @return int Light value for a sensor.
	*/
	public static int getLight(LightSensor sensor) {
		return sensor.getLightValue();
	}

	/**
	* Detects a line under a sensor.
	* @param sensor LightSensor The sensor which may be over the line.
	* @return boolean Whether a line has been detected. 
	*/
	public static boolean lineDetected(LightSensor sensor){
		int lightValue = getLight(sensor);

		if (sensor.equals(leftSensor)) { // left sensor
			if (lightValue < LEFT_LINE_CUTOFF) {
				return true;
			} else {
				return false;
			}
		} else { // right sensor
			if (lightValue < RIGHT_LINE_CUTOFF) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	* Detects obstacles.
	* @param sensor UltrasonicSensor The ultrasonic sensor which may have detected an object.
	* @return boolean Whether an obstacle has been detected. 
	*/
	public static boolean obstacleDetected(UltrasonicSensor sensor) {
		if (sensor.getDistance() < OBSTACLE_DISTANCE) {
			return true;
		}else{
			return false;
		}
	}

	/**
	* Detects very close obstacles.
	* @param sensor UltrasonicSensor The ultrasonic sensor which may have detected an object.
	* @return boolean Whether an obstacle has been detected. 
	*/
	public static boolean obstacleDetectedClose(UltrasonicSensor sensor) {
		if (sensor.getDistance() < CLOSE_OBSTACLE_DISTANCE) {
			return true;
		}else{
			return false;
		}
	}

	/*
	* Motor C - Right Wheel
	* Motor B - Left Wheel
	*/

	/**
	* Sets the Robot's motors to move forwards.
	*/
	public static void forward() {
		System.out.println("Forward");
		Motor.B.setSpeed(FORWARD_SPEED);
		Motor.C.setSpeed(FORWARD_SPEED);
		Motor.B.forward();
		Motor.C.forward();
	}

	/**
	* Sets the robot's motors to turn left.
	*/
	public static void turnLeft() {
		System.out.println("Turning Left");
		Motor.C.setSpeed(NORMAL_TURN_SPEED);
		Motor.B.setSpeed(NORMAL_TURN_SPEED);
		Motor.C.forward();
		Motor.B.backward();
	}

	/**
	* Sets the robot's motors to turn right.
	*/
	public static void turnRight() {
		System.out.println("Turning Right");
		Motor.C.setSpeed(NORMAL_TURN_SPEED);
		Motor.B.setSpeed(NORMAL_TURN_SPEED);
		Motor.C.backward();
		Motor.B.forward();
	}

	/**
	* Sets the robot's motors to turn hard to the left.
	*/
	public static void  hardTurnLeft() {
		System.out.println("Hard Turning Left");
		Motor.C.setSpeed(HARD_TURN_SPEED);
		Motor.B.setSpeed(HARD_TURN_SPEED);
		Motor.C.forward();
		Motor.B.backward();
	}

	/**
	* Sets the robot's motors to turn hard to the right.
	*/
	public static void hardTurnRight() {
		System.out.println("Hard Turning Right");
		Motor.C.setSpeed(HARD_TURN_SPEED);
		Motor.B.setSpeed(HARD_TURN_SPEED);
		Motor.C.backward();
		Motor.B.forward();
	}

	/**
	* Sets the robot's motors to stop.
	*/
	public static void stop() {
		System.out.println("Stopped");
		Motor.C.stop();
		Motor.B.stop();
	}

	/**
	* Sets the robot's motors to dance on the spot, and play music.
	* @throws InterruptedException Occurs if sleep is interrupted.
	*/
	public static void dance() throws InterruptedException {
		forward();
		Thread.sleep(2000);
		spin();
		playSong();
		stop();
	}

	/**
	* Sets the robot's motors to spin on the spot.
	*/
	public static void spin() {
		System.out.println("Spinning");
		Motor.C.setSpeed(HARD_TURN_SPEED);
		Motor.B.setSpeed(HARD_TURN_SPEED);
		Motor.C.forward();
		Motor.B.backward();
	}

	/**
	* Makes the robot play music.
	* @throws InterruptedException Occurs if sleep is interrupted.
	*/
	public static void playSong() throws InterruptedException {
		System.out.println("Playing Song");
		int[] notes = {587,587,587,587,466,523,587,523,587};
		for (int note : notes) {
			Sound.playTone(note, 400);
			Thread.sleep(450);
		}
	}

}