pipeline {
    agent any
    stages{
        stage('Deploy') {
            steps {
                script {
                    sh './gradlew publish'
                }
            }
        }
    post {
        cleanup {
            cleanWs()
        }
    }
}

