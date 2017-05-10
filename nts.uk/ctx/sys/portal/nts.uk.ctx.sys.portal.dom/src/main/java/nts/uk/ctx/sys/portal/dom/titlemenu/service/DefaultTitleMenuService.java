package nts.uk.ctx.sys.portal.dom.titlemenu.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.portal.dom.layout.service.LayoutService;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;

@Stateless

/**
 * author hieult
 */

public class DefaultTitleMenuService implements TitleMenuService  {

	@Inject
	TitleMenuRepository titleMenuRepository;
	
	@Inject
	LayoutService layoutService;
	
	@Override
	public boolean isExist(String companyID, String titleMenuCD) {
		Optional<TitleMenu> titleMenu = titleMenuRepository.findByCode(companyID, titleMenuCD);
		return titleMenu.isPresent();
	}

	@Override
	public void deleteTitleMenu(String companyID, String titleMenuCD) {
		titleMenuRepository.remove(companyID, titleMenuCD);
		// TODO: Delete Layout
	}

	@Override
	public void copyTitleMenu(String companyID, String sourceTitleMenuCD, String targetTitleMenuCD, String targetTitleMenuName, Boolean overwrite) {
		TitleMenu oldTitleMenu = titleMenuRepository.findByCode(companyID, sourceTitleMenuCD).get();
		
		if (isExist(companyID, targetTitleMenuCD)) {
			if (overwrite)
				titleMenuRepository.remove(companyID, targetTitleMenuCD);
			else
				throw new BusinessException("Msg_3");
		}
		
		// Create new TitleMenu's Layout
		String newLayoutId = layoutService.copyTitleMenuLayout(oldTitleMenu.getLayoutID());
		// Create new TitleMenu				
		TitleMenu newTitleMenu = TitleMenu.createFromJavaType(
				oldTitleMenu.getCompanyID(), targetTitleMenuCD,
				targetTitleMenuName, newLayoutId);
		titleMenuRepository.add(newTitleMenu);
	}
	
}
