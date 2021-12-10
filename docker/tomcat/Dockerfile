FROM tomcat:8

RUN cp -r /usr/local/tomcat/webapps.dist/* /usr/local/tomcat/webapps

ADD context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
ADD tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

CMD ["catalina.sh", "run"]