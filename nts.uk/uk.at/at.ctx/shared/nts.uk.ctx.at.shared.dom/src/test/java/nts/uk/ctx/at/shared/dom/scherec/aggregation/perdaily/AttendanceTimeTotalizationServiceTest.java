package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import lombok.val;

/**
 * Test for AttendanceTimeTotalizationService
 * @author kumiko_otake
 *
 */
public class AttendanceTimeTotalizationServiceTest {

	/*
	 * Target	: totalize
	 * Pattern	: 集計対象の値がない
	 * Output	: 集計単位ごとに「0」が返る
	 */
	@Test
	public void test_totalize_valuesIsEmpty() {

		// 集計単位リスト
		val targets = Arrays.asList(AttendanceTimesForAggregation.values());

		// Execute
		val result = AttendanceTimeTotalizationService.totalize(targets, Collections.emptyList());

		// Assertion
		assertThat( result ).containsOnlyKeys( targets );
		assertThat( result.values() ).containsOnly( BigDecimal.ZERO );

	}

	/**
	 * Target	: totalize
	 */
	@Test
	public void test_totalizeTimes() {

		// 集計単位リスト
		val targets = Arrays.asList(
						AttendanceTimesForAggregation.WORKING_WITHIN	// 就業時間
					,	AttendanceTimesForAggregation.WORKING_EXTRA		// 時間外時間
				);
		// 値リスト
		val values = Arrays.asList(
						IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithTime( 480,  94 )
					,	IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithTime( 400,   0 )
					,	IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithTime( 450, 153 )
				);


		// Execute
		val result = AttendanceTimeTotalizationService.totalize( targets, values );


		// Assertion
		assertThat( result ).containsOnlyKeys( targets );

		result.entrySet()
			.forEach( entry -> {
				assertThat( entry.getValue() ).isEqualTo(
						BigDecimal.ZERO
						.add( entry.getKey().getTime( values.get(0) ) )
						.add( entry.getKey().getTime( values.get(1) ) )
						.add( entry.getKey().getTime( values.get(2) ) )
					);
			} );

	}

}
