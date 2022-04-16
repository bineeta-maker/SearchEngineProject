package com.searchengine.utils;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SEConstants {
    final public static String PMC_ID ="PMC_ID";
    final public static String JOURNAL_ID ="JOURNAL_ID";
    final public static String DETAILS ="DETAILS";
    final public static String INDEXDATAPATH ="D:\\1.Course_Material\\IR\\searchEngine\\indexdata\\";
    final public static String DATAPATH ="D:\\1.Course_Material\\IR\\searchEngine\\data\\";
    final public static String TOPICQUERYPATH ="D:\\1.Course_Material\\IR\\searchEngine\\data\\topics-2015-A.xml";
    final public static List<String> FILES = new ArrayList<String>() {
        {
            add("pmc-text-00.parquet");
            add("pmc-text-01.parquet");
            add("pmc-text-02.parquet");
            add("pmc-text-03.parquet");
        }
    };

}
