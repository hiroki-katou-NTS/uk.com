package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

/**
 * 休日出勤時間帯
 * @author keisuke_hoshina
 *
 */
public class HolidayWorkTimeSheet extends CalculationTimeSheet{
	
	private HolidayWorkTimeOfDaily workHolidayTime;
	
	/**
	 * Constructor
	 */
	public HolidayWorkTimeSheet(
			TimeSpanWithRounding roundingSheet,
			TimeSpanForCalc calculationTimeSheet,
			HolidayWorkTimeOfDaily holidayWorkOfDaily) {
		
		super(roundingSheet,calculationTimeSheet);
		this.workHolidayTime = holidayWorkOfDaily;
		
	}
	
	
	/**
	 * 全ての休日出勤時間帯から休日出勤時間を算出する(休日出勤時間帯の合計の時間を取得し1日の範囲に返す)
	 */
	public int calcHolidayWorkTime() {
		
	}
	
	/**
	 * 休日出勤時間帯の遅刻時間を計算する
	 * @return 遅刻時間
	 */
	public int calcLateLeaveEarlyinHolidayWorkTime() {
		
	}
	
}
