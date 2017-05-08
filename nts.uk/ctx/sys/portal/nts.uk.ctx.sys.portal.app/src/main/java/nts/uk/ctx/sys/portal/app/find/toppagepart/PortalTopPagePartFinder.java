package nts.uk.ctx.sys.portal.app.find.toppagepart;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Stateless
public class PortalTopPagePartFinder {
	
	@Inject
	private TopPagePartRepository topPagePartRepository;
	
	/**
	 * Find all TopPagePart and TopPagePartType
	 * @return List PlaceTopPagePartDto
	 */
	public ActiveTopPagePartDto findAll() {
		// Company ID
		String companyID = AppContexts.user().companyID();
		
		// List TopPage Part Type
		List<EnumConstant> listTopPagePartType = EnumAdaptor.convertToValueNameList(TopPagePartType.class);
		
		//List TopPage Part
		List<TopPagePartDto> listTopPagePart = topPagePartRepository.findAll(companyID).stream()
				.map(topPagePart -> TopPagePartDto.fromDomain(topPagePart))
				.collect(Collectors.toList());
		
		// Build Dto
		ActiveTopPagePartDto activeTopPagePartDto = new ActiveTopPagePartDto(listTopPagePartType, listTopPagePart);
		return activeTopPagePartDto;
	}

}
