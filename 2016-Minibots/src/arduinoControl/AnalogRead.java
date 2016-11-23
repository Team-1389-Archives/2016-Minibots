package arduinoControl;

public class AnalogRead extends Read{

	public AnalogRead(int pin) {
		super(Constants.TYPE.ANALOG, pin);
	}

}
