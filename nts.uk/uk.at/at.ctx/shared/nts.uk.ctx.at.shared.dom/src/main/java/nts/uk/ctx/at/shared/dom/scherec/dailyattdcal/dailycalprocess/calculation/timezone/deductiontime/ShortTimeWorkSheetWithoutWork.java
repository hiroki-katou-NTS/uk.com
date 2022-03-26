package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.TemporaryTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務外短時間勤務時間帯
 * @author shuichi_ishida
 */
@Getter
public class ShortTimeWorkSheetWithoutWork {
	
	/** 所定内 */
	private List<TimeSheetOfDeductionItem> within;
	/** 所定外 */
	private List<TimeSheetOfDeductionItem> without;

	/** コンストラクタ */
	public ShortTimeWorkSheetWithoutWork(){
		this.within = new ArrayList<>();
		this.without = new ArrayList<>();
	}
	
	/**
	 * 勤務外短時間勤務時間帯の作成
	 * @param workType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param calcRangeOfOneDay 1日の計算範囲
	 * @param companyCommonSetting 会社別設定管理
	 * @param personCommonSetting 社員設定管理
	 * @return 勤務外短時間勤務時間帯
	 */
	public static ShortTimeWorkSheetWithoutWork create(
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			CalculationRangeOfOneDay calcRangeOfOneDay,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personCommonSetting){

		// クラスを作成する
		ShortTimeWorkSheetWithoutWork result = new ShortTimeWorkSheetWithoutWork();
		// 休出かどうかの判断
		if (workType.isHolidayWork()) return result;
		// 控除時間帯の作成（計算用）
		DeductionTimeSheet deductionTimeSheet = DeductionTimeSheet.createDeductionTimeForCalc(
				workType,
				integrationOfWorkTime,
				integrationOfDaily,
				calcRangeOfOneDay.getOneDayOfRange(),
				calcRangeOfOneDay.getAttendanceLeavingWork().getTimeLeavingWorks(),
				Optional.empty(),
				companyCommonSetting,
				personCommonSetting);
		// 勤務外短時間の取得
		List<TimeSheetOfDeductionItem> shortTimeWithoutWorkList = getShortTimeWithoutWork(
				calcRangeOfOneDay, deductionTimeSheet.getForRecordTimeZoneList());
		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = workType.checkWorkDay();
		// 勤務外短時間の所定時間帯の取得
		List<TimeSpanForCalc> predSheetList = getPredSheetOfShortTimeWithoutWork(
				integrationOfWorkTime, calcRangeOfOneDay, workStyle.toAttendanceHolidayAttr());
		// 勤務外短時間の登録
		registShortTimeWithoutWork(result, integrationOfWorkTime, shortTimeWithoutWorkList, predSheetList);;
		// 勤務外短時間勤務時間帯を返す
		return result;
	}
	
	/**
	 * 勤務外短時間の取得
	 * @param oneDay 1日の計算範囲
	 * @param itemList 計上用控除項目
	 * @return 勤務外短時間List
	 */
	private static List<TimeSheetOfDeductionItem> getShortTimeWithoutWork(
			CalculationRangeOfOneDay oneDay,
			List<TimeSheetOfDeductionItem> itemList){
		
		List<TimeSheetOfDeductionItem> shortTimeWithoutWork = new ArrayList<>();
		
		// 計上用控除項目を確認する
		List<TimeSheetOfDeductionItem> childCareList =
				itemList.stream().filter(t -> t.getDeductionAtr().isChildCare()).collect(Collectors.toList());
		for (TimeSheetOfDeductionItem item : childCareList){
			List<TimeSpanForCalc> results = new ArrayList<>();		// 結果List
			// 就業時間内時間帯を確認する
			if (oneDay.getWithinWorkingTimeSheet().isPresent()){
				WithinWorkTimeSheet withinWorkTimeSheet = oneDay.getWithinWorkingTimeSheet().get();
				// 遅刻早退控除前時間帯に含まない時間帯の取得
				results.addAll(withinWorkTimeSheet.getTimeSheetNotDupBeforeLateEarly(
						item.getTimeSheet().getTimeSpan()));
			}
			else {
				results.add(item.getTimeSheet().getTimeSpan());
			}
			// 就業時間外時間帯を確認する
			if (oneDay.getOutsideWorkTimeSheet().isPresent()){
				OutsideWorkTimeSheet outsideWorkTimeSheet = oneDay.getOutsideWorkTimeSheet().get();
				// 残業時間帯を確認する
				if (outsideWorkTimeSheet.getOverTimeWorkSheet().isPresent()){
					OverTimeSheet overTimeSheet = outsideWorkTimeSheet.getOverTimeWorkSheet().get();
					List<TimeSpanForCalc> notDupList = new ArrayList<>();
					for (TimeSpanForCalc result : results){
						// 残業時間帯に含まない時間帯の取得
						notDupList.addAll(overTimeSheet.getTimeSheetNotDupOverTime(result));
					}
					results = notDupList;
				}
				// 臨時時間帯を確認する
				if (outsideWorkTimeSheet.getTemporaryTimeSheet().isPresent()){
					TemporaryTimeSheet temporaryTimeSheet = outsideWorkTimeSheet.getTemporaryTimeSheet().get();
					List<TimeSpanForCalc> notDupList = new ArrayList<>();
					for (TimeSpanForCalc result : results){
						// 臨時時間帯に含まない時間帯の取得
						notDupList.addAll(temporaryTimeSheet.getTimeSheetNotDupTemporary(result));
					}
					results = notDupList;
				}
			}
			// 結果Listを控除項目の時間帯Listに変換する
			for (TimeSpanForCalc result : results){
				shortTimeWithoutWork.add(item.cloneWithNewTimeSpan(Optional.of(TimeSpanForDailyCalc.of(result))));
			}
		}
		// 勤務外短時間Listを返す
		return shortTimeWithoutWork;
	}
	
	/**
	 * 勤務外短時間の所定時間帯の取得
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param calcRangeOfOneDay 1日の計算範囲
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @return 所定時間帯List
	 */
	private static List<TimeSpanForCalc> getPredSheetOfShortTimeWithoutWork(
			IntegrationOfWorkTime integrationOfWorkTime,
			CalculationRangeOfOneDay calcRangeOfOneDay,
			AttendanceHolidayAttr attendanceHolidayAttr){
		
		List<TimeSpanForCalc> predSheetList = new ArrayList<>();	// 所定時間帯List

		// 勤務形態を取得する
		WorkTimeForm workTimeForm = integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm();
		switch(workTimeForm){
		case FIXED:
			// 固定勤務の所定時間帯の取得
			predSheetList = getPredSheetOfFixed(integrationOfWorkTime, attendanceHolidayAttr,
					calcRangeOfOneDay.getPredetermineTimeSetForCalc());
			break;
		case FLOW:
			// 流動勤務の所定時間帯の取得
			predSheetList = getPredSheetOfFlow(calcRangeOfOneDay.getWorkInformationOfDaily());
			break;
		case FLEX:
			break;
		default:
			break;
		}
		// 所定時間帯Listを返す
		return predSheetList;
	}
	
	/**
	 * 固定勤務の所定時間帯の取得
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @param predTimeSetForCalc 計算用所定時間設定
	 * @return 所定時間帯List
	 */
	private static List<TimeSpanForCalc> getPredSheetOfFixed(
			IntegrationOfWorkTime integrationOfWorkTime,
			AttendanceHolidayAttr attendanceHolidayAttr,
			PredetermineTimeSetForCalc predTimeSetForCalc){
		
		List<TimeSpanForCalc> predSheetList = new ArrayList<>();
		
		// 出勤休日区分から平日出勤用勤務時間帯を取得する
		// ※　半日年休の時、年休部分の短時間勤務時間帯を所定内として判定するため、半日出勤時でも１日の所定・勤務で所定内外判定する。
		Optional<FixedWorkSetting> fixedWorkSet = integrationOfWorkTime.getFixedWorkSetting();
		if (!fixedWorkSet.isPresent()) return predSheetList;
		Optional<FixHalfDayWorkTimezone> halfDaySheetSet =
				fixedWorkSet.get().getFixHalfDayWorkTimezone(AttendanceHolidayAttr.FULL_TIME);
		if (!halfDaySheetSet.isPresent()) return predSheetList;
		FixedWorkTimezoneSet workSheetSet = halfDaySheetSet.get().getWorkTimezone();
		// 所定時間内に含まれる就業時間帯を取得する
		List<TimezoneUse> predTimeSetList = predTimeSetForCalc.getTimeSheets(AttendanceHolidayAttr.FULL_TIME);
		for (TimezoneUse predTimeSet : predTimeSetList){
			List<EmTimeZoneSet> workSheetList = workSheetSet.getWorkTimeSpanWithinPred(predTimeSet);
			// 所定時間帯Listに追加する
			for (EmTimeZoneSet workSheet : workSheetList){
				predSheetList.add(new TimeSpanForCalc(
						workSheet.getTimezone().getStart(), workSheet.getTimezone().getEnd()));
			}
		}
		// 所定時間帯Listを返す
		return predSheetList;
	}
	
	/**
	 * 流動勤務の所定時間帯の取得
	 * @param workInfoOfDaily 日別勤怠の勤務情報
	 * @return 所定時間帯List
	 */
	private static List<TimeSpanForCalc> getPredSheetOfFlow(
			WorkInfoOfDailyAttendance workInfoOfDaily){
		
		List<TimeSpanForCalc> predSheetList = new ArrayList<>();
		
		// 始業終業時間帯の取得
		for (ScheduleTimeSheet scheTimeSheet : workInfoOfDaily.getScheduleTimeSheets()){
			// 所定時間帯Listに追加する
			predSheetList.add(new TimeSpanForCalc(scheTimeSheet.getAttendance(), scheTimeSheet.getLeaveWork()));
		}
		// 所定時間帯Listを返す
		return predSheetList;
	}
	
	/**
	 * 勤務外短時間の登録
	 * @param target 登録対象
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param shortTimeWithoutWorkList 勤務外短時間
	 * @param predSheetList 所定時間帯
	 */
	private static void registShortTimeWithoutWork(
			ShortTimeWorkSheetWithoutWork target,
			IntegrationOfWorkTime integrationOfWorkTime,
			List<TimeSheetOfDeductionItem> shortTimeWithoutWorkList,
			List<TimeSpanForCalc> predSheetList){
		
		// 勤務形態を取得する
		WorkTimeForm workTimeForm = integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm();
		if (workTimeForm.isFlex()){
			// フレックス勤務
			// 所定内　←　勤務外短時間
			target.within.addAll(shortTimeWithoutWorkList);
			return;
		}
		// フレックス勤務以外
		for (TimeSheetOfDeductionItem shortTimeWithoutWork : shortTimeWithoutWorkList){
			// 勤務外時間帯を計算用時間帯に変換する
			TimeSpanForCalc timeSpan = shortTimeWithoutWork.getTimeSheet().getTimeSpan();
			// 指定Listと重複している時間帯の取得
			List<TimeSpanForCalc> withinSpanList = timeSpan.getDuplicatedWith(predSheetList);
			// 所定内Listを控除項目の時間帯Listに変換する
			for (TimeSpanForCalc withinSpan : withinSpanList){
				target.within.add(shortTimeWithoutWork.cloneWithNewTimeSpan(
						Optional.of(TimeSpanForDailyCalc.of(withinSpan))));
			}
			// 指定Listと重複していない時間帯の取得
			List<TimeSpanForCalc> withoutSpanList = timeSpan.getNotDuplicatedWith(predSheetList);
			// 所定外Listを控除項目の時間帯Listに変換する
			for (TimeSpanForCalc withoutSpan : withoutSpanList){
				target.without.add(shortTimeWithoutWork.cloneWithNewTimeSpan(
						Optional.of(TimeSpanForDailyCalc.of(withoutSpan))));
			}
		}
	}
	
	/**
	 * 勤務外短時間勤務時間を累計する
	 * @param conditionAtr 控除種別区分
	 * @param roundAtr 丸め区分(時間帯で丸めるかの区分)
	 * @param sumRoundSet 合算丸め設定
	 * @param isWithin 所定内かどうか
	 * @return 勤務外短時間勤務時間
	 */
	public AttendanceTime sumShortWorkTimeWithoutWork(
			ConditionAtr conditionAtr,
			TimeSheetRoundingAtr roundAtr,
			Optional<TimeRoundingSetting> sumRoundSet,
			boolean isWithin) {

		List<TimeSheetOfDeductionItem> source = new ArrayList<>();
		final ChildCareAtr childCareAtr;
		if (conditionAtr.isChild()){
			childCareAtr = ChildCareAtr.CHILD_CARE;
		}
		else if (conditionAtr.isCare()){
			childCareAtr = ChildCareAtr.CARE;
		}
		else{
			return AttendanceTime.ZERO;
		}
		if (isWithin) {
			// 所定内を確認する
			source = this.within;
		}
		else {
			// 所定外を確認する
			source = this.without;
		}
		List<TimeSheetOfDeductionItem> target = source.stream()
				.filter(t -> t.getDeductionAtr().isChildCare())
				.filter(t -> t.getChildCareAtr().isPresent())
				.filter(t -> t.getChildCareAtr().get() == childCareAtr)
				.collect(Collectors.toList());
		int withoutMinutes = 0;
		for (TimeSheetOfDeductionItem item : target){
			// 時間の計算
			withoutMinutes += item.calcTotalTime(NotUseAtr.USE,
					(roundAtr == TimeSheetRoundingAtr.ALL ? NotUseAtr.NOT_USE : NotUseAtr.USE)).valueAsMinutes();
		}
		if (roundAtr == TimeSheetRoundingAtr.ALL){
			// 丸め処理
			if (sumRoundSet.isPresent()){
				withoutMinutes = sumRoundSet.get().round(withoutMinutes);
			}
		}
		// 勤務外短時間勤務時間を返す
		return new AttendanceTime(withoutMinutes);
	}
}
