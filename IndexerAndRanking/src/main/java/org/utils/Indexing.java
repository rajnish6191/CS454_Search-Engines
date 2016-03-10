package org.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexing {

	private static final String SEARCHED_CONTENT = "Wikipedia";
	
	private static final String PREFIX_EXTRACTED = "extract";
	private static final String INDEXED_DIR = "indexed";

	private static final String FILE_NAME = "filename";
	private static final String CONTENTS = "contents";

	private static final Logger LOG = Logger.getLogger(Indexing.class);

	/**
	 * Make indexes with Apache Lucene
	 * Examples here: {@link http://www.lucenetutorial.com/lucene-in-5-minutes.html}
	 */
	public void makeIndex(){
		File indexDir = new File(INDEXED_DIR);
		if(indexDir.exists()){
			try {
				FileUtils.deleteDirectory(indexDir);
			} catch (IOException e) {
				LOG.error(e);
			}
		}
		
		indexDir.mkdir();
		
		// Read all extracted dir
		Directory dir = null;
		try {
			dir = FSDirectory.open(indexDir.toPath());
		} catch (IOException e) {
			LOG.error(e);
		}
		
		// Create Analizer and write indexes
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig conf = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(dir, conf);
		} catch (IOException e) {
			LOG.error(e);
		}
		
		// for each file make a document and store it with IndexWriter
		Collection<File> files = FileUtils.listFiles(new File(PREFIX_EXTRACTED), CanReadFileFilter.CAN_READ, DirectoryFileFilter.INSTANCE);
		for (File file : files) {		
			try {
				addDoc(indexWriter, file);
				LOG.info("Indexed -> "+file.getName());
			} catch (IOException e) {
				LOG.error(e);
			}
		}
		try {
			indexWriter.close();
			
		} catch (IOException e) {
			LOG.error(e);
		}
		
		search(dir, analyzer);
		
		try {
			dir.close();
		} catch (IOException e) {
			LOG.error(e);
		}
	}
	
	/**
	 * Add files and content to index
	 * @param w
	 * @param f
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private static void addDoc(IndexWriter w, File f) throws IOException {
		  Document doc = new Document();
//		  doc.add(new StringField(indexedDir, s, Field.Store.YES));  // or TextField for tokenized content
		  doc.add(new Field(FILE_NAME, f.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));  // or TextField for tokenized content  d(indexedContext, s, Field.Store.YES)
		  doc.add(new Field(CONTENTS, new FileReader(f)));
		  w.addDocument(doc);
		}
	
	/**
	 * Search data in indexed content
	 * @param dir
	 * @param analyzer
	 */
	private static void search(Directory dir, Analyzer analyzer){
		// test query
		Query q = null;
		try {
			q = new QueryParser(CONTENTS, analyzer).parse(SEARCHED_CONTENT);
		} catch (ParseException e) {
			LOG.error(e);
		}
		
		// read from query
		ScoreDoc[] hits = null;
		try {
			
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs docs = searcher.search(q, 10);
			hits = docs.scoreDocs;
			dir.close();
		} catch (IOException e) {
			LOG.error(e);
		}
		
		// display
		LOG.info("Found " + hits.length + " hits.");
	}
}
