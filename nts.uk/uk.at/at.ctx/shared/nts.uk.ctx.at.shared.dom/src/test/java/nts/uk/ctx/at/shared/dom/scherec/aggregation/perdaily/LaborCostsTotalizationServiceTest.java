package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * Test for LaborCostsTotalizationService
 * @author kumiko_otake
 */
public class LaborCostsTotalizationServiceTest {

	/*
	 * [メモ] 以下のメソッドはコードレビューにて確認
	 * ・金額を集計する - totalizeAmounts
	 * ・時間を集計する - totalizeTimes
	 */

	/*
	 * Target	: [private] totalize
	 * Pattern	: 集計対象の値がない
	 * Output	: 集計単位ごとに「0」が返る
	 */
	@Test
	public void test_private_totalize_valuesIsEmpty() {

		// 集計単位リスト
		val targets = Arrays.asList(AggregationUnitOfLaborCosts.values());
		// 値取得処理定義
		val getValueFunction = new BiFunction<AggregationUnitOfLaborCosts, AttendanceTimeOfDailyAttendance, BigDecimal>() {
			@Override
			public BigDecimal apply( AggregationUnitOfLaborCosts unit, AttendanceTimeOfDailyAttendance dlyAtd ) {
				// 常に「1」を返す
				return BigDecimal.ONE;
			}
		};

		// Execute
		Map<AggregationUnitOfLaborCosts, BigDecimal> result = NtsAssert.Invoke.privateMethod(
						new LaborCostsTotalizationService()
					,	"totalize"
						,	targets
						,	Collections.emptyList()
						,	getValueFunction
				);

		// Assertion
		assertThat( result ).containsOnlyKeys( targets );
		assertThat( result.values() ).containsOnly( BigDecimal.ZERO );

	}

	/**
	 * Target	: [private] totalize
	 * Pattern	: 集計対象の値がある
	 * Output	: 集計単位ごとに合計値が返る
	 * @param dlyAtd1 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd2 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd3 日別勤怠の勤怠時間(dummy)
	 */
	@Test
	public void test_private_totalize_valueIsNotEmpty(
				@Injectable AttendanceTimeOfDailyAttendance dlyAtd1
			,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd2
			,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd3
	) {

		// 集計単位リスト
		val targets = Arrays.asList(
						AggregationUnitOfLaborCosts.TOTAL	// 合計
					,	AggregationUnitOfLaborCosts.EXTRA	// 時間外時間
				);
		// 値リスト
		val dummyValues = new HashMap<AttendanceTimeOfDailyAttendance, Integer>() {{
			put( dlyAtd1, 1 );
			put( dlyAtd2, 5 );
			put( dlyAtd3, 2 );
		}};
		// 係数
		val coefficients = new HashMap<AggregationUnitOfLaborCosts, Double>() {{
			put( AggregationUnitOfLaborCosts.WITHIN, 1.0 );
			put( AggregationUnitOfLaborCosts.EXTRA, 0.25);
			put( AggregationUnitOfLaborCosts.TOTAL, 1.5 );
		}};
		// 値取得処理定義
		val getValueFunction = new BiFunction<AggregationUnitOfLaborCosts, AttendanceTimeOfDailyAttendance, BigDecimal>() {
			@Override
			public BigDecimal apply( AggregationUnitOfLaborCosts unit, AttendanceTimeOfDailyAttendance dlyAtd ) {
				// 値×係数
				return BigDecimal.valueOf( dummyValues.get(dlyAtd) * coefficients.get(unit) );
			}
		};

		// Execute
		Map<AggregationUnitOfLaborCosts, BigDecimal> result = NtsAssert.Invoke.privateMethod(
						new LaborCostsTotalizationService()
					,	"totalize"
						,	targets
						,	Arrays.asList( dlyAtd1, dlyAtd2, dlyAtd3 )
						,	getValueFunction
				);

		// Assertion
		assertThat( result ).containsOnlyKeys( targets );

		assertThat( result.get(AggregationUnitOfLaborCosts.EXTRA) )
			.isEqualTo(BigDecimal.ZERO
					.add(BigDecimal.valueOf( 1 * 0.25 ))
					.add(BigDecimal.valueOf( 5 * 0.25 ))
					.add(BigDecimal.valueOf( 2 * 0.25 ))
				);
		assertThat( result.get(AggregationUnitOfLaborCosts.TOTAL) )
			.isEqualTo(BigDecimal.ZERO
					.add(BigDecimal.valueOf( 1 * 1.5 ))
					.add(BigDecimal.valueOf( 5 * 1.5 ))
					.add(BigDecimal.valueOf( 2 * 1.5 ))
				);

	}

}
