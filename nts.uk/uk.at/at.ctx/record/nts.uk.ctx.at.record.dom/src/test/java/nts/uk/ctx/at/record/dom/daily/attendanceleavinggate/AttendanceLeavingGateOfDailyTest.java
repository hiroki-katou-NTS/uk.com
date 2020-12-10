package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
//
//import org.junit.Assert;
import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGate;
//import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
//import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
//import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
//import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class AttendanceLeavingGateOfDailyTest {

	
	@Test
	public void test_calcBeforeAttendanceTime() {
		
		List<AttendanceLeavingGate> attendanceLeavingGates = new ArrayList<>();
		attendanceLeavingGates.add(new AttendanceLeavingGate(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1),
										   new WorkStamp(new TimeWithDayAttr(420),new WorkLocationCD(null),TimeChangeMeans.REAL_STAMP, null),
										   new WorkStamp(new TimeWithDayAttr(1200),new WorkLocationCD(null),TimeChangeMeans.REAL_STAMP, null)));
//		attendanceLeavingGates.add(new AttendanceLeavingGate(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(2),
//										   new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER),
//										   new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER)));
		//日別実績の入退門　　
//		AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily = new AttendanceLeavingGateOfDaily("",GeneralDate.today(),attendanceLeavingGates);
//		
//		List<TimeLeavingWork> list = new ArrayList<>();
//		WorkStamp a = new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER);
//		WorkStamp b = new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER);
		
//		list.add(new TimeLeavingWork(new WorkNo(1),Optional.of(new TimeActualStamp(a,a, 1)),Optional.of(new TimeActualStamp(b,b, 1))));
////		list.add(new TimeLeavingWork(new WorkNo(2),Optional.of(new TimeActualStamp(a,b, 1)),Optional.of(new TimeActualStamp(a,b, 1))));
//		
//		Optional<TimeLeavingOfDailyPerformance> attendanceLeave = Optional.of(new TimeLeavingOfDailyPerformance("",new WorkTimes(1),list,GeneralDate.today()));
//			
//		AttendanceTime result = attendanceLeavingGateOfDaily.calcBeforeAttendanceTime(attendanceLeave, GoLeavingWorkAtr.GO_WORK);
//		Assert.assertEquals(result, new AttendanceTime(-90));
		
	}
	
	
}
