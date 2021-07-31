#event-driven-platform-with-saga

1. Problem statement & solution approach - https://vijayredkar.medium.com/event-driven-saga-compensation-95af562d39c5
2. Architecture diagram - !!![BankNext_Choreog_SAGA_Arch](https://user-images.githubusercontent.com/25388646/127653144-b97fa414-1bc2-4a58-b4c7-7fca7d6530b6.png)


3. Local Kafka setup
    - download and install - https://www.tutorialspoint.com/apache_kafka/apache_kafka_installation_steps.htm
    - Zookeeper start cmd  - <Kafka_install_dir>\bin\windows\zookeeper-server-start.bat <Kafka_install_dir>\config\zookeeper.properties
    - Kafka-server start cmd - <Kafka_install_dir>\bin\windows\kafka-server-start.bat <Kafka_install_dir>\config\server.properties
4. Launch microservices
   - git clone in to your 
     - <project_dir> - https://github.com/vijayredkar/event-driven-platform-with-saga.git
   #### core services
   - entity-mgt launch      
     - cd <project_dir>\event-driven-platform-with-saga\banknext\entity-management >  mvn spring-boot:run
   - customer-mgt launch    
     - cd <project_dir>\event-driven-platform-with-saga\banknext\customer-management >  mvn spring-boot:run
   - account-mgt launch     
     - cd <project_dir>\event-driven-platform-with-saga\banknext\account-management >  mvn spring-boot:run   
   - entity-aggregation     
     - cd <project_dir>\event-driven-platform-with-saga\banknext\entity-aggregation >  mvn spring-boot:run   
   #### prechecks services
   - screening-svc launch     
     - cd <project_dir>\event-driven-platform-with-saga\banknext\prechecks\screening-svc >  mvn spring-boot:run   
   - deduplication-svc launch     
     - cd <project_dir>\event-driven-platform-with-saga\banknext\prechecks\deduplication-svc >  mvn spring-boot:run
   #### supporting services
   - monitoring-mgt launch  
     - cd <project_dir>\event-driven-platform-with-saga\banknext\supporting\monitoring-svc >  mvn spring-boot:run
   - notification-mgt launch  
     - cd <project_dir>\event-driven-platform-with-saga\banknext\supporting\notification-svc >  mvn spring-boot:run
   - recommendation-mgt launch 
     - cd <project_dir>\event-driven-platform-with-saga\banknext\supporting\recommendation-svc >  mvn spring-boot:run
5. Sample test cURLs for simulation
     - <project_dir>\event-driven-platform-with-saga\banknext/test-docs/1-cURL-event-initiator-flow-NoRollback-Reqd.txt
     - <project_dir>\event-driven-platform-with-saga\banknext/test-docs/2-cURL-event-initiator-SAGA-AcctFail-CustRollback-Reqd.txt
     - <project_dir>\event-driven-platform-with-saga\banknext/test-docs/3-cURL-event-initiator-SAGA-AggrFail-CustAcctRollback-Reqd.txt
