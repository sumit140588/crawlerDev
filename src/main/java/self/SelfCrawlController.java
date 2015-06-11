package self;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class SelfCrawlController extends CrawlController {

	String reqURL;
	
	public String getReqURL() {
		return reqURL;
	}

	public void setReqURL(String reqURL) {
		this.reqURL = reqURL;
	}

	public SelfCrawlController(CrawlConfig config, PageFetcher pageFetcher,
			RobotstxtServer robotstxtServer) throws Exception {
		super(config, pageFetcher, robotstxtServer);
		// TODO Auto-generated constructor stub
	}

	public SelfCrawlController(CrawlConfig config, PageFetcher pageFetcher,
			RobotstxtServer robotstxtServer, String reqURL) throws Exception {
		super(config, pageFetcher, robotstxtServer);
		this.reqURL = reqURL;
		System.out.println("req -"+reqURL);
	}
	

}
