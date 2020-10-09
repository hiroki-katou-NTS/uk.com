package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandCheck;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandPC;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDetailDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto_Old;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.WorkChangeCheckRegisterDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeSetRepository;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkChangeCheckRegOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet_Old;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeFinder {

	@Inject
	private IAppWorkChangeSetRepository appWRep;

	@Inject
	private AppWorkChangeSetRepository appWorkChangeSetRepo;

	@Inject
	private IWorkChangeRegisterService workChangeRegisterService;

	@Inject
	private AppWorkChangeService appWorkChangeService;

	public AppWorkChangeSetDto_Old findByCom() {
		String companyId = AppContexts.user().companyId();
		Optional<AppWorkChangeSet_Old> app = appWRep.findWorkChangeSetByID(companyId);
		if (app.isPresent()) {
			return AppWorkChangeSetDto_Old.fromDomain(app.get());
		}
		return null;
	}

	public AppWorkChangeSetDto findByCompany() {
		String companyId = AppContexts.user().companyId();
		Optional<AppWorkChangeSet> appWorkChangeSet = appWorkChangeSetRepo.findByCompanyId(companyId);
		if (appWorkChangeSet.isPresent()) {
			AppWorkChangeSetDto.fromDomain(appWorkChangeSet.get());
		}
		return null;
	}

	public boolean isTimeRequired(String workTypeCD) {
		return workChangeRegisterService.isTimeRequired(workTypeCD);
	}

	public AppWorkChangeDispInfoDto getStartNew(AppWorkChangeParamPC param) {
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = param.getDateLst().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
				.collect(Collectors.toList());
		AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService.getStartNew(
				companyID, 
				param.getEmpLst(), 
				dateLst, 
				param.getAppDispInfoStartupOutput().toDomain());
		return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
	}

	public AppWorkChangeDispInfoDto changeAppDate(AppWorkChangeAppdateDto param) {
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = param.getDateLst().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
				.collect(Collectors.toList());
		AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService.changeAppDate(
		        companyID, 
		        dateLst, 
		        param.getAppWorkChangeDispInfo().toDomain());
		return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
	}

	public AppWorkChangeDispInfoDto_Old changeWorkSelection(AppWorkChangeParamPC param) {
		// error EA refactor 4
		/*
		 * AppWorkChangeDispInfoCmd cmd = param.appWorkChangeDispInfoCmd;
		 * AppWorkChangeDispInfoDto result = new AppWorkChangeDispInfoDto(
		 * cmd.appDispInfoStartupOutput, cmd.appWorkChangeSet, cmd.workTypeLst,
		 * cmd.setupType, null, cmd.workTypeCD, cmd.workTimeCD); String companyID =
		 * AppContexts.user().companyId(); ChangeWkTypeTimeOutput changeWkTypeTimeOutput
		 * = appWorkChangeService.changeWorkTypeWorkTime( companyID, cmd.workTypeCD,
		 * Optional.of(cmd.workTimeCD), cmd.appWorkChangeSet.toDomain());
		 * result.setupType = changeWkTypeTimeOutput.getSetupType().value;
		 * PredetemineTimeSettingDto predetemineTimeSettingDto = new
		 * PredetemineTimeSettingDto();
		 * if(changeWkTypeTimeOutput.getOpPredetemineTimeSetting().isPresent()) {
		 * changeWkTypeTimeOutput.getOpPredetemineTimeSetting().get().saveToMemento(
		 * predetemineTimeSettingDto); } result.predetemineTimeSetting =
		 * predetemineTimeSettingDto; return result;
		 */
		return null;
	}

	public WorkChangeCheckRegisterDto checkBeforeRegister(AddAppWorkChangeCommandPC command) {
		// error EA refactor 4
		/*
		 * AppWorkChangeDispInfo appWorkChangeDispInfo =
		 * command.getAppWorkChangeDispInfoCmd().toDomain(); // Application command
		 * CreateApplicationCommand appCommand = command.getApplication(); // Work
		 * change command AppWorkChangeCommand workChangeCommand =
		 * command.getWorkChange(); // 会社ID String companyId =
		 * AppContexts.user().companyId(); // 申請ID String appID =
		 * IdentifierUtil.randomUniqueId(); // 入力者 = 申請者 // 申請者 String applicantSID =
		 * Strings.isNotBlank(appCommand.getApplicantSID()) ?
		 * appCommand.getApplicantSID() : AppContexts.user().employeeId();
		 * 
		 * AppTypeSetting appTypeSetting =
		 * appWorkChangeDispInfo.getAppDispInfoStartupOutput().
		 * getAppDispInfoNoDateOutput()
		 * .getRequestSetting().getApplicationSetting().getListAppTypeSetting().stream()
		 * .filter(x -> x.getAppType() ==
		 * ApplicationType_Old.WORK_CHANGE_APPLICATION).findFirst().get();
		 * 
		 * String appReason = Strings.EMPTY; String typicalReason = Strings.EMPTY;
		 * String displayReason = Strings.EMPTY;
		 * if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY){
		 * typicalReason += appCommand.getAppReasonID(); }
		 * if(appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){
		 * if(Strings.isNotBlank(typicalReason)){ displayReason +=
		 * System.lineSeparator(); } displayReason += appCommand.getApplicationReason();
		 * } ApplicationSetting applicationSetting =
		 * appWorkChangeDispInfo.getAppDispInfoStartupOutput().
		 * getAppDispInfoNoDateOutput() .getRequestSetting().getApplicationSetting();
		 * if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY ||
		 * appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){ if
		 * (applicationSetting.getAppLimitSetting().getRequiredAppReason() &&
		 * Strings.isBlank(typicalReason+displayReason)) { throw new
		 * BusinessException("Msg_115"); } } appReason = typicalReason + displayReason;
		 * 
		 * // 申請 Application_New app = iFactoryApplication.buildApplication(appID,
		 * appCommand.getStartDate(), appCommand.getPrePostAtr(), appReason, appReason,
		 * ApplicationType_Old.WORK_CHANGE_APPLICATION, appCommand.getStartDate(),
		 * appCommand.getEndDate(), applicantSID);
		 * 
		 * // 勤務変更申請 AppWorkChange_Old workChangeDomain =
		 * AppWorkChange_Old.createFromJavaType( companyId, appID,
		 * workChangeCommand.getWorkTypeCd(), workChangeCommand.getWorkTimeCd(),
		 * workChangeCommand.getExcludeHolidayAtr(),
		 * workChangeCommand.getWorkChangeAtr(), workChangeCommand.getGoWorkAtr1(),
		 * workChangeCommand.getBackHomeAtr1(), workChangeCommand.getBreakTimeStart1(),
		 * workChangeCommand.getBreakTimeEnd1(), workChangeCommand.getWorkTimeStart1(),
		 * workChangeCommand.getWorkTimeEnd1(), workChangeCommand.getWorkTimeStart2(),
		 * workChangeCommand.getWorkTimeEnd2(), workChangeCommand.getGoWorkAtr2(),
		 * workChangeCommand.getBackHomeAtr2());
		 * 
		 * WorkChangeCheckRegOutput output = appWorkChangeService.checkBeforeRegister(
		 * companyId, appWorkChangeDispInfo.getAppDispInfoStartupOutput().
		 * getAppDispInfoWithDateOutput().getErrorFlag(), app, workChangeDomain);
		 * 
		 * return WorkChangeCheckRegisterDto.fromDomain(output);
		 */
		return null;
	}

	public AppWorkChangeOutputDto startDetailScreen(AppWorkChangeDetailParam appWorkChangeDetailParam) {
		String companyID = AppContexts.user().companyId();
		AppWorkChangeOutputDto appWorkChangeOutputDto = new AppWorkChangeOutputDto();
		AppWorkChangeDetailDto appWorkChangeDetailDto = AppWorkChangeDetailDto
				.fromDomain(appWorkChangeService.startDetailScreen(
						companyID, 
						appWorkChangeDetailParam.getAppDispInfoStartupDto().getAppDetailScreenInfo().getApplication().getAppID(), 
						appWorkChangeDetailParam.getAppDispInfoStartupDto().toDomain()));
		appWorkChangeOutputDto.setAppWorkChangeDispInfo(appWorkChangeDetailDto.appWorkChangeDispInfo);
		appWorkChangeOutputDto.setAppWorkChange(appWorkChangeDetailDto.appWorkChange);
		return appWorkChangeOutputDto;
	}

	public void checkBeforeUpdate(AddAppWorkChangeCommandPC command) {
		// error EA refactor 4
		/*
		 * // Command data CreateApplicationCommand appCommand =
		 * command.getApplication(); AppWorkChangeCommand workChangeCommand =
		 * command.getWorkChange(); AppWorkChangeDispInfo appWorkChangeDispInfo =
		 * command.getAppWorkChangeDispInfoCmd().toDomain();
		 * 
		 * // 会社ID String companyId = AppContexts.user().companyId(); // 申請ID String
		 * appID = appCommand.getApplicationID();
		 * 
		 * OutputMode outputMode =
		 * appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDetailScreenInfo().
		 * get().getOutputMode(); String appReason =
		 * applicationRepository.findByID(companyId, appID).get().getAppReason().v();
		 * AppTypeSetting appTypeSetting =
		 * appWorkChangeDispInfo.getAppDispInfoStartupOutput().
		 * getAppDispInfoNoDateOutput()
		 * .getRequestSetting().getApplicationSetting().getListAppTypeSetting().stream()
		 * .filter(x -> x.getAppType() ==
		 * ApplicationType_Old.WORK_CHANGE_APPLICATION).findFirst().get();
		 * if(outputMode==OutputMode.EDITMODE){ String typicalReason = Strings.EMPTY;
		 * String displayReason = Strings.EMPTY; boolean isFixedDisplay =
		 * appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY; boolean
		 * isTextDisplay = appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY;
		 * if(isFixedDisplay){ typicalReason += appCommand.getAppReasonID(); } //màn B
		 * có cách lấy reason khác if(isTextDisplay){
		 * if(Strings.isNotBlank(typicalReason)){ displayReason +=
		 * System.lineSeparator(); } displayReason += appCommand.getApplicationReason();
		 * }else{ if(Strings.isBlank(typicalReason)){ displayReason = appReason; } }
		 * ApplicationSetting applicationSetting =
		 * appWorkChangeDispInfo.getAppDispInfoStartupOutput().
		 * getAppDispInfoNoDateOutput() .getRequestSetting().getApplicationSetting();
		 * if(isFixedDisplay || isTextDisplay){ if
		 * (applicationSetting.getAppLimitSetting().getRequiredAppReason() &&
		 * Strings.isBlank(typicalReason+displayReason)) { throw new
		 * BusinessException("Msg_115"); } appReason = typicalReason + displayReason; }
		 * }
		 * 
		 * // 申請 Application_New updateApp = new Application_New(
		 * appCommand.getVersion(), companyId, appID,
		 * EnumAdaptor.valueOf(appCommand.getPrePostAtr(), PrePostAtr_Old.class),
		 * GeneralDateTime.now(), appCommand.getEnteredPersonSID(), new
		 * AppReason(Strings.EMPTY), appCommand.getStartDate(), new
		 * AppReason(appReason), ApplicationType_Old.WORK_CHANGE_APPLICATION,
		 * appCommand.getApplicantSID(), Optional.of(appCommand.getStartDate()),
		 * Optional.of(appCommand.getEndDate()), ReflectionInformation_New.builder()
		 * .stateReflection( EnumAdaptor.valueOf(appCommand.getReflectPlanState(),
		 * ReflectedState_New.class)) .stateReflectionReal(
		 * EnumAdaptor.valueOf(appCommand.getReflectPerState(),
		 * ReflectedState_New.class)) .forcedReflection(
		 * EnumAdaptor.valueOf(appCommand.getReflectPlanEnforce(),
		 * DisabledSegment_New.class)) .forcedReflectionReal(
		 * EnumAdaptor.valueOf(appCommand.getReflectPerEnforce(),
		 * DisabledSegment_New.class))
		 * .notReason(Optional.ofNullable(appCommand.getReflectPlanScheReason()) .map(x
		 * -> EnumAdaptor.valueOf(x, ReasonNotReflect_New.class)))
		 * .notReasonReal(Optional.ofNullable(appCommand.getReflectPerScheReason())
		 * .map(x -> EnumAdaptor.valueOf(x, ReasonNotReflectDaily_New.class)))
		 * .dateTimeReflection(Optional .ofNullable(appCommand.getReflectPlanTime() ==
		 * null ? null :
		 * GeneralDateTime.legacyDateTime(appCommand.getReflectPlanTime().date())))
		 * .dateTimeReflectionReal(Optional .ofNullable(appCommand.getReflectPerTime()
		 * == null ? null :
		 * GeneralDateTime.legacyDateTime(appCommand.getReflectPerTime().date())))
		 * .build()); // 勤務変更申請 AppWorkChange_Old workChangeDomain =
		 * AppWorkChange_Old.createFromJavaType(workChangeCommand.getCid(),
		 * workChangeCommand.getAppId(), workChangeCommand.getWorkTypeCd(),
		 * workChangeCommand.getWorkTimeCd(), workChangeCommand.getExcludeHolidayAtr(),
		 * workChangeCommand.getWorkChangeAtr(), workChangeCommand.getGoWorkAtr1(),
		 * workChangeCommand.getBackHomeAtr1(), workChangeCommand.getBreakTimeStart1(),
		 * workChangeCommand.getBreakTimeEnd1(), workChangeCommand.getWorkTimeStart1(),
		 * workChangeCommand.getWorkTimeEnd1(), workChangeCommand.getWorkTimeStart2(),
		 * workChangeCommand.getWorkTimeEnd2(), workChangeCommand.getGoWorkAtr2(),
		 * workChangeCommand.getBackHomeAtr2()); //OptimisticLock
		 * workChangeDomain.setVersion(appCommand.getVersion());
		 * 
		 * appWorkChangeService.checkBeforeUpdate(companyId, updateApp,
		 * workChangeDomain, false);
		 */
	}
	// start at create and modify mode
	public AppWorkChangeOutputDto getStartKAFS07(AppWorkChangeParam appWorkChangeParam) {

		boolean mode = appWorkChangeParam.getMode();
		String companyId = appWorkChangeParam.getCompanyId();
		String employeeId = null;
		if (appWorkChangeParam.getEmployeeId() != null) {
			employeeId = appWorkChangeParam.getEmployeeId();
		}
		List<GeneralDate> dates = null;

		if (appWorkChangeParam.getListDates() != null) {
			dates = appWorkChangeParam.getListDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
					.collect(Collectors.toList());
		}
		AppWorkChangeDispInfo appWorkChangeDispInfo = null;
		if (appWorkChangeParam.getMode()) {
			if (appWorkChangeParam.getAppWorkChangeOutputDto() != null) {
				appWorkChangeDispInfo = appWorkChangeParam.getAppWorkChangeOutputDto().getAppWorkChangeDispInfo().toDomain();									
			}
			
		} else {
			if (appWorkChangeParam.getAppWorkChangeOutputCmd() != null) {
				appWorkChangeDispInfo = appWorkChangeParam.getAppWorkChangeOutputCmd().getAppWorkChangeDispInfo().toDomain();							
			}
		}
		AppWorkChange appWorkChange = null;
		if (appWorkChangeParam.getAppWorkChangeDto() != null) {
			if (appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDetailScreenInfo().isPresent()) {
				appWorkChange = appWorkChangeParam.getAppWorkChangeDto().toDomain(appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getApplication());
			}
		}

		return AppWorkChangeOutputDto.fromDomain(
				appWorkChangeService.getAppWorkChangeOutput(mode, companyId, Optional.ofNullable(employeeId),
						Optional.ofNullable(dates), Optional.ofNullable(appWorkChangeDispInfo),
						Optional.ofNullable(appWorkChange)));
	}
	
	public AppWorkChangeDispInfoDto getUpdateKAFS07(UpdateWorkChangeParam updateWorkChangeParam) {
		
		List<GeneralDate> dates = null;

		if (updateWorkChangeParam.getListDates() != null) {
			dates = updateWorkChangeParam.getListDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
					.collect(Collectors.toList());
		}
		AppWorkChangeDispInfo appWorkChangeDispInfo = null;
		if (updateWorkChangeParam.getAppWorkChangeDispInfo() != null) {
			appWorkChangeDispInfo = updateWorkChangeParam.getAppWorkChangeDispInfo().toDomain();
		}

		return AppWorkChangeDispInfoDto.fromDomain(
				appWorkChangeService.changeAppDate(updateWorkChangeParam.getCompanyId(), dates, appWorkChangeDispInfo));

	}

	// 勤務変更申請の登録前チェック処理
	public WorkChangeCheckRegisterDto checkBeforeRegisterNew(AddAppWorkChangeCommandCheck command) {

		Boolean mode = command.getMode();
		String companyId = command.getCompanyId();
		String sId = AppContexts.user().employeeId();
		ApplicationDto applicationDto = command.getApplicationDto();
		applicationDto.setEmployeeID(sId);
		Application application = applicationDto.toDomain();
//      if (command.getAppWorkChangeDto().getAppID() != null ) {
//          application.setAppID(command.getAppWorkChangeDto().getAppID());
//      }
		
		AppWorkChangeDto appWorkChangeDto = command.getAppWorkChangeDto();
		int isError = command.getIsError();
		WorkChangeCheckRegOutput workChangeCheckRegOutput = appWorkChangeService.checkBeforeRegister(mode, companyId,
				application, appWorkChangeDto.toDomain(application),
				EnumAdaptor.valueOf(isError, ErrorFlagImport.class), command.getAppDispInfoStartupDto().toDomain());

		return WorkChangeCheckRegisterDto.fromDomain(workChangeCheckRegOutput);

	}
	
	// 勤務変更申請の登録前チェック処理
    public WorkChangeCheckRegisterDto checkBeforeRegisterPC(AddAppWorkChangeCommandCheck command) {

        Boolean mode = command.getMode();
        String companyId = command.getCompanyId();
        String sId = AppContexts.user().employeeId();
        Application application = new Application();
        ApplicationDto applicationDto = command.getApplicationDto();
        applicationDto.setEmployeeID(sId);
        if (StringUtils.isBlank(applicationDto.getAppID())) {
            application = Application.createFromNew(
                    EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class), 
                    applicationDto.getEmployeeID(), 
                    EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class), 
                    new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")), 
                    applicationDto.getEnteredPerson(), 
                    applicationDto.getOpStampRequestMode() == null ? Optional.empty() : Optional.ofNullable(EnumAdaptor.valueOf(applicationDto.getOpStampRequestMode(), StampRequestMode.class)), 
                            applicationDto.getOpReversionReason() == null ? Optional.empty() : Optional.ofNullable(new ReasonForReversion(applicationDto.getOpReversionReason())), 
                                    applicationDto.getOpAppStartDate() == null ? Optional.empty() : Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                                            applicationDto.getOpAppEndDate() == null ? Optional.empty() : Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                                                    applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.ofNullable(new AppReason(applicationDto.getOpAppReason())), 
                                                            applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.ofNullable(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())));
        } else {
            application = applicationDto.toDomain();
        }
        
        AppWorkChangeDto appWorkChangeDto = command.getAppWorkChangeDto();
        int isError = command.getIsError();
        WorkChangeCheckRegOutput workChangeCheckRegOutput = appWorkChangeService.checkBeforeRegister(mode, companyId,
                application, appWorkChangeDto.toDomain(application),
                EnumAdaptor.valueOf(isError, ErrorFlagImport.class), command.getAppDispInfoStartupDto().toDomain());

        return WorkChangeCheckRegisterDto.fromDomain(workChangeCheckRegOutput);

    }

	// 起動する B KAFS07
	public AppWorkChangeOutputDto getDetailKAFS07(AppWorkChangeDetailParam appWorkChangeDetailParam) {
		String companyId = AppContexts.user().companyId();
		String appId = appWorkChangeDetailParam.getAppId();
		AppDispInfoStartupDto appDispInfoStartupDto = appWorkChangeDetailParam.getAppDispInfoStartupDto();
		AppWorkChangeOutputDto appWorkChangeOutputDto = new AppWorkChangeOutputDto();
		AppWorkChangeDetailDto appWorkChangeDetailDto = AppWorkChangeDetailDto
				.fromDomain(appWorkChangeService.startDetailScreen(companyId, appId, appDispInfoStartupDto.toDomain()));
		appWorkChangeOutputDto.setAppWorkChangeDispInfo(appWorkChangeDetailDto.appWorkChangeDispInfo);
		appWorkChangeOutputDto.setAppWorkChange(appWorkChangeDetailDto.appWorkChange);
		return appWorkChangeOutputDto;
	}
	// check worktime
	public ChangeWkTypeTimeDto checkWorkTime(CheckWorkTimeParam param) {
		return ChangeWkTypeTimeDto.fromDomain(appWorkChangeService.changeWorkTypeWorkTime(param.getCompanyId(), param.getWorkType(), Optional.ofNullable(param.getWorkTime()), param.getAppWorkChangeSetDto().toDomain()));
	}

}
