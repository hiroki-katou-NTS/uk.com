package nts.uk.ctx.at.record.dom.daily.holidayworktime;


import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 休出枠時間帯
 * @author ken_takasu
 *
 */
@Getter
public class HolidayWorkFrameTimeSheet {
	//休出枠No
	private HolidayWorkFrameNo holidayWorkTimeSheetNo;
	//時間帯
	private TimeSpanForCalc timeSheet;
	
	/**
	 * Constructor
	 */
	public HolidayWorkFrameTimeSheet(HolidayWorkFrameNo holidayWorkTimeSheetNo, TimeSpanForCalc timeSheet) {
		super();
		this.holidayWorkTimeSheetNo = holidayWorkTimeSheetNo;
		this.timeSheet = timeSheet;
	}
	
}
