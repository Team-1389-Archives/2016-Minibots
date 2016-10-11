//If equal to 1, then pin in use. Pins 0 and 1 are in use from the beggining as they are Tx and Rx
int digitalPins[14] = {1,1,0,0,0,0,0,0,0,0,0,0,0};

//True if new port set, false if otherwise
boolean setNewPort(String package){
  String type = getValue(package, '|', 0); //a or d or s
  int port = (getValue(package, '|', 1)).toInt(); //2-13 or 0-6
  String function = getValue(package, '|', 2); //w or r

  //Makes sure ports are valid and unused
  if(!checkPort(port, type)){
    return false;
  }

  if(function == "r"){
      if(type == "a"){
        analogPinsRead[port] = 1;
      }
      else if(type == "d"){
        digitalPinsRead[port] = 1;
        digitalPins[port] = 1;
      }
  }
  else if(function == "w"){
      if(type == "s"){
        servos[port].attach(port);
        digitalPins[port] = 1;
      }
      else if(type == "d"){
        digitalPinsWrite[port] = 1;
        digitalPins[port] = 1;
      }
  }
  else{
    //Package not a set new port, just exit the method returning false
    return false;
  }
  return true;
    
}

boolean checkPort(int port, String type){
  if(type == "a"){
    return port >= 0 && port <= 5 && analogPinsRead[port] == 0;
  }
  else if(type == "d"){
    return port >= 0 && port <= 13 && digitalPins[port] == 0;
  }
  else if(type == "s"){
    return (port == 3 || port == 5 || port == 6 || port == 9 || port == 10 || port == 11) && digitalPins[port] == 0;
  }
}


