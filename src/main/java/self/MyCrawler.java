package self;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebInitParam;

import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.frontier.Frontier;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	static final Logger logger = Logger.getLogger(MyCrawler.class.getName());
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

	public MyCrawler() {

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
		System.out.println(referringPage.getWebURL().getURL()
				+ "l;;;;;;;;;;;;;;;");
		return !FILTERS.matcher(href).matches()
				&& href.startsWith("http://www.ics.uci.edu/");

	}

	@Override
	public boolean shouldVisit(WebURL pUrl) {
		String href = pUrl.getURL().toLowerCase();
		// if(!FILTERS.matcher(href).matches()
		// )
		// System.out.println((!FILTERS.matcher(href).matches()
		// )+" href " + href);
		 SelfCrawlController controller=(SelfCrawlController)
		 getMyController();

		 System.out.println("check instance "+(getMyController() instanceof SelfCrawlController)+"  -"+pUrl.getDomain()+"should visit"+
		 url_param+"  reqURL "+controller.getReqURL());
		System.out.println(controller.getReqURL().contains(pUrl.getDomain()));
		return (!FILTERS.matcher(href).matches() && controller.getReqURL().contains(pUrl.getDomain())) ;

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
		System.out.println("URL: " + url + " ->> " + (++count));
		List<WebURL> links;
		Map<WebURL, String> indexableData;
		links = cCrawlStat.getLinks();
		if (null != cCrawlStat.getIndexableData()) {
			indexableData = cCrawlStat.getIndexableData(); // =
															// htmlParseData.getOutgoingUrls();
		} else {
			// links = new ArrayList<WebURL>();
		}
		links.add(page.getWebURL());

		cCrawlStat.setLinks(links);
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();

			// cCrawlStat.incTotalLinks(links.size());

			System.out.println("link Size " + cCrawlStat.getTotalLinks());
			// System.out.println("Text length: " + text.length());
			// System.out.println("Html length: " + html);
			// System.out.println("Number of outgoing links: " + links.size());
			// logger.info("Text length: " + text.length());
			// logger.info("Html length: " + html);
			// logger.info("Number of outgoing links: " + links.size());
			// for (WebURL webURL : links) {
			// logger.info(webURL.getURL());
			// logger.info(webURL.getAnchor());
			// }
		}
	}

	@Override
	public void run() {
		// System.out.println("RunCallled");
		super.run();
	}

}
