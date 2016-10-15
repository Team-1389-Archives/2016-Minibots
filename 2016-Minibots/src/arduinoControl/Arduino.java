package arduinoControl;

import arduinoControl.Constants.TYPE;

public class Arduino {

	//Null means no pin attached yet
	private static Pin[] digitalPins = new Pin[14]; //0,1 should always be null
	private static AnalogRead[] analogPins = new AnalogRead[6];


	synchronized static public void updateValue(String message){
		System.out.println(message);
		boolean goodMessage = false;
		try{
			String[] parts = message.split("|");
			String type = parts[0];
			int pinNum = Integer.parseInt(parts[3]);
			int value = Integer.parseInt(parts[2]);

			if(type.equals("a")){
				if(analogPins[pinNum] != null){
					AnalogRead pin = analogPins[pinNum];
					pin.update(value);
					goodMessage = true; 
				}
			}
			else if(type.equals("d")){
				if(digitalPins[pinNum] != null){
					Pin pin = digitalPins[pinNum];
					if(pin instanceof DigitalRead){
						((DigitalRead) pin).update(value);
						goodMessage = true; 
					}
				}
					
			}
				
		}
		catch(Exception e){
		}
		
		System.out.println("BAD MESSAGE");

	}



	synchronized static public boolean attachPin(Pin pin){
		int pinNum = pin.getPinNum();
		TYPE pinType = pin.getType();
		if(pin instanceof AnalogRead && analogPins[pinNum] == null){
			analogPins[pinNum] = (AnalogRead) pin;
			return true;
		}
		else if(pinType == TYPE.DIGITAL && digitalPins[pinNum] == null){
			if(pinNum > 1){ //Makes sure Tx, Rx not used
				digitalPins[pinNum] = pin;
				return true;
			}		
		}
		return false;
	}


}
