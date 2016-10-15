package arduinoControl;

import arduinoControl.Constants.TYPE;

class Read extends Pin{

	int value;
	Read(TYPE type, int pin) {
		super(type, pin);
		value = 0;
	}

	
	protected void update(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}

}
