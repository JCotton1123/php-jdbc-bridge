# PHP-JDBC Bridge

The PHP-JDBC bridge is a service and library for allowing a PHP application
to interface with a database for which only a JDBC driver exists. This is a
fork of [PJBS](http://sourceforge.net/projects/pjbs/).

The java component runs as a service which accepts socket requests from 
the PHP component allowing the transfer of request and response between PHP 
and the JDBC database. 

## Requirements

* Java 1.6+
* PHP 5.3+
* A JDBC driver

## Build (Java Service)

To build the PHP-JDBC bridge jar:

```sh
cd java
./build.sh
```

To build a PHP-JDBC RPM:

```sh
cd java
./build.sh
./build-rpm.sh
```

## Usage

### Java Service

To run the service:

```sh 
java -cp 'lib/pjbridge.jar:lib/commons-daemon-1.0.15.jar:lib/<JDBC driver>.jar Server <JDBC driver entry point> <port>
```

Example:

```sh
cd java
java -cp 'lib/pjbridge.jar:lib/commons-daemon-1.0.15.jar:lib/dharma.jar' Server dharma.jdbc.DharmaDriver 4444
```

where the lib directory contains the php-jdbc jar, the commons-daemon jar and your JDBC driver jar.

### PHP

Example:

```php
<?php
require "PJBridge.php";

$dbHost = "server";
$dbName = "";
$dbPort = "1990";
$dbUser = "dharma";
$dbPass = "";

$connStr = "jdbc:dharma:T:${dbHost}:${dbName}:${dbPort}";

$db = new PJBridge();
$result = $db->connect($connStr, $dbUser, $dbPass);
if(!$result){
    die("Failed to connect");
}

$cursor = $db->exec("SELECT * FROM \"AR Customer File\"");

while($row = $db->fetch_array($cursor)){
    print_r($row);
}

$db->free_result($cursor);
```
