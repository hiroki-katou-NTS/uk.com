package nts.uk.ctx.sys.portal.dom.flowmenu.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;

/**
 * @author HieuLT
 */
@Stateless
public class DefaultFlowMenuService implements FlowMenuService {

	@Inject
	private FlowMenuRepository flowMenuRepository;
	
	@Inject
	private TopPagePartService topPagePartService;
	
	@Override
	public boolean isExist(String companyID, String toppagePartID) {
		Optional<FlowMenu> flowMenu = flowMenuRepository.findByCode(companyID, toppagePartID);
		return flowMenu.isPresent();
	}

	@Override
	public void deleteFlowMenu(String companyID, String toppagePartID) {
		if (isExist(companyID, toppagePartID)) {
			flowMenuRepository.remove(companyID, toppagePartID);
			topPagePartService.deleteTopPagePart(companyID, toppagePartID);
		}
	}

}