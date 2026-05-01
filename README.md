# Dungeon Escape

To build and run the game use "mvn javafx:run" in the terminal.
To test the game use "mvn -q test" in the terminal.
To build JAR file use: mvn clean package "-DskipTests" "-Dmaven.javadoc.skip=true" (if you want to skip the tests and javadoc generation, otherwise you can take those 2 out) and then to open the JAR use: java -jar target/dungeon-escape-1.0.0.jar
To generate JavaDocs use: mvn javadoc:javadoc. To open JavaDocs use :Invoke-Item target\apidocs\index.html
