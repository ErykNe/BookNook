# BookNook

BookNook is a web application that allows users to search for books and accessories, add them to a cart, and place orders. This application is built using Java.

## Prerequisites

To run the BookNook application, you need to have the following installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or higher)
- [Apache Maven](https://maven.apache.org/download.cgi) (version 3.9.9)
- [Apache Tomcat](https://tomcat.apache.org/download-90.cgi) (version 8.5.96)
- Make sure you have java and maven in environment variables

## Running the Application

Follow these steps to run the BookNook application:

### Step 1: UnZip Project

- Unzip .zip file BookNook.zip to Desktop
- You should to have folder with the project now in folder BookNook-main on the Desktop.
- open cmd and navigate to BookNook-main

### Step 2: Run the Application in System Terminal

- In cmd type: ```mvn clean package``` and wait for it to build
- then type: ```copy target\BookNook.war C:\path\to\tomcat\webapps\```
- next type: ```cd C:\path\to\tomcat\bin```
- type: ```startup.bat```
- Open your browser and go to http://localhost:8080/BookNook

## Login Details
