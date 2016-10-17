package arduinoControl;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException{
		UDPServer.initializeServer();
		
		Servo s = new Servo(3);
		for(int i = 0; i < 10; i++){
			s.send(180);
			Thread.sleep(500);
			s.send(0);
			Thread.sleep(500);
		}

	}
}
