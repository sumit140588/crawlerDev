package com.crawl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import self.Controller;
import self.MyCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  
		String url_param=pReq.getParameter("url");
		if(null!=url_param && !url_param.isEmpty()){
		String[] urls=url_param.split(",");
	    Matcher m = p.matcher("your url here"); 
		System.out.println(pReq.getParameter("url "));
		try {
		List<String> indexURLs=	new  Controller().main(urls);
		if(null!=indexURLs && !indexURLs.isEmpty()){
			System.out.println("index url count "+indexURLs.size());
			pReq.setAttribute("indexURLs", indexURLs);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pReq.setAttribute("a", pReq.getParameter("url"));
		//pReq.("index.jsp").forward(pReq, pResp);
		}//pReq.getRequestDispatcher("index.jsp").forward(pReq, pResp);
		pReq.getRequestDispatcher("/index.jsp").forward(pReq, pResp);
	
		return;
	}

	public void callCaller() throws Exception {
		String crawlStorageFolder = "/data/crawl/root";
		int numberOfCrawlers = 7;

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(0);
		config.setMaxPagesToFetch(1);
		config.setPolitenessDelay(200);

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
		controller.addSeed("http://www.ics.uci.edu/~lopes/");
		// controller.addSeed("http://www.ics.uci.edu/~welling/");
		// controller.addSeed("http://www.ics.uci.edu/");

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(MyCrawler.class, numberOfCrawlers);
		System.out.println("Hello");
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		System.out.println("Crawller Servlet ->" + crawlersLocalData.size());

	}
}
