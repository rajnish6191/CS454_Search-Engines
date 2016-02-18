package project;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawler {

	public Set<String> visitedURLList = new LinkedHashSet<String>();
	


	public static void main(String args[]) throws Exception
	{
		
		try
		{
		int depth = 2;
		String url = null;
      
		
		OptionParser parser = new OptionParser( "d:u:e" );

        OptionSet options = parser.parse(args);
        
        // Parse the options for depth and URL
        if(options.has("d") && options.has("u"))
        {
        	url = (String) options.valueOf("u");
        	depth = Integer.parseInt((String) options.valueOf("d"));
        	
        	if((url == null || url.equals("")))
        	{
        		System.out.println("Invalid URL Argument");
        	}
        	
        	Crawler c = new Crawler();
    		Set<String> subLinkList = new LinkedHashSet<String>();
    		subLinkList.add(url);
    		c.addAll(subLinkList);
    		if(!subLinkList.isEmpty() && subLinkList!=null )
    		{
    			for(int i = 0; i < depth;i++)
    			{
    				if(subLinkList.isEmpty())
    				{
    					break;
    				}
    				Set<String> tempList = new LinkedHashSet<String>();
    				tempList.addAll(subLinkList);
    				subLinkList.clear();
    				for(String link : tempList)
        			{
    					if(link!=null)
    					{		
    					System.out.println(link);
    					c.parseList(c.getVisitedURLList(), subLinkList);
        				subLinkList.addAll(c.crawlPage(link));
    					}
        			}
    				c.addAll(subLinkList);
    			}
    			System.out.println("Crawled Link List:");
    			System.out.println(c.getVisitedURLList());
    		
    		// Extraction	
    			if ( options.has("e")) {
					File yourFile = new File("./metadata/metadata.csv");
					System.out.println(yourFile.getAbsolutePath());
					if (!yourFile.exists()) {
						yourFile.createNewFile();
					}
					FileOutputStream oFile = new FileOutputStream(yourFile,
							false);
					final PrintStream printStream = new PrintStream(oFile);
					for (String link : c.getVisitedURLList()) {
						c.getMetaTags(link, yourFile, printStream);
					}
					printStream.flush();
					printStream.close();
				}
    		}
        }
        else
        {
        	System.out.println("Invalid arguments Specified. Specify -d and -u.");
        	}
		}catch(Exception ae)
		{
			ae.printStackTrace();
			System.out.println("exception=="+ae.getMessage());
			}
		}
	
	// Remove the duplicate links from the list
	public void parseList(Set<String> currentList, Set<String> newList)
	{
		for(String url : currentList)
		{
			for (Iterator<String> i = newList.iterator(); i.hasNext();) 
			{
				String element = i.next();
			    if(url.equals(element))
				{
					i.remove();
				}
			}	
		}
	}
	
	// Download the links and respected htmls
	public Map<String, String> getMetaTags(String url, File oFile, PrintStream printStream) throws IOException
	{
		Map<String,String> metaTags = new HashMap<String,String>();
		try
		{
		Document htmlDocumentFromResponse = Jsoup.connect(url).get();
		
		//Get a list of Element objects, all of which are href tags
		Elements links = htmlDocumentFromResponse.select("meta");
		PrintWriter linkOut = new PrintWriter(UUID.randomUUID().toString() + ".html");
		linkOut.println(htmlDocumentFromResponse);
		linkOut.flush();
		linkOut.close();
		printStream.print(url);
		for(Element meta : links)
		{
			printStream.print("," + meta.outerHtml());
		}
		
		printStream.print("\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("exception=="+e.getMessage());			
		}
		return metaTags;
		
	}
	
	// Crawling
	public Set<String> crawlPage(String url) throws Exception
	{
		Set<String> urlList = new LinkedHashSet<String>();
		Document doc = null;
		try 
		{
			doc = Jsoup.connect(url).timeout(60*1000).get();
		} 
		catch (Exception e) 
		{
			System.out.println("Error trying to crawl: " + url + " | Skipping page. e=" + e.getMessage());
		}
		
		Elements links = doc.select("a[href]");
		
		if(links!=null)
		{
		for (Element link : links) 
		{
			
			if(!visitedURLList.contains(link.attr("abs:href")))
			{
				urlList.add(link.attr("abs:href"));
			}
        }
		}
		return urlList;
	}
	
	public void addAll(Set<String> subLinkList)
	{
		this.visitedURLList.addAll(subLinkList);
	}
	
	public Set<String> getVisitedURLList() {
		return visitedURLList;
	}

	public void setVisitedURLList(Set<String> visitedURLList) {
		this.visitedURLList = visitedURLList;
	}
}