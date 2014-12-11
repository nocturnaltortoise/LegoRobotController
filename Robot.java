/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {
	public static void main(String[] args) throws InterruptedException {
		final int INTERVAL = 100;
		final int CUTOFF = 60;
		final int DISTANCE = 20;
		int lightLevel = 0;
		boolean goalReached = false;

		NXTCommand.open();
		NXTCommand.setVerify(true);

		LightSensor floorSensor = new LightSensor(SensorPort.S3);
		UltrasonicSensor distSensor = new UltrasonicSensor(SensorPort.S2)

		while (!goalReached) {
			lightLevel = floorSensor.getLightPercent());
			System.out.println(lightLevel);
			if (distSensor.getDistance() < DISTANCE) {
				System.out.println("Object infront");
			}
			if (lightLevel < CUTOFF) {
				System.out.println("Not on black line");
			}
			Thread.sleep(INTERVAL);
		}

		dance()

	}

	public void turnLeft() {
		// use of motors
	}

	public void dance() {

	}
}