package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
/**
 * 
 * @author tutk
 *
 */
public class AttendanceOneDayTest {
	
	@Test
	public void getters() {
		AttendanceOneDay attendanceOneDay = DomainServiceHeplper.getAttendanceOneDayDefault();
		NtsAssert.invokeGetters(attendanceOneDay);
	}
	
	@Test
	public void testAttendanceOneDay() {
		GeneralDate date = GeneralDate.today();
		Optional<TimeActualStamp> attendance1 = Optional.of(new TimeActualStamp());//dummy
		Optional<TimeActualStamp> leavingStamp1 = Optional.of(new TimeActualStamp());//dummy
		Optional<TimeActualStamp> attendance2 = Optional.of(new TimeActualStamp());//dummy
		Optional<TimeActualStamp> leavingStamp2 = Optional.of(new TimeActualStamp());//dummy
		AttendanceOneDay attendanceOneDay = new AttendanceOneDay(date, attendance1, leavingStamp1, attendance2,
				leavingStamp2);
		NtsAssert.invokeGetters(attendanceOneDay);
	}
}
