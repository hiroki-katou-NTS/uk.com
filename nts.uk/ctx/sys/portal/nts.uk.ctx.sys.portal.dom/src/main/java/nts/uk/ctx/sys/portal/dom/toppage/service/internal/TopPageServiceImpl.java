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
//		// remove layout of duplicate
//		Optional<Layout> layout = layoutRepository.find(topPage.getLayoutId());
//		if (layout.isPresent()) {
//			layoutRepository.remove(companyId, topPage.getLayoutId());
//		}
		// TODO dang ki layout moi
		String newLayoutId = layoutService.copyTopPageLayout(topPage.getLayoutId());
		TopPage newTopPage = TopPage.createFromJavaType(topPage.getCompanyId(), topPage.getTopPageCode().v(),
				newLayoutId, topPage.getTopPageName().v(), topPage.getLanguageNumber());

		// dang ki tp moi
		topPageRepository.update(newTopPage);
				
		// layoutService.registry();
		// layoutRepository.findByType(companyId,PGType.TopPage.value);

	}
}
