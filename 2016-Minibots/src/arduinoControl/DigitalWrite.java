package arduinoControl;

import java.io.IOException;

class DigitalWrite extends Write{

	DigitalWrite(int pin) {
		super(Constants.TYPE.DIGITAL, pin);
	}
	
	public void send(int value) throws IOException{
		if(value == 0 || value == 1){
			super.send(value, "d");
		}
	}

	@Override
	protected String getWriteType() {
		return "d";
	}

}
