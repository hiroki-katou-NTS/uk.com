package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;
@RunWith(JMockit.class)	
public class OutingTimeOfDailyAttdTest {
	@Test
	public void getters() {
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack()))
				));
		NtsAssert.invokeGetters(outTime);
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut empty
	 * workDay of comeback not empty
	 * timeDay of comeback  empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_1() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Time_Day_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Time_Day_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut empty
	 * workDay of comeback not empty
	 * timeDay of comeback not empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_2() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Time_Day_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut empty
	 * workDay of comeback empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_3() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Time_Day_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut not empty
	 * workDay of comeback not empty
	 * timeDay of comeback empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_4() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Time_Day_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut not empty
	 * workDay of comeback not empty
	 * timeDay of comeback not empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_5() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack()))
				));
		List<TimeSpanForCalc> excected = Arrays.asList(new TimeSpanForCalc(new TimeWithDayAttr(120), new TimeWithDayAttr(3600)));
		assertThat(outTime.getTimeZoneByGoOutReason()).containsExactlyInAnyOrderElementsOf(excected);
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut not empty
	 * workDay of comeback  empty
	 * timeDay of comeback  empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_6() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut  empty
	 * timeDay of goOut  empty
	 * workDay of comeback  not empty
	 * timeDay of comeback  empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_7() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Time_Day_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut  empty
	 * timeDay of goOut  empty
	 * workDay of comeback  not empty
	 * timeDay of comeback  not empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_8() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut  empty
	 * timeDay of goOut  empty
	 * workDay of comeback   empty
	 * timeDay of comeback   empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_9() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
}
