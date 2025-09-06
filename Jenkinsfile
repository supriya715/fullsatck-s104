pipeline {
    agent any

    tools {
        jdk 'JDK_HOME'
        maven 'MAVEN_HOME'
    }

    environment {
        TOMCAT_URL = 'http://43.205.242.252:9090/manager/text'
        TOMCAT_USER = 'admin'
        TOMCAT_PASS = 'admin'

        MAIN_REPO = 'https://github.com/supriya715/fullsatck-s104.git'

        BACKEND_DIR = 'crud_back'
        FRONTEND_DIR = 'crud_front'

        BACKEND_WAR = 'crud_back/target/springapp1.war'
        FRONTEND_WAR = 'crud_front/frontapp1.war'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: "${env.MAIN_REPO}"
            }
        }

        stage('Build React Frontend') {
            steps {
                dir("${env.FRONTEND_DIR}") {
                    script {
                        def nodeHome = tool name: 'NODE_HOME', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
                        env.PATH = "${nodeHome}/bin:${env.PATH}"
                    }
                    // Clean install to avoid permission issues
                    sh 'rm -rf node_modules'
                    sh 'npm install'
                    sh 'chmod +x node_modules/.bin/vite'  // Fix permission issue
                    sh 'npm run build'
                }
            }
        }

        stage('Package React as WAR') {
            steps {
                script {
                    def warDir = "${env.FRONTEND_DIR}/war_content"
                    sh "rm -rf ${warDir}"
                    sh "mkdir -p ${warDir}/META-INF ${warDir}/WEB-INF"
                    sh "cp -r ${env.FRONTEND_DIR}/dist/* ${warDir}/"
                    sh "jar -cvf ${env.FRONTEND_WAR} -C ${warDir} ."
                }
            }
        }

        stage('Build Spring Boot App') {
            steps {
                dir("${env.BACKEND_DIR}") {
                    sh 'mvn clean package'
                    sh 'mv target/*.war target/springapp1.war'
                }
            }
        }

        stage('Deploy Backend to Tomcat (/springapp1)') {
            steps {
                sh """
                    curl -u ${TOMCAT_USER}:${TOMCAT_PASS} \
                        --upload-file ${BACKEND_WAR} \
                        "${TOMCAT_URL}/deploy?path=/springapp1&update=true"
                """
            }
        }

        stage('Deploy Frontend to Tomcat (/frontapp1)') {
            steps {
                sh """
                    curl -u ${TOMCAT_USER}:${TOMCAT_PASS} \
                        --upload-file ${FRONTEND_WAR} \
                        "${TOMCAT_URL}/deploy?path=/frontapp1&update=true"
                """
            }
        }
    }

    post {
        success {
            echo "✅ Backend deployed: http://43.205.242.252:9090/springapp1"
            echo "✅ Frontend deployed: http://43.205.242.252:9090/frontapp1"
        }
        failure {
            echo "❌ Build or deployment failed"
        }
    }
}
