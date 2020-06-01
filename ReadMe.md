# Actus demo web app
This demo will show you a simple application of the actus-core library to evaluate demo contracts.

## Getting started
These instructions will get you a copy of the demo up and running on your local machine for testing purposes.

## Requirements

* java: 8(+)

* mongodb: 5(*)

* Node

* npm

* maven: 3(+)

* gradle: 11(+)

* actus-core 1.0 (+)

  

If you need additional help installing these requirements, you find a step by instruction at the bottom of the page.

## Build the actus-core library

* Request an auth-token for access to the actus-core library through [Actusfrf.org](https://www.actusfrf.org/developers).
* Build actus-core dependency and add to your local maven repository

### Linux:
```sh
# actus-core/
mvn clean install -Dmaven.test.failure.ignore=true
```
### Windows:
```sh
# actus-core/
mvn clean install "-Dmaven.test.failure.ignore=true"
```

## Set up local environment

First, clone the actus-webapp repository than continue with the steps for your os.

### Linux:

Start services and set up the database for running the app locally by running the start script in your terminal (needs sudo privileges)

```sh
# actus-webapp/
sudo sh ./scripts/start.sh
```

### Windows:
* Install MongoDB as a service.
* Add the MongoDB bin folder to the PATH environment variable.
* On installation nodejs will add itself to the PATH environment variable. If npm can't be run in PowerShell or CMD add it to the PATH variable.

Start services and set up the database for running the app locally by running the start script in your terminal

```sh
# actus-webapp/
.\scripts\start.bat
```

## Build and run the app

Build and run the app through the following commands in your terminal

### Linux:
```sh
# actus-webapp/
chmod +x gradlew
./gradlew build
./gradlew bootRun
```

### Windows:
```sh
# actus-webapp/
.\gradlew.bat build
.\gradlew.bat bootRun
```

Yey, you made it to the first level - the backend to the ACTUS App is now running!


## Test REST endpoints

In a fresh terminal execute the following commands (Linux and Windows)

```sh
# Fetch demo data for PAM contract
curl -i -H "Accept: application/json" localhost:8080/demos/meta/PAM
```


### Build and run the frontend

Welcome to level 2! Now you are going to build and run the frontend to the ACTUS App. Note that the instructions in this step work for both Linux and Windows.

First, navigate to the frontend root folder

```sh
# /actus-webapp
cd ./frontend
```

Then, install the NPM packages required to build and run the frontend. This process can take a while.

```sh
# /frontend
npm install
```

Finally, build and start the frontend

```sh
# /frontend
npm run build
npm start
```

Now, open your browser and the app through url <a href="http://localhost:3000">http://localhost:3000</a>.

## Installing requirements: Step by step

### Install Java

#### On Linux:
```sh
sudo apt update
sudo apt install default-jre
sudo apt install default-jdk
```
Additionally, add the JAVA_HOME path variable to /etc/environment.

#### On Mac:
* Download the Java SE Development Kit: https://www.oracle.com/java/technologies/javase-jdk14-downloads.html
* Double click the downloaded file and follow the instructions

### Install mongodb

#### On Linux:
```sh
wget -qO - https://www.mongodb.org/static/pgp/server-4.2.asc | sudo apt-key add -
```

### Install maven

#### On Linux:

#### On Mac:
* Go to page: https://maven.apache.org/download.cgi

* Download Binary tar.gz archive

* Extract it: 

   tar -xzf apache-maven-3.6.3