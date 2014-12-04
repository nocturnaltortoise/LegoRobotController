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

		LightSensor l = new LightSensor(SensorPort.S3);

		for (int i=0;i<100;i++) {
			System.out.println(l.getLightPercent());
			Thread.sleep(INTERVAL);
		}

		NXTCommand.close();

	}
}