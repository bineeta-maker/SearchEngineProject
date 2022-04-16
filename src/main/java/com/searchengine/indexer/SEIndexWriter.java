package com.searchengine.indexer;

import com.searchengine.utils.SEConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.parquet.column.page.PageReadStore;
import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.example.data.simple.convert.GroupRecordConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.ColumnIOFactory;
import org.apache.parquet.io.MessageColumnIO;
import org.apache.parquet.io.RecordReader;
import org.apache.parquet.schema.MessageType;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SEIndexWriter {
    int count=0;

    public void writeIndex(Analyzer analyzer) throws IOException {
        Directory indexDirectory =
                FSDirectory.open(new File(SEConstants.INDEXDATAPATH).toPath());
        ParquetFileReader reader = null;
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(indexDirectory, conf);

        //create the indexer
        Instant start = Instant.now();
        System.out.println("WRITER --: " + writer);
        SEConstants.FILES.forEach(n -> {
            try {
                createDocIndex(n, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("TOTAL TIME ELAPSED --: " + timeElapsed.toMinutes());
        writer.close();
    }

    private void createDocIndex(String n, IndexWriter writer) throws IOException {
        ParquetFileReader reader = ParquetFileReader.open(HadoopInputFile.fromPath(new Path(SEConstants.DATAPATH + n), new Configuration()));
        System.out.println("FILEPATH --: " + reader.getFile());
        MessageType schema = reader.getFileMetaData().getSchema();
        System.out.println("SCHEMA --: " + schema);
        PageReadStore pages;
        while ((pages = reader.readNextRowGroup()) != null) {
            long rows = pages.getRowCount();
            System.out.println("ROWS --: " + rows);
            MessageColumnIO columnIO = new ColumnIOFactory().getColumnIO(schema);
            RecordReader recordReader = columnIO.getRecordReader(pages, new GroupRecordConverter(schema));
            for (int i = 0; i < rows; i++) {
                System.out.println(" countdown:[" + count++ + "]");
                SimpleGroup simpleGroup = (SimpleGroup) recordReader.read();
                boolean isExists = isValidData(simpleGroup);
                if (isExists) {
                    String pmcid = simpleGroup.getString(SEConstants.PMC_ID, 0);
                    String journalid = simpleGroup.getString(SEConstants.JOURNAL_ID, 0);
                    String details = simpleGroup.getString(SEConstants.DETAILS, 0);

                    Document document = new Document();
                    document.add(new TextField(SEConstants.PMC_ID, pmcid, Field.Store.YES));
                    document.add(new TextField(SEConstants.JOURNAL_ID, journalid, Field.Store.YES));
                    document.add(new TextField(SEConstants.DETAILS, details, Field.Store.YES));
                    writer.addDocument(document);
                }
            }
        }
        reader.close();
    }

    //validates if the data is available
    private boolean isValidData(SimpleGroup simpleGroup) {
        if (simpleGroup.toString().equalsIgnoreCase("")){
            return false;
        }else{
            return true;
        }
    }
}
