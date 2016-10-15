package arduinoControl;

import arduinoControl.Constants.TYPE;

public class Servo extends Write{

	Servo(TYPE type, int pin) {
		super(type, pin);
		if(pin != 3 || pin != 5 || pin != 6 || pin != 3 || pin != 5 || pin != 6){
			throw new IllegalArgumentException();
		}
	}

}
