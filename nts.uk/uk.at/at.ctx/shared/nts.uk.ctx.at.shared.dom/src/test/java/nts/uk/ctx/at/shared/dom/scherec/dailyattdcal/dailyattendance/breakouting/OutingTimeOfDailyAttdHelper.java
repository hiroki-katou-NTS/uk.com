package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class OutingTimeOfDailyAttdHelper {	
	/**
	 * 全部データがある
	 * @return
	 */
	public static TimeActualStamp createTimeActualStamp() {
		val workStamp = new WorkStamp(  new TimeWithDayAttr(60)
		        , new TimeWithDayAttr(120)
		        , new WorkLocationCD("004")
		        , TimeChangeMeans.REAL_STAMP
		        , EngravingMethod.DIRECT_BOUNCE_BUTTON);
		val overTimes = new OvertimeDeclaration(  new AttendanceTime(new Integer(10)), new AttendanceTime(new Integer(10)));
		val timeZone = new TimeZone(new TimeWithDayAttr(120), new TimeWithDayAttr(240));
		return new TimeActualStamp(workStamp, workStamp, new Integer(10), overTimes, timeZone);
	}
	
	/**
	 * 全部データがある
	 * @return
	 */
	public static  TimeActualStamp create_GoOut() {
		val workStamp = new WorkStamp(  new TimeWithDayAttr(60)
		        , new TimeWithDayAttr(120)
		        , new WorkLocationCD("004")
		        , TimeChangeMeans.REAL_STAMP
		        , EngravingMethod.DIRECT_BOUNCE_BUTTON);
		val overTimes = new OvertimeDeclaration(  new AttendanceTime(new Integer(10)), new AttendanceTime(new Integer(10)));
		val timeZone = new TimeZone(new TimeWithDayAttr(120), new TimeWithDayAttr(240));
		return new TimeActualStamp(Optional.of(workStamp), Optional.of(workStamp), new Integer(10)
				, Optional.of(overTimes), Optional.of(timeZone));
	}
	
	public static  TimeActualStamp create_ComeBack() {
		val workStamp = new WorkStamp(  new TimeWithDayAttr(60)
		        , new TimeWithDayAttr(3600)
		        , new WorkLocationCD("004")
		        , TimeChangeMeans.REAL_STAMP
		        , EngravingMethod.DIRECT_BOUNCE_BUTTON);
		val overTimes = new OvertimeDeclaration(  new AttendanceTime(new Integer(10)), new AttendanceTime(new Integer(10)));
		val timeZone = new TimeZone(new TimeWithDayAttr(120), new TimeWithDayAttr(240));
		return new TimeActualStamp(Optional.of(workStamp), Optional.of(workStamp), new Integer(10)
				                 , Optional.of(overTimes), Optional.of(timeZone));
	}
	
	public static  TimeActualStamp create_GoOut_Empty() {
		val overTimes = new OvertimeDeclaration(  new AttendanceTime(new Integer(10)), new AttendanceTime(new Integer(10)));
		val timeZone = new TimeZone(new TimeWithDayAttr(120), new TimeWithDayAttr(240));
		return new TimeActualStamp(Optional.of(new WorkStamp()), Optional.empty(), new Integer(10)
				, Optional.of(overTimes), Optional.of(timeZone));
	}
	
	public static  TimeActualStamp create_ComeBack_Empty() {
		val overTimes = new OvertimeDeclaration(  new AttendanceTime(new Integer(10)), new AttendanceTime(new Integer(10)));
		val timeZone = new TimeZone(new TimeWithDayAttr(120), new TimeWithDayAttr(240));
		return new TimeActualStamp(Optional.of(new WorkStamp()), Optional.empty(), new Integer(10)
				, Optional.of(overTimes), Optional.of(timeZone));
	}
	
	public static  TimeActualStamp create_GoOut_Time_Day_Empty() {
		val workStamp = new WorkStamp(  new TimeWithDayAttr(60)
		        , null
		        , new WorkLocationCD("004")
		        , TimeChangeMeans.REAL_STAMP
		        , EngravingMethod.DIRECT_BOUNCE_BUTTON);
		val actualStamp = new WorkStamp();
		val overTimes = new OvertimeDeclaration(  new AttendanceTime(new Integer(10)), new AttendanceTime(new Integer(10)));
		val timeZone = new TimeZone(new TimeWithDayAttr(120), new TimeWithDayAttr(240));
		return new TimeActualStamp(Optional.of(actualStamp), Optional.of(workStamp), new Integer(10)
				, Optional.of(overTimes), Optional.of(timeZone));
	}
	
	public static  TimeActualStamp create_ComeBack_Time_Day_Empty() {
		val workStamp = new WorkStamp(  new TimeWithDayAttr(60)
		        , null
		        , new WorkLocationCD("004")
		        , TimeChangeMeans.REAL_STAMP
		        , EngravingMethod.DIRECT_BOUNCE_BUTTON);
		val overTimes = new OvertimeDeclaration(new AttendanceTime(new Integer(10)), new AttendanceTime(new Integer(10)));
		val timeZone = new TimeZone(new TimeWithDayAttr(120), new TimeWithDayAttr(240));
		return new TimeActualStamp(Optional.of(new WorkStamp()), Optional.of(workStamp), new Integer(10)
				, Optional.of(overTimes), Optional.of(timeZone));
	}
}
