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
                        curl -f -sS -u "\${NEXUS_USER}:\${NEXUS_PASS}" \\
                          -X POST "${NEXUS_URL}/service/rest/v1/components?repository=${NEXUS_REPO}" \\
                          -F maven2.groupId=com.mycompany.app \\
                          -F maven2.artifactId=jenkins-ci-sample-app \\
                          -F maven2.version=1.0-SNAPSHOT \\
                          -F maven2.generate-pom=true \\
                          -F maven2.packaging=jar \\
                          -F maven2.asset1=@\${JAR_FILE} \\
                          -F maven2.asset1.extension=jar
                        echo "Uploaded \${JAR_FILE} to Nexus repository ${NEXUS_REPO}"
                    """
                }
            }
        }
    }
}
