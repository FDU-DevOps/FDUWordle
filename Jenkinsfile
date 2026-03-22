pipeline {
    agent any
    stages {
        stage('Verify Environment') {
            steps {
                echo "Running retrieval for push (for test)"
                sh 'pwd'
                sh 'whoami'
            }
        }
        stage('Verify Retrieved Files') {
            steps {
                sh 'ls -la'
            }
        }
        stage('Build JAR') {
            steps {
                withMaven(maven: 'Maven') {   
                  sh 'mvn clean package -Dmaven.compiler.release=21'
                }
            }
        }
        stage('Copy to Test Directory') {
            steps {
                sh 'mkdir -p /opt/wordle/test'
                sh 'rm -rf /opt/wordle/test/*'
                sh 'cp target/*.jar /opt/wordle/test/'
            }
        }
        stage('Verify Deployment Directory') {
            steps {
                sh 'ls -la /opt/wordle/test'
            }
        }
        stage('Run JAR') {
            steps {   
                sh 'pkill -f "/opt/wordle/test/.*jar" || true'
                sh 'JENKINS_NODE_COOKIE=dontKillMe nohup java -jar /opt/wordle/test/*.jar --server.port=8081 > /opt/wordle/test/app.log 2>&1 &'
            } //https://stackoverflow.com/questions/37341545/unable-to-run-nohup-command-from-jenkins-as-a-background-process test
        }
    }
}
