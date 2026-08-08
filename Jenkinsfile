pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK25'
    }

    environment {
        REQRES_PUBLIC_KEY = credentials('REQRES_PUBLIC_KEY')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('REST Assured Tests') {
            steps {
                bat 'mvn -B test -Dtest=ProductsApiTest,ProductsApiNegativeTest -DREQRES_PUBLIC_KEY=%REQRES_PUBLIC_KEY%'
            }
        }

        stage('Karate Tests') {
            steps {
                bat 'mvn -B test -Dtest=KarateTestRunner -DREQRES_PUBLIC_KEY=%REQRES_PUBLIC_KEY%'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/karate-reports/**/*', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'All tests passed!'
        }
        failure {
            echo 'Tests failed — check the report.'
        }
    }
}