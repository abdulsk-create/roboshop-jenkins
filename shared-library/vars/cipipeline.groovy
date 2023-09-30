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


      if(env.TAG_NAME ==~ ".*") {
        common.compile()
        common.release()

      } else {
        if(env.BRANCH_NAME == "main") {
          common.compile()
          common.test()
          common.codeQuality()
          common.codeSecurity()
        } else {
          common.compile()
          common.test()
          common.codeQuality()
        }
      }
    }
}



