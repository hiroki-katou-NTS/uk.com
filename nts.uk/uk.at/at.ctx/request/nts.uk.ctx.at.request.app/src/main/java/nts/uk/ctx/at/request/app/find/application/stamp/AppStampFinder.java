package nts.uk.ctx.at.request.app.find.application.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampNewPreDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampSetDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.StampCombinationDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.stamp.dto.StampRequestSettingDto;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampDetailDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampNewDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.StampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampNewPreOutput;
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
	
	public AppStampNewPreDto newAppStampPreProcess() {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		AppStampNewPreOutput appStampNewPreOutput = this.appStampNewDomainService.appStampPreProcess(companyID, employeeID, GeneralDate.today());
		AppStampNewPreDto appStampNewPreDto = new AppStampNewPreDto();
		appStampNewPreDto.appCommonSettingDto = new AppCommonSettingDto(
				GeneralDate.today().toString("yyyy/MM/dd"), 
				null, 
				null, 
				null, 
				null);
		appStampNewPreDto.appStampSetDto = new AppStampSetDto(
				new StampRequestSettingDto(
						companyID, 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getTopComment(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getTopCommentFontColor(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getTopCommentFontWeight(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getBottomComment(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getBottomCommentFontColor(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getBottomCommentFontWeight(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getResultDisp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getSupFrameDispNO(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampPlaceDisp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampAtr_Work_Disp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampAtr_GoOut_Disp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampAtr_Care_Disp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampAtr_Sup_Disp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Private_Disp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Public_Disp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Compensation_Disp(), 
						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampGoOutAtr_Union_Disp()),  
				appStampNewPreOutput.appStampSetOutput.getApplicationReasons().stream()
					.map(x -> new ApplicationReasonDto(
							x.getCompanyId(),
							x.getAppType().value,
							x.getReasonID(),
							x.getDispOrder(),
							x.getReasonTemp(),
							x.getDefaultFlg().value))
					.collect(Collectors.toList()));
		return appStampNewPreDto;
	}
	
	public List<StampCombinationDto> getStampCombinationAtr(){
		List<StampCombinationDto> stampCombinationDtos = new ArrayList<>();
		for(StampCombinationAtr a : StampCombinationAtr.values()){
			stampCombinationDtos.add(new StampCombinationDto(a.value, a.name));
		}
		return stampCombinationDtos;
	}
}
