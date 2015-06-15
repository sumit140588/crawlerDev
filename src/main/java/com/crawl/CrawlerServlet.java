package com.crawl;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.PropertyConfigurator;

import self.Controller;

/**
 * The Class CrawlerServlet. this class is used to handle the crawler form
 * request for url and process the request to fetch the links and send as
 * response.
 */
public class CrawlerServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* this method is initialize the logger.
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
		}
		super.init();
	}

	/*
	 * handle the get request.
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		String url_param = pReq.getParameter("url");
		if (null != url_param && !url_param.isEmpty()) {
			String[] urls = url_param.split(",");

			HttpSession session = pReq.getSession();
			String run = (String) session.getAttribute("run");
			try {
				Set<String> indexURLs = null;
				Set<String> externalURLs = null;

				Controller c;
				Controller Controller_param = (Controller) session
						.getAttribute("contolerObject");
				if (null == Controller_param) {
					System.out.println("If part controler");
					c = new Controller();
					c.setUrls(urls);
				} else {
					c = Controller_param;
				}

				Thread t;
				Thread Thread_param = (Thread) session.getAttribute("thread");
				if (null == Thread_param) {
					t = new Thread(c, "controlerThread");
				} else {
					t = Thread_param;
				}

				if (!c.isRunning() && (null == run || run.isEmpty())) {
					session.setAttribute("contolerObject", c);
					session.setAttribute("thread", t);
					session.setAttribute("run", "end");
					pResp.setIntHeader("Refresh", 5);
					if (!t.isAlive())
						t.start();
				} else {
					if (c.isRunning()) {
						pResp.setIntHeader("Refresh", 5);
					} else {
						indexURLs = c.getIndexUrls();
						externalURLs = c.getExternalURls();
						System.out.println("top level link count"
								+ c.getToplevelPages());
						pReq.setAttribute("topLevelPage", c.getToplevelPages());
						session.setAttribute("run", null);
						session.setAttribute("contolerObject", null);
						session.setAttribute("thread", null);
					}

				}
				long totalPage = 0;
				if (null != indexURLs && !indexURLs.isEmpty()) {
					pReq.setAttribute("indexURLs", indexURLs);
					totalPage += indexURLs.size();
				}
				if (null != externalURLs && !externalURLs.isEmpty()) {
					pReq.setAttribute("externalURLs", externalURLs);
					totalPage += externalURLs.size();
				}
				pReq.setAttribute("totalPage", totalPage);
			} catch (Exception e) {
				e.printStackTrace();
			}

			pReq.setAttribute("a", pReq.getParameter("url"));

		}
		pReq.getRequestDispatcher("/index.jsp").forward(pReq, pResp);

		return;
	}
 
 /* (non-Javadoc)
  * handle the post method.
  * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
  */
 @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
doGet(req,resp);
}
}
