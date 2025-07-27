pipeline {
    agent any
    environment {
        // Use the job name and build number to create a unique image tag
        IMAGE_NAME = "my-spring-boot-app"
        IMAGE_TAG  = "build-${env.BUILD_NUMBER}"
        APP_NAME   = "spring-boot-app-container"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from the repository
                checkout scm
            }
        }

        stage('Build & Test in Subdirectory') {
            steps {
                // Use the dir step to change into the 'demo' directory
                dir('demo') {
                    // The 'mvn' command is now available due to the 'tools' directive.
                    // This command runs inside the 'demo' directory, where pom.xml is located.
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image in Subdirectory') {
            steps {
                // The Dockerfile is also in the 'demo' directory
                dir('demo') {
                    script {
                        // The build context ('.') is now the 'demo' directory
                        docker.build("${IMAGE_NAME}:${IMAGE_TAG}", ".")
                    }
                }
            }
        }

        stage('Deploy Application') {
            steps {
                // This stage does not need to change directory, as it only uses the Docker image name
                script {
                    // Stop and remove the old container to prevent conflicts
                    sh "docker stop ${APP_NAME} || true"
                    sh "docker rm ${APP_NAME} || true"

                    // Run the new Docker image as a container
                    sh "docker run -d --name ${APP_NAME} -p 8081:8080 ${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
    }

}