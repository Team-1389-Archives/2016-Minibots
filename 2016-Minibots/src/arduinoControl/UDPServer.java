package arduinoControl;

import java.io.IOException;
import java.net.*;

public class UDPServer extends Thread{

    private DatagramSocket recieveSocket;
    private DatagramSocket sendSocket;
    private InetAddress ipOfESP; 
    
    UDPServer() throws SocketException, UnknownHostException{
		recieveSocket = new DatagramSocket(Constants.RECIEVE_PORT);
		sendSocket = new DatagramSocket(Constants.SEND_PORT);
		ipOfESP = InetAddress.getByName("192.168.4.1"); 
		
	}
	
	@Override
	public void run(){
        byte[] buf = new byte[128];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        
        //Try to recieve packet, hangs until new packet comes in
        try {
			recieveSocket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //Truncate last 2 chars, turn to string
        String reading = new String(packet.getData(), 0 , packet.getLength() - 2);

        //Send to arduin to update
        Arduino.updateValue(reading);
	}
	
	/**
	 * Sends a command to the ESP. Possible forms are as follows: <p>
	 * Initialization (type|pinNumber|function;) <br>
	 * Set up reading from a digital pin: "d|[2-13]|r;" Example: "d|2|r;"<br>
	 * Set up reading from an analog pin: "a|[0-5]|r;" Example: "a|0|r;"<br>
	 * Set up writing to a digital pin: "d|[2-13]|w;" Example: "d|10|w;"<br>
	 * Set up writing to a servo motor: "s|[3,5,6,9,10,11]|w;" Example: "s|3|w;"<p>
	 * 
	 * Send update for writing (Only after initialization) (type|pinNumber|value;): <br>
	 * Write to a digital pin: "d|[2-13]|[0,1]" Example: "d|10|1;"<br>
	 * Write to a servo pin: "d|[2-13]|[0,180];" Example: "s|3|79;;"<p>
	 * @param message The message to send in the above format
	 * @throws IOException 
	 */
	public void sendString(String message) throws IOException{
		sendSocket.send(new DatagramPacket(message.getBytes(), message.length(), ipOfESP, Constants.SEND_PORT));
	}

	
}
