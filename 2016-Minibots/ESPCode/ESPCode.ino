//THIS WORKS OH YEAHH

#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

unsigned int localUdpPort = 4210;  // local port to listen on
WiFiUDP Udp;

void setup()
{
  Serial.begin(115200);
  Serial.println();

  Serial.print("Setting soft-AP ... ");
  boolean result = WiFi.softAP("ESPFinalTest");
  if(result == true)
  {
    Serial.println("Ready");
  }
  else
  {
    Serial.println("Failed!");
  }

   Udp.begin(localUdpPort);

   IPAddress myIP = WiFi.softAPIP();
   Serial.print("Listening on port: ");
   Serial.print(localUdpPort);
   Serial.print(", IP Adress: ");
   Serial.println(myIP);
}

void loop()
{

  int packetSize = Udp.parsePacket();
  if (packetSize){ //Packet not empty
    Serial.println(packetSize);
  }
  
}
