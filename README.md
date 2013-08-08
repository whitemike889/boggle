Boggle
====
Depends on:
* XMLTransport (github.com/intere/XMLTransport)

Uses Maven to build (tests are currently broken and need fixing).


    [XMLTransport] $ mvn clean install	# installs XML Transport to your local git repo
    [XMLTransport] $ cd ../boggle
    [boggle] $ mvn clean install -DskipTests=true # installs the artifact for you, but doesn't execute the tests


Entry Points:

Client: ./src/main/java/com/erici/boggle/client/Main.java
Server: ./src/main/java/com/erici/boggle/Main.java
