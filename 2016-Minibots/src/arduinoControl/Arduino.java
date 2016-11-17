package arduinoControl;

import arduinoControl.Constants.TYPE;

class Arduino {

	//Null means no pin attached yet
	private static Pin[] digitalPins = new Pin[14]; //0,1 should always be null
	private static AnalogRead[] analogPins = new AnalogRead[6];


	/**
	 * Checks first to see if pin in message is legit and there is already a class waiting to recieve.
	 * Then sends it to that class
	 * @param message
	 */
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
					Pin pin = analogPins[pinNum];
					if(pin instanceof AnalogRead){ //Should always be AnalogRead, but just to make sure
						((AnalogRead) pin).update(value);
						goodMessage = true; 
					}
				}
			}
			else if(type.equals("d")){
				if(digitalPins[pinNum] != null){
					Pin pin = digitalPins[pinNum];
					if(pin instanceof DigitalRead){ //Could be a digital write or servo/pwm pin
						((DigitalRead) pin).update(value);
						goodMessage = true; 
					}
				}

			}

		}
		catch(Exception e){
		}

		if(!goodMessage){
			System.out.println("BAD MESSAGE");
		}
	}



	synchronized static public boolean attachPin(Pin pin){
		int pinNum = pin.getPinNum();
		TYPE pinType = pin.getType();
		if(pinType == TYPE.ANALOG && analogPins[pinNum] == null){
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
