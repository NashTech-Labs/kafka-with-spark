## Configuration Steps   
1. Download the Kafka for your system from below link.  
https://kafka.apache.org/quickstart
2. In the **app package** we have two scala file one is **KafkaProducerApp** that file is used to produce data to kafka topic and another file is **KafkaConsumerApp** that is used to Consume the data that are published to the kafka topic.  
3. **Before Running the code files** makes sure that your **zookeeper and kafka server start.**
4. Open the Terminal & run **Kafka Zookeeper through the below command** in the kafka directory where you have downloaded the kafka folder **(/Downloads/kafka_2.12-3.6.1
   )**.     
>bin/zookeeper-server-start.sh config/zookeeper.properties   
5. Now Run the Kafka-Server  
> bin/kafka-server-start.sh config/server.properties
6. Run the **KafkaProducerApp** to publish the sample data to the kafka topic that is defined under that file.  
7. Now Run the **KafkaConsumerApp** to access the data from kafka topic and I have filtered the data as per follower count and print it to console and also written code to save in directory.