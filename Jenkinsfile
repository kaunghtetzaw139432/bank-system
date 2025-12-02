pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building project...'
                bat 'mvnw clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvnw test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying (simulated)...'
            }
        }
    }
}
