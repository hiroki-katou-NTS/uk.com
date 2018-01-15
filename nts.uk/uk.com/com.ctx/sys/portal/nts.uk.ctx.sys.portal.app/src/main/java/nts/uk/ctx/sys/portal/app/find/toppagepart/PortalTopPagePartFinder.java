package nts.uk.ctx.sys.portal.app.find.toppagepart;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Stateless
public class PortalTopPagePartFinder {
	
	@Inject
	private TopPagePartService topPagePartSerivce;
	
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

}