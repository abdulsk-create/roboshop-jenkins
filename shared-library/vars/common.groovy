def compile() {
  if (env.codeType == "python" || env.codeType == "static") {
    return "return don't need compilation!"
  }
  stage('Compile Code') {
    if (env.codeType == "maven") {
      sh '/home/centos/maven/bin/mvn package'           // it will download all the dependencies
    }

    if (env.codeType == "nodejs") {
      sh 'npm install'
    }
  }
}

def test() {
  stage('Test Cases') {
    if (env.codeType == "maven") {
      //sh '/home/centos/maven/bin/mvn test'           // it will download all the dependencies
      print 'OKAY'
    }

    if (env.codeType == "nodejs") {
      //sh 'npm test'
      print 'OKAY'
    }
    if (env.codeType == "python") {
      //sh 'python3.6 -m unittest'
      print 'OKAY'
    }
  }
}

def codeQuality() {
  stage('Code Quality') {
    env.sonaruser = sh (script: 'aws ssm get-parameter --name "sonarqube.user" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    env.sonarpass = sh (script: 'aws ssm get-parameter --name "sonarqube.pass" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: sonarpass]]]) {
      if(env.codeType == "maven") {
        //sh 'sonar-scanner -Dsonar.host.url=http://172.31.83.91:9000 -Dsonar.login=${sonaruser} -Dsonar.password=${sonarpass} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
        print 'OKAY'

      } else {
         //sh 'sonar-scanner -Dsonar.host.url=http://172.31.83.91:9000 -Dsonar.login=${sonaruser} -Dsonar.password=${sonarpass} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true'
        print 'OKAY'

      }
    }
  }
}

def codeSecurity() {
  stage('Code Security') {
    print 'Code Security'
    // in code security we will generally use SAST (Static Application Security Testing) and SCA (Software Composition Analysis) checks.
    // For code security in our org we are using check marx SAST & check marx SCA
  }
}

def release() {
  stage ('Release') {
    env.nexususer = sh (script: 'aws ssm get-parameter --name "nexus.username" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    env.nexuspass = sh (script: 'aws ssm get-parameter --name "nexus.password" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: nexuspass]]]) {
      if(codeType == "nodejs") {
        sh 'zip -r ${component}-${TAG_NAME}.zip server.js node_modules'
      }

      sh 'curl -v -u ${nexususer}:${nexuspass} --upload-file ${component}-${TAG_NAME}.zip http://172.31.30.93:8081/repository/${component}/${component}-${TAG_NAME}.zip'
    }
  }
}

