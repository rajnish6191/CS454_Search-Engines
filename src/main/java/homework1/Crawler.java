package homework1;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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
		int depth = 2;
		String url = null;
      
		
		//Parse the options
		OptionParser parser = new OptionParser( "d:u:" );

        OptionSet options = parser.parse(args);
        
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
    		if(!subLinkList.isEmpty())
    		{
    			for(int i = 0; i < depth;i++)
    			{
    				
    				Set<String> tempList = new LinkedHashSet<String>();
    				tempList.addAll(subLinkList);
    				subLinkList.clear();
    				for(String link : tempList)
        			{
    					System.out.println(link);
    					c.parseList(c.getVisitedURLList(), subLinkList);
        				subLinkList.addAll(c.crawlPage(link));
    					
        			}
    				c.addAll(subLinkList);
    			}
    			System.out.println("Crawled Link List:");
    			System.out.println(c.getVisitedURLList());
    		}
        }
        else
        {
        	System.out.println("Invalid arguments Specified. Specify -d and -u.");
        }
	}
	
	//Remove duplicates from list
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
	
	//Crawl page using jsoup
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

		//Find all of the links in the page
		Elements links = doc.select("a[href]");
		
		for (Element link : links) 
		{
			///Check if already added
			if(!visitedURLList.contains(link.attr("abs:href")))
			{
				urlList.add(link.attr("abs:href"));
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
