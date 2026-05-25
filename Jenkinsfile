pipeline {
    agent any

    environment {
        NEXUS_URL = 'http://172.31.27.93:8081'
        NEXUS_REPO = 'jenkins-maven-snapshots'
    }

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run App Smoke Test') {
            steps {
                sh 'java -jar $(ls target/*.jar | head -1)'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube-Server') {
                    script {
                        def scannerHome = tool 'SonarQube-Scanner'
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                              -Dsonar.projectKey=jenkins-ci-sample-app \
                              -Dsonar.sources=src \
                              -Dsonar.java.binaries=target/classes
                        """
                    }
                }
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar'
            }
        }

        stage('Upload to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-admin-creds', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh """
                        set -eu
                        set +x
                        JAR_FILE=\$(ls target/*.jar | head -1)
                        cat > nexus-settings.xml <<EOF
<settings>
  <servers>
    <server>
      <id>nexus-snapshots</id>
      <username>\${NEXUS_USER}</username>
      <password>\${NEXUS_PASS}</password>
    </server>
  </servers>
</settings>
EOF
                        mvn deploy:deploy-file \\
                          -DrepositoryId=nexus-snapshots \\
                          -Durl=${NEXUS_URL}/repository/${NEXUS_REPO}/ \\
                          -Dfile=\${JAR_FILE} \\
                          -DgroupId=com.mycompany.app \\
                          -DartifactId=jenkins-ci-sample-app \\
                          -Dversion=1.0-SNAPSHOT \\
                          -Dpackaging=jar \\
                          -DgeneratePom=true \\
                          -s nexus-settings.xml
                        rm -f nexus-settings.xml
                        echo "Uploaded \${JAR_FILE} to Nexus repository ${NEXUS_REPO}"
                    """
                }
            }
        }
    }
}
