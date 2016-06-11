Boggle
====
Depends on:
* XMLTransport (github.com/intere/XMLTransport)

Uses Maven to build (tests are currently broken and need fixing).

```bash
cd ..
git clone https://github.com/intere/XMLTransportLayer.git
cd XMLTranslportLayer
mvn clean install	# installs XML Transport to your local git repo
cd ../boggle
mvn clean install -DskipTests=true # installs the artifact for you, but doesn't execute the tests
```

### Running the Server
```bash
./server.sh
mvn -P server exec:java -Dexec.args="--help"  # HELP!
mvn -P server exec:java  # Default Options (port 4445)
```

### Running the Client
```bash
mvn -P client exec:java -Dexec.args="--help"   # HELP!
mvn -P client exec:java -Dexec.args="localhost 4445 Eric"  # Connect to a server on localhost, port 445, with the username: Eric
```

Entry Points:
* Client: com.erici.boggle.client.Main
* Server: com.erici.boggle.server.BoggleServer


## Screenshots

![Screenshot 1](https://raw.githubusercontent.com/intere/boggle/master/screenshots/Screenshot1.png)
![Screenshot 2](https://raw.githubusercontent.com/intere/boggle/master/screenshots/Screenshot2.png)
![Screenshot 3](https://raw.githubusercontent.com/intere/boggle/master/screenshots/Screenshot3.png)
