package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 非勤務時間帯
 * @author keisuke_hoshina
 *
 */
public class NonWorkingTimeSheet {

	//所定内休憩時間帯
	List<TimeSpanForCalc> whithinBreakTimeSheet;
	//所定外休憩時間帯
	List<TimeSpanForCalc> excessBreakTimeSheet;
	
	
	/**
	 * Constructor 
	 */
	public NonWorkingTimeSheet(List<TimeSpanForCalc> whithinBreakTimeSheet,
			List<TimeSpanForCalc> excessBreakTimeSheet) {
		super();
		this.whithinBreakTimeSheet = whithinBreakTimeSheet;
		this.excessBreakTimeSheet = excessBreakTimeSheet;
	}
}
