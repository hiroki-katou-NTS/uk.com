package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;


import java.util.List;

import lombok.Value;

/**
 * 0時跨ぎ集計枠設定
 * @author keisuke_hoshina
 *
 */
@Value
public class OverDayEndAggregateFrameSet {
	private List<OverDayEndSetOfWeekDayHoliday> overDayEndSetOfWeekDayHoliday;
	private List<OverDayEndSetOfHolidayAttendance> overDayEndSetOfHolidayAttendance;
	private List<OverDayEndSetOfHolidayHoliday>    overDayEndSetOfHolidayHoliday;
	
}
