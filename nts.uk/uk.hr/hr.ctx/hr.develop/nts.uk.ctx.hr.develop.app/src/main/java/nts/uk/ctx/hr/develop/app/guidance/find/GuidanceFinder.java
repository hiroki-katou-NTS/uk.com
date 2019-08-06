package nts.uk.ctx.hr.develop.app.guidance.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.guidance.dto.GuidanceDto;
import nts.uk.ctx.hr.develop.dom.guidance.GuidanceService;

@Stateless
public class GuidanceFinder {

	@Inject
	private GuidanceService guidanceService;
	
	public GuidanceDto getGuideDispSetting(String companyId) {
		return new GuidanceDto(guidanceService.getGuideDispSetting(companyId));
	}
	
}
