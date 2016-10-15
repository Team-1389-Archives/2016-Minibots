package arduinoControl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class UDPServer extends Thread{

    private DatagramSocket recieveSocket;
    private DatagramSocket sendSocket;
    private InetAddress ipOfESP; 
	private ArrayList<String> readings = new ArrayList<String>();
    
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
        synchronized(readings){
        	readings.add(reading);
        }
	}
	
	/**
	 * Sends a command to the ESP. Possible forms are as follows: <p>
	 * Initialization (type|portNumber|function;) <br>
	 * Set up reading from a digital port: "d|[2-13]|r;" Example: "d|2|r;"<br>
	 * Set up reading from an analog port: "a|[0-5]|r;" Example: "a|0|r;"<br>
	 * Set up writing to a digital port: "d|[2-13]|w;" Example: "d|10|w;"<br>
	 * Set up writing to a servo motor: "s|[3,5,6,9,10,11]|w;" Example: "s|3|w;"<p>
	 * 
	 * Send update for writing (Only after initialization) (type|portNumber|value;): <br>
	 * Write to a digital port: "d|[2-13]|[0,1]" Example: "d|10|1;"<br>
	 * Write to a servo port: "d|[2-13]|[0,180];" Example: "s|3|79;;"<p>
	 * @param message The message to send in the above format
	 * @throws IOException 
	 */
	public void sendString(String message) throws IOException{
		sendSocket.send(new DatagramPacket(message.getBytes(), message.length(), ipOfESP, Constants.SEND_PORT));
	}
	
	/**
	 * Returns all readings and clears the ArrayList
	 * @return Readings since the last call of getNewReadings()
	 */
	public ArrayList<String> getNewReadings(){
		synchronized(readings){
			ArrayList<String> temp = (ArrayList<String>) readings.clone();
			readings.clear();
			return temp;
		}
	}
	
	
}
