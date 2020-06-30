package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeDispInfoCmd;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDetailDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.WorkChangeCheckRegisterDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.DisabledSegment_New;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.PrePostAtr_Old;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily_New;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;
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
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
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
				.changeAppDate(companyID, dateLst, param.appWorkChangeDispInfoCmd.toDomain());
		return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
	}
	
	public AppWorkChangeDispInfoDto changeWorkSelection(AppWorkChangeParam param) {
		AppWorkChangeDispInfoCmd cmd = param.appWorkChangeDispInfoCmd;
		AppWorkChangeDispInfoDto result = new AppWorkChangeDispInfoDto(
				cmd.appDispInfoStartupOutput, 
				cmd.appWorkChangeSet, 
				cmd.workTypeLst, 
				cmd.setupType, 
				null, 
				cmd.workTypeCD, 
				cmd.workTimeCD);
		String companyID = AppContexts.user().companyId();
		ChangeWkTypeTimeOutput changeWkTypeTimeOutput = appWorkChangeService.changeWorkTypeWorkTime(
				companyID, 
				cmd.workTypeCD, 
				Optional.of(cmd.workTimeCD), 
				cmd.appWorkChangeSet.toDomain());
		result.setupType = changeWkTypeTimeOutput.getSetupType().value;
		PredetemineTimeSettingDto predetemineTimeSettingDto = new PredetemineTimeSettingDto();
		if(changeWkTypeTimeOutput.getOpPredetemineTimeSetting().isPresent()) {
			changeWkTypeTimeOutput.getOpPredetemineTimeSetting().get().saveToMemento(predetemineTimeSettingDto);
		}
		result.predetemineTimeSetting = predetemineTimeSettingDto;
		return result;
	}
	
	public WorkChangeCheckRegisterDto checkBeforeRegister(AddAppWorkChangeCommand command) {
		AppWorkChangeDispInfo appWorkChangeDispInfo = command.getAppWorkChangeDispInfoCmd().toDomain();
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
		String applicantSID = Strings.isNotBlank(appCommand.getApplicantSID()) ? appCommand.getApplicantSID() : AppContexts.user().employeeId();
		
		AppTypeSetting appTypeSetting = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
				.getRequestSetting().getApplicationSetting().getListAppTypeSetting().stream()
				.filter(x -> x.getAppType() == ApplicationType_Old.WORK_CHANGE_APPLICATION).findFirst().get();
		
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
				appReason, ApplicationType_Old.WORK_CHANGE_APPLICATION, appCommand.getStartDate(), appCommand.getEndDate(), applicantSID);
					
		// 勤務変更申請
		AppWorkChange_Old workChangeDomain = AppWorkChange_Old.createFromJavaType(
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
	
	public void checkBeforeUpdate(AddAppWorkChangeCommand command) {
		// Command data
		CreateApplicationCommand appCommand = command.getApplication();
		AppWorkChangeCommand workChangeCommand = command.getWorkChange();
		AppWorkChangeDispInfo appWorkChangeDispInfo = command.getAppWorkChangeDispInfoCmd().toDomain();
		
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = appCommand.getApplicationID();
		
		OutputMode outputMode = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getOutputMode();
		String appReason = applicationRepository.findByID(companyId, appID).get().getAppReason().v();
		AppTypeSetting appTypeSetting = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
				.getRequestSetting().getApplicationSetting().getListAppTypeSetting().stream()
				.filter(x -> x.getAppType() == ApplicationType_Old.WORK_CHANGE_APPLICATION).findFirst().get();
		if(outputMode==OutputMode.EDITMODE){
			String typicalReason = Strings.EMPTY;
			String displayReason = Strings.EMPTY;
			boolean isFixedDisplay = appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY;
			boolean isTextDisplay = appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY;
			if(isFixedDisplay){
				typicalReason += appCommand.getAppReasonID();
			}
			//màn B có cách lấy reason khác
			if(isTextDisplay){
				if(Strings.isNotBlank(typicalReason)){
					displayReason += System.lineSeparator();
				}
				displayReason += appCommand.getApplicationReason();
			}else{
				if(Strings.isBlank(typicalReason)){
					displayReason = appReason;
				}
			}
			ApplicationSetting applicationSetting = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
					.getRequestSetting().getApplicationSetting();
			if(isFixedDisplay || isTextDisplay){
				if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
						&& Strings.isBlank(typicalReason+displayReason)) {
					throw new BusinessException("Msg_115");
				}
				appReason = typicalReason + displayReason;
			}
		}
		
		// 申請
		Application_New updateApp = new Application_New(
				appCommand.getVersion(), 
				companyId, 
				appID,
				EnumAdaptor.valueOf(appCommand.getPrePostAtr(), PrePostAtr_Old.class), 
				GeneralDateTime.now(), 
				appCommand.getEnteredPersonSID(), 
				new AppReason(Strings.EMPTY), 
				appCommand.getStartDate(),
				new AppReason(appReason),
				ApplicationType_Old.WORK_CHANGE_APPLICATION, 
				appCommand.getApplicantSID(),
				Optional.of(appCommand.getStartDate()),
				Optional.of(appCommand.getEndDate()), 
				ReflectionInformation_New.builder()
						.stateReflection(
								EnumAdaptor.valueOf(appCommand.getReflectPlanState(), ReflectedState_New.class))
						.stateReflectionReal(
								EnumAdaptor.valueOf(appCommand.getReflectPerState(), ReflectedState_New.class))
						.forcedReflection(
								EnumAdaptor.valueOf(appCommand.getReflectPlanEnforce(), DisabledSegment_New.class))
						.forcedReflectionReal(
								EnumAdaptor.valueOf(appCommand.getReflectPerEnforce(), DisabledSegment_New.class))
						.notReason(Optional.ofNullable(appCommand.getReflectPlanScheReason())
								.map(x -> EnumAdaptor.valueOf(x, ReasonNotReflect_New.class)))
						.notReasonReal(Optional.ofNullable(appCommand.getReflectPerScheReason())
								.map(x -> EnumAdaptor.valueOf(x, ReasonNotReflectDaily_New.class)))
						.dateTimeReflection(Optional
								.ofNullable(appCommand.getReflectPlanTime() == null ? null : GeneralDateTime.legacyDateTime(appCommand.getReflectPlanTime().date())))
						.dateTimeReflectionReal(Optional
								.ofNullable(appCommand.getReflectPerTime() == null ? null : GeneralDateTime.legacyDateTime(appCommand.getReflectPerTime().date())))
						.build());
		// 勤務変更申請
		AppWorkChange_Old workChangeDomain = AppWorkChange_Old.createFromJavaType(workChangeCommand.getCid(),
				workChangeCommand.getAppId(), workChangeCommand.getWorkTypeCd(), workChangeCommand.getWorkTimeCd(),
				workChangeCommand.getExcludeHolidayAtr(), workChangeCommand.getWorkChangeAtr(),
				workChangeCommand.getGoWorkAtr1(), workChangeCommand.getBackHomeAtr1(),
				workChangeCommand.getBreakTimeStart1(), workChangeCommand.getBreakTimeEnd1(),
				workChangeCommand.getWorkTimeStart1(), workChangeCommand.getWorkTimeEnd1(),
				workChangeCommand.getWorkTimeStart2(), workChangeCommand.getWorkTimeEnd2(),
				workChangeCommand.getGoWorkAtr2(), workChangeCommand.getBackHomeAtr2());
		//OptimisticLock
		workChangeDomain.setVersion(appCommand.getVersion());
		
		appWorkChangeService.checkBeforeUpdate(companyId, updateApp, workChangeDomain, false);
	}
}
