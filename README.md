## Calendar Integration Services - Java Spike

This is a java spike of the Calendar Integration Service making use of Spring Boot. The idea is to understand the EWS managed API for Java 
and see how the API works. There are two api's currently available. One is the ability to subscribe to **streaming notifications** and the 
other is to **fetch real time data** from the exchange server for a given period and a bunch of users.
 
## Build 

From the root of the project run this

```
./gradlew build
```

This will build the project and create a fat jar which can be then run using the command below

```
java -jar ./build/libs/cis-0.0.1-SNAPSHOT.jar
```

This should start the Spring Tomcat server on port 8080 and would expose 2 endpoints both supporting only POST

```
/users/subscribe 
/users/check_availability
```

The payload for each of the methods is below and is the json that needs to be passed to the POST request
 
## Subscribe To Streaming Notifications
 
```
{
   "users":[
      {
         "email":"phijo.joseph@testad.jifflenow.net",
         "uuid":"9eD_Hn4cXOu114iZilgE8g",
         "user_schedule_url":"http://<companyname>.jifflenow.com/portal/users/9eD_Hn4cXOu114iZilgE8g/schedule"
      },
      {
         "email":"shyamsundar.cs@testad.jifflenow.net",
         "uuid":"7HoARwdxl1010-e3sguwWw",
         "user_schedule_url":"<companyname>.jifflenow.com/portal/users/7HoARwdxl1010-e3sguwWw/schedule"
      }
   ],
   "jn_request_id":"wMuMRHjzrFodZSiw-gVaGw",
   "include_jf_meeting":"true",
   "callback":"something"
}
```

This will result in making a call to the exchange server and subscribe to streaming notifications. When the calendar
FreeBusy changes, for the user being monitored, it currently logs to the console.

## Fetch Calendar Availability of one or more users

```
{
   "users":[
      
         "phijo.joseph@testad.jifflenow.net",
         "shyamsundar.cs@testad.jifflenow.net"
   ],
   "jn_request_id":"wMuMRHjzrFodZSiw-gVaGw",
   "include_jf_meeting":"true",
   "start_time":"2015-10-27T04:00:00.000Z",
   "end_time":"2015-10-27T05:00:00.000Z"
}
```

This will result in making a call to the exchange server and request for appointments of the users between the start
time and the end time. The threadpool sizes can be managed using the application properties. If the users have any appointments/meetings during the time provided, the response returns as
blocked else available. Response format should be below.

```
[
    {
        "email":"phijo.joseph@testad.jifflenow.net",
        "status":"Available"
    }
]
```

This call makes use of async semantics of Spring and hence fires parallel requests to the exchange server and then
when the results comeback, they are collated and returned.