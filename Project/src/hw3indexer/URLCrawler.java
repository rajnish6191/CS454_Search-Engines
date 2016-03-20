package hw3indexer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class URLCrawler {

	private static final Logger LOG = Logger.getLogger(URLCrawler.class);

	private static final String url = "http://www.search-engines-book.com/collections/";
	
	// path to save an archive
	private static final String prefix = "download";

	// path to extract archive
	private static final String prefixExtracted = "extract";

	private Set<String> visitedURLList;
	private List<String> urlList;
	private String filename;
	
	/**
	 * Download archive file
	 * @param eBooks	enum class with archive names
	 */
	public void download(EngineBooks eBooks) {
		visitedURLList = new LinkedHashSet<String>();
		urlList = new ArrayList<String>();

		// init the save dir
		File savedDir = new File(prefix);
		if(savedDir.exists())
			try {
				FileUtils.deleteDirectory(savedDir);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1);
			}

		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(60 * 1000).get();
		} catch (Exception e) {
			LOG.error("Error trying to crawl: " + url + " | Skipping page. e=" + e);
		}

		// choose all links
		Elements links = doc.select("a[href]");

		if (links != null) {
			for (Element link : links) {

				if (!visitedURLList.contains(link.attr("abs:href"))) {
					urlList.add(link.attr("abs:href"));
				}
			}
		}

		LOG.info("Links available -> " + urlList);

		// set file name to store archive
		switch (eBooks) {
		case WikiSmall:
			filename = "wiki-small.tar.gz";
			break;
		case WikiLarge:
			filename = "wiki-large.tar.gz";
			break;
		case Cacm:
			filename = "cacm.tar.gz";
			break;
		default:
			filename = "wiki-small.tar.gz";
			break;
		}

		// download choosed link
		for (int i = 0; i < urlList.size(); i++) {
			if (urlList.get(i).contains(filename)) {
				LOG.info("Link choosed -> " + urlList.get(i));
				if(!savedDir.exists()){
					savedDir.mkdir();
				}
				
				URL link;
				try {
					link = new URL(urlList.get(i));
					InputStream in = link.openStream();
					OutputStream out = new BufferedOutputStream(new FileOutputStream(prefix + File.separator + filename));

					for (int b; (b = in.read()) != -1;) {
						out.write(b);
					}
					out.close();
					in.close();
				} catch (MalformedURLException e) {
					LOG.error(e);
				} catch (IOException e) {
					LOG.error(e);
				}
			}
		}
		
		LOG.info("Start Extracting...");
		// extract archive
		extract();
		LOG.info("Finish!");
	}
	
	/**
	 * Extractor for archive
	 */
	private void extract(){
		File extracted = new File(prefixExtracted+File.separator);
		if(extracted.exists())
			try {
				FileUtils.deleteDirectory(extracted);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOG.error(e);
			}
		if(!extracted.exists()) extracted.mkdir();
		
		// main extract proccess
		TarGZipUnArchiver gz = new TarGZipUnArchiver();
		gz.setSourceFile(new File(prefix+File.separator+filename));
		gz.setDestDirectory(extracted);
		gz.enableLogging(new ConsoleLogger(ConsoleLogger.LEVEL_DEBUG, "Logger"));
		gz.extract();
	}
}
