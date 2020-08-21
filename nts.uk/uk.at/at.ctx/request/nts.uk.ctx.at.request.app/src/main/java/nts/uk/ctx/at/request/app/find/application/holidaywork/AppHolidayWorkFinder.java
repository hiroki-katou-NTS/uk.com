package nts.uk.ctx.at.request.app.find.application.holidaywork;

/*import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone.BreakTimeZoneSharedOutPut;*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHdWorkDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HolidayWorkDetailDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.RecordWorkDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkDetailOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.DeductionTimeDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Stateless
public class AppHolidayWorkFinder {
	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
//	@Inject
//	private CollectAchievement collectAchievement;
	/*@Inject
	private HolidayPreProcess holidayPreProcess;
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;*/
	@Inject
	private HolidayService holidayService;
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	/*@Inject
	private EmployeeRequestAdapter employeeAdapter;*/
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	/*@Inject
	private HolidaySixProcess holidaySixProcess; */
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	/*@Inject
	private BeforePreBootMode beforePreBootMode;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;*/
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private WithdrawalAppSetRepository withdrawalAppSetRepository;
	
	/*@Inject 
	private InitMode initMode;*/
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	/*@Inject
	private OvertimeService overtimeService;*/

	/**
	 * 1.休出申請（新規）起動処理
	 * getAppHolidayWork for start UI KAF010A
	 * @param appDateInput
	 * @param uiType
	 * @return
	 */
	public AppHdWorkDispInfoDto getAppHolidayWork(String appDate,int uiType,List<String> lstEmployee,Integer payoutType,String employeeID, AppHolidayWorkDto result){
		/*String companyID = AppContexts.user().companyId();
		if(CollectionUtil.isEmpty(lstEmployee) && employeeID == null){
			 employeeID = AppContexts.user().employeeId();
		}else if(!CollectionUtil.isEmpty(lstEmployee)){
			employeeID = lstEmployee.get(0);
			List<EmployeeInfoImport> employees = this.atEmployeeAdapter.getByListSID(lstEmployee);
			if(!CollectionUtil.isEmpty(employees)){
				List<EmployeeOvertimeDto> employeeOTs = new ArrayList<>();
				for(EmployeeInfoImport emp : employees){
					EmployeeOvertimeDto employeeOT = new EmployeeOvertimeDto(emp.getSid(), emp.getBussinessName());
					employeeOTs.add(employeeOT);
				}
				result.setEmployees(employeeOTs);
			}
		}
		List<GeneralDate> dateLst = new ArrayList<>();
		if(appDate != null) {
			dateLst.add(GeneralDate.fromString(appDate, DATE_FORMAT));
		}
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);
		
		// 起動時の申請表示情報を取得する
		AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
				companyID, 
				ApplicationType.BREAK_TIME_APPLICATION, 
				lstEmployee, 
				dateLst, 
				true);
		
		result.setAppDispInfoStartupDto(AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput));
		
		// 01-02_時間外労働を取得
		Optional<AgreeOverTimeOutput> opAgreeOverTimeOutput = commonOvertimeHoliday.getAgreementTime(companyID,
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), ApplicationType.BREAK_TIME_APPLICATION);
		if (opAgreeOverTimeOutput.isPresent()) {
			result.setAgreementTimeDto(AgreeOverTimeDto.fromDomain(opAgreeOverTimeOutput.get()));
		}
		
		// 1-1.休日出勤申請（新規）起動時初期データを取得する		
		this.getData(companyID, appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), appDate, result, uiType, payoutType, null, null, overtimeRestAppCommonSet);
		
		// 01-08_乖離定型理由を取得, 01-07_乖離理由を取得
		getDivigenceReason(overtimeRestAppCommonSet, result, companyID);
		
		return result;*/
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = Strings.isNotBlank(appDate) ? Arrays.asList(GeneralDate.fromString(appDate, "yyyy/MM/dd")) : Collections.emptyList();
		AppHdWorkDispInfoOutput output = holidayService.getStartNew(companyID, lstEmployee, dateLst);
		return AppHdWorkDispInfoDto.fromDomain(output);
		
	}
	/**
	 * findChangeAppDate
	 * @param appDate
	 * @param prePostAtr
	 * @param siftCD
	 * @param breakTime
	 * @return
	 */
	public AppHdWorkDispInfoDto findChangeAppDate(String appDate,int prePostAtr,String siftCD, List<CaculationTime> breakTime, String changeEmployee, 
			Integer startTime, Integer endTime, AppHdWorkDispInfoCmd appHdWorkDispInfoCmd){
		/*String companyID = AppContexts.user().companyId();
		AppHolidayWorkDto result = new AppHolidayWorkDto();
		List<GeneralDate> dateLst = new ArrayList<>();
		AppDispInfoStartupDto appDispInfoStartupDto = new AppDispInfoStartupDto();
		AppDispInfoNoDateDto appDispInfoNoDateDto = appHolidayWorkDto.getAppDispInfoStartupDto().appDispInfoNoDateOutput;

		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);

		// 申請日を変更する
		// INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする
		if (appDispInfoNoDateDto.requestSetting.applicationSetting.recordDate == BaseDateFlg.SYSTEM_DATE.value) {
			appDate = GeneralDate.today().toString(DATE_FORMAT);
		}
		dateLst.add(GeneralDate.fromString(appDate, DATE_FORMAT));
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.getAppDispInfoWithDate(companyID, ApplicationType.BREAK_TIME_APPLICATION, dateLst, appDispInfoNoDateDto.toDomain(), true);
		appDispInfoStartupDto.appDispInfoNoDateOutput = appDispInfoNoDateDto;
		appDispInfoStartupDto.appDispInfoWithDateOutput = AppDispInfoWithDateDto.fromDomain(appDispInfoWithDateOutput);
		result.setAppDispInfoStartupDto(appDispInfoStartupDto);
		
		// 1-1.休日出勤申請（新規）起動時初期データを取得する
		this.getData(companyID, appDispInfoNoDateDto.employeeInfoLst.get(0).getSid(), appDate, result, 0, null, null, null,
				overtimeRestAppCommonSet);

		return result;*/
		// error EA refactor 4
		/*String companyID = AppContexts.user().companyId();
		GeneralDate targetDate = GeneralDate.fromString(appDate, "yyyy/MM/dd");
		List<GeneralDate> dateLst = Arrays.asList(targetDate);
		AppHdWorkDispInfoOutput appHdWorkDispInfoOutput = appHdWorkDispInfoCmd.toDomain();
		AppDispInfoNoDateOutput_Old appDispInfoNoDateOutput = appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput();
		AppDispInfoWithDateOutput_Old appDispInfoWithDateOutput = appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput();
		// 共通インタラクション「申請日を変更する」を実行する
		appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(
				companyID, 
				dateLst,
				ApplicationType_Old.BREAK_TIME_APPLICATION, 
				appDispInfoNoDateOutput,
				appDispInfoWithDateOutput);
		// 「休日出勤申請起動時の表示情報」．申請表示情報．申請表示情報(基準日関係あり)=上記取得した「申請表示情報(基準日関係あり)」
		appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		// 1-1.休日出勤申請（新規）起動時初期データを取得する
		HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput = holidayService.initDataNew(
				companyID, 
				appDispInfoNoDateOutput.getEmployeeInfoLst().get(0).getSid(), 
				dateLst.stream().findFirst(), 
				targetDate, 
				appDispInfoWithDateOutput.getPrePostAtr(), 
				appDispInfoWithDateOutput.getEmploymentSet(), 
				appDispInfoWithDateOutput.getWorkTimeLst(), 
				appDispInfoWithDateOutput.getApprovalFunctionSet(), 
				appDispInfoNoDateOutput.getRequestSetting(), 
				appDispInfoWithDateOutput.getAchievementOutputLst());
		// 「休日出勤申請起動時の表示情報」．休日出勤申請起動時の表示情報(申請対象日関係あり)=上記取得した「休日出勤申請起動時の表示情報(申請対象日関係あり)」
		appHdWorkDispInfoOutput.setHdWorkDispInfoWithDateOutput(hdWorkDispInfoWithDateOutput);
		
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		// 07-01_事前申請状態チェック
		PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
				companyID, 
				appDispInfoNoDateOutput.getEmployeeInfoLst().get(0).getSid(), 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType_Old.BREAK_TIME_APPLICATION);
		// 07-02_実績取得・状態チェック
		ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
				companyID, 
				appDispInfoNoDateOutput.getEmployeeInfoLst().get(0).getSid(), 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType_Old.BREAK_TIME_APPLICATION, 
				hdWorkDispInfoWithDateOutput.getWorkTypeCD(), 
				hdWorkDispInfoWithDateOutput.getWorkTimeCD(), 
				withdrawalAppSet.getOverrideSet(), 
				Optional.empty(),
				Collections.emptyList());
		appHdWorkDispInfoOutput.setPreAppCheckResult(preAppCheckResult);
		appHdWorkDispInfoOutput.setActualStatusCheckResult(actualStatusCheckResult);
		
		return AppHdWorkDispInfoDto.fromDomain(appHdWorkDispInfoOutput);*/
		return null;
	}
	/**
	 * getCaculationValue
	 * @param breakTime
	 * @param bonusTimes
	 * @param prePostAtr
	 * @param appDate
	 * @param siftCD
	 * @return
	 */
	/*public ColorConfirmResult calculationresultConfirm(List<CaculationTime> breakTime ,
			int prePostAtr,
			String appDate,
			String siftCD,
			String workTypeCode,
			String employeeID,
			GeneralDateTime inputDate,
			Integer startTime,
			Integer endTime,
			List<Integer> startTimeRests,
			List<Integer> endTimeRests){
		if(inputDate == null){
			inputDate = GeneralDateTime.now();
		}
		String companyID = AppContexts.user().companyId();
		String employeeIDOrapproverID = AppContexts.user().employeeId();
		ColorConfirmResult result = new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
		// 6.計算処理 : TODO
		// 表示しない
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeIDOrapproverID,
				1, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class), inputDate.toDate());
		if (!isSettingDisplay(appCommonSettingOutput)) {
			Optional<TimeWithDayAttr> opStartTime = startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime)); 
			Optional<TimeWithDayAttr> opEndTime = endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime)); 
			// 休憩時間帯を取得する
			List<DeductionTimeDto> timeZones =  getBreakTimes(workTypeCode, siftCD, opStartTime, opEndTime); 

			if (!CollectionUtil.isEmpty(timeZones)) {
				startTimeRests = timeZones.stream().map(x -> x.getStart())
						.collect(Collectors.toList());

				endTimeRests = timeZones.stream().map(x -> x.getEnd())
						.collect(Collectors.toList());
			}
		}
		
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation.getCalculation(employeeID,
																GeneralDate.fromString(appDate, DATE_FORMAT),
																workTypeCode,
																siftCD,
																startTime,
																endTime,
																startTimeRests,
																endTimeRests);
		if (!employeeIDOrapproverID.equals(employeeID)) {
			//  06-01-a_色表示チェック（承認者）
			result = commonOvertimeHoliday.checkDisplayColorForApproverCF(breakTime,
															dailyAttendanceTimeCaculationImport.getHolidayWorkTime(),
															prePostAtr,
															inputDate,
															GeneralDate.fromString(appDate, DATE_FORMAT), 
															ApplicationType.BREAK_TIME_APPLICATION.value,
															employeeID,
															companyID,
															siftCD);
		}else{
			//06-01_色表示チェック
			result = commonOvertimeHoliday.checkDisplayColorHolCF(breakTime,
					dailyAttendanceTimeCaculationImport.getHolidayWorkTime(), prePostAtr, inputDate,
					GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION.value,
					employeeID, companyID, siftCD);
		}
		result.setDailyAttendanceTimeCaculationImport(dailyAttendanceTimeCaculationImport);
		return result;
	}*/
	
	/**
	 * getCaculationValue
	 * @param breakTime
	 * @param bonusTimes
	 * @param prePostAtr
	 * @param appDate
	 * @param siftCD
	 * @return
	 */
	/*public List<CaculationTime> getCaculationValue(List<CaculationTime> breakTime ,
			int prePostAtr,
			String appDate,
			String siftCD,
			String workTypeCode,
			String employeeID,
			GeneralDateTime inputDate,
			Integer startTime,
			Integer endTime,
			List<Integer> startTimeRests,
			List<Integer> endTimeRests,
			DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport){
		if(inputDate == null){
			inputDate = GeneralDateTime.now();
		}
		String companyID = AppContexts.user().companyId();
		String employeeIDOrapproverID = AppContexts.user().employeeId();
		List<CaculationTime> result = new ArrayList<>();
		// 6.計算処理 : TODO
		// 表示しない
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeIDOrapproverID,
				1, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class), inputDate.toDate());
		if (!isSettingDisplay(appCommonSettingOutput)) {
			Optional<TimeWithDayAttr> opStartTime = startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime)); 
			Optional<TimeWithDayAttr> opEndTime = endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime)); 
			// 休憩時間帯を取得する
			List<DeductionTimeDto> timeZones =  getBreakTimes(workTypeCode, siftCD, opStartTime, opEndTime); 

			if (!CollectionUtil.isEmpty(timeZones)) {
				startTimeRests = timeZones.stream().map(x -> x.getStart())
						.collect(Collectors.toList());

				endTimeRests = timeZones.stream().map(x -> x.getEnd())
						.collect(Collectors.toList());
			}
		}
		if (!employeeIDOrapproverID.equals(employeeID)) {
			//  06-01-a_色表示チェック（承認者）
			result = commonOvertimeHoliday.checkDisplayColorForApprover(breakTime,
															dailyAttendanceTimeCaculationImport.getHolidayWorkTime(),
															prePostAtr,
															inputDate,
															GeneralDate.fromString(appDate, DATE_FORMAT), 
															ApplicationType.BREAK_TIME_APPLICATION.value,
															employeeID,
															companyID,
															siftCD);
		}else{
			//06-01_色表示チェック
			result = commonOvertimeHoliday.checkDisplayColorHol(breakTime,
					dailyAttendanceTimeCaculationImport.getHolidayWorkTime(), prePostAtr, inputDate,
					GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION.value,
					employeeID, companyID, siftCD);
		}
		// 06-02_休出時間を取得
		this.holidaySixProcess.getCaculationHolidayWork(companyID,
				employeeID,
				appDate,
				ApplicationType.BREAK_TIME_APPLICATION.value,
				result,
				dailyAttendanceTimeCaculationImport.getHolidayWorkTime(),prePostAtr);
		return result;
	}*/
	
	public PreActualColorResult getCalculateValue(String employeeID, String appDate, Integer prePostAtr, String workTypeCD, String workTimeCD,
			List<CaculationTime> overtimeInputLst, Integer startTime, Integer endTime, List<Integer> startTimeRests, List<Integer> endTimeRests){
		String companyID = AppContexts.user().companyId();
		
		// 6.計算処理 : 
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation.getCalculation(employeeID,
				GeneralDate.fromString(appDate, DATE_FORMAT),
				workTypeCD,
				workTimeCD,
				startTime,
				endTime,
				startTimeRests,
				endTimeRests);
		List<OvertimeInputCaculation> breaktimeInputCaculations = new ArrayList<>();
				
		for (Map.Entry<Integer, TimeWithCalculationImport> entry : dailyAttendanceTimeCaculationImport.getHolidayWorkTime().entrySet()) {
			OvertimeInputCaculation breaktimeCal = new OvertimeInputCaculation(AttendanceType.BREAKTIME.value,
					entry.getKey(), entry.getValue().getCalTime());
			breaktimeInputCaculations.add(breaktimeCal);
		}
		
		List<OvertimeColorCheck> otTimeLst = overtimeInputLst.stream()
				.map(x -> OvertimeColorCheck.createApp(x.getAttendanceID(), x.getFrameNo(), x.getApplicationTime()))
				.collect(Collectors.toList());
		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.HOLIDAY_WORK_APPLICATION.value).get();
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		// 07-01_事前申請状態チェック
		PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
				companyID, 
				employeeID, 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType.HOLIDAY_WORK_APPLICATION);
		// 07-02_実績取得・状態チェック
		ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
				companyID, 
				employeeID, 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType.HOLIDAY_WORK_APPLICATION, 
				workTypeCD, 
				workTimeCD, 
				withdrawalAppSet.getOverrideSet(), 
				Optional.empty(),
				Collections.emptyList());
		// 07_事前申請・実績超過チェック
		PreActualColorResult preActualColorResult =	preActualColorCheck.preActualColorCheck(
				preExcessDisplaySetting, 
				performanceExcessAtr, 
				ApplicationType.HOLIDAY_WORK_APPLICATION, 
				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
				breaktimeInputCaculations, 
				otTimeLst, 
				preAppCheckResult.opAppBefore, 
				preAppCheckResult.beforeAppStatus, 
				actualStatusCheckResult.actualLst, 
				actualStatusCheckResult.actualStatus);
		
		return preActualColorResult;
	}
	
	/**
	 * 休憩時間帯を取得する
	 * @param workTypeCD
	 * @param workTimeCD
	 * @return BreakTimeZoneSharedOutPut
	 */
	public List<DeductionTimeDto> getBreakTimes(String workTypeCD ,String workTimeCD, Optional<TimeWithDayAttr> opStartTime, Optional<TimeWithDayAttr> opEndTime){
		String companyID = AppContexts.user().companyId();
		return this.commonOvertimeHoliday.getBreakTimes(companyID, workTypeCD, workTimeCD, opStartTime, opEndTime).stream().map(domain -> {
			DeductionTimeDto dto = new DeductionTimeDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * 7.休出申請（詳細）起動処理
	 * @param appID
	 * @return
	 */
	public HolidayWorkDetailDto getAppHolidayWorkByAppID(String appID){
		String companyID = AppContexts.user().companyId();
		HolidayWorkDetailOutput output = holidayService.findDetailByID(companyID, appID);
		return HolidayWorkDetailDto.fromDomain(output);
		/*String companyID = AppContexts.user().companyId();
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);
		// 14-1.詳細画面起動前申請共通設定を取得する
		// 詳細画面の申請データを取得する
		Optional<AppHolidayWork> opAppHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, appID);
		if(!opAppHolidayWork.isPresent()){
			throw new BusinessException("Msg_198");
		}
		AppHolidayWork appHolidayWork = opAppHolidayWork.get();
		String appDate = !appHolidayWork.getApplication().getStartDate().isPresent() ? null : appHolidayWork.getApplication().getStartDate().get().toString(DATE_FORMAT);
		
		// 起動時の申請表示情報を取得する
		List<String> lstEmployee = new ArrayList<String>();
		lstEmployee.add(appHolidayWork.getApplication().getEmployeeID());
		List<GeneralDate> dateLst = new ArrayList<>();
		dateLst.add(GeneralDate.fromString(appDate, DATE_FORMAT));
		AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
				companyID, 
				ApplicationType.BREAK_TIME_APPLICATION, 
				lstEmployee, 
				dateLst, 
				true);
		
		// ドメインモデル「休日出勤申請」を取得する
		AppHolidayWorkDto appHolidayWorkDto = AppHolidayWorkDto.fromDomain(appHolidayWork);
		appHolidayWorkDto.setAppDispInfoStartupDto(AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput));
		
		// 申請表示情報(基準日関係あり)．事前事後区分=取得した「申請」．事前事後区分
		appHolidayWorkDto.getAppDispInfoStartupDto().appDispInfoWithDateOutput.prePostAtr = appHolidayWorkDto.getApplication().getPrePostAtr();
		
		// 詳細画面の利用者とステータスを取得する
		DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput = this.beforePreBootMode.judgmentDetailScreenMode(companyID, appHolidayWork.getApplication().getEmployeeID(), appID, 
				GeneralDate.fromString(appHolidayWorkDto.getAppDispInfoStartupDto().appDispInfoWithDateOutput.baseDate, DATE_FORMAT));
		// 詳細画面の画面モードを判断する
		DetailScreenInitModeOutput detailScreenInitModeOutput = this.initMode.getDetailScreenInitMode(detailedScreenPreBootModeOutput.getUser(), detailedScreenPreBootModeOutput.getReflectPlanState().value);
		appHolidayWorkDto.setDetailedScreenPreBootModeOutput(detailedScreenPreBootModeOutput);
		appHolidayWorkDto.setDetailScreenInitModeOutput(detailScreenInitModeOutput);
		
		// 1-1.休日出勤申請（新規）起動時初期データを取得する		
		this.getData(companyID, appHolidayWork.getApplication().getEmployeeID(), appDate, appHolidayWorkDto, 0, null, null, null, overtimeRestAppCommonSet);

		// 07-01_事前申請状態チェック
		PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(companyID,
				appHolidayWork.getApplication().getEmployeeID(), appHolidayWork.getApplication().getAppDate(),
				appHolidayWork.getApplication().getAppType());
		// 07-02_実績取得・状態チェック
		ActualStatusCheckResult actualStatusCheckResult = null;
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		if (detailedScreenPreBootModeOutput.getUser() == User.APPLICANT_APPROVER
				|| detailedScreenPreBootModeOutput.getUser() == User.APPLICANT) {
			actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID, appHolidayWork.getApplication().getEmployeeID(),
					GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION, appHolidayWork.getWorkTypeCode().v(),
					appHolidayWork.getWorkTimeCode().v(), withdrawalAppSet.getOverrideSet(), Optional.of(withdrawalAppSet.getCalStampMiss()));
		} else {
			actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID, appHolidayWork.getApplication().getEmployeeID(),
					GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION, appHolidayWork.getWorkTypeCode().v(),
					appHolidayWork.getWorkTimeCode().v(), OverrideSet.TIME_OUT_PRIORITY, Optional.of(CalcStampMiss.CAN_NOT_REGIS));
		}		
		
		// 07_事前申請・実績超過チェック
		List<OvertimeColorCheck> holidayLst = new ArrayList<>();
		appHolidayWorkDto.getHolidayWorkInputDtos().forEach(holidayInput -> {
			if (holidayInput.getAttendanceType() == 2) {
				holidayLst.add(OvertimeColorCheck.createApp(2, holidayInput.getFrameNo(), holidayInput.getApplicationTime()));
			}		
		});
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.get().getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.get().getPerformanceExcessAtr();
		PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(preExcessDisplaySetting,
				performanceExcessAtr, appHolidayWork.getApplication().getAppType(),
				appHolidayWork.getApplication().getPrePostAtr(), Collections.emptyList(), holidayLst,
				preAppCheckResult.opAppBefore, preAppCheckResult.beforeAppStatus,
				actualStatusCheckResult.actualLst,
				actualStatusCheckResult.actualStatus);
		List<CaculationTime> caculationTimes = preActualColorResult.resultLst.stream()
				.map(x -> new CaculationTime(companyID, appID, x.attendanceID, x.frameNo, 0, "", x.appTime,
						x.preAppTime == null ? null : x.preAppTime.toString(),
						x.actualTime == null ? null : x.actualTime.toString(),
						getErrorCode(x.calcError, x.preAppError, x.actualError), false,
						preActualColorResult.beforeAppStatus, preActualColorResult.actualStatus == 3))
				.collect(Collectors.toList());
		appHolidayWorkDto.setCaculationTimes(caculationTimes);
		appHolidayWorkDto.setPreActualColorResult(preActualColorResult);

		return appHolidayWorkDto;*/
	}
	
	/*private int getErrorCode(int calcError, int preAppError, int actualError){
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
    }*/
	
	/*private boolean isSettingDisplay(AppCommonSettingOutput appCommonSettingOutput) {
		return appCommonSettingOutput.approvalFunctionSetting.getApplicationDetailSetting().get()
				.getBreakInputFieldDisp().equals(true)
				&& appCommonSettingOutput.getApprovalFunctionSetting().getApplicationDetailSetting().get()
						.getTimeCalUse().equals(UseAtr.USE);

	}*/
	
	/**
	 * 01-14_勤務時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param siftCD
	 * @return
	 */
	public RecordWorkDto getRecordWork(String employeeID, String appDate, String siftCD,int prePortAtr,List<CaculationTime> overtimeHours, String workTypeCD, 
			String appID, AppHdWorkDispInfoCmd appHdWorkDispInfoCmd){
//		AppHdWorkDispInfoOutput appHdWorkDispInfoOutput = appHdWorkDispInfoCmd.toDomain();
//		String companyID = AppContexts.user().companyId();
//		Integer startTime1 = -1; 
//		Integer endTime1 = -1;
//		Integer startTime2 = -1;
//		Integer endTime2 = -1;
//		AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
//		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
//				employeeID,
//				1, ApplicationType.HOLIDAY_WORK_APPLICATION, appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
//		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
//		
//		// 01-14_勤務時間取得(lay thoi gian): Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」)
//		RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(
//				companyID, 
//				employeeID, 
//				appDate == null ? null : GeneralDate.fromString(appDate, "yyyy/MM/dd"), 
//				approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
//				approvalFunctionSetting.getApplicationDetailSetting().get().getAtworkTimeBeginDisp(),
//				ApplicationType.HOLIDAY_WORK_APPLICATION, 
//				siftCD, 
//				Optional.empty(),
//				Optional.empty(), 
//				approvalFunctionSetting);
//		startTime1 = recordWorkOutput.getStartTime1();
//		endTime1 = recordWorkOutput.getEndTime1();
//		startTime2 = recordWorkOutput.getStartTime2();
//		endTime2 = recordWorkOutput.getEndTime2();
//		
//		// 休憩時間帯を取得する
//		List<DeductionTimeDto> timeZones = getBreakTimes(workTypeCD, siftCD, Optional.empty(), Optional.empty());
//		
//		List<DeductionTime> deductionTimeLst = CollectionUtil.isEmpty(timeZones) ? Collections.emptyList() : 
//			timeZones.stream().map(x -> {
//					nts.uk.ctx.at.shared.app.command.worktime.common.dto.DeductionTimeDto cmd = new nts.uk.ctx.at.shared.app.command.worktime.common.dto.DeductionTimeDto();
//					cmd.setStart(x.getStart());
//					cmd.setEnd(x.getEnd());
//					return new DeductionTime(cmd);
//				}).collect(Collectors.toList());
//		
//		// 07-02_実績取得・状態チェック
//		// アルゴリズム「残業申請設定を取得する」を実行する
//		ActualStatusCheckResult actualStatusCheckResult = null;
//		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
//		if (appDate != null) {
//			if (appID != null) {
//				// 詳細画面の申請データを取得する
//				Optional<AppHolidayWork> opAppHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, appID);
//				if (!opAppHolidayWork.isPresent()) {
//					throw new BusinessException("Msg_198");
//				}
//				// DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput = appHolidayWorkDto.getDetailedScreenPreBootModeOutput();
//				User user = appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getUser();
//				
//				if (user == User.APPLICANT_APPROVER || user == User.APPLICANT) {
//					actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID, employeeID,
//							GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.HOLIDAY_WORK_APPLICATION, workTypeCD,
//							siftCD, withdrawalAppSet.getOverrideSet(), Optional.of(withdrawalAppSet.getCalStampMiss()), deductionTimeLst);
//				} else {
//					actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID, employeeID,
//							GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.HOLIDAY_WORK_APPLICATION, workTypeCD,
//							siftCD, OverrideSet.TIME_OUT_PRIORITY, Optional.of(CalcStampMiss.CAN_NOT_REGIS), deductionTimeLst);
//				}			
//			} else {
//				actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID, employeeID,
//						GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.HOLIDAY_WORK_APPLICATION, workTypeCD,
//						siftCD, withdrawalAppSet.getOverrideSet(), Optional.of(withdrawalAppSet.getCalStampMiss()), deductionTimeLst);
//			}
//			
//		}
//		return new RecordWorkDto(startTime1, endTime1, startTime2, endTime2, appOvertimeReference, timeZones, actualStatusCheckResult);
		return null;
	}
	
	/**
	 * 1-1.休日出勤申請（新規）起動時初期データを取得する
	 * @param companyID
	 * @param employeeID
	 * @param appDateHolidayWork
	 * @param appCommonSettingOutput
	 * @param result
	 */
	/*private void getData(String companyID,String employeeID,String appDate, AppHolidayWorkDto result,
			int uiType,Integer payoutType, Integer startTime, Integer endTime, Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet){
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		List<HolidayWorkInputDto> holidayWorkInputDtos = new ArrayList<>();
		AppCommonSettingOutput appCommonSettingOutput = new AppCommonSettingOutput();
		appCommonSettingOutput.approvalFunctionSetting = result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.toDomain().getApprovalFunctionSet();
		//01-12_申請日付取得
		applicationDto.setApplicationDate(appDate);
		// 01-01_休出通知情報を取得
		HolidayWorkInstruction holidayWorkInstruction = holidayPreProcess.getHolidayInstructionInformation(
				appCommonSettingOutput.getApprovalFunctionSetting().getInstructionUseSetting().getInstructionUseDivision(), 
				Strings.isNotBlank(appDate) ? GeneralDate.fromString(appDate, "yyyy/MM/dd") : null, 
				employeeID);
		result.setDisplayHolidayInstructInforFlg(holidayWorkInstruction.isDisplayHolidayWorkInstructInforFlg());
		result.setHolidayInstructInformation(holidayWorkInstruction.getHolidayWorkInstructInfomation());
		// 1-2.起動時勤務種類リストを取得する
		// 1-3.起動時勤務種類・就業時間帯の初期選択
		// 01-01_休憩時間を取得する
		getWorkTypeAndWorkTime(companyID, employeeID, result, uiType, payoutType, false,
				startTime, endTime);
		// 01-03_休出時間枠を取得
		getBreaktime(companyID, holidayWorkInputDtos);
		
		// new mode
		if (CollectionUtil.isEmpty(result.getHolidayWorkInputDtos())) {
			// 01-14_勤務時間取得
			getWorkingHour(companyID, employeeID, appDate, result, "", null);
			// 01-04_加給時間を取得
			if (overtimeRestAppCommonSet.isPresent()) {
				result.setDisplayBonusTime(false);
				if (overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value) {
					result.setDisplayBonusTime(true);
					List<BonusPayTimeItem> bonusPayTimeItems = this.commonOvertimeHoliday.getBonusTime(companyID,
							employeeID,
							appDate == null ? GeneralDate.today() : GeneralDate.fromString(appDate, DATE_FORMAT),
							overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr());
					List<BonusPayTimeItem> bonusPayTimeItemNotSpecial = bonusPayTimeItems.stream()
							.filter(x -> x.getTimeItemTypeAtr().value == TimeItemTypeAtr.NORMAL_TYPE.value)
							.collect(Collectors.toList());
					for (BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItemNotSpecial) {
						holidayWorkInputDtos
								.add(convertBonusPayTimeItem(bonusPayTimeItem, AttendanceType.BONUSPAYTIME.value));
					}
					List<BonusPayTimeItem> bonusPayTimeItemSpecial = bonusPayTimeItems.stream()
							.filter(x -> x.getTimeItemTypeAtr().value == TimeItemTypeAtr.SPECIAL_TYPE.value)
							.collect(Collectors.toList());
					for (BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItemSpecial) {
						holidayWorkInputDtos
								.add(convertBonusPayTimeItem(bonusPayTimeItem, AttendanceType.BONUSSPECIALDAYTIME.value));
					}
				}
			}
			result.setHolidayWorkInputDtos(holidayWorkInputDtos);
		} else {
			// edit mode
			List<HolidayWorkInputDto> breakTimes = result.getHolidayWorkInputDtos().stream()
					.filter(x -> x.getAttendanceType() == AttendanceType.BREAKTIME.value).collect(Collectors.toList());
			List<Integer> breakFrameNos = new ArrayList<>();
			breakTimes.forEach(x -> {
				breakFrameNos.add(x.getFrameNo());
			});

			holidayWorkInputDtos.forEach(x -> {
				breakTimes.forEach(breakTime -> {
					if (x.getFrameNo() == breakTime.getFrameNo()) {
						breakTime.setFrameName(x.getFrameName());
					}
				});
			});
			for (int i = 1; i < 11; i++) {
				HolidayWorkInputDto holidayInputDto = new HolidayWorkInputDto();
				holidayInputDto.setAttendanceType(AttendanceType.RESTTIME.value);
				holidayInputDto.setFrameNo(i);
				holidayInputDto.setFrameName(Integer.toString(i));
				holidayWorkInputDtos.add(holidayInputDto);
			}
			result.getHolidayWorkInputDtos().forEach(item -> {
				holidayWorkInputDtos.stream().filter(x -> (x.getAttendanceType() == item.getAttendanceType())
						&& (x.getFrameNo() == item.getFrameNo())).findAny().ifPresent(value -> {
							value.setStartTime(item.getStartTime());
							value.setEndTime(item.getEndTime());
							value.setApplicationTime(item.getApplicationTime());
						});
				;
			});
			result.setHolidayWorkInputDtos(holidayWorkInputDtos);
			
			// 起動時の36協定時間の状態を取得する
			if (overtimeRestAppCommonSet.isPresent()) {
				Map<String,AppHolidayWork> appHolidayWorkDetailMap = appHolidayWorkRepository.getListAppHdWorkFrame(companyID, Arrays.asList(result.getAppID()));
				if(!appHolidayWorkDetailMap.isEmpty()){
					Optional<AppOvertimeDetail> appOvertimeDetail = appHolidayWorkDetailMap.entrySet().stream().findFirst().get().getValue().getAppOvertimeDetail();
					result.setAppOvertimeDetailDto(AppOvertimeDetailDto.fromDomain(appOvertimeDetail));
					if(!appOvertimeDetail.isPresent()){
						result.setAppOvertimeDetailStatus(null);
					} else {
						result.setAppOvertimeDetailStatus(overtimeService.getTime36Detail(appOvertimeDetail.get()).value);
					}
				}
			}
		}
		
		String employeeName = "";
		employeeName = employeeAdapter.getEmployeeName(employeeID);
		result.setEmployeeID(employeeID);

		result.setEmployeeName(employeeName);
		if(appCommonSettingOutput.getApprovalFunctionSetting()!=null){
			result.setEnableOvertimeInput(appCommonSettingOutput.getApprovalFunctionSetting().getApplicationDetailSetting().get().getTimeInputUse().value==1?true:false);
		}
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.get().getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.get().getPerformanceExcessAtr();
		result.setPreExcessDisplaySetting(preExcessDisplaySetting.value);
		result.setPerformanceExcessAtr(performanceExcessAtr.value);
		
		List<AppTypeSettingDto> listAppTypeSetting= result.getAppDispInfoStartupDto().appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting;
		Optional<AppTypeSettingDto> appTypeSettingDtoOpt = listAppTypeSetting.stream().filter(x -> x.appType == 6).findFirst();
		if (!appTypeSettingDtoOpt.isPresent()) {
			return;
		}
		result.setRequireAppReasonFlg(result.getAppDispInfoStartupDto().appDispInfoNoDateOutput.requestSetting.applicationSetting.appLimitSetting.requiredAppReason);
		result.setManualSendMailAtr(result.getAppDispInfoStartupDto().appDispInfoNoDateOutput.requestSetting.applicationSetting.appDisplaySetting.manualSendMailAtr  ==1 ?true : false);
		result.setSendMailWhenApprovalFlg(appTypeSettingDtoOpt.get().sendMailWhenApproval);
		result.setSendMailWhenRegisterFlg(appTypeSettingDtoOpt.get().sendMailWhenRegister);
		
		result.setTypicalReasonDisplayFlg(appTypeSettingDtoOpt.get().displayFixedReason == AppDisplayAtr.DISPLAY.value ? true : false);

		result.setDisplayAppReasonContentFlg(appTypeSettingDtoOpt.get().displayAppReason == AppDisplayAtr.DISPLAY.value ? true : false);
		result.setDisplayPrePostFlg(result.getAppDispInfoStartupDto().appDispInfoNoDateOutput.requestSetting.applicationSetting.appDisplaySetting.prePostAtrDisp);
		result.setPrePostCanChangeFlg(appTypeSettingDtoOpt.get().canClassificationChange);
		if (overtimeRestAppCommonSet.isPresent()) {
			// 時間外表示区分
			result.setExtratimeDisplayFlag(
					overtimeRestAppCommonSet.get().getExtratimeDisplayAtr().value == 1 ? true : false);
		}
	}*/
	/**
	 * 01-03_休出時間を取得
	 * @param companyID
	 * @param holidayWorkInputDtos
	 */
	/*private void getBreaktime(String companyID,List<HolidayWorkInputDto> holidayWorkInputDtos){
		List<WorkdayoffFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		for(WorkdayoffFrame breaktimeFrame :breaktimeFrames){
			HolidayWorkInputDto holidayWorkInputDto = new HolidayWorkInputDto();
			holidayWorkInputDto.setAttendanceType(AttendanceType.BREAKTIME.value);
			holidayWorkInputDto.setFrameNo(breaktimeFrame.getWorkdayoffFrNo().v().intValueExact());
			holidayWorkInputDto.setFrameName(breaktimeFrame.getWorkdayoffFrName().toString());
			holidayWorkInputDtos.add(holidayWorkInputDto);
		}
	}*/
	/**
	 * 4.勤務種類を取得する, 5.就業時間帯を取得する,01-14_勤務時間取得, 01-17_休憩時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param appCommonSettingOutput
	 * @param result
	 */
	/*private void getWorkTypeAndWorkTime(String companyID,String employeeID, AppHolidayWorkDto result,
			int uiType,Integer payoutType,boolean isChangeDate, Integer startTime, Integer endTime){
		ApprovalFunctionSetting approvalFunctionSetting = result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.toDomain().getApprovalFunctionSet();
		result.setDisplayCaculationTime(false);
		if(approvalFunctionSetting != null){
			result.setDisplayCaculationTime(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE));
		}
			// 時刻計算利用チェック
//			if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
				// 4.勤務種類を取得する :
				WorkTypeHolidayWork WorkTypes = null;
				if (result.getWorkType() == null) {
					GeneralDate baseDate = GeneralDate.fromString(result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.baseDate, DATE_FORMAT);
					//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
					Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeID,baseDate);
					List<AppEmploymentSetting> appEmploymentWorkType = new ArrayList<AppEmploymentSetting>();
					appEmploymentWorkType.add(result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.toDomain().getEmploymentSet());
					WorkTypes = new WorkTypeHolidayWork();
					if(uiType != 1){
						// 4.勤務種類を取得する
						WorkTypes = holidayService.getWorkTypes(companyID, employeeID, appEmploymentWorkType, baseDate,
						personalLablorCodition, isChangeDate);
					}else{
						// 4_a.勤務種類を取得する（法定内外休日） 
						WorkTypes = holidayService.getWorkTypeForLeaverApp(companyID, employeeID, appEmploymentWorkType, baseDate, personalLablorCodition, payoutType);
					}
					if (WorkTypes == null) {
						result.setWorkTypes(null);
						WorkTypeOvertime workType = new WorkTypeOvertime();
						result.setWorkType(workType);
					}
					result.setWorkTypes(WorkTypes.getWorkTypeCodes());
					WorkTypeOvertime workType = new WorkTypeOvertime();
					workType.setWorkTypeCode(WorkTypes.getWorkTypeCode());
					workType.setWorkTypeName(WorkTypes.getWorkTypeName());
					result.setWorkType(workType);
					// 5.就業時間帯を取得する
					WorkTimeHolidayWork workTimes = this.holidayService.getWorkTimeHolidayWork(companyID, employeeID, baseDate, personalLablorCodition,isChangeDate);
					if (workTimes == null) {
						result.setWorkTimes(null);
						SiftType workTime = new SiftType();
						result.setWorkTime(workTime);
					}
					result.setWorkTimes(workTimes.getWorkTimeCodes());
					SiftType workTime = new SiftType();
					workTime.setSiftCode(workTimes.getWorkTimeCode());
					workTime.setSiftName(workTimes.getWorkTimeName());
					result.setWorkTime(workTime);
					Optional<TimeWithDayAttr> opStartTime = startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime)); 
					Optional<TimeWithDayAttr> opEndTime = endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime));
					//休憩時間帯を取得する
					List<DeductionTimeDto> timeZones = getBreakTimes(WorkTypes.getWorkTypeCode(),workTime.getSiftCode(), opStartTime, opEndTime);
					
					result.setTimeZones(timeZones);
				} else {
					String workTypeCD = "";
					String workTimeCD = "";
					workTypeCD = result.getWorkType().getWorkTypeCode();
					WorkType workType = workTypeRepository.findByPK(companyID, workTypeCD).orElse(null) ;
					String wkTypeName = null ;
					if(workType != null){
						wkTypeName = workType.getName().v();
					}
					result.setWorkType(new WorkTypeOvertime(workTypeCD,wkTypeName));

					workTimeCD = result.getWorkTime().getSiftCode();
					WorkTimeSetting workTime =  workTimeRepository.findByCode(companyID, workTimeCD).orElse(null);
					String wkTimeName= null ;
					if(workTime != null){
						wkTimeName = workTime.getWorkTimeDisplayName().getWorkTimeName().toString();
					}
					result.setWorkTime(new SiftType(workTimeCD,wkTimeName));
				}
				// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
				boolean displayRestTime = commonOvertimeHoliday.getRestTime(
						companyID,
						approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
						approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
						ApplicationType.BREAK_TIME_APPLICATION);
				result.setDisplayRestTime(displayRestTime);
//			}
//		}
	}*/
	/**
	 * getWorkingHour
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param appCommonSettingOutput
	 * @param result
	 * @param workTimeCode
	 */
	/*private void getWorkingHour(String companyID,String employeeID,String appDate ,AppHolidayWorkDto result,String workTimeCode, String changeEmployee){
		ApprovalFunctionSetting approvalFunctionSetting = result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.toDomain().getApprovalFunctionSet();
		if(approvalFunctionSetting != null){
			result.setDisplayCaculationTime(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE));
		}
//			if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
//				result.setDisplayCaculationTime(true);
				// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
				RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(
						companyID, 
						employeeID, 
						appDate == null ? null : GeneralDate.fromString(appDate, "yyyy/MM/dd"), 
						approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
						approvalFunctionSetting.getApplicationDetailSetting().get().getAtworkTimeBeginDisp(),
						ApplicationType.BREAK_TIME_APPLICATION, 
						result.getWorkTime() == null ? workTimeCode : result.getWorkTime().getSiftCode(), 
						Optional.empty(),
						Optional.empty(), 
						approvalFunctionSetting);
				result.setWorkClockStart1(recordWorkOutput.getStartTime1());
				result.setWorkClockStart2(recordWorkOutput.getStartTime2());
				result.setWorkClockEnd1(recordWorkOutput.getEndTime1());
				result.setWorkClockEnd2(recordWorkOutput.getEndTime2());
//			}
//		}
	}*/
	/**
	 * convert BonusPayTimeItem -> HolidayWorkInputDto
	 * @param bonusPayTimeItem
	 * @param attendanceType
	 * @return
	 */
	/*private HolidayWorkInputDto convertBonusPayTimeItem(BonusPayTimeItem bonusPayTimeItem,int attendanceType){
		HolidayWorkInputDto holidayWorkInputDto = new HolidayWorkInputDto();
		holidayWorkInputDto.setAttendanceType(attendanceType);
		holidayWorkInputDto.setFrameNo(bonusPayTimeItem.getId());
		holidayWorkInputDto.setFrameName(bonusPayTimeItem.getTimeItemName().toString());
		return holidayWorkInputDto;
	}*/

	/**
	 * 01-08_乖離定型理由を取得, 01-07_乖離理由を取得
	 * @param overtimeRestAppCommonSet
	 * @param result
	 * @param companyID
	 */
	/*private void getDivigenceReason(Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet,AppHolidayWorkDto result,String companyID){
		
		if(overtimeRestAppCommonSet.isPresent()){
			//01-08_乖離定型理由を取得
			result.setDisplayDivergenceReasonForm(false);
			if(result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.prePostAtr != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
				result.setDisplayDivergenceReasonForm(true);
				List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
						.getDivergenceReasonForm(
								companyID,  
								EnumAdaptor.valueOf(result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.prePostAtr, PrePostAtr.class),
								overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr(), 
								ApplicationType.BREAK_TIME_APPLICATION);
				convertToDivergenceReasonDto(divergenceReasons,result);
			}
			//01-07_乖離理由を取得
			result.setDisplayDivergenceReasonInput(
					commonOvertimeHoliday.displayDivergenceReasonInput(
							EnumAdaptor.valueOf(result.getAppDispInfoStartupDto().appDispInfoWithDateOutput.prePostAtr, PrePostAtr.class),
							overtimeRestAppCommonSet.get().getDivergenceReasonInputAtr()));
		}
	}*/
	
	/**
	 * convertToDivergenceReasonDto
	 * @param divergenceReasons
	 * @param result
	 */
	/*private void convertToDivergenceReasonDto(List<DivergenceReason> divergenceReasons, AppHolidayWorkDto result){
		List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
		for(DivergenceReason divergenceReason : divergenceReasons){
			DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
			divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
			divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
			divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
			
			divergenceReasonDtos.add(divergenceReasonDto);
		}
		result.setDivergenceReasonDtos(divergenceReasonDtos);
	}*/
}
