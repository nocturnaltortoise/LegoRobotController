/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {

	final static int LINECUTOFF = 48;
	final static int LINECUTOFFRIGHT;
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
			} else { // On line, moving forward
				forward();
			}

			Thread.sleep(INTERVAL);

		}


		while(true){
			System.out.println("Left Sensor: " + leftSensor.getLightPercent());
			System.out.println("Right Sensor: " + rightSensor.getLightPercent());

			Thread.sleep(INTERVAL);
		}
		


		// while (!goalReached) {
		// 	lightLevel = floorSensor.getLightPercent();
		// 	lightLevel2 = floorSensor2.getLightPercent();
		// 	System.out.println("Light Sensor 1: " + lightLevel);
		// 	System.out.println("Light Sensor 2: " + lightLevel2);

		// 	forward();

		// 	if (distSensor.getDistance() < DISTANCE) {
		// 		System.out.println("Object infront");
		// 		while (lightLevel > LINECUTOFF) { 
		// 			lightLevel = floorSensor.getLightPercent();
		// 			System.out.println(floorSensor.getLightPercent());
		// 			turnLeft();
		// 		}
		// 	}
		// 	if ((lightLevel < LINECUTOFF && lightLevel > SPOTCUTOFF)
		// 		&& (lightLevel2 < LINECUTOFF && lightLevel2 > SPOTCUTOFF)) {
		// 		//System.out.println("Both on black line");
		// 	} else if (lightLevel < SPOTCUTOFF || lightLevel2 < SPOTCUTOFF) {
		// 		//Sytem.out.println("On Spot");
		// 		//dance();
		// 	} else if (lightLevel > LINECUTOFF && lightLevel2 < LINECUTOFF) {
		// 		//Sytem.out.println("Right Sensor on line, left not");
		// 		lightLevel = floorSensor.getLightPercent();
		// 		lightLevel2 = floorSensor2.getLightPercent();
		// 		turnRight();
		// 	} else if (lightLevel < LINECUTOFF && lightLevel2 > LINECUTOFF) {
		// 		//Sytem.out.println("Left Sensor on line, right not");
		// 		lightLevel = floorSensor.getLightPercent();
		// 		lightLevel2 = floorSensor2.getLightPercent();
		// 		turnLeft();
		// } else {
		// 		//System.out.println("Neither on black line");
		// 		while (lightLevel > LINECUTOFF && lightLevel2 > LINECUTOFF) {
		// 			lightLevel = floorSensor.getLightPercent();
		// 			lightLevel2 = floorSensor2.getLightPercent();
		// 			//System.out.println(floorSensor.getLightPercent());
		// 			turnLeft();
		// 		}
		// 	}
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
	}
}