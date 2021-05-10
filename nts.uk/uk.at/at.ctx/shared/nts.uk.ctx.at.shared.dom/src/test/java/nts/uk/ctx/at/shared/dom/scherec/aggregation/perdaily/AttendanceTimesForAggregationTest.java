package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;

/**
 * Test for AttendanceTimesForAggregation
 * @author kumiko_otake
 */
public class AttendanceTimesForAggregationTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(AttendanceTimesForAggregation.class);
	}


	/**
	 * Target	: getTime
	 */
	@Test
	public void test_getTime() {

		// 期待値
		val expected = new HashMap<AttendanceTimesForAggregation, BigDecimal>();
		{
			expected.put( AttendanceTimesForAggregation.WORKING_WITHIN,	BigDecimal.valueOf(	450	) );
			expected.put( AttendanceTimesForAggregation.WORKING_EXTRA,	BigDecimal.valueOf(	131	) );
			expected.put( AttendanceTimesForAggregation.WORKING_TOTAL,	BigDecimal.ZERO
							.add( expected.get(AttendanceTimesForAggregation.WORKING_WITHIN) )
							.add( expected.get(AttendanceTimesForAggregation.WORKING_EXTRA) )
					);
			// TODO 日別勤怠の医療項目決定待ち
//			expected.put( AttendanceTimesForAggregation.NIGHTSHIFT, 	BigDecimal.valueOf(	960	) );
		}


		// 日別勤怠の勤怠時間
		val dailyAttendance = IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithTime(
						expected.get(AttendanceTimesForAggregation.WORKING_WITHIN).intValue()
					,	expected.get(AttendanceTimesForAggregation.WORKING_EXTRA).intValue()
				);

		// Execute
		val result = Stream.of( AttendanceTimesForAggregation.values() )
				.filter( e -> e != AttendanceTimesForAggregation.NIGHTSHIFT )	// TODO 日別勤怠の医療項目決定待ち
				.collect(Collectors.toMap( e -> e , e -> e.getTime( dailyAttendance ) ));


		// assertion
		assertThat( result ).hasSameSizeAs( expected );
		result.entrySet().stream()
			.forEach( entry -> {
				assertThat( entry.getValue() ).isEqualTo( expected.get( entry.getKey() ) );
			} );

	}
}
