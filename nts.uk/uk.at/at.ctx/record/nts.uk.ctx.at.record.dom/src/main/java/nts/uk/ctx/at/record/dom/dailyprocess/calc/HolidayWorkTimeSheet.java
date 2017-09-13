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
}
