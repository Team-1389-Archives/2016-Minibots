package arduinoControl;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Joystick {
	int joyPort;
	Controller jinCont;
	List<Component> axisPorts;
	List<Component> buttonPorts;

	public Joystick(int port) {
		this.joyPort = port;
		axisPorts = new ArrayList<>();
		buttonPorts = new ArrayList<>();

		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		if (port < controllers.length) {
			jinCont = controllers[joyPort];

		} else {
			jinCont = null;
		}
	}

	private void initPortLists() {
		Component[] comps = jinCont.getComponents();
		for (Component c : comps) {
			if (c.isAnalog()) {
				axisPorts.add(c);
			} else {
				buttonPorts.add(c);
			}
		}
	}

	public double getAxis(int port) {
		if (jinCont == null) {
			System.out.println("error! No joystick plugged in on port " + joyPort);
			return 0.0;
		} else {
			return axisPorts.get(port).getPollData();
		}
	}

	public double getButton(int port) {
		if (jinCont == null) {
			System.out.println("error! No joystick plugged in on port " + joyPort);
			return 0.0;
		} else {
			return buttonPorts.get(port).getPollData();
		}
	}
}
