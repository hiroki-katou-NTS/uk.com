package nts.uk.ctx.hr.develop.app.guidance.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.guidance.dto.GuidanceDto;
import nts.uk.ctx.hr.develop.app.guidance.dto.GuideMsgDto;
import nts.uk.ctx.hr.develop.dom.guidance.GuidanceService;
import nts.uk.ctx.hr.develop.dom.guidance.GuideMsgRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateGuidanceCommandHandler {

	@Inject
	private GuidanceService service; 
	
	@Inject
	private GuideMsgRepository guideMsgRepo; 
	
	public void updateGuideDispSetting(GuidanceDto guidance) {
		service.updateGuideDispSetting(AppContexts.user().companyId(), guidance.usageFlgCommon, guidance.guideMsgAreaRow, guidance.guideMsgMaxNum);
	}
	
	public void updateGuideMsg(GuideMsgDto guideMsg) {
		guideMsgRepo.updateGuideMsg(guideMsg.guideMsgId, guideMsg.guideMsg, guideMsg.usageFlgByScreen.equals("使用する"));
	}
}
