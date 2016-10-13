#include <Servo.h>

void setup(){
  Serial.begin(115200);
}


//If element is equal to -1, pin is not set to read
//Otherwise element is equal to last read value
int analogPinsRead[6] = {-1,-1,-1,-1,-1,-1};

//If element is equal to -1, pin is not set to read
//Otherwise element is equal to last read value
int digitalPinsRead[14] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

//If an index is not null, them write that servo pulse
//All pins besides 3,5,6,9,10,11 should always be null
Servo servos[14] = {};

//If element is equal to 1, pin is ready to write but still LOW
//If element is equal to 2, pin is sending HIGH
int digitalPinsWrite[14] = {};

boolean packageDone = false;
String package = "";
long startTime = millis(); 
int packageCount = 0;
void loop(){
 if (Serial.available() > 0){
    char c = Serial.read();
    if(c == 59 /*Semi Colon*/){
      packageDone = true;
    }
    else{
      package = package + c;
    }
  }  
  if(packageDone){
    updatePorts(package);
    //writeToPorts();
    packageDone = false;
    package = "";
  }

  //Limit to 10 packages a second
  if(packageCount < 10){
      packageCount = packageCount + readAndSendPorts();
  }
  if(millis() - startTime >= 1000){
      startTime = millis();
      packageCount = 0;
  }
}

  
void updatePorts(String package){
  if(!setNewPort(package)){
    //Package is an update to a write, not setting a new port
     String type = getValue(package, '|', 0); //d or s
     int port = (getValue(package, '|', 1)).toInt(); //2-13 or 0-6
     int value = getValue(package, '|', 2).toInt(); //If d, should be 1 or 0
     
     if(type == "d"){
        if(digitalPinsWrite[port] != 0 && (value == 0 || value == 1)){
          digitalPinsWrite[port] = value + 1;
          digitalWrite(port, value);
        }
     }
     else if(type == "s" && servos[port].attached()){
         servos[port].write(value);   
     }
  }

}

int readAndSendPorts(){
  int count = 0;
  for(int i = 2; i < 11; i++){
    if(digitalPinsRead[i] >= 0){
      int value = digitalRead(i);
      if(value != digitalPinsRead[i]){ //value has changed
        sendValue("d",i,value);
        count++;
        digitalPinsRead[i] = value;
      }
    }
  }
  for(int i = 0; i <= 5; i++){
    if(analogPinsRead[i] >= 0){
      int value = analogRead(i);
      if(abs(value - analogPinsRead[i]) > 50){ //value has changed
        sendValue("a",i,value);
        count++;
        analogPinsRead[i] = value;
      }
    }
  }
  return count;
}

void sendValue(String type, int port, int value){
  Serial.print(type);
  Serial.print("|");
  Serial.print(port);
  Serial.print("|");
  Serial.print(value);
  Serial.print("\n");
}


//Utility method, copied from the internet
String getValue(String data, char separator, int index)
{
  int found = 0;
  int strIndex[] = {0, -1};
  int maxIndex = data.length()-1;
  for(int i=0; i<=maxIndex && found<=index; i++){
    if(data.charAt(i)==separator || i==maxIndex){
      found++;
      strIndex[0] = strIndex[1]+1;
      strIndex[1] = (i == maxIndex) ? i+1 : i;
    }
 }
  return found>index ? data.substring(strIndex[0], strIndex[1]) : "";
}

