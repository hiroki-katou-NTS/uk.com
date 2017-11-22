package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import lombok.Value;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 休出枠時間
 * @author keisuke_hoshina
 *
 */
@Value
public class HolidayWorkFrameTime {
	private HolidayWorkFrameNo holidayFrameNo;
	private Finally<TimeWithCalculation> holidayWorkTime;
	private Finally<TimeWithCalculation> transferTime;
	private Finally<AttendanceTime> beforeApplicationTime;
	
	
	//休出枠Noのみ指定した休出枠Noに更新する
	public HolidayWorkFrameTime updateHolidayFrameNo(HolidayWorkFrameNo holidayFrameNo) {
		
		HolidayWorkFrameTime holidayWorkFrameTime = new HolidayWorkFrameTime(
				holidayFrameNo,
				this.holidayWorkTime,
				this.transferTime,
				this.beforeApplicationTime);
		return holidayWorkFrameTime;
	}
	
}
