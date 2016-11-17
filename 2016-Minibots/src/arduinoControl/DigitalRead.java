package arduinoControl;

class DigitalRead extends Read{

	public DigitalRead(int pin) {
		super(Constants.TYPE.DIGITAL, pin);
	}

}
