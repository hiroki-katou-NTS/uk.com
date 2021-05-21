package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeSheet;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務外短時間勤務時間帯
 * @author shuichi_ishida
 */
@Getter
public class ShortTimeWorkSheetWithoutWork {
	
	/** 計上用 */
	private List<TimeSheetOfDeductionItem> forRecord;

	/** コンストラクタ */
	public ShortTimeWorkSheetWithoutWork(){
		this.forRecord = new ArrayList<>();
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
				calcRangeOfOneDay.getAttendanceLeavingWork(),
				Optional.empty(),
				companyCommonSetting,
				personCommonSetting);
		// 控除時間帯の作成（控除用）
		result.forRecord = getShortTimeWithoutWork(
				calcRangeOfOneDay,
				deductionTimeSheet.getForRecordTimeZoneList());
		// 勤務外短時間勤務時間帯を返す
		return result;
	}
	
	/**
	 * 勤務外短時間の取得
	 * @param oneDay 1日の計算範囲
	 * @param itemList 控除項目の時間帯List
	 * @return 勤務外短時間List
	 */
	private static List<TimeSheetOfDeductionItem> getShortTimeWithoutWork(
			CalculationRangeOfOneDay oneDay,
			List<TimeSheetOfDeductionItem> itemList){
		
		List<TimeSheetOfDeductionItem> results = new ArrayList<>();
		
		List<TimeSheetOfDeductionItem> childCareList =
				itemList.stream().filter(t -> t.getDeductionAtr().isChildCare()).collect(Collectors.toList());
		for (TimeSheetOfDeductionItem item : childCareList){
			// 遅刻早退控除前時間帯に含まない時間帯の取得
			List<TimeSpanForCalc> checking = new ArrayList<>(
					oneDay.getWithinWorkingTimeSheet().get().getTimeSheetNotDupBeforeLateEarly(
							item.getTimeSheet().getTimeSpan()));
			// 確認中Listを確認する
			List<TimeSpanForCalc> checked = new ArrayList<>(checking);
			if (oneDay.getOutsideWorkTimeSheet().isPresent()){
				OutsideWorkTimeSheet outsideWorkTimeSheet = oneDay.getOutsideWorkTimeSheet().get();
				if (outsideWorkTimeSheet.getOverTimeWorkSheet().isPresent()){
					OverTimeSheet overTimeSheet = outsideWorkTimeSheet.getOverTimeWorkSheet().get();
					checking = new ArrayList<>(checked);
					checked = new ArrayList<>();
					for (TimeSpanForCalc check : checking){
						// 残業時間帯に含まない時間帯の取得
						checked.addAll(overTimeSheet.getTimeSheetNotDupOverTime(check));
					}
				}
			}
			// 結果Listを控除項目の時間帯Listに変換する
			for (TimeSpanForCalc target : checked){
				results.add(item.cloneWithNewTimeSpan(Optional.of(TimeSpanForDailyCalc.of(target))));
			}
		}
		// 勤務外短時間Listを返す
		return results;
	}
	
	/**
	 * 勤務外短時間勤務時間を累計する
	 * @param conditionAtr 控除種別区分
	 * @param roundAtr 丸め区分(時間帯で丸めるかの区分)
	 * @param sumRoundSet 合算丸め設定
	 * @return 勤務外短時間勤務時間
	 */
	public AttendanceTime sumShortWorkTimeWithoutWork(
			ConditionAtr conditionAtr,
			TimeSheetRoundingAtr roundAtr,
			Optional<TimeRoundingSetting> sumRoundSet) {
		
		// 計上用を確認する
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
		List<TimeSheetOfDeductionItem> target = this.forRecord.stream()
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
