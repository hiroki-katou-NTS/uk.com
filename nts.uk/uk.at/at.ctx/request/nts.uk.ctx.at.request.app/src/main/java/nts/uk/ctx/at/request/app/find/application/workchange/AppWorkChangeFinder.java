package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeCommand;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDetailDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.WorkChangeCheckRegisterDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDetailOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.application.workchange.output.ChangeWkTypeTimeOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkChangeCheckRegOutput;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeFinder {
	
	@Inject
	private IAppWorkChangeSetRepository appWRep;
	
	@Inject 
	private IWorkChangeRegisterService workChangeRegisterService;
	
	@Inject
	private AppWorkChangeService appWorkChangeService;
	
	@Inject
	private IFactoryApplication iFactoryApplication;
	
	public AppWorkChangeSetDto findByCom(){
		String companyId = AppContexts.user().companyId();
		Optional<AppWorkChangeSet> app = appWRep.findWorkChangeSetByID(companyId);
		if(app.isPresent()){
			return AppWorkChangeSetDto.fromDomain(app.get());
		}
		return null;
	}
	
	public boolean isTimeRequired(String workTypeCD){
		return workChangeRegisterService.isTimeRequired(workTypeCD);
	}
	
	public AppWorkChangeDispInfoDto getStartNew(AppWorkChangeParam param) {
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = param.dateLst.stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList());
		AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService.getStartNew(companyID, param.empLst, dateLst);
		return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
	}
	
	public AppWorkChangeDispInfoDto changeAppDate(AppWorkChangeParam param) {
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = param.dateLst.stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList());
		AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService
				.changeAppDate(companyID, dateLst, param.appWorkChangeDispInfoDto.toDomain());
		return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
	}
	
	public AppWorkChangeDispInfoDto changeWorkSelection(AppWorkChangeParam param) {
		AppWorkChangeDispInfoDto result = param.appWorkChangeDispInfoDto;
		String companyID = AppContexts.user().companyId();
		ChangeWkTypeTimeOutput changeWkTypeTimeOutput = appWorkChangeService.changeWorkTypeWorkTime(
				companyID, 
				result.workTypeCD, 
				Optional.of(result.workTimeCD), 
				result.appWorkChangeSet.toDomain());
		result.setupType = changeWkTypeTimeOutput.getSetupType().value;
		PredetemineTimeSettingDto predetemineTimeSettingDto = null;
		if(changeWkTypeTimeOutput.getOpPredetemineTimeSetting().isPresent()) {
			changeWkTypeTimeOutput.getOpPredetemineTimeSetting().get().saveToMemento(predetemineTimeSettingDto);
		}
		result.predetemineTimeSetting = predetemineTimeSettingDto;
		return result;
	}
	
	public WorkChangeCheckRegisterDto checkBeforeRegister(AddAppWorkChangeCommand command) {
		AppWorkChangeDispInfo appWorkChangeDispInfo = command.getAppWorkChangeDispInfoDto().toDomain();
		// Application command
		CreateApplicationCommand appCommand = command.getApplication();
		// Work change command
		AppWorkChangeCommand workChangeCommand = command.getWorkChange();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		// 入力者 = 申請者
		// 申請者
		String applicantSID = appCommand.getApplicantSID() != null ? appCommand.getApplicantSID() : AppContexts.user().employeeId();
		
		AppTypeSetting appTypeSetting = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
				.getRequestSetting().getApplicationSetting().getListAppTypeSetting().stream()
				.filter(x -> x.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION).findFirst().get();
		
		String appReason = Strings.EMPTY;	
		String typicalReason = Strings.EMPTY;
		String displayReason = Strings.EMPTY;
		if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY){
			typicalReason += appCommand.getAppReasonID();
		}
		if(appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){
			if(Strings.isNotBlank(typicalReason)){
				displayReason += System.lineSeparator();
			}
			displayReason += appCommand.getApplicationReason();
		}
		ApplicationSetting applicationSetting = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
				.getRequestSetting().getApplicationSetting();
		if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY
			|| appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){
			if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
					&& Strings.isBlank(typicalReason+displayReason)) {
				throw new BusinessException("Msg_115");
			}
		}
		appReason = typicalReason + displayReason;
		
		// 申請
		Application_New app = iFactoryApplication.buildApplication(appID, appCommand.getStartDate(), appCommand.getPrePostAtr(), appReason, 
				appReason, ApplicationType.WORK_CHANGE_APPLICATION, appCommand.getStartDate(), appCommand.getEndDate(), applicantSID);
					
		// 勤務変更申請
		AppWorkChange workChangeDomain = AppWorkChange.createFromJavaType(
				companyId, 
				appID,
				workChangeCommand.getWorkTypeCd(), 
				workChangeCommand.getWorkTimeCd(),
				workChangeCommand.getExcludeHolidayAtr(), 
				workChangeCommand.getWorkChangeAtr(),
				workChangeCommand.getGoWorkAtr1(), 
				workChangeCommand.getBackHomeAtr1(),
				workChangeCommand.getBreakTimeStart1(), 
				workChangeCommand.getBreakTimeEnd1(),
				workChangeCommand.getWorkTimeStart1(), 
				workChangeCommand.getWorkTimeEnd1(),
				workChangeCommand.getWorkTimeStart2(), 
				workChangeCommand.getWorkTimeEnd2(),
				workChangeCommand.getGoWorkAtr2(), 
				workChangeCommand.getBackHomeAtr2());
		
		WorkChangeCheckRegOutput output = appWorkChangeService.checkBeforeRegister(
				companyId, 
				appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getErrorFlag(), 
				app, 
				workChangeDomain);
		
		return WorkChangeCheckRegisterDto.fromDomain(output);
	}
	
	public AppWorkChangeDetailDto startDetailScreen(String appID) {
		String companyID = AppContexts.user().companyId();
		AppWorkChangeDetailOutput output = appWorkChangeService.startDetailScreen(companyID, appID);
		return AppWorkChangeDetailDto.fromDomain(output);
	}
}
