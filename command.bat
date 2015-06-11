rmdir data /s /q&& rmdir target /s /q && mvn package && java -jar lib/webapp-runner.jar --expand-war target/*.war
