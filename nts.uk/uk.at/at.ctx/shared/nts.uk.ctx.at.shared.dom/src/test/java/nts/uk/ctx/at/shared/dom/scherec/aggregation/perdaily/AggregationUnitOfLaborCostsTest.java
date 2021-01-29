package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;

/**
 * Test for AggregationUnitOfLaborCosts
 * @author kumiko_otake
 */
public class AggregationUnitOfLaborCostsTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(AggregationUnitOfLaborCosts.class);
	}


	/**
	 * Target	: getAmount
	 */
	@Test
	public void test_getAmount() {

		// 期待値
		val expected = new HashMap<AggregationUnitOfLaborCosts, Integer>() {{
			put( AggregationUnitOfLaborCosts.WITHIN,	619270 );
			put( AggregationUnitOfLaborCosts.EXTRA,		  3497 );
			put( AggregationUnitOfLaborCosts.TOTAL
					, get(AggregationUnitOfLaborCosts.WITHIN) + get(AggregationUnitOfLaborCosts.EXTRA) );
		}}.entrySet().stream()
		.collect(Collectors.toMap(Map.Entry::getKey, e -> BigDecimal.valueOf( e.getValue() ) ));


		// 日別勤怠の勤怠時間
		val dailyAttendance = AttendanceTimeOfDailyAttendanceHelper.createWithAmount(
						expected.get(AggregationUnitOfLaborCosts.WITHIN).intValue()
					,	expected.get(AggregationUnitOfLaborCosts.EXTRA).intValue()
				);

		// Execute
		val result = Stream.of( AggregationUnitOfLaborCosts.values() )
				.collect(Collectors.toMap( e -> e , e -> e.getAmount( dailyAttendance ) ));


		// assertion
		assertThat( result ).hasSameSizeAs( expected );
		result.entrySet().stream()
			.forEach( entry -> {
				assertThat( entry.getValue() ).isEqualTo( expected.get( entry.getKey() ) );
			} );

	}


}
