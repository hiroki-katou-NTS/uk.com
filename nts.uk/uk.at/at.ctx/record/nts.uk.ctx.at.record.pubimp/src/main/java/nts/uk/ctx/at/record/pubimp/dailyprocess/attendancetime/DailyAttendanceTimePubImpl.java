package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

public class DailyAttendanceTimePubImpl implements DailyAttendanceTimePub{

	/**
	 * RequestList No23
	 */
	@Override
	public DailyAttendanceTimePubExport calcDailyAttendance(DailyAttendanceTimePubImport imp) {

		return new DailyAttendanceTimePubExport(TimeWithCalculation.sameTime(new AttendanceTime(0)),
												new OverTimeFrameNo(0),
												TimeWithCalculation.sameTime(new AttendanceTime(0)),
												new HolidayWorkFrameNo(0),
												TimeWithCalculation.sameTime(new AttendanceTime(0)),
												0,
												TimeWithCalculation.sameTime(new AttendanceTime(0)),
												0,
												TimeWithCalculation.sameTime(new AttendanceTime(0)),
												TimeWithCalculation.sameTime(new AttendanceTime(0))
											);
	}

}
