package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.service;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * ドメインサービス：実働時間帯リスト計算
 * @author shuichi_ishida
 */
public class ActualWorkTimeSheetListService {

	/**
	 * 控除時間の計算
	 * @param actualAtr 実働時間帯区分
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param goOutSet 就業時間帯の外出設定
	 * @param actualWorkTimeSheetList 実働時間帯List
	 * @return 控除合計時間
	 */
	public static AttendanceTime calcDeductionTime(
			ActualWorkTimeSheetAtr actualAtr,
			ConditionAtr conditionAtr,
			DeductionAtr dedAtr,
			Optional<WorkTimezoneGoOutSet> goOutSet,
			List<ActualWorkingTimeSheet> actualWorkTimeSheetList,
			NotUseAtr canOffset){

		int totalDeductMinutes = 0;		// 控除合計時間
		// 実働時間帯リストを取得
		for (ActualWorkingTimeSheet actualWorkTimeSheet : actualWorkTimeSheetList){
			// 控除時間の計算
			totalDeductMinutes += actualWorkTimeSheet.calcDedTimeByAtr(actualAtr, dedAtr, conditionAtr, goOutSet, canOffset).valueAsMinutes();
		}
		// 控除合計時間を返す
		return new AttendanceTime(totalDeductMinutes);
	}
	
	/**
	 * 控除回数の計算
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param actualWorkTimeSheetList 実働時間帯List
	 * @return 合計控除回数
	 */
	public static int calcDeductionCount(
			ConditionAtr conditionAtr,
			DeductionAtr dedAtr,
			List<ActualWorkingTimeSheet> actualWorkTimeSheetList){

		int totalDeductCount = 0;		// 合計控除回数
		// 実働時間帯Listを取得
		for (ActualWorkingTimeSheet actualWorkTimeSheet : actualWorkTimeSheetList){
			// 控除回数の計算
			totalDeductCount += actualWorkTimeSheet.calcDeductionCount(conditionAtr, dedAtr);
		}
		// 合計控除回数を返す
		return totalDeductCount;
	}

	/** 相殺時間休暇使用時間の計算 */
	public static TimevacationUseTimeOfDaily calcOffsetTimeVacationUseTime(ConditionAtr conditionAtr,
			DeductionAtr dedAtr, List<ActualWorkingTimeSheet> actualWorkTimeSheetList,
			CompanyHolidayPriorityOrder priorityOrder, TimevacationUseTimeOfDaily timeVacationUseOfDaily) {
		
		TimevacationUseTimeOfDaily offsetTime = TimevacationUseTimeOfDaily.defaultValue();
		// 実働時間帯リストを取得
		for (ActualWorkingTimeSheet actualWorkTimeSheet : actualWorkTimeSheetList){
			// 相殺時間休暇使用の合計を算出する
			offsetTime = offsetTime.add(actualWorkTimeSheet.calcOffsetTimeVacationUseTime(
											conditionAtr, dedAtr, priorityOrder, timeVacationUseOfDaily));
		}
		return offsetTime;
	}
}
