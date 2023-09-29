def call() {

    node('workstation') {

      sh 'find .'
      stage ('Compile Code') {
        common.compile()
      }

      stage ('Test') {
        print 'Hello'
      }

      stage ('Code Quality') {
        print 'Hello'
      }

      stage ('Code Security') {
        print 'Hello'
      }

      stage ('Release') {
        print 'Hello'
      }
    }
}



