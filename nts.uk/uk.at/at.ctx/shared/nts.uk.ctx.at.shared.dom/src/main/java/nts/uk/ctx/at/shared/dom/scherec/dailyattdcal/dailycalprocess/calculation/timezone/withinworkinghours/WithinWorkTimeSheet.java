package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AdditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.BonusPayAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.CommonFixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.LateLeaveEarlyManagementTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.VacationAddTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.WorkHour;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.MidNightTimeSheetForCalcList;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.service.ActualWorkTimeSheetListService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
@Getter
public class WithinWorkTimeSheet implements LateLeaveEarlyManagementTimeSheet{

	//就業時間内時間枠
	protected final List<WithinWorkTimeFrame> withinWorkTimeFrame;
	//短時間時間帯
	protected List<TimeSheetOfDeductionItem> shortTimeSheet;
	//早退判断時刻
	protected List<LeaveEarlyDecisionClock> leaveEarlyDecisionClock = new ArrayList<>();
	//遅刻判断時刻
	protected List<LateDecisionClock> lateDecisionClock = new ArrayList<>();
	//外出休暇使用残時間
	//protected Optional<TimevacationUseTimeOfDaily> outingVacationUseTime = Optional.empty();
	
	private Map<GoingOutReason,TimevacationUseTimeOfDaily> outingVacationUseTime = new HashMap<>();
	{
		outingVacationUseTime.put(GoingOutReason.PRIVATE, TimevacationUseTimeOfDaily.defaultValue());
		outingVacationUseTime.put(GoingOutReason.COMPENSATION, TimevacationUseTimeOfDaily.defaultValue());
		outingVacationUseTime.put(GoingOutReason.PUBLIC, TimevacationUseTimeOfDaily.defaultValue());
		outingVacationUseTime.put(GoingOutReason.UNION, TimevacationUseTimeOfDaily.defaultValue());
	}
	
	//休暇使用合計残時間未割当
	private Finally<AttendanceTime> timeVacationAdditionRemainingTime = Finally.of(new AttendanceTime(0));
	
	public WithinWorkTimeSheet(List<WithinWorkTimeFrame> withinWorkTimeFrame,List<TimeSheetOfDeductionItem> shortTimeSheets,Optional<LateDecisionClock> lateDecisionClock,Optional<LeaveEarlyDecisionClock> leaveEarlyDecisionClock) {
		this.withinWorkTimeFrame = withinWorkTimeFrame;
		this.shortTimeSheet = shortTimeSheets;
		if(lateDecisionClock != null
			&& lateDecisionClock.isPresent())
			this.lateDecisionClock.add(lateDecisionClock.get());
		if(leaveEarlyDecisionClock != null
			&& leaveEarlyDecisionClock.isPresent())
			this.leaveEarlyDecisionClock.add(leaveEarlyDecisionClock.get());
	}

	
	/**
	 * 空で作成する
	 * @return 就業時間内時間帯
	 */
	public static WithinWorkTimeSheet createEmpty() {
		return new WithinWorkTimeSheet(
				Arrays.asList(WithinWorkTimeFrame.createEmpty(new EmTimeFrameNo(1))),
				Collections.emptyList(),
				Optional.empty(),
				Optional.empty());
	}

	/**
	 * 就業時間内時間帯
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param deductionTimeSheet 控除時間帯
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param timeLeavingWork 出退勤
	 * @return 就業時間内時間帯
	 */
	public static WithinWorkTimeSheet createAsFixed(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			DeductionTimeSheet deductionTimeSheet,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			TimeLeavingWork timeLeavingWork) {

		/*出勤系ではない場合は時間帯は作成しない*/
		if(!todayWorkType.isWeekDayAttendance())
			return new WithinWorkTimeSheet(new ArrayList<>(),new ArrayList<>(),Optional.empty(),Optional.empty());
		
		// 遅刻判断時刻を求める
		Optional<LateDecisionClock> lateDesClock = LateDecisionClock.create(
				timeLeavingWork.getWorkNo().v(),
				predetermineTimeSetForCalc,
				integrationOfWorkTime,
				integrationOfDaily,
				timeLeavingWork,
				todayWorkType,
				deductionTimeSheet);
		
		// 早退判断時刻を求める
		Optional<LeaveEarlyDecisionClock>leaveEarlyDesClock = LeaveEarlyDecisionClock.create(
				timeLeavingWork.getWorkNo().v(),
				predetermineTimeSetForCalc,
				integrationOfWorkTime,
				integrationOfDaily,
				timeLeavingWork,
				todayWorkType,
				deductionTimeSheet);
		
		//就業時間内枠の作成
		List<WithinWorkTimeFrame> timeFrames = isWeekDayProcess(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				timeLeavingWork,
				deductionTimeSheet,
				predetermineTimeSetForCalc,
				personDailySetting.getPredetermineTimeSetByPersonWeekDay(),
				lateDesClock,
				leaveEarlyDesClock);
		
		//短時間時間帯の取得
		// upd 2021.1.20 shuichi_ishida (ver4対応) 
		//List<TimeSheetOfDeductionItem> shortTimeSheets = toHaveShortTime(timeFrames,deductionTimeSheet.getForDeductionTimeZoneList());
		List<TimeSheetOfDeductionItem> shortTimeSheets = new ArrayList<>();
		
		return new WithinWorkTimeSheet(timeFrames,shortTimeSheets,lateDesClock,leaveEarlyDesClock);
	}

	/**
	 * 就業時間内時間帯の作成
	 * アルゴリズム：固定勤務（平日・就内）
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param timeLeavingWork 出退勤
	 * @param deductionTimeSheet 控除時間帯
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param predetermineTimeSetByPersonWeekDay 計算用所定時間設定（個人平日時）
	 * @param lateDesClock 遅刻判断時刻
	 * @param leaveEarlyDesClock 早退判断時刻
	 * @return 就業時間内時間枠(List)
	 */
	private static List<WithinWorkTimeFrame> isWeekDayProcess(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeLeavingWork timeLeavingWork,
			DeductionTimeSheet deductionTimeSheet,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			PredetermineTimeSetForCalc predetermineTimeSetByPersonWeekDay,
			Optional<LateDecisionClock> lateDesClock,
			Optional<LeaveEarlyDecisionClock>leaveEarlyDesClock) {
		
		//出退勤時刻と就業時間帯の勤務時間帯との重複部分を取得
		CommonFixedWorkTimezoneSet workTimeZone = new CommonFixedWorkTimezoneSet();
		workTimeZone = workTimeZone.forWorkTime(integrationOfWorkTime);
		List<WithinWorkTimeFrame> withinWorkTimeFrame = duplicatedByStamp(timeLeavingWork,workTimeZone,todayWorkType,integrationOfWorkTime,predetermineTimeSetForCalc);
		
		val timeFrames = new ArrayList<WithinWorkTimeFrame>();
		for(WithinWorkTimeFrame duplicateTimeSheet :withinWorkTimeFrame) {
			//就業時間内時間枠の作成
			timeFrames.add(WithinWorkTimeFrame.createWithinWorkTimeFrame(
					companyCommonSetting,
					personDailySetting,
					todayWorkType,
					integrationOfWorkTime,
					integrationOfDaily,
					timeLeavingWork,
					duplicateTimeSheet,
					deductionTimeSheet,
					predetermineTimeSetForCalc,
					lateDesClock,
					leaveEarlyDesClock,
					duplicateTimeSheet.getWorkingHoursTimeNo().v().intValue() == withinWorkTimeFrame.size()));
		}
		return timeFrames;
	}
	
	
	/**
	 * 自身が持つ短時間勤務時間帯(控除)を収集
	 * @return　短時間勤務時間帯
	 */
	public static List<TimeSheetOfDeductionItem> toHaveShortTime(List<WithinWorkTimeFrame> afterWithinPremiumCreate,List<TimeSheetOfDeductionItem> dedTimeSheet) {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		List<TimeSheetOfDeductionItem> allInFrameShortTimeSheets = new ArrayList<>();
		for(WithinWorkTimeFrame frame : afterWithinPremiumCreate) {
			allInFrameShortTimeSheets.addAll(frame.collectShortTimeSheetInFrame());
		}
		//マスタ側の控除時間帯ループ
		val loopList = dedTimeSheet.stream().filter(tc -> tc.getDeductionAtr().isChildCare()).collect(Collectors.toList());
		for(TimeSheetOfDeductionItem masterDedItem : loopList) {
			List<TimeSheetOfDeductionItem> notDupShort = Arrays.asList(masterDedItem);
			for(TimeSheetOfDeductionItem dedItem:allInFrameShortTimeSheets) {
				//ループ中短時間のどこにも所属していない時間帯
				List<TimeSpanForDailyCalc> timeReplace = notDupShort.stream().map(tc -> tc.getTimeSheet().getNotDuplicationWith(dedItem.getTimeSheet())).flatMap(List::stream).collect(Collectors.toList());
				timeReplace = timeReplace.stream().filter(ts -> ts.lengthAsMinutes() > 0).collect(Collectors.toList());
				notDupShort = timeReplace.stream().map(ts -> dedItem.cloneWithNewTimeSpan(Optional.of(ts))).collect(Collectors.toList());
			}
			if(!notDupShort.isEmpty()) {
				returnList.addAll(notDupShort);
			}
		}		
		return returnList;
	}

	/***
	 * 出勤、退勤時刻との重複部分を調べる
	 * @param workingHourSet 就業時間枠の時間帯
	 * @param timeLeavingWork　出退勤
	 * @return　時間枠の時間帯と出退勤の重複時間
	 */
	private static List<WithinWorkTimeFrame> duplicatedByStamp(
			TimeLeavingWork timeLeavingWork,
			CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
		List<WithinWorkTimeFrame> returnList = new ArrayList<>();
		Optional<TimeSpanForCalc> duplicatedRange = Optional.empty(); 
		val attendanceHolidayAttr = workType.getAttendanceHolidayAttr();
		val emTimeZoneSet = getWorkingHourSetByAmPmClass(lstHalfDayWorkTimezone, attendanceHolidayAttr);
		
		for(EmTimeZoneSet timeZone:emTimeZoneSet) {
			duplicatedRange = timeZone.getTimezone().getDuplicatedWith(timeLeavingWork.getTimespan());
			if(duplicatedRange.isPresent()) {
				returnList.add(new WithinWorkTimeFrame(
						timeZone.getEmploymentTimeFrameNo(),
						new TimeSpanForDailyCalc(duplicatedRange.get()),
						WithinWorkTimeFrame.getBeforeLateEarlyTimeSheet(
								new TimeSpanForDailyCalc(duplicatedRange.get()),
								workType,
								integrationOfWorkTime,
								predetermineTimeSetForCalc,
								timeLeavingWork),
						timeZone.getTimezone().getRounding(),
						new ArrayList<>(),
						new ArrayList<>(),
						new ArrayList<>(),
						MidNightTimeSheetForCalcList.createEmpty(),
						new ArrayList<>(),
						Optional.empty(),
						Optional.empty()));
			}else {
				returnList.add(new WithinWorkTimeFrame(timeZone.getEmploymentTimeFrameNo(),
						new TimeSpanForDailyCalc(timeZone.getTimezone().getStart(),timeZone.getTimezone().getStart()),
						new TimeSpanForDailyCalc(timeZone.getTimezone().getStart(),timeZone.getTimezone().getStart()),
						timeZone.getTimezone().getRounding(),
						new ArrayList<>(),
						new ArrayList<>(),
						new ArrayList<>(),
						MidNightTimeSheetForCalcList.createEmpty(),
						new ArrayList<>(),
						Optional.empty(),
						Optional.empty()));
			}
		}
		return returnList;
	}

	/**
	 * 平日出勤の出勤時間帯を取得
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @return 出勤時間帯
	 */
	private static List<EmTimeZoneSet> getWorkingHourSetByAmPmClass(
			CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,
			AttendanceHolidayAttr attendanceHolidayAttr) {
		
		switch (attendanceHolidayAttr) {
		case FULL_TIME:
		case HOLIDAY:
			return lstHalfDayWorkTimezone.getFixedWorkTimezoneMap().get(AmPmAtr.ONE_DAY).getLstWorkingTimezone();
		case MORNING:
			return lstHalfDayWorkTimezone.getFixedWorkTimezoneMap().get(AmPmAtr.AM).getLstWorkingTimezone();
		case AFTERNOON:
			return lstHalfDayWorkTimezone.getFixedWorkTimezoneMap().get(AmPmAtr.PM).getLstWorkingTimezone();
		default:
			throw new RuntimeException("unknown attendanceHolidayAttr" + attendanceHolidayAttr);
		}
	}
	
	/**
	 * 遅刻判断時刻を取得する
	 * @param workNo
	 * @return 遅刻判断時刻
	 */
	public Optional<LateDecisionClock> getLateDecisionClock(int workNo) {
		return this.lateDecisionClock.stream().filter(tc -> tc.getWorkNo() == workNo).findFirst();
	}
	
	/**
	 * 早退判断時刻を取得する
	 * @param workNo
	 * @return 早退判断時刻
	 */
	public Optional<LeaveEarlyDecisionClock> getLeaveEarlyDecisionClock(int workNo) {
		return this.leaveEarlyDecisionClock.stream().filter(tc -> tc.getWorkNo() == workNo).findFirst();
	}
	
	/**
	 * コアタイムのセット
	 * @param coreTimeSetting コアタイム時間設定
	 */
	public WithinWorkTimeSheet createWithinFlexTimeSheet(CoreTimeSetting coreTimeSetting,DeductionTimeSheet dedTimeSheet) {
		List<TimeSpanForDailyCalc> duplicateCoreTimeList = new ArrayList<>();
		for(WithinWorkTimeFrame workTimeFrame : this.withinWorkTimeFrame) {
			Optional<TimeSpanForDailyCalc> duplicateSpan = workTimeFrame.getTimeSheet().getDuplicatedWith(new TimeSpanForDailyCalc(coreTimeSetting.getCoreTimeSheet().getStartTime(),
																									 					 coreTimeSetting.getCoreTimeSheet().getEndTime())); 
			if(duplicateSpan.isPresent()) {
				duplicateCoreTimeList.add(duplicateSpan.get());
			}
		}
		TimeWithDayAttr startTime = new TimeWithDayAttr(0);
		TimeWithDayAttr endTime = new TimeWithDayAttr(0);
		if(!duplicateCoreTimeList.isEmpty()) {
			startTime = duplicateCoreTimeList.stream().sorted((first,second)-> first.getStart().compareTo(second.getStart())).collect(Collectors.toList()).get(0).getStart();
			endTime = duplicateCoreTimeList.stream().sorted((first,second)-> first.getStart().compareTo(second.getStart())).collect(Collectors.toList()).get(duplicateCoreTimeList.size() - 1).getEnd();
			/*フレックス時間帯に入れる*/
			return new FlexWithinWorkTimeSheet(
					this.withinWorkTimeFrame,
					toHaveShortTime(this.withinWorkTimeFrame,dedTimeSheet.getForDeductionTimeZoneList()),
					this.leaveEarlyDecisionClock,
					this.lateDecisionClock,
					this.outingVacationUseTime,
					this.timeVacationAdditionRemainingTime,
					Optional.of(new TimeSpanForDailyCalc(startTime, endTime)));
		}
		else {
			return new FlexWithinWorkTimeSheet(
					this.withinWorkTimeFrame,
					toHaveShortTime(this.withinWorkTimeFrame,dedTimeSheet.getForDeductionTimeZoneList()),
					this.leaveEarlyDecisionClock,
					this.lateDecisionClock,
					this.outingVacationUseTime,
					this.timeVacationAdditionRemainingTime,
					Optional.empty());
		}
	}
	
	/**
	 * 就業時間の計算(控除時間差し引いた後)
	 * アルゴリズム：ループ処理
	 * @param premiumAtr 割増区分
	 * @param vacationClass 休暇クラス
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param siftCode 就業時間帯コード
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間設定（個人）
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @param holidayAddition 休暇加算区分
	 * @param lateEarlyMinusAtr 遅刻早退控除する
	 * @param attendanceLeavingWork 日別勤怠の出退勤
	 * @return 就業時間
	 */
	public WorkHour calcWorkTime(
			PremiumAtr premiumAtr,
			VacationClass vacationClass,
			AttendanceTime timevacationUseTimeOfDaily,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			Optional<WorkTimeCode> siftCode,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<CoreTimeSetting> coreTimeSetting,
			HolidayAdditionAtr holidayAddition,
			NotUseAtr lateEarlyMinusAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
		//就業時間の計算
		AttendanceTime workTime = calcWorkTimeBeforeDeductPremium(
				holidayAddition,
				timevacationUseTimeOfDaily,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				holidayCalcMethodSet,
				premiumAtr,
				commonSetting,
				lateEarlyMinusAtr,
				attendanceLeavingWork);
		
		int holidayAddTime = 0;
		if(holidayAddition.isHolidayAddition()) {
			//休暇加算処理
			//休暇加算時間を計算
			VacationAddTime vacationAddTime = vacationClass.calcVacationAddTime(
					premiumAtr,
					workType,
					siftCode,
					conditionItem,
					Optional.of(holidayAddtionSet),
					holidayCalcMethodSet,
					predetermineTimeSet == null ? Optional.empty():Optional.of(predetermineTimeSet),
					predetermineTimeSetByPersonInfo);
			
			holidayAddTime = vacationAddTime.calcTotaladdVacationAddTime();
			//休暇加算時間を加算
			workTime = workTime.addMinutes(holidayAddTime);
		}
		
		//所定内割増時間の計算
		AttendanceTime withinpremiumTime = calcPredeterminePremiumTime(workType, dailyUnit, predetermineTimeSet, workTime);
		//所定内割増時間を減算
		workTime = workTime.minusMinutes(withinpremiumTime.valueAsMinutes());
		
		//コア無しフレックスで遅刻した場合の時間補正
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&coreTimeSetting.isPresent()&&!coreTimeSetting.get().isUseTimeSheet()) {
			//遅刻時間を就業時間から控除しない場合
			if(!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
				TimeWithCalculation calcedLateTime = calcNoCoreCalcLateTimeForWorkTime(
						workTime,
						DeductionAtr.Appropriate,
						autoCalcOfLeaveEarlySetting.isLate(),
						holidayCalcMethodSet,
						coreTimeSetting,
						commonSetting);
				
				//コア無しフレックス遅刻時間　＞　0 の場合
				if(calcedLateTime.getCalcTime().greaterThan(0)) {
					workTime = coreTimeSetting.get().getMinWorkTime();
				}
			}
		}
		return new WorkHour(workTime, new AttendanceTime(holidayAddTime), withinpremiumTime);
	}
	
	/**
	 * 就業時間内時間枠の全枠分の就業時間を算出する
	 * (所定内割増時間を差し引く前)
	 * アルゴリズム：就業時間の計算
	 * @param holidayAdditionAtr 休暇加算区分
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param premiumAtr 割増区分
	 * @param commonSetting 就業時間帯の共通設定
	 * @param lateEarlyMinusAtr 遅刻早退控除する
	 * @param attendanceLeavingWork 日別勤怠の出退勤
	 * @return 就業時間内時間枠の全枠分の就業時間
	 */
	public AttendanceTime calcWorkTimeBeforeDeductPremium(
			HolidayAdditionAtr holidayAdditionAtr,
			AttendanceTime timevacationUseTimeOfDaily,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			PremiumAtr premiumAtr,
			Optional<WorkTimezoneCommonSet> commonSetting,
			NotUseAtr lateEarlyMinusAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
//		boolean decisionDeductChild = decisionDeductChild(premiumAtr,holidayCalcMethodSet,commonSetting);
		AttendanceTime workTime = new AttendanceTime(0);
		for(WithinWorkTimeFrame copyItem: withinWorkTimeFrame) {
			//就業時間の計算
			workTime = new AttendanceTime(workTime.v()+copyItem.calcActualWorkTimeAndWorkTime(holidayAdditionAtr,
																							timevacationUseTimeOfDaily,
																							autoCalcOfLeaveEarlySetting,
																							addSetting,
																							holidayAddtionSet,
																							holidayCalcMethodSet,
																							premiumAtr,commonSetting,
																							lateEarlyMinusAtr,
																							attendanceLeavingWork.flatMap(a -> a.getAttendanceLeavingWork(copyItem.getWorkingHoursTimeNo().v())),
																							this.getLateDecisionClock(copyItem.getWorkingHoursTimeNo().v()),
																							this.getLeaveEarlyDecisionClock(copyItem.getWorkingHoursTimeNo().v())).v());
		}
		return workTime;
	}
	
	/**
	 * コアタイム無しの遅刻時間計算
	 * @param workTime 就業時間帯
	 * @param deductionAtr 控除区分
	 * @param late 日別実績の計算区分.遅刻早退の自動計算設定.遅刻
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return コアタイム無しの遅刻時間
	 */
	public TimeWithCalculation calcNoCoreCalcLateTimeForWorkTime(
			AttendanceTime workTime,
			DeductionAtr deductionAtr,
			boolean late,//日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<CoreTimeSetting> coreTimeSetting,
			Optional<WorkTimezoneCommonSet> commonSetting){
		
		//遅刻時間の計算
		AttendanceTime lateTime = calcLateTimeForWorkTime(
				workTime,
				deductionAtr,
				holidayCalcMethodSet,
				coreTimeSetting,
				commonSetting);
		
		//時間休暇との相殺処理(いずれ実装が必要)
		
		//遅刻早退の自動計算設定．遅刻をチェック
		if(late) {
			return TimeWithCalculation.sameTime(lateTime);
		}
		return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0), lateTime);
	}
	
	/**
	 * 遅刻時間の計算
	 * フレックスのコア無しの場合専用の遅刻時間の計算処理(就業時間計算用)
	 * @param workTime 勤務種類
	 * @param deductionAtr 控除区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 遅刻時間
	 */
	public AttendanceTime calcLateTimeForWorkTime(
			AttendanceTime workTime,
			DeductionAtr deductionAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<CoreTimeSetting> coreTimeSetting,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		
		//パラメータ「控除区分」＝”控除”　かつ　控除しない
		if(deductionAtr.isDeduction()&&holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
			return new AttendanceTime(0);
		}
		//遅刻時間の計算   (最低勤務時間　－　パラメータで受け取った就業時間)
		AttendanceTime result = coreTimeSetting.get().getMinWorkTime().minusMinutes(workTime.valueAsMinutes());
		//計算結果がマイナスの場合は0
		if(result.valueAsMinutes()<0) {
			return new AttendanceTime(0);
		}
		return result;
	}
	
	/**コア無しフレックス遅刻時間の計算
	 * 
	 * @return
	 */
	public TimeWithCalculation calcNoCoreCalcLateTime(DeductionAtr deductionAtr,
													  
													   PremiumAtr premiumAtr, 
													   CalcurationByActualTimeAtr calcActualTime,
													   VacationClass vacationClass,
													   AttendanceTime timevacationUseTimeOfDaily,
													   StatutoryDivision statutoryDivision,
													   WorkType workType,
													   PredetermineTimeSetForCalc predetermineTimeSet,
													   Optional<WorkTimeCode> siftCode,
													   AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
													   AddSetting addSetting,
													   HolidayAddtionSet holidayAddtionSet,
													   HolidayCalcMethodSet holidayCalcMethodSet,
													   Optional<CoreTimeSetting> coreTimeSetting,
													   DailyUnit dailyUnit,
													   Optional<WorkTimezoneCommonSet> commonSetting,
													   WorkingConditionItem conditionItem,
													   Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
		   												List<LateTimeOfDaily> lateTimes, 
		   												List<LeaveEarlyTimeOfDaily> leaveEarlyTime,
		   												Optional<DeductLeaveEarly> deductLeaveEarly,
		   												NotUseAtr lateEarlyMinusAtr,
		   												Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork
													  ){
		
		//遅刻時間の計算
		AttendanceTime lateTime = calcLateTime(deductionAtr,
											   premiumAtr,
											   calcActualTime,
											   vacationClass,
											   timevacationUseTimeOfDaily,
											   statutoryDivision,
											   workType,
											   predetermineTimeSet,
											   siftCode,
											   autoCalcOfLeaveEarlySetting,
											   addSetting,
											   holidayAddtionSet,
											   holidayCalcMethodSet,
											   coreTimeSetting,
											   dailyUnit,
											   commonSetting,
											   conditionItem,
											   predetermineTimeSetByPersonInfo,
											   lateTimes,
											   leaveEarlyTime,
											   lateEarlyMinusAtr,
											   attendanceLeavingWork
											   );
		//時間休暇との相殺処理(いずれ実装が必要)
		//丸め
//		if(commonSetting.isPresent()) {
//			val setting = commonSetting.get().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getRoundingSetByDedAtr(deductionAtr.isDeduction());
//			lateTime = new AttendanceTime(setting.round(lateTime.valueAsMinutes()));
//		}
//		
		//遅刻早退の自動計算設定．遅刻をチェック
		if(autoCalcOfLeaveEarlySetting.isLate()) {
			return TimeWithCalculation.sameTime(lateTime);
		}
		return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0), lateTime);
	}
	
	/**
	 * 遅刻時間の計算
	 * フレックスのコア無しの場合専用の遅刻時間の計算処理
	 * @param workTime
	 * @param deductionAtr
	 * @param coreTimeSetting
	 * @param holidayCalcMethodSet
	 * @param commonSetting
	 * @return
	 */
	public AttendanceTime calcLateTime(DeductionAtr deductionAtr,
			   PremiumAtr premiumAtr, 
			   CalcurationByActualTimeAtr calcActualTime,
			   VacationClass vacationClass,
			   AttendanceTime timevacationUseTimeOfDaily,
			   StatutoryDivision statutoryDivision,
			   WorkType workType,
			   PredetermineTimeSetForCalc predetermineTimeSet,
			   Optional<WorkTimeCode> siftCode,
			   AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			   AddSetting addSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   Optional<CoreTimeSetting> coreTimeSetting,
			   DailyUnit dailyUnit,
			   Optional<WorkTimezoneCommonSet> commonSetting,
			   WorkingConditionItem conditionItem,
			   Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			   List<LateTimeOfDaily> lateTime, 
			   List<LeaveEarlyTimeOfDaily> leaveEarlyTime,
			   NotUseAtr lateEarlyMinusAtr,
			   Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork
			) {
		//パラメータ「控除区分」＝”控除”　かつ　控除しない
		if(deductionAtr.isDeduction()&&holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
			return new AttendanceTime(0);
		}
		
		DeductLeaveEarly leaveLateset = new DeductLeaveEarly(1,1);
		if(addSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()) {
			leaveLateset = addSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly();
		}
		//コア無し計算遅刻時間
		AttendanceTime noCore = clacNoCoreWorkTime(premiumAtr,
				   								   calcActualTime,
				   								   vacationClass,
				   								   timevacationUseTimeOfDaily,
				   								   statutoryDivision,
				   								   workType,
				   								   predetermineTimeSet,
				   								   siftCode,
				   								   autoCalcOfLeaveEarlySetting,
				   								   addSetting,
				   								   holidayAddtionSet,
				   								   holidayCalcMethodSet,
				   								   coreTimeSetting,
				   								   dailyUnit,
				   								   commonSetting,
				   								   conditionItem,
				   								   predetermineTimeSetByPersonInfo,
												   lateTime, 
												   leaveEarlyTime,
												   leaveLateset,
												   lateEarlyMinusAtr,
												   attendanceLeavingWork);
		//遅刻時間の計算   (最低勤務時間　－　パラメータで受け取った就業時間)
		AttendanceTime result = coreTimeSetting.get().getMinWorkTime().minusMinutes(noCore.valueAsMinutes());
		//計算結果がマイナスの場合は0
		if(result.valueAsMinutes()<0) {
			return new AttendanceTime(0);
		}
		return result;
	}
	
	/**
	 * コアタイム無し遅刻時間計算用の就業時間の計算
	 * 遅刻時間の計算時にのみ利用する（就業時間計算時には利用しない）
	 * @return
	 */
	public AttendanceTime clacNoCoreWorkTime(PremiumAtr premiumAtr, 
			   								 CalcurationByActualTimeAtr calcActualTime,
			   								 VacationClass vacationClass,
			   								AttendanceTime timevacationUseTimeOfDaily,
			   								 StatutoryDivision statutoryDivision,
			   								 WorkType workType,
			   								 PredetermineTimeSetForCalc predetermineTimeSet,
			   								 Optional<WorkTimeCode> siftCode,
			   								 AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			   								 AddSetting addSetting,
			   								 HolidayAddtionSet holidayAddtionSet,
			   								 HolidayCalcMethodSet holidayCalcMethodSet,
			   								 Optional<CoreTimeSetting> coreTimeSetting,
			   								 DailyUnit dailyUnit,
			   								 Optional<WorkTimezoneCommonSet> commonSetting,
			   								 WorkingConditionItem conditionItem,
			   								 Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
											 List<LateTimeOfDaily> lateTime, 
											 List<LeaveEarlyTimeOfDaily> leaveEarlyTime,
											 DeductLeaveEarly deductLeaveEarly,
											 NotUseAtr lateEarlyMinusAtr,
											 Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork
			   								 ) {
		
		//遅刻、早退の控除設定を「控除する」に変更する
		HolidayCalcMethodSet changeHolidayCalcMethodSet = new HolidayCalcMethodSet(holidayCalcMethodSet.getPremiumCalcMethodOfHoliday(),holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().changeDeduct());
		//就業時間帯の遅刻早退を控除するかを見る場合、就業時間帯の遅刻、早退の控除設定を「控除する」に変更する
		Optional<WorkTimezoneCommonSet> changeCommonSetting = commonSetting;
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&
		   holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly().isEnableSetPerWorkHour()&&
		   commonSetting.isPresent()) {
			 changeCommonSetting = Optional.of(commonSetting.get().changeWorkTimezoneLateEarlySet());
		}
		
		//就業時間（法定内用）の計算
		AttendanceTime result = calcWorkTime(premiumAtr,
											 vacationClass,
											 timevacationUseTimeOfDaily,
											 workType,
											 predetermineTimeSet,
											 siftCode,
											 autoCalcOfLeaveEarlySetting,
											 addSetting,
											 holidayAddtionSet,
											 changeHolidayCalcMethodSet,
											 dailyUnit,
											 changeCommonSetting,
											 conditionItem,
											 predetermineTimeSetByPersonInfo,
											 coreTimeSetting
											 ,HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(calcActualTime),
											 lateEarlyMinusAtr,
											 attendanceLeavingWork
				).getWorkTime();
		
		return result;
	}
	
	/**
	 * 
	 * @param lateRemainingTime 遅刻残数
	 * @param timeVacationUseTime　時間休暇使用時間
	 * @return
	 */
	public AttendanceTime calcOffSetTime(int lateRemainingTime,AttendanceTime timeVacationUseTime) {
		int offSetTime;
		//相殺する時間を計算（比較）する
		if(timeVacationUseTime.lessThanOrEqualTo(lateRemainingTime)) {
			offSetTime = timeVacationUseTime.valueAsMinutes();
		}else {
			offSetTime = lateRemainingTime;
		}
		return new AttendanceTime(offSetTime);
	}
		
	/**
	 * 就業時間内時間帯に入っている加給時間の計算
	 * アルゴリズム：加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param bonusPayAtr 加給区分
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 加給時間(List)
	 */
	public List<BonusPayTime> calcBonusPayTimeInWithinWorkTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			BonusPayAtr bonusPayAtr,
			CalAttrOfDailyAttd calcAtrOfDaily) {
		
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcBonusPay(
					ActualWorkTimeSheetAtr.WithinWorkTime,
					raisingAutoCalcSet,
					bonusPayAutoCalcSet,
					calcAtrOfDaily,
					bonusPayAtr));
		}
		//同じNo同士はここで加算し、Listのサイズを減らす
		return sumBonusPayTime(bonusPayList);
	}

	/**
	 * 就業時間内時間帯に入っている特定加給時間の計算
	 * アルゴリズム：加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param bonusPayAtr 加給区分
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @return 特定加給時間(List)
	 */
	public List<BonusPayTime> calcSpecifiedBonusPayTimeInWithinWorkTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			BonusPayAtr bonusPayAtr,
			CalAttrOfDailyAttd calcAtrOfDaily) {
		
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcSpacifiedBonusPay(
					ActualWorkTimeSheetAtr.WithinWorkTime,
					raisingAutoCalcSet,
					bonusPayAutoCalcSet,
					calcAtrOfDaily,
					bonusPayAtr));
		}
		//同じNo同士はここで加算し、Listのサイズを減らす
		return sumBonusPayTime(bonusPayList);
	}
	
	/**
	 * 同じ加給時間Ｎｏを持つものを１つにまとめる
	 * @param bonusPayTime　加給時間
	 * @return　Noでユニークにした加給時間List
	 */
	private List<BonusPayTime> sumBonusPayTime(List<BonusPayTime> bonusPayTime){
		List<BonusPayTime> returnList = new ArrayList<>();
		List<BonusPayTime> refineList = new ArrayList<>();
		for(int bonusPayNo = 1 ; bonusPayNo <= 10 ; bonusPayNo++) {
			refineList = getByBonusPayNo(bonusPayTime, bonusPayNo);
			if(refineList.size()>0) {
				returnList.add(new BonusPayTime(bonusPayNo,
												new AttendanceTime(refineList.stream().map(tc -> tc.getBonusPayTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
												TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(refineList.stream().map(tc -> tc.getWithinBonusPay().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
																							  new AttendanceTime(refineList.stream().map(tc -> tc.getWithinBonusPay().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)))),
												TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(refineList.stream().map(tc -> tc.getExcessBonusPayTime().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))),
																							  new AttendanceTime(refineList.stream().map(tc -> tc.getExcessBonusPayTime().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc))))
												));
			}
		}
		return returnList;
	}
	
	/**
	 * 受け取った加給時間Ｎｏを持つ加給時間を取得
	 * @param bonusPayTime 加給時間
	 * @param bonusPayNo　加給時間Ｎｏ
	 * @return　加給時間リスト
	 */
	private List<BonusPayTime> getByBonusPayNo(List<BonusPayTime> bonusPayTime,int bonusPayNo){
		return bonusPayTime.stream().filter(tc -> tc.getBonusPayTimeItemNo() == bonusPayNo).collect(Collectors.toList());
	}
	
	/**
	 * 法定内深夜時間の計算
	 * @return　法定内深夜時間
	 */
	public AttendanceTime calcMidNightTime() {
		int totalMidNightTime = 0;
		totalMidNightTime = withinWorkTimeFrame.stream()
											   .map(ts -> ts.getMidNightTimeSheet().calcTotalTime().v())
											   .collect(Collectors.summingInt(tc -> tc));
		
		return new AttendanceTime(totalMidNightTime);
	}
	
	/**
	 * 控除時間を取得
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param roundAtr 丸め区分
	 * @return 控除時間
	 */
	public AttendanceTime getDeductionTime(
			ConditionAtr conditionAtr, DeductionAtr dedAtr, TimeSheetRoundingAtr roundAtr) {
		
		return ActualWorkTimeSheetListService.calcDeductionTime(conditionAtr, dedAtr, roundAtr,
				this.withinWorkTimeFrame.stream().map(tc -> (ActualWorkingTimeSheet)tc).collect(Collectors.toList()));
	}
	
	/**
	 * 控除回数の計算
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @return 控除回数
	 */
	public int calcDeductionCount(ConditionAtr conditionAtr, DeductionAtr dedAtr) {
		
		return ActualWorkTimeSheetListService.calcDeductionCount(conditionAtr, dedAtr,
				this.withinWorkTimeFrame.stream().map(tc -> (ActualWorkingTimeSheet)tc).collect(Collectors.toList()));
	}
	
	/**
	 * 休憩未取得用の処理(大塚要件)
	 * 就業時間内時間帯に含まれている休憩時間を計算する
	 * @param restTimeSheet　就業時間帯マスタの休憩時間帯
	 * @return　取得した休憩時間
	 */
	public AttendanceTime getDupRestTime(DeductionTime restTimeSheet) {
		List<TimeSpanForDailyCalc> notDupSpanList = Arrays.asList(new TimeSpanForDailyCalc(restTimeSheet.getStart(), restTimeSheet.getEnd()));
		for(WithinWorkTimeFrame frame : this.getWithinWorkTimeFrame()) {
			notDupSpanList = notDupSpanList.stream()
										   .filter(tc -> tc.getDuplicatedWith(frame.getTimeSheet()).isPresent())
										   .map(tc -> tc.getDuplicatedWith(frame.getTimeSheet()).get())
										   .collect(Collectors.toList());
		}
		return new AttendanceTime(notDupSpanList.stream().map(ts -> ts.lengthAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
	}
	/**
	 * 大塚モード使用時専用の遅刻、早退削除処理
	 */
	public void cleanLateLeaveEarlyTimeForOOtsuka() {
		this.withinWorkTimeFrame.forEach(tc -> tc.cleanLateLeaveEarlyTimeForOOtsuka());
		cleanLateTime();
		cleanLeaveEarly();
	}
	
	private void cleanLateTime() {
		this.lateDecisionClock = Collections.emptyList();
	}
	
	private void cleanLeaveEarly() {
		this.leaveEarlyDecisionClock = Collections.emptyList();
	}

	/**
	 * 大塚所定内割増→残業への変換後に所定内割増リセット
	 */
	public void resetPremiumTimeSheet() {
		this.withinWorkTimeFrame.forEach(tc -> {
			tc.setPremiumTimeSheetInPredetermined(Optional.empty());
		});
	}
	
	 /**
	 * 遅刻控除
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param deductionTimeSheet 控除時間帯
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @return 出退勤
	 */
	public TimeLeavingWork calcLateTimeDeduction(
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			DeductionTimeSheet deductionTimeSheet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet){

		int workNo = timeLeavingWork.getWorkNo().v();
		
		//遅刻判断時刻を取得
		Optional<LateDecisionClock> lateDecisionClock = LateDecisionClock.create(
				workNo,
				predetermineTimeSet,
				integrationOfWorkTime,
				integrationOfDaily,
				timeLeavingWork,
				todayWorkType,
				deductionTimeSheet);
		
		if(lateDecisionClock.isPresent())
			this.lateDecisionClock.add(lateDecisionClock.get());
		
		val within = getWithinWorkTimeFrame(workNo);
		
		if(within == null) return timeLeavingWork; 
		
		//遅刻時間帯の作成
		within.createLateTimeSheet(
				lateDecisionClock,
				timeLeavingWork,
				integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE),
				within,
				deductionTimeSheet,
				predetermineTimeSet.getTimeSheets(todayWorkType.getDailyWork().decisionNeedPredTime(), workNo),
				workNo,
				todayWorkType,
				predetermineTimeSet,
				integrationOfWorkTime,
				integrationOfDaily);
		
		if(!within.getLateTimeSheet().isPresent() || !within.getLateTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
			return timeLeavingWork;
		}
		//遅刻控除時間帯
		LateLeaveEarlyTimeSheet lateDeducation = within.getLateTimeSheet().get().getForDeducationTimeSheet().get();
		
		//控除判断処理
		boolean isDeductLateTime = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().decisionLateDeductSetting(
				lateDeducation.calcTotalTime(),
				integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet(),
				integrationOfWorkTime.getCommonSetting(),
				todayWorkType);

		//控除する場合
		if(isDeductLateTime && timeLeavingWork.getStampOfAttendance().isPresent()){
			//出退勤．出勤 ← 遅刻時間帯終了時刻
			timeLeavingWork.getStampOfAttendance().get().getTimeDay().setTimeWithDay(
					Optional.of(lateDeducation.getTimeSheet().getEnd()));
			
			//時間帯．開始 ← 遅刻時間帯終了時刻
			this.withinWorkTimeFrame.stream()
					.filter(c -> c.getWorkingHoursTimeNo().v() == workNo)
					.findFirst().ifPresent(c -> c.shiftStart(lateDeducation.getTimeSheet().getEnd()));
		}
		return timeLeavingWork;
	}


	private WithinWorkTimeFrame getWithinWorkTimeFrame(int workNo) {
		return this.withinWorkTimeFrame.stream().filter(c -> c.getWorkingHoursTimeNo().v() == workNo)
				.findFirst().orElse(null);
	}
	
	 /**
	 * 早退控除
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param deductionTimeSheet 控除時間帯
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @return 出退勤
	 */
	public TimeLeavingWork calcLeaveEarlyTimeDeduction(
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			DeductionTimeSheet deductionTimeSheet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet){
	
		int workNo = timeLeavingWork.getWorkNo().v();
		
		//早退判断時刻を取得
		Optional<LeaveEarlyDecisionClock> leaveEarlyDecisionClock = LeaveEarlyDecisionClock.create(
				workNo,
				predetermineTimeSet,
				integrationOfWorkTime,
				integrationOfDaily,
				timeLeavingWork,
				todayWorkType,
				deductionTimeSheet);
		
		if(leaveEarlyDecisionClock.isPresent())
			this.leaveEarlyDecisionClock.add(leaveEarlyDecisionClock.get());
		
		val within = getWithinWorkTimeFrame(workNo);
		
		if(within == null) return timeLeavingWork; 
		
		//早退時間帯の作成
		within.createLeaveEarlyTimeSheet(
				leaveEarlyDecisionClock,
				timeLeavingWork,
				integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY),
				within,
				deductionTimeSheet,
				predetermineTimeSet.getTimeSheets(todayWorkType.getDailyWork().decisionNeedPredTime(), workNo),
				workNo,
				todayWorkType,
				predetermineTimeSet,
				integrationOfWorkTime,
				integrationOfDaily);
		
		if(!within.getLeaveEarlyTimeSheet().isPresent() || !within.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
			return timeLeavingWork;
		}
		//遅刻控除時間帯
		LateLeaveEarlyTimeSheet leaveEarlyDeducation = within.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().get();
		
		//控除判断処理
		boolean isDeductLateTime = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().decisionLateDeductSetting(
				AttendanceTime.ZERO,
				integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getGraceTimeSet(),
				integrationOfWorkTime.getCommonSetting(),
				todayWorkType);
		
		//控除する場合
		if(isDeductLateTime && timeLeavingWork.getStampOfLeave().isPresent()){
			//出退勤．退勤 ← 早退時間帯終了時刻 
			timeLeavingWork.getStampOfLeave().get().getTimeDay().setTimeWithDay(
				 Optional.of(leaveEarlyDeducation.getTimeSheet().getStart()));
			
			//時間帯．終了 ← 早退時間帯終了時刻
			this.withinWorkTimeFrame.stream()
					.filter(c -> c.getWorkingHoursTimeNo().v() == workNo)
					.findFirst().ifPresent(c -> c.shiftEnd(leaveEarlyDeducation.getTimeSheet().getStart()));
		}
		return timeLeavingWork;
	}
	
	/**
	 * 流動勤務(平日・就内)
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param deductionTimeSheet 控除時間帯
	 * @param creatingWithinWorkTimeSheet 就業時間内時間帯（遅刻早退を事前に求めた結果が入っている）
	 * @return 就業時間内時間帯
	 */
	public static WithinWorkTimeSheet createAsFlow(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			WithinWorkTimeSheet creatingWithinWorkTimeSheet) {
		
		//1回目の開始
		TimeWithDayAttr startTime = creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(0).getTimeSheet().getStart();
		
		//休暇使用時間の取得
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			creatingWithinWorkTimeSheet.setVacationUseTime(
					integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily(),
					integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLeaveEarlyTimeOfDaily(),
					integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getOutingTimeOfDailyPerformance());
		}
		
		//控除時間中の時間休暇相殺時間の計算
		creatingWithinWorkTimeSheet.calcTimeVacationOffsetTime(
				new CompanyHolidayPriorityOrder(integrationOfWorkTime.getWorkTimeSetting().getCompanyId()),
				deductionTimeSheet.getForDeductionTimeZoneList());
		
		//就業時間内時間帯を作成
		creatingWithinWorkTimeSheet.createWithinWorkTimeSheetAsFlowWork(
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				startTime,
				deductionTimeSheet,
				integrationOfWorkTime.getFlowWorkSetting().get(),
				predetermineTimeSet,
				companyCommonSetting.getHolidayAdditionPerCompany());
		
		//時間休暇溢れ分の割り当て（流動就内）
		creatingWithinWorkTimeSheet.allocateOverflowTimeVacation(
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				predetermineTimeSet,
				companyCommonSetting.getHolidayAdditionPerCompany().get());
		
		// 控除時間帯の登録
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(0).registDeductionListForWithin(
				deductionTimeSheet, Optional.of(integrationOfWorkTime.getCommonSetting()));
		
		//加給時間帯を作成
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(0).createBonusPayTimeSheet(
				personDailySetting.getBonusPaySetting(),
				integrationOfDaily.getSpecDateAttr(),
				deductionTimeSheet);
		
		//深夜時間帯を作成
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(0).createMidNightTimeSheet(
				companyCommonSetting.getMidNightTimeSheet(),
				Optional.of(integrationOfWorkTime.getCommonSetting()),
				deductionTimeSheet);
		
		return creatingWithinWorkTimeSheet;
	}
	
	/**
	 * 休暇使用時間の取得
	 * @param lateTimeOfDaily 日別実績の遅刻時間
	 * @param leaveEarlyTimeOfDaily 日別実績の早退時間
	 * @param outingTimeOfDailyPerformance 日別実績の外出時間
	 */
	private void setVacationUseTime(
			List<LateTimeOfDaily> lateTimeOfDaily,
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily,
			List<OutingTimeOfDaily> outingTimeOfDailyPerformance) {
		
		//遅刻早退休暇使用時間
		for(val within : this.withinWorkTimeFrame) {
			//枠No取得
			EmTimeFrameNo frameNo = new EmTimeFrameNo(within.getWorkingHoursTimeNo().v());
			
			//就業時間内時間枠の枠Noと日別実績の遅刻時間の勤務Noが一致しているものをセットする。遅刻早退は勤務Noが2しかない場合がある為。
			within.setLateVacationUseTime(
					lateTimeOfDaily.stream()
							.filter(late -> late.getWorkNo().v().equals(frameNo.v()))
							.map(late -> late.getTimePaidUseTime())
							.findFirst());
			
			within.setLeaveEarlyVacationUseTime(
					lateTimeOfDaily.stream()
							.filter(leaveEarly -> leaveEarly.getWorkNo().v().equals(frameNo.v()))
							.map(leaveEarly -> leaveEarly.getTimePaidUseTime())
							.findFirst());
		}
		
		//外出休暇使用時間
		for(OutingTimeOfDaily outing : outingTimeOfDailyPerformance) {
			//私用、組合のみセット 控除する外出しか時間休暇で相殺されない為
			if(outing.getReason().anyMatch(GoingOutReason.PRIVATE, GoingOutReason.UNION)) {
				this.outingVacationUseTime.put(outing.getReason(), outing.getTimeVacationUseOfDaily());
			}
		}
	}
	
	/**
	 * 控除時間中の時間休暇相殺時間の計算
	 * @param priorityOrder 時間休暇相殺優先順位
	 * @param timeSheetOfDeductionItems 控除項目の時間帯 この中の外出を相殺する
	 */
	private void calcTimeVacationOffsetTime(CompanyHolidayPriorityOrder priorityOrder, List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
		for(val within : this.withinWorkTimeFrame) {
			//遅刻相殺時間の計算
			if(within.getLateTimeSheet().isPresent() && within.getLateVacationUseTime().isPresent()) {
				within.getLateTimeSheet().get().setOffsetTime(
						DeductionAtr.Appropriate,
						priorityOrder,
						within.getLateVacationUseTime().get());
				within.setLateVacationUseTime(
						within.getLateVacationUseTime().map(l -> l.minus(within.getLateTimeSheet().get().getOffsetTime()
								.orElse(DeductionOffSetTime.createAllZero()))));
			}
			//早退相殺時間の計算
			if(within.getLeaveEarlyTimeSheet().isPresent() && within.getLeaveEarlyVacationUseTime().isPresent()) {
				within.getLeaveEarlyTimeSheet().get().setOffsetTime(
						DeductionAtr.Deduction,
						priorityOrder,
						within.getLeaveEarlyVacationUseTime().get());
				within.setLeaveEarlyVacationUseTime(
						within.getLeaveEarlyVacationUseTime().map(l -> l.minus(within.getLeaveEarlyTimeSheet().get().getOffsetTime()
								.orElse(DeductionOffSetTime.createAllZero()))));
			}
		}
		//外出相殺時間の計算
		for(TimeSheetOfDeductionItem item : timeSheetOfDeductionItems) {
			item.setOutOffsetTime(priorityOrder, this.outingVacationUseTime);
			if(!item.getGoOutReason().isPresent()
					|| !this.outingVacationUseTime.containsKey(item.getGoOutReason().get())
					|| !item.getDeductionOffSetTime().isPresent()) {
				continue;
			}
			this.outingVacationUseTime.replace(
					item.getGoOutReason().get(),
					this.outingVacationUseTime.get(item.getGoOutReason().get()).minus(item.getDeductionOffSetTime().get()));
		}
	}
	
	/**
	 * 就業時間内時間帯を作成
	 * @param personDailySetting 社員設定管理
	 * @param workType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param startTime 開始時刻
	 * @param deductionTimeSheet 控除時間帯
	 * @param flowWorkSetting 流動勤務設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param holidayAdditionSet 休暇加算時間設定
	 */
	private void createWithinWorkTimeSheetAsFlowWork(
			ManagePerPersonDailySet personDailySetting,
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeWithDayAttr startTime,
			DeductionTimeSheet deductionTimeSheet,
			FlowWorkSetting flowWorkSetting,
			PredetermineTimeSetForCalc predetermineTimeSet,
			Optional<HolidayAddtionSet> holidayAdditionSet) {
		
		TimeWithDayAttr endTime;
		
		if(flowWorkSetting.getHalfDayWorkTimezoneLstOTTimezone().isEmpty()) {
			//1日の計算範囲から終了時刻を計算
			endTime = predetermineTimeSet.getOneDayTimeSpan().getEnd();
		}
		else{
			//残業開始となる経過時間を取得
			AttendanceTime elapsedTime = flowWorkSetting.getHalfDayWorkTimezoneLstOTTimezone().get(0).getFlowTimeSetting().getElapsedTime();
			
			//経過時間から終了時刻を計算
			endTime = this.withinWorkTimeFrame.get(0).getTimeSheet().getStart().forwardByMinutes(elapsedTime.valueAsMinutes());
			
			//休暇加算時間を計算する
			VacationAddTime vacationAddTime = VacationClass.createAllZero().calcVacationAddTime(
					PremiumAtr.RegularWork,
					workType,
					Optional.of(integrationOfWorkTime.getCode()),
					personDailySetting.getPersonInfo(),
					holidayAdditionSet,
					personDailySetting.getAddSetting().getVacationCalcMethodSet(),
					Optional.of(predetermineTimeSet),
					Optional.of(personDailySetting.getPredetermineTimeSetByPersonWeekDay()));
			
			//時間休暇加算時間を計算する
			AttendanceTime timeVacationAddTime = this.calcTimeVacationAddTime(
					integrationOfDaily,
					deductionTimeSheet,
					personDailySetting.getAddSetting().getVacationCalcMethodSet(),
					holidayAdditionSet,
					integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm());
			
			endTime = endTime.backByMinutes(timeVacationAddTime.addMinutes(vacationAddTime.calcTotaladdVacationAddTime()).valueAsMinutes());
			
			for(TimeSheetOfDeductionItem item : deductionTimeSheet.getForDeductionTimeZoneList()) {
				// 重複している時間帯
				Optional<TimeSpanForDailyCalc> overlapptingTime =
						item.getTimeSheet().getDuplicatedWith(new TimeSpanForDailyCalc(startTime, endTime));
				if(!overlapptingTime.isPresent()) continue;
				// 重複していた時、対象の控除時間帯から重複開始時刻以降の時間帯を取り出す
				TimeSheetOfDeductionItem diffSheet = item.reCreateOwn(overlapptingTime.get().getStart(), false);
				// 控除時間の計算
				int deductTime = diffSheet.calcTotalTime(NotUseAtr.USE, NotUseAtr.NOT_USE).valueAsMinutes();
				if (deductTime > 0){
					// 控除時間分、終了時刻をズラす
					endTime = endTime.forwardByMinutes(deductTime);
				}
			}
		}
		//退勤時刻の補正
		endTime = this.correctleaveTimeForFlow(endTime);
		
		//退勤時刻を更新する
		this.updateleaveTime(endTime);
		
		//遅刻早退控除前時間帯を作成
		this.setBeforeLateEarlyTimeSheet(workType, integrationOfWorkTime, predetermineTimeSet, integrationOfDaily.getAttendanceLeave());
	}
	
	/**
	 * 時間休暇加算時間を取得する
	 * @param integrationOfDaily 日別勤怠(Work)
	 * @param deductionTimeSheet 控除時間帯
	 * @param calcMethodSet 休暇の計算方法の設定
	 * @param addtionSet 休暇加算時間設定
	 * @param workTimeForm 就業時間帯の勤務形態
	 * @return 時間休暇加算時間
	 */
	private AttendanceTime calcTimeVacationAddTime(IntegrationOfDaily integrationOfDaily, DeductionTimeSheet deductionTimeSheet,
			HolidayCalcMethodSet calcMethodSet, Optional<HolidayAddtionSet> addtionSet, WorkTimeForm workTimeForm) {
		if(!addtionSet.isPresent()) {
			return AttendanceTime.ZERO;
		}
		if(!integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return AttendanceTime.ZERO;
		}
		//遅刻の時間休暇加算時間を取得する
		List<LateTimeOfDaily> lateDailies = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily();
		AttendanceTime lateTime = new AttendanceTime(lateDailies.stream()
				.map(l -> l.calcVacationAddTime(calcMethodSet, addtionSet, this.withinWorkTimeFrame, workTimeForm))
				.collect(Collectors.summingInt(a -> a.valueAsMinutes())));
		
		//早退の時間休暇加算時間を取得する
		List<LeaveEarlyTimeOfDaily> leaveEarlyDailies = integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily();
		AttendanceTime leaveEarlyTime = new AttendanceTime(leaveEarlyDailies.stream()
				.map(l -> l.calcVacationAddTime(calcMethodSet, addtionSet, this.withinWorkTimeFrame, workTimeForm))
				.collect(Collectors.summingInt(a -> a.valueAsMinutes())));
		
		//外出の時間休暇加算時間を取得する
		AttendanceTime outTime = new AttendanceTime(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().stream()
					.filter(o -> o.getReason().isPrivateOrUnion())
					.map(o -> o.calcVacationAddTime(calcMethodSet, addtionSet, deductionTimeSheet, workTimeForm))
					.collect(Collectors.summingInt(a -> a.valueAsMinutes())));
		
		AttendanceTime totalTime = AttendanceTime.ZERO;
		return totalTime
				.addMinutes(lateTime.valueAsMinutes())
				.addMinutes(leaveEarlyTime.valueAsMinutes())
				.addMinutes(outTime.valueAsMinutes());
	}
	
	/**
	 * 退勤時刻の補正（流動勤務で経過時間より退勤時刻が早い場合に補正する）
	 * @param endTime 就業時間内時間帯終了時刻
	 * @return 補正後の退勤時刻
	 */
	private TimeWithDayAttr correctleaveTimeForFlow(TimeWithDayAttr endTime){
		
		TimeWithDayAttr leaveTime = TimeWithDayAttr.THE_PRESENT_DAY_0000;
		
		//退勤時刻を求める
		//2勤務目がある場合、2勤務目の退勤時刻
		if(this.withinWorkTimeFrame.size() >= 2) {
			leaveTime = this.withinWorkTimeFrame.stream()
					.sorted((c1, c2) -> c2.getWorkingHoursTimeNo().compareTo(c1.getWorkingHoursTimeNo()))
					.findFirst().get().getTimeSheet().getEnd();
		}
		//1勤務のみの場合、1勤務目の退勤時刻
		else {
			leaveTime = this.withinWorkTimeFrame.stream()
					.sorted((c1, c2) -> c1.getWorkingHoursTimeNo().compareTo(c2.getWorkingHoursTimeNo()))
					.findFirst().get().getTimeSheet().getEnd();
		}
		//就業時間内時間帯終了時刻←退勤時刻
		if(leaveTime.lessThan(endTime)) {
			endTime = endTime.backByMinutes(endTime.valueAsMinutes() - leaveTime.valueAsMinutes());
		}
		return endTime;
	}
	
	/**
	 * 指定時刻を含む就業時間内時間枠の終了時刻を更新する
	 * @param endTime 指定時刻
	 */
	private void updateleaveTime(TimeWithDayAttr endTime) {
		List<WithinWorkTimeFrame> frames = new ArrayList<WithinWorkTimeFrame>();
		
		for(WithinWorkTimeFrame frame : this.withinWorkTimeFrame) {
			//指定時刻が時間帯に含まれているか判断
			if(frame.getTimeSheet().contains(endTime)) {
				frame.shiftEnd(endTime);
				frames.add(frame);
				break;
			}
			frames.add(frame);
		}
		this.withinWorkTimeFrame.clear();
		this.withinWorkTimeFrame.addAll(frames);
	}
	
	/**
	 * 時間休暇溢れ分の割り当て（流動就内）
	 * @param personDailySetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param workType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)
	 * @param flowWorkSetting 流動勤務設定
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @return 休暇使用合計残時間未割当
	 */
	private void allocateOverflowTimeVacation(
			ManagePerPersonDailySet personDailySetting,
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSet,
			HolidayAddtionSet holidayAdditionPerCompany) {
		
		//休暇使用合計残時間を取得
		AttendanceTime totalTime = this.calcTotalVacationUseRemainingTime(holidayAdditionPerCompany);
		
		//就業時間内時間枠に入れる
		this.withinWorkTimeFrame.get(0).setTimeVacationOverflowTime(
				Optional.of(this.allocateVacationTimeToLegalTime(
						personDailySetting,
						workType,
						integrationOfWorkTime,
						integrationOfDaily,
						predetermineTimeSet,
						holidayAdditionPerCompany,
						totalTime)));
		
		//まだ残っている残時間を計算する
		AttendanceTime remainingTotalTime = totalTime.minusMinutes(this.withinWorkTimeFrame.get(0).getTimeVacationOverflowTime().get().valueAsMinutes());
		
		//所定内割増に時間休暇溢れ時間を割り当て
		if(this.withinWorkTimeFrame.get(0).getPremiumTimeSheetInPredetermined().isPresent()) {
			this.withinWorkTimeFrame.get(0).getPremiumTimeSheetInPredetermined().get().setTimeVacationOverflowTime(
					Optional.of(this.allocateVacationTimeToPredetermineTime(
							personDailySetting,
							workType,
							integrationOfWorkTime,
							integrationOfDaily,
							predetermineTimeSet,
							holidayAdditionPerCompany,
							remainingTotalTime)));
			
			//まだ残っている残時間を計算する
			remainingTotalTime = remainingTotalTime.minusMinutes(
					this.withinWorkTimeFrame.get(0).getPremiumTimeSheetInPredetermined().get().getTimeVacationOverflowTime().get().valueAsMinutes());
		}
		//return remainingTotalTime;
		this.timeVacationAdditionRemainingTime = Finally.of(remainingTotalTime);
	}
	
	/**
	 * 休暇使用合計残時間を取得
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @return AttendanceTime 休暇使用合計残時間
	 */
	private AttendanceTime calcTotalVacationUseRemainingTime(HolidayAddtionSet holidayAdditionPerCompany){
		
		AttendanceTime totalTime = AttendanceTime.ZERO;
		
		//遅刻休暇使用時間の合計
		totalTime.addMinutes(this.withinWorkTimeFrame.stream()
				.filter(frame -> frame.getLateVacationUseTime().isPresent())
				.mapToInt(frame -> frame.getLateVacationUseTime().get().calcTotalVacationAddTime(Optional.of(holidayAdditionPerCompany), AdditionAtr.All))
				.sum());
		
		//早退休暇使用時間の合計
		totalTime.addMinutes(this.withinWorkTimeFrame.stream()
				.filter(frame -> frame.getLeaveEarlyVacationUseTime().isPresent())
				.mapToInt(frame -> frame.getLeaveEarlyVacationUseTime().get().calcTotalVacationAddTime(Optional.of(holidayAdditionPerCompany), AdditionAtr.All))
				.sum());

		//外出休暇使用時間の合計
		totalTime.addMinutes(this.outingVacationUseTime.values().stream()
				.mapToInt(outing -> outing.calcTotalVacationAddTime(Optional.of(holidayAdditionPerCompany), AdditionAtr.All))
				.sum());
		
		return totalTime;
	}
	
	/**
	 * 法定労働時間に時間休暇溢れ時間割り当て（流動就内）
	 * @param personDailySetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param workType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)
	 * @param flowWorkSetting 流動勤務設定
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @param totalTime 休暇使用合計残時間
	 * @return 割り当て時間
	 */
	private AttendanceTime allocateVacationTimeToLegalTime(
			ManagePerPersonDailySet personDailySetting,
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSet,
			HolidayAddtionSet holidayAdditionPerCompany,
			AttendanceTime totalTime) {
		
		if(totalTime.lessThanOrEqualTo(AttendanceTime.ZERO))
			return AttendanceTime.ZERO;
		
		//就業時間を計算
		WorkHour workTime = this.calcWorkTime(
				PremiumAtr.RegularWork, 
				VacationClass.createAllZero(),
				this.timeVacationAdditionRemainingTime.isPresent()?this.timeVacationAdditionRemainingTime.get():AttendanceTime.ZERO,
				workType,
				predetermineTimeSet,
				Optional.of(integrationOfWorkTime.getCode()),
				integrationOfDaily.getCalAttr().getLeaveEarlySetting(),
				personDailySetting.getAddSetting(),
				holidayAdditionPerCompany,
				personDailySetting.getAddSetting().getVacationCalcMethodSet(),
				personDailySetting.getDailyUnit(),
				Optional.of(integrationOfWorkTime.getCommonSetting()),
				personDailySetting.getPersonInfo(),
				Optional.of(personDailySetting.getPredetermineTimeSetByPersonWeekDay()),
				Optional.empty(),//コアタイム時間帯設定
				HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(
						personDailySetting.getAddSetting().getCalculationByActualTimeAtr(PremiumAtr.RegularWork)),
				NotUseAtr.USE,
				integrationOfDaily.getAttendanceLeave());
		
		//就業時間 >= 法定労働時間
		if(workTime.getWorkTime().greaterThanOrEqualTo(new AttendanceTime(personDailySetting.getDailyUnit().getDailyTime().valueAsMinutes())))
			return AttendanceTime.ZERO;
		
		//法定労働時間不足時間を計算
		AttendanceTime missingTime = new AttendanceTime(
				personDailySetting.getDailyUnit().getDailyTime().minusMinutes(workTime.getWorkTime().valueAsMinutes()).valueAsMinutes());
		
		//法定労働時間不足分 < 時間休暇加算残時間未割当
		if(missingTime.lessThanOrEqualTo(totalTime))
			return missingTime;
		
		return totalTime;
	}
	
	/**
	 * 所定労働時間に時間休暇溢れ時間割り当て（流動就内）
	 * @param personDailySetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayAdditionPerCompany 休暇加算時間設定
	 * @param totalTime 休暇使用合計残時間
	 * @return 割り当て時間
	 */
	private AttendanceTime allocateVacationTimeToPredetermineTime(
			ManagePerPersonDailySet personDailySetting,
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSet,
			HolidayAddtionSet holidayAdditionPerCompany,
			AttendanceTime totalTime){
		
		if(totalTime.lessThanOrEqualTo(AttendanceTime.ZERO))
			return AttendanceTime.ZERO;
		
		//所定内割増時間の上限を計算（所定時間-法定労働時間）
		AttendanceTime upperLimitTime = new AttendanceTime(
				personDailySetting.getDailyUnit().getDailyTime().minusMinutes(predetermineTimeSet.getAdditionSet().getPredTime().getOneDay().minute()).v());
		
		if(upperLimitTime.isNegative())
			upperLimitTime = AttendanceTime.ZERO;
		
		//所定内割増合計時間の計算
		WorkHour workTime = this.calcWorkTime(
				PremiumAtr.RegularWork, 
				VacationClass.createAllZero(),
				this.timeVacationAdditionRemainingTime.isPresent()?this.timeVacationAdditionRemainingTime.get():AttendanceTime.ZERO,
				workType,
				predetermineTimeSet,
				Optional.of(integrationOfWorkTime.getCode()),
				integrationOfDaily.getCalAttr().getLeaveEarlySetting(),
				personDailySetting.getAddSetting(),
				holidayAdditionPerCompany,
				personDailySetting.getAddSetting().getVacationCalcMethodSet(),
				personDailySetting.getDailyUnit(),
				Optional.of(integrationOfWorkTime.getCommonSetting()),
				personDailySetting.getPersonInfo(),
				Optional.of(personDailySetting.getPredetermineTimeSetByPersonWeekDay()),
				Optional.empty(),//コアタイム時間帯設定
				HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(
						personDailySetting.getAddSetting().getCalculationByActualTimeAtr(PremiumAtr.RegularWork)),
				NotUseAtr.USE,
				integrationOfDaily.getAttendanceLeave());
		
		AttendanceTime withinpremiumTime = AttendanceTime.ZERO;
		withinpremiumTime = workTime.getWithinPremiumTime();
				
		return new AttendanceTime(upperLimitTime.minusMinutes(withinpremiumTime.valueAsMinutes()).valueAsMinutes());
	}
	
	/**
	 * 就業時間内時間枠(List)の最初の開始時刻～最後の終了時刻を求める
	 * @param withinWorkTimeFrame 就業時間内時間枠(List)
	 * @return 最初の開始時刻～最後の終了時刻
	 */
	public Optional<TimeSpanForDailyCalc> getStartEndToWithinWorkTimeFrame() {
		
		if(this.withinWorkTimeFrame.isEmpty()) return Optional.empty();
		TimeWithDayAttr start = this.withinWorkTimeFrame.get(0).getTimeSheet().getStart();
		TimeWithDayAttr end = this.withinWorkTimeFrame.get(this.withinWorkTimeFrame.size()-1).getTimeSheet().getEnd();
		return Optional.of(new TimeSpanForDailyCalc(start, end));
	}
	
	/**
	 * 遅刻早退控除前時間帯を作成する
	 * @param workType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param timeLeavingOfDailyAttd 日別勤怠の出退勤
	 */
	public void setBeforeLateEarlyTimeSheet(
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			PredetermineTimeSetForCalc predetermineTimeSet,
			Optional<TimeLeavingOfDailyAttd> timeLeavingOfDailyAttd) {
		this.withinWorkTimeFrame.forEach(
				f -> f.setBeforeLateEarlyTimeSheet(workType, integrationOfWorkTime, predetermineTimeSet, timeLeavingOfDailyAttd));
	}
	
	/**
	 * 所定内割増時間の計算（就業時間の計算内部で呼ぶ処理）
	 * @param workType 勤務種類
	 * @param dailyUnit 法定労働時間
	 * @param predetermineTimeSet 所定時間設定
	 * @param workTime 就業時間
	 * @return 所定内割増時間
	 */
	private AttendanceTime calcPredeterminePremiumTime(
			WorkType workType,
			DailyUnit dailyUnit,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AttendanceTime workTime) {
		if(dailyUnit.getDailyTime().lessThanOrEqualTo(0))
			return AttendanceTime.ZERO;
		
		//所定労働時間
		AttendanceTime predetermineTime = predetermineTimeSet.getpredetermineTime(workType.getDailyWork());
		if(predetermineTime.lessThanOrEqualTo(dailyUnit.getDailyTime().valueAsMinutes()))
			return AttendanceTime.ZERO;
		
		return workTime.minusMinutes(dailyUnit.getDailyTime().valueAsMinutes());
	}
	
	/**
	 * 遅刻早退控除前時間帯に含まない時間帯の取得
	 * @param timeSpan 計算用時間帯
	 * @return 計算用時間帯List
	 */
	public List<TimeSpanForCalc> getTimeSheetNotDupBeforeLateEarly(TimeSpanForCalc timeSpan){
		
		List<TimeSpanForCalc> results = new ArrayList<>(Arrays.asList(timeSpan));
		
		// 確認中Listに保存
		List<TimeSpanForCalc> checking = new ArrayList<>(Arrays.asList(timeSpan));
		// 就業時間内時間枠を確認する
		for (WithinWorkTimeFrame frame : this.withinWorkTimeFrame){
			// 確認中Listを確認する
			for (TimeSpanForCalc check : checking){
				// 結果Listをクリア
				results = new ArrayList<>();
				// 基準時間帯の比較対象と重複していない部分の取得
				results.addAll(check.getNotDuplicationWith(frame.getBeforeLateEarlyTimeSheet().getTimeSpan()));
				// 確認中List ←　結果List
				checking = new ArrayList<>(results);
			}
		}
		// 結果を返す
		return results;
	}

	/**
	 * 指定した時間帯に絞り込む
	 * @param timeSpan 時間帯
	 */
	public void reduceRange(TimeSpanForDailyCalc timeSpan) {
		List<WithinWorkTimeFrame> frames = this.withinWorkTimeFrame.stream()
				.filter(t -> t.getTimeSheet().checkDuplication(timeSpan).isDuplicated())
				.collect(Collectors.toList());
		
		for(int i=0; i<frames.size(); i++) {
			//就業時間内時間枠を指定した時間帯に絞り込む
			frames.get(i).reduceRange(timeSpan);
		}
		this.withinWorkTimeFrame.clear();
		this.withinWorkTimeFrame.addAll(frames);
	}
}
