package arduinoControl;

import java.io.IOException;

import arduinoControl.Constants.TYPE;

public class Write extends Pin{

	Write(TYPE type, int pin) {
		super(type, pin);
	}

	protected void send(int value, String type) throws IOException {
		UDPServer.sendString(type + "|" + pin + "|" + value);
	}


}
