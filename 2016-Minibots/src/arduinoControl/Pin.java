package arduinoControl;

public abstract class Pin {

	protected Constants.TYPE type; 
	protected int pin;
	Pin(Constants.TYPE type, int pin){
		if(!Arduino.checkAvailability(type, pin)){
			throw new IllegalArgumentException();
		}
		this.pin = pin;
		this.type = type;
	}
	
	protected abstract void sendMessage();
}
