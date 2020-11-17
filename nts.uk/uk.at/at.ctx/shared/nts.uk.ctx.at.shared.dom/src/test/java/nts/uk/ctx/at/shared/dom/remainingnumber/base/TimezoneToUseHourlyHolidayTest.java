package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class TimezoneToUseHourlyHolidayTest {

	/**
	 * Target	: getBeforeWorking
	 */
	@Test
	public void testGetBeforeWorking() {

		// Expected
		val expected = new HashMap<Integer, TimezoneToUseHourlyHoliday>();
		{
			expected.put( 1,	TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE );
			expected.put( 2,	TimezoneToUseHourlyHoliday.WORK_NO2_BEFORE );
			expected.put( 3,	TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE );
		}

		// Execute
		val result = expected.keySet().stream()
				.collect(Collectors.toMap( num -> num, num -> TimezoneToUseHourlyHoliday.getBeforeWorking(new WorkNo( num )) ));

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}


	/**
	 * Target	: getAfterWorking
	 */
	@Test
	public void testGetAfterWorking() {

		// Expected
		val expected = new HashMap<Integer, TimezoneToUseHourlyHoliday>();
		{
			expected.put( 1,	TimezoneToUseHourlyHoliday.WORK_NO1_AFTER );
			expected.put( 2,	TimezoneToUseHourlyHoliday.WORK_NO2_AFTER );
			expected.put( 3,	TimezoneToUseHourlyHoliday.WORK_NO1_AFTER );
		}

		// Execute
		val result = expected.keySet().stream()
				.collect(Collectors.toMap( num -> num, num -> TimezoneToUseHourlyHoliday.getAfterWorking(new WorkNo( num )) ));

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}
	
	/**
	 * Target	: getOutingReason
	 */
	@Test
	public void testGetOutingReason_exception() {
		
		NtsAssert.systemError(
				() -> TimezoneToUseHourlyHoliday.getOutingReason(GoingOutReason.PUBLIC));
	}
	
	/**
	 * Target	: getOutingReason
	 */
	@Test
	public void testGetOutingReason() {

		// Expected
		val expected = new HashMap<GoingOutReason, TimezoneToUseHourlyHoliday>();
		{
			expected.put( GoingOutReason.PRIVATE,	TimezoneToUseHourlyHoliday.GOINGOUT_PRIVATE );
			expected.put( GoingOutReason.UNION,	TimezoneToUseHourlyHoliday.GOINGOUT_UNION );
			expected.put( GoingOutReason.UNION,	TimezoneToUseHourlyHoliday.GOINGOUT_UNION );
		}

		// Execute
		val result = expected.keySet().stream()
				.collect(Collectors.toMap( 
						reason -> reason, 
						reason -> TimezoneToUseHourlyHoliday.getOutingReason(reason)));

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}
	
}
