package project;

public class URL 
{
	public String URL = "";
	@Override
	public String toString() {
		return "URL [URL=" + URL + ", visited=" + visited + "]";
	}

	public boolean visited = true;
	
	public URL(String url, boolean visited)
	{
		this.URL = url;
		this.visited = visited;
	}
}