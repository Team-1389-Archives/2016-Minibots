package arduinoControl;

import java.io.IOException;

import arduinoControl.Constants.TYPE;

abstract class Write extends Pin{

	private int lastValueSent = 0;
	Write(TYPE type, int pin) {
		super(type, pin);
	}

	protected void send(int value, String type) throws IOException {
		lastValueSent = value;
		UDPServer.sendString(type + "|" + pin + "|" + value + ";");
	}

	@Override
	protected String getInitilizationMessage() {
		String t = getWriteType();
		return t + "|" + getPinNum() + "|w;";
		
	}

	protected abstract String getWriteType();
	
	public int getLastValue(){
		return lastValueSent;
	}
	

}
