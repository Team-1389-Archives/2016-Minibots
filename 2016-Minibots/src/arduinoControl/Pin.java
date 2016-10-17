package arduinoControl;

import java.io.IOException;

import arduinoControl.Constants.TYPE;

abstract class Pin {

	protected Constants.TYPE type; 
	protected int pin;
	Pin(Constants.TYPE type, int pin){
		this.type = type;
		this.pin = pin;
		if(check()){
			if(!Arduino.attachPin(this)){
				throw new IllegalArgumentException();
			}
			try {
				UDPServer.sendString(getInitilizationMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Can be overridden by implementations to check condition before attaching class.
	 * Allows implementations to check their own stuff too.
	 * This is actually really clever, if i do say so myself.
	 * Im 99% sure that java works this way.
	 * @return True if pin is ok, false if not
	 */
	protected boolean check(){
		return true;
	}

	protected int getPinNum() {
		return pin;
	}

	protected TYPE getType() {
		return type;
	}
	
	/**
	 * @return The initlization message
	 */
	protected abstract String getInitilizationMessage();
}
