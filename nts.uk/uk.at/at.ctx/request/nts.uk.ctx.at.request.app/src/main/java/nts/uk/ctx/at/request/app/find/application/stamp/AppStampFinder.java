package nts.uk.ctx.at.request.app.find.application.stamp;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.NewAppStampDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.stamp.dto.StampRequestSettingDto;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampDetailDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampNewDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampSetOutput;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampFinder {
	
	@Inject
	private AppStampNewDomainService appStampNewDomainService;
	
	@Inject
	private AppStampDetailDomainService appStampDetailDomainService;
	
	public NewAppStampDto newAppStampPreProcess() {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		AppStampSetOutput appStampSetOutput = this.appStampNewDomainService.appStampPreProcess(companyID, employeeID, GeneralDate.today());
		NewAppStampDto newAppStampDto = new NewAppStampDto();
		newAppStampDto.setStampRequestSettingDto(new StampRequestSettingDto(
				companyID, 
				appStampSetOutput.getStampRequestSetting().getTopComment(), 
				appStampSetOutput.getStampRequestSetting().getTopCommentFontColor(), 
				appStampSetOutput.getStampRequestSetting().getTopCommentFontWeight(), 
				appStampSetOutput.getStampRequestSetting().getBottomComment(), 
				appStampSetOutput.getStampRequestSetting().getBottomCommentFontColor(), 
				appStampSetOutput.getStampRequestSetting().getBottomCommentFontWeight(), 
				appStampSetOutput.getStampRequestSetting().getResultDisp(), 
				appStampSetOutput.getStampRequestSetting().getSupFrameDispNO(), 
				appStampSetOutput.getStampRequestSetting().getStampPlaceDisp(), 
				appStampSetOutput.getStampRequestSetting().getStampAtr_Work_Disp(), 
				appStampSetOutput.getStampRequestSetting().getStampAtr_GoOut_Disp(), 
				appStampSetOutput.getStampRequestSetting().getStampAtr_Care_Disp(), 
				appStampSetOutput.getStampRequestSetting().getStampAtr_Sup_Disp(), 
				appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Private_Disp(), 
				appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Public_Disp(), 
				appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Compensation_Disp(), 
				appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Union_Disp()));
		newAppStampDto.setApplicationReasonDtos(appStampSetOutput.getApplicationReasons().stream()
				.map(x -> new ApplicationReasonDto(
						companyID, 
						x.getAppType().value, 
						x.getReasonID(), 
						x.getDispOrder(), 
						x.getReasonTemp(), 
						x.getDefaultFlg().value))
				.collect(Collectors.toList()));
		return newAppStampDto;
	}
	
}
