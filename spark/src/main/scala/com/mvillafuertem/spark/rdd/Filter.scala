package com.mvillafuertem.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}

object Filter {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("airports").setMaster("local")
    val sc = new SparkContext(conf)

    val resourcesPath = getClass.getResource("/airports.text")
    val airportsRDD = sc.textFile(resourcesPath.getPath)

    val COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"

    val airportPairRDD = airportsRDD.map(
      line => (line.split(COMMA_DELIMITER)(1), line.split(COMMA_DELIMITER)(3)))

    val airportsApplyFilter = airportPairRDD.filter(k => k._2 != "\"United States\"")

    airportsApplyFilter.saveAsTextFile("target/airports_not_in_usa_pair_rdd.text")

  }

}
