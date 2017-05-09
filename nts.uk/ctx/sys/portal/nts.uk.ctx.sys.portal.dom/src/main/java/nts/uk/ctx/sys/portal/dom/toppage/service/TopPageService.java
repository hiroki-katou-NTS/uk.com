package nts.uk.ctx.sys.portal.dom.toppage.service;

import nts.uk.ctx.sys.portal.dom.toppage.TopPage;

/**
 * The Interface TopPageService.
 */
public interface TopPageService {
	
	/**
	 * Copy top page.
	 */
	void copyTopPage(TopPage topPage,String companyId);
}
