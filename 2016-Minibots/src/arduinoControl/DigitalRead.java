package arduinoControl;

public class DigitalRead extends Read{

	public DigitalRead(int pin) {
		super(Constants.TYPE.DIGITAL, pin);
	}

}
