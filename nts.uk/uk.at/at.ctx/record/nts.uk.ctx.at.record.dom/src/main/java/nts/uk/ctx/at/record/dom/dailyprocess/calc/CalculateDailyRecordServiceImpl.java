package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.midnight.MidnightTimeSheetRepo;
import nts.uk.ctx.at.record.dom.adapter.personnelcostsetting.PersonnelCostSettingAdapter;
import nts.uk.ctx.at.record.dom.attendanceitem.StoredProcdureProcess;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.CalcDefaultValue;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.CalculationErrorCheckService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.DailyRecordCreateErrorAlermService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ootsuka.OotsukaProcessService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.PersonnelCostSettingImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.HolidayTimesheetCalculationSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.OvertimeTimesheetCalculationSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.WorkingTimesheetCalculationSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BPUnitUseSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethodOfEachPremiumHalfWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethodOfHalfWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.TimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PreviousAndNextDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.SchedulePerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Formula;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.WorkplaceCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CalculateDailyRecordServiceImpl implements CalculateDailyRecordService {

	/* 勤務種類 */
	@Inject
	private WorkTypeRepository workTypeRepository;
	/* 所定時間帯 */
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSetRepository;
	/* 就業時間帯設定 */
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	/* 固定勤務設定 */
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	/* 流動勤務設定 */
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;
	/* 時差勤務設定 */
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	/* フレックス勤務設定 */
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;

	/* 労働規則 */
	// @Inject
	// private SpecificWorkRuleRepository specificWorkRuleRepository;

	/* 勤怠項目と勤怠項目の実際の値のマッピング */
	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;

	/* 大塚ｶｽﾀﾏｲｽﾞ専用処理 */
	@Inject
	private OotsukaProcessService ootsukaProcessService;

	/* エラーチェック処理 */
	@Inject
	private CalculationErrorCheckService calculationErrorCheckService;

	// @Inject
	// 職場の加給時間設定
	// private WPBonusPaySettingRepository wPBonusPaySettingRepository;

	@Inject
	// 就業時間帯加給時間設定
	private WTBonusPaySettingRepository wTBonusPaySettingRepository;

	/* 加給設定 */
	@Inject
	private BPSettingRepository bPSettingRepository;

	/* 加給時間帯設定 */
	@Inject
	private BPTimesheetRepository bPTimeSheetRepository;

	/* 特定日加給時間帯設定 */
	@Inject
	private SpecBPTimesheetRepository specBPTimesheetRepository;

	// 任意項目の計算の為に追加
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	// 割増計算用に追加
	@Inject
	private PersonnelCostSettingAdapter personnelCostSettingAdapter;

	@Inject
	// 日別作成側にあったエラーチェック処理
	private DailyRecordCreateErrorAlermService dailyRecordCreateErrorAlermService;

	@Inject
	private MidnightTimeSheetRepo midnightTimeSheetRepo;

	@Inject
	private StoredProcdureProcess storedProcdureProcess;
	
	/* 休暇加算設定 */
	@Inject
	private HolidayAddtionRepository hollidayAdditonRepository;//ichiokaメモ 削除予定。ビルドを通す為に残している。

	/**
	 * 勤務情報を取得して計算
	 * 
	 * @param calculateOption
	 * @param integrationOfDaily(日別実績(WORK))
	 * @param companyCommonSetting
	 *            計算をするための会社共通設定
	 * @param personCommonSetting
	 *            計算をするための個人共通設定
	 * @param yesterDayInfo
	 *            前日の勤務情報
	 * @param tomorrowDayInfo
	 *            翌日の勤務情報
	 * @return 日別実績(Work)
	 */
	@Override
	public ManageCalcStateAndResult calculate(CalculateOption calculateOption, IntegrationOfDaily integrationOfDaily,
			ManagePerCompanySet companyCommonSetting, ManagePerPersonDailySet personCommonSetting,
			Optional<WorkInfoOfDailyPerformance> yesterDayInfo, Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {

		DailyRecordToAttendanceItemConverter converter = attendanceItemConvertFactory.createDailyConverter();
		// 計算できる状態にあるかのチェック①(勤務情報、会社共通の設定、個人共通の設定)
		if (integrationOfDaily.getAffiliationInfor() == null || companyCommonSetting == null
				|| personCommonSetting == null) {
			return ManageCalcStateAndResult.failCalc(integrationOfDaily, attendanceItemConvertFactory);
		}

		// 20190607
		// boolean isShareContainerNotInit = companyCommonSetting.getShareContainer() ==
		// null;
		// if (isShareContainerNotInit) {
		// companyCommonSetting.setShareContainer(MasterShareBus.open());
		// }
		// 実績データの計算
		ManageCalcStateAndResult result = this.calcDailyAttendancePerformance(integrationOfDaily, companyCommonSetting,
				personCommonSetting, converter, yesterDayInfo, tomorrowDayInfo);

		if (!calculateOption.isSchedule()) {
			// 大塚モードの処理
			// val afterOOtsukaModeCalc =
			// replaceStampForOOtsuka(result.getIntegrationOfDaily(), companyCommonSetting,
			// personCommonSetting, tomorrowDayInfo, tomorrowDayInfo, converter);

			// 任意項目の計算
			// result.setIntegrationOfDaily(this.calcOptionalItem(afterOOtsukaModeCalc,
			// converter, companyCommonSetting));

			result.setIntegrationOfDaily(
					this.calcOptionalItem(result.getIntegrationOfDaily(), converter, companyCommonSetting,personCommonSetting));
		}

		if (!calculateOption.isMasterTime()) {
			// エラーチェック
			if(integrationOfDaily != null) {
			result.getIntegrationOfDaily().setEmployeeId(integrationOfDaily.getEmployeeId());
			result.getIntegrationOfDaily().setYmd((integrationOfDaily.getYmd()));
			}
			result.setIntegrationOfDaily(calculationErrorCheckService.errorCheck(result.getIntegrationOfDaily(),
					personCommonSetting, companyCommonSetting));
		}
		// 20190607
		// if (isShareContainerNotInit) {
		// companyCommonSetting.getShareContainer().clearAll();
		// }

		return result;
	}

	// 大塚モード(計算項目置き換えと計算)
	// private IntegrationOfDaily replaceStampForOOtsuka(IntegrationOfDaily
	// integrationOfDaily,
	// ManagePerCompanySet companyCommonSetting, ManagePerPersonDailySet
	// personCommonSetting,
	// Optional<WorkInfoOfDailyPerformance> yesterDayInfo,
	// Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo,
	// DailyRecordToAttendanceItemConverter converter) {
	// if (integrationOfDaily.getPcLogOnInfo().isPresent()) {
	// DailyRecordToAttendanceItemConverter calcrecordFromStamp =
	// converter.setData(integrationOfDaily);
	// // 打刻
	// val recordStamp = calcrecordFromStamp.toDomain();
	// // ログオン・オフ
	// val pcStamp = recordStamp.getPcLogOnInfo().get().getLogOnInfo();
	// // 打刻←ログオン・オフ
	// recordStamp.stampReplaceFromPcLogInfo(pcStamp);
	//
	// // 入れ替えた打刻で実際にレコード作成
	// val ootsukaRecord = this.createRecord(recordStamp, TimeSheetAtr.RECORD,
	// companyCommonSetting,
	// personCommonSetting, yesterDayInfo, tomorrowDayInfo);
	// ootsukaRecord.setCompanyCommonSetting(companyCommonSetting,
	// personCommonSetting);
	// // 計算
	// val calcrecordFromPcLogInfo = calcRecord(ootsukaRecord, ootsukaRecord,
	// companyCommonSetting,
	// personCommonSetting, converter);
	// return ootsukaProcessService.integrationConverter(integrationOfDaily,
	// calcrecordFromPcLogInfo);
	// }
	// return integrationOfDaily;
	// }

	private ManageCalcStateAndResult calcDailyAttendancePerformance(IntegrationOfDaily integrationOfDaily,
			ManagePerCompanySet companyCommonSetting, ManagePerPersonDailySet personCommonSetting,
			DailyRecordToAttendanceItemConverter converter, Optional<WorkInfoOfDailyPerformance> yesterDayInfo,
			Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {
		// 出退勤打刻順序不正のチェック
		// ※他の打刻順序不正は計算処理を実施する必要があるため、ここでは弾かない
		// 不正の場合、勤務情報の計算ステータス→未計算にしつつ、エラーチェックは行う必要有）
		val stampIncorrectError = dailyRecordCreateErrorAlermService.stampIncorrect(integrationOfDaily);
		val lackOfstampError = dailyRecordCreateErrorAlermService.lackOfTimeLeavingStamping(integrationOfDaily);
		//if (stampIncorrectError != null || lackOfstampError != null) {
		if (stampIncorrectError != null || (!lackOfstampError.isEmpty() && lackOfstampError.get(0) != null)) {
			return ManageCalcStateAndResult.failCalc(integrationOfDaily, attendanceItemConvertFactory);
		}
		
		Optional<SchedulePerformance> schedule = createSchedule(
				integrationOfDaily, 
				companyCommonSetting,
				personCommonSetting,
				converter,
				yesterDayInfo,
				tomorrowDayInfo);
		
		//日別勤怠（Work）をcloneする
		IntegrationOfDaily clonedIntegrationOfDaily = converter.setData(integrationOfDaily).toDomain();
				
		//勤務種類の取得
		Optional<WorkType> workType = this.getWorkTypeFromShareContainer(
				companyCommonSetting.getShareContainer(),
				AppContexts.user().companyId(),
				clonedIntegrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
		
		//統合就業時間帯の作成
		Optional<IntegrationOfWorkTime> integrationOfWorkTime = this.createIntegrationOfWorkTime(
				companyCommonSetting.getShareContainer(),
				AppContexts.user().companyId(),
				personCommonSetting,
				clonedIntegrationOfDaily.getWorkInformation());
		
		//休憩を固定で入れる（Iワーク用）
		if(integrationOfWorkTime.isPresent()) {
			List<BreakTimeOfDailyPerformance> breakTimeOfDailyList = this.getBreakTimeForIWork(clonedIntegrationOfDaily, integrationOfWorkTime.get(), TimeSheetAtr.RECORD);
			if(!breakTimeOfDailyList.isEmpty()) {
				clonedIntegrationOfDaily.setBreakTime(breakTimeOfDailyList.stream().map(c -> c.getTimeZone()).collect(Collectors.toList()));
			}
		}
		
		Optional<CalculationRangeOfOneDay> record = createRecord(
				clonedIntegrationOfDaily,
				TimeSheetAtr.RECORD,
				companyCommonSetting,
				personCommonSetting,
				yesterDayInfo,
				tomorrowDayInfo,
				workType,
				integrationOfWorkTime,
				schedule);

		if ((!record.isPresent() || !schedule.isPresent())
				|| (!integrationOfWorkTime.isPresent() && !schedule.get().getIntegrationOfWorkTime().isPresent())) {
			return ManageCalcStateAndResult.failCalc(integrationOfDaily, attendanceItemConvertFactory);
		}
		
		ManageReGetClass scheduleManageReGetClass = new ManageReGetClass(
				schedule.get().getCalculationRangeOfOneDay(),
				companyCommonSetting,
				personCommonSetting,
				schedule.get().getWorkType(),
				schedule.get().getIntegrationOfWorkTime(),
				integrationOfDaily);
		
		ManageReGetClass recordManageReGetClass = new ManageReGetClass(
				record.get(),
				companyCommonSetting,
				personCommonSetting,
				workType,
				integrationOfWorkTime,
				integrationOfDaily);

		// 実際の計算処理
		val calcResult = calcRecord(recordManageReGetClass, scheduleManageReGetClass, converter);
		return ManageCalcStateAndResult.successCalc(calcResult);
	}

	/**
	 * @param integrationOfDaily 日別実績(Work)
	 * @param timeSheetAtr 実働or予定時間帯作成から呼び出されたか
	 * @param companyCommonSetting 会社別設定管理
	 * @param personCommonSetting 社員設定管理
	 * @param yesterDayInfo 前日の勤務情報
	 * @param tomorrowDayInfo 翌日の勤務情報
	 * @param workType 当日の勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param schedulePerformance 予定実績
	 * @return 1日の計算範囲
	 */
	private Optional<CalculationRangeOfOneDay> createRecord(
			IntegrationOfDaily integrationOfDaily,
			TimeSheetAtr timeSheetAtr,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personCommonSetting,
			Optional<WorkInfoOfDailyPerformance> yesterDayInfo,
			Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo,
			Optional<WorkType> workType,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			Optional<SchedulePerformance> schedulePerformance) {
	
		// 休暇加算時間設定
		Optional<HolidayAddtionSet> holidayAddtionSetting = companyCommonSetting.getHolidayAdditionPerCompany();
		// 休暇加算設定が取得できなかった時のエラー
		if (!holidayAddtionSetting.isPresent()) {
			throw new BusinessException("Msg_1446");
		}

		/// 連続勤務：ＡＬＬかつＣａｎｔにしないとフレ時間がー所定時間 で算出されてしまう
		if (!workType.isPresent() || shouldTimeALLZero(integrationOfDaily, workType.get())) {
			return Optional.of(CalculationRangeOfOneDay.createEmpty(integrationOfDaily));
		}
		
		Optional<WorkTimeCode> workTimeCode = decisionWorkTimeCode(integrationOfDaily.getWorkInformation(), personCommonSetting, workType);

		/* 就業時間帯勤務区分 */
		// 1日休日の場合、就業時間帯コードはnullであるので、
		// all0を計算させるため(実績が計算できなくても、予定時間を計算する必要がある
		if (!workTimeCode.isPresent())
			return Optional.of(CalculationRangeOfOneDay.createEmpty(integrationOfDaily));
		
		if (!integrationOfWorkTime.isPresent())
			return Optional.empty();
		
		// 就業時間帯の共通設定
		Optional<WorkTimezoneCommonSet> commonSet = Optional.of(integrationOfWorkTime.get().getCommonSetting());
		
		/* 1日の計算範囲クラスを作成 */
		val oneRange = createOneDayRange(integrationOfDaily, commonSet, true, workType.get(),
				companyCommonSetting.getShareContainer(), workTimeCode);
		
		/**
		 * 勤務種類が休日系なら、所定時間の時間を変更する
		 */
		if (workType.get().getDecisionAttendanceHolidayAttr()) {
			oneRange.getPredetermineTimeSetForCalc().endTimeSetStartTime();
		}
		
		MasterShareContainer<String> shareContainer = companyCommonSetting.getShareContainer();

		Optional<WorkInformation> yesterInfo = yesterDayInfo.map(c -> c.getWorkInformation().getRecordInfo());
				
		Optional<WorkInformation> tommorowInfo = tomorrowDayInfo.map(c -> c.getWorkInformation().getRecordInfo());

		Optional<FixedWorkCalcSetting> ootsukaFixedWorkSet = Optional.empty();

		switch(integrationOfWorkTime.get().getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm()) {
			case FLEX:
				if (!integrationOfWorkTime.get().getFlexWorkSetting().isPresent())
					return Optional.empty();
				
				/* フレックス勤務 */ //予定時間帯を求める為に一旦実績に変換する
				if (timeSheetAtr.isSchedule()) {
					integrationOfWorkTime.get().getFlexWorkSetting().get().getOffdayWorkTime().getRestTimezone().restoreFixRestTime(true);
					integrationOfWorkTime.get().getFlexWorkSetting().get().getRestSetting().getCommonRestSetting().changeCalcMethodToRecordUntilLeaveWork();
					integrationOfWorkTime.get().getFlexWorkSetting().get().getRestSetting().getFlowRestSetting().getFlowFixedRestSetting()
							.changeCalcMethodToSchedule();

				}
	
				/* 大塚モード */
				workType = Optional.of(ootsukaProcessService.getOotsukaWorkType(workType.get(), ootsukaFixedWorkSet,
						oneRange.getAttendanceLeavingWork(),
						integrationOfWorkTime.get().getFlexWorkSetting().get().getCommonSetting().getHolidayCalculation()));
				
				/* 前日の勤務情報取得 */
				val yesterDayForFlex = getWorkTypeByWorkInfo(yesterDayInfo, workType.get(), shareContainer);
				/* 翌日の勤務情報取得 */
				val tomorrowForFlex = getWorkTypeByWorkInfo(tomorrowDayInfo, workType.get(), shareContainer);
				// 前日と翌日の勤務
				PreviousAndNextDaily previousAndNextDailyForFlex = new PreviousAndNextDaily(yesterDayForFlex, tomorrowForFlex, yesterInfo, tommorowInfo);
				
				// フレックス勤務の時間帯作成
				oneRange.createTimeSheetAsFlex(
						companyCommonSetting,
						personCommonSetting,
						workType.get(),
						integrationOfWorkTime.get(),
						integrationOfDaily,
						previousAndNextDailyForFlex);
				
				break;
				
			case FIXED:	
				if (!integrationOfWorkTime.get().getFixedWorkSetting().isPresent())
					return Optional.empty();

				// 大塚用勤務種類チェック処理の前後で勤務種類が変更になったか
				final boolean isSpecialHoliday = workType.get().getDailyWork().isOneOrHalfDaySpecHoliday();
				boolean workTypeChangedFlagForOOtsuka = ootsukaProcessService.decisionOotsukaMode(workType.get(),
						ootsukaFixedWorkSet, oneRange.getAttendanceLeavingWork(),
						integrationOfWorkTime.get().getFixedWorkSetting().get().getCommonSetting().getHolidayCalculation());
				/* 大塚モード */
				workType = Optional.of(ootsukaProcessService.getOotsukaWorkType(workType.get(), ootsukaFixedWorkSet,
						oneRange.getAttendanceLeavingWork(),
						integrationOfWorkTime.get().getFixedWorkSetting().get().getCommonSetting().getHolidayCalculation()));

				// 大塚用 勤務種類が変更になった時に
				if (workTypeChangedFlagForOOtsuka && isSpecialHoliday) {
					integrationOfDaily.setCalAttr(
							integrationOfDaily.getCalAttr().reCreate(AutoCalAtrOvertime.APPLYMANUALLYENTER));
				}
				
				ootsukaFixedWorkSet = integrationOfWorkTime.get().getFixedWorkSetting().get().getCalculationSetting();

				/* 前日の勤務情報取得 */
				val yesterDayForFixed = getWorkTypeByWorkInfo(yesterDayInfo, workType.get(), shareContainer);
				/* 翌日の勤務情報取得 */
				val tomorrowForFixed = getWorkTypeByWorkInfo(tomorrowDayInfo, workType.get(), shareContainer);
				// 前日と翌日の勤務
				PreviousAndNextDaily previousAndNextDailyForFix = new PreviousAndNextDaily(yesterDayForFixed, tomorrowForFixed, yesterInfo, tommorowInfo);
				
				if (timeSheetAtr.isSchedule()) {
					integrationOfWorkTime.get().getFixedWorkSetting().get().getFixedWorkRestSetting().changeCalcMethodToSche();
				}
				
				// 固定勤務の時間帯作成
				oneRange.createWithinWorkTimeSheet(
						companyCommonSetting,
						personCommonSetting,
						workType.get(),
						integrationOfWorkTime.get(),
						integrationOfDaily,
						previousAndNextDailyForFix);
				
				// 大塚モードの判定(緊急対応)
				if (ootsukaProcessService.decisionOotsukaMode(workType.get(), ootsukaFixedWorkSet,
						oneRange.getAttendanceLeavingWork(),
						integrationOfWorkTime.get().getFixedWorkSetting().get().getCommonSetting().getHolidayCalculation()))
					oneRange.cleanLateLeaveEarlyTimeForOOtsuka();
				
				break;
				
			case FLOW:
				if (!integrationOfWorkTime.get().getFlowWorkSetting().isPresent())
					return Optional.empty();
				
				/* 前日の勤務情報取得 */
				val yesterDayForFlow = getWorkTypeByWorkInfo(yesterDayInfo, workType.get(), shareContainer);
				/* 翌日の勤務情報取得 */
				val tomorrowForFlow = getWorkTypeByWorkInfo(tomorrowDayInfo, workType.get(), shareContainer);
				// 前日と翌日の勤務
				PreviousAndNextDaily previousAndNextDailyFlow = new PreviousAndNextDaily(yesterDayForFlow, tomorrowForFlow, yesterInfo, tommorowInfo);
				
				// 流動勤務の時間帯作成
				oneRange.createFlowWork(
						companyCommonSetting,
						personCommonSetting,
						workType.get(),
						integrationOfWorkTime.get(),
						integrationOfDaily,
						previousAndNextDailyFlow,
						schedulePerformance);
				
				break;
				
			case TIMEDIFFERENCE:
				/* 時差勤務 */
				return Optional.empty();
				
			default:
				throw new RuntimeException(
						"unknown workTimeMethodSet" + integrationOfWorkTime.get().getWorkTimeSetting().getWorkTimeDivision().getWorkTimeMethodSet());
		}
		return Optional.of(oneRange);
	}
	

	/**
	 * 就業時間帯コードの取得 勤務情報 > 労働条件 > 就業時間帯無と判定
	 * 
	 * @param workInfo
	 * @param personCommonSetting
	 * @param workType
	 * @return
	 */
	private Optional<WorkTimeCode> decisionWorkTimeCode(WorkInfoOfDailyAttendance workInfo,
			ManagePerPersonDailySet personCommonSetting, Optional<WorkType> workType) {
		if(!workType.isPresent() || workType.get().isNoneWorkTimeType())
			return Optional.empty();
		
		if (workInfo == null || workInfo.getRecordInfo() == null
				|| workInfo.getRecordInfo().getWorkTimeCode() == null) {
				return personCommonSetting.getPersonInfo().getWorkCategory().getWeekdayTime().getWorkTimeCode();
		}
		return Optional.of(workInfo.getRecordInfo().getWorkTimeCode());
	}

	private boolean checkAttendanceLeaveState(Optional<TimeLeavingOfDailyAttd> attendanceLeave) {
		if (attendanceLeave.isPresent()) {
			for (TimeLeavingWork leavingWork : attendanceLeave.get().getTimeLeavingWorks()) {
				if (leavingWork.checkLeakageStamp())
					return true;
			}
		}
		return false;
	}

	/**
	 * 作成した時間帯から時間を計算する
	 * @param recordReGetClass 実績
	 * @param scheduleReGetClass 予定
	 * @param converter
	 * @return 日別実績(Work)
	 */
	private IntegrationOfDaily calcRecord(ManageReGetClass recordReGetClass, ManageReGetClass scheduleReGetClass,
			DailyRecordToAttendanceItemConverter converter) {
		String companyId = AppContexts.user().companyId();

		GeneralDate targetDate = recordReGetClass.getIntegrationOfDaily().getYmd();

		// 加給時間計算設定
		BonusPayAutoCalcSet bonusPayAutoCalcSet = new BonusPayAutoCalcSet(new CompanyId(companyId), 1,
				WorkingTimesheetCalculationSetting.CalculateAutomatic,
				OvertimeTimesheetCalculationSetting.CalculateAutomatic,
				HolidayTimesheetCalculationSetting.CalculateAutomatical);

		// 休暇クラス
		VacationClass vacation = CalcDefaultValue.DEFAULT_VACATION;

		Optional<SettingOfFlexWork> flexCalcMethod = Optional.of(new SettingOfFlexWork(new FlexCalcMethodOfHalfWork(
				new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay),
				new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay))));

		// 総拘束時間の計算
		Optional<CalculateOfTotalConstraintTime> optionalCalculateOfTotalConstraintTime = recordReGetClass.getCompanyCommonSetting()
				.getCalculateOfTotalCons();
		if (!optionalCalculateOfTotalConstraintTime.isPresent()) {
			// 総拘束時間が取得できない場合のエラー
			throw new BusinessException("Msg_1447");
		}
		CalculateOfTotalConstraintTime calculateOfTotalConstraintTime = optionalCalculateOfTotalConstraintTime.get();

		// 会社別代休設定取得
		val compensLeaveComSet = recordReGetClass.getCompanyCommonSetting().getCompensatoryLeaveComSet();
		List<CompensatoryOccurrenceSetting> eachCompanyTimeSet = new ArrayList<>();
		if (compensLeaveComSet != null)
			eachCompanyTimeSet = compensLeaveComSet.getCompensatoryOccurrenceSetting();

		// -------------------------計算用一時的クラス作成----------------------------

		val workType = recordReGetClass.getWorkType();
		// if(!workType.isPresent() ||
		// !recordReGetClass.getWorkTimeSetting().isPresent()) return
		// recordReGetClass.getIntegrationOfDaily();
		if (!workType.isPresent())
			return recordReGetClass.getIntegrationOfDaily();

		// 乖離時間(AggregateRoot)取得
		List<DivergenceTimeRoot> divergenceTimeList = recordReGetClass.getCompanyCommonSetting().getDivergenceTime();

		// スケジュール側の補正
		Optional<PredetermineTimeSetForCalc> schePred = Optional.empty();
		if (scheduleReGetClass.getIntegrationOfDaily().getWorkInformation().getScheduleInfo()
				.getWorkTimeCode() == null) {
			if (recordReGetClass.getPersonDailySetting().getPersonInfo()
					.getWorkCategory().getWeekdayTime().getWorkTimeCode().isPresent()) {
				schePred = getPredetermineTimeSetForCalcFromShareContainer(recordReGetClass.getCompanyCommonSetting().getShareContainer(),
						companyId, recordReGetClass.getPersonDailySetting().getPersonInfo().getWorkCategory().getWeekdayTime()
								.getWorkTimeCode().get().toString());
			}
		} else {
			schePred = getPredetermineTimeSetForCalcFromShareContainer(recordReGetClass.getCompanyCommonSetting().getShareContainer(),
					companyId, scheduleReGetClass.getIntegrationOfDaily().getWorkInformation().getScheduleInfo()
							.getWorkTimeCode().v());
		}

		List<PersonnelCostSettingImport> personalSetting = getPersonalSetting(companyId, targetDate,
				recordReGetClass.getCompanyCommonSetting());

		/* 時間の計算 */
		recordReGetClass.setIntegrationOfDaily(AttendanceTimeOfDailyPerformance.calcTimeResult(vacation, workType.get(),
				flexCalcMethod, bonusPayAutoCalcSet, eachCompanyTimeSet, divergenceTimeList,
				calculateOfTotalConstraintTime, scheduleReGetClass, recordReGetClass,
				recordReGetClass.getPersonDailySetting().getPersonInfo(),
				getPredByPersonInfo(recordReGetClass.getPersonDailySetting().getPersonInfo().getWorkCategory().getWeekdayTime().getWorkTimeCode(),
						recordReGetClass.getCompanyCommonSetting().getShareContainer()),
				recordReGetClass.getLeaveLateSet().isPresent() ? recordReGetClass.getLeaveLateSet().get()
						: new DeductLeaveEarly(1, 1),
				scheduleReGetClass.getLeaveLateSet().isPresent() ? scheduleReGetClass.getLeaveLateSet().get()
						: new DeductLeaveEarly(1, 1),
				schePred, converter, recordReGetClass.getCompanyCommonSetting(), personalSetting,
				decisionWorkTimeCode(recordReGetClass.getIntegrationOfDaily().getWorkInformation(), recordReGetClass.getPersonDailySetting(), workType)));

		/* 日別実績への項目移送 */
		return recordReGetClass.getIntegrationOfDaily();
	}

	/**
	 * 割増設定取得
	 * 
	 * @param companyId
	 *            会社ID
	 * @param targetDate
	 *            対象日
	 * @param companyCommonSetting
	 *            会社共通設定
	 * @return 割増設定
	 */
	private List<PersonnelCostSettingImport> getPersonalSetting(String companyId, GeneralDate targetDate,
			ManagePerCompanySet companyCommonSetting) {
		if (!CollectionUtil.isEmpty(companyCommonSetting.getPersonnelCostSettings())) {

			List<PersonnelCostSettingImport> current = companyCommonSetting.getPersonnelCostSettings().stream()
					.filter(pcs -> {
						return pcs.getPeriod().contains(targetDate);
					}).collect(Collectors.toList());

			if (!current.isEmpty()) {
				return current;
			}
		}
		return personnelCostSettingAdapter.findAll(companyId, targetDate);
	}

	/**
	 * １日の範囲クラス作成
	 * 
	 * @param companyId
	 *            会社コード
	 * @param employeeId
	 *            社員ID
	 * @param targetDate
	 *            対象日
	 * @param integrationOfDaily
	 *            日別実績(Work)
	 * @param isOotsukaMode
	 * @param workType
	 * @return 1日の計算範囲
	 */
	private CalculationRangeOfOneDay createOneDayRange(IntegrationOfDaily integrationOfDaily,
			Optional<WorkTimezoneCommonSet> commonSet, boolean isOotsukaMode, WorkType workType,
			MasterShareContainer<String> shareContainer, Optional<WorkTimeCode> workTimeCode) {
		String companyId = AppContexts.user().companyId();
		String employeeId = integrationOfDaily.getEmployeeId();
		GeneralDate targetDate = integrationOfDaily.getYmd();
		/* 所定時間設定取得 */
		Optional<PredetemineTimeSetting> predetermineTimeSet = Optional.empty();
		if (workTimeCode.isPresent()) {
			predetermineTimeSet = getPredetermineTimeSetFromShareContainer(shareContainer, companyId,
					workTimeCode.get().v());
		}
		/*休日の場合など、もともと働く日じゃない場合は所定時間を0にしたい。*/
		if (!predetermineTimeSet.isPresent()) {
			predetermineTimeSet = Optional.of(new PredetemineTimeSetting(companyId, new AttendanceTime(0),
					workTimeCode.get(),
					new PredetermineTime(
							new BreakDownTimeDay(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0)),
							new BreakDownTimeDay(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0))),
					false, new PrescribedTimezoneSetting(new TimeWithDayAttr(0), new TimeWithDayAttr(0),
							Collections.emptyList()),
					new TimeWithDayAttr(0), false));

		}
		/* 1日の計算範囲取得 */
		val calcRangeOfOneDay = new TimeSpanForDailyCalc(predetermineTimeSet.get().getStartDateClock(),
				predetermineTimeSet.get().getStartDateClock()
						.forwardByMinutes(predetermineTimeSet.get().getRangeTimeDay().valueAsMinutes()));

		WorkInfoOfDailyAttendance toDayWorkInfo = integrationOfDaily.getWorkInformation();
		Optional<TimeLeavingOfDailyAttd> timeLeavingOfDailyPerformance = integrationOfDaily.getAttendanceLeave();
		/* 日別実績の出退勤時刻セット */
		timeLeavingOfDailyPerformance = correctStamp(integrationOfDaily.getAttendanceLeave(), employeeId, targetDate);

		/* ジャストタイムの判断するための設定取得 */
		boolean justLate = false;
		boolean justEarlyLeave = false;
		if (commonSet.isPresent()) {
			justLate = commonSet.get().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE)
					.isStampExactlyTimeIsLateEarly();
			justEarlyLeave = commonSet.get().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY)
					.isStampExactlyTimeIsLateEarly();
		}
		// この区分は本来は引数として計算処理の呼出し口から渡されます（2018/6/6現在は引数として渡されていないので一時的に固定値を渡しています）
		JustCorrectionAtr justCorrectionAtr = JustCorrectionAtr.USE;

		return new CalculationRangeOfOneDay(Finally.empty(), Finally.empty(), calcRangeOfOneDay,
				timeLeavingOfDailyPerformance.get().calcJustTime(justLate, justEarlyLeave, justCorrectionAtr), // ジャスト遅刻、早退による時刻補正
				PredetermineTimeSetForCalc.convertMastarToCalc(predetermineTimeSet.get())/* 所定時間帯(計算用) */,
				toDayWorkInfo, Optional.empty());
	}

	
	/**
	 * 予定時間帯の作成
	 * @param integrationOfDaily 日別実績(Work)
	 * @param companyCommonSetting 会社別設定管理
	 * @param personCommonSetting 社員設定管理
	 * @param converter
	 * @param yesterDayInfo 昨日の勤務情報
	 * @param tomorrowDayInfo 明日の勤務情報
	 * @return 予定実績
	 */
	private Optional<SchedulePerformance> createSchedule(IntegrationOfDaily integrationOfDaily,
			ManagePerCompanySet companyCommonSetting, ManagePerPersonDailySet personCommonSetting,
			DailyRecordToAttendanceItemConverter converter, Optional<WorkInfoOfDailyPerformance> yesterDayInfo,
			Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {
		val integrationOfDailyForSchedule = converter.setData(integrationOfDaily).toDomain();
		// 予定時間１ ここで、「勤務予定を取得」～「休憩情報を変更」を行い、日別実績(Work)をReturnとして受け取る
		IntegrationOfDaily afterScheduleIntegration = SchedulePerformance
				.createScheduleTimeSheet(integrationOfDailyForSchedule);
		
		//勤務種類の取得
		Optional<WorkType> workType = this.getWorkTypeFromShareContainer(
				companyCommonSetting.getShareContainer(),
				AppContexts.user().companyId(),
				afterScheduleIntegration.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
		
		//統合就業時間帯の作成
		Optional<IntegrationOfWorkTime> integrationOfWorkTime = this.createIntegrationOfWorkTime(
				companyCommonSetting.getShareContainer(),
				AppContexts.user().companyId(),
				personCommonSetting,
				afterScheduleIntegration.getWorkInformation());
		
		//休憩を固定で入れる（Iワーク用）
		if(integrationOfWorkTime.isPresent()) {
			List<BreakTimeOfDailyPerformance> breakTimeOfDailyList = this.getBreakTimeForIWork(afterScheduleIntegration, integrationOfWorkTime.get(), TimeSheetAtr.SCHEDULE);
			if(!breakTimeOfDailyList.isEmpty()) {
				afterScheduleIntegration.setBreakTime(breakTimeOfDailyList.stream().map(c -> c.getTimeZone()).collect(Collectors.toList()));
			}
		}
		
		// 予定時間2 ここで、「時間帯を作成」を実施 Returnとして１日の計算範囲を受け取る
		val returnResult = this.createRecord(
				afterScheduleIntegration,
				TimeSheetAtr.SCHEDULE,
				companyCommonSetting,
				personCommonSetting,
				yesterDayInfo,
				tomorrowDayInfo,
				workType.isPresent()
					? Optional.of(workType.get().clone())
					: Optional.empty(),
				integrationOfWorkTime,
				Optional.empty());
		
		if(!returnResult.isPresent()) return Optional.empty();
		
		return Optional.of(new SchedulePerformance(returnResult.get(), workType, integrationOfWorkTime));
	}

	/**
	 * 任意項目の計算
	 * @param personCommonSetting 
	 * 
	 * @return
	 */
	private IntegrationOfDaily calcOptionalItem(IntegrationOfDaily integrationOfDaily,
			DailyRecordToAttendanceItemConverter converter, ManagePerCompanySet companyCommonSetting, ManagePerPersonDailySet personCommonSetting) {
		String companyId = AppContexts.user().companyId();
		String employeeId = integrationOfDaily.getEmployeeId();
		GeneralDate targetDate = integrationOfDaily.getYmd();
		// 「所属雇用履歴」を取得する
		Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt = this.shareEmploymentAdapter
				.findEmploymentHistory(companyId, employeeId, targetDate);
		// AggregateRoot「任意項目」取得
		List<OptionalItem> optionalItems = companyCommonSetting.getOptionalItems();
		// 計算式を取得(任意項目NOで後から絞る必要あり)
		List<Formula> formulaList = companyCommonSetting.getFormulaList();
		// 計算式の並び順を取得(任意項目NOで後から絞る必要あり)
		List<FormulaDispOrder> formulaOrderList = companyCommonSetting.getFormulaOrderList();
		// 適用する雇用条件の取得(任意項目全部分)
		List<EmpCondition> empCondition = companyCommonSetting.getEmpCondition();
		// 項目選択による計算時に必要なので取得
		converter.setData(integrationOfDaily);

		Optional<WorkTimeSetting> workTime = Optional.empty();
		Optional<PredetemineTimeSetting> predSet = Optional.empty();
		Optional<WorkType> workType = Optional.empty();
		MasterShareContainer<String> shareContainer = companyCommonSetting.getShareContainer();
		
		
		
		if (integrationOfDaily != null ) {
			Optional<WorkTimeCode> recordWorkTime = decisionWorkTimeCode(integrationOfDaily.getWorkInformation(), personCommonSetting, workType);
			if (recordWorkTime.isPresent()) {
				workTime = getWorkTimeSettingFromShareContainer(shareContainer, companyId,recordWorkTime.get().v());
				predSet = getPredetermineTimeSetFromShareContainer(shareContainer, companyId,recordWorkTime.get().v());
			}

			if (integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode() != null) {
				workType = getWorkTypeFromShareContainer(shareContainer, companyId,
						integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().toString());
			}
		}

		val resultProcedure = storedProcdureProcess.dailyProcessing(integrationOfDaily, workType, workTime, predSet);
		resultProcedure.ifPresent(tt -> {
			integrationOfDaily.getAttendanceTimeOfDailyPerformance().ifPresent(tc -> {
				tc.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
						.getOverTimeWork().ifPresent(ts -> {
							ts.mergeOverTimeList(tt.getOverTimes());
						});
			});
		});

		// 編集状態を取得（日別実績の編集状態が持つ勤怠項目IDのみのList作成）
		List<Integer> attendanceItemIdList = integrationOfDaily.getEditState().stream()
				.map(editState -> editState.getAttendanceItemId()).distinct().collect(Collectors.toList());
		List<ItemValue> itemValueList = new ArrayList<>();
		if (!attendanceItemIdList.isEmpty()) {
			itemValueList = converter.convert(attendanceItemIdList);
		}

		// 任意項目の計算
		integrationOfDaily.setAnyItemValue(Optional.of(
				AnyItemValueOfDaily.caluculationAnyItem(companyId, employeeId, targetDate, optionalItems, formulaList,
						formulaOrderList, empCondition, Optional.of(converter), bsEmploymentHistOpt, resultProcedure).getAnyItem()));

		IntegrationOfDaily calcResultIntegrationOfDaily = integrationOfDaily;

		if (!itemValueList.isEmpty()) {
			converter.setData(integrationOfDaily);
			converter.merge(itemValueList);
			// 手修正された項目の値を計算前に戻す
			calcResultIntegrationOfDaily.setAnyItemValue(converter.anyItems());
		}

		return calcResultIntegrationOfDaily;
	}

	private Optional<PredetermineTimeSetForCalc> getPredByPersonInfo(Optional<WorkTimeCode> workTimeCode,
			MasterShareContainer<String> shareContainer) {
		if (!workTimeCode.isPresent())
			return Optional.empty();
		// val predSetting =
		// predetemineTimeSetRepository.findByWorkTimeCode(AppContexts.user().companyId(),
		// workTimeCode.get().toString());
		val predSetting = getPredetermineTimeSetFromShareContainer(shareContainer, AppContexts.user().companyId(),
				workTimeCode.get().toString());
		if (!predSetting.isPresent())
			return Optional.empty();
		return Optional.of(PredetermineTimeSetForCalc.convertFromAggregatePremiumTime(predSetting.get()));

	}

	private WorkType getWorkTypeByWorkInfo(Optional<WorkInfoOfDailyPerformance> otherDayWorkInfo, WorkType nowWorkType,
			MasterShareContainer<String> shareContainer) {
		if (otherDayWorkInfo.isPresent()) {
			WorkTypeCode workTypeCode;
			try {
				workTypeCode = otherDayWorkInfo.get().getWorkInformation().getRecordInfo().getWorkTypeCode();
			} catch (Exception e) {
				workTypeCode = otherDayWorkInfo.get().getWorkInformation().getScheduleInfo().getWorkTypeCode();
			}
			String typeCode = workTypeCode.v().toString();
			// val findedWorkType =
			// this.workTypeRepository.findByPK(AppContexts.user().companyId(), typeCode);
			val findedWorkType = getWorkTypeFromShareContainer(shareContainer, AppContexts.user().companyId(),
					typeCode);
			val useWorkType = findedWorkType.orElse(nowWorkType);
			return useWorkType;
		} else {
			return nowWorkType;
		}
	}

	private Optional<BonusPaySetting> getBpSetting(Optional<BPUnitUseSetting> bpUnitSetting,
			Optional<WorkplaceCode> workPlaceCode, Optional<WorkTimeCode> workTimeCode,
			Optional<WorkingConditionItem> bpCodeInPersonInfo) {
		if (bpUnitSetting.isPresent()) {
			// 就業時間帯の加給
			if (bpUnitSetting.get().getWorkingTimesheetUseAtr().isUse()) {
				if (workTimeCode.isPresent()) {
					val bpCode = wTBonusPaySettingRepository.getWTBPSetting(AppContexts.user().companyId(),
							new WorkingTimesheetCode(workTimeCode.get().toString()));
					if (bpCode.isPresent()) {

						val bpSetting = bPSettingRepository.getBonusPaySetting(AppContexts.user().companyId(),
								bpCode.get().getBonusPaySettingCode());
						val bpTimeSheet = bPTimeSheetRepository.getListTimesheet(AppContexts.user().companyId(),
								new BonusPaySettingCode(bpCode.get().getBonusPaySettingCode().toString()));
						val specBpTimeSheet = specBPTimesheetRepository.getListTimesheet(AppContexts.user().companyId(),
								new BonusPaySettingCode(bpCode.get().getBonusPaySettingCode().toString()));
						return Optional.of(BonusPaySetting.createFromJavaType(AppContexts.user().companyId(),
								bpSetting.get().getCode().toString(), bpSetting.get().getName().toString(), bpTimeSheet,
								specBpTimeSheet));
					}
				}
				return Optional.empty();
			}
			// 職場の加給
			else if (bpUnitSetting.get().getWorkplaceUseAtr().isUse()) {
				if (workPlaceCode.isPresent()) {
					// val bpCode = wPBonusPaySettingRepository.get
				}
			}
			// 社員の加給
			else if (bpUnitSetting.get().getPersonalUseAtr().isUse()) {
				if (bpCodeInPersonInfo.isPresent() && bpCodeInPersonInfo.get().getTimeApply().isPresent()) {
					val bpSetting = bPSettingRepository.getBonusPaySetting(AppContexts.user().companyId(),
							new BonusPaySettingCode(bpCodeInPersonInfo.get().getTimeApply().get().toString()));
					val bpTimeSheet = bPTimeSheetRepository.getListTimesheet(AppContexts.user().companyId(),
							new BonusPaySettingCode(bpCodeInPersonInfo.get().getTimeApply().get().toString()));
					val specBpTimeSheet = specBPTimesheetRepository.getListTimesheet(AppContexts.user().companyId(),
							new BonusPaySettingCode(bpCodeInPersonInfo.get().getTimeApply().get().toString()));
					return Optional.of(BonusPaySetting.createFromJavaType(AppContexts.user().companyId(),
							bpSetting.get().getCode().toString(), bpSetting.get().getName().toString(), bpTimeSheet,
							specBpTimeSheet));
				}
			}
			// 会社の加給
			else {

			}
		}
		return Optional.empty();
	}

	private Optional<TimeLeavingOfDailyAttd> correctStamp(
			Optional<TimeLeavingOfDailyAttd> timeLeavingOfDailyPerformance, String employeeId,
			GeneralDate targetDate) {

		/*出退勤がない場合は時刻0：00で作っておく*/
		if (!timeLeavingOfDailyPerformance.isPresent()) {
			WorkStamp attendance = new WorkStamp(new TimeWithDayAttr(0), new TimeWithDayAttr(0),
					new WorkLocationCD("01"), TimeChangeMeans.AUTOMATIC_SET,null);
			WorkStamp leaving = new WorkStamp(new TimeWithDayAttr(0), new TimeWithDayAttr(0), 
					new WorkLocationCD("01"), TimeChangeMeans.AUTOMATIC_SET,null);
			TimeActualStamp stamp = new TimeActualStamp(attendance, leaving, 1);
			TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new WorkNo(1), stamp, stamp);
			List<TimeLeavingWork> timeLeavingWorkList = new ArrayList<>();
			timeLeavingWorkList.add(timeLeavingWork);
			timeLeavingOfDailyPerformance = Optional.of(
					new TimeLeavingOfDailyAttd( timeLeavingWorkList,new WorkTimes(1) ));
		}
		return timeLeavingOfDailyPerformance;
	}

	/**
	 * 出勤、振出、休出なのに打刻が漏れているか判定する
	 * 
	 * @return 打刻がもれている
	 */
	private boolean shouldTimeALLZero(IntegrationOfDaily integrationOfDaily, WorkType workType) {
		return (workType.getDailyWork().isWeekDayOrHolidayWork()
				&& !checkAttendanceLeaveState(integrationOfDaily.getAttendanceLeave()));
	}

	/**
	 * 共有コンテナを使った勤務種類の取得
	 * 
	 * @param shareContainer
	 * @param WorkTypeCode
	 * @param companyId
	 * @return
	 */
	private Optional<WorkType> getWorkTypeFromShareContainer(MasterShareContainer<String> shareContainer,
			String companyId, String WorkTypeCode) {
		// val x = shareContainer.getShared("WorkType" + WorkTypeCode);
		val workType = shareContainer.getShared("WorkType" + WorkTypeCode,
				() -> workTypeRepository.findNoAbolishByPK(companyId, WorkTypeCode));
		if (workType.isPresent()) {
			return Optional.of(workType.get().clone());
		}
		return Optional.empty();
	}

	/**
	 * 共有コンテナを使った就業時間帯の取得
	 * 
	 * @param shareContainer
	 * @param companyId
	 * @param workTimeCode
	 * @return
	 */
	private Optional<WorkTimeSetting> getWorkTimeSettingFromShareContainer(MasterShareContainer<String> shareContainer,
			String companyId, String workTimeCode) {
		val workTimeSetting = shareContainer.getShared("WorkTime" + workTimeCode,
				() -> workTimeSettingRepository.findByCode(companyId, workTimeCode));
		if (workTimeSetting.isPresent()) {
			return Optional.of(workTimeSetting.get().clone());
		}
		return Optional.empty();
	}

	/**
	 * 共有コンテナを使った計算用所定時間帯設定の取得
	 * 
	 * @param shareContainer
	 * @param companyId
	 * @param workTimeCode
	 * @return
	 */
	private Optional<PredetermineTimeSetForCalc> getPredetermineTimeSetForCalcFromShareContainer(
			MasterShareContainer<String> shareContainer, String companyId, String workTimeCode) {
		val predSet = getPredetermineTimeSetFromShareContainer(shareContainer, companyId, workTimeCode);
		if (predSet.isPresent()) {
			return Optional.of(PredetermineTimeSetForCalc.convertFromAggregatePremiumTime(predSet.get()));
		}
		return Optional.empty();
	}

	/**
	 * 共有コンテナを使った所定時間帯設定の取得
	 * 
	 * @param shareContainer
	 * @param companyId
	 * @param workTimeCode
	 * @return
	 */
	private Optional<PredetemineTimeSetting> getPredetermineTimeSetFromShareContainer(
			MasterShareContainer<String> shareContainer, String companyId, String workTimeCode) {
		val predSet = shareContainer.getShared("PredetemineSet" + workTimeCode,
				() -> predetemineTimeSetRepository.findByWorkTimeCode(companyId, workTimeCode));
		if (predSet.isPresent()) {
			return Optional.of(predSet.get().clone());
		}
		return Optional.empty();
	}

	/**
	 * 共有コンテナを使った各勤務形態の就業時間帯の取得
	 * （勤務形態を判断して取得する）
	 * 
	 * @return
	 */
	private WorkTimeAggregateRoot getWorkSettingFromShareContainer(
			MasterShareContainer<String> shareContainer,
			String companyId,
			WorkTimeSetting workTimeSetting) {
		switch(workTimeSetting.getWorkTimeDivision().getWorkTimeForm()) {
			case FIXED:				return this.getFixedWorkSettingFromShareContainer(shareContainer, companyId, workTimeSetting.getWorktimeCode().toString()).get();
			case FLEX:				return this.getFlexWorkSetOptFromShareContainer(shareContainer, companyId, workTimeSetting.getWorktimeCode().toString()).get();
			case FLOW:				return this.getFlowWorkSettingFromShareContainer(shareContainer, companyId, workTimeSetting.getWorktimeCode().toString()).get();
			case TIMEDIFFERENCE:	throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			default:				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	/**
	 * 共有コンテナを使ったフレ就業時間帯の取得
	 * 
	 * @return
	 */
	private Optional<FlexWorkSetting> getFlexWorkSetOptFromShareContainer(MasterShareContainer<String> shareContainer,
			String companyId, String workTimeCode) {
		val flexWorkSetOpt = shareContainer.getShared("FLEX_WORK" + companyId + workTimeCode,
				() -> flexWorkSettingRepository.find(companyId, workTimeCode));
		if (flexWorkSetOpt.isPresent()) {
			return Optional.of(flexWorkSetOpt.get().clone());
		}
		return Optional.empty();
	}

	/**
	 * 共有コンテナを使った固定就業時間帯の取得
	 * 
	 * @return
	 */
	private Optional<FixedWorkSetting> getFixedWorkSettingFromShareContainer(
			MasterShareContainer<String> shareContainer, String companyId, String workTimeCode) {
		val fixedWorkSet = shareContainer.getShared("FIXED_WORK" + companyId + workTimeCode,
				() -> fixedWorkSettingRepository.findByKey(companyId, workTimeCode));
		if (fixedWorkSet.isPresent()) {
			return fixedWorkSet.map(f -> f.clone());
		}
		return Optional.empty();
	}
	
	/**
	 * 共有コンテナを使った流動就業時間帯の取得
	 * 
	 * @return
	 */
	private Optional<FlowWorkSetting> getFlowWorkSettingFromShareContainer(
			MasterShareContainer<String> shareContainer, String companyId, String workTimeCode) {
		val flowWorkSet = shareContainer.getShared("FLOW_WORK" + companyId + workTimeCode,
				() -> flowWorkSettingRepository.find(companyId, workTimeCode));
		if (flowWorkSet.isPresent()) {
			return flowWorkSet.map(f -> f.clone());
		}
		return Optional.empty();
	}

	/**
	 * 就業時間帯の共通設定を取得する
	 * 
	 * @return
	 */
	private Optional<WorkTimezoneCommonSet> getWorkTimezoneCommonSetFromShareContainer(WorkTimeCode workTimeCode,
			String companyId, Optional<WorkTimeSetting> workTime, MasterShareContainer<String> shareContainer) {

		if (workTime.get().getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {
			val flexWorkSetOpt = getFlexWorkSetOptFromShareContainer(shareContainer, companyId, workTimeCode.v());
			if (flexWorkSetOpt.isPresent()) {
				return Optional.of(flexWorkSetOpt.get().getCommonSetting().clone());
			}
		} else {

			switch (workTime.get().getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				/* 固定 */
				val fixedWorkSetting = getFixedWorkSettingFromShareContainer(shareContainer, companyId,
						workTimeCode.v());
				if (fixedWorkSetting.isPresent()) {
					return Optional.of(fixedWorkSetting.get().getCommonSetting().clone());
				}
				break;
			case FLOW_WORK:
				/* 流動勤務 */
				val flowWorkSetOpt = getFlowWorkSettingFromShareContainer(shareContainer, companyId,
						workTimeCode.v());
				if (flowWorkSetOpt.isPresent()) {
					return Optional.of(flowWorkSetOpt.get().getCommonSetting().clone());
				}
				break;
			case DIFFTIME_WORK:
				/* 時差勤務 */
				val diffWorkSetOpt = shareContainer.getShared("DIFFTIME_WORK" + companyId + workTimeCode.v(),
						() -> diffTimeWorkSettingRepository.find(companyId, workTimeCode.v()));
				if (diffWorkSetOpt.isPresent()) {
					return Optional.of(diffWorkSetOpt.get().getCommonSet().clone());
				}
				break;
			default:
				throw new RuntimeException(
						"unknown workTimeMethodSet" + workTime.get().getWorkTimeDivision().getWorkTimeMethodSet());
			}
		}
		return Optional.empty();
	}
	
	/**
	 * @param map 各加算設定
	 * @param workingSystem 労働制
	 * @return 加算設定
	 */
	private AddSetting getAddSetting(String companyID, Map<String, AggregateRoot> map, WorkingSystem workingSystem) {
		
		switch(workingSystem) {
		case REGULAR_WORK:
			AggregateRoot workRegularAdditionSet = map.get("regularWork");
			return workRegularAdditionSet != null
					?(WorkRegularAdditionSet) workRegularAdditionSet
					: new WorkRegularAdditionSet(companyID, HolidayCalcMethodSet.emptyHolidayCalcMethodSet());
		
		case FLEX_TIME_WORK:
			AggregateRoot workFlexAdditionSet = map.get("flexWork");
			return workFlexAdditionSet != null
					?(WorkFlexAdditionSet) workFlexAdditionSet
					: new WorkFlexAdditionSet(companyID, HolidayCalcMethodSet.emptyHolidayCalcMethodSet());
			
		case VARIABLE_WORKING_TIME_WORK:
			AggregateRoot workDeformedLaborAdditionSet = map.get("irregularWork");
			return workDeformedLaborAdditionSet != null
					? (WorkDeformedLaborAdditionSet) workDeformedLaborAdditionSet
					: new WorkDeformedLaborAdditionSet(companyID, HolidayCalcMethodSet.emptyHolidayCalcMethodSet());
		
		default:
			throw new RuntimeException("unknown WorkingSystem");
		}
	}
	
	/**
	 * @param shareContainer
	 * @param companyId
	 * @param workTimeSetting
	 * @return
	 */
	private Optional<IntegrationOfWorkTime> createIntegrationOfWorkTimeToForm(
			MasterShareContainer<String> shareContainer,
			String companyId,
			WorkTimeSetting workTimeSetting) {
		switch(workTimeSetting.getWorkTimeDivision().getWorkTimeForm()) {
			case FIXED:	
				Optional<FixedWorkSetting> fixedWorkSetting = this.getFixedWorkSettingFromShareContainer(
						shareContainer,
						companyId,
						workTimeSetting.getWorktimeCode().toString());
				if(!fixedWorkSetting.isPresent())
					return Optional.empty();
				
				//固定勤務で統合就業時間帯を作成
				return Optional.of(new IntegrationOfWorkTime(workTimeSetting.getWorktimeCode(), workTimeSetting, fixedWorkSetting.get()));
				
			case FLEX:
				Optional<FlexWorkSetting> flexWorkSetting = this.getFlexWorkSetOptFromShareContainer(
						shareContainer,
						companyId,
						workTimeSetting.getWorktimeCode().toString());
				if(!flexWorkSetting.isPresent())
					return Optional.empty();
				
				//フレックス勤務で統合就業時間帯を作成
				return Optional.of(new IntegrationOfWorkTime(workTimeSetting.getWorktimeCode(), workTimeSetting, flexWorkSetting.get()));
				
			case FLOW:
				Optional<FlowWorkSetting> flowWorkSetting = this.getFlowWorkSettingFromShareContainer(
						shareContainer,
						companyId,
						workTimeSetting.getWorktimeCode().toString());
				if(!flowWorkSetting.isPresent())
					return Optional.empty();
				
				//流動勤務で統合就業時間帯を作成
				return Optional.of(new IntegrationOfWorkTime(workTimeSetting.getWorktimeCode(), workTimeSetting, flowWorkSetting.get()));
				
			case TIMEDIFFERENCE:
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
				
			default:
				throw new RuntimeException("Non-conformity No Work");
		}
	}
	
	private Optional<WorkType> getWorkType(MasterShareContainer<String> shareContainer, String companyId, WorkInfoOfDailyPerformance workInfo){
		/* 勤務種類の取得 */
		val workTypeCode = workInfo.getWorkInformation().getRecordInfo().getWorkTypeCode().v();
		return this.getWorkTypeFromShareContainer(shareContainer, companyId, workTypeCode);
	}
	
	private Optional<IntegrationOfWorkTime> createIntegrationOfWorkTime(
			MasterShareContainer<String> shareContainer,
			String companyId,
			ManagePerPersonDailySet personCommonSetting,
			WorkInfoOfDailyAttendance workInfo){
		/* 勤務種類の取得 */
		Optional<WorkType> workType = this.getWorkTypeFromShareContainer(shareContainer, companyId, workInfo.getRecordInfo().getWorkTypeCode().v());
		
		Optional<WorkTimeCode> workTimeCode = this.decisionWorkTimeCode(workInfo, personCommonSetting, workType);
		
		/* 就業時間帯勤務区分 */
		// 1日休日の場合、就業時間帯コードはnullであるので、
		// all0を計算させるため(実績が計算できなくても、予定時間を計算する必要がある
		if (!workTimeCode.isPresent())
			return Optional.empty();
		
		Optional<WorkTimeSetting> workTimeSetting = this.getWorkTimeSettingFromShareContainer(shareContainer, companyId, workTimeCode.get().toString());
		if (!workTimeSetting.isPresent())
			return Optional.empty();
		
		return this.createIntegrationOfWorkTimeToForm(shareContainer, companyId, workTimeSetting.get());
	}
	
	private List<BreakTimeOfDailyPerformance> getBreakTimeForIWork(
			IntegrationOfDaily integrationOfDaily,
			IntegrationOfWorkTime integrationOfWorkTime,
			TimeSheetAtr timeSheetAtr){
		if(!integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm().isFixed())
			return Collections.emptyList();
		
		// 休憩時間帯(BreakManagement)
		List<BreakTimeSheet> breakTimeSheet = new ArrayList<>();
		List<BreakTimeOfDailyPerformance> breakTimeOfDailyList = new ArrayList<>();
		
		BreakType nowBreakType = BreakType.REFER_SCHEDULE;
		if (timeSheetAtr.isRecord()) {
			nowBreakType = BreakType.convertFromFixedRestCalculateMethod(
					integrationOfWorkTime.getFixedWorkSetting().get().getFixedWorkRestSetting().getCalculateMethod());
		}
		final BreakType flexBreakType = nowBreakType;
		// 大塚IW限定処理(休憩を固定で入れる案)
		// final WorkType nowWorkType = workType.get();
		if (!integrationOfDaily.getBreakTime().isEmpty()) {

			Optional<BreakTimeOfDailyAttd> breakTimeByBreakType = integrationOfDaily.getBreakTime()
					.stream().filter(breakTime -> breakTime.getBreakType() == flexBreakType).findFirst();
			breakTimeByBreakType.ifPresent(tc -> {
				// 大塚IW限定処理(休憩を固定で入れる案)
				// if(flexBreakType.isReferWorkTime()) {
				// breakTimeSheet.addAll(ootsukaProcessService.convertBreakTimeSheetForOOtsuka(Optional.of(tc),nowWorkType,new
				// WorkTimeCode(workInfo.getRecordInfo().getWorkTimeCode().toString())).get().getBreakTimeSheets());
				// }
				// else {
				breakTimeSheet.addAll(tc.getBreakTimeSheets());
				// }
			});
		}
		// 大塚IW限定処理(休憩を固定で入れる案)
		// if(ootsukaProcessService.isIWWorkTimeAndCode(nowWorkType,
		// workTime.get().getWorktimeCode())) {
		// breakTimeSheet.add(new BreakTimeSheet(new BreakFrameNo(1),new
		// TimeWithDayAttr(720), new TimeWithDayAttr(780)));
		// }
		breakTimeOfDailyList.add(new BreakTimeOfDailyPerformance(
				integrationOfDaily.getEmployeeId(),
				nowBreakType,
				breakTimeSheet,
				integrationOfDaily.getYmd()));
		
		return breakTimeOfDailyList;
	}
}