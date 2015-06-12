package self;

import java.util.List;
import java.util.Map;

import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlStat {
	  private int totalProcessedPages;
	  private long totalLinks;
	  private long totalTextSize;
	  List<WebURL> links;
	  List<WebURL> externalLinks;
	  Map<WebURL,String> indexableData;
	  
	  
	  
	/**
	 * @return the indexableData
	 */
	public Map<WebURL, String> getIndexableData() {
		return indexableData;
	}

	/**
	 * @param pIndexableData the indexableData to set
	 */
	public void setIndexableData(Map<WebURL, String> pIndexableData) {
		indexableData = pIndexableData;
	}

	/**
	 * @return the links
	 */
	public List<WebURL> getLinks() {
		return links;
	}

	/**
	 * @param pLinks the links to set
	 */
	public void setLinks(List<WebURL> pLinks) {
		links = pLinks;
	}

	public int getTotalProcessedPages() {
	    return totalProcessedPages;
	  }

	  public void setTotalProcessedPages(int totalProcessedPages) {
	    this.totalProcessedPages = totalProcessedPages;
	  }

	  public void incProcessedPages() {
	    this.totalProcessedPages++;
	  }

	  public long getTotalLinks() {
	    return totalLinks;
	  }

	  public void setTotalLinks(long totalLinks) {
	    this.totalLinks = totalLinks;
	  }

	  public long getTotalTextSize() {
	    return totalTextSize;
	  }

	  public void setTotalTextSize(long totalTextSize) {
	    this.totalTextSize = totalTextSize;
	  }

	  public void incTotalLinks(int count) {
	    this.totalLinks += count;
	  }

	  public void incTotalTextSize(int count) {
	    this.totalTextSize += count;
	  }

	public List<WebURL> getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(List<WebURL> externalLinks) {
		this.externalLinks = externalLinks;
	}
	  
	}