package arduinoControl;

import arduinoControl.Constants.TYPE;

class Read extends Pin{

	Read(TYPE type, int pin) {
		super(type, pin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void sendMessage() {
		// TODO Auto-generated method stub
		
	}
	
	protected void update(int value){
		
	}

}
