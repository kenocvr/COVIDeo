# COVIDeo

A Video Conferencing application using Jitsi.
COVIDeo = COVID + Video

##### Backend (Spring Boot). Port: 8080

##### Frontend (Angular). Port: 9000

## Installation

    npm install

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

## Run COVIDeo

    ./mvnw
    npm start

## Host your own video conference server

##### (HTTP) Port: 8000 or (HTTPS) Port: 8443

    cd docker-jitsi-meet
    docker-compose up -d

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.
