package arduinoControl;

import java.io.IOException;

public class CANTalon extends Servo{

	CANTalon(int pin) {
		super(pin);
	}
	@Override
	public void send(int value) throws IOException{
		if(value >= -1 && value <= 1){
			super.send((int)((value+1)*90), "s");
		}
	}

}
