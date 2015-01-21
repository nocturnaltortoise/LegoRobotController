/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {

	final static int LEFTLINECUTOFF = 600;
	final static int RIGHTLINECUTOFF = 510;
	final static int SPOTCUTOFF = 0;
	final static int DISTANCE = 20;
	static LightSensor leftSensor = new LightSensor(SensorPort.S1);
	static LightSensor rightSensor = new LightSensor(SensorPort.S2);

	public static void main(String[] args) throws InterruptedException {
		final int INTERVAL = 10;

		NXTCommand.open();
		NXTCommand.setVerify(true);
		
		UltrasonicSensor distSensor = new UltrasonicSensor(SensorPort.S4);

		// 33 - Black Spot
		// 50 - Black Line
		// 60 - Floor

		// Motor C - Right Wheel
		// Motor B - Left Wheel


		while(!lineDetected(leftSensor) && !lineDetected(rightSensor)) {
			System.out.println("Left: " + getLight(leftSensor));
			System.out.println("Right: " + getLight(rightSensor));
			forward();
		}		

		hardTurnLeft();

		while (true) {

			System.out.println("Left: " + getLight(leftSensor));
			System.out.println("Right: " + getLight(rightSensor));

			if (lineDetected(leftSensor) && !lineDetected(rightSensor)) { // Turn Left
				turnLeft();
			} else if (!lineDetected(leftSensor) && lineDetected(rightSensor)) { // Turn Right
				turnRight();
			} else if (obstacleDetected(distSensor) && (lineDetected(leftSensor) || lineDetected(rightSensor))) { // Turn at obstacle and both sensor on line
				hardTurnRight();
			} else if (lineDetected(leftSensor) && lineDetected(rightSensor) && !obstacleDetected(distSensor)) { // Found Spot
				System.out.println("Spot");
				stop();
				//dance();
			} else { // On line, moving forward
				forward();
			}

			Thread.sleep(INTERVAL);

		}


		// while(true){
		// 	System.out.println("Left Sensor: " + leftSensor.getLightValue());
		// 	System.out.println("Right Sensor: " + rightSensor.getLightValue());

		// 	Thread.sleep(INTERVAL);
		// }
		

	}

	private static int getLight(LightSensor sensor) {

		return sensor.getLightValue();

	}

	private static boolean lineDetected(LightSensor sensor){

		int lightValue = getLight(sensor);

		if (sensor.equals(leftSensor)) {
			if (lightValue < LEFTLINECUTOFF) {
				return true;
			} else {
				return false;
			}
		} else {
			if (lightValue < RIGHTLINECUTOFF) {
				return true;
			} else {
				return false;
			}
		}


		
	}

	private static boolean obstacleDetected(UltrasonicSensor sensor) {

		if (sensor.getDistance() < DISTANCE) {
			return true;
		}else{
			return false;
		}

	}

	private static void forward() {
		System.out.println("Forward");
		Motor.B.setSpeed(100);
		Motor.C.setSpeed(100);
		Motor.B.forward();
		Motor.C.forward();
	}

	private static void turnLeft() {
		System.out.println("Turning Left");
		Motor.C.setSpeed(40);
		Motor.B.setSpeed(40);
		Motor.C.forward();
		Motor.B.backward();
	}

	private static void turnRight() {
		System.out.println("Turning Right");
		Motor.C.setSpeed(40);
		Motor.B.setSpeed(40);
		Motor.C.backward();
		Motor.B.forward();
	}

	private static void  hardTurnLeft() {
		System.out.println("Hard Turning Left");
		Motor.C.setSpeed(200);
		Motor.B.setSpeed(200);
		Motor.C.forward();
		Motor.B.backward();
	}

	private static void hardTurnRight() {
		System.out.println("Hard Turning Right");
		Motor.C.setSpeed(200);
		Motor.B.setSpeed(200);
		Motor.C.backward();
		Motor.B.forward();
	}
	private static void stop() {
		System.out.println("Stopped");
		Motor.C.stop();
		Motor.B.stop();
	}

	private static void dance() {
		spin();
		playSong();
	}

	private static void spin() {
		System.out.println("Spinning");
		Motor.C.setSpeed(150);
		Motor.B.setSpeed(150);
		Motor.C.forward();
		Motor.B.backward();
	}

	private static void playSong() {
		
	}

}