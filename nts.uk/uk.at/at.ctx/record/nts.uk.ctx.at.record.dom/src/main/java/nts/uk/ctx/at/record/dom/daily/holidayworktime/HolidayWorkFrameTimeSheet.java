package nts.uk.ctx.at.record.dom.daily.holidayworktime;


import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 休出枠時間帯
 * @author ken_takasu
 *
 */
@Value
public class HolidayWorkFrameTimeSheet {
	private HolidayWorkFrameNo holidayWorkTimeSheetNo;
	private TimeSpanForCalc timeSheet;
}
