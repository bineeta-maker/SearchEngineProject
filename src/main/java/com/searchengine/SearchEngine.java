package com.searchengine;

import com.searchengine.indexer.SEIndexWriter;
import com.searchengine.searcher.SEQueryParser;
import com.searchengine.searcher.SESearcher;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SearchEngine {

    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {
        SearchEngine se = new SearchEngine();
        //Analyzer analyzer = new StopAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        Analyzer analyzer = new StandardAnalyzer();

        String str = args == null
                ? "NO PROCESS EXECUTED"
                : ((args.length == 1 && args[0].equalsIgnoreCase("createindex"))
                ? se.createIndex(analyzer)
                : se.searchQuery(args,analyzer));
        System.out.println(str);
    }

    private String searchQuery(String[] topicQuery, Analyzer analyzer) throws ParseException, IOException, ParserConfigurationException, SAXException {

        SEQueryParser parser = new SEQueryParser();
        SESearcher searcher = new SESearcher();
        // read XML query
        topicQuery = parser.getQueryStatement(topicQuery);
        String results = searcher.getResults(topicQuery, analyzer);
        return results;
    }

    private String createIndex(Analyzer analyzer) throws IOException {

        SEIndexWriter indexWriter = new SEIndexWriter();
        System.out.println("INDEXING PROCESS START");
        //docHandler.handleDocs();
        indexWriter.writeIndex(analyzer);
        return "INDEXING PROCESS COMPLETE.";
    }
}

