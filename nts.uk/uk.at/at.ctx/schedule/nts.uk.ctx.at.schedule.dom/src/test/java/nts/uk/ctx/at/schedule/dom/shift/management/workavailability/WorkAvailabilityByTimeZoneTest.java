package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WorkAvailabilityByTimeZoneTest {
	
	@Injectable
    private WorkAvailability.Require require;
	
	private WorkAvailabilityByTimeZone timezoneAvailability;
	
	@Before
	public void initTimezoneAvailability() {
		timezoneAvailability = WorkAvailabilityByTimeZone.create(Arrays.asList(
				new TimeSpanForCalc( new TimeWithDayAttr(360), new TimeWithDayAttr(720)), // 6:00~12:00
				new TimeSpanForCalc( new TimeWithDayAttr(1080), new TimeWithDayAttr(1320)))); // 18:00~22:00
	}
	
	@Test
	public void getters() {
		
		NtsAssert.invokeGetters(timezoneAvailability);
	}
	
	@Test
	public void testGetAssignmentMethod() {
		assertThat(timezoneAvailability.getAssignmentMethod()).isEqualTo(AssignmentMethod.TIME_ZONE);
	}
	
	@Test
	public void testIsHolidayAvailability() {
		assertThat(timezoneAvailability.isHolidayAvailability()).isFalse();
	}
	
	@Test
	public void testIsMatchingWorkAvailability_timeZoneList_notMatch1() {
		
		// ExpectedTimeZone: 6:00~12:00 and 18:00~22:00
		
		List<TimeSpanForCalc> timeZoneList = Arrays.asList(
				new TimeSpanForCalc( new TimeWithDayAttr(780), new TimeWithDayAttr(1080)), // 13:00~18:00 not matching
				new TimeSpanForCalc( new TimeWithDayAttr(1320), new TimeWithDayAttr(1440)) // 22:00~24:00 not matching
				); 
		
        boolean result = timezoneAvailability.isMatchingWorkAvailability(require, null, timeZoneList);
        assertThat(result).isFalse();
	}
	
	@Test
	public void testIsMatchingWorkAvailability_timeZoneList_notMatch2() {
		
		// ExpectedTimeZone: 6:00~12:00 and 18:00~22:00
		
		List<TimeSpanForCalc> timeZoneList = Arrays.asList(
				new TimeSpanForCalc( new TimeWithDayAttr(480), new TimeWithDayAttr(560)), // 9:00~11:00 matching
				new TimeSpanForCalc( new TimeWithDayAttr(1320), new TimeWithDayAttr(1440))); // 22:00~24:00 not matching
		
        boolean result = timezoneAvailability.isMatchingWorkAvailability(require, null, timeZoneList);
        assertThat(result).isFalse();
	}
	
	@Test
	public void testIsMatchingWorkAvailability_timeZoneList_Match1() {
		
		// ExpectedTimeZone: 6:00~12:00 and 18:00~22:00
		
		List<TimeSpanForCalc> timeZoneList = Arrays.asList(
				new TimeSpanForCalc( new TimeWithDayAttr(480), new TimeWithDayAttr(560)), // 9:00~11:00 matching
				new TimeSpanForCalc( new TimeWithDayAttr(1080), new TimeWithDayAttr(1260))); // 18:00~21:00 matching
		
        boolean result = timezoneAvailability.isMatchingWorkAvailability(require, null, timeZoneList);
        assertThat(result).isTrue();
	}
	
	@Test
	public void testIsMatchingWorkAvailability_timeZoneList_Match2() {
		
		// ExpectedTimeZone: 6:00~12:00 and 18:00~22:00
		
		List<TimeSpanForCalc> timeZoneList = Arrays.asList(
				new TimeSpanForCalc( new TimeWithDayAttr(360), new TimeWithDayAttr(480)), // 6:00~8:00 matching
				new TimeSpanForCalc( new TimeWithDayAttr(560), new TimeWithDayAttr(720))); // 9:00~12:00 matching
		
        boolean result = timezoneAvailability.isMatchingWorkAvailability(require, null, timeZoneList);
        assertThat(result).isTrue();
	}
	
	@Test
	public void testGetDisplayInformation() {
		
		/*
		 *  Expected TimeZone
		 *  360~720   (6:00~12:00)
		 *  1080~1320 (18:00~22:00)
		 */
		
		WorkAvailabilityDisplayInfo displayInfo = timezoneAvailability.getDisplayInformation(require);
		
		assertThat(displayInfo.getMethod()).isEqualTo(AssignmentMethod.TIME_ZONE);
		assertThat(displayInfo.getNameList()).isEmpty();
		assertThat(displayInfo.getTimeZoneList())
			.extracting( 
				d -> d.startValue(),
				d -> d.endValue())
			.containsExactly(
					tuple( 360, 720),
					tuple(1080, 1320));
	}
	
	
}
