pipeline {
    agent any
    
    stages{
        stage ("main"){
            when{
                branch "main"
            }
            steps{
                git 'https://github.com/Esthersurya1223/Train-Ticket-Reservation-System.git'
            }
        }
        stage ("build"){
            when{
                branch "devlop"
            }
            steps{
                sh 'mvn clean package'
            }
        }
    }
    
}
