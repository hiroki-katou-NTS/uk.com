package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import java.util.HashMap;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

@Stateless
public class DailyAttendanceTimePubImpl implements DailyAttendanceTimePub{

	/**
	 * RequestList No23
	 */
	@Override
	public DailyAttendanceTimePubExport calcDailyAttendance(DailyAttendanceTimePubImport imp) {

		val overTimeFrames = new HashMap<OverTimeFrameNo,TimeWithCalculation>();
		val holidayWorkFrames = new HashMap<HolidayWorkFrameNo,TimeWithCalculation>();
		val bonusPays = new HashMap<Integer,TimeWithCalculation>();
		val specBonusPays = new HashMap<Integer,TimeWithCalculation>();
		for(int loopNumber = 1 ; loopNumber <=10 ; loopNumber++ ) {
			//残業
			overTimeFrames.put(new OverTimeFrameNo(loopNumber), TimeWithCalculation.sameTime(new AttendanceTime(0)));
			//休出
			holidayWorkFrames.put(new HolidayWorkFrameNo(loopNumber), TimeWithCalculation.sameTime(new AttendanceTime(0)));
			//加給
			bonusPays.put(loopNumber, TimeWithCalculation.sameTime(new AttendanceTime(0)));
			//特定日加給
			specBonusPays.put(loopNumber, TimeWithCalculation.sameTime(new AttendanceTime(0)));
			
		}
		
		
		return new DailyAttendanceTimePubExport(overTimeFrames,
												holidayWorkFrames,
												bonusPays,
												specBonusPays,
												TimeWithCalculation.sameTime(new AttendanceTime(0)),
												TimeWithCalculation.sameTime(new AttendanceTime(0))
											);
	}

}
