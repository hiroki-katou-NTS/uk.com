package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import java.util.ArrayList;

import lombok.val;
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

		val overTime = new ArrayList<TimeWithCalculation>();
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		overTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		
		val overTimeFrameNo = new ArrayList<OverTimeFrameNo>();
		overTimeFrameNo.add(new OverTimeFrameNo(1));
		overTimeFrameNo.add(new OverTimeFrameNo(2));
		overTimeFrameNo.add(new OverTimeFrameNo(3));
		overTimeFrameNo.add(new OverTimeFrameNo(4));
		overTimeFrameNo.add(new OverTimeFrameNo(5));
		overTimeFrameNo.add(new OverTimeFrameNo(6));
		overTimeFrameNo.add(new OverTimeFrameNo(7));
		overTimeFrameNo.add(new OverTimeFrameNo(8));
		overTimeFrameNo.add(new OverTimeFrameNo(9));
		overTimeFrameNo.add(new OverTimeFrameNo(10));
		
		val holidayWorkTime = new ArrayList<TimeWithCalculation>();
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		holidayWorkTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		
		val holidayWorkTimeNo = new ArrayList<HolidayWorkFrameNo>();
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(1));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(2));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(3));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(4));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(5));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(6));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(7));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(8));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(9));
		holidayWorkTimeNo.add(new HolidayWorkFrameNo(10));
		
		val bonusPayTime = new ArrayList<TimeWithCalculation>();
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		bonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		
		val bonusPayNo = new ArrayList<Integer>();
		bonusPayNo.add(1);
		bonusPayNo.add(2);
		bonusPayNo.add(3);
		bonusPayNo.add(4);
		bonusPayNo.add(5);
		bonusPayNo.add(6);
		bonusPayNo.add(7);
		bonusPayNo.add(8);
		bonusPayNo.add(9);
		bonusPayNo.add(10);
		
		val specBonusPayTime = new ArrayList<TimeWithCalculation>();
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		specBonusPayTime.add(TimeWithCalculation.sameTime(new AttendanceTime(0)));
		
		val specBonusPayNo = new ArrayList<Integer>();
		specBonusPayNo.add(1);
		specBonusPayNo.add(2);
		specBonusPayNo.add(3);
		specBonusPayNo.add(4);
		specBonusPayNo.add(5);
		specBonusPayNo.add(6);
		specBonusPayNo.add(7);
		specBonusPayNo.add(8);
		specBonusPayNo.add(9);
		specBonusPayNo.add(10);
		
		return new DailyAttendanceTimePubExport(overTime,
												overTimeFrameNo,
												holidayWorkTime,
												holidayWorkTimeNo,
												bonusPayTime,
												bonusPayNo,
												specBonusPayTime,
												specBonusPayNo,
												TimeWithCalculation.sameTime(new AttendanceTime(0)),
												TimeWithCalculation.sameTime(new AttendanceTime(0))
											);
	}

}
