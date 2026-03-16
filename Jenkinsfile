//Testing 
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
              sh 'mvn clean package -DskipTests -Dmaven.compiler.release=21'
            }
        }
        stage('Copy to Test Directory') {
            steps {
                sh 'mkdir -p /opt/wordle/test'
                sh 'cp -r * /opt/wordle/test/'
            }
        }
        stage('Verify Deployment Directory') {
            steps {
                sh 'ls -la /opt/wordle/test'
            }
        }
        stage('Run JAR') {
            steps {
                sh 'pkill -f "FDUWordle" || true'
                sh 'nohup java -jar /opt/wordle/test/target/*.jar --server.port=8081 > /opt/wordle/test/app.log 2>&1 &'
            }
        }
    }
}
