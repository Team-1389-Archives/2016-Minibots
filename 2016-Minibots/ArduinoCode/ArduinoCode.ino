
void setup(){
  Serial.begin(115200);
}


//If element is equal to 1, read from that indexed pin A0-A5
int analogPinsRead[] = {0,0,0,0,0,0};
//If element is equal to 1, read from that indexed pin + 2 2-12
int digitalPinsRead[] = {0,0,0,0,0,0,0,0,0,0,0};
//Write value to indexed pin 3,5,6,9,10,11. Again add 2. 
//Other indexed elements should always be 0 
int analogPinsWrite[] = {0,0,0,0,0,0,0,0,0,0,0};
//If element is equal to 1, write to that indexed pin + 2 2-12
int digitalPinsWrite[] = {0,0,0,0,0,0,0,0,0,0,0};

boolean packageDone = false;
String package = "";
long startTime = millis(); 
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
    changePorts(package);
    writeToPorts();
    packageDone = false;
    package = "";
  }

  
  if(millis() - startTime > 100){
      readAndSendPorts();
      startTime = millis();
  }
}

//All lowercase
void changePorts(String package){
    //Read or write
    String function = getValue(package, '|', 0);
    //Port to write/read to, number not letter
    String sPort = (getValue(package, '|', 1));
    int port = sPort.toInt(); 
    //d or a
    String type = getValue(package, '|', 2);

    Serial.println(function);
    Serial.println(port);
    Serial.println(type);

    if(function == "read"){
      if(type == "a"){
        analogPinsRead[port] = 1;
      }
      else if(type == "d"){
        digitalPinsRead[port] = 1;
      }
    }
    else if(function == "write"){
      if(type == "a"){
        //PWM
        //Amount of voltage to write
        int value = getValue(package, '|', 3).toInt();
        analogPinsWrite[port] = value;
      }
      //else if(type == "d"){
        //String value = getValue(package, '|', 3);
        //analogPinsWrite[port] = value;
      //}
    }
}  

String getValue(String data, char separator, int index)
{
 int found = 0;
  int strIndex[] = {
0, -1  };
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

void writeToPorts(){
  for(int i = 0; i < 11; i++){
    if(i == 3 - 2 || i == 5 - 2 || i == 6 - 2 || i == 9 - 2 || i == 10 - 2 || i == 11 - 2){
      analogWrite(i + 2, analogPinsWrite[i]);
    }
  }
  //Do digital later
}  

void readAndSendPorts(){
  for(int i = 0; i < 11; i++){
    if(digitalPinsRead[i] == 1){
      int value = digitalRead(i + 2);
      Serial.print("d|");
      Serial.print(value);
      Serial.print("\n");
    }
  }
  for(int i = 0; i < 5; i++){
    if(analogPinsRead[i] == 1){
      int value = analogRead(i + 2);
      Serial.print("a|");
      Serial.print(value);
      Serial.print("\n");
    }
  }
}

