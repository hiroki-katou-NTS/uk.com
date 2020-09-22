package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;

@Getter
public class DailyHolidayWorkPubExport {
	
	List<HolidayWorkFrameTime> holidayWorkFrames;

	/**
	 * Constructor 
	 */
	private DailyHolidayWorkPubExport(List<HolidayWorkFrameTime> holidayWorkFrames) {
		super();
		this.holidayWorkFrames = holidayWorkFrames;
	}
	
	public static DailyHolidayWorkPubExport create(List<HolidayWorkFrameTime> frames) {
		return new DailyHolidayWorkPubExport(frames);
	}
}
