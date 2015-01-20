/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {

	final static int LINECUTOFF = 48;
	final static int SPOTCUTOFF = 40;
	final static int DISTANCE = 20;

	public static void main(String[] args) throws InterruptedException {
		final int INTERVAL = 100;

		NXTCommand.open();
		NXTCommand.setVerify(true);

		LightSensor leftSensor = new LightSensor(SensorPort.S1);
		LightSensor rightSensor = new LightSensor(SensorPort.S2);
		UltrasonicSensor distSensor = new UltrasonicSensor(SensorPort.S4);

		// 33 - Black Spot
		// 50 - Black Line
		// 60 - Floor

		// Motor A - Right Wheel
		// Motor B - Left Wheel


		while(!lineDetected(getLight(leftSensor)) && !lineDetected(getLight(rightSensor))) {
			forward();
		}		

		turnLeft();

		while (true) {

			if (lineDetected(getLight(leftSensor)) && !lineDetected(getLight(rightSensor))) { // Turn Left
				turnLeft();
			} else if (!lineDetected(getLight(leftSensor)) && lineDetected(getLight(rightSensor))) { // Turn Right
				turnRight();
			} else if (obstacleDetected(distSensor) && lineDetected(getLight(leftSensor)) && lineDetected(getLight(rightSensor))) { // Turn at obstacle and both sensor on line
				turnRight();
			} else if (lineDetected(getLight(leftSensor)) && lineDetected(getLight(rightSensor))) { // Found Spot
				System.out.println("Spot");
				//dance();
			} else { // On line, moving forward
				forward();
			}

			Thread.sleep(INTERVAL);

		}


		// while(true){
		// 	System.out.println("Left Sensor: " + leftSensor.getLightPercent());
		// 	System.out.println("Right Sensor: " + rightSensor.getLightPercent());

		// 	Thread.sleep(INTERVAL);
		// }
		

	}

	private static int getLight(LightSensor sensor) {

		return sensor.getLightPercent();

	}

	private static boolean lineDetected(int lightPercent){

		if(lightPercent < LINECUTOFF){
			blackDetectCount++;
			return true;
		}
		
	}

	private static boolean obstacleDetected(UltrasonicSensor sensor) {

		if (sensor.getDistance() < DISTANCE) {
			return true
		}

	}

	private static void forward() {
		System.out.println("Forward");
		Motor.A.setSpeed(100);
		Motor.B.setSpeed(100);
		Motor.A.forward();
		Motor.B.forward();
	}

	private static void turnLeft() {
		System.out.println("Turning Left");
		Motor.A.setSpeed(50);
		Motor.B.setSpeed(50);
		Motor.A.forward();
		Motor.B.backward();
	}

	private static void turnRight() {
		System.out.println("Turning Right");
		Motor.A.setSpeed(50);
		Motor.B.setSpeed(50);
		Motor.A.backward();
		Motor.B.forward();

	}

	private static void stop() {
		System.out.println("Stopped");
		Motor.A.stop();
		Motor.B.stop();
	}

	private static void dance() {
		spin();
		playSong();
	}

	private static void spin() {
		System.out.println("Spinning");
		Motor.A.setSpeed(150);
		Motor.B.setSpeed(150);
		Motor.A.forward();
		Motor.B.backward();
	}

	private static void playSong() {
		
	}

}