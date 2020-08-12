package com.mvillafuertem.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}

object ReduceByKey {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordCounts").setMaster("local")
    val sc = new SparkContext(conf)

    val resourcesPath = getClass.getResource("/word_count.text")
    val lines = sc.textFile(resourcesPath.getPath)
    val wordRDD = lines.flatMap(line => line.split(" "))
    val wordPairRDD = wordRDD.map(word => (word, 1))

    val wordCounts = wordPairRDD.reduceByKey((x, y) => x + y)

    for ((word, count) <- wordCounts.collect()) println(word + " : " + count)
  }

}
