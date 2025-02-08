pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'JDK17'
    }
    stages {
        stage ('Clone') {
            steps {
                sh '''
                echo "PATH = ${PATH}"
                echo "JAVA_HOME = ${JAVA_HOME}"
                echo "M2_HOME = ${M2_HOME}"
                java -version
                echo "** starting notificaciones compilation"
                mvn package -Dmaven.skip.test
                echo "** end notificaciones compilation"                            '''
            }
        }
        stage ('Build') {
            steps {
                 sh '''
                     IMAGE_NAME="richyortega/proyecto-diplomado"
                     NEW_VERSION=$(git describe)
                     sudo docker build -t ${IMAGE_NAME}:${NEW_VERSION}
                 '''
            }
        }
        stage ('Publish') {
            steps {
                 withCredentials([usernamePassword(credentialsId: 'openshift-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
                    sh '''
                      echo $DOCKER_PASSWORD | sudo docker login -u $DOCKER_USER --password-stdin docker.io
                      sudo docker push ${IMAGE_NAME}:${NEW_VERSION}
                    '''
                }
            }
        }
    }  
}
