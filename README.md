bitalino-server
==========

### Pre-requisites ###

1. JDK 7
2. Maven 3.1.0+

### Build ###

```
mvn clean package
```

### Run ###

```
java -jar server/target/server-<version>.jar
```

Optionally, if you want to bind a different port, in this case TCP 8765:

```
java -jar server/target/server-<version>.jar --port 8765
```
