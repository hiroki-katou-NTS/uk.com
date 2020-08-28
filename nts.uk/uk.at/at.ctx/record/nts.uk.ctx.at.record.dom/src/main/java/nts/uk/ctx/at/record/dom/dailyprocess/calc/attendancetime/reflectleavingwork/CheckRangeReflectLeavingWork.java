package nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectleavingwork;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.OutputCheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.StampReflectTimezoneOutput;

/**
 * 退勤打刻の反映範囲か確認する
 * @author tutk
 *
 */

@Stateless
public class CheckRangeReflectLeavingWork {
	
	public OutputCheckRangeReflectAttd checkRangeReflectAttd(Stamp stamp,StampReflectRangeOutput s,IntegrationOfDaily integrationOfDaily) {
		
		AttendanceTime attendanceTime = stamp.getAttendanceTime().isPresent()?
				stamp.getAttendanceTime().get():new AttendanceTime(stamp.getStampDateTime().clockHourMinute().v());
		List<StampReflectTimezoneOutput> lstStampReflectTimezone = s.getLstStampReflectTimezone();
		int n = lstStampReflectTimezone.size();
		for (int i = 0; i < n; i++) {
			StampReflectTimezoneOutput stampReflectTimezone = lstStampReflectTimezone.get(i);
			if (stampReflectTimezone.getStartTime().v().intValue() <= attendanceTime.v().intValue()
					&& stampReflectTimezone.getEndTime().v().intValue() >= attendanceTime.v().intValue()
					&& stampReflectTimezone.getClassification().value == 1) {
				if (stampReflectTimezone.getWorkNo().v().intValue() == 1) {
					return OutputCheckRangeReflectAttd.FIRST_TIME;
				}
				if (stampReflectTimezone.getWorkNo().v().intValue() == 2) {
					return OutputCheckRangeReflectAttd.SECOND_TIME;
				}
			}
		}
		return OutputCheckRangeReflectAttd.OUT_OF_RANGE;
	}

}
