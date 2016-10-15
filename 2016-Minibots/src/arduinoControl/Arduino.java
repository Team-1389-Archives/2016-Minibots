package arduinoControl;

public class Arduino {

	//Null means no pin attached yet
	private static Pin[] digitalPins = new Pin[14]; //0,1 should always be null
	private static Pin[] analogPins = new Pin[6];
	
	
	synchronized static public void updateValue(String message){
		System.out.println(message);
		String[] parts = message.split("|");
		String key = parts[0];
		int value = Integer.parseInt(parts[1]);
	}

	synchronized static public boolean checkAvailability(Constants.TYPE type, int pin){
		if(type == Constants.TYPE.ANALOG){
			return analogPins[pin] == null;
		}
		else if(pin > 1){ //Makes sure Tx, Rx not used
			return digitalPins[pin] == null;
		}
		return false;
	}

	//synchronized public void getValue(Constants.TYPE type, int pin){

	//}
}
