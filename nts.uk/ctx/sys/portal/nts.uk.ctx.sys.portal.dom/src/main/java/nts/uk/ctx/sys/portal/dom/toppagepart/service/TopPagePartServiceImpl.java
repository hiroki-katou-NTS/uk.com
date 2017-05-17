package nts.uk.ctx.sys.portal.dom.toppagepart.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.placement.service.PlacementService;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;

/**
 * @author HieuLT
 */
@Stateless
public class TopPagePartServiceImpl implements TopPagePartService{

	@Inject
	private TopPagePartRepository topPagePartRepository;

	@Inject
	private PlacementService placementService;
	
	@Override
	public boolean isExist(String companyID, String code, int type) {
		Optional<TopPagePart> topPage = topPagePartRepository.dataByCodeAndType(companyID, code, type);
		if(topPage.isPresent()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void deleteTopPagePart(String companyID, String topPagePartID) {
		Optional<TopPagePart> checkTopPagePart = topPagePartRepository.find(topPagePartID);
		if (checkTopPagePart.isPresent()) {
			topPagePartRepository.remove(companyID, topPagePartID);
			placementService.deletePlacementByTopPagePart(companyID, topPagePartID);
		}
	}
	
}
