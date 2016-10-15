package arduinoControl;

import arduinoControl.Constants.TYPE;

public class DigitalRead extends Read{

	DigitalRead(int pin) {
		super(Constants.TYPE.DIGITAL, pin);
	}

}
