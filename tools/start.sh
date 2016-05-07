DOMAINE=/apps/gdomoticz
nohup java -jar $DOMAINE/bin/gdomoticz.jar --spring.config.location=$DOMAINE/bin/application.properties --logging.config=$DOMAINE/bin/logback.xml &
echo $! > $DOMAINE/gdomoticz.pid
