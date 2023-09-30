def compile() {
  stage('Compile Code') {
    if (env.codeType == "maven") {
      sh '/home/centos/maven/bin/mvn package'           // it will download all the dependencies
    }

    if (env.codeType == "nodejs") {
      print 'NodeJS'
    }

    if (env.codeType == "python") {
      print 'Python'
    }

    if (env.codeType == "static") {
      print 'Static'
    }
  }


}

def Test() {
  stage('Test Cases') {
    print 'Test'
  }
}

def CodeQuality() {
  stage('Code Quality') {
    print 'Code Quality'
  }
}

def CodeSecurity() {
  stage('Code Security') {
    print 'Code Security'
  }
}

def Release() {
  stage ('Release') {
    print 'Release'
  }
}