pipeline {
    agent any

    environment {
        // Setup Ruby to PATH
        RUBY_HOME = "/usr/local/opt/ruby"
        PATH = "$RUBY_HOME/bin:$PATH"
        LANG = "en_US.UTF-8"
    }

    stages{
        stage('Setup') {
            steps {
                script {
                    sh 'gem install bundler'
                    sh 'bundle install'
                    // Copy node env file to export environment variables
                    withCredentials([file(credentialsId: 'env-default', variable: 'env')]) {
                        sh 'rm -f .env.default'
                        sh 'cp $env .env.default'
                    }
                }
            }
        }
        stage('Deploy Snapshot') {
            steps {
                script {
                    sh 'bundle exec fastlane publish_snapshot'
                }
            }
        }
        stage('Deploy Release') {
            when { branch pattern: "release(-v.+)?", comparator: "REGEXP"}
            steps {
                script {
                    sh 'bundle exec fastlane publish_release'
                }
            }
        }
    }
    post {
        cleanup {
            cleanWs()
        }
    }
}

