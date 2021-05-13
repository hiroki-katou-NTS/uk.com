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
		val expected = new HashMap<AggregationUnitOfLaborCosts, BigDecimal>();
		{
			expected.put( AggregationUnitOfLaborCosts.WITHIN,	BigDecimal.valueOf(	619270	) );
			expected.put( AggregationUnitOfLaborCosts.EXTRA,	BigDecimal.valueOf(	  3497	) );
			expected.put( AggregationUnitOfLaborCosts.TOTAL,	BigDecimal.ZERO
							.add( expected.get(AggregationUnitOfLaborCosts.WITHIN) )
							.add( expected.get(AggregationUnitOfLaborCosts.EXTRA) )
					);
		}


		// 日別勤怠の勤怠時間
		val dailyAttendance = IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithAmount(
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
