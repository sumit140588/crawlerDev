package self;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public class DevCrawler extends WebCrawler {
	static final Logger logger = Logger.getLogger(DevCrawler.class.getName());
	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|svg|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz"
					+ "doc|docx|bib|ppt|pptx" + "))$");
	// private final static Pattern IMAGE_FILTERS =
	// Pattern.compile(".*(|png|tiff?|mid|mp2|mp3|mp4))$");
	CrawlStat cCrawlStat;
	String url_param;

	public DevCrawler() {

		cCrawlStat = new CrawlStat();
		cCrawlStat.setLinks(new ArrayList<WebURL>());
	}

	/**
	 * This method receives two parameters. The first parameter is the page in
	 * which we have discovered this new url and the second parameter is the new
	 * url. You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic). In this example,
	 * we are instructing the crawler to ignore urls that have css, js, git, ...
	 * extensions and to only accept urls that start with
	 * "http://www.ics.uci.edu/". In this case, we didn't need the referringPage
	 * parameter to make the decision.
	 */
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		logger.info(referringPage.getWebURL().getURL()
				+ "l;;;;;;;;;;;;;;;");
		return !FILTERS.matcher(href).matches()
				&& href.startsWith("http://www.ics.uci.edu/");

	}

	@Override
	public boolean shouldVisit(WebURL pUrl) {
		String href = pUrl.getURL().toLowerCase();
		// if(!FILTERS.matcher(href).matches()
		// )
		// logger.info((!FILTERS.matcher(href).matches()
		// )+" href " + href);
		DevCrawlController controller = (DevCrawlController) getMyController();

		logger.info("check instance "
				+ (getMyController() instanceof DevCrawlController) + "  -"
				+ pUrl.getDomain() + "should visit" + href + "-req url"
				+ controller.getReqURL());
		logger.info(!FILTERS.matcher(href).matches()
				&& pUrl.getURL().startsWith(controller.getReqURL()));
		List<WebURL> externalLinks = cCrawlStat.getExternalLinks();
		if (null == externalLinks || externalLinks.isEmpty()
				|| externalLinks.size() == 0) {
			externalLinks = new ArrayList<WebURL>();
		}
		if (!FILTERS.matcher(href).matches()
				&& !pUrl.getURL()
						.substring(pUrl.getURL().indexOf(":"))
						.startsWith(
								controller.getReqURL().substring(
										controller.getReqURL().indexOf(":")))) {
			externalLinks.add(pUrl);
			cCrawlStat.setExternalLinks(externalLinks);
			logger.info("External link size "
					+ cCrawlStat.getExternalLinks().size());
		}
		return (!FILTERS.matcher(href).matches() && pUrl.getURL().startsWith(
				controller.getReqURL()));

	}

	@Override
	public Object getMyLocalData() {
		return cCrawlStat;
	}

	static int count;

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		cCrawlStat.incProcessedPages();

		String url = page.getWebURL().getURL();
		logger.info("URL: " + url + " ->> " + (++count));
		List<WebURL> links;
		// Map<WebURL, String> indexableData;
		links = cCrawlStat.getLinks();
		if (null == links) {
			links = new ArrayList<WebURL>();
		}
		links.add(page.getWebURL());

		cCrawlStat.setLinks(links);
		
	}

	@Override
	public void run() {
		// logger.info("RunCallled");
		super.run();
	}

}
