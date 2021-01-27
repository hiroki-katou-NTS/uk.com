package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * Test for LaborCostsTotalizationService
 * @author kumiko_otake
 */
public class LaborCostsTotalizationServiceTest {

	/**
	 * Target	: totalizeAmounts
	 * @param dlyAtd1 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd2 日別勤怠の勤怠時間(dummy)
	 * @param dlyAtd3 日別勤怠の勤怠時間(dummy)
	 */
	@Test
	public void test_totalizeAmounts(
			@Injectable AttendanceTimeOfDailyAttendance dlyAtd1
		,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd2
		,	@Injectable AttendanceTimeOfDailyAttendance dlyAtd3
	) {

		// Exceptations
		// 値対応リスト
		val dummyValueMap = new HashMap<AttendanceTimeOfDailyAttendance, Double>() {{
			put( dlyAtd1, 30000.4 );
			put( dlyAtd2, 2185.0 );
			put( dlyAtd3, 52163.9 );
		}};
		// 係数
		val coefficients = new HashMap<AggregationUnitOfLaborCosts, Double>() {{
			put( AggregationUnitOfLaborCosts.WITHIN, 1.25 );
			put( AggregationUnitOfLaborCosts.EXTRA, 3.3);
			put( AggregationUnitOfLaborCosts.TOTAL, 16.0 );
		}};

		// Mock: 値取得処理
		new MockUp<AggregationUnitOfLaborCosts>() {
			@Mock
			public BigDecimal getAmount(AttendanceTimeOfDailyAttendance dlyAtd) {
				// ダミー値×係数
				return BigDecimal.valueOf( dummyValueMap.get(dlyAtd) * coefficients.get( this.getMockInstance() ) );
			}
		};


		// 集計単位リスト
		val targets = Arrays.asList(AggregationUnitOfLaborCosts.values());
		// 値リスト
		val values = dummyValueMap.keySet().stream().collect(Collectors.toList());

		// Execute
		val result = LaborCostsTotalizationService.totalizeAmounts( targets, values );


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


	/**
	 * Target	: totalizeTimes
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
		val coefficients = new HashMap<AggregationUnitOfLaborCosts, Double>() {{
			put( AggregationUnitOfLaborCosts.WITHIN, 1.25 );
			put( AggregationUnitOfLaborCosts.EXTRA, 3.3);
			put( AggregationUnitOfLaborCosts.TOTAL, 16.0 );
		}};

		// Mock: 値取得処理
		new MockUp<AggregationUnitOfLaborCosts>() {
			@Mock
			public BigDecimal getTime(AttendanceTimeOfDailyAttendance dlyAtd) {
				// ダミー値×係数
				return BigDecimal.valueOf( dummyValueMap.get(dlyAtd) * coefficients.get( this.getMockInstance() ) );
			}
		};


		// 集計単位リスト
		val targets = Arrays.asList(AggregationUnitOfLaborCosts.values());
		// 値リスト
		val values = dummyValueMap.keySet().stream().collect(Collectors.toList());

		// Execute
		val result = LaborCostsTotalizationService.totalizeTimes( targets, values );


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

		// Exceptations
		// 値対応リスト
		val dummyValueMap = new HashMap<AttendanceTimeOfDailyAttendance, Integer>() {{
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

		// Mock: 値取得処理定義
		val getValueFunction = new BiFunction<AggregationUnitOfLaborCosts, AttendanceTimeOfDailyAttendance, BigDecimal>() {
			@Override
			public BigDecimal apply( AggregationUnitOfLaborCosts unit, AttendanceTimeOfDailyAttendance dlyAtd ) {
				// ダミー値×係数
				return BigDecimal.valueOf( dummyValueMap.get(dlyAtd) * coefficients.get(unit) );
			}
		};


		// 集計単位リスト
		val targets = Arrays.asList(
						AggregationUnitOfLaborCosts.TOTAL	// 合計
					,	AggregationUnitOfLaborCosts.EXTRA	// 時間外時間
				);
		// 値リスト
		val values = dummyValueMap.keySet().stream().collect(Collectors.toList());

		// Execute
		Map<AggregationUnitOfLaborCosts, BigDecimal> result = NtsAssert.Invoke.privateMethod(
						new LaborCostsTotalizationService()
					,	"totalize"
					,	targets, values, getValueFunction
				);


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
