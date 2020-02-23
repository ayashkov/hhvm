#!groovy

def target = env.BRANCH_NAME.equals('master') ||
    env.BRANCH_NAME.startsWith('release/') ? 'deploy' : 'verify'

pipeline {
    agent {
        kubernetes {
            defaultContainer 'maven'
            yaml """
apiVersion: v1
kind: Pod
spec:
  volumes:
  - name: maven-repo
    persistentVolumeClaim:
      claimName: maven-repo
  containers:
  - name: maven
    image: maven:3-jdk-13-alpine
    volumeMounts:
    - mountPath: "/root/.m2/repository"
      name: maven-repo
    command:
    - cat
    tty: true
"""
        }
    }

    stages {
        stage('build') {
            steps {
                sh "mvn clean ${target}"
            }
        }
    }
}
