
void setup(){
  Serial.begin(115200);
}

void loop(){
 while (Serial.available() > 0){
    char c = Serial.read();
    if(c != 10 && c != 13)
      Serial.println(c);
  }  
}

