package arduinoControl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Test extends JPanel implements KeyListener{

	static int change = 0;
	public static void main(String[] args) throws IOException, InterruptedException{
		UDPServer.initializeServer();
		
		Test t = new Test();
		JFrame f = new JFrame();
		f.setSize(400, 400);
		f.getContentPane().add(t);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		Servo s = new Servo(3);
		s.send(90);
		while(true){
			s.send(s.getLastValue() + change);
			change = 0;
			Thread.sleep(10);
		}

	}

	Test(){
		addKeyListener(this);
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyChar() == 's'){
			change = -5;
		}
		else if(arg0.getKeyChar() == 'd'){
			change = 5;
		}
		System.out.println(arg0.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		System.out.println(arg0.getKeyChar());
		
	}
}
