def call() {

  node('workstation') {

    sh "find . | sed -e '1d' |xargs rm -rf"     //cleaning up the content
    if(env.TAG_NAME ==~ ".*") {
      env.branch_name = "refs/tags/${env.TAG_NAME}"
    } else {
      env.branch_name = "${env.BRANCH_NAME}"
    }
      checkout scmGit(
          branches: [[name: branch_name]],
          userRemoteConfigs: [[url: "https://github.com/abdulsk-create/${component}"]]
      )

      stage ('Compile Code') {
        common.compile()
      }
      if(env.TAG_NAME == null) {
        stage ('Test') {
          print 'Hello'
        }

        stage ('Code Quality') {
          print 'Hello'
        }
      }



      stage ('Code Security') {
        print 'Hello'
      }

      stage ('Release') {
        print 'Hello'
      }
    }
}



