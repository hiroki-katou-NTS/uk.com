package nts.uk.ctx.sys.portal.dom.toppage.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService#copyTopPage(nts.
	 * uk.ctx.sys.portal.dom.toppage.TopPage, java.lang.String)
	 */
	@Override
	public void copyTopPage(TopPage topPage, String copyCode,String copyLayoutId, String companyId) {
		String topPageCode = topPage.getTopPageCode().v();
		// remove duplicate top page
		topPageRepository.remove(companyId, topPageCode);
		// remove layout of duplicate
		Optional<Layout> layout = layoutRepository.find(copyLayoutId);
		if (layout.isPresent()) {
			layoutRepository.remove(companyId, copyLayoutId);
		}
		
		// dang ki tp moi
		topPageRepository.add(topPage);

		// TODO dang ki layout moi
		// layoutService.registry();
		// layoutRepository.findByType(companyId,PGType.TopPage.value);

	}
}
