package self;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class Controller extends Thread {
	String[] urls;

	volatile boolean isRunning;

	public long getToplevelPages() {
		return toplevelPages;
	}

	public void setToplevelPages(long toplevelPages) {
		this.toplevelPages = toplevelPages;
	}

	public long getTotalNumberofpages() {
		return totalNumberofpages;
	}

	public void setTotalNumberofpages(long totalNumberofpages) {
		this.totalNumberofpages = totalNumberofpages;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	Set<String> indexUrls;
	Set<String> externalURls;

	
	

	public Set<String> getIndexUrls() {
		return indexUrls;
	}

	public void setIndexUrls(Set<String> indexUrls) {
		this.indexUrls = indexUrls;
	}

	public Set<String> getExternalURls() {
		return externalURls;
	}

	public void setExternalURls(Set<String> externalURls) {
		this.externalURls = externalURls;
	}

	public void run() {
		try {
			isRunning = true;
			indexUrls = main(urls);

		} catch (Exception e) {

		} finally {
			isRunning = false;
		}
	}

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	

	public Set<String> main(String[] args) throws Exception {
		String crawlStorageFolder = "data/crawl/root";
		int numberOfCrawlers = 50;
		Set<String> indexURLS;
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(3);
		config.setMaxPagesToFetch(50);
		config.setPolitenessDelay(200);
		config.setProxyPort(0);
		config.setProxyHost(null);
		config.setIncludeHttpsPages(true);;
		config.setFollowRedirects(true);
		//config.setResumableCrawling(true);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);

		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		SelfCrawlController controller = new SelfCrawlController(config,
				pageFetcher, robotstxtServer, urls[0]);
//		CrawlController controller = new CrawlController(config,
//				pageFetcher, robotstxtServer);
		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		
		for (String ar : args){
		System.out.println("ar-"+ar);
			controller.addSeed(ar);
		}
		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		MyCrawler myCrawler = new MyCrawler();
	//	myCrawler.url_param = args[0];
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
		indexURLS = new TreeSet<String>();
		externalURls= new TreeSet<String>();
		for (Object c : crawlersLocalData) {
			if (null != c) {
				CrawlStat temp = (CrawlStat) c;
				List<WebURL> tempLinks=temp.getLinks();
				indexURLS.addAll(extractLinks( tempLinks));
				
				externalURls.addAll(extractLinks(temp.getExternalLinks()));
				
			}
		}
		return indexURLS;
	}

	private List<String> extractLinks( List<WebURL> tempLinks) {
		List<String> indexURLS=new ArrayList<String>();
		if (null !=  tempLinks&& !tempLinks.isEmpty())
			for (WebURL w : tempLinks) {
				// System.out.println("domain " + w.getDomain() + " "
				// + w.getURL());
				indexURLS.add(w.getURL());
				// if(w.get)
				// toplevelPages
				System.out.println("URL-" + w.getURL());
				System.out.println("docid-" + w.getDocid());
				System.out.println("Domain-" + w.getDomain());
				System.out.println("subdomain" + w.getSubDomain());
				System.out.println("getDepth-" + w.getDepth());
				System.out.println("parentdocid-" + w.getParentDocid());
				System.out.println("parentURL-" + w.getParentUrl());
				if (w.getDepth() == 1) {
					toplevelPages++;
				}

			}
		return indexURLS;
	}

	long toplevelPages, totalNumberofpages;
}