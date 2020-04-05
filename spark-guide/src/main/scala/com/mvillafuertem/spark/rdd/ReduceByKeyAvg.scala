package com.mvillafuertem.spark.rdd

import com.mvillafuertem.spark.rdd.ReduceByKey.getClass
import org.apache.spark.{SparkConf, SparkContext}



object ReduceByKeyAvg {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("avgHousePrice").setMaster("local")
    val sc = new SparkContext(conf)

    val resourcesPath = getClass.getResource("/RealEstate.csv")
    val lines = sc.textFile(resourcesPath.getPath)
    val cleanedLines = lines.filter(line => !line.contains("Bedrooms"))

    val housePricePairRDD = cleanedLines.map(line => (line.split(",")(3), (1, line.split(",")(2).toDouble)))

    val housePriceTotal = housePricePairRDD.reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
    println("HousePriceTotal: ")
    for ((bedroom, total) <- housePriceTotal.collect()) println(bedroom + " : " + total)

    val housePriceAvg = housePriceTotal.mapValues(avgCount => avgCount._2 / avgCount._1)
    println("HousePriceAvg: ")
    for ((bedroom, avg) <- housePriceAvg.collect()) println(bedroom + " : " + avg)
  }
}
