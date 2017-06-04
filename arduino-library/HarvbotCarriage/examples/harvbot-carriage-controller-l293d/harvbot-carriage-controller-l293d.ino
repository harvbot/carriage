#include <HarvbotCarriage.h>
#include <HarvbotMotorAdafruitL293D.h>
#include <HarvbotCarriageAdafruitL293D.h>

HarvbotCarriage* carriage;

void setup() 
{
  Serial.begin(9600);
   
  carriage = new HarvbotCarriageAdafruitL293D();
  carriage->setSpeed(255);
}

void loop() 
{
  // Read message.
  String msg = Serial.readString();

  if(msg != NULL && msg != "")
  {
    // Get command.
    String cmd = getRequestValue(msg, ':', 1);

    if(cmd == "left")
    {
        // Turn left.
        carriage->turnLeft();

        // Return response.
        Serial.println(getResponse(cmd, ""));
    }
    else if(cmd == "right")
    {
        // Turn right.
        carriage->turnRight();

        // Return response.
        Serial.println(getResponse(cmd, ""));
    }
    else if(cmd == "stop")
    {
        // Stop.
        carriage->stop();

        // Return response.
        Serial.println(getResponse(cmd, ""));
    }
    else if(cmd == "forward")
    {
        // Moves forward.
        carriage->forward();

        // Return response.
        Serial.println(getResponse(cmd, ""));
    }
    else if(cmd == "backward")
    {
        // Moves backward.
        carriage->backward();

        // Return response.
        Serial.println(getResponse(cmd, ""));
    }
    else
    {
        Serial.println(getResponse("invalid-command", cmd));
    }
  }
}

String getResponse(String command, String data)
{
    String result = "hcarriage:";
    result += command;

    if(data != NULL && data != "")
    {
      result += ":";
      result += data;
    }
    
    result += ":~hcarriage";
    return result;
}

String getRequestValue(String data, char separator, int index)
{
    int found = 0;
    int strIndex[] = { 0, -1 };
    int maxIndex = data.length() - 1;

    for (int i = 0; i <= maxIndex && found <= index; i++) {
        if (data.charAt(i) == separator || i == maxIndex) {
            found++;
            strIndex[0] = strIndex[1] + 1;
            strIndex[1] = (i == maxIndex) ? i+1 : i;
        }
    }
    return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}

