package com.searchengine.indexer;

import com.searchengine.utils.SEConstants;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class SEDocumentHandler {

    public void handleDocs() {

        SparkConf conf= new SparkConf().setAppName("JavaSpark").setMaster("local[*]");
        System.out.println("Hello World!  conf: "+conf);
        SparkSession spark = SparkSession.builder().appName("JavaSpark").config("spark.some.config.option", "some-value").getOrCreate();
        System.out.println("Hello World!  spark: "+spark);
        JavaSparkContext sc = new JavaSparkContext();
        System.out.println("Hello World!  sc: "+sc);
        JavaRDD rdd = sc.textFile(SEConstants.DATAPATH+"pmc-data-00.parquet");
        //Dataset<Row> employees = spark.read().parquet(SEConstants.DATAPATH+"pmc-data-00.parquet");
        System.out.println(rdd);
    }
}
