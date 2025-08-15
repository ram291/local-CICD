pipeline {
    agent any

    tools {
        // This name MUST match the name you configured in
        // Manage Jenkins -> Tools
        maven 'Maven3'
    }

    environment {
        // Use the job name and build number to create a unique image tag
        IMAGE_NAME = "my-spring-boot-app"
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

            }
        }
    }

}

