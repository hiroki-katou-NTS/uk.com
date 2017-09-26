package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.OverDayEndSetOfWeekDayAttendance;

/**
 * 0時跨ぎの集計枠設定
 * @author keisuke_hoshina
 *
 */
@Value
public class OverDayEndAggregateFrameSet {
	private List<OverDayEndSetOfWeekDayAttendance> overDayEndSetOfWeekDayAttendance;
	private List<OverDayEndSetOfHolidayAttendance> overDayEndSetOfHolidayAttendance;
}
