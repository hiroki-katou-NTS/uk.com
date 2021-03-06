package nts.uk.ctx.at.request.app.find.application.appabsence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.kdl035.HolidayWorkAssociationStart;
import nts.uk.ctx.at.request.app.command.application.kdl035.Kdl035InputData;
import nts.uk.ctx.at.request.app.command.application.kdl035.Kdl035OutputData;
import nts.uk.ctx.at.request.app.command.application.kdl036.HolidayAssociationStart;
import nts.uk.ctx.at.request.app.command.application.kdl036.Kdl036InputData;
import nts.uk.ctx.at.request.app.command.application.kdl036.Kdl036OutputData;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceCheckRegisterDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceStartScreenBOutput;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AccumulatedRestManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AnualLeaveManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeRelationShipDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeWorkTimeParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeWorkTypeParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.DisplayAllScreenParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.NursingCareLeaveManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.Overtime60HManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SpecAbsenceParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SubstituteLeaveManagementDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.CheckDispHolidayType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AbsenceCheckRegisterOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppForLeaveStartOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.VacationCheckOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.SpecialHolidayEventAlgorithm;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author loivt
 *
 */
@Stateless
public class AppAbsenceFinder {

	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private PredetemineTimeSettingRepository predTimeRepository;
	
	
	@Inject
	private HolidayShipmentScreenAFinder hdShipmentScreenAFinder;
	
	@Inject
	private AbsenceServiceProcess absenseProcess;
	
	@Inject
	private SpecialHolidayEventAlgorithm specHdEventAlg;
	
	@Inject
    private HolidayAssociationStart holidayAssociationStart;
	
	@Inject
    private HolidayWorkAssociationStart holidayWorkAssociationStart;
	

	/**
	 * 1.???????????????????????????????????????
	 * @param appDate
	 * @param employeeID
	 * @param employeeIDs
	 * @return
	 */
	public AppAbsenceStartInfoDto getAppForLeave(AppDispInfoStartupDto appDispInfo) {
		String companyID = AppContexts.user().companyId();
		
		// 1.????????????????????????????????????
		AppAbsenceStartInfoDto appDispInfoStartup = AppAbsenceStartInfoDto.fromDomain(absenseProcess.getVacationActivation(companyID, appDispInfo.toDomain()));
		
		return appDispInfoStartup;
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param startAppDate
	 * @param endAppDate
	 * @param workType
	 * @param employeeID
	 * @param holidayType
	 * @param alldayHalfDay
	 * @return
	 */
	public AppAbsenceStartInfoDto getAllDisplay(DisplayAllScreenParam param) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenseProcess.holidayTypeChangeProcess(
				companyID, 
				param.getStartInfo().toDomain(companyID), 
				param.getAppDates(),
				EnumAdaptor.valueOf(param.getHolidayAppType(), HolidayAppType.class));
		
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * ???????????????????????? getChangeAppDate
	 * ????????????????????????
	 * @param startAppDate
	 * @param displayHalfDayValue
	 * @param employeeID
	 * @param workTypeCode
	 * @param holidayType
	 * @param alldayHalfDay
	 * @param prePostAtr
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeAppDate(String companyID, AppAbsenceStartInfoDto appAbsenceStartInfoOutput, List<String> appDates, int holidayType, AppDispInfoWithDateDto appWithDate) {
		// INPUT????????????????????????????????????????????????????????????
	    appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appWithDate);
	    // ???????????????????????????
	    AppAbsenceStartInfoOutput appAbsence = this.absenseProcess.holidayTypeChangeProcess(companyID, appAbsenceStartInfoOutput.toDomain(companyID), appDates, EnumAdaptor.valueOf(holidayType, HolidayAppType.class));
	    
	    // INPUT????????????????????????????????????????????????????????????
	    appAbsenceStartInfoOutput = AppAbsenceStartInfoDto.fromDomain(appAbsence);
	    
		// ???????????????????????????????????????
		CheckDispHolidayType checkDispHolidayType = absenseProcess.checkDisplayAppHdType(
				companyID, 
				appAbsenceStartInfoOutput.toDomain(companyID).getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().stream().findFirst().get().getSid(), 
				appAbsenceStartInfoOutput.toDomain(companyID).getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getBaseDate());
		// ?????????????????????????????????????????????????????????
		appAbsenceStartInfoOutput.getRemainVacationInfo().setAnnualLeaveManagement(AnualLeaveManagementDto.fromDomain(checkDispHolidayType.getAnnAnualLeaveManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setAccumulatedRestManagement(AccumulatedRestManagementDto.fromDomain(checkDispHolidayType.getAccumulatedRestManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setSubstituteLeaveManagement(SubstituteLeaveManagementDto.fromDomain(checkDispHolidayType.getSubstituteLeaveManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setOvertime60hManagement(Overtime60HManagementDto.fromDomain(checkDispHolidayType.getOvertime60hManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setNursingCareLeaveManagement(NursingCareLeaveManagementDto.fromDomain(checkDispHolidayType.getNursingCareLeaveManagement()));
		
//		List<HolidayAppTypeName> holidayAppTypes = new ArrayList<>();
//		holidayAppTypes = this.getHolidayAppTypeName(
//				Optional.of(appAbsenceStartInfoOutput.getHdAppSet()),
//				holidayAppTypes,
//				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getEmploymentSet());
//		holidayAppTypes.sort((a, b) -> a.getHolidayAppTypeCode().compareTo(b.getHolidayAppTypeCode()));
//		result.holidayAppTypeName = holidayAppTypes;
		// ???????????????????????????????????????????????????
		return appAbsenceStartInfoOutput;
	}

	/**
	 * ???????????????????????????????????????????????????
	 * 
	 * @param startAppDate
	 * @param displayHalfDayValue
	 * @param employeeID
	 * @param workTypeCode
	 * @param holidayType
	 * @param alldayHalfDay
	 * @param prePostAtr
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeByAllDayOrHalfDay(AppAbsenceStartInfoDto appAbsenceStartInfoDto, 
			boolean displayHalfDayValue, Integer alldayHalfDay, Integer holidayType) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain(companyID);
		// INPUT????????????????????????????????????
		if(holidayType!=null) {
			// ???????????????????????????
//			appAbsenceStartInfoOutput = absenseProcess.holidayTypeChangeProcess(
//					companyID, 
//					appAbsenceStartInfoOutput, 
//					displayHalfDayValue, 
//					alldayHalfDay, 
//					EnumAdaptor.valueOf(holidayType, HolidayAppType.class));
		}
		// ??????????????????????????????????????????????????????????????????
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}


	/**
	 * ???????????????????????????????????????????????????
	 * 
	 * @param startAppDate
	 * @param displayHalfDayValue
	 * @param employeeID
	 * @param holidayType
	 * @param alldayHalfDay
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeDisplayHalfDay(String startAppDate, boolean displayHalfDayValue, String employeeID,
			String workTypeCode, Integer holidayType, int alldayHalfDay, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain(companyID);
		appAbsenceStartInfoOutput = absenseProcess.allHalfDayChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				displayHalfDayValue, 
				alldayHalfDay, 
				holidayType == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(holidayType, HolidayAppType.class)));
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @param startAppDate
	 * @param employeeID
	 * @param workTypeCode
	 * @param holidayType
	 * @param workTimeCode
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeWorkType(ChangeWorkTypeParam param) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getStartInfo().toDomain(companyID);
		appAbsenceStartInfoOutput = absenseProcess.workTypeChangeProcess(
				companyID, 
				param.getAppDates(),
				appAbsenceStartInfoOutput, 
				EnumAdaptor.valueOf(param.getHolidayAppType(), HolidayAppType.class), 
				Optional.ofNullable(param.getWorkTypeCd()));
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}
	
	/**
	 * ??????????????????????????????????????????????????????
	 * @param ??????ID sId
	 * @param ????????? date
	 * @param ????????????????????? workTypeCd
	 * @param ???????????????????????? workTimeCd
	 * @param ???????????????????????????????????? appInfo
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeWorkTime(ChangeWorkTimeParam param) {
	    // ??????????????????????????????????????????????????????
	        // process on UI
	    
	    // ???????????????????????????????????????????????????????????????
	    AttendanceTime requiredTime = absenseProcess.calculateTimeRequired(
	            param.getSId(), 
	            param.getDate() == null ? Optional.empty() : Optional.ofNullable(GeneralDate.fromString(param.getDate(), "yyyy/MM/dd")), 
	            Optional.ofNullable(param.getWorkTypeCd()), 
	            Optional.ofNullable(param.getWorkTimeCd()), 
	            Optional.empty(), 
	            Optional.empty(), 
	            Optional.empty());
	    
	    // ????????????????????????????????????????????????????????????????????????????????????????????????
	    AppAbsenceStartInfoDto appInfo = param.getAppAbsenceStartInfo();
	    appInfo.setRequiredVacationTime(requiredTime.v());
	    
	    return appInfo;
	}

	/**
	 * getListWorkTimeCodes
	 * 
	 * @param startDate
	 * @param employeeID
	 * @return
	 */
	public List<String> getListWorkTimeCodes(String startDate, String employeeID) {
		String companyID = AppContexts.user().companyId();
		// 1.?????????????????????????????????
		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,
				startDate == null ? GeneralDate.today() : GeneralDate.fromString(startDate, DATE_FORMAT))
				.stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
		return listWorkTimeCodes;
	}

	/**
	 * getWorkingHours
	 * 
	 * @param workTimeCode
	 * @param workTypeCode
	 * @param holidayType
	 * @return
	 */
	public AppAbsenceStartInfoDto getWorkingHours(String date, String workTimeCode, String workTypeCode, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain(companyID);
		// ??????????????????????????????
		appAbsenceStartInfoOutput = absenseProcess.workTimesChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				workTypeCode, 
				Optional.of(workTimeCode));
		
		Optional<GeneralDate> dateOpt = StringUtils.isEmpty(date) ?
		        Optional.empty() : Optional.of(GeneralDate.fromString(date, DATE_FORMAT));
		// ???????????????????????????????????????????????????????????????
		AttendanceTime requireTime = absenseProcess.calculateTimeRequired(
		        employeeID,
		        dateOpt,
		        Optional.ofNullable(workTypeCode),
		        Optional.ofNullable(workTimeCode),
		        Optional.empty(), 
		        Optional.empty(), 
		        Optional.empty());
		
		// ????????????????????????????????????????????????????????????????????????????????????????????????
		appAbsenceStartInfoOutput.setRequiredVacationTimeOptional(Optional.ofNullable(requireTime));
		
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param companyID
	 * @param workTypeCode
	 * @param workTimeCode
	 * @return
	 */
	public PrescribedTimezoneSetting initWorktimeCode(String companyID, String workTypeCode, String workTimeCode) {

		Optional<WorkType> WkTypeOpt = workTypeRepository.findByPK(companyID, workTypeCode);
		if (WkTypeOpt.isPresent()) {
			// ?????????????????????1??????????????????1???????????????????????????????????????
			WorkStyle workStyle = basicScheduleService.checkWorkDay(companyID, WkTypeOpt.get().getWorkTypeCode().toString());
			if (workStyle == null) {
				return null;
			}
			if (!workStyle.equals(WorkStyle.ONE_DAY_REST)) {
				// ?????????????????????????????????????????????????????????????????????
				// ??????????????????????????????
				if (workTimeCode != null && !workTimeCode.equals("")) {
					if (this.predTimeRepository.findByWorkTimeCode(companyID, workTimeCode).isPresent()) {
						PrescribedTimezoneSetting prescribedTzs = this.predTimeRepository
								.findByWorkTimeCode(companyID, workTimeCode).get().getPrescribedTimezoneSetting();
						return prescribedTzs;
					}
				}
			}
		}
		return null;
	}
	
	
	public List<TimezoneUse> initWorkingHours(String companyID, String workTypeCode, String workTimeCode) {

		Optional<WorkType> WkTypeOpt = workTypeRepository.findByPK(companyID, workTypeCode);
		if (WkTypeOpt.isPresent()) {
			// ?????????????????????1??????????????????1???????????????????????????????????????
			WorkStyle workStyle = basicScheduleService.checkWorkDay(companyID, WkTypeOpt.get().getWorkTypeCode().toString());
			if (workStyle == null) {
				return Collections.emptyList();
			}
			if (!workStyle.equals(WorkStyle.ONE_DAY_REST)) {
				// ?????????????????????????????????????????????????????????????????????
				// ??????????????????????????????
				return hdShipmentScreenAFinder.getTimeZones(companyID, workTimeCode, EnumAdaptor.valueOf(workStyle.value, AttendanceHolidayAttr.class));
			}
		}
		return Collections.emptyList();
	}
	/**
	 * ??????????????????????????????
	 * @author hoatt
	 * @return
	 */
	public ChangeRelationShipDto changeRelationShip(SpecAbsenceParam specAbsenceParam){
		//hoatt - 2018.08.08 - doi ung specHd
		String companyId = AppContexts.user().companyId();
		/*//?????????????????????????????????
		//??????????????????????????????????????????????????????????????????????????????
		CheckWkTypeSpecHdEventOutput checkSpecHd = specHdEventAlg.checkWkTypeSpecHdForEvent(companyId, workTypeCD);*/
		MaxDaySpecHdOutput maxDay = null;
		//if(checkSpecHd.isSpecHdForEventFlag()){
			//???????????????????????????????????????????????????
		maxDay = specHdEventAlg.getMaxDaySpecHd(companyId, specAbsenceParam.frameNo,
				specAbsenceParam.specHdEvent.toDomain(), Optional.ofNullable(specAbsenceParam.relationCD));
		//}
		return new ChangeRelationShipDto(maxDay);
	};
	
	public AbsenceCheckRegisterDto checkBeforeRegister(CreatAppAbsenceCommand param) {
	    // ??????ID
	    String companyID = AppContexts.user().companyId();
	    
	    ApplyForLeave applyForLeave = param.getApplyForLeave().toDomain();
	    Application application = Application.createFromNew(
                EnumAdaptor.valueOf(param.getApplication().getPrePostAtr(), PrePostAtr.class),
                param.getApplication().getEmployeeID(), EnumAdaptor.valueOf(param.getApplication().getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd")),
                param.getApplication().getEmployeeID(),
                param.getApplication().getOpStampRequestMode() == null ? Optional.empty()
                        : Optional.of(EnumAdaptor.valueOf(param.getApplication().getOpStampRequestMode(),
                                StampRequestMode.class)),
                Optional.of(new ReasonForReversion(param.getApplication().getOpReversionReason())),
                param.getApplication().getOpAppStartDate() == null ? Optional.empty()
                        : Optional.of(new ApplicationDate(
                                GeneralDate.fromString(param.getApplication().getOpAppStartDate(), "yyyy/MM/dd"))),
                        param.getApplication().getOpAppEndDate() == null ? Optional.empty()
                        : Optional.of(new ApplicationDate(
                                GeneralDate.fromString(param.getApplication().getOpAppEndDate(), "yyyy/MM/dd"))),
                Optional.of(new AppReason(param.getApplication().getOpAppReason())),
                param.getApplication().getOpAppStandardReasonCD() == null ? 
                        Optional.empty() : Optional.of(new AppStandardReasonCode(param.getApplication().getOpAppStandardReasonCD())));
	    applyForLeave.setApplication(application);
	    
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain(companyID);
		
		// ?????????????????????????????????????????????
        Kdl036OutputData kdl036output = holidayAssociationStart.init(new Kdl036InputData(
                application.getEmployeeID(), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                param.getAppAbsenceStartInfoDto().getWorkTypeLst().stream()
                    .filter(x -> x.getWorkTypeCode()
                    .equals(param.getApplyForLeave().getReflectFreeTimeApp().getWorkInfo().getWorkType())).findFirst().get().getWorkAtr() == 0 ? 1 : 0, 
                1,
                param.getAppAbsenceStartInfoDto().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst(), 
                new ArrayList<LeaveComDayOffManaDto>()));
        
        
        // ?????????????????????????????????????????????
        Kdl035OutputData kdl035output = holidayWorkAssociationStart.init(new Kdl035InputData(
                application.getEmployeeID(), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                param.getAppAbsenceStartInfoDto().getWorkTypeLst().stream()
                    .filter(x -> x.getWorkTypeCode()
                    .equals(param.getApplyForLeave().getReflectFreeTimeApp().getWorkInfo().getWorkType())).findFirst().get().getWorkAtr() == 0 ? 1 : 0, 
                1,
                param.getAppAbsenceStartInfoDto().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst(), 
                new ArrayList<PayoutSubofHDManagementDto>()));
		
		AbsenceCheckRegisterOutput result = absenseProcess.checkBeforeRegister(
				companyID, 
				appAbsenceStartInfoOutput, 
				applyForLeave,
				param.isAgentAtr(), 
                kdl036output.getHolidayWorkInfoList().isEmpty(), 
                kdl035output.getSubstituteWorkInfoList().isEmpty());
		return AbsenceCheckRegisterDto.fromDomain(result);
	}
	
	public AbsenceCheckRegisterDto checkBeforeUpdate(CreatAppAbsenceCommand param) {
	 // ??????ID
        String companyID = AppContexts.user().companyId();
        
        ApplyForLeave applyForLeave = param.getApplyForLeave().toDomain();
        param.getApplication().setInputDate(GeneralDateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
        Application application = param.getApplication().toDomain();
        applyForLeave.setApplication(application);
        AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain(companyID);
        
        // ?????????????????????????????????????????????
        Kdl036OutputData kdl036output = holidayAssociationStart.init(new Kdl036InputData(
                application.getEmployeeID(), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                param.getAppAbsenceStartInfoDto().getWorkTypeLst().stream()
                    .filter(x -> x.getWorkTypeCode()
                    .equals(param.getApplyForLeave().getReflectFreeTimeApp().getWorkInfo().getWorkType())).findFirst().get().getWorkAtr() == 0 ? 1 : 0, 
                1,
                param.getAppAbsenceStartInfoDto().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst(), 
                new ArrayList<LeaveComDayOffManaDto>()));
        
        
        // ?????????????????????????????????????????????
        Kdl035OutputData kdl035output = holidayWorkAssociationStart.init(new Kdl035InputData(
                application.getEmployeeID(), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd"), 
                param.getAppAbsenceStartInfoDto().getWorkTypeLst().stream()
                    .filter(x -> x.getWorkTypeCode()
                    .equals(param.getApplyForLeave().getReflectFreeTimeApp().getWorkInfo().getWorkType())).findFirst().get().getWorkAtr() == 0 ? 1 : 0, 
                1,
                param.getAppAbsenceStartInfoDto().getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst(), 
                new ArrayList<PayoutSubofHDManagementDto>()));
        
		return AbsenceCheckRegisterDto.fromDomain(absenseProcess.checkBeforeUpdate(
		        companyID, 
		        appAbsenceStartInfoOutput, 
		        applyForLeave, 
		        param.isAgentAtr(), 
		        kdl036output.getHolidayWorkInfoList().isEmpty(), 
		        kdl035output.getSubstituteWorkInfoList().isEmpty()));
	}

	public VacationCheckOutput checkVacationTyingManage(WorkTypeDto wtBefore, WorkTypeDto wtAfter,
            List<LeaveComDayOffManaDto> leaveComDayOffMana, List<PayoutSubofHDManagementDto> payoutSubofHDManagements) {
	    return this.absenseProcess.checkVacationTyingManage(
	            wtBefore != null ? wtBefore.toDomain() : null, 
	            wtAfter != null ? wtAfter.toDomain() : null, 
	            leaveComDayOffMana.stream().map(item -> item.toDomain()).collect(Collectors.toList()), 
	            payoutSubofHDManagements.stream().map(item -> item.toDomain()).collect(Collectors.toList()));
	}
	
	public AbsenceStartScreenBOutput getAppForLeaveStartB(String companyID, String appID, AppDispInfoStartupDto appDispInfoStartupOutput) {
	    AppForLeaveStartOutput appForLeaveStart = absenseProcess.getAppForLeaveStartB(companyID, appID, appDispInfoStartupOutput.toDomain());
	    
	    return new AbsenceStartScreenBOutput(
	            AppAbsenceStartInfoDto.fromDomain(appForLeaveStart.getAppAbsenceStartInfoOutput()), 
	            ApplyForLeaveDto.fromDomain(appForLeaveStart.getApplyForLeave()));
	}
	
	public AppAbsenceStartInfoDto getChangeHolidayDates(String companyID, List<String> holidayDates, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
	    AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenseProcess.getChangeHolidayDates(
	            companyID, 
	            holidayDates.stream().map(x -> GeneralDate.fromString(x, "yyyy-MM-dd")).collect(Collectors.toList()), 
	            appAbsenceStartInfoDto.toDomain(companyID));
	    return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}
	
	public AbsenceCheckRegisterDto checkBeforeRegisterHolidayDates(String companyID, ApplicationDto oldApplication, ApplicationDto newApplication, AppAbsenceStartInfoDto appAbsenceStartInfoDto, ApplyForLeaveDto originApplyForLeave, ApplyForLeaveDto newApplyForLeave) {
	    Application oldApp = oldApplication.toDomain();
	    Application newApp = newApplication.toDomain();
	    ApplyForLeave oldApplyForLeave = originApplyForLeave.toDomain();
	    oldApplyForLeave.setApplication(oldApp);
	    ApplyForLeave applyForLeave = newApplyForLeave.toDomain();
	    applyForLeave.setApplication(newApp);
	    
	    AbsenceCheckRegisterOutput absenceCheckRegisterOutput = absenseProcess.checkAppAbsenceRegister(true, companyID, 
	            appAbsenceStartInfoDto.toDomain(companyID), 
	            oldApplyForLeave, 
	            applyForLeave);
	    return AbsenceCheckRegisterDto.fromDomain(absenceCheckRegisterOutput);
	}
}
