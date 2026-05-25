# jenkins-ci-sample-app

A simple **DevOps Build Info App** used in my Jenkins CI/CD portfolio project.

This Maven application was customized from the official Jenkins tutorial project
[simple-java-maven-app](https://github.com/jenkins-docs/simple-java-maven-app).
It demonstrates build, test, and package steps that can be automated in a Jenkins pipeline.

The root **`Jenkinsfile`** currently runs checkout, build/test, and artifact archiving. **SonarQube** quality analysis will be added in a later step.

## What this app does

- Prints application name, status, environment, Java version, and build tool
- Reads the environment from the `APP_ENV` variable (defaults to `local`)
- Exposes build information for unit tests and CI reporting
- Produces a runnable JAR with Maven

## Example output

```
Application: Jenkins CI Sample App
Status: Running successfully
Environment: local
Java Version: 21.0.6
Build Tool: Maven
```

## Requirements

- Java 21 or later
- Maven 3.9.9 or later

## Build and test locally

```bash
mvn clean test
mvn clean package
```

Run the packaged application:

```bash
java -jar target/jenkins-ci-sample-app-1.0-SNAPSHOT.jar
```

Run with a custom environment:

```bash
APP_ENV=jenkins java -jar target/jenkins-ci-sample-app-1.0-SNAPSHOT.jar
```

## Project structure

```
jenkins-ci-sample-app/
├── pom.xml
├── src/
│   ├── main/java/com/mycompany/app/App.java
│   └── test/java/com/mycompany/app/AppTest.java
└── jenkins/
    ├── Jenkinsfile
    └── scripts/deliver.sh
```
