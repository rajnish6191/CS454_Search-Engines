package org.gradle;

import org.utils.EngineBooks;
import org.utils.Indexing;
import org.utils.URLCrawler;

public class Indexer {
	
	public static void main(String[] args) {
		// upload file and extract them
		URLCrawler crawler = new URLCrawler();
		
		// you can choose it by enum class
		crawler.download(EngineBooks.WikiSmall);
		
		// start indexing
		Indexing indexing = new Indexing();
		indexing.makeIndex();
	}

}
