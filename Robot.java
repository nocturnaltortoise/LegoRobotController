/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {
	public static void main(String[] args) throws InterruptedException {
		final int INTERVAL = 10;
		final int LINECUTOFF = 55;
		final int SPOTCUTOFF = 45;
		final int DISTANCE = 20;
		int lightLevel = 0;
		int lightLevel2 = 0;
		boolean goalReached = false;

		NXTCommand.open();
		NXTCommand.setVerify(true);

		LightSensor floorSensor = new LightSensor(SensorPort.S1);
		LightSensor floorSensor2 = new LightSensor(SensorPort.S2);
		UltrasonicSensor distSensor = new UltrasonicSensor(SensorPort.S4);

		// 33 - Black Spot
		// 50 - Black Line
		// 60 - Floor

		// Motor A - Right Wheel
		// Motor B - Left Wheel

		while (!goalReached) {
			lightLevel = floorSensor.getLightPercent();
			lightLevel2 = floorSensor2.getLightPercent();
			System.out.println("Light Sensor 1: " + lightLevel);
			System.out.println("Light Sensor 2: " + lightLevel2);

			forward();

			if (distSensor.getDistance() < DISTANCE) {
				System.out.println("Object infront");
				while (lightLevel > LINECUTOFF) { 
					lightLevel = floorSensor.getLightPercent();
					System.out.println(floorSensor.getLightPercent());
					turnLeft();
				}
			}
			if ((lightLevel < LINECUTOFF && lightLevel > SPOTCUTOFF)
				|| (lightLevel2 < LINECUTOFF && lightLevel > SPOTCUTOFF)) {
				//System.out.println("Both on black line");
			} else if (lightLevel < SPOTCUTOFF || lightLevel2 < SPOTCUTOFF) {
				//Sytem.out.println("On Spot");
				dance();
			} else if (lightLevel > LINECUTOFF && lightLevel2 < LINECUTOFF) {
				//Sytem.out.println("Right Sensor on line, left not");
				lightLevel = floorSensor.getLightPercent();
				lightLevel2 = floorSensor2.getLightPercent();
				turnRight();
			} else if (lightLevel < LINECUTOFF && lightLevel2 > LINECUTOFF) {
				//Sytem.out.println("Left Sensor on line, right not");
				lightLevel = floorSensor.getLightPercent();
				lightLevel2 = floorSensor2.getLightPercent();
				turnLeft();
			} else {
				//System.out.println("Neither on black line");
				while (!(lightLevel < LINECUTOFF) && !(lightLevel2 < LINECUTOFF)) {
					lightLevel = floorSensor.getLightPercent();
					lightLevel2 = floorSensor2.getLightPercent();
					//System.out.println(floorSensor.getLightPercent());
					turnLeft();
				}
			}
			Thread.sleep(INTERVAL);
		}

		dance();

	}

	public static void forward() {
		System.out.println("Forward");
		//Motor.A.setSpeed(100);
		//Motor.B.setSpeed(100);
		Motor.A.forward();
		Motor.B.forward();
	}

	public static void turnLeft() {
		System.out.println("Turning Left");
		//Motor.A.setSpeed(50);
		//Motor.B.setSpeed(50);
		Motor.A.forward();
		Motor.B.backward();
	}

	public static void turnRight() {
		System.out.println("Turning Right");
		//Motor.A.setSpeed(50);
		//Motor.B.setSpeed(50);
		Motor.A.backward();
		Motor.B.forward();

	}

	public static void stop() {
		System.out.println("Stopped");
		Motor.A.stop();
		Motor.B.stop();
	}

	public static void dance() {
		//playTone(500,500);
		stop();
	}
}