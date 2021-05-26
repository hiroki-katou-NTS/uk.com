package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;

/**
 * 非勤務時間帯
 * @author keisuke_hoshina
 *
 */
public class NonWorkingTimeSheet {

	/** 勤務間休憩時間帯 */
	TimeSheetOfDeductionItem betweenBreakTimeSheet;
	
	
	/**
	 * Constructor 
	 */
	public NonWorkingTimeSheet(TimeSheetOfDeductionItem betweenBreakTimeSheet) {
		super();
		this.betweenBreakTimeSheet = betweenBreakTimeSheet;
	}

	/**
	 * 勤務間休憩時間を計算する
	 * @return 勤務間休憩時間
	 */
	public AttendanceTime calcBetweenBreakTime() {
		return this.betweenBreakTimeSheet.calcTotalTime();
	}
}
