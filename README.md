# jenkins-ci-sample-app

A simple **DevOps Build Info App** used in my Jenkins CI/CD portfolio project.

This Maven application was customized from the official Jenkins tutorial project
[simple-java-maven-app](https://github.com/jenkins-docs/simple-java-maven-app).
It demonstrates build, test, package, and automated delivery through a Jenkins pipeline.

The root **`Jenkinsfile`** defines the full CI/CD flow: checkout, build/test, smoke test, SonarQube analysis, artifact archive, and Nexus upload.

## What this app does

- Prints application name, status, environment, Java version, and build tool
- Reads the environment from the `APP_ENV` variable (defaults to `local`)
- Exposes build information for unit tests and CI reporting
- Produces a runnable JAR with Maven

## Example output

```
Application: Jenkins CI/CD Sample App
Status: Running pipelines successfully
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

## Jenkins pipeline (root Jenkinsfile)

| Stage | Action |
|---|---|
| Checkout | Clone from GitHub |
| Build and Test | `mvn clean package` |
| Run App Smoke Test | Run the JAR and print app output to the console |
| SonarQube Analysis | `sonar-scanner` with main and test sources |
| Archive Artifact | Store `target/*.jar` in Jenkins |
| Upload to Nexus | `mvn deploy:deploy-file` to snapshot repository |

Jenkins credential **IDs** (`sonarqube-token`, `nexus-admin-creds`) are referenced in the pipeline; secret values live only in Jenkins. The `Jenkinsfile` includes **lab VPC URLs** for Nexus — replace them when deploying to another environment.

## Project structure

```
jenkins-ci-sample-app/
├── Jenkinsfile
├── pom.xml
├── src/
│   ├── main/java/com/mycompany/app/App.java
│   └── test/java/com/mycompany/app/AppTest.java
└── jenkins/
    ├── Jenkinsfile          (legacy tutorial — not used by Jenkins jobs)
    └── scripts/deliver.sh
```

## Portfolio documentation

The full infrastructure setup report (AWS, Jenkins, SonarQube, Nexus) is maintained in a separate portfolio repository.
