package nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CommonCompanySettingForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.record.dom.divergence.time.service.DivTimeSysFixedCheckService;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service.ErAlCheckService;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.SystemFixedErrorAlarm;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.CheckExcessAtr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.DailyStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 日別計算用のエラーチェック
 * @author keisuke_hoshina
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CalculationErrorCheckServiceImpl implements CalculationErrorCheckService{
	@Inject
	/*システム固定エラーアラームに対する処理*/
	private DivTimeSysFixedCheckService divTimeSysFixedCheckService;
	
	@Inject
	private ErAlCheckService erAlCheckService;
	
	@Inject
	private AttendanceItemConvertFactory converterFactory;
	//エラーアラーム設定
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
	@Inject
	/*日別作成側に実装されていたエラーアラーム処理*/
	private DailyRecordCreateErrorAlermService dailyRecordCreateErrorAlermService;
	
	@Inject
	private CommonCompanySettingForCalc commonCompanySettingForCalc;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private CalculationErrorCheckService calculationErrorCheckService;
	
	@Inject 
	private RecordDomRequireService requireService;
	
	@Inject
	private FactoryManagePerPersonDailySet factoryManagePerPersonDailySet;
	
	@Override
	public IntegrationOfDaily errorCheck(IntegrationOfDaily integrationOfDaily, ManagePerPersonDailySet personCommonSetting, ManagePerCompanySet master) {
		String companyID = AppContexts.user().companyId();
		List<EmployeeDailyPerError> addItemList = integrationOfDaily.getEmployeeError() == null? integrationOfDaily.getEmployeeError() :new ArrayList<>();
		List<ErrorAlarmWorkRecord> divergenceError = new ArrayList<>();
		DailyRecordToAttendanceItemConverter attendanceItemConverter = this.converterFactory.createDailyConverter().setData(integrationOfDaily);
		//勤務実績のエラーアラーム数分ループ
		for(ErrorAlarmWorkRecord errorItem : errorAlarmWorkRecordRepository.getAllErAlCompanyAndUseAtr(companyID, true)) {
			//使用しない
			if(!errorItem.getUseAtr()) continue;
			//乖離系のシステムエラーかどうかチェック
			if(includeDivergence(errorItem)) { 
				divergenceError.add(errorItem);
				continue;
			}
			//システム固定
			List<EmployeeDailyPerError>  addItems = new ArrayList<>();
			if(errorItem.getFixedAtr()) {
				addItems = systemErrorCheck(integrationOfDaily,errorItem,attendanceItemConverter, master);
			}
			
			//ユーザ設定
			else {
				addItems = erAlCheckService.checkErrorFor(companyID, integrationOfDaily.getEmployeeId(), 
						integrationOfDaily.getYmd(), errorItem, integrationOfDaily);
				//addItemList.addAll(addItems);
			}
			addItemList.addAll(addItems);
		}
		
		//乖離系のエラーはここでまとめてチェック(レスポンス対応のため)
		addItemList.addAll(divergenceErrorCheck(integrationOfDaily, master, divergenceError));
		addItemList = addItemList.stream().filter(tc -> tc != null).collect(Collectors.toList());
		integrationOfDaily.setEmployeeError(addItemList);
		return integrationOfDaily;
	}
	

	/**
	 * 乖離系のエラーアラームチェック
	 * @return　エラーアラーム一覧
	 */
	private List<EmployeeDailyPerError> divergenceErrorCheck(IntegrationOfDaily integrationOfDaily,ManagePerCompanySet master,List<ErrorAlarmWorkRecord> errorList) {
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			TimeLeavingOfDailyPerformance dailyPerformance = new TimeLeavingOfDailyPerformance(integrationOfDaily.getEmployeeId(), 
					integrationOfDaily.getYmd(), 
					integrationOfDaily.getAttendanceLeave().isPresent() ? integrationOfDaily.getAttendanceLeave().get() : null);
			return divTimeSysFixedCheckService.divergenceTimeCheckBySystemFixed(AppContexts.user().companyId(), 
																	 	 		integrationOfDaily.getEmployeeId(), 
																	 	 		integrationOfDaily.getYmd(),
																	 	 		integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime().getDivergenceTime(),
																	 	 		Optional.ofNullable(dailyPerformance),
																	 	 		errorList,
																	 	 		master.getDivergenceTime(),
																	 	 		master.getShareContainer());
		}
		return Collections.emptyList();
	}

	/**
	 * 該当のエラーが乖離系であるか判定する
	 * @param errorItem エラーアラーム
	 * @return　乖離系である
	 */
	private boolean includeDivergence(ErrorAlarmWorkRecord errorItem) {
		Optional<SystemFixedErrorAlarm> fixedErrorAlarmCode = SystemFixedErrorAlarm.getEnumFromErrorCode(errorItem.getCode().toString());
		if(!fixedErrorAlarmCode.isPresent()) 
			return false; 
		switch (fixedErrorAlarmCode.get()) {
		case ERROR_OF_DIVERGENCE_TIME:
		case ALARM_OF_DIVERGENCE_TIME:
		case DIVERGENCE_ERROR_1:
		case DIVERGENCE_ALARM_1:
		case DIVERGENCE_ERROR_2:
		case DIVERGENCE_ALARM_2:
		case DIVERGENCE_ERROR_3:
		case DIVERGENCE_ALARM_3:
		case DIVERGENCE_ERROR_4:
		case DIVERGENCE_ALARM_4:
		case DIVERGENCE_ERROR_5:
		case DIVERGENCE_ALARM_5:
		case DIVERGENCE_ERROR_6:
		case DIVERGENCE_ALARM_6:
		case DIVERGENCE_ERROR_7:
		case DIVERGENCE_ALARM_7:
		case DIVERGENCE_ERROR_8:
		case DIVERGENCE_ALARM_8:
		case DIVERGENCE_ERROR_9:
		case DIVERGENCE_ALARM_9:	
		case DIVERGENCE_ERROR_10:
		case DIVERGENCE_ALARM_10:
			return true;
		default:
			return false;
		}
	}

	/**
	 * システム固定エラーチェック
	 * @param attendanceItemConverter 
	 * @return 社員の日別実績エラー一覧
	 */
	public List<EmployeeDailyPerError> systemErrorCheck(IntegrationOfDaily integrationOfDaily,ErrorAlarmWorkRecord errorItem, 
			DailyRecordToAttendanceItemConverter attendanceItemConverter, ManagePerCompanySet master) {
		Optional<SystemFixedErrorAlarm> fixedErrorAlarmCode = SystemFixedErrorAlarm.getEnumFromErrorCode(errorItem.getCode().toString());
		//if(!integrationOfDaily.getAttendanceLeave().isPresent() || !fixedErrorAlarmCode.isPresent())
		if(!fixedErrorAlarmCode.isPresent())
			return Collections.emptyList();

		switch(fixedErrorAlarmCode.get()) {
			//事前残業申請超過
			case PRE_OVERTIME_APP_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_OVERTIME_APP_EXCESS);
			//事前休出申請超過
			case PRE_HOLIDAYWORK_APP_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_HOLIDAYWORK_APP_EXCESS);
			//事前フレックス申請超過
			case PRE_FLEX_APP_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_FLEX_APP_EXCESS);
			//事前深夜申請超過
			case PRE_MIDNIGHT_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_MIDNIGHT_EXCESS);
			//残業時間実績超過
			case OVER_TIME_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.OVER_TIME_EXCESS);
			//休出時間実績超過
			case REST_TIME_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.REST_TIME_EXCESS);
			//フレックス時間実績超過
			case FLEX_OVER_TIME:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.FLEX_OVER_TIME);
			//深夜時間実績超過
			case MIDNIGHT_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.MIDNIGHT_EXCESS);
			//遅刻
			case LATE:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.LATE);
			//早退
			case LEAVE_EARLY:
				return integrationOfDaily.getErrorList(integrationOfDaily.getEmployeeId(), 
												integrationOfDaily.getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.LEAVE_EARLY);
			//出勤打刻漏れ
			case TIME_LEAVING_STAMP_LEAKAGE:
				return dailyRecordCreateErrorAlermService.lackOfTimeLeavingStamping(integrationOfDaily);
			//入退門漏
			case ENTRANCE_STAMP_LACKING:
				return dailyRecordCreateErrorAlermService.lackOfAttendanceGateStamping(integrationOfDaily);
			//PCログ打刻漏れ
			case PCLOG_STAMP_LEAKAGE:
				return dailyRecordCreateErrorAlermService.lackOfAttendancePCLogOnStamping(integrationOfDaily);
			//打刻順序不正
			case INCORRECT_STAMP: 
				return dailyRecordCreateErrorAlermService.stampIncorrectOrderAlgorithm(integrationOfDaily);
			//休日打刻
			case HOLIDAY_STAMP:
				val result = dailyRecordCreateErrorAlermService.checkHolidayStamp(integrationOfDaily);
				if(!result.isPresent()) {
					return Collections.emptyList();
				}
				else {
					return Arrays.asList(result.get());
				}
				
			//二重打刻
			case DOUBLE_STAMP:
				return dailyRecordCreateErrorAlermService.doubleStampAlgorithm(integrationOfDaily);
			//申告
			case DECLARE:
				return integrationOfDaily.getDeclareErrorList(fixedErrorAlarmCode.get());
			//それ以外ルート
			default:
				return Collections.emptyList();
		}
	}


	@Override
	public IntegrationOfDaily errorCheck(String companyId, String employeeID, GeneralDate ymd,
			IntegrationOfDaily integrationOfDaily, boolean sysfixecategory) {
		// 会社共通の設定を
		val companyCommonSetting = commonCompanySettingForCalc.getCompanySetting();

		// 社員毎の期間取得
		val integraListByRecordAndEmpId = getIntegrationOfDailyByEmpId(Arrays.asList(integrationOfDaily));

		// 労働制マスタ取得
		val masterData = workingConditionItemRepository
				.getBySidAndPeriodOrderByStrDWithDatePeriod(integraListByRecordAndEmpId, ymd, ymd);

		// nowIntegrationの労働制取得
		Optional<Entry<DateHistoryItem, WorkingConditionItem>> nowWorkingItem = masterData
				.getItemAtDateAndEmpId(integrationOfDaily.getYmd(), integrationOfDaily.getEmployeeId());
		if (nowWorkingItem.isPresent()) {
			DailyUnit dailyUnit = DailyStatutoryLaborTime.getDailyUnit(
					requireService.createRequire(),
					new CacheCarrier(), companyId,
					integrationOfDaily.getAffiliationInfor().getEmploymentCode().toString(),
					integrationOfDaily.getEmployeeId(), integrationOfDaily.getYmd(),
					nowWorkingItem.get().getValue().getLaborSystem());
			if (dailyUnit == null || dailyUnit.getDailyTime() == null)
				dailyUnit = new DailyUnit(new TimeOfDay(0));
			else {
				if (companyCommonSetting.getShareContainer() == null) {
					MasterShareContainer<String> shareContainer = MasterShareBus.open();
					companyCommonSetting.setShareContainer(shareContainer);
				}
				Optional<ManagePerPersonDailySet> optManagePerPersonDailySet = factoryManagePerPersonDailySet.create(companyId, companyCommonSetting, 
						integrationOfDaily, nowWorkingItem.get().getValue());
				if(optManagePerPersonDailySet.isPresent()) {
					integrationOfDaily = errorCheckNew(integrationOfDaily,
						optManagePerPersonDailySet.get(),
						companyCommonSetting, sysfixecategory);
				}
			}
		}
		return integrationOfDaily;
	}

	public IntegrationOfDaily errorCheckNew(IntegrationOfDaily integrationOfDaily,
			ManagePerPersonDailySet personCommonSetting, ManagePerCompanySet master, boolean sysfixecategory) {
		String companyID = AppContexts.user().companyId();
		List<EmployeeDailyPerError> addItemList = integrationOfDaily.getEmployeeError() == null
				? integrationOfDaily.getEmployeeError()
				: new ArrayList<>();
		List<ErrorAlarmWorkRecord> divergenceError = new ArrayList<>();
		DailyRecordToAttendanceItemConverter attendanceItemConverter = this.converterFactory.createDailyConverter()
				.setData(integrationOfDaily);
		// 勤務実績のエラーアラーム数分ループ
		for (ErrorAlarmWorkRecord errorItem : errorAlarmWorkRecordRepository.getAllErAlCompanyAndUseAtr(companyID, true)) {
			// 使用しない
			if (!errorItem.getUseAtr())
				continue;
			// 乖離系のシステムエラーかどうかチェック
			if (includeDivergence(errorItem)) {
				divergenceError.add(errorItem);
				continue;
			}
			// システム固定
			List<EmployeeDailyPerError> addItems = new ArrayList<>();
			if (sysfixecategory) {
				if(errorItem.getFixedAtr()) {
					addItems = systemErrorCheck(integrationOfDaily, errorItem, attendanceItemConverter, master);
				}
			}else {
				if(!errorItem.getFixedAtr()) {
					// ユーザ設定
					addItems = erAlCheckService.checkErrorFor(companyID, integrationOfDaily.getEmployeeId(),
							integrationOfDaily.getYmd(), errorItem, integrationOfDaily);
				}
			}
			addItemList.addAll(addItems);
		}

		// 乖離系のエラーはここでまとめてチェック(レスポンス対応のため)
		addItemList.addAll(divergenceErrorCheck(integrationOfDaily, master, divergenceError));
		addItemList = addItemList.stream().filter(tc -> tc != null).collect(Collectors.toList());
		integrationOfDaily.setEmployeeError(addItemList);
		return integrationOfDaily;
	}

	/**
	 * 日別実績(WORK)Listから社員毎の計算したい期間を取得する
	 * 
	 * @param integrationOfDaily
	 * @return <社員、計算したい期間
	 */
	private Map<String, DatePeriod> getIntegrationOfDailyByEmpId(List<IntegrationOfDaily> integrationOfDaily) {
		Map<String, DatePeriod> returnMap = new HashMap<>();
		// しゃいんID 一覧取得
		List<String> idList = getAllEmpId(integrationOfDaily);
		idList.forEach(id -> {
			// 特定の社員IDに一致しているintegrationに絞る
			val integrationOfDailys = integrationOfDaily.stream().filter(tc -> tc.getEmployeeId().equals(id))
					.collect(Collectors.toList());
			// 特定社員の開始～終了を取得する
			val createdDatePriod = getDateSpan(integrationOfDailys);
			// Map<特定の社員ID、開始～終了>に追加する
			returnMap.put(id, createdDatePriod);
		});
		return returnMap;
	}

	/*
	 * 社員の一覧取得
	 */
	private List<String> getAllEmpId(List<IntegrationOfDaily> integrationOfDailys) {
		return integrationOfDailys.stream().distinct().map(integrationOfDaily -> integrationOfDaily.getEmployeeId())
				.collect(Collectors.toList());
	}

	/**
	 * 開始～終了の作成
	 * @param integrationOfDaily 日別実績(WORK)LIST
	 * @return 開始～終了
	 */
	private DatePeriod getDateSpan(List<IntegrationOfDaily> integrationOfDailys) {
		val sortedIntegration = integrationOfDailys.stream().sorted((first,second) -> first.getYmd().compareTo(second.getYmd())).collect(Collectors.toList());
		return new DatePeriod(sortedIntegration.get(0).getYmd(), sortedIntegration.get(sortedIntegration.size() - 1).getYmd());
	}
}
