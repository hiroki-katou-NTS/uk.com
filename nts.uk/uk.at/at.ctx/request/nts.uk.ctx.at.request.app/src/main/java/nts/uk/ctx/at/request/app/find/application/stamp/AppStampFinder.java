package nts.uk.ctx.at.request.app.find.application.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampNewPreDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampSetDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.StampCombinationDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingDto;
import nts.uk.ctx.at.request.app.find.setting.stamp.dto.StampRequestSettingDto;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCommonDefaultImpl;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCommonDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampNewDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
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
	private AppStampCommonDomainService appStampCommonDomainService;
	
	public AppStampNewPreDto newAppStampPreProcess() {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		System.out.println(employeeID);
		AppStampNewPreOutput appStampNewPreOutput = this.appStampNewDomainService.appStampPreProcess(companyID, employeeID, GeneralDate.today());
		AppStampNewPreDto appStampNewPreDto = new AppStampNewPreDto();
		appStampNewPreDto.appCommonSettingDto = new AppCommonSettingDto(
				GeneralDate.today().toString("yyyy/MM/dd"), 
				null, 
				null, 
				appStampNewPreOutput.appCommonSettingOutput.appTypeDiscreteSettings.stream().map(x -> AppTypeDiscreteSettingDto.convertToDto(x)).collect(Collectors.toList()), 
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
		appStampNewPreDto.companyID = companyID;
		appStampNewPreDto.employeeID = employeeID;
		appStampNewPreDto.employeeName = appStampNewPreOutput.employeeName;
		return appStampNewPreDto;
	}
	
	public List<StampCombinationDto> getStampCombinationAtr(){
		List<StampCombinationDto> stampCombinationDtos = new ArrayList<>();
		for(AppStampCombinationAtr a : AppStampCombinationAtr.values()){
			stampCombinationDtos.add(new StampCombinationDto(a.value, a.name));
		}
		return stampCombinationDtos;
	}
	
	public AppStampDto getAppStampByID(String appID){
		String companyID = AppContexts.user().companyId();
		AppStamp appStamp = appStampCommonDomainService.findByID(companyID, appID);
		String employee = appStampCommonDomainService.getEmployeeName(appStamp.getApplication_New().getEmployeeID());
		return AppStampDto.convertToDto(appStamp, employee);
	}
}
