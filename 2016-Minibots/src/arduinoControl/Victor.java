package arduinoControl;

import java.io.IOException;

public class Victor{
	Servo servo;
	Victor(int pin) {
		this.servo=new Servo(pin);
	}
	public void set(double pwr){
		if(pwr<-1){
			pwr=-1;
		}
		if(pwr>1){
			pwr=1;
		}
		try {
			servo.send((int) (pwr * 90 + 90));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
