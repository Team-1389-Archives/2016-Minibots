package arduinoControl;

import arduinoControl.Constants.TYPE;

abstract class Pin {

	protected Constants.TYPE type; 
	protected int pin;
	Pin(Constants.TYPE type, int pin){
		this.type = type;
		this.pin = pin;
		if(!Arduino.attachPin(this)){
			throw new IllegalArgumentException();
		}
	}
	
	protected abstract void sendMessage();

	protected int getPinNum() {
		return pin;
	}

	protected TYPE getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
