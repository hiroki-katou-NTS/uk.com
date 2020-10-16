package nts.uk.ctx.sys.portal.app.find.toppagepart;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementPartDto;
import nts.uk.ctx.sys.portal.dom.flowmenu.deprecated.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Stateless
public class PortalTopPagePartFinder {
	
	@Inject
	private TopPagePartService topPagePartSerivce;
	
	@Inject
	private TopPagePartRepository topPagePartRepository;
	
	@Inject
	private FlowMenuRepository flowMenuRepository;
	
	/**
	 * Find all TopPagePart and TopPagePartType
	 * @return ActiveTopPagePartDto
	 */
	public ActiveTopPagePartDto findAll(int pgType) {
		String companyID = AppContexts.user().companyId();
		
		// List TopPage Part Type
		List<EnumConstant> usingTopPagePartType = topPagePartSerivce.getAllActiveTopPagePartType(companyID, EnumAdaptor.valueOf(pgType, PGType.class));
		
		//List TopPage Part
		List<TopPagePartDto> listTopPagePart = topPagePartSerivce.getAllActiveTopPagePart(companyID, EnumAdaptor.valueOf(pgType, PGType.class))
				.stream().map(topPagePart -> TopPagePartDto.fromDomain(topPagePart))
				.collect(Collectors.toList());
     
		// Build Dto
		ActiveTopPagePartDto activeTopPagePartDto = new ActiveTopPagePartDto(usingTopPagePartType, listTopPagePart);
		return activeTopPagePartDto;
	}
	
	/** Find a PlacementPart with given ID */
	public PlacementPartDto findPlacementPartByID(String topPagePartID) {
		String companyID = AppContexts.user().companyId();
		
		val optTopPagePart = topPagePartRepository.findByKey(companyID,topPagePartID);
		if (!optTopPagePart.isPresent())
			throw new RuntimeException("Can't find TopPagePart with id: " + topPagePartID);
		val topPagePart = optTopPagePart.get();
		
		if (topPagePart.isFlowMenu()) {
			val optFlowMenu = flowMenuRepository.findByCode(companyID, topPagePartID);
			if (!optFlowMenu.isPresent())
				throw new RuntimeException("Can't find FlowMenu with id: " + topPagePartID);
			return PlacementPartDto.createFromTopPagePart(optFlowMenu.get());
		} else if (topPagePart.isDashBoard()) {
			throw new RuntimeException("Not implement yet DashBoard");
		} else if (topPagePart.isOptionalWidget()) {
			return PlacementPartDto.createFromTopPagePart(topPagePart);
		} else if (topPagePart.isStandardWidget()) {
			return PlacementPartDto.createFromTopPagePart(topPagePart);
		} else {
			throw new RuntimeException("Invalid TopPagePart type: " + topPagePart.getType() );
		}
	}
}