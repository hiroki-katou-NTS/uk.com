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
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * Test for LaborCostsTotalizationService
 * @author kumiko_otake
 */
public class LaborCostsTotalizationServiceTest {

	/**
	 * Target	: totalizeAmounts
	 */
	@Test
	public void test_totalizeAmounts() {

		// 集計単位リスト
		val targets = Arrays.asList(AggregationUnitOfLaborCosts.values());
		// 値リスト
		val values = Arrays.asList(
						IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithAmount( 48791,  3995 )
					,	IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithAmount( 65539, 12163 )
					,	IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithAmount( 16162,  2185 )
				);

		// Execute
		val result = LaborCostsTotalizationService.totalizeAmounts( targets, values );


		// Assertion
		assertThat( result ).containsOnlyKeys( targets );

		result.entrySet()
			.forEach( entry -> {
				assertThat( entry.getValue() ).isEqualTo(
						BigDecimal.ZERO
						.add( entry.getKey().getAmount( values.get(0) ) )
						.add( entry.getKey().getAmount( values.get(1) ) )
						.add( entry.getKey().getAmount( values.get(2) ) )
					);
			} );

	}


	/**
	 * Target	: totalizeTimes
	 */
	@Test
	public void test_totalizeTimes() {

		// 集計単位リスト
		val targets = Arrays.asList(AggregationUnitOfLaborCosts.values());
		// 値リスト
		val values = Arrays.asList(
						IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithAmount( 1289,  291 )
					,	IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithAmount(  863,  209 )
					,	IntegrationOfDailyHelperInScheRec.AtdTimeHelper.createWithAmount(  960,   29 )
				);

		// Execute
		val result = LaborCostsTotalizationService.totalizeTimes( targets, values );


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
		val dummyValueMap = new HashMap<AttendanceTimeOfDailyAttendance, Integer>();
		{
			dummyValueMap.put( dlyAtd1, 1 );
			dummyValueMap.put( dlyAtd2, 5 );
			dummyValueMap.put( dlyAtd3, 2 );
		};
		// 係数
		val coefficients = new HashMap<AggregationUnitOfLaborCosts, Double>();
		{
			coefficients.put( AggregationUnitOfLaborCosts.WITHIN, 1.0 );
			coefficients.put( AggregationUnitOfLaborCosts.EXTRA, 0.25);
			coefficients.put( AggregationUnitOfLaborCosts.TOTAL, 1.5 );
		};

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
