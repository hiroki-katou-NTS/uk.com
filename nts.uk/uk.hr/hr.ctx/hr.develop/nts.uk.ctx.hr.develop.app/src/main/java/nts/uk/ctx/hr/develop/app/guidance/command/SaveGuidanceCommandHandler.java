package nts.uk.ctx.hr.develop.app.guidance.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.guidance.dto.GuidanceDto;
import nts.uk.ctx.hr.develop.dom.guidance.GuidanceService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveGuidanceCommandHandler {

	@Inject
	private GuidanceService service; 
	
	public void updateGuideDispSetting(GuidanceDto guidance) {
		service.updateGuideDispSetting(AppContexts.user().companyId(), guidance.usageFlgCommon, guidance.guideMsgAreaRow, guidance.guideMsgMaxNum);
	}
}
