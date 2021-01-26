package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
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
	 * Pattern	: 集計対象の値がある
	 * Output	: 集計単位ごとに合計値が返る
	 * @param dlyAtd1 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd2 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd3 日別勤怠の勤怠時間(dummy)
	 */
	@Test
	public void test_totalize_valueIsNotEmpty(
			@Injectable AttendanceTimeOfDailyAttendance dlyAtd1
		,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd2
		,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd3
	) {

		// 集計単位リスト
		val targets = Arrays.asList(
						AttendanceTimesForAggregation.WORKING_WITHIN	// 就業時間
					,	AttendanceTimesForAggregation.WORKING_EXTRA		// 時間外時間
				);

		// Execute
		val result = AttendanceTimeTotalizationService
				.totalize(targets, Arrays.asList( dlyAtd1, dlyAtd2, dlyAtd3 ));

		// Assertion
		assertThat( result ).containsOnlyKeys( targets );

		result.entrySet().stream()
			.forEach( entry -> {
				assertThat( entry.getValue() ).isEqualTo(
						BigDecimal.ZERO
						.add( entry.getKey().getTime( dlyAtd1 ) )
						.add( entry.getKey().getTime( dlyAtd2 ) )
						.add( entry.getKey().getTime( dlyAtd3 ) )
					);
			} );

	}

}
