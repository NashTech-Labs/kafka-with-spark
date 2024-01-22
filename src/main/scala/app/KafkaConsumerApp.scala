package app

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types._

import scala.concurrent.duration.DurationInt

object KafkaConsumerApp extends App {

  private val spark = SparkSession.builder()
    .appName("consumer")
    .master("local[3]")
    .getOrCreate()

  private val data = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "employee")
    .option("startingOffsets", "latest")
    .option("maxOffsetsPerTrigger", "9")
    .load

  private val schema = new StructType()
    .add("id", IntegerType)
    .add("firstname", StringType)
    .add("lastname", StringType)
    .add("location", StringType)
    .add("online", BooleanType)
    .add("followers", IntegerType)

  private val dataframe = data.select(from_json(col("value").cast(StringType), schema).as("data"))
    .select("data.*")

  private val filteredDataframe =
    dataframe
      .filter("followers >= 1000")

  /* This code snippet print the output on the console */
  filteredDataframe
    .writeStream
    .format("console")
    .partitionBy("firstname")
    .outputMode("append")
    .start()
    .awaitTermination()

  /* This code snippet will save the data into the resource directory*/

  /*filteredDataframe
    .writeStream
    .partitionBy("firstname")
    .format("json")
    .option("path", "src/main/resources/Checkpoint")
    .option("checkPointLocation", "src/main/resources/Checkpoint")
    .option("mode", "overwrite")
    .trigger(Trigger.ProcessingTime(30.seconds))
    .start()
    .awaitTermination()*/
}