package com.searchengine.searcher;

import com.searchengine.utils.SEConstants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class SESearcher {

    public String getResults(String[] topicQuery, Analyzer analyzer) throws ParseException, IOException {
        StringBuffer str = new StringBuffer("");
        System.out.println("QUERY --: " + topicQuery[2]);
        // Now search the index:
        Directory indexDirectory =
                FSDirectory.open(new File(SEConstants.INDEXDATAPATH).toPath());
        DirectoryReader ireader = DirectoryReader.open(indexDirectory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser(SEConstants.DETAILS, analyzer);
        Query query = parser.parse(topicQuery[2]);
        System.out.println("PARSED QUERY --: " + query);
        isearcher.setSimilarity(new BM25Similarity());
        ScoreDoc[] hits = isearcher.search(query, Integer.parseInt(topicQuery[1])).scoreDocs;
        System.out.println("TOTAL NUMBER OF HITS --: " + hits.length);
        // Iterate through the results:
        str.append("\n").append("TopicNo.").append(",").append("PMCID").append(",").append("SCORE").append("\n");
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            float score = hits[i].score;
            str.append(topicQuery[0]).append(",").append(hitDoc.get(SEConstants.PMC_ID)).append(",").append(score).append("\n");
        }
        str.append("\n").append("SEARCH PROCESS COMPLETE.").append("\n");
        ireader.close();
        indexDirectory.close();
        return str.toString();
    }
}
