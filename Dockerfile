FROM jboss/wildfly

COPY ./postgresql-42.2.5.jar $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/
COPY ./module.xml $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/

RUN sh -c "$JBOSS_HOME/bin/standalone.sh &" && \
    sleep 30 && \
    $JBOSS_HOME/bin/jboss-cli.sh --connect --command="/subsystem=datasources/jdbc-driver=postgresql-driver:add(driver-class-name=org.postgresql.Driver, driver-module-name=org.postgresql, driver-name=postgresql-driver)" && \
    $JBOSS_HOME/bin/jboss-cli.sh --connect --command="data-source add --connection-url=jdbc:postgresql://postgres/ --driver-name=postgresql-driver --name=PostgresDS --jndi-name=java:/PostgresDS --user-name=postgres" && \
    $JBOSS_HOME/bin/jboss-cli.sh --connect --command=:shutdown

RUN rm -R /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current/*

COPY ./target/webapp.war $JBOSS_HOME/standalone/deployments/

