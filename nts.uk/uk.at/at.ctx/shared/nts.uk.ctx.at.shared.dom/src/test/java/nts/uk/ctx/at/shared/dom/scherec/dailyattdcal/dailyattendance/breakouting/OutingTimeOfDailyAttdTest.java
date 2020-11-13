package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStampTest;
import nts.uk.shr.com.time.TimeWithDayAttr;
@RunWith(JMockit.class)	
public class OutingTimeOfDailyAttdTest {
	@Test
	public void getters() {
		
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				Helper.createOutingTimeSheetWithReason(GoingOutReason.PUBLIC)
				));
		
		NtsAssert.invokeGetters(outTime);
	}
	
	/**
	 * Reason isn't matched
	 */
	@Test
	public void getTimeZoneByGoOutReason_empty_case1() {	
		
		OutingTimeSheet outingTimeSheet1 = Helper.createOutingTimeSheetWithReason(GoingOutReason.PRIVATE);
		OutingTimeSheet outingTimeSheet2 = Helper.createOutingTimeSheetWithReason(GoingOutReason.UNION);
		
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				outingTimeSheet1,
				outingTimeSheet2
				));
		
		// Action
		List<TimeSpanForCalc> result = outTime.getTimeZoneByGoOutReason(GoingOutReason.PUBLIC);
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	
	/**
	 * timeZone is empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_empty_case2() {	
		
		OutingTimeSheet outingTimeSheet1 = Helper.createOutingTimeSheetWithReason(GoingOutReason.PUBLIC);
		OutingTimeSheet outingTimeSheet2 = Helper.createOutingTimeSheetWithReason(GoingOutReason.PRIVATE);
		
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				outingTimeSheet1,
				outingTimeSheet2
				));
		
		// Mock
		new Expectations(outingTimeSheet1) {{
			
			outingTimeSheet1.getTimeZone();
			// result = empty; 
		}};
		
		// Action
		List<TimeSpanForCalc> result = outTime.getTimeZoneByGoOutReason(GoingOutReason.PUBLIC);
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	
	@Test
	public void getTimeZoneByGoOutReason_not_empty() {	
		
		OutingTimeSheet outingTimeSheet1 = Helper.createOutingTimeSheetWithReason(GoingOutReason.PUBLIC); // true
		OutingTimeSheet outingTimeSheet2 = Helper.createOutingTimeSheetWithReason(GoingOutReason.PRIVATE);
		OutingTimeSheet outingTimeSheet3 = Helper.createOutingTimeSheetWithReason(GoingOutReason.PUBLIC); // true
		OutingTimeSheet outingTimeSheet4 = Helper.createOutingTimeSheetWithReason(GoingOutReason.UNION);
		OutingTimeSheet outingTimeSheet5 = Helper.createOutingTimeSheetWithReason(GoingOutReason.PUBLIC); // true
		
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				outingTimeSheet1,
				outingTimeSheet2,
				outingTimeSheet3,
				outingTimeSheet4,
				outingTimeSheet5
				));
		// Mock
		TimeSpanForCalc timeZone1 = new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200));
		TimeSpanForCalc timeZone5 = new TimeSpanForCalc(new TimeWithDayAttr(300), new TimeWithDayAttr(400));
		
		new Expectations(outingTimeSheet1, outingTimeSheet3, outingTimeSheet5) {{
			
			outingTimeSheet1.getTimeZone();
			result = Optional.of(timeZone1);
			
			outingTimeSheet3.getTimeZone();
			// result = empty; 
			
			outingTimeSheet5.getTimeZone();
			result = Optional.of(timeZone5);
		}};
		
		// Action
		List<TimeSpanForCalc> result = outTime.getTimeZoneByGoOutReason(GoingOutReason.PUBLIC);
		
		// Assert
		assertThat(result).extracting( e -> e )
						.containsExactly( timeZone1, timeZone5);
		
	}
	
	
	static class Helper {
		
		static OutingTimeSheet createOutingTimeSheetWithReason( GoingOutReason reason) {
			
			TimeActualStamp goOut = new TimeActualStamp(
					WorkStampTest.WorkStampHelper.createWorkStampWithTimeWithDay(1000)
					, WorkStampTest.WorkStampHelper.createWorkStampWithTimeWithDay(1000)
					, 1
	                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
	                , null);
			
			TimeActualStamp comeBack = new TimeActualStamp(
					WorkStampTest.WorkStampHelper.createWorkStampWithTimeWithDay(1000)
					, WorkStampTest.WorkStampHelper.createWorkStampWithTimeWithDay(1000)
					, 1
	                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
	                , null);
			
			return new OutingTimeSheet(
					new OutingFrameNo(1)
					, Optional.of(goOut)
					, new AttendanceTime(1600)
					, new AttendanceTime(1700)
					, reason
					, Optional.of(comeBack));
		}
		
	} 
	
}
