#!groovy

def target = env.BRANCH_NAME.equals('master') ||
    env.BRANCH_NAME.startsWith('release/') ? 'install' : 'verify'

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
