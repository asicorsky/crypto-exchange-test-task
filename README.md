# crypto-exchange-test-task

## Require:
1. Java 14+
2. Maven 3

## Install:
Open command line, go to project root and execute "mvn clean install"

## Execute:
Go to the target directory in command line which created after install and execute "java -Dapplication.credentials.api-key=$BSDEX_API_KEY -Dapplication.credentials.api-secret=$BSDEX_API_SECRET -jar crypto-exchange.jar".

Also you can start it from IDE (ApplicationEntryPoint main class).

## How it works:
After application startup the system connected to required websocket channel and start to receive messages.

Each message spawn new thread and do necessary work (storing to collections, check current prices, changing/modifying objects, etc.). 

After processing system creates own websocket message to client (browser in current case) with object which should be removed, added or changed. Only one object. 
Javascript handle the object and put to needed place in table. 

All logic based on NavigableMap (removing elements with low/high price or detection the place which should pe processed).
Also I did not use any iteration for object detection and it should work much faster than "iterate, find, operate".

First task (with REST connection) also proceeds on project startup and show loaded objects in logs.

## Frameworks and libraries
1. Lombok because life is too short
2. Spring Web
3. Spring Thymeleaf as template engine
4. Spring WebSocket as main library for the application which work with websockets
5. WebFlux as REST client
6. Apache utilities (for HMAC and IO)
7. Bootstrap and jQuery for the GUI

## NOTES
Sometimes WS connection cannot be established because of external server problems. In this case need to restart application and check logs ("Application successfully subscribed to required websocket channel..." line should appear)
