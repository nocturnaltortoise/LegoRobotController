/**
* @author Alex Adams, Simon Turner
*/

import icommand.nxt.*;
import icommand.nxt.comm.NXTCommand;

public class Robot {
	public static void main(String[] args) throws InterruptedException {
		final int INTERVAL = 100;

		NXTCommand.open();
		NXTCommand.setVerify(true);

		LightSensor floorSensor = new LightSensor(SensorPort.S3);

		while(true){
			System.out.println(floorSensor.getLightPercent());
			Thread.sleep(INTERVAL);
		}

		NXTCommand.close();

	}
}