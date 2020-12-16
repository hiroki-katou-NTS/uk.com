package nts.uk.ctx.at.request.app.find.application.overtime;

/*import java.util.Collections;*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOverTimeMobDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeInputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeSettingData;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeSettingDataDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.PreAppOvertimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.RecordWorkDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeFourProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.FlexExcessUseSetAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class AppOvertimeFinder {
	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "0:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	@Inject
	private OvertimeSixProcess overtimeSixProcess;
	@Inject
	private OvertimeFourProcess overtimeFourProcess;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	@Inject
	private OvertimeAppSetRepository appOvertimeSettingRepository;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeRepo;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	/**
	 * @param url
	 * @param appDate
	 * @param uiType
	 * @return
	 */
	public OverTimeDto getOvertimeByUIType(String url,String appDate,int uiType,Integer timeStart1,Integer timeEnd1,String reasonContent,List<String> employeeIDs,String employeeID){
		
//		OverTimeDto result = new OverTimeDto();
//		ApplicationDto_New applicationDto = new ApplicationDto_New();
//		List<OvertimeInputDto> overTimeInputs = new ArrayList<>();
//		String companyID = AppContexts.user().companyId();
//		// 10_申請者を作成
//		if(CollectionUtil.isEmpty(employeeIDs) && employeeID == null){
//			 employeeID = AppContexts.user().employeeId();
//		}else if(!CollectionUtil.isEmpty(employeeIDs)){
//			employeeID = employeeIDs.get(0);
//			List<EmployeeInfoImport> employees = this.atEmployeeAdapter.getByListSID(employeeIDs);
//			if(!CollectionUtil.isEmpty(employees)){
//				List<EmployeeOvertimeDto> employeeOTs = new ArrayList<>();
//				for(EmployeeInfoImport emp : employees){
//					EmployeeOvertimeDto employeeOT = new EmployeeOvertimeDto(emp.getSid(), emp.getBussinessName());
//					employeeOTs.add(employeeOT);
//				}
//				result.setEmployees(employeeOTs);
//			}
//		}
//		
//		int rootAtr = 1;
//		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
//		
//		// 11_設定データを取得
//		OvertimeSettingData overtimeSettingData = getSettingData(companyID, employeeID, rootAtr, ApplicationType.OVER_TIME_APPLICATION, appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
//		result.setOvertimeSettingDataDto(this.convSettingToDto(overtimeSettingData));
//		
//		AppCommonSettingOutput appCommonSettingOutput = overtimeSettingData.appCommonSettingOutput;
//		result.setRequireAppReasonFlg(appCommonSettingOutput.getApplicationSetting().getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED) ? true : false);
//		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  == 1 ? false : true);
//		result.setSendMailWhenApprovalFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenApprovalFlg().value == 1 ? true : false);
//		result.setSendMailWhenRegisterFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenRegisterFlg().value == 1 ? true : false);
//		
//		// 01_初期データ取得
//		getData(result,uiType,appDate,companyID,employeeID,overtimeSettingData,applicationDto,overTimeInputs,preAppOvertimeDto,timeStart1,timeEnd1,reasonContent);
//		
//		result.setApplication(applicationDto);
//		String employeeName = "";
//		if(Strings.isNotBlank(applicationDto.getApplicantSID())){
//			employeeName = employeeAdapter.getEmployeeName(applicationDto.getApplicantSID());
//			result.setEmployeeID(applicationDto.getApplicantSID());
//		} else {
//			employeeName = employeeAdapter.getEmployeeName(employeeID);
//			result.setEmployeeID(employeeID);
//		}
//		result.setEmployeeName(employeeName);
//		if(url != null)
//		result.setOvertimeAtr(Integer.parseInt(url));
//		return result;
		return null;
	}
	
	public OvertimeSettingDataDto convSettingToDto(OvertimeSettingData overtimeSettingData) {
//		AppOvertimeSettingDto appOvertimeSettingDto = AppOvertimeSettingDto
//				.convertToDto(overtimeSettingData.getAppOvertimeSetting());
//
//		ApplicationSettingDto applicationSettingDto = ApplicationSettingDto
//				.convertToDto(overtimeSettingData.getAppCommonSettingOutput().getApplicationSetting());
//		ApprovalFunctionSettingDto approvalFunctionSettingDto = ApprovalFunctionSettingDto
//				.convertToDto(overtimeSettingData.getAppCommonSettingOutput().getApprovalFunctionSetting());
//		List<AppTypeDiscreteSettingDto> appTypeDiscreteSettingDto = overtimeSettingData.getAppCommonSettingOutput()
//				.getAppTypeDiscreteSettings().stream().map(item -> AppTypeDiscreteSettingDto.convertToDto(item))
//				.collect(Collectors.toList());
//		List<ApplicationDeadlineDto> applicationDeadlineDto = overtimeSettingData.getAppCommonSettingOutput()
//				.getApplicationDeadlines().stream().map(item -> ApplicationDeadlineDto.convertToDto(item))
//				.collect(Collectors.toList());
//		AppEmploymentSettingDto appEmploymentSettingDto = AppEmploymentSettingDto.fromDomain(overtimeSettingData.getAppCommonSettingOutput().getAppEmploymentWorkType());
//		
//		AppCommonSettingOutputDto appCommonSettingOutputDto = new AppCommonSettingOutputDto(
//				overtimeSettingData.getAppCommonSettingOutput().getGeneralDate().toString(DATE_FORMAT), applicationSettingDto,
//				approvalFunctionSettingDto, appTypeDiscreteSettingDto, applicationDeadlineDto, appEmploymentSettingDto);
//
//		OvertimeRestAppCommonSettingDto overtimeRestAppCommonSettingDto = OvertimeRestAppCommonSettingDto
//				.convertToDto(overtimeSettingData.getOvertimeRestAppCommonSet());
//
//		OvertimeSettingDataDto overtimeSettingDto = new OvertimeSettingDataDto(appCommonSettingOutputDto,
//				appOvertimeSettingDto, overtimeRestAppCommonSettingDto);
//		
//		return overtimeSettingDto;
		return null;
	}
	
	public ColorConfirmResult calculationresultConfirm(List<CaculationTime> overtimeHours,
			List<CaculationTime> bonusTimes,
			int prePostAtr,
			String appDate,
			String siftCD,
			String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests){
		ColorConfirmResult result = new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
//		String companyID = AppContexts.user().companyId();
//		String employeeID = AppContexts.user().employeeId();
//		GeneralDateTime inputDate = GeneralDateTime.now();
//		int rootAtr = 1;
//		//1-1.新規画面起動前申請共通設定を取得する
//		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
//						employeeID,
//						rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
//		
//		// 6.計算処理 : 
//		List<OvertimeInputCaculation> overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, siftCD, workTypeCode, startTime, endTime, startTimeRests, endTimeRests);
//		// 06-01_色表示チェック
//		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
//		if(approvalFunctionSetting != null){
//			result = commonOvertimeHoliday.checkDisplayColorCF(overtimeHours,
//					overtimeInputCaculations,
//					prePostAtr,
//					inputDate,
//					appDate == null ? null :GeneralDate.fromString(appDate, DATE_FORMAT),
//					ApplicationType.OVER_TIME_APPLICATION.value,
//					employeeID, 
//					companyID, 
//					approvalFunctionSetting,
//					siftCD);
//		}
		
		// 計算フラグ=0(client setting)
		
		return result;
	}
	
	public PreActualColorResult getCalculationResultMob(List<CaculationTime> overtimeHours,
			List<CaculationTime> bonusTimes, int prePostAtr, String appDate, String siftCD, String workTypeCode,
			Integer startTime, Integer endTime, List<Integer> startTimeRests, List<Integer> endTimeRests, boolean displayCaculationTime, boolean isFromStepOne,
			ApplicationDto opAppBefore, boolean beforeAppStatus, int actualStatus, List<OvertimeColorCheck> actualLst, OvertimeSettingDataDto overtimeSettingDataDto) {
//		OvertimeSettingData overtimeSettingData = overtimeSettingDataDto.toDomain();
//		//1-1.新規画面起動前申請共通設定を取得する
//		AppCommonSettingOutput appCommonSettingOutput = overtimeSettingData.appCommonSettingOutput;
//		// 6.計算処理 :
//		List<OvertimeInputCaculation> overtimeInputCaculations = new ArrayList<>();
//		if (isFromStepOne && displayCaculationTime) {
//			overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, siftCD, 
//					workTypeCode, startTime, endTime, startTimeRests, endTimeRests);
//		}	
//		List<OvertimeColorCheck> otTimeLst = overtimeHours.stream()
//				.map(x -> OvertimeColorCheck.createApp(x.getAttendanceID(), x.getFrameNo(), x.getApplicationTime()))
//				.collect(Collectors.toList());
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
//		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
//		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
//		// 07_事前申請・実績超過チェック
//		PreActualColorResult preActualColorResult =	preActualColorCheck.preActualColorCheck(
//				preExcessDisplaySetting, 
//				performanceExcessAtr, 
//				ApplicationType.OVER_TIME_APPLICATION, 
//				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
//				overtimeInputCaculations, 
//				otTimeLst, 
//				opAppBefore == null ? Optional.empty() : Optional.of(ApplicationDto_New.toEntity(opAppBefore)), 
//				beforeAppStatus, 
//				actualLst, 
//				EnumAdaptor.valueOf(actualStatus, ActualStatus.class));
//		return preActualColorResult;
		return null;
	}
	
	/**
	 * @return
	 */
	public List<CaculationTime> getCaculationValue(List<CaculationTime> overtimeHours,
			List<CaculationTime> bonusTimes,
			int prePostAtr,
			String appDate,
			String siftCD,
			String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests){
		 
		List<CaculationTime> caculationTimes = new ArrayList<>();
//		String companyID = AppContexts.user().companyId();
//		String employeeID = AppContexts.user().employeeId();
//		GeneralDateTime inputDate = GeneralDateTime.now();
//		int rootAtr = 1;
//		//1-1.新規画面起動前申請共通設定を取得する
//		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
//						employeeID,
//						rootAtr, ApplicationType.OVER_TIME_APPLICATION, appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
//		
//		// 6.計算処理 : 
//		List<OvertimeInputCaculation> overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, siftCD, workTypeCode, startTime, endTime, startTimeRests, endTimeRests);
//		// 06-01_色表示チェック
//		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
//		if(approvalFunctionSetting != null){
//			overtimeHours = commonOvertimeHoliday.checkDisplayColor(overtimeHours,
//					overtimeInputCaculations,
//					prePostAtr,
//					inputDate,
//					appDate == null ? null :GeneralDate.fromString(appDate, DATE_FORMAT),
//					ApplicationType.OVER_TIME_APPLICATION.value,
//					employeeID, 
//					companyID, 
//					approvalFunctionSetting,
//					siftCD);
//		}
//		// 06-02_残業時間を取得
//		List<CaculationTime> caculationTimeHours = this.overtimeSixProcess.getCaculationOvertimeHours(companyID, employeeID, appDate, ApplicationType.OVER_TIME_APPLICATION.value,overtimeHours,overtimeInputCaculations);
//		caculationTimes.addAll(caculationTimeHours);
//		
//		// 06-03_加給時間を取得
//		List<CaculationTime> caculationTimeBonus= commonOvertimeHoliday.getCaculationBonustime(companyID, employeeID, appDate,  ApplicationType.OVER_TIME_APPLICATION.value,bonusTimes);
//		caculationTimes.addAll(caculationTimeBonus);
		
		// 計算フラグ=0(client setting)
		
		return caculationTimes;
	}
	
	/**
	 * @return
	 */
	public PreActualColorResult getCalculateValue(String employeeID, String appDate, Integer prePostAtr, String workTypeCD, String workTimeCD,
			List<CaculationTime> overtimeInputLst, Integer startTime, Integer endTime, List<Integer> startTimeRests, List<Integer> endTimeRests,
			ApplicationDto opAppBefore, boolean beforeAppStatus, int actualStatus, List<OvertimeColorCheck> actualLst, OvertimeSettingDataDto overtimeSettingDataDto){
//		OvertimeSettingData overtimeSettingData = overtimeSettingDataDto.toDomain();
//		//1-1.新規画面起動前申請共通設定を取得する
//		AppCommonSettingOutput appCommonSettingOutput = overtimeSettingData.appCommonSettingOutput;
//		// 6.計算処理 : 
//		List<OvertimeInputCaculation> overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, workTimeCD, workTypeCD, startTime, endTime, startTimeRests, endTimeRests);
//		List<OvertimeColorCheck> otTimeLst = overtimeInputLst.stream()
//				.map(x -> OvertimeColorCheck.createApp(x.getAttendanceID(), x.getFrameNo(), x.getApplicationTime()))
//				.collect(Collectors.toList());
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
//		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
//		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
//		// 07_事前申請・実績超過チェック
//		PreActualColorResult preActualColorResult =	preActualColorCheck.preActualColorCheck(
//				preExcessDisplaySetting, 
//				performanceExcessAtr, 
//				ApplicationType.OVER_TIME_APPLICATION, 
//				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
//				overtimeInputCaculations, 
//				otTimeLst, 
//				opAppBefore == null ? Optional.empty() : Optional.of(ApplicationDto_New.toEntity(opAppBefore)), 
//				beforeAppStatus, 
//				actualLst, 
//				EnumAdaptor.valueOf(actualStatus, ActualStatus.class));
//		return preActualColorResult;
		return null;
	}

	private List<OvertimeInputCaculation> convertMaptoList(Map<Integer,TimeWithCalculationImport> overTime,TimeWithCalculationImport flexTime,TimeWithCalculationImport midNightTime){
		List<OvertimeInputCaculation> result = new ArrayList<>();
		
		for(Map.Entry<Integer,TimeWithCalculationImport> entry : overTime.entrySet()){
			OvertimeInputCaculation overtimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, entry.getKey(), entry.getValue().getCalTime());
			result.add(overtimeCal);
		}
		OvertimeInputCaculation flexTimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, 12,(flexTime.getCalTime() == null || flexTime.getCalTime() < 0)? null : flexTime.getCalTime());
		OvertimeInputCaculation midNightTimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, 11, midNightTime.getCalTime());
		result.add(flexTimeCal);
		result.add(midNightTimeCal);
		return result;
	}
	
	/**
	 * @param appID
	 * @return
	 */
	public OverTimeDto findDetailByAppID(String appID){
//		String companyID = AppContexts.user().companyId();
//		Optional<AppOverTime> opAppOverTime = overtimeRepository.getFullAppOvertime(companyID, appID);
//		if(!opAppOverTime.isPresent()){
//			throw new BusinessException("Msg_198");
//		}
//		AppOverTime appOverTime = opAppOverTime.get();
//		Integer startTime = appOverTime.getWorkClockFrom1();
//		Integer endTime = appOverTime.getWorkClockTo1();
//		OverTimeDto overTimeDto = OverTimeDto.fromDomain(appOverTime);
//		overTimeDto.setOvertimeAtr(appOverTime.getOverTimeAtr().value);
//		String employeeName = employeeAdapter.getEmployeeName(appOverTime.getApplication().getEmployeeID());
//		overTimeDto.setEmployeeName(employeeName);
//		overTimeDto.setEnteredPersonName(appOverTime.getApplication().getEnteredPersonID() == null ? null : employeeAdapter.getEmployeeName(appOverTime.getApplication().getEnteredPersonID()));
//		
//		// 11_設定データを取得
//		OvertimeSettingData overtimeSettingData = getSettingData(companyID, appOverTime.getApplication().getEmployeeID(), 1, ApplicationType.OVER_TIME_APPLICATION, appOverTime.getApplication().getAppDate());
//		overTimeDto.setOvertimeSettingDataDto(this.convSettingToDto(overtimeSettingData));
//		
//		AppCommonSettingOutput appCommonSettingOutput = overtimeSettingData.appCommonSettingOutput;
//		
//		//hoatt
//		overTimeDto.setRequireAppReasonFlg(appCommonSettingOutput.getApplicationSetting().getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED) ? true : false);
//		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
//		overTimeDto.setEnableOvertimeInput(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeInputUse().value==1?true:false);
//		
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
//		AppTypeDiscreteSetting appTypeDiscreteSetting = appCommonSettingOutput.appTypeDiscreteSettings.stream()
//				.filter(x -> x.getAppType()==ApplicationType.OVER_TIME_APPLICATION).findFirst().get();
//		
//		// 時刻計算利用チェック
//		if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
//			overTimeDto.setDisplayCaculationTime(true);
//		} else {
//			overTimeDto.setDisplayCaculationTime(false);
//		}
//		
//		// 01-02-01_残業時間帯、休憩時間帯を作成
//		List<OvertimeInputDto> overTimeInputs = new ArrayList<>();
//		// 01-03_残業枠を取得: chua xong
//		overTimeDto.setAppOvertimeNightFlg(appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value);
//		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(appOverTime.getOverTimeAtr().value,companyID);
//		
//		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
//			overtimeInputDto.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
//			overtimeInputDto.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue());
//			overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
//			overTimeInputs.add(overtimeInputDto);
//		}
//		OvertimeInputDto overtimeInputShiftNight = new OvertimeInputDto();
//		overtimeInputShiftNight.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
//		overtimeInputShiftNight.setFrameNo(11);
//		overTimeInputs.add(overtimeInputShiftNight);
//		
//		OvertimeInputDto overtimeInputFlexExessTime = new OvertimeInputDto();
//		overtimeInputFlexExessTime.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
//		overtimeInputFlexExessTime.setFrameNo(12);
//		overTimeInputs.add(overtimeInputFlexExessTime);
//		
//		for(int i = 1; i < 11; i++){
//			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
//			overtimeInputDto.setAttendanceID(AttendanceType.RESTTIME.value);
//			overtimeInputDto.setFrameNo(i);
//			overtimeInputDto.setFrameName(Integer.toString(i));
//			overtimeInputDto.setTimeItemTypeAtr(0);
//			overTimeInputs.add(overtimeInputDto);
//		}
//		overTimeDto.getOverTimeInputs().forEach(item -> {
//			overTimeInputs.stream().filter(x -> 
//				(x.getAttendanceID()==item.getAttendanceID())
//				&& (x.getFrameNo()==item.getFrameNo())
//				&& (x.getTimeItemTypeAtr()==item.getTimeItemTypeAtr())
//					).findAny().ifPresent(value -> {
//						value.setStartTime(item.getStartTime());
//						value.setEndTime(item.getEndTime());
//						value.setApplicationTime(item.getApplicationTime());
//					});;
//		});
//		overTimeDto.setOverTimeInputs(overTimeInputs);
//		
//		//01-08_乖離定型理由を取得
//		if(overtimeRestAppCommonSet.getDivergenceReasonFormAtr().value == UseAtr.USE.value){
//			overTimeDto.setDisplayDivergenceReasonForm(true);
//			List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
//					.getDivergenceReasonForm(
//							companyID,
//							EnumAdaptor.valueOf(overTimeDto.getApplication().getPrePostAtr(), PrePostAtr.class),
//							overtimeRestAppCommonSet.getDivergenceReasonFormAtr(),
//							ApplicationType.OVER_TIME_APPLICATION);
//			convertToDivergenceReasonDto(divergenceReasons,overTimeDto);
//		}
//		
//		// 勤務種類の名称を取得する
//		String workTypeCD = appOverTime.getWorkTypeCode() == null ? "" : appOverTime.getWorkTypeCode().v();
//		WorkType workType = workTypeRepository.findNoAbolishByPK(companyID, workTypeCD).orElse(null);
//		String workTypeName = "" ;
//		if (workType != null) {
//			workTypeName = workType.getName().v();
//		}
//		overTimeDto.setWorkType(new WorkTypeOvertime(workTypeCD, workTypeName));
//		
//		// 就業時間帯の名称を取得する
//		String workTimeCD = appOverTime.getSiftCode() == null ? "" : appOverTime.getSiftCode().v();
//		String workTimeName = null;
//		WorkTimeSetting workTime = workTimeRepository.findByCode(companyID, workTimeCD).orElse(null);
//		if (workTime != null) {
//			workTimeName = workTime.getWorkTimeDisplayName().getWorkTimeName().v();
//		}
//		overTimeDto.setSiftType(new SiftType(workTimeCD, workTimeName));
//		
//		// 07_勤務種類取得: lay loai di lam 
//		Optional<AppEmploymentSetting> appEmploymentWorkType = appCommonSettingOutput.appEmploymentWorkType;
//		List<WorkTypeOvertime> workTypeOvertimes = overtimeService.getWorkType(companyID, appOverTime.getApplication().getEmployeeID(),approvalFunctionSetting,appEmploymentWorkType);
//		
//		List<String> workTypeCodes = new ArrayList<>();
//		for(WorkTypeOvertime workTypeOvertime : workTypeOvertimes){
//			workTypeCodes.add(workTypeOvertime.getWorkTypeCode());
//		}
//		overTimeDto.setWorkTypes(workTypeCodes);
//		
//		// 08_就業時間帯取得(lay loai gio lam viec) 
//		List<SiftType> siftTypes = overtimeService.getSiftType(companyID, appOverTime.getApplication().getEmployeeID(), approvalFunctionSetting,appOverTime.getApplication().getAppDate());
//		List<String> siftCodes = new ArrayList<>();
//		for(SiftType siftType : siftTypes){
//			siftCodes.add(siftType.getSiftCode());
//		}
//		overTimeDto.setSiftTypes(siftCodes);
//		
//		// 13_フレックス時間を表示するかチェック
//		GeneralDate baseDate = appCommonSettingOutput.generalDate;
//		if(appOvertimeSettingRepository.getAppOver().isPresent()){
//			//フレックス時間を表示するかチェック
//			overTimeDto.setFlexFLag(flexDisplayCheck(baseDate, appOverTime.getApplication().getEmployeeID()));
//			//ドメインモデル「残業申請設定」.勤種変更可否フラグがtrueの場合
//			overTimeDto.setWorkTypeChangeFlg(false);
//			if(appOvertimeSettingRepository.getAppOver().get().getWorkTypeChangeFlag().equals(Changeable.CHANGEABLE)){
//				overTimeDto.setWorkTypeChangeFlg(true);
//			}
//		}
//		
//		// 表示対象の就業時間帯を取得する
//		Optional<PredetemineTimeSetting> optFindByCode = predetemineTimeRepo.findByWorkTimeCode(companyID, workTimeCD);
//		if (optFindByCode.isPresent()) {
//			overTimeDto.setWorktimeStart(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().v());
//			overTimeDto.setWorktimeEnd(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().v());
//		}
//		
//		// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
//		boolean displayRestTime = commonOvertimeHoliday.getRestTime(
//				companyID,
//				approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
//				approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
//				ApplicationType.OVER_TIME_APPLICATION);
//		overTimeDto.setDisplayRestTime(displayRestTime);
//		if(displayRestTime) {
//			// 休憩時間帯を取得する
//			Optional<TimeWithDayAttr> opStartTime = startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime)); 
//			Optional<TimeWithDayAttr> opEndTime = endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime)); 
//			List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(companyID,workTypeCD, workTimeCD, opStartTime, opEndTime);
//			List<DeductionTimeDto> timeZones = breakTimes.stream().map(domain->{
//				DeductionTimeDto dto = new DeductionTimeDto();
//				domain.saveToMemento(dto);
//				return dto;
//			}).collect(Collectors.toList());
//			overTimeDto.setTimezones(timeZones);
//		}
//		
//		// 01-05_申請定型理由を取得
//		if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
//			// error EA refactor 4
//			/*overTimeDto.setTypicalReasonDisplayFlg(true);
//			List<ApplicationReason> applicationReasons = otherCommonAlgorithm.getApplicationReasonType(
//					companyID,
//					appTypeDiscreteSetting.getTypicalReasonDisplayFlg(),
//					ApplicationType_Old.OVER_TIME_APPLICATION);
//			List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
//			for (ApplicationReason applicationReason : applicationReasons) {
//				ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
//						applicationReason.getReasonTemp().v(), applicationReason.getDefaultFlg().value);
//				applicationReasonDtos.add(applicationReasonDto);
//			}
//			overTimeDto.setApplicationReasonDtos(applicationReasonDtos);*/
//		}else{
//			overTimeDto.setTypicalReasonDisplayFlg(false);
//		}
//		//01-06_申請理由を取得
//		overTimeDto.setDisplayAppReasonContentFlg(otherCommonAlgorithm.displayAppReasonContentFlg(appTypeDiscreteSetting.getDisplayReasonFlg()));
//		
//		//時間外表示区分
//		overTimeDto.setExtratimeDisplayFlag(overtimeRestAppCommonSet.getExtratimeDisplayAtr().value == 1 ? true : false);
//		//01-07_乖離理由を取得
//		overTimeDto.setDisplayDivergenceReasonInput(
//				commonOvertimeHoliday.displayDivergenceReasonInput(
//						EnumAdaptor.valueOf(overTimeDto.getApplication().getPrePostAtr(), PrePostAtr.class), 
//						overtimeRestAppCommonSet.getDivergenceReasonInputAtr()));
//		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
//		overTimeDto.setPerformanceExcessAtr(performanceExcessAtr.value);
//		
//		
//		
//		// xu li hien thi du lieu xin truoc
//		// hien thi du lieu thuc te
//		overTimeDto.setReferencePanelFlg(false);
//		if(overTimeDto.getApplication().getPrePostAtr() == InitValueAtr.POST.value && overtimeRestAppCommonSet.getPerformanceDisplayAtr().value == UseAtr.USE.value){
//			overTimeDto.setReferencePanelFlg(true);
//		}
//		// hien thi don xin truoc
//		overTimeDto.setAllPreAppPanelFlg(false);
//		if(overtimeRestAppCommonSet.getPreDisplayAtr().value == UseAtr.USE.value && overTimeDto.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value){
//			overTimeDto.setAllPreAppPanelFlg(true);
//		}
//		
//		if(overtimeRestAppCommonSet.getExtratimeDisplayAtr()==UseAtr.USE){
//			Map<String, AppOverTime> appOverTimeDetailMap = overtimeRepository.getListAppOvertimeFrame(companyID, Arrays.asList(appID));
//			if(!appOverTimeDetailMap.isEmpty()){
//				Optional<AppOvertimeDetail> appOvertimeDetail = appOverTimeDetailMap.entrySet().stream().findFirst().get().getValue().getAppOvertimeDetail();
//				overTimeDto.setAppOvertimeDetailDto(AppOvertimeDetailDto.fromDomain(appOvertimeDetail));
//				if(!appOvertimeDetail.isPresent()){
//					overTimeDto.setAppOvertimeDetailStatus(null);
//				} else {
//					overTimeDto.setAppOvertimeDetailStatus(overtimeService.getTime36Detail(appOvertimeDetail.get()).value);
//				}
//			}
//		}
//		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
//		overTimeDto.setPreExcessDisplaySetting(preExcessDisplaySetting.value);
//		overTimeDto.setPerformanceExcessAtr(performanceExcessAtr.value);
//		List<CaculationTime> caculationTimes = overTimeInputs.stream()
//				.map(x -> new CaculationTime(
//						companyID, 
//						appID, 
//						x.getAttendanceID(), 
//						x.getFrameNo(), 
//						0, 
//						"", 
//						x.getApplicationTime(), 
//						"", 
//						"", 
//						0, 
//						false, 
//						false, 
//						false))
//				.collect(Collectors.toList());
//		AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
//		if(appOverTime.getApplication().getPrePostAtr() == PrePostAtr.POSTERIOR) {
//			List<OvertimeColorCheck> otTimeLst = new ArrayList<>();
//			overTimeInputs.forEach(overtimeInput -> {
//				otTimeLst.add(OvertimeColorCheck.createApp(overtimeInput.getAttendanceID(), overtimeInput.getFrameNo(), overtimeInput.getApplicationTime()));
//			});
//			appOvertimeReference.setAppDateRefer(appOverTime.getApplication().getAppDate().toString(DATE_FORMAT));
//			List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
//			// アルゴリズム「残業申請設定を取得する」を実行する
//			AppOvertimeSetting appOvertimeSetting = appOvertimeSettingRepository.getAppOver().get();
//			// 07-01_事前申請状態チェック
//			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
//					companyID, 
//					appOverTime.getApplication().getEmployeeID(), 
//					appOverTime.getApplication().getAppDate(), 
//					appOverTime.getApplication().getAppType());
//			// 07-02_実績取得・状態チェック
//			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
//					companyID, 
//					appOverTime.getApplication().getEmployeeID(), 
//					appOverTime.getApplication().getAppDate(), 
//					appOverTime.getApplication().getAppType(), 
//					appOverTime.getWorkTypeCode() == null ? null : appOverTime.getWorkTypeCode().v(), 
//					appOverTime.getSiftCode() == null ? null : appOverTime.getSiftCode().v(), 
//					appOvertimeSetting.getPriorityStampSetAtr(), 
//					Optional.empty(),
//					Collections.emptyList());
//			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
//			PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(
//					preExcessDisplaySetting, 
//					performanceExcessAtr, 
//					appOverTime.getApplication().getAppType(), 
//					appOverTime.getApplication().getPrePostAtr(), 
//					Collections.emptyList(),
//					otTimeLst,
//					preAppCheckResult.opAppBefore,
//					preAppCheckResult.beforeAppStatus,
//					actualStatusCheckResult.actualLst,
//					actualStatusCheckResult.actualStatus);
//			overTimeDto.setOpAppBefore(preAppCheckResult.opAppBefore.map(x -> ApplicationDto_New.fromDomain(x)).orElse(null));
//			overTimeDto.setBeforeAppStatus(preAppCheckResult.beforeAppStatus);
//			overTimeDto.setActualStatus(actualStatusCheckResult.actualStatus.value);
//			overTimeDto.setWorkTypeActual(actualStatusCheckResult.workType);
//			overTimeDto.setWorkTimeActual(actualStatusCheckResult.workTime);
//			overTimeDto.setStartTimeActual(actualStatusCheckResult.startTime);
//			overTimeDto.setEndTimeActual(actualStatusCheckResult.endTime);
//			overTimeDto.setActualLst(actualStatusCheckResult.actualLst);
//			caculationTimes = preActualColorResult.resultLst.stream()
//					.map(x -> new CaculationTime(
//							companyID, 
//							appID, 
//							x.attendanceID, 
//							x.frameNo, 
//							0, 
//							"", 
//							x.appTime, 
//							x.preAppTime==null ? null : x.preAppTime.toString(), 
//							x.actualTime==null ? null : x.actualTime.toString(), 
//							getErrorCode(x.calcError, x.preAppError, x.actualError), 
//							false, 
//							preActualColorResult.beforeAppStatus, 
//							preActualColorResult.actualStatus==3))
//					.collect(Collectors.toList());
//			
//			//01-09_事前申請を取得
//			if(overTimeDto.isAllPreAppPanelFlg()){
//				if(!preAppCheckResult.beforeAppStatus){
//					overTimeDto.setPreAppPanelFlg(true);
//					PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
//					AppOverTime appOvertime = otherCommonAlgorithm.getPreApplication(
//							appOverTime.getApplication().getEmployeeID(),
//							appOverTime.getApplication().getPrePostAtr(),
//							overtimeRestAppCommonSet.getPreDisplayAtr(), 
//							appOverTime.getApplication().getAppDate(),
//							ApplicationType.OVER_TIME_APPLICATION);
//					convertOverTimeDto(companyID,preAppOvertimeDto,overTimeDto,appOvertime);
//				}else{
//					overTimeDto.setPreAppPanelFlg(false);
//				}
//			}
//			
//			for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//				overTimeInputsRefer.add(CaculationTime.builder()
//						.attendanceID(1)
//						.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
//						.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
//						.build());
//			}
//			if(actualStatusCheckResult.actualStatus==ActualStatus.NO_ACTUAL) {
//				appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//			} else {
//				appOvertimeReference.setWorkTypeRefer(
//						new WorkTypeOvertime(actualStatusCheckResult.workType, 
//								workTypeRepository.findByPK(companyID, actualStatusCheckResult.workType).map(x -> x.getName().toString()).orElse(null)));
//				appOvertimeReference.setSiftTypeRefer(
//						new SiftType(actualStatusCheckResult.workTime, 
//								workTimeRepository.findByCode(companyID, actualStatusCheckResult.workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
//				appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(actualStatusCheckResult.startTime, actualStatusCheckResult.endTime));
//				for(CaculationTime caculationTime : overTimeInputsRefer) {
//					caculationTime.setApplicationTime(actualStatusCheckResult.actualLst.stream()
//							.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
//							.findAny().map(y -> y.actualTime).orElse(null));
//				}
//				appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//				appOvertimeReference.setOverTimeShiftNightRefer(actualStatusCheckResult.actualLst.stream()
//						.filter(x -> x.attendanceID == 1 && x.frameNo == 11)
//						.findAny().map(y -> y.actualTime).orElse(null));
//				appOvertimeReference.setFlexExessTimeRefer(actualStatusCheckResult.actualLst.stream()
//						.filter(x -> x.attendanceID == 1 && x.frameNo == 12)
//						.findAny().map(y -> y.actualTime).orElse(null));
//				overTimeDto.setAppOvertimeReference(appOvertimeReference);
//			}
//		}
//		overTimeDto.setAppOvertimeReference(appOvertimeReference);
//		overTimeDto.setCaculationTimes(caculationTimes);
//		return overTimeDto;
		return null;
	} 
	
	private int getErrorCode(int calcError, int preAppError, int actualError){
        if(actualError > preAppError) {
            if(actualError > calcError) {
                return actualError;
            } else {
                return calcError;
            }
        } else {
            if(preAppError > calcError) {
                return preAppError;
            } else {
                return calcError;
            }
        }
    }
	
	public List<OvertimeInputDto> checkColorCaculationForUIB(List<OvertimeInputDto> overtimeHours,int prePostAtr,String appDate,String inputDate,String siftCD,
			String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests){
		
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// 6.計算処理 : 
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation.getCalculation(employeeID,
				GeneralDate.fromString(appDate, DATE_FORMAT),
				workTypeCode, siftCD, startTime, endTime, startTimeRests , endTimeRests);
		Map<Integer,TimeWithCalculationImport> overTime = dailyAttendanceTimeCaculationImport.getOverTime();
		List<OvertimeInputCaculation> overtimeInputCaculations = convertMaptoList(overTime,dailyAttendanceTimeCaculationImport.getFlexTime(),dailyAttendanceTimeCaculationImport.getMidNightTime());
		List<CaculationTime> overTimeInputs = new ArrayList<>();
		for(OvertimeInputDto overtime : overtimeHours){
			CaculationTime calcu = new CaculationTime();
			calcu.setAttendanceID(overtime.getAttendanceID());
			calcu.setFrameNo(overtime.getFrameNo());
			calcu.setApplicationTime(overtime.getApplicationTime());
			overTimeInputs.add(calcu);
		}
		
		overTimeInputs = this.overtimeFourProcess.checkDisplayColor(overTimeInputs, overtimeInputCaculations, prePostAtr, inputDate == null ? null : GeneralDateTime.fromString(inputDate, DATE_TIME_FORMAT), appDate == null ? null :GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION.value, employeeID, companyID);
		for(OvertimeInputDto overtime : overtimeHours){
			for(CaculationTime calculation : overTimeInputs){
				if(overtime.getFrameNo() == calculation.getFrameNo()){
					overtime.setErrorCode(calculation.getErrorCode());
				}
			}
		}
		return overtimeHours;
	}
	
	/**
	 * @param appDate
	 * @param prePostAtr
	 * @return
	 */
	public OverTimeDto findByChangeAppDate(String appDate,int prePostAtr,String siftCD, List<CaculationTime> overtimeHours,String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests,int overtimeAtr, String changeEmployee){
//		String companyID = AppContexts.user().companyId();
//		String employeeID = AppContexts.user().employeeId();
//		OverTimeDto result = new OverTimeDto();
//		ApplicationDto_New applicationDto = new ApplicationDto_New();
//		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
//		
//		// 01-01_残業通知情報を取得
//		int rootAtr = 1;
//		
//		// 11_設定データを取得
//		OvertimeSettingData overtimeSettingData = getSettingData(companyID, employeeID, rootAtr, ApplicationType.OVER_TIME_APPLICATION, appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
//		
//		AppCommonSettingOutput appCommonSettingOutput = overtimeSettingData.appCommonSettingOutput;
//		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  == 1 ? false : true);
//		result.setSendMailWhenApprovalFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenApprovalFlg().value == 1 ? true : false);
//		result.setSendMailWhenRegisterFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenRegisterFlg().value == 1 ? true : false);
//		applicationDto.setPrePostAtr(prePostAtr);
//		result.setApplication(applicationDto);
//		
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
//		// hien thi du lieu thuc te
//		result.setReferencePanelFlg(false);
//		if(result.getApplication().getPrePostAtr() == InitValueAtr.POST.value && overtimeRestAppCommonSet.getPerformanceDisplayAtr().value == UseAtr.USE.value){
//			result.setReferencePanelFlg(true);
//		}
//		// hien thi don xin truoc
//		result.setAllPreAppPanelFlg(false);
//		if(overtimeRestAppCommonSet.getPreDisplayAtr().value == UseAtr.USE.value && result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value){
//			result.setAllPreAppPanelFlg(true);
//		}
//		
//		// ドメインモデル「申請表示設定」．事前事後区分表示をチェックする
//		result.setDisplayPrePostFlg(AppDisplayAtr.DISPLAY.value);
//		if(appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().value == AppDisplayAtr.NOTDISPLAY.value){
//			result.setDisplayPrePostFlg(AppDisplayAtr.NOTDISPLAY.value);
//			// error EA refactor 4
//			// 3.事前事後の判断処理(事前事後非表示する場合)
//			/*PrePostAtr_Old prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(EnumAdaptor.valueOf(ApplicationType_Old.OVER_TIME_APPLICATION.value, ApplicationType_Old.class), GeneralDate.fromString(appDate, DATE_FORMAT),overtimeAtr);
//			if(prePostAtrJudgment != null){
//				prePostAtr = prePostAtrJudgment.value;
//				applicationDto.setPrePostAtr(prePostAtr);
//			}*/
//		}
//		
//		// 14_表示データを取得
//		getDisplayData(result, companyID, employeeID, appDate, overtimeSettingData, startTime, endTime);
//		
//		//01-09_事前申請を取得
//		if(result.isAllPreAppPanelFlg()){
//			if(prePostAtr  == PrePostAtr.POSTERIOR.value ){
//				AppOverTime appOvertime = otherCommonAlgorithm.getPreApplication(
//						employeeID,
//						EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
//						overtimeRestAppCommonSet.getPreDisplayAtr(), 
//						appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT),
//						ApplicationType.OVER_TIME_APPLICATION);
//				if(appOvertime != null){
//					result.setPreAppPanelFlg(true);
//					convertOverTimeDto(companyID,preAppOvertimeDto,result,appOvertime);
//				}else{
//					result.setPreAppPanelFlg(false);
//				}
//				
//			}
//		}
//		if (prePostAtr  == PrePostAtr.POSTERIOR.value ) {
//			// 01-18_実績の内容を表示し直す : chưa xử lí
//			AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
//			appOvertimeReference.setAppDateRefer(appDate);
//			List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
//			List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
//			for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//				overTimeInputsRefer.add(CaculationTime.builder()
//						.attendanceID(1)
//						.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
//						.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
//						.build());
//			}
//			if(result.getActualStatus()==ActualStatus.NO_ACTUAL.value) {
//				appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//				result.setAppOvertimeReference(appOvertimeReference);
//			} else {
//				appOvertimeReference.setWorkTypeRefer(
//						new WorkTypeOvertime(result.getWorkTypeActual(), 
//								workTypeRepository.findByPK(companyID, result.getWorkTypeActual()).map(x -> x.getName().toString()).orElse(null)));
//				appOvertimeReference.setSiftTypeRefer(
//						new SiftType(result.getWorkTimeActual(), 
//								workTimeRepository.findByCode(companyID, result.getWorkTimeActual()).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
//				appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(result.getStartTimeActual(), result.getEndTimeActual()));
//				for(CaculationTime caculationTime : overTimeInputsRefer) {
//					caculationTime.setApplicationTime(result.getActualLst().stream()
//							.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
//							.findAny().map(y -> y.actualTime).orElse(null));
//				}
//				appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//				appOvertimeReference.setOverTimeShiftNightRefer(result.getActualLst().stream()
//							.filter(x -> x.attendanceID == 1 && x.frameNo == 11)
//							.findAny().map(y -> y.actualTime).orElse(null));
//				appOvertimeReference.setFlexExessTimeRefer(result.getActualLst().stream()
//							.filter(x -> x.attendanceID == 1 && x.frameNo == 12)
//							.findAny().map(y -> y.actualTime).orElse(null));
//				result.setAppOvertimeReference(appOvertimeReference);
//			}
//		}
//		String employeeName = "";
//		if(Strings.isNotBlank(applicationDto.getApplicantSID())){
//			employeeName = employeeAdapter.getEmployeeName(applicationDto.getApplicantSID());
//		} else {
//			employeeName = employeeAdapter.getEmployeeName(employeeID);
//		}
//		result.setEmployeeName(employeeName);
//		
//		return result;
		return null;
		
	}
	
	/**
	 * 01_初期データ取得
	 * @param result
	 * @param uiType
	 * @param appDate
	 * @param companyID
	 * @param employeeID
	 * @param appCommonSettingOutput
	 * @param applicationDto
	 * @param overtimeAtr
	 * @param overTimeInputs
	 */
	private void getData(OverTimeDto result,int uiType,String appDate,String companyID,String employeeID,
			OvertimeSettingData overtimeSettingData,ApplicationDto applicationDto,
			List<OvertimeInputDto> overTimeInputs,PreAppOvertimeDto preAppOvertimeDto,Integer startTime1,Integer endTime1,String reasonContent){
//		AppCommonSettingOutput appCommonSettingOutput = overtimeSettingData.appCommonSettingOutput;
//		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
//		ApplicationSetting applicationSetting = appCommonSettingOutput.applicationSetting;
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
//		AppTypeDiscreteSetting appTypeDiscreteSetting = appCommonSettingOutput.getAppTypeDiscreteSettings().stream()
//				.filter(x -> x.getAppType()==ApplicationType.OVER_TIME_APPLICATION).findFirst().get();
//		
//		//申請日付を取得 : lay thong tin lam them
//		applicationDto.setApplicationDate(appDate);
//		
//		// 01-13_事前事後区分を取得
//		DisplayPrePost displayPrePost =	commonOvertimeHoliday.getDisplayPrePost(
//				companyID, 
//				ApplicationType.OVER_TIME_APPLICATION,
//				uiType,
//				OverTimeAtr.ALL,
//				appDate == null ? GeneralDate.today() : GeneralDate.fromString(appDate, DATE_FORMAT),
//				applicationSetting.getDisplayPrePostFlg());
//		result.setDisplayPrePostFlg(displayPrePost.getDisplayPrePostFlg());
//		applicationDto.setPrePostAtr(displayPrePost.getPrePostAtr());
//		result.setApplication(applicationDto);
//		result.setPrePostCanChangeFlg(displayPrePost.isPrePostCanChangeFlg());
//				
//		// 14_表示データを取得
//		getDisplayData(result, companyID, employeeID, appDate, overtimeSettingData, startTime1, endTime1);
//		
//		result.setEnableOvertimeInput(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeInputUse().value==1?true:false);
//		// 時刻計算利用チェック
//		if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
//			result.setDisplayCaculationTime(true);
//		} else {
//			result.setDisplayCaculationTime(false);
//		}
//		
//		// 01-03_残業枠を取得
//		result.setAppOvertimeNightFlg(appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value);
//		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
//		List<CaculationTime> overTimeHours = new ArrayList<>(); 
//		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//			CaculationTime cal = new CaculationTime();
//			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
//			overtimeInputDto.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
//			overtimeInputDto.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValueExact());
//			overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
//			cal.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
//			cal.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValueExact());
//			cal.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
//			overTimeHours.add(cal);
//			overTimeInputs.add(overtimeInputDto);
//		}
//		for(int i = 11; i<= 12;i++){
//			CaculationTime caculationTime = new CaculationTime();
//			caculationTime.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
//			caculationTime.setFrameNo(i);
//			overTimeHours.add(caculationTime);
//		}
//		
//		//時間外表示区分
//		result.setExtratimeDisplayFlag(overtimeRestAppCommonSet.getExtratimeDisplayAtr().value == 1 ? true : false);
//		result.setOverTimeInputs(overTimeInputs);
//		result.setTypicalReasonDisplayFlg(false);
//		
//		// 01-05_申請定型理由を取得
//		if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
//			result.setTypicalReasonDisplayFlg(true);
//			// error EA refactor 4
//			/*List<ApplicationReason> applicationReasons = otherCommonAlgorithm.getApplicationReasonType(
//					companyID,
//					appTypeDiscreteSetting.getTypicalReasonDisplayFlg(),
//					ApplicationType_Old.OVER_TIME_APPLICATION);
//			List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
//			for (ApplicationReason applicationReason : applicationReasons) {
//				ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
//						applicationReason.getReasonTemp().v(), applicationReason.getDefaultFlg().value);
//				applicationReasonDtos.add(applicationReasonDto);
//			}
//			result.setApplicationReasonDtos(applicationReasonDtos);*/
//		}
//		
//		//01-06_申請理由を取得
//		result.setDisplayAppReasonContentFlg(otherCommonAlgorithm.displayAppReasonContentFlg(appTypeDiscreteSetting.getDisplayReasonFlg()));
//		if(result.isDisplayAppReasonContentFlg()){
//			applicationDto.setApplicationReason(reasonContent);
//		}
//		
//		//01-08_乖離定型理由を取得
//		if(overtimeRestAppCommonSet.getDivergenceReasonFormAtr().value == UseAtr.USE.value){
//			result.setDisplayDivergenceReasonForm(true);
//			List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
//					.getDivergenceReasonForm(
//							companyID,
//							EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class),
//							overtimeRestAppCommonSet.getDivergenceReasonFormAtr(),
//							ApplicationType.OVER_TIME_APPLICATION);
//			convertToDivergenceReasonDto(divergenceReasons,result);
//		}
//		
//		//01-07_乖離理由を取得
//		result.setDisplayDivergenceReasonInput(
//				commonOvertimeHoliday.displayDivergenceReasonInput(
//						EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class), 
//						overtimeRestAppCommonSet.getDivergenceReasonInputAtr()));
//		
//		// xu li hien thi du lieu xin truoc
//		if(overtimeRestAppCommonSet.getPerformanceDisplayAtr().value == UseAtr.USE.value){
//			//dung cho thay doi xin truoc xin sau
//			result.setPerformanceDisplayAtr(true);
//		}
//		if(overtimeRestAppCommonSet.getPreDisplayAtr().value == UseAtr.USE.value){
//			result.setPreDisplayAtr(true);
//		}
//		// hien thi du lieu thuc te
//		if(result.getApplication().getPrePostAtr() == InitValueAtr.POST.value && overtimeRestAppCommonSet.getPerformanceDisplayAtr().value == UseAtr.USE.value){
//			result.setReferencePanelFlg(true);
//		}
//		// hien thi don xin truoc
//		if(overtimeRestAppCommonSet.getPreDisplayAtr().value == UseAtr.USE.value && result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value){
//			result.setAllPreAppPanelFlg(true);
//		}else{
//			result.setAllPreAppPanelFlg(false);
//		}
//		if(result.isAllPreAppPanelFlg()){
//			// 01-09_事前申請を取得
//			// 07-01_事前申請状態チェック
//			if(result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value ){
//				result.setPreAppPanelFlg(false);
//				AppOverTime appOvertime = otherCommonAlgorithm.getPreApplication(
//						employeeID,
//						EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class),
//						overtimeRestAppCommonSet.getPreDisplayAtr(), 
//						appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT),
//						ApplicationType.OVER_TIME_APPLICATION);
//				if(appOvertime != null){
//					result.setPreAppPanelFlg(true);
//					result.setOpAppBefore(ApplicationDto_New.fromDomain(appOvertime.getApplication()));
//					result.setBeforeAppStatus(true);
//					convertOverTimeDto(companyID,preAppOvertimeDto,result,appOvertime);
//				}			
//			}
//		}
//		if(result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value && appDate != null){
//			// 01-18_実績の内容を表示し直す
//			// 07-02_実績取得・状態チェック
//			if (approvalFunctionSetting != null) {
//				AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
//				appOvertimeReference.setAppDateRefer(appDate);
//				List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
//				for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//					overTimeInputsRefer.add(CaculationTime.builder()
//							.attendanceID(1)
//							.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
//							.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
//							.build());
//				}
//				if(result.getActualStatus()==ActualStatus.NO_ACTUAL.value) {
//					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//					result.setAppOvertimeReference(appOvertimeReference);
//					result.setActualLst(null);
//				} else {
//					appOvertimeReference.setWorkTypeRefer(
//							new WorkTypeOvertime(result.getWorkTypeActual(), 
//									workTypeRepository.findByPK(companyID, result.getWorkTypeActual()).map(x -> x.getName().toString()).orElse(null)));
//					appOvertimeReference.setSiftTypeRefer(
//							new SiftType(result.getWorkTimeActual(), 
//									workTimeRepository.findByCode(companyID, result.getWorkTimeActual()).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
//					appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(result.getStartTimeActual(), result.getEndTimeActual()));
//					
//					for(CaculationTime caculationTime : overTimeInputsRefer) {
//						caculationTime.setApplicationTime(result.getActualLst().stream()
//								.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
//								.findAny().map(y -> y.actualTime).orElse(null));
//					}
//					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//					result.setAppOvertimeReference(appOvertimeReference);
//				}
//			}
//		}
//		
//		// 19_計算処理
//		if(result.getApplication().getApplicationDate() != null
//				&& result.getWorkClockFrom1() != null
//				&& result.getWorkClockTo1() != null
//				&& result.getWorkType() != null
//				&& !StringUtils.isEmpty(result.getWorkType().getWorkTypeCode()) 
//				&& result.getSiftType() != null
//				&& !StringUtils.isEmpty(result.getSiftType().getSiftCode())
//				&& result.isDisplayCaculationTime()){
//			result.setCaculationTimes(this.getCaculationValue(overTimeHours,
//					Collections.emptyList(),
//					result.getApplication().getPrePostAtr(), 
//					appDate,
//					result.getSiftType().getSiftCode(),
//					result.getWorkType().getWorkTypeCode(),
//					result.getWorkClockFrom1(),
//					result.getWorkClockTo1(),
//					result.getTimezones().stream().map(x -> x.getStart()).collect(Collectors.toList()),
//					result.getTimezones().stream().map(x -> x.getEnd()).collect(Collectors.toList())));
//			result.setResultCaculationTimeFlg(true);
//			
//		}
//		result.setApplication(applicationDto);
//		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
//		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
//		result.setPreExcessDisplaySetting(preExcessDisplaySetting.value);
//		result.setPerformanceExcessAtr(performanceExcessAtr.value);
	}

	/**
	 * @param divergenceReasons
	 * @param result
	 */
	private void convertToDivergenceReasonDto(List<DivergenceReason> divergenceReasons, OverTimeDto result){
		List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
		for(DivergenceReason divergenceReason : divergenceReasons){
			DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
			divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
			divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
			// divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
			
			divergenceReasonDtos.add(divergenceReasonDto);
		}
		result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	
	/**
	 * @param companyID
	 * @param applicationDto
	 * @param result
	 * @param appOvertime
	 */
	private void convertOverTimeDto(String companyID,PreAppOvertimeDto preAppOvertimeDto, OverTimeDto result,AppOverTime appOvertime){
//		if(appOvertime.getApplication() != null){
//			if(appOvertime.getApplication().getAppDate() != null){
//				preAppOvertimeDto.setAppDatePre(appOvertime.getApplication().getAppDate().toString(DATE_FORMAT));
//			}
//		}
//		
//		if (appOvertime.getWorkTypeCode() != null) {
//			WorkTypeOvertime workTypeOvertime = new WorkTypeOvertime();
//			workTypeOvertime.setWorkTypeCode(appOvertime.getWorkTypeCode().toString());
//			Optional<WorkType> workType = workTypeRepository.findNoAbolishByPK(companyID,
//					appOvertime.getWorkTypeCode().toString());
//			if (workType.isPresent()) {
//				workTypeOvertime.setWorkTypeName(workType.get().getName().toString());
//			}
//			preAppOvertimeDto.setWorkTypePre(workTypeOvertime);
//		}
//		if (appOvertime.getSiftCode() != null) {
//			SiftType siftType = new SiftType();
//
//			siftType.setSiftCode(appOvertime.getSiftCode().toString().equals("000")? "" : appOvertime.getSiftCode().toString());
//			Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(companyID,
//					appOvertime.getSiftCode().toString()); 
//			if (workTime.isPresent()) {
//                siftType.setSiftName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().v());
//			}
//			preAppOvertimeDto.setSiftTypePre(siftType);
//		}
//
//		preAppOvertimeDto.setWorkClockFromTo1Pre(convertWorkClockFromTo(appOvertime.getWorkClockFrom1(),appOvertime.getWorkClockTo1()));
//		preAppOvertimeDto.setWorkClockFromTo2Pre(convertWorkClockFromTo(appOvertime.getWorkClockFrom2(),appOvertime.getWorkClockTo2()));
//
//		
//		List<OvertimeInputDto> overtimeInputDtos = new ArrayList<>();
//		List<OverTimeInput> overtimeInputs = appOvertime.getOverTimeInput();
//		if (overtimeInputs != null && !overtimeInputs.isEmpty()) {
//			List<Integer> frameNo = new ArrayList<>();
//			for (OverTimeInput overTimeInput : overtimeInputs) {
//				OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
//				overtimeInputDto.setAttendanceID(overTimeInput.getAttendanceType().value);
//				overtimeInputDto.setFrameNo(overTimeInput.getFrameNo());
//				overtimeInputDto.setStartTime(overTimeInput.getStartTime() == null ? null : overTimeInput.getStartTime().v());
//				overtimeInputDto.setEndTime(overTimeInput.getEndTime() == null ? null : overTimeInput.getEndTime().v());
//				overtimeInputDto.setApplicationTime(overTimeInput.getApplicationTime()== null ? null : overTimeInput.getApplicationTime().v());
//				overtimeInputDtos.add(overtimeInputDto);
//				frameNo.add(overTimeInput.getFrameNo());
//			}
//			List<OvertimeWorkFrame> overtimeFrames = this.overtimeFrameRepository.getOvertimeWorkFrameByFrameNos(companyID,frameNo);
//			for (OvertimeInputDto overtimeInputDto : overtimeInputDtos) {
//				for (OvertimeWorkFrame overtimeFrame : overtimeFrames) {
//					if (overtimeInputDto.getFrameNo() == overtimeFrame.getOvertimeWorkFrNo().v().intValueExact()) {
//						overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
//						continue;
//					}
//				}
//			}
//			preAppOvertimeDto.setOverTimeInputsPre(overtimeInputDtos);
//			preAppOvertimeDto.setOverTimeShiftNightPre(appOvertime.getOverTimeShiftNight());
//			preAppOvertimeDto.setFlexExessTimePre(appOvertime.getFlexExessTime());
//			
//		}
//		result.setPreAppOvertimeDto(preAppOvertimeDto);
	}
	private String convertWorkClockFromTo(Integer startTime, Integer endTime){
		String WorkClockFromTo = "";
		if(startTime == null && endTime != null){
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
			WorkClockFromTo = "　"
					+  "　~　"
					+ endTimeWithDay.getDayDivision().description
					+ convert(endTime);
		}
		if(startTime != null && endTime != null){
			TimeWithDayAttr startTimeWithDay = new TimeWithDayAttr(startTime);
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
		 WorkClockFromTo = startTimeWithDay.getDayDivision().description 
							+ convert(startTime) + "　~　"
							+ endTimeWithDay.getDayDivision().description
							+ convert(endTime);
		}
		return WorkClockFromTo;
	}
	private String convert(Integer minute) {
		if(minute == null){
			return null;
		}
		TimeWithDayAttr timeConvert = new TimeWithDayAttr(minute);
		return timeConvert.getInDayTimeWithFormat();
	}


	
	/**
	 * 01-14_勤務時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param siftCD
	 * @return
	 */
	public RecordWorkDto getRecordWork(String employeeID, String appDate, String siftCD,int prePortAtr,List<CaculationTime> overtimeHours,
			String workTypeCode,List<Integer> startRestTimes,List<Integer> endRestTimes, boolean restTimeDisFlg,
			OvertimeSettingDataDto overtimeSettingDataDto){
		
//		String companyID = AppContexts.user().companyId();
//		Integer startTime1 = null; 
//		Integer endTime1 = null;
//		Integer startTime2 = null;
//		Integer endTime2 = null;
//		AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
//		AppCommonSettingOutputDto appCommonSettingOutput = overtimeSettingDataDto.appCommonSettingOutput;
//		ApprovalFunctionSettingDto approvalFunctionSettingDto = appCommonSettingOutput.getApprovalFunctionSetting();
//		ApprovalFunctionSetting approvalFunctionSetting = ApprovalFunctionSettingDto.createFromJavaType(approvalFunctionSettingDto);
//		// 01-14_勤務時間取得(lay thoi gian): Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」)
//		RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(
//				companyID, 
//				employeeID, 
//				appDate == null ? null : GeneralDate.fromString(appDate, "yyyy/MM/dd"), 
//				approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
//				approvalFunctionSetting.getApplicationDetailSetting().get().getAtworkTimeBeginDisp(),
//				ApplicationType.OVER_TIME_APPLICATION, 
//				siftCD, 
//				Optional.empty(),
//				Optional.empty(), 
//				approvalFunctionSetting);
//		startTime1 = recordWorkOutput.getStartTime1();
//		endTime1 = recordWorkOutput.getEndTime1();
//		startTime2 = recordWorkOutput.getStartTime2();
//		endTime2 = recordWorkOutput.getEndTime2();
//		List<DeductionTimeDto> timeZones = new ArrayList<>();
//		if(restTimeDisFlg){
//			Optional<TimeWithDayAttr> opStartTime = startTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime1)); 
//			Optional<TimeWithDayAttr> opEndTime = endTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime1));
//			//休憩時間帯を取得する
//			List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(companyID,workTypeCode, siftCD, opStartTime, opEndTime);
//			timeZones = breakTimes.stream().map(domain->{
//				DeductionTimeDto dto = new DeductionTimeDto();
//				domain.saveToMemento(dto);
//				return dto;
//			}).collect(Collectors.toList());
//			// 01-18_実績の内容を表示し直す
//			if(appDate!=null) {
//				AppOvertimeSetting appOvertimeSetting = appOvertimeSettingRepository.getAppOver().get();
//				ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck
//						.actualStatusCheck(companyID, employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION, 
//								workTypeCode, siftCD, appOvertimeSetting.getPriorityStampSetAtr(), Optional.empty(), breakTimes);
//				appOvertimeReference.setAppDateRefer(appDate);
//				List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
//				List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
//				for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//					overTimeInputsRefer.add(CaculationTime.builder()
//							.attendanceID(1)
//							.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
//							.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
//							.build());
//				}
//				if(actualStatusCheckResult.actualStatus==ActualStatus.NO_ACTUAL) {
//					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//				} else {
//					appOvertimeReference.setWorkTypeRefer(
//							new WorkTypeOvertime(actualStatusCheckResult.workType, 
//									workTypeRepository.findByPK(companyID, actualStatusCheckResult.workType).map(x -> x.getName().toString()).orElse(null)));
//					appOvertimeReference.setSiftTypeRefer(
//							new SiftType(actualStatusCheckResult.workTime, 
//									workTimeRepository.findByCode(companyID, actualStatusCheckResult.workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
//					appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(actualStatusCheckResult.startTime, actualStatusCheckResult.endTime));
//					for(CaculationTime caculationTime : overTimeInputsRefer) {
//						caculationTime.setApplicationTime(actualStatusCheckResult.actualLst.stream()
//								.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
//								.findAny().map(y -> y.actualTime).orElse(null));
//					}
//					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//					appOvertimeReference.setOverTimeShiftNightRefer(actualStatusCheckResult.actualLst.stream()
//							.filter(x -> x.attendanceID == 1 && x.frameNo == 11)
//							.findAny().map(y -> y.actualTime).orElse(null));
//					appOvertimeReference.setFlexExessTimeRefer(actualStatusCheckResult.actualLst.stream()
//							.filter(x -> x.attendanceID == 1 && x.frameNo == 12)
//							.findAny().map(y -> y.actualTime).orElse(null));
//				}
//			}
//		}
//		return new RecordWorkDto(startTime1, endTime1, startTime2, endTime2, appOvertimeReference, timeZones, null);
		return null;
	} 
	
	public AppOverTimeMobDto getDetailMob(String appID, AppCommonSettingOutput appCommonSettingOutput) {
//		AppOverTimeMobDto appOverTimeMobDto = new AppOverTimeMobDto();
//		String companyID = AppContexts.user().companyId();
//		Optional<AppOverTime> opAppOverTime = overtimeRepository.getFullAppOvertime(companyID, appID);
//		if(!opAppOverTime.isPresent()){
//			throw new BusinessException("Msg_198");
//		}
//		AppOverTime appOverTime = opAppOverTime.get();
//		Application application = appOverTime.getApplication();
//		appOverTimeMobDto.applicant = employeeAdapter.getEmployeeName(application.getEmployeeID());
//		if(!application.getEmployeeID().equals(application.getEnteredPersonID())){
//			appOverTimeMobDto.representer = employeeAdapter.getEmployeeName(application.getEnteredPersonID());
//		}
//		appOverTimeMobDto.appDate = application.getAppDate().toString(DATE_FORMAT);
//		appOverTimeMobDto.inputDate = application.getInputDate().toString(DATE_TIME_FORMAT);
//		appOverTimeMobDto.appType = application.getAppType().value;
//		appOverTimeMobDto.prePostAtr = application.getPrePostAtr().value;
//		appOverTimeMobDto.workTypeCD = appOverTime.getWorkTypeCode()==null ? null : appOverTime.getWorkTypeCode().v();
//		appOverTimeMobDto.workTimeCD = appOverTime.getSiftCode() == null ? null : appOverTime.getSiftCode().v() ;
//		appOverTimeMobDto.startTime = appOverTime.getWorkClockFrom1();
//		appOverTimeMobDto.endTime = appOverTime.getWorkClockTo1();
//		appOverTimeMobDto.appReason = application.getAppReason().v();
//		appOverTimeMobDto.divergenceReasonContent = appOverTime.getDivergenceReason();
//		appOverTimeMobDto.frameLst = new ArrayList<>();
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
//				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value).get();
//		if(overtimeRestAppCommonSet.getBonusTimeDisplayAtr().value == UseAtr.USE.value){
//			appOverTimeMobDto.displayBonusTime = true;
//		} 
//		List<OvertimeColorCheck> otTimeLst = new ArrayList<>();
//		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
//		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//			appOverTimeMobDto.frameLst.add(new OvertimeFrameDto(
//					AttendanceType.NORMALOVERTIME.value, 
//					overtimeFrame.getOvertimeWorkFrNo().v().intValue(), 
//					overtimeFrame.getOvertimeWorkFrName().toString()));
//			otTimeLst.add(OvertimeColorCheck.createApp(
//					AttendanceType.NORMALOVERTIME.value, 
//					overtimeFrame.getOvertimeWorkFrNo().v().intValue(), 
//					null));
//		}
//		appOverTimeMobDto.appOvertimeNightFlg = appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value == 1 ? true : false;
//		
//		//フレックス時間を表示するかチェック
//		appOverTimeMobDto.flexFLag = flexDisplayCheck(application.getAppDate(), application.getEmployeeID());
//
//		if(appOverTimeMobDto.appOvertimeNightFlg) {
//			otTimeLst.add(OvertimeColorCheck.createApp(AttendanceType.NORMALOVERTIME.value, 11, null));
//		}
//		if(appOverTimeMobDto.flexFLag) {
//			otTimeLst.add(OvertimeColorCheck.createApp(AttendanceType.NORMALOVERTIME.value, 12, null));
//		}
//		if(appOverTimeMobDto.displayBonusTime){
//			List<BonusPayTimeItem> bonusPayTimeItems= this.commonOvertimeHoliday.getBonusTime(
//					companyID,
//					appOverTime.getApplication().getEmployeeID(),
//					appOverTime.getApplication().getAppDate(),
//					overtimeRestAppCommonSet.getBonusTimeDisplayAtr());
//			for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItems){
//				appOverTimeMobDto.frameLst.add(new OvertimeFrameDto(
//						AttendanceType.BONUSPAYTIME.value, 
//						bonusPayTimeItem.getId(), 
//						bonusPayTimeItem.getTimeItemName().toString()));
//				otTimeLst.add(OvertimeColorCheck.createApp(
//						AttendanceType.BONUSPAYTIME.value, 
//						bonusPayTimeItem.getId(), 
//						null));
//			}
//		}
//		List<OvertimeBreakDto> breakTimeLst = appOverTime.getOverTimeInput().stream()
//				.filter(x -> x.getAttendanceType().value==0)
//				.map(x -> new OvertimeBreakDto(x.getFrameNo(), x.getStartTime().v(), x.getEndTime().v()))
//				.collect(Collectors.toList());
//		appOverTimeMobDto.breakTimeLst = breakTimeLst;
//		otTimeLst = otTimeLst.stream().map(x -> {
//			Integer value = appOverTime.getOverTimeInput().stream()
//			.filter(y -> y.getAttendanceType().value==x.attendanceID && y.getFrameNo()==x.frameNo)
//			.findAny().map(z -> z.getApplicationTime().v()).orElse(null);
//			return OvertimeColorCheck.createApp(x.attendanceID, x.frameNo, value);
//		}).collect(Collectors.toList());
//		if(appOverTime.getWorkTypeCode()!=null && appOverTime.getSiftCode()!=null) {
//			// // アルゴリズム「残業申請設定を取得する」を実行する
//			UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
//			AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
//			AppOvertimeSetting appOvertimeSetting = appOvertimeSettingRepository.getAppOver().get();
//			// 07-01_事前申請状態チェック
//			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
//					companyID, 
//					application.getEmployeeID(), 
//					application.getAppDate(), 
//					application.getAppType());
//			// 07-02_実績取得・状態チェック
//			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
//					companyID, 
//					application.getEmployeeID(), 
//					application.getAppDate(), 
//					application.getAppType(), 
//					appOverTime.getWorkTypeCode() == null ? null : appOverTime.getWorkTypeCode().v(), 
//					appOverTime.getSiftCode() == null ? null : appOverTime.getSiftCode().v(), 
//					appOvertimeSetting.getPriorityStampSetAtr(), 
//					Optional.empty(),
//					Collections.emptyList());
//			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
//			PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(
//					preExcessDisplaySetting, 
//					performanceExcessAtr, 
//					application.getAppType(), 
//					application.getPrePostAtr(), 
//					Collections.emptyList(),
//					otTimeLst,
//					preAppCheckResult.opAppBefore,
//					preAppCheckResult.beforeAppStatus,
//					actualStatusCheckResult.actualLst,
//					actualStatusCheckResult.actualStatus);
//			appOverTimeMobDto.timeLst = preActualColorResult.resultLst;
//		} else {
//			appOverTimeMobDto.timeLst = otTimeLst;
//		}
//		// appOverTimeMobDto.timeLst = Collections.emptyList();
//		// アルゴリズム「勤務種類名称を取得する」を実行する
//		if(appOverTime.getWorkTypeCode()!=null){
//			appOverTimeMobDto.workTypeName = workTypeRepository.findByPK(companyID, appOverTimeMobDto.workTypeCD)
//				.map(x -> x.getName().v()).orElse(null);
//		}
//		// アルゴリズム「就業時間帯名称を取得する」を実行する
//		if(appOverTime.getSiftCode()!=null){
//			appOverTimeMobDto.workTimeName = workTimeRepository.findByCode(companyID, appOverTimeMobDto.workTimeCD)
//				.map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null);
//		}
//		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
//		if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
//			appOverTimeMobDto.displayCaculationTime = true;
//		}
//		appOverTimeMobDto.displayRestTime = commonOvertimeHoliday.getRestTime(
//				companyID,
//				approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
//				approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
//				ApplicationType.OVER_TIME_APPLICATION);
//		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository
//				.getAppTypeDiscreteSettingByAppType(companyID,  ApplicationType.OVER_TIME_APPLICATION.value);
//		if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
//			appOverTimeMobDto.typicalReasonDisplayFlg = true;
//		}
//		appOverTimeMobDto.displayAppReasonContentFlg = otherCommonAlgorithm.displayAppReasonContentFlg(appTypeDiscreteSetting.get().getDisplayReasonFlg());
//		if(appOverTimeMobDto.prePostAtr != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.getDivergenceReasonFormAtr().value == UseAtr.USE.value){
//			appOverTimeMobDto.displayDivergenceReasonForm = true;
//		}
//		appOverTimeMobDto.displayDivergenceReasonInput = commonOvertimeHoliday.displayDivergenceReasonInput(
//						EnumAdaptor.valueOf(appOverTimeMobDto.prePostAtr, PrePostAtr.class), overtimeRestAppCommonSet.getDivergenceReasonInputAtr());
//		return appOverTimeMobDto;
		return null;
	}
	
	/**
	 * フレックス時間を表示するかチェック
	 * 
	 * @param baseDate
	 * @param employeeID
	 * @return フレックス時間を表示する区分
	 */
	public boolean flexDisplayCheck(GeneralDate baseDate, String employeeID) {
//		if (appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.DISPLAY)) {
//			Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeID, baseDate);
//			if (personalLablorCodition.isPresent()) {
//				if (personalLablorCodition.get().getLaborSystem().isFlexTimeWork()) {
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				return false;
//			}
//		} else if (appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.ALWAYSDISPLAY)) {
//			return true;
//		} else {
//			return false;
//		}
		return true;
	}
	
	/**
	 * 11_設定データを取得
	 * @param companyID
	 * @param employeeID
	 * @param rootAtr
	 * @param appType
	 * @param date
	 * @return
	 */
	private OvertimeSettingData getSettingData(String companyID, String employeeID, Integer rootAtr, ApplicationType appType, GeneralDate date) {
		OvertimeSettingData overtimeSettingData = new OvertimeSettingData(); 
		// 1-1.新規画面起動前申請共通設定を取得する
//		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
//				companyID, employeeID, rootAtr, ApplicationType.OVER_TIME_APPLICATION, date);
//		
//		// ドメインモデル「申請承認機能設定」．「申請利用設定」．利用区分をチェックする(check 利用区分 trong domain 「申請承認機能設定」．「申請利用設定」  )
//		if (appCommonSettingOutput.getApprovalFunctionSetting().getAppUseSetting().getUseDivision() == UseDivision.NOT_USE) {
//			// 利用区分が利用しない
//			throw new BusinessException("Msg_323");
//		}
//		overtimeSettingData.appCommonSettingOutput = appCommonSettingOutput;
//		
//		// 残業休出申請共通設定を取得
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
//				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value).get();
//		overtimeSettingData.overtimeRestAppCommonSet = overtimeRestAppCommonSet;
//		
//		// 残業申請設定を取得する
//		AppOvertimeSetting appOvertimeSetting = appOvertimeSettingRepository.getAppOver().get();
//		overtimeSettingData.appOvertimeSetting = appOvertimeSetting;
		
		return overtimeSettingData;
	}
	
	/**
	 * 14_表示データを取得
	 * @param result
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param overtimeSettingData
	 * @param startTime1
	 * @param endTime1
	 */
	private void getDisplayData(OverTimeDto result, String companyID, String employeeID, String appDate, OvertimeSettingData overtimeSettingData,
			Integer startTime1, Integer endTime1) {
//		GeneralDate generalAppDate = Strings.isBlank(appDate) ? GeneralDate.today() : GeneralDate.fromString(appDate, DATE_FORMAT);
//		AppCommonSettingOutput appCommonSettingOutput = overtimeSettingData.appCommonSettingOutput;
//		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
//		AppOvertimeSetting appOvertimeSetting = overtimeSettingData.appOvertimeSetting;
//		
//		// 01-01_残業通知情報を取得
//		OvertimeInstructInfomation overtimeInstructInfomation = iOvertimePreProcess.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
//		result.setDisplayOvertimeInstructInforFlg(overtimeInstructInfomation.isDisplayOvertimeInstructInforFlg());
//		result.setOvertimeInstructInformation(overtimeInstructInfomation.getOvertimeInstructInfomation());
//		
//		//01-02_時間外労働を取得
//		Optional<AgreeOverTimeOutput> opAgreeOverTimeOutput = commonOvertimeHoliday
//				.getAgreementTime(companyID, employeeID, ApplicationType.OVER_TIME_APPLICATION);
//		if(opAgreeOverTimeOutput.isPresent()){
//			result.setAgreementTimeDto(AgreeOverTimeDto.fromDomain(opAgreeOverTimeOutput.get()));
//		}
//		
//		Optional<AppEmploymentSetting> appEmploymentWorkType = appCommonSettingOutput.appEmploymentWorkType;
//		// 07_勤務種類取得: lay loai di lam 
//		List<WorkTypeOvertime> workTypeOvertimes = overtimeService.getWorkType(companyID, employeeID, approvalFunctionSetting, appEmploymentWorkType);
//		List<String> workTypeCodes = new ArrayList<>();
//		for(WorkTypeOvertime workTypeOvertime : workTypeOvertimes){
//			workTypeCodes.add(workTypeOvertime.getWorkTypeCode());
//		}
//		result.setWorkTypes(workTypeCodes);
//		
//		GeneralDate baseDate = appCommonSettingOutput.generalDate;
//		// 08_就業時間帯取得(lay loai gio lam viec) 
//		List<SiftType> siftTypes = overtimeService.getSiftType(companyID, employeeID, approvalFunctionSetting, baseDate);
//		List<String> siftCodes = new ArrayList<>();
//		for(SiftType siftType : siftTypes){
//			siftCodes.add(siftType.getSiftCode());
//		}
//		result.setSiftTypes(siftCodes);
//		
//		// 09_勤務種類就業時間帯の初期選択をセットする
//		WorkTypeAndSiftType workTypeAndSiftType = overtimeService.getWorkTypeAndSiftTypeByPersonCon(companyID, employeeID, 
//				Strings.isBlank(appDate) ? null : GeneralDate.fromString(appDate, "yyyy/MM/dd"), workTypeOvertimes, siftTypes);
//		result.setWorkType(workTypeAndSiftType.getWorkType());
//		result.setSiftType(workTypeAndSiftType.getSiftType());
//		
//		// 表示対象の就業時間帯を取得する
//		if (null != workTypeAndSiftType.getSiftType()) {
//			Optional<PredetemineTimeSetting> optFindByCode = predetemineTimeRepo.findByWorkTimeCode(companyID, workTypeAndSiftType.getSiftType().getSiftCode());
//			if(optFindByCode.isPresent()){
//				result.setWorktimeStart(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().v());
//				result.setWorktimeEnd(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().v());
//			}
//		}
//		
//		// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
//		RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(
//				companyID, 
//				employeeID, 
//				appDate == null ? null : GeneralDate.fromString(appDate, "yyyy/MM/dd"), 
//				approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
//				approvalFunctionSetting.getApplicationDetailSetting().get().getAtworkTimeBeginDisp(),
//				ApplicationType.OVER_TIME_APPLICATION, 
//				result.getSiftType() == null? "" :result.getSiftType().getSiftCode(), 
//				Optional.empty(), 
//				Optional.empty(), 
//				approvalFunctionSetting);
//		result.setDisplayCaculationTime(BooleanUtils.toBoolean(recordWorkOutput.getRecordWorkDisplay().value));
//		result.setWorkClockFrom1(recordWorkOutput.getStartTime1());
//		result.setWorkClockFrom2(recordWorkOutput.getStartTime2());
//		result.setWorkClockTo1(recordWorkOutput.getEndTime1());
//		result.setWorkClockTo2(recordWorkOutput.getEndTime2());
//		if(startTime1 != null){
//			result.setWorkClockFrom1(startTime1);
//		}
//		if(endTime1 != null){
//			result.setWorkClockTo1(endTime1);
//		}
//		
//		// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
//		boolean displayRestTime = commonOvertimeHoliday.getRestTime(
//				companyID,
//				approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
//				approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
//				ApplicationType.OVER_TIME_APPLICATION);
//		result.setDisplayRestTime(displayRestTime);
//		if(displayRestTime) {
//			// 休憩時間帯を取得する
//			Optional<TimeWithDayAttr> opStartTime = startTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime1)); 
//			Optional<TimeWithDayAttr> opEndTime = endTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime1)); 
//			// 01-01_休憩時間を取得する
//			List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(
//					companyID, 
//					result.getWorkType() == null ? null : result.getWorkType().getWorkTypeCode(), 
//					result.getSiftType() ==  null ? null : result.getSiftType().getSiftCode(), 
//					opStartTime, 
//					opEndTime);
//			List<DeductionTimeDto> timeZones = breakTimes.stream().map(domain->{
//				DeductionTimeDto dto = new DeductionTimeDto();
//				domain.saveToMemento(dto);
//				return dto;
//			}).collect(Collectors.toList());
//			result.setTimezones(timeZones);
//		}
//		
//		// 13_フレックス時間を表示するかチェック
//		if(appOvertimeSettingRepository.getAppOver().isPresent()){
//			//フレックス時間を表示するかチェック
//			result.setFlexFLag(flexDisplayCheck(baseDate, employeeID));
//			//ドメインモデル「残業申請設定」.勤種変更可否フラグがtrueの場合
//			result.setWorkTypeChangeFlg(false);
//			if(appOvertimeSettingRepository.getAppOver().get().getWorkTypeChangeFlag().equals(Changeable.CHANGEABLE)){
//				result.setWorkTypeChangeFlg(true);
//			}
//		}
//		
//		// Input．事前事後区分　AND　Input.申請日をチェック
//		if(result.getApplication().getPrePostAtr() == PrePostAtr.POSTERIOR.value && appDate != null){
//			// 07-01_事前申請状態チェック
//			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
//					companyID, 
//					employeeID, 
//					generalAppDate, 
//					ApplicationType.OVER_TIME_APPLICATION);
//			// 07-02_実績取得・状態チェック
//			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck
//					.actualStatusCheck(companyID, employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION, 
//							result.getWorkType() == null ? null : result.getWorkType().getWorkTypeCode(), 
//							result.getSiftType() ==  null ? null : result.getSiftType().getSiftCode(), 
//							appOvertimeSetting.getPriorityStampSetAtr(), Optional.empty(), Collections.emptyList());
//			result.setOpAppBefore(preAppCheckResult.opAppBefore.map(x -> ApplicationDto_New.fromDomain(x)).orElse(null));
//			result.setBeforeAppStatus(preAppCheckResult.beforeAppStatus);
//			result.setActualStatus(actualStatusCheckResult.actualStatus.value);
//			result.setWorkTypeActual(actualStatusCheckResult.workType);
//			result.setWorkTimeActual(actualStatusCheckResult.workTime);
//			result.setStartTimeActual(actualStatusCheckResult.startTime);
//			result.setEndTimeActual(actualStatusCheckResult.endTime);
//			result.setActualLst(actualStatusCheckResult.actualLst);
//		}
	}
}
