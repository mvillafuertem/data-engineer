package com.mvillafuertem.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}

object MapValues {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("airports").setMaster("local")
    val sc = new SparkContext(conf)

    val resourcesPath = getClass.getResource("/airports.text")
    val airportsRDD = sc.textFile(resourcesPath.getPath)

    val COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"

    val airportPairRDD = airportsRDD.map(
      line => (line.split(COMMA_DELIMITER)(1), line.split(COMMA_DELIMITER)(3)))

    val airportsApplyFilter = airportPairRDD.mapValues(countryName => countryName.toUpperCase())

    airportsApplyFilter.saveAsTextFile("target/airports_map_value_uppercase.text")

  }

}
