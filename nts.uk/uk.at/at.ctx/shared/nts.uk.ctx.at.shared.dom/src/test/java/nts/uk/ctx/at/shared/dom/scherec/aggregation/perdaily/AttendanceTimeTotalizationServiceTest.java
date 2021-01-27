package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

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
	 * @param dlyAtd1 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd2 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd3 日別勤怠の勤怠時間(dummy)
	 */
	@Test
	public void test_totalizeTimes(
			@Injectable AttendanceTimeOfDailyAttendance dlyAtd1
		,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd2
		,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd3
	) {

		// Exceptations
		// 値対応リスト
		val dummyValueMap = new HashMap<AttendanceTimeOfDailyAttendance, Integer>() {{
			put( dlyAtd1, 24 );
			put( dlyAtd2, 16 );
			put( dlyAtd3, 3 );
		}};
		// 係数
		val coefficients = new HashMap<AttendanceTimesForAggregation, Double>() {{
			put( AttendanceTimesForAggregation.WORKING_WITHIN, 1.25 );
			put( AttendanceTimesForAggregation.WORKING_EXTRA, 3.3);
			put( AttendanceTimesForAggregation.WORKING_TOTAL, 16.0 );
		}};

		// Mock: 値取得処理
		new MockUp<AttendanceTimesForAggregation>() {
			@Mock
			public BigDecimal getTime(AttendanceTimeOfDailyAttendance dlyAtd) {
				// ダミー値×係数
				return BigDecimal.valueOf( dummyValueMap.get(dlyAtd) * coefficients.get( this.getMockInstance() ) );
			}
		};


		// 集計単位リスト
		val targets = Arrays.asList(
						AttendanceTimesForAggregation.WORKING_WITHIN	// 就業時間
					,	AttendanceTimesForAggregation.WORKING_EXTRA		// 時間外時間
				);
		// 値リスト
		val values = dummyValueMap.keySet().stream().collect(Collectors.toList());

		// Execute
		val result = AttendanceTimeTotalizationService.totalize( targets, values );


		// Assertion
		assertThat( result ).containsOnlyKeys( targets );

		result.entrySet()
			.forEach( entry -> {
				val coefficient = coefficients.get( entry.getKey() );
				assertThat( entry.getValue() ).isEqualTo(
						BigDecimal.ZERO
						.add( BigDecimal.valueOf( dummyValueMap.get( dlyAtd1 ) * coefficient ) )
						.add( BigDecimal.valueOf( dummyValueMap.get( dlyAtd2 ) * coefficient ) )
						.add( BigDecimal.valueOf( dummyValueMap.get( dlyAtd3 ) * coefficient ) )
					);
			} );

	}

}
