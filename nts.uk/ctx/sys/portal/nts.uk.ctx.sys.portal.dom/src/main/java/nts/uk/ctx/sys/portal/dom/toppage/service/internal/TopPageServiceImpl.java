package nts.uk.ctx.sys.portal.dom.toppage.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.dom.layout.service.LayoutService;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService;

/**
 * The Class TopPageServiceImpl.
 */
@Stateless
public class TopPageServiceImpl implements TopPageService {

	/** The top page repository. */
	@Inject
	TopPageRepository topPageRepository;

	@Inject
	LayoutRepository layoutRepository;
	
	@Inject
	LayoutService layoutService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService#copyTopPage(nts.
	 * uk.ctx.sys.portal.dom.toppage.TopPage, java.lang.String)
	 */
	@Override
	public void copyTopPage(TopPage topPage, String companyId) {
		String newLayoutId = layoutService.copyTopPageLayout(topPage.getLayoutId());
		TopPage newTopPage = TopPage.createFromJavaType(topPage.getCompanyId(), topPage.getTopPageCode().v(),
				newLayoutId, topPage.getTopPageName().v(), topPage.getLanguageNumber());
		topPageRepository.update(newTopPage);
	}

	@Override
	public void removeTopPage(String topPageCode, String companyId) {
		TopPage tp = topPageRepository.findByCode(companyId, topPageCode).get();
		topPageRepository.remove(companyId, topPageCode);
		if (!tp.getLayoutId().equals("")) {
			//TODO change to service of Lam san
			layoutRepository.remove(companyId, tp.getLayoutId());
		}
	}
}
