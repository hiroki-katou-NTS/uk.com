package nts.uk.ctx.sys.portal.dom.flowmenu.deprecated.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.flowmenu.deprecated.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.deprecated.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;

/**
 * @author HieuLT
 */
@Stateless
public class DefaultFlowMenuService implements FlowMenuService {

	@Inject
	private FlowMenuRepository flowMenuRepository;
	
	@Inject
	private TopPagePartRepository topPagePartRepository;
	
	@Inject
	private TopPagePartService topPagePartService;
	
	@Inject
	private MyPageSettingRepository	myPageSettingRepository;
	
	@Override
	public boolean isExist(String companyID, String toppagePartID) {
		Optional<FlowMenu> flowMenu = flowMenuRepository.findByCode(companyID, toppagePartID);
		return flowMenu.isPresent();
	}

	@Override
	public void createFlowMenu(FlowMenu flowMenu) {
		if (topPagePartService.isExist(flowMenu.getCompanyID(), flowMenu.getCode().v(), TopPagePartType.FlowMenu.value)) {
			throw new BusinessException("Msg_3");
		}
		flowMenuRepository.add(flowMenu);
		topPagePartRepository.add(flowMenu);
		// TopPagePart Setting
		TopPagePartUseSetting topPagePartUseSetting = TopPagePartUseSetting.createFromJavaType(
				flowMenu.getCompanyID(), flowMenu.getToppagePartID(),
				flowMenu.getCode().v(), flowMenu.getName().v(),
				UseDivision.Use.value, TopPagePartType.FlowMenu.value);
		myPageSettingRepository.addTopPagePartUseSetting(topPagePartUseSetting);
	}
	

	@Override
	public void updateFlowMenu(FlowMenu flowMenu) {
		flowMenuRepository.update(flowMenu);
		topPagePartRepository.update(flowMenu);
	}

	@Override
	public void deleteFlowMenu(String companyID, String toppagePartID ) {
		if (isExist(companyID, toppagePartID)) {
			flowMenuRepository.remove(companyID, toppagePartID);
			topPagePartService.deleteTopPagePart(companyID, toppagePartID);
			
		}
	}

}