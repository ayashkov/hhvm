#!groovy

def target = env.BRANCH_NAME.equals('master') ||
    env.BRANCH_NAME.startsWith('release/') ? 'install' : 'verify'

// Test pull request

pipeline {
    agent {
        kubernetes {
            inheritFrom 'maven'
            defaultContainer 'maven'
        }
    }

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }

    stages {
        stage('build') {
            steps {
                sh "mvn clean ${target}"
            }
        }
    }
}
