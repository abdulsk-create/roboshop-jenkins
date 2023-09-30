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
    print 'Test'
  }
}

def codeQuality() {
  stage('Code Quality') {
    print 'Code Quality'
  }
}

def codeSecurity() {
  stage('Code Security') {
    print 'Code Security'
  }
}

def release() {
  stage ('Release') {
    print 'Release'
  }
}