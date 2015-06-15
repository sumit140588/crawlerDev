/*
 * 
 */
package self;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;


/**
 * The Class Controller.
 */
public class Controller extends Thread {

	static final Logger logger = Logger.getLogger(Controller.class.getName());
	/** The urls. */
	String[] urls;

	/** The total numberofpages. */
	long toplevelPages, totalNumberofpages;

	/** The index urls. */
	Set<String> indexUrls;

	/** The external u rls. */
	Set<String> externalURls;

	/** The is running. */
	volatile boolean isRunning;

	/**
	 * Gets the toplevel pages.
	 * 
	 * @return the toplevel pages
	 */
	public long getToplevelPages() {
		return toplevelPages;
	}

	/**
	 * Sets the toplevel pages.
	 * 
	 * @param toplevelPages
	 *            the new toplevel pages
	 */
	public void setToplevelPages(long toplevelPages) {
		this.toplevelPages = toplevelPages;
	}

	/**
	 * Gets the total numberofpages.
	 * 
	 * @return the total numberofpages
	 */
	public long getTotalNumberofpages() {
		return totalNumberofpages;
	}

	/**
	 * Sets the total numberofpages.
	 * 
	 * @param totalNumberofpages
	 *            the new total numberofpages
	 */
	public void setTotalNumberofpages(long totalNumberofpages) {
		this.totalNumberofpages = totalNumberofpages;
	}

	/**
	 * Checks if is running.
	 * 
	 * @return true, if is running
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Sets the running.
	 * 
	 * @param isRunning
	 *            the new running
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * Gets the index urls.
	 * 
	 * @return the index urls
	 */
	public Set<String> getIndexUrls() {
		return indexUrls;
	}

	/**
	 * Sets the index urls.
	 * 
	 * @param indexUrls
	 *            the new index urls
	 */
	public void setIndexUrls(Set<String> indexUrls) {
		this.indexUrls = indexUrls;
	}

	/**
	 * Gets the external u rls.
	 * 
	 * @return the external u rls
	 */
	public Set<String> getExternalURls() {
		return externalURls;
	}

	/**
	 * Sets the external u rls.
	 * 
	 * @param externalURls
	 *            the new external u rls
	 */
	public void setExternalURls(Set<String> externalURls) {
		this.externalURls = externalURls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			isRunning = true;
			indexUrls = startCrawler(urls);

		} catch (Exception e) {

		} finally {
			isRunning = false;
		}
	}

	/**
	 * Gets the urls.
	 * 
	 * @return the urls
	 */
	public String[] getUrls() {
		return urls;
	}

	/**
	 * Sets the urls.
	 * 
	 * @param urls
	 *            the new urls
	 */
	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	/**
	 * Start crawler. this method is use to fetch the result and return the links.
	 * 
	 * @param args
	 *            the args
	 * @return the sets the
	 * @throws Exception
	 *             the exception
	 */
	public Set<String> startCrawler(String[] args) throws Exception {
		int numberOfCrawlers = 50;
		Set<String> indexURLS;
		CrawlConfig config = updateCrawlConfig();

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);

		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		DevCrawlController controller = new DevCrawlController(config,
				pageFetcher, robotstxtServer, urls[0]);
		
		for (String ar : args) {
			controller.addSeed(ar);
		}
		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		DevCrawler devCrawler = new DevCrawler();
		controller.start(devCrawler.getClass(), numberOfCrawlers);
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		controller.shutdown();
		indexURLS = new TreeSet<String>();
		externalURls = new TreeSet<String>();
		for (Object c : crawlersLocalData) {
			if (null != c) {
				CrawlStat temp = (CrawlStat) c;
				List<WebURL> tempLinks = temp.getLinks();
				indexURLS.addAll(extractLinks(tempLinks));
				externalURls.addAll(extractLinks(temp.getExternalLinks()));

			}
		}
		logger.info("crawler operation is finished");
		return indexURLS;
	}

	/**
	 * Update crawl config. this method configure and CrawlConfig object.
	 * 
	 * @return the crawl config
	 */
	private CrawlConfig updateCrawlConfig() {
		// String crawlStorageFolder = "data/crawl/root";
		CrawlConfig config = new CrawlConfig();
		Properties prop = readCrawlConfig();
		System.out.println(" depth "+prop.getProperty("maxDepthOfCrawling"));
		config.setCrawlStorageFolder(prop.getProperty("crawlStorageFolder"));
		config.setMaxDepthOfCrawling(Integer.valueOf(prop
				.getProperty("maxDepthOfCrawling")));
		config.setMaxPagesToFetch(Integer.valueOf(prop
				.getProperty("maxPagesToFetch")));
		config.setPolitenessDelay(Integer.valueOf(prop
				.getProperty("politenessDelay")));
		config.setProxyPort(Integer.valueOf(prop.getProperty("proxyPort")));
	
		//config.setProxyHost(prop.getProperty("proxyHost"));
		config.setIncludeHttpsPages(Boolean.valueOf(prop
				.getProperty("includeHttpsPages")));
		config.setFollowRedirects(Boolean.valueOf(prop
				.getProperty("followRedirects")));
		return config;
	}

	/**
	 * Read crawl config. this method will read the crawlerConfig.properties file.
	 * 
	 * @return the properties
	 */
	private Properties readCrawlConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		String workingDir = System.getProperty("user.dir");
		try {
			input = new FileInputStream(workingDir + File.separator
					+ "crawlerConfig.properties");
			prop.load(input);
			// System.out.println(prop.getProperty("crawlStorageFolder"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	/**
	 * Extract links.
	 * 
	 * @param tempLinks
	 *            the temp links
	 * @return the list
	 */
	private List<String> extractLinks(List<WebURL> tempLinks) {
		List<String> indexURLS = new ArrayList<String>();
		if (null != tempLinks && !tempLinks.isEmpty())
			for (WebURL w : tempLinks) {
				indexURLS.add(w.getURL());
				logger.info("URL-" + w.getURL());
				logger.info("docid-" + w.getDocid());
				logger.info("Domain-" + w.getDomain());
				logger.info("subdomain" + w.getSubDomain());
				logger.info("getDepth-" + w.getDepth());
				logger.info("parentdocid-" + w.getParentDocid());
				logger.info("parentURL-" + w.getParentUrl());
				if (w.getDepth() == 1) {
					toplevelPages++;
				}
			}
		return indexURLS;
	}

}