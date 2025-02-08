pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'JDK17'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                echo "PATH = ${PATH}"
                echo "JAVA_HOME = ${JAVA_HOME}"
                echo "M2_HOME = ${M2_HOME}"
                java -version
                echo "** starting notificaciones compilation"
                mvn clean package
                echo "** end notificaciones compilation"                            '''
            }
        }
    }  
}
