#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

unsigned int localUdpPort = 4210;  // local port to listen on
WiFiUDP Udp;
IPAddress remoteIP;
int remotePort = 0;

//IP Adress: 192.168.4.1
void setup()
{
  Serial.begin(115200);
  WiFi.softAP("ESPFinalTest");
  
  //Serial.println();
  /*Serial.print("Setting soft-AP ... ");
  boolean result = WiFi.softAP("ESPFinalTest");
  if(result == true)
  {
    Serial.println("Ready");
  }
  else
  {
    Serial.println("Failed!");
  }*/

   Udp.begin(localUdpPort);

   /*IPAddress myIP = WiFi.softAPIP();
   Serial.print("Listening on port: ");
   Serial.print(localUdpPort);
   Serial.print(", IP Adress: ");
   Serial.println(myIP);
   */
}

void loop()
{

  //Looks for packets, sends to Arduino
  int packetSize = Udp.parsePacket();
  if (packetSize > 0){ //Packet not empty
    remoteIP = Udp.remoteIP();
    //remotePort = Udp.remotePort();

    char incomingPacket[packetSize];
    int len = Udp.read(incomingPacket, packetSize);
    
    //Truncates the end signifier bytes
    if (len > 0)
    {
      incomingPacket[len] = 0; 
    }

    //Send packet to ESP
    Serial.write(incomingPacket);

  }

   //Gets input from arduino and sends to computer over UDP
   String packetToSend = "";
   while(Serial.available() > 0){
      char nextChar = Serial.read();
      
      //Sends packet if next char is carriage return
      if(nextChar == '\n'){
        sendPacket(packetToSend);
        packetToSend = "";
      } 
      else{
        packetToSend.concat(nextChar);
      }
      delay(10); 
      
  }


  
  
}

//Sends packet to last computer a packet was recieved from
void sendPacket(String packet){  
  
  //Creates byte array
  int bufferLength = packet.length() + 1;
  byte buf[bufferLength];
  packet.getBytes(buf, bufferLength);

  /*
  Serial.print("Packet Sent: ");
  Serial.println(packet);
  Serial.print("IP Adress: ");
  Serial.print(remoteIP);
  Serial.print(", Port: ");
  Serial.println(remotePort);
  */

  Udp.beginPacket(remoteIP, 4211);
  Udp.write(buf, bufferLength);
  Udp.endPacket();
  
}


