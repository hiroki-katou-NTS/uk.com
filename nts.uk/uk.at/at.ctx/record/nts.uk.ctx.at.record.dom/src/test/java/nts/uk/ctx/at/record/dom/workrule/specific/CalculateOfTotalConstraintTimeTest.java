package nts.uk.ctx.at.record.dom.workrule.specific;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.LogOnInfo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnNo;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class CalculateOfTotalConstraintTimeTest {
	
	@Test
	public void test_calcCalculateOfTotalConstraintTime() {
		
//		//総拘束時間の計算
//		CalculateOfTotalConstraintTime calculateOfTotalConstraintTime = new CalculateOfTotalConstraintTime(new CompanyId("1"),CalculationMethodOfConstraintTime.REQUEST_FROM_ENTRANCE_EXIT_OUTSIDE_ATTENDANCE);
//		
//		List<TimeLeavingWork> list = new ArrayList<>();
//		WorkStamp a = new WorkStamp(new TimeWithDayAttr(400),new TimeWithDayAttr(400),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER);
//		WorkStamp b = new WorkStamp(new TimeWithDayAttr(1200),new TimeWithDayAttr(1200),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER);
//		list.add(new TimeLeavingWork(new WorkNo(1),Optional.of(new TimeActualStamp(a,a, 1)),Optional.of(new TimeActualStamp(b,b, 1))));
////		list.add(new TimeLeavingWork(new WorkNo(2),Optional.of(new TimeActualStamp(a,b, 1)),Optional.of(new TimeActualStamp(a,b, 1))));
//		//日別実績の出退勤
//		Optional<TimeLeavingOfDailyPerformance> attendanceLeave = Optional.of(new TimeLeavingOfDailyPerformance("",new WorkTimes(1),list,GeneralDate.today()));
//		
//		
//		List<AttendanceLeavingGate> attendanceLeavingGates = new ArrayList<>();
//		attendanceLeavingGates.add(new AttendanceLeavingGate(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(1),
//										   new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER),
//										   new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER)));
////		attendanceLeavingGates.add(new AttendanceLeavingGate(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(2),
////										   new WorkStamp(new TimeWithDayAttr(510),new TimeWithDayAttr(510),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER),
////										   new WorkStamp(new TimeWithDayAttr(1050),new TimeWithDayAttr(1050),new WorkLocationCD(null),StampSourceInfo.TIME_RECORDER)));
//		//日別実績の入退門　　
//		AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily = new AttendanceLeavingGateOfDaily("",GeneralDate.today(),attendanceLeavingGates);
//		
//		
//		List<LogOnInfo> logOnInfo = new ArrayList<>();
//		logOnInfo.add(new LogOnInfo(new PCLogOnNo(1),new TimeWithDayAttr(515),new TimeWithDayAttr(1055)));
////		logOnInfo.add(new LogOnInfo(new PCLogOnNo(2),new TimeWithDayAttr(515),new TimeWithDayAttr(1055)));
//		//日別実績のPCログオン情報
//		PCLogOnInfoOfDaily pCLogOnInfoOfDaily = new PCLogOnInfoOfDaily("",GeneralDate.today(),logOnInfo);
//
//		
//		
//		AttendanceTime result = calculateOfTotalConstraintTime.calcCalculateOfTotalConstraintTime(Optional.of(attendanceLeavingGateOfDaily),
//																								  Optional.of(pCLogOnInfoOfDaily),
//																								  attendanceLeave);
//		Assert.assertEquals(result, new AttendanceTime(800));
			
	}
	
	
	
}
