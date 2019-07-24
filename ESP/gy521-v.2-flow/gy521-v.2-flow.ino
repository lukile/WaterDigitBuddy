#include <ArduinoJson.h>

#include <MPU6050_tockn.h>

#include <SPI.h>

#include <time.h>

#include<Wire.h>
#include <array>

#include <HTTPClient.h>

#include <WiFiUdp.h>
#include <ETH.h>
#include <WiFiSTA.h>
#include <WiFiMulti.h>
#include <WiFiType.h>
#include <WiFiServer.h>
#include <WiFiClient.h>
#include <WiFiScan.h>
#include <WiFiAP.h>
#include <WiFiGeneric.h>
#include <WiFi.h>
#include "config2.h"

MPU6050 mpu6050(Wire);

String device_id = "WDB-001A";
String user_id;

long timerStartSession = 0;
double arraySpeeds[1000];
int cpt = 0;
double currentV = 0;
double currentX = 0;
double previousV = 0;
double previousX = 0;

double distance = 0;
double averageSpeed = 0;

String averageSpeedJson;
String distanceJson;

float minimalHighAccX = 0.20;
float minimalLowAccX = -0.20;

String datePerformance;
String startTime;
String endTime;

int flagLight = 0;
int firstTimer = 0;
int lastTimer = 0;

int isPerformanceStart = 0;
int isWifiStart = 0;

const char* ssid = "ssid";
const char* password = "pass";

int light;

void setup(){
  Serial.begin(9600);
  //Set RGB
  pinMode(blue, OUTPUT);
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  digitalWrite(red, LOW);
  digitalWrite(blue, LOW);
  digitalWrite(green, LOW);

  //Config 1 for ESP needs define port SDA, SCL
  if(CONFIG == 1) {
      Wire.begin(SDA, SCL);
  } else {
      Wire.begin();
  }
  mpu6050.begin();

  //Calculate offSets
  mpu6050.calcGyroOffsets(true);
  timerStartSession = millis();

  //PhotoSensor
  pinMode(PinSeuilLumiere, INPUT);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  //Start wifi
  connectToWifi();

  //Connect to get localDateTime
  configTime(3 * 3600, 0, "pool.ntp.org", "time.nist.gov");
}

void loop(){

  light = digitalRead(PinSeuilLumiere);

   if(light == 1 && flagLight == 0) {
      flagLight = 1;
      firstTimer = millis();
      //delay(2000);
  }

  
  if(light == 0 && flagLight == 1) {
    lastTimer = (millis() - firstTimer) / 1000; // en sec

    //Light is under high light between 3 - 5 seconds  
    if(lastTimer >= 3 && lastTimer < 6) // start or stop performances
    {
      
      //delay(800);
      flagLight = 0;

      //Start performance
      if(isPerformanceStart == 0) {
        isPerformanceStart = 1;
        //Turn on blue light
        digitalWrite(blue, HIGH);
      } else if(isPerformanceStart == 2) {
        //Calculate average speed and distanec
        averageSpeed = averageArray(arraySpeeds, cpt);
        averageSpeedJson = "\"speed\" : " + (String)averageSpeed + ",";
  
        distance = currentV * ((millis() - timerStartSession)/1000);
        distanceJson = "\"distance\" : " + (String)distance + ",";

        endTime = "\"endTime\" : \"" + (getCurrentDate() + getCurrentTime()) + "\"";
        //Stop measures (performances)
        isPerformanceStart = 0; 
        //Turn off blue light
        digitalWrite(blue, LOW);
      } 

      // Start or stop synchronization Wifi when hidden for 6 seconds or more
    } else if(lastTimer >= 6)  {
      //delay(800);
       if(isWifiStart == 0) {
        //Wifi start, turn on green light
        digitalWrite(green, HIGH); 
        //Get user id
        user_id = getUserId();
        isWifiStart = 1;
        //Wifi stop, turn off green light
        digitalWrite(green, LOW); 
      } else if(isWifiStart == 1) {
        //Wifi started, turn on green light
        digitalWrite(green, HIGH); 
        //Send datas to api
        sendToApi(datePerformance, startTime, averageSpeedJson, distanceJson, endTime);
        isWifiStart = 0;
        //Turn off the green light
        digitalWrite(green, LOW); // wifi stoped
      }
      //Reset flashLight
      flagLight = 0;
    }
  }

  //Parse json for datePerf, startTime, endTime 
  if(isPerformanceStart == 1) {
    datePerformance = "\"datePerformance\" : \"" +  (getCurrentDate() + getCurrentTime()) + "\"" + ",";
    startTime = "\"startTime\" : \"" + (getCurrentDate() + getCurrentTime()) + "\"" + ",";
    isPerformanceStart = 2;
  }
  
  if(isPerformanceStart == 2) {
    //Update offsets
    mpu6050.update();
    //Secure values, only take x acceleration when > 0.20  or < -0.20
    if(mpu6050.getAccX() > minimalHighAccX || mpu6050.getAccX() < minimalLowAccX) {
      //Keep previousValue, multiply x acceleration by gravity and time (50 = 0.05)
      currentV = previousV + mpu6050.getAccX()* 9.8 * 0.05;
      currentX = previousX + currentV * 0.05;
      previousV = currentV;
      previousX = currentX;
      //Put current value in array
      arraySpeeds[cpt] = currentV;
  
      cpt += 1;
    }
  }
  delay(50);
}

void connectToWifi() {
  //When no connection, turn on red light
  digitalWrite(red, HIGH);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }
  //When get connection, turn off red light
  digitalWrite(red, LOW);
}

double averageArray(double *array, int arraySize) {
  double sum = 0;
  //Compute average on speed
  for (int i = 0; i < arraySize; i++) { 
    sum += array[i];
  }
  return ((double) sum) / arraySize;
}

void sendToApi(String datePerformance, String startTime, String averageSpeed, String distance, String endTime) {
    connectToWifi();
  
    HTTPClient http;

    //Put correct api address
    http.begin("https://xxxx/performances/" + user_id);
    http.addHeader("Content-Type", "application/json");
    //Give device_id as header parameter
    http.addHeader("x-access-deviceid", device_id);
    //Parse json to post properly 
    String json = "{\"performance\" : { \"deviceId\" : \""+ device_id + "\"" + "," + "\n" + datePerformance + "\n" + startTime + "\n" + averageSpeed + "\n" + distance + "\n" + endTime + "\n"+ "}\n}";

    int httpCode = http.POST(json);

    String payload = http.getString();

    http.end();
    delay(1000);
}

String getUserId() {
    connectToWifi();

    HTTPClient http;
    
    //Change to correct api
    http.begin("https://xxxxx/users/current/deviceId");
    http.addHeader("x-access-deviceid", device_id);

    int httpCode = http.GET();

    String payload = http.getString();

    http.end();
    delay(1000);
    DynamicJsonDocument doc(1024);
    DeserializationError error = deserializeJson(doc, payload);

    if (error) {
      return "error json parsed";
    }
    String userId = doc["data"]["user"]["_id"];
    return userId;
}

String getCurrentDate() {
  struct tm* localTime = getLocalDateTime();
  String zero = "0";
  String date;

  //Format date because received as 2019-07-23T10:51:21.960Z
  //Check if we have a single number (under 10), for july by example 07, we only get 7, so we have to add a 0 before
  if(localTime->tm_mon < 10 && localTime->tm_mday < 10) {
    date = ((String)(localTime->tm_year + 1900) + "-" + zero + "" + ((String)(localTime->tm_mon + 1)) + "-" + zero + "" + ((String)localTime->tm_mday) + "T");
    return date;
  }
  if(localTime->tm_mon < 10) {
    date = ((String)(localTime->tm_year + 1900) + "-" + zero + "" + ((String)(localTime->tm_mon + 1)) + "-" + ((String)localTime->tm_mday) + "T");
    return date;
  }
  if(localTime->tm_mday < 10) {
    date = ((String)(localTime->tm_year + 1900) + "-" + ((String)(localTime->tm_mon + 1)) + "-" + zero + "" + ((String)localTime->tm_mday) + "T");
    return date;
  } 
  
  if(localTime->tm_mon > 10 && localTime->tm_mday > 10) {
    date = ((String)(localTime->tm_year + 1900) + "-" + ((String)(localTime->tm_mon + 1)) + "-" + ((String)localTime->tm_mday) + "T");
    return date;
  }
}

String getCurrentTime() {
  struct tm* localTime = getLocalDateTime();
  String zero = "0";
  String tim3;
  
  //Format date because received as 2019-07-23T10:51:21.960Z
  //Check if we have a value under 10, for 10:07:09 by example, we only get 7 and 9 for minutes and seconds, we have to add 0 before 
  if(localTime->tm_hour < 10 && localTime->tm_min < 10 && localTime->tm_sec < 10) {
      tim3 = zero +"" +((String)localTime->tm_hour) + ":" + zero + "" +((String)localTime->tm_min) + ":" + zero + "" + ((String)localTime->tm_sec) + ".960Z";
      return tim3;
  }
  if(localTime->tm_hour < 10 && localTime->tm_min < 10) {
      tim3 = zero +"" +((String)localTime->tm_hour) + ":" + zero + "" +((String)localTime->tm_min) + ":" + ((String)localTime->tm_sec) + ".960Z";
      return tim3;
  }
  if(localTime->tm_hour < 10 && localTime->tm_sec < 10) {
      tim3 = zero +"" +((String)localTime->tm_hour) + ":" +((String)localTime->tm_min) + ":" + zero + "" + ((String)localTime->tm_sec) + ".960Z";
      return tim3;
  }
  if(localTime->tm_min < 10 && localTime->tm_sec < 10) {
     tim3 = ((String)localTime->tm_hour) + ":" + zero + "" + ((String)localTime->tm_min) + ":" + zero + "" + ((String)localTime->tm_sec) + ".960Z";
     return tim3;
  }
  if(localTime->tm_sec < 10) {
    tim3 = ((String)localTime->tm_hour) + ":" + ((String)localTime->tm_min) + ":" + zero + "" + ((String)localTime->tm_sec) + ".960Z";
    return tim3;
  } 
  if (localTime->tm_min < 10) {
    tim3 = ((String)localTime->tm_hour) + ":" + zero + "" +((String)localTime->tm_min) + ":" + ((String)localTime->tm_sec) + ".960Z";
    return tim3;
  } 
  if (localTime->tm_hour < 10) {
    tim3 = zero + "" + ((String)localTime->tm_hour) + ":" + ((String)localTime->tm_min) + ":" + ((String)localTime->tm_sec) + ".960Z";
    return tim3;
  } 
  if(localTime->tm_sec >= 10 && localTime->tm_min >= 10 && localTime->tm_hour >= 10) {
    tim3 = ((String)localTime->tm_hour) + ":" + ((String)localTime->tm_min) + ":" + ((String)localTime->tm_sec) + ".960Z";
    return tim3;
  }
}

//Get localeDateTime, - 1 hour to have a proper hour
struct tm* getLocalDateTime() {
  time_t currentTime;
  struct tm *localTime;
  time( &currentTime );
  currentTime -= 2*30*60;
  localTime= localtime(&currentTime);

  return localTime;
}
