# PHP-JDBC Bridge

The PHP-JDBC bridge is a service and library for allowing a PHP application
to interface with a database for which only a JDBC driver exists. This is a
fork of [PJBS](http://sourceforge.net/projects/pjbs/).

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
java -cp lib/ Server <JDBC driver entry point> <port>
```

Example:

```sh
java -cp lib/ Server dharma.jdbc.DharmaDriver 4444
```

where the lib directory contains the php-jdbc jar and your JDBC driver jar.

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

print_r($db->fetch_array($cursor));

$db->free_result($cursor);
```
