package arduinoControl;

import java.io.IOException;

import arduinoControl.Constants.TYPE;

public class Servo extends Write{

	Servo(TYPE type, int pin) {
		super(type, pin);
	}

	@Override()
	protected boolean check(){
		return pin == 3 || pin == 5 || pin == 6 || pin == 7 || pin == 8 || pin == 9;
	}
	
	public void send(int value) throws IOException{
		if(value <= 180 && value >= 0){
			super.send(value, "s");
		}
	}
}
