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
                mvn package -Dmaven.test.skip
                echo "** end notificaciones compilation"                            '''
            }
        }
        stage ('Build') {
            steps {
                 sh '''
                     IMAGE_NAME="richyortega/proyecto-diplomado"
                     NEW_VERSION=$(git describe)
                     sudo docker build -t ${IMAGE_NAME}:${NEW_VERSION} .
                     echo "IMAGE_NAME=$IMAGE_NAME" > env.properties
                     echo "NEW_VERSION=$NEW_VERSION" >> env.properties
                 '''
            }
        }
        stage ('Publish') {
            steps {
                 withEnv(readFile('env.properties').split('\n') as List) {
                     withCredentials([usernamePassword(credentialsId: 'dockerhub-cristian-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
                         sh '''
                             echo $DOCKER_PASSWORD | sudo docker login -u $DOCKER_USER --password-stdin d>
                             sudo docker push ${IMAGE_NAME}:${NEW_VERSION}
                             sudo docker image rm ${IMAGE_NAME}:${NEW_VERSION}
                             sudo docker logout
                         '''
                     }

                 }
            }
        }
    }  
}
