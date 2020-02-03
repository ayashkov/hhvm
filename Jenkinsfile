pipeline {
  agent {
    kubernetes {
      defaultContainer 'maven'
      yaml """
apiVersion: v1
kind: Pod
metadata:
  namespace: jenkins
spec:
  containers:
  - name: maven
    image: maven:3-jdk-13-alpine
    command:
    - cat
    tty: true
"""
    }
  }
  stages {
    stage('maven') {
      steps {
        sh 'mvn clean package'
      }
    }
  }
}
