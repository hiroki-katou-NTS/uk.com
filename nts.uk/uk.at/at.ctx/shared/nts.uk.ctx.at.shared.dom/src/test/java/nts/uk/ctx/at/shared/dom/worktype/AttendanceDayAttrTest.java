package nts.uk.ctx.at.shared.dom.worktype;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;

/**
 * Test for AttendanceDayAttr
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class AttendanceDayAttrTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(AttendanceDayAttr.class);
	}


	/**
	 * Target	: isHolidayWork
	 */
	@Test
	public void testIsHolidayWork() {

		// Execute
		val result = Stream.of( AttendanceDayAttr.values() )
						.collect(Collectors.toMap( e -> e,  e -> e.isHolidayWork() ));

		// Assertion
		assertThat( result.get(AttendanceDayAttr.HOLIDAY_WORK) ).isTrue();
		assertThat( result.entrySet() )
			.filteredOn( e -> e.getKey() != AttendanceDayAttr.HOLIDAY_WORK )
			.extracting( d -> d.getValue() )
			.containsOnly( false );

	}

	/**
	 * Target	: isHoliday
	 */
	@Test
	public void testIsHoliday() {

		// Execute
		val result = Stream.of( AttendanceDayAttr.values() )
						.collect(Collectors.toMap( e -> e,  e -> e.isHoliday() ));

		// Assertion
		assertThat( result.get(AttendanceDayAttr.HOLIDAY) ).isTrue();
		assertThat( result.entrySet() )
			.filteredOn( e -> e.getKey() != AttendanceDayAttr.HOLIDAY )
			.extracting( d -> d.getValue() )
			.containsOnly( false );

	}

	/**
	 * Target	: toAmPmAtr
	 */
	@Test
	public void testToAmPmAtr() {

		// Expected
		val expected = new HashMap<AttendanceDayAttr, Optional<AmPmAtr>>();
		{
			expected.put( AttendanceDayAttr.FULL_TIME,		Optional.of(AmPmAtr.ONE_DAY) );
			expected.put( AttendanceDayAttr.HOLIDAY_WORK,	Optional.of(AmPmAtr.ONE_DAY) );

			expected.put( AttendanceDayAttr.HALF_TIME_AM,	Optional.of(AmPmAtr.AM) );
			expected.put( AttendanceDayAttr.HALF_TIME_PM,	Optional.of(AmPmAtr.PM) );

			expected.put( AttendanceDayAttr.HOLIDAY,		Optional.empty() );
		}

		// Execute
		val result = Stream.of( AttendanceDayAttr.values() )
						.collect(Collectors.toMap( e -> e , e -> e.toAmPmAtr() ));

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );
	}

}
