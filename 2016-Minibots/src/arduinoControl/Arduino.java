package arduinoControl;

public class Arduino {

	synchronized static public void updateValue(String message){
		System.out.println(message);
		String[] parts = message.split("|");
		String key = parts[0];
		int value = Integer.parseInt(parts[1]);
	}

	synchronized static public boolean checkAvailability(Constants.TYPE type, int pin){
		return true;
	}

	//synchronized public void getValue(Constants.TYPE type, int pin){

	//}
}
