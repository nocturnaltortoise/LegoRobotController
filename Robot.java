/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {

	static int blackDetectCount = 0;

	public static void main(String[] args) throws InterruptedException {
		final int INTERVAL = 100;
		final int LINECUTOFF = 48;
		final int LINECUTOFFRIGHT;
		final int SPOTCUTOFF = 40;
		final int DISTANCE = 20;
		// int leftLightLevel = 0;
		// int rightLightLevel = 0;
		// boolean goalReached = false;

		NXTCommand.open();
		NXTCommand.setVerify(true);

		LightSensor leftFloorSensor = new LightSensor(SensorPort.S1);
		LightSensor rightFloorSensor = new LightSensor(SensorPort.S2);
		UltrasonicSensor distSensor = new UltrasonicSensor(SensorPort.S4);

		// 33 - Black Spot
		// 50 - Black Line
		// 60 - Floor

		// Motor A - Right Wheel
		// Motor B - Left Wheel

		// while(!blackLineDetected(leftFloorSensor.getLightPercent()) && !blackLineDetected(rightFloorSensor.getLightPercent()){
		// 	if(blackDetectCount == 0){
		// 		forward();
		// 	}else{
		// 		turnLeft();
		// 	}
		// }


		// while(blackLineDetected(leftFloorSensor.getLightPercent()) || blackLineDetected(rightFloorSensor.getLightPercent())){


		// 	if(blackLineDetected(leftFloorSensor.getLightPercent()) && !blackLineDetected(rightFloorSensor.getLightPercent())){
		// 		turnLeft();
		// 	}else if(!blackLineDetected(leftFloorSensor.getLightPercent()) && blackLineDetected(rightFloorSensor.getLightPercent())){
		// 		turnRight();
		// 	}

		// }

		while(true){
			System.out.println("Left Sensor: " + leftFloorSensor.getLightPercent());
			System.out.println("Right Sensor: " + rightFloorSensor.getLightPercent());

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

	private static boolean blackLineDetected(int lightPercent){

		if(lightPercent < LINECUTOFF){
			blackDetectCount++;
			return true;
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