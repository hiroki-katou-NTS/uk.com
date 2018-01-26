package nts.uk.ctx.sys.portal.dom.titlemenu.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private TitleMenuRepository titleMenuRepository;
	
	@Inject
	private LayoutService layoutService;
	

	
	@Override
	public boolean isExist(String companyID, String titleMenuCD) {
		List<TitleMenu> lstTitleMenu = titleMenuRepository.findAll(companyID);
		List<TitleMenu> lstTmp = lstTitleMenu.stream().filter(x -> x.getTitleMenuCD().toString().equals(titleMenuCD)).collect(Collectors.toList());
		return !lstTmp.isEmpty();
	}

	@Override
	public void deleteTitleMenu(String companyID, String titleMenuCD) {
		Optional<TitleMenu> checkTitleMenu = titleMenuRepository.findByCode(companyID, titleMenuCD);
		if (checkTitleMenu.isPresent()){
			titleMenuRepository.remove(companyID, titleMenuCD);
			layoutService.deleteLayout(companyID, checkTitleMenu.get().getLayoutID());
		}
	}

	@Override
	public void copyTitleMenu(String companyID, String sourceTitleMenuCD, String targetTitleMenuCD, String targetTitleMenuName, Boolean overwrite) {
		TitleMenu oldTitleMenu = titleMenuRepository.findByCode(companyID, sourceTitleMenuCD).get();
		if(isExist(companyID, targetTitleMenuCD)) {
			if(overwrite) {
				titleMenuRepository.remove(companyID, targetTitleMenuCD);
			}else {
				throw new BusinessException("Msg_3");
			}
		}
		// Create new TitleMenu's Layout
		String newLayoutId = layoutService.copyTitleMenuLayout(oldTitleMenu.getLayoutID());
		// Create new TitleMenu				
		TitleMenu newTitleMenu = TitleMenu.createFromJavaType(
				oldTitleMenu.getCompanyID(), targetTitleMenuCD,
				targetTitleMenuName, newLayoutId);
		titleMenuRepository.add(newTitleMenu);
	}

	@Override
	public void createTitleMenu(TitleMenu titleMenu) {
		if (!isExist(titleMenu.getCompanyID(), titleMenu.getTitleMenuCD().v()))
			titleMenuRepository.add(titleMenu);
		else
			throw new BusinessException("Msg_3");
	}
	
}
