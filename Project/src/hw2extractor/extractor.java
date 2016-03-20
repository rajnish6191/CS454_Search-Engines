package hw2extractor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import java.net.URL;

public class Crawler {

	private final static int threads = 20;
	
	private final static Logger LOG = Logger.getLogger(Crawler.class);

	public Set<String> visitedURLList = new LinkedHashSet<String>();

	public static void main(String args[]) throws Exception {

		try {
			int depth = 2;
			String url = null;

			OptionParser parser = new OptionParser("d:u:e");

			OptionSet options = parser.parse(args);

			// Parse the options for depth and URL
			if (options.has("d") && options.has("u")) {
				url = (String) options.valueOf("u");
				depth = Integer.parseInt((String) options.valueOf("d"));

				if ((url == null || url.equals(""))) {
					LOG.info("Invalid URL Argument");
				}

				final Crawler c = new Crawler();
				final Set<String> subLinkList = new LinkedHashSet<String>();
				subLinkList.add(url);
				c.addAll(subLinkList);
				if (!subLinkList.isEmpty() && subLinkList != null) {
					for (int i = 0; i < depth; i++) {
						if (subLinkList.isEmpty()) {
							break;
						}
						Set<String> tempList = new LinkedHashSet<String>();
						tempList.addAll(subLinkList);
						subLinkList.clear();
						for (final String link : tempList) {
							if (link != null) {
								new Runnable() {

									@Override
									public void run() {
										LOG.info(link);
										c.parseList(c.getVisitedURLList(), subLinkList);
										try {
											subLinkList.addAll(c.crawlPage(link));
										} catch (Exception e) {
											LOG.error("exception==" + e);
										}
									}
								}.run();
							}
						}
						c.addAll(subLinkList);
					}
					LOG.info("Crawled Link List:");
					LOG.info(c.getVisitedURLList());

					// Extraction
					if (options.has("e")) {
						File yourFile = new File("./metadata/metadata.csv");
						LOG.info(yourFile.getAbsolutePath());
						if (!yourFile.exists()) {
							yourFile.createNewFile();
						}
						FileOutputStream oFile = new FileOutputStream(yourFile, false);
						final PrintStream printStream = new PrintStream(oFile);

						// Executor service with 20 threads
						ExecutorService executorPool = Executors.newFixedThreadPool(threads);

						for (final String link : c.getVisitedURLList()) {
							executorPool.submit(new Runnable() {

								@Override
								public void run() {
									try {
										c.getMetaTags(link, printStream);
									} catch (IOException e) {
										try {
											c.getMetaBytesTag(link);
										} catch (IOException e1) {
											LOG.error("exception==" + e1);
										}
									}
								}
							});
						}

						executorPool.shutdown();

						printStream.flush();
						printStream.close();
					}
				}
			} else {
				LOG.info("Invalid arguments Specified. Specify -d and -u.");
			}
		} catch (Exception ae) {
			LOG.error("exception==" + ae);
		}
	}

	// Remove the duplicate links from the list
	public void parseList(Set<String> currentList, Set<String> newList) {
		for (String url : currentList) {
			for (Iterator<String> i = newList.iterator(); i.hasNext();) {
				String element = i.next();
				if (url.equals(element)) {
					i.remove();
				}
			}
		}
	}

	// Download the links and respected htmls
	public void getMetaTags(String url, final PrintStream printStream) throws IOException {
		int timeout = 10 * 1000;

		try {
			String fileName = "./html/" + UUID.randomUUID().toString() + ".html";

			Document htmlDocumentFromResponse = Jsoup.connect(url).timeout(timeout).get();

			// Get a list of Element objects, all of which are href tags
			Elements links = htmlDocumentFromResponse.select("meta");

			PrintWriter linkOut = new PrintWriter(fileName);
			LOG.info(fileName);
			linkOut.println(htmlDocumentFromResponse);
			linkOut.flush();
			linkOut.close();
			printStream.print(url);
			for (Element meta : links) {
				printStream.print("," + meta.outerHtml());
			}

			printStream.print("\n");

		} catch (Exception e) {
			getMetaBytesTag(url);
		}

	}

	private void getMetaBytesTag(String url) throws IOException {
		LOG.info("IMAGE -> "+url);
		
		String fileName = "./html/images/" +UUID.randomUUID().toString()+"-"+ url.replaceAll(".*/(.*)$", "$1");
		
		URL u = new URL(url);
        InputStream in = u.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
	}

	// Crawling
	public Set<String> crawlPage(String url) throws Exception {
		Set<String> urlList = new LinkedHashSet<String>();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(60 * 1000).get();
		} catch (Exception e) {
			LOG.error("Error trying to crawl: " + url + " | Skipping page. e=" + e);
		}

		Elements links = doc.select("a[href]");
		Elements images = doc.select("img[src]");

		if (links != null) {
			for (Element link : links) {

				if (!visitedURLList.contains(link.attr("abs:href"))) {
					urlList.add(link.attr("abs:href"));
				}
			}
		}

		if (images != null) {
			for (Element image : images) {
				urlList.add(image.attr("abs:src"));
			}
		}

		return urlList;
	}

	public void addAll(Set<String> subLinkList) {
		visitedURLList.addAll(subLinkList);
	}

	public Set<String> getVisitedURLList() {
		return visitedURLList;
	}

	public void setVisitedURLList(Set<String> visitedURLList) {
		this.visitedURLList = visitedURLList;
	}
}