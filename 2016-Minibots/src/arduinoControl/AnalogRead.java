package arduinoControl;

import arduinoControl.Constants.TYPE;

public class AnalogRead extends Read{

	AnalogRead(int pin) {
		super(Constants.TYPE.ANALOG, pin);
	}

}
