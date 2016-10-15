package arduinoControl;

public class Pin {

	Pin(Constants.TYPE type, int pin){
		if(!Arduino.checkAvailability(type, pin)){
			throw new IllegalArgumentException();
		}
	}
	
	private void sendMessage(Constants.TYPE type,)
}
