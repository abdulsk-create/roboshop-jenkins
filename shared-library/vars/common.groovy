def compile() {
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