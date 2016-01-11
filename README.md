# spring-cassandra-showcase-application
[![Build Status](https://travis-ci.org/smartcat-labs/spring-cassandra-showcase-application.svg?branch=master)](https://travis-ci.org/smartcat-labs/spring-cassandra-showcase-application)

Showcase application for Cassandra database usage with Spring framework and DataStax Java driver.

# General overview

This project is showcase of Cassandra usage on Spring Boot projects. Currently This project is showing how Spring Boot application should be setup to use Embedded Cassandra for testing on one side, and it is showing usage of our [Cassandra Migration Tool Java](https://github.com/smartcat-labs/cassandra-migration-tool-java) for DATA and SCHEMA migrations on the other side. [CassandraConfiguration](https://github.com/smartcat-labs/spring-cassandra-showcase-application/blob/master/api/src/main/java/io/smartcat/spring/cassandra/showcase/adapter/cassandra/CassandraConfiguration.java) holds all necessary configuration for cluster and session. It pulls values from [application.yml](https://github.com/smartcat-labs/spring-cassandra-showcase-application/blob/master/api/src/main/resources/application.yml). Configuration goes through couple of steps:

* Create keyspace if it does not exist
* Use keyspace
* Execute all SCHEMA migrations

Showcase is done in lightweight *DDD* fashion, with [onion architecture](http://alistair.cockburn.us/Hexagonal+architecture) in mind. *Adapter* package holds adpters to outside world (Cassandra configuration and implementation of Repository classes). *Domain* package holds domain logic, Value and Entity objects and Repository interface.

#SCHEMA migrations

Execution of SCHEMA migrations are part of connection to cluster. Right after Session is created and keyspace is selected all unexecuted SCHEMA migrations are executed. More details on *Cassandra Migration Tool* could be found on this [link](https://github.com/smartcat-labs/cassandra-migration-tool-java). Important part for this showcase is that SCHEMA migrations are part of server startup and they are blocking since Application would not start if mapped objects are different then SCHEMA for connected cluster.

#DATA migrations

Execution of DATA migrations is done after application is fully started. This kind of setup is meant for ETL like operations on data which can be applied async and this data will be in sync eventually. This can be seen in [Application](https://github.com/smartcat-labs/spring-cassandra-showcase-application/blob/master/api/src/main/java/io/smartcat/spring/cassandra/showcase/Application.java) class where migrateData is executed after application is fully started.

#Embedded Cassandra Setup

First step of adding Embedded Cassandra to project is adding listener, configuring Embedded Cassandra and connecting it alltogether. Second problem we faced is testing asyn execution of statements. These problems are tackled in [test Cassandra package](https://github.com/smartcat-labs/spring-cassandra-showcase-application/tree/master/api/src/test/java/io/smartcat/spring/cassandra/showcase/test/cassandra) and explained in details on our [Setting up Embedded Cassandra on Spring projects](https://www.smartcat.io/blog/setting-up-embedded-cassandra-on-spring-project/) blog post. 

In a nutshell Test Listener implementation is used to hook creating and truncating tables for testing needs. We overrode Session to fake async execution as sync, which enable testing without waiting on results to be propagated in datbase. 
