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


	@Override
	protected String getInitilizationMessage() {
		String t = getType() == TYPE.ANALOG? "a" : "d";
		return t + "|" + getPinNum() + "|r;";
		
	}

}
