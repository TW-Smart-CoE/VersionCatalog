pipeline {
    agent any

    environment {
        // Setup Ruby to PATH
        RUBY_HOME = "/usr/local/opt/ruby"
        PATH = "$RUBY_HOME/bin:$PATH"
        LANG = "en_US.UTF-8"
    }
    parameters {
        string(name: 'PUBLISH_VERSION', defaultValue: '1.0.0', description: 'Publish version')
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
        stage('Deploy Dev') {
            steps {
                script {
                    sh "bundle exec fastlane publish publishVersion:${params.PUBLISH_VERSION}-SNAPSHOT"
                }
            }
        }
        stage('Deploy Release') {
            when { branch pattern: "release(-v.+)?", comparator: "REGEXP"}
            steps {
                script {
                    sh "bundle exec fastlane publish publishVersion:${params.PUBLISH_VERSION}"
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

