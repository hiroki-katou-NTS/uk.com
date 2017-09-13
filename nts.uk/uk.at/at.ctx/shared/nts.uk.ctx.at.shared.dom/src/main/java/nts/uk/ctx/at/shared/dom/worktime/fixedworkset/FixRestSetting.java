package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

/**
 * 休出時間の時間帯設定
 * @author keisuke_hoshina
 *
 */
@Value
public class FixRestSetting {
	private TimeSpanWithRounding hours;
	
//	private ??? legalHolidayFrameNo;
//	private ??? statutoryHolidayFrameNo;
//	private ??? isNonStatutoryDayoffConstraintTime;
	
	private boolean isLegalHolidayConstraintTime;
	private boolean isNonStatutoryHolidayConstraintTime;
	private boolean isNonStatutoryDayoffConstraintTime;
}
