package nts.uk.ctx.at.request.app.find.application.holidaywork;

/*import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone.BreakTimeZoneSharedOutPut;*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AgreeOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDataHasDate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDataNoDate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HolidayWorkInputDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.EmployeeOvertimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.RecordWorkDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
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
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
//import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
//import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
//import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayPreProcess;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidaySixProcess;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTimeHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTypeHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.TimeItemTypeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.AppOvertimeReference;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayPrePost;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.output.RecordWorkOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service.BaseDateGet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
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
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
//	@Inject
//	private CollectAchievement collectAchievement;
	@Inject
	private HolidayPreProcess holidayPreProcess;
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	@Inject
	private HolidayService holidayService;
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	@Inject
	private HolidaySixProcess holidaySixProcess; 
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	@Inject
	private BeforePreBootMode beforePreBootMode;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private WithdrawalAppSetRepository withdrawalAppSetRepository;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private RequestOfEachCompanyRepository requestOfEachCompanyRepository;
	
	@Inject
	private BaseDateGet getBaseDate;
	
	@Inject
	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;
	
	@Inject
	private AppEmploymentSettingRepository appEmploymentSetting;
	
	@Inject 
	private InitMode initMode;

	/**
	 * 1.休出申請（新規）起動処理
	 * getAppHolidayWork for start UI KAF010A
	 * @param appDateInput
	 * @param uiType
	 * @return
	 */
	public AppHolidayWorkDto getAppHolidayWork(List<String> appDateInput,int uiType,List<String> lstEmployee,Integer payoutType,String employeeID, AppHolidayWorkDto result){
		String companyID = AppContexts.user().companyId();
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);
		// xu li hien thi du lieu xin truoc
		if (overtimeRestAppCommonSet.isPresent()) {
			// 時間外表示区分
			result.setExtratimeDisplayFlag(
					overtimeRestAppCommonSet.get().getExtratimeDisplayAtr().value == 1 ? true : false);
		}
		
		// 起動時の申請表示情報を取得する
		// 申請表示情報(基準日関係なし)を取得する
		AppHolidayWorkDataNoDate appHolidayWorkDataNoDate = this.getAppDisplayInfoNoBaseDate(result, companyID, lstEmployee, employeeID);
		result.setAppHolidayWorkDataNoDate(appHolidayWorkDataNoDate);
		
		// 申請表示情報(基準日関係あり)を取得する
		AppHolidayWorkDataHasDate appHolidayWorkDataHasDate = this.getAppDisplayInfoHasBaseDate(appDateInput, result, companyID, appHolidayWorkDataNoDate, overtimeRestAppCommonSet, uiType);
		result.setAppHolidayWorkDataHasDate(appHolidayWorkDataHasDate);
		
		// 01-02_時間外労働を取得
		Optional<AgreeOverTimeOutput> opAgreeOverTimeOutput = commonOvertimeHoliday.getAgreementTime(companyID,
				appHolidayWorkDataNoDate.getEmployeeOTs().get(0).getEmployeeIDs(), ApplicationType.BREAK_TIME_APPLICATION);
		if (opAgreeOverTimeOutput.isPresent()) {
			result.setAgreementTimeDto(AgreeOverTimeDto.fromDomain(opAgreeOverTimeOutput.get()));
		}
		
		// 1-1.休日出勤申請（新規）起動時初期データを取得する
		String appDate = null;
		if (!CollectionUtil.isEmpty(appDateInput)) {
			appDate = appDateInput.get(0);
		}
		
		appHolidayWorkDataNoDate.getAppCommonSettingOutput().appEmploymentWorkType = appHolidayWorkDataHasDate.getLstEmploymentWt();

		this.getData(companyID, appHolidayWorkDataNoDate.getEmployeeOTs().get(0).getEmployeeIDs(), appDate, appHolidayWorkDataNoDate.getAppCommonSettingOutput(), result, uiType, payoutType, null, null, overtimeRestAppCommonSet);
		
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		if (!CollectionUtil.isEmpty(appDateInput)) {
			// 07-02_実績取得・状態チェック
			// アルゴリズム「残業申請設定を取得する」を実行する
			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID,
					employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION,
					result.getWorkType().getWorkTypeCode(), result.getWorkTime().getSiftCode(),
					withdrawalAppSet.getOverrideSet(), Optional.empty());
			appHolidayWorkDataHasDate.setActualStatusCheckResult(actualStatusCheckResult);
			// 07-01_事前申請状態チェック
			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(companyID, employeeID,
					GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION);
			appHolidayWorkDataHasDate.setPreAppCheckResult(preAppCheckResult);
		}
		

		result.setAppHolidayWorkDataHasDate(appHolidayWorkDataHasDate);
		
		if (CollectionUtil.isEmpty(result.getHolidayWorkInputDtos())) {
			// 01-03_休出時間を取得
			List<HolidayWorkInputDto> holidayWorkInputDtos = new ArrayList<>();
			getBreaktime(companyID, holidayWorkInputDtos);
			result.setHolidayWorkInputDtos(holidayWorkInputDtos);
		} else {
			// edit mode
			List<HolidayWorkInputDto> holidayWorkInputDtos = new ArrayList<>();
			getBreaktime(companyID, holidayWorkInputDtos);
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
		}
		
		// 01-08_乖離定型理由を取得, 01-07_乖離理由を取得
		getDivigenceReason(overtimeRestAppCommonSet, result, companyID);
		
		return result;
	}
	/**
	 * findChangeAppDate
	 * @param appDate
	 * @param prePostAtr
	 * @param siftCD
	 * @param breakTime
	 * @return
	 */
	public AppHolidayWorkDto findChangeAppDate(List<String> appDateInput,int prePostAtr,String siftCD, List<CaculationTime> breakTime, String changeEmployee, Integer startTime, Integer endTime, 
			AppHolidayWorkDataNoDate appHolidayWorkDataNoDate){
		String companyID = AppContexts.user().companyId();
		AppHolidayWorkDto result = new AppHolidayWorkDto();
		AppHolidayWorkDataHasDate appHolidayWorkDataHasDate = new AppHolidayWorkDataHasDate();
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);
		// xu li hien thi du lieu xin truoc
		if (overtimeRestAppCommonSet.isPresent()) {
			// 時間外表示区分
			result.setExtratimeDisplayFlag(
					overtimeRestAppCommonSet.get().getExtratimeDisplayAtr().value == 1 ? true : false);
		}
		// 申請日を変更する
		// INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする
		if (appHolidayWorkDataNoDate.getAppCommonSettingOutput().getApplicationSetting().getBaseDateFlg().value == BaseDateFlg.SYSTEM_DATE.value) {
			appDateInput = new ArrayList<String>();
			appDateInput.add(GeneralDate.today().toString(DATE_FORMAT));
		}
		appHolidayWorkDataHasDate = this.getAppDisplayInfoHasBaseDate(appDateInput, result, companyID, appHolidayWorkDataNoDate, overtimeRestAppCommonSet, 0);
		result.setAppHolidayWorkDataNoDate(appHolidayWorkDataNoDate);
		result.setAppHolidayWorkDataHasDate(appHolidayWorkDataHasDate);
		
		// 1-1.休日出勤申請（新規）起動時初期データを取得する
		String appDate = null;
		if (!CollectionUtil.isEmpty(appDateInput)) {
			appDate = appDateInput.get(0);
		}

		appHolidayWorkDataNoDate.getAppCommonSettingOutput().appEmploymentWorkType = appHolidayWorkDataHasDate
				.getLstEmploymentWt();
		this.getData(companyID, appHolidayWorkDataNoDate.getEmployeeOTs().get(0).getEmployeeIDs(), appDate,
				appHolidayWorkDataNoDate.getAppCommonSettingOutput(), result, 0, null, null, null,
				overtimeRestAppCommonSet);
		// 07-02_実績取得・状態チェック
		// アルゴリズム「残業申請設定を取得する」を実行する
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		if (!CollectionUtil.isEmpty(appDateInput)) {
			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID,
					result.getAppHolidayWorkDataNoDate().getEmployeeOTs().get(0).getEmployeeIDs(), GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION,
					result.getWorkType().getWorkTypeCode(), result.getWorkTime().getSiftCode(),
					withdrawalAppSet.getOverrideSet(), Optional.empty());
			appHolidayWorkDataHasDate.setActualStatusCheckResult(actualStatusCheckResult);
		}

		return result;
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
	public ColorConfirmResult calculationresultConfirm(List<CaculationTime> breakTime ,
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
	public List<CaculationTime> getCaculationValue(List<CaculationTime> breakTime ,
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
	}
	
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
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value).get();
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		// 07-01_事前申請状態チェック
		PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
				companyID, 
				employeeID, 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType.BREAK_TIME_APPLICATION);
		// 07-02_実績取得・状態チェック
		ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
				companyID, 
				employeeID, 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType.BREAK_TIME_APPLICATION, 
				workTypeCD, 
				workTimeCD, 
				withdrawalAppSet.getOverrideSet(), 
				Optional.empty());
		// 07_事前申請・実績超過チェック
		PreActualColorResult preActualColorResult =	preActualColorCheck.preActualColorCheck(
				preExcessDisplaySetting, 
				performanceExcessAtr, 
				ApplicationType.BREAK_TIME_APPLICATION, 
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
	 * @param appID
	 * @return
	 */
	public AppHolidayWorkDto getAppHolidayWorkByAppID(String appID){
		
		String companyID = AppContexts.user().companyId();
		// 14-1.詳細画面起動前申請共通設定を取得する
		// 詳細画面の申請データを取得する
		Optional<AppHolidayWork> opAppHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, appID);
		if(!opAppHolidayWork.isPresent()){
			throw new BusinessException("Msg_198");
		}
		AppHolidayWork appHolidayWork = opAppHolidayWork.get();
		List<String> appDateInput = new ArrayList<String>();
		appDateInput.add(!appHolidayWork.getApplication().getStartDate().isPresent() ? null : appHolidayWork.getApplication().getStartDate().get().toString(DATE_FORMAT));
		AppHolidayWorkDto appHolidayWorkDto = AppHolidayWorkDto.fromDomain(appHolidayWork);
		// 起動時の申請表示情報を取得する
		getAppHolidayWork(appDateInput, 0, new ArrayList<String>() , null , appHolidayWork.getApplication().getEmployeeID(), appHolidayWorkDto);		
		
		// 詳細画面の利用者とステータスを取得する
		DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput = this.beforePreBootMode.judgmentDetailScreenMode(companyID, appHolidayWork.getApplication().getEmployeeID(), appID, appHolidayWorkDto.getAppHolidayWorkDataNoDate().getAppCommonSettingOutput().generalDate);
		// 詳細画面の画面モードを判断する
		DetailScreenInitModeOutput detailScreenInitModeOutput = this.initMode.getDetailScreenInitMode(detailedScreenPreBootModeOutput.getUser(), detailedScreenPreBootModeOutput.getReflectPlanState().value);
		appHolidayWorkDto.setDetailedScreenPreBootModeOutput(detailedScreenPreBootModeOutput);
		appHolidayWorkDto.setDetailScreenInitModeOutput(detailScreenInitModeOutput);
		
		// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
		List<OvertimeColorCheck> holidayLst = new ArrayList<>();
		appHolidayWorkDto.getHolidayWorkInputDtos().forEach(holidayInput -> {
			if (holidayInput.getAttendanceType() == 2) {
				holidayLst.add(OvertimeColorCheck.createApp(2, holidayInput.getFrameNo(), holidayInput.getApplicationTime()));
			}		
		});
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.get().getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.get().getPerformanceExcessAtr();
		PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(preExcessDisplaySetting,
				performanceExcessAtr, appHolidayWork.getApplication().getAppType(),
				appHolidayWork.getApplication().getPrePostAtr(), Collections.emptyList(), holidayLst,
				appHolidayWorkDto.getAppHolidayWorkDataHasDate().getPreAppCheckResult().opAppBefore, appHolidayWorkDto.getAppHolidayWorkDataHasDate().getPreAppCheckResult().beforeAppStatus,
				appHolidayWorkDto.getAppHolidayWorkDataHasDate().getActualStatusCheckResult().actualLst,
				appHolidayWorkDto.getAppHolidayWorkDataHasDate().getActualStatusCheckResult().actualStatus);
		List<CaculationTime> caculationTimes = preActualColorResult.resultLst.stream()
				.map(x -> new CaculationTime(companyID, appID, x.attendanceID, x.frameNo, 0, "", x.appTime,
						x.preAppTime == null ? null : x.preAppTime.toString(),
						x.actualTime == null ? null : x.actualTime.toString(),
						getErrorCode(x.calcError, x.preAppError, x.actualError), false,
						preActualColorResult.beforeAppStatus, preActualColorResult.actualStatus == 3))
				.collect(Collectors.toList());
		appHolidayWorkDto.setCaculationTimes(caculationTimes);
		appHolidayWorkDto.setPreActualColorResult(preActualColorResult);

		return appHolidayWorkDto;
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
	
	private boolean isSettingDisplay(AppCommonSettingOutput appCommonSettingOutput) {
		return appCommonSettingOutput.approvalFunctionSetting.getApplicationDetailSetting().get()
				.getBreakInputFieldDisp().equals(true)
				&& appCommonSettingOutput.getApprovalFunctionSetting().getApplicationDetailSetting().get()
						.getTimeCalUse().equals(UseAtr.USE);

	}
	
	/**
	 * 01-14_勤務時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param siftCD
	 * @return
	 */
	public RecordWorkDto getRecordWork(String employeeID, String appDate, String siftCD,int prePortAtr,List<CaculationTime> overtimeHours, String workTypeCD){
		String companyID = AppContexts.user().companyId();
		Integer startTime1 = -1; 
		Integer endTime1 = -1;
		Integer startTime2 = -1;
		Integer endTime2 = -1;
		AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				1, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class), appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		// 休憩時間帯を取得する
		List<DeductionTimeDto> timeZones = getBreakTimes(workTypeCD, siftCD, Optional.empty(), Optional.empty());
		// 01-14_勤務時間取得(lay thoi gian): Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」)
		RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(companyID, employeeID, null,
				appDate,approvalFunctionSetting,siftCD, false);
		startTime1 = recordWorkOutput.getStartTime1();
		endTime1 = recordWorkOutput.getEndTime1();
		startTime2 = recordWorkOutput.getStartTime2();
		endTime2 = recordWorkOutput.getEndTime2();
		
		// 07-02_実績取得・状態チェック
		// アルゴリズム「残業申請設定を取得する」を実行する
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID, employeeID,
				GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.BREAK_TIME_APPLICATION, workTypeCD,
				siftCD, withdrawalAppSet.getOverrideSet(), Optional.empty());
		
		return new RecordWorkDto(startTime1, endTime1, startTime2, endTime2, appOvertimeReference, timeZones, actualStatusCheckResult);
	}
	
	/**
	 * 1-1.休日出勤申請（新規）起動時初期データを取得する
	 * @param companyID
	 * @param employeeID
	 * @param appDateHolidayWork
	 * @param appCommonSettingOutput
	 * @param result
	 */
	private void getData(String companyID,String employeeID,String appDate,AppCommonSettingOutput appCommonSettingOutput,AppHolidayWorkDto result,
			int uiType,Integer payoutType, Integer startTime, Integer endTime, Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet){
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		List<HolidayWorkInputDto> holidayWorkInputDtos = new ArrayList<>();
		//01-12_申請日付取得
		applicationDto.setApplicationDate(appDate);
		// 01-01_休出通知情報を取得
		HolidayWorkInstruction holidayWorkInstruction = holidayPreProcess.getHolidayInstructionInformation(appCommonSettingOutput, appDate, employeeID);
		result.setDisplayHolidayInstructInforFlg(holidayWorkInstruction.isDisplayHolidayWorkInstructInforFlg());
		result.setHolidayInstructInformation(holidayWorkInstruction.getHolidayWorkInstructInfomation());
		// 1-2.起動時勤務種類リストを取得する
		// 1-3.起動時勤務種類・就業時間帯の初期選択
		// 01-01_休憩時間を取得する
		getWorkTypeAndWorkTime(companyID, employeeID, appCommonSettingOutput, result, uiType, payoutType, false,
				startTime, endTime);
		// 01-14_勤務時間取得
		getWorkingHour(companyID, employeeID, appDate, appCommonSettingOutput, result, "", null);
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
		String employeeName = "";
		if(Strings.isNotBlank(result.getApplication().getApplicantSID())){
			employeeName = employeeAdapter.getEmployeeName(result.getApplication().getApplicantSID());
			result.setEmployeeID(result.getApplication().getApplicantSID());
		} else {
			employeeName = employeeAdapter.getEmployeeName(employeeID);
			result.setEmployeeID(employeeID);
		}
		result.setEmployeeName(employeeName);
		if(appCommonSettingOutput.getApprovalFunctionSetting()!=null){
			result.setEnableOvertimeInput(appCommonSettingOutput.getApprovalFunctionSetting().getApplicationDetailSetting().get().getTimeInputUse().value==1?true:false);
		}
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.get().getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.get().getPerformanceExcessAtr();
		result.setPreExcessDisplaySetting(preExcessDisplaySetting.value);
		result.setPerformanceExcessAtr(performanceExcessAtr.value);
	}
	/**
	 * 01-03_休出時間を取得
	 * @param companyID
	 * @param holidayWorkInputDtos
	 */
	private void getBreaktime(String companyID,List<HolidayWorkInputDto> holidayWorkInputDtos){
		List<WorkdayoffFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		for(WorkdayoffFrame breaktimeFrame :breaktimeFrames){
			HolidayWorkInputDto holidayWorkInputDto = new HolidayWorkInputDto();
			holidayWorkInputDto.setAttendanceType(AttendanceType.BREAKTIME.value);
			holidayWorkInputDto.setFrameNo(breaktimeFrame.getWorkdayoffFrNo().v().intValueExact());
			holidayWorkInputDto.setFrameName(breaktimeFrame.getWorkdayoffFrName().toString());
			holidayWorkInputDtos.add(holidayWorkInputDto);
		}
	}
	/**
	 * 4.勤務種類を取得する, 5.就業時間帯を取得する,01-14_勤務時間取得, 01-17_休憩時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param appCommonSettingOutput
	 * @param result
	 */
	private void getWorkTypeAndWorkTime(String companyID,String employeeID,AppCommonSettingOutput appCommonSettingOutput,AppHolidayWorkDto result,
			int uiType,Integer payoutType,boolean isChangeDate, Integer startTime, Integer endTime){
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		result.setDisplayCaculationTime(false);
		if(approvalFunctionSetting != null){
			result.setDisplayCaculationTime(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE));
		}
			// 時刻計算利用チェック
//			if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
				// 4.勤務種類を取得する :
				WorkTypeHolidayWork WorkTypes = null;
				if (result.getWorkType() == null) {
					GeneralDate baseDate = appCommonSettingOutput.generalDate;
					//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
					Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeID,baseDate);
					List<AppEmploymentSetting> appEmploymentWorkType = appCommonSettingOutput.appEmploymentWorkType;
					WorkTypes = new WorkTypeHolidayWork();
					if(uiType != 1){
						// 4.勤務種類を取得する
						WorkTypes = holidayService.getWorkTypes(companyID, employeeID, appEmploymentWorkType, baseDate,
						personalLablorCodition, isChangeDate);
					}else{
						// 4_a.勤務種類を取得する（法定内外休日） 
						WorkTypes = holidayService.getWorkTypeForLeaverApp(companyID, employeeID, appEmploymentWorkType, baseDate, personalLablorCodition, payoutType);
					}
					result.setWorkTypes(WorkTypes.getWorkTypeCodes());
					WorkTypeOvertime workType = new WorkTypeOvertime();
					workType.setWorkTypeCode(WorkTypes.getWorkTypeCode());
					workType.setWorkTypeName(WorkTypes.getWorkTypeName());
					result.setWorkType(workType);
					// 5.就業時間帯を取得する
					WorkTimeHolidayWork workTimes = this.holidayService.getWorkTimeHolidayWork(companyID, employeeID, baseDate, personalLablorCodition,isChangeDate);
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
	}
	/**
	 * getWorkingHour
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param appCommonSettingOutput
	 * @param result
	 * @param workTimeCode
	 */
	private void getWorkingHour(String companyID,String employeeID,String appDate,AppCommonSettingOutput appCommonSettingOutput,AppHolidayWorkDto result,String workTimeCode, String changeEmployee){
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		if(approvalFunctionSetting != null){
			result.setDisplayCaculationTime(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE));
		}
//			if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
//				result.setDisplayCaculationTime(true);
				// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
				RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(companyID, 
						employeeID, changeEmployee, appDate,approvalFunctionSetting,result.getWorkTime() == null ? 
								workTimeCode : result.getWorkTime().getSiftCode(), false);
				result.setWorkClockStart1(recordWorkOutput.getStartTime1());
				result.setWorkClockStart2(recordWorkOutput.getStartTime2());
				result.setWorkClockEnd1(recordWorkOutput.getEndTime1());
				result.setWorkClockEnd2(recordWorkOutput.getEndTime2());
//			}
//		}
	}
	/**
	 * convert BonusPayTimeItem -> HolidayWorkInputDto
	 * @param bonusPayTimeItem
	 * @param attendanceType
	 * @return
	 */
	private HolidayWorkInputDto convertBonusPayTimeItem(BonusPayTimeItem bonusPayTimeItem,int attendanceType){
		HolidayWorkInputDto holidayWorkInputDto = new HolidayWorkInputDto();
		holidayWorkInputDto.setAttendanceType(attendanceType);
		holidayWorkInputDto.setFrameNo(bonusPayTimeItem.getId());
		holidayWorkInputDto.setFrameName(bonusPayTimeItem.getTimeItemName().toString());
		return holidayWorkInputDto;
	}

	/**
	 * 01-08_乖離定型理由を取得, 01-07_乖離理由を取得
	 * @param overtimeRestAppCommonSet
	 * @param result
	 * @param companyID
	 */
	private void getDivigenceReason(Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet,AppHolidayWorkDto result,String companyID){
		
		if(overtimeRestAppCommonSet.isPresent()){
			//01-08_乖離定型理由を取得
			result.setDisplayDivergenceReasonForm(false);
			if(result.getApplication().getPrePostAtr() != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
				result.setDisplayDivergenceReasonForm(true);
				List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
						.getDivergenceReasonForm(
								companyID,  
								EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class), 
								overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr(), 
								ApplicationType.BREAK_TIME_APPLICATION);
				convertToDivergenceReasonDto(divergenceReasons,result);
			}
			//01-07_乖離理由を取得
			result.setDisplayDivergenceReasonInput(
					commonOvertimeHoliday.displayDivergenceReasonInput(
							EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class), 
							overtimeRestAppCommonSet.get().getDivergenceReasonInputAtr()));
		}
	}
	
	/**
	 * convertToDivergenceReasonDto
	 * @param divergenceReasons
	 * @param result
	 */
	private void convertToDivergenceReasonDto(List<DivergenceReason> divergenceReasons, AppHolidayWorkDto result){
		List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
		for(DivergenceReason divergenceReason : divergenceReasons){
			DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
			divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
			divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
			divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
			
			divergenceReasonDtos.add(divergenceReasonDto);
		}
		result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	
	/**
	 * 申請表示情報(基準日関係なし)を取得する
	 * @param 会社ID = INPUT．「会社ID」
	 * @param 申請者リスト = INPUT．「申請者リスト」
	 * @param 申請種類 = INPUT．「申請種類」
	 */
	public AppHolidayWorkDataNoDate getAppDisplayInfoNoBaseDate(AppHolidayWorkDto result, String companyID, List<String> lstEmployee, String employeeID) {
		AppHolidayWorkDataNoDate appHolidayWorkDataNoDate = new AppHolidayWorkDataNoDate();
		// 申請者情報を取得する
		List<EmployeeOvertimeDto> employeeOTs = new ArrayList<>();
		if(CollectionUtil.isEmpty(lstEmployee) && employeeID == null){
			 employeeID = AppContexts.user().employeeId();
			 employeeOTs.add(new EmployeeOvertimeDto(employeeID, null));
		}else if(!CollectionUtil.isEmpty(lstEmployee)){
			employeeID = lstEmployee.get(0);
			List<EmployeeInfoImport> employees = this.atEmployeeAdapter.getByListSID(lstEmployee);
			if(!CollectionUtil.isEmpty(employees)){
				for(EmployeeInfoImport emp : employees){
					EmployeeOvertimeDto employeeOT = new EmployeeOvertimeDto(emp.getSid(), emp.getBussinessName());
					employeeOTs.add(employeeOT);
				}
			}
		} else {
			employeeOTs.add(new EmployeeOvertimeDto(employeeID, null));
		}
		appHolidayWorkDataNoDate.setEmployeeOTs(employeeOTs);
		// 申請承認設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				1, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class), null);
		appHolidayWorkDataNoDate.setAppCommonSettingOutput(appCommonSettingOutput);
		result.setRequireAppReasonFlg(appCommonSettingOutput.getApplicationSetting().getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED) ? true : false);
		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  ==1 ?true : false);
		result.setSendMailWhenApprovalFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenApprovalFlg().value == 1 ? true : false);
		result.setSendMailWhenRegisterFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenRegisterFlg().value == 1 ? true : false);
		
		// 01-05_申請定型理由を取得
		result.setTypicalReasonDisplayFlg(false);
		if (appCommonSettingOutput.appTypeDiscreteSettings.get(0).getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value) {
			result.setTypicalReasonDisplayFlg(true);
			List<ApplicationReason> applicationReasons = otherCommonAlgorithm.getApplicationReasonType(companyID,
					appCommonSettingOutput.appTypeDiscreteSettings.get(0).getTypicalReasonDisplayFlg(), ApplicationType.BREAK_TIME_APPLICATION);
			List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
			for (ApplicationReason applicationReason : applicationReasons) {
				ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
						applicationReason.getReasonTemp().v(), applicationReason.getDefaultFlg().value);
				applicationReasonDtos.add(applicationReasonDto);
			}
			appHolidayWorkDataNoDate.setApplicationReasonDtos(applicationReasonDtos);
		}
		//01-06_申請理由を取得
		result.setDisplayAppReasonContentFlg(otherCommonAlgorithm.displayAppReasonContentFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getDisplayReasonFlg()));
		return appHolidayWorkDataNoDate;
		
	}
	
	/**
	 * 申請表示情報(基準日関係あり)を取得する
	 * @param 会社ID = INPUT．「会社ID」
	 * @param 申請者リスト = INPUT．「申請者リスト」
	 * @param 申請種類 = INPUT．「申請種類」
	 * @param 申請表示情報(基準日関係なし) = 取得した「申請表示情報(基準日関係なし)」
	 * @param 新規詳細モード = INPUT．「新規詳細モード」
	 */
	public AppHolidayWorkDataHasDate getAppDisplayInfoHasBaseDate(List<String> appDateInput, AppHolidayWorkDto result, String companyID, AppHolidayWorkDataNoDate appHolidayWorkDataNoDate, 
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet, int uiType) {
		AppHolidayWorkDataHasDate appHolidayWorkDataHasDate = new AppHolidayWorkDataHasDate();
		int prePostAtr = 0;
		String employeeID = appHolidayWorkDataNoDate.getEmployeeOTs().get(0).getEmployeeIDs();
		AppCommonSettingOutput appCommonSettingOutput = appHolidayWorkDataNoDate.getAppCommonSettingOutput();
		// 基準日として扱う日の取得
		String appDate = null;
		if (!CollectionUtil.isEmpty(appDateInput)) {
			appDate = appDateInput.get(0);			
		}
		GeneralDate baseDate = getBaseDate.getBaseDate(appDate == null ? Optional.empty() : Optional.of(GeneralDate.fromString(appDate, DATE_FORMAT)));
		
		appHolidayWorkDataHasDate.setBaseDate(baseDate);
		// 社員IDから申請承認設定情報の取得
		// 申請本人の所属職場を含める上位職場を取得する 
		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, baseDate);
		List<ApprovalFunctionSetting> loopResult = new ArrayList<>();
		for (String workPlaceID : workPlaceIDs) {
			// ドメインモデル「職場別申請承認設定」を取得する
			Optional<ApprovalFunctionSetting> settingOfEarchWorkplaceOp = requestOfEachWorkplaceRepository
					.getFunctionSetting(companyID, workPlaceID, ApplicationType.BREAK_TIME_APPLICATION.value);
			if (settingOfEarchWorkplaceOp.isPresent()) {
				loopResult.add(settingOfEarchWorkplaceOp.get());
				break;
			}
		}
		
		ApprovalFunctionSetting approvalFunctionSetting = null;
		if (loopResult.size() == 0) {
			// ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval
			// setting by company" )
			Optional<ApprovalFunctionSetting> rqOptional = requestOfEachCompanyRepository.getFunctionSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);
			if (rqOptional.isPresent()) {
				approvalFunctionSetting = rqOptional.get();
			}
		} else {
			// ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval
			// setting by company" )
			approvalFunctionSetting = loopResult.get(0);
		}
		appHolidayWorkDataHasDate.setApprovalFunctionSetting(approvalFunctionSetting);
		
		// 承認ルートを取得
		ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(companyID, employeeID, EmploymentRootAtr.APPLICATION, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class), appCommonSettingOutput.generalDate, "", true);
		// 取得したドメインモデル「申請承認機能設定．申請利用設定．利用区分」をチェックする
		startupErrorCheckService.startupErrorCheck(appHolidayWorkDataNoDate.getAppCommonSettingOutput().generalDate, ApplicationType.BREAK_TIME_APPLICATION.value, approvalRootPattern.getApprovalRootContentImport());
		appHolidayWorkDataHasDate.setApprovalRootPattern(approvalRootPattern);
		
		// 使用可能な就業時間帯を取得する
		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,baseDate).stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
		appHolidayWorkDataHasDate.setListWorkTimeCodes(listWorkTimeCodes);
		
		// 社員所属雇用履歴を取得する
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, employeeID, baseDate);
		if (empHistImport == null || empHistImport.getEmploymentCode() == null) {
			throw new BusinessException("Msg_426");
		}
		// 雇用別申請承認設定を取得する
		List<AppEmploymentSetting> lstEmploymentWt = appEmploymentSetting.getEmploymentSetting(companyID, empHistImport.getEmploymentCode(), ApplicationType.BREAK_TIME_APPLICATION.value);
		appHolidayWorkDataHasDate.setLstEmploymentWt(lstEmploymentWt);

		// INPUT．事前事後区分表示をチェックする
		// 01-13_事前事後区分を取得
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		DisplayPrePost displayPrePost = commonOvertimeHoliday.getDisplayPrePost(companyID,
				ApplicationType.BREAK_TIME_APPLICATION, uiType, OverTimeAtr.ALL,
				appDate == null ? GeneralDate.today() : GeneralDate.fromString(appDate, DATE_FORMAT),
				appHolidayWorkDataNoDate.getAppCommonSettingOutput().getApplicationSetting().getDisplayPrePostFlg());
		result.setDisplayPrePostFlg(displayPrePost.getDisplayPrePostFlg());
		applicationDto.setPrePostAtr(displayPrePost.getPrePostAtr());
		if (result.getApplication() == null) {
			result.setApplication(applicationDto);
		}
		result.setPrePostCanChangeFlg(displayPrePost.isPrePostCanChangeFlg());
		appHolidayWorkDataHasDate.setPrePostAtr(prePostAtr);
		
		return appHolidayWorkDataHasDate;
	}
}
