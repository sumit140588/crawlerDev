package self;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class Controller extends Thread {
	String[] urls;
	volatile boolean isRunning;
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	List<String> indexUrls;
	public void run() {
		try {
			isRunning=true;
			indexUrls=main(urls);
			
		} catch (Exception e) {
			
		}finally{
		isRunning=false;
		}
	}
	
	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public List<String> getIndexUrls() {
		return indexUrls;
	}

	public void setIndexUrls(List<String> indexUrls) {
		this.indexUrls = indexUrls;
	}

	public List<String> main(String[] args) throws Exception {
		String crawlStorageFolder = "data/crawl/root";
		int numberOfCrawlers = 10;
List<String> indexURLS;
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(5);
		config.setMaxPagesToFetch(1000);
		config.setPolitenessDelay(200);
		config.setProxyPort(0);
		config.setProxyHost(null);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);

		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher,
				robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		for (String ar : args)
			controller.addSeed(ar);

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		MyCrawler myCrawler = new MyCrawler();

		controller.start(myCrawler.getClass(), numberOfCrawlers);
		System.out.println("Hello");
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		// controller.

		System.out.println(crawlersLocalData.size());
		// while(controller.isFinished()){
		// System.out.println("Not Finished");
		// Thread.currentThread().sleep(10000);
		// }

		System.out.println("before shutdown thread count"
				+ Thread.activeCount());

		System.out.println("Ginished" + crawlersLocalData.size());
		controller.shutdown();
		System.out.println("After shutdown thread count "
				+ Thread.activeCount());
		indexURLS=new ArrayList<String>();
		for (Object c : crawlersLocalData) {
			if (null != c) {
				CrawlStat temp = (CrawlStat) c;

				if (null != temp.getLinks() && !temp.getLinks().isEmpty())
					for (WebURL w : temp.getLinks()) {
						//System.out.println("domain " + w.getDomain() + " "
							//	+ w.getURL());
						indexURLS.add(w.getURL());
					}
			}
		}
		return indexURLS;
	}
}