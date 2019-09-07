Rabobank statement processor
===================
Rabobank statement processor is a spring boot based web application. It uses thymeleaf template engine to display html files. The first page shows
option to upload MT940 file (either CSV or XML file ) from the local machine. On clicking the submit button the file is validated at the 
backend and status is displayed in the new page 


## Setup ##

This project built using Intellij IDEA ide and Maven dependency management. The project can be directly imported in IJ ide or can be run from the 
command line using maven command. The application uses inbuilt spring boot tomcat server can be accessed using the url http://localhost:8080







## Run using Intellij IDEA ##

Import the project in Intellij IDEA ide. Goto Source directory -> Right click SpringBootWebApplication class file and run it.


## Run using Maven commandline ##

Goto project directory in the command promt and run the following commands

__mvn clean package__

__java -jar target/Rabobank-Stmt-Process-1.0.jar__
