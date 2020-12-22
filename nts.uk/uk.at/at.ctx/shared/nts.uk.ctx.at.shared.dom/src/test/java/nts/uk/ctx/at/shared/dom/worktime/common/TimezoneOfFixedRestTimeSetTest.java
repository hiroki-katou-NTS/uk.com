package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for TimezoneOfFixedRestTimeSet
 * @author kumiko_otake
 */
public class TimezoneOfFixedRestTimeSetTest {

	@Test
	public void testGetters() {
		val instance = new TimezoneOfFixedRestTimeSet(Arrays.asList(
						new DeductionTime(TimeWithDayAttr.hourMinute( 12,  0 ), TimeWithDayAttr.hourMinute( 12, 45 ))
					,	new DeductionTime(TimeWithDayAttr.hourMinute( 17, 30 ), TimeWithDayAttr.hourMinute( 18,  0 ))
				));
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: getRestTimezonesForCalc
	 */
	@Test
	public void testGetRestTimezonesForCalc() {

		// 時間帯
		val timezones = Arrays.asList(
						new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 12,  0 ), TimeWithDayAttr.hourMinute( 12, 45 ))
					,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 17, 30 ), TimeWithDayAttr.hourMinute( 18,  0 ))
				);

		// Execute
		val result = new TimezoneOfFixedRestTimeSet(
					timezones.stream()
						.map( e -> new DeductionTime( e.getStart(), e.getEnd() ))
						.collect(Collectors.toList())
				).getRestTimezonesForCalc();

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderElementsOf( timezones );

	}

}
