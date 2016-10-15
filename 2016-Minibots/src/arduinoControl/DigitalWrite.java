package arduinoControl;

import java.io.IOException;

public class DigitalWrite extends Write{

	DigitalWrite(int pin) {
		super(Constants.TYPE.DIGITAL, pin);
	}
	
	public void send(int value) throws IOException{
		if(value == 0 || value == 1){
			super.send(value, "d");
		}
	}

}
