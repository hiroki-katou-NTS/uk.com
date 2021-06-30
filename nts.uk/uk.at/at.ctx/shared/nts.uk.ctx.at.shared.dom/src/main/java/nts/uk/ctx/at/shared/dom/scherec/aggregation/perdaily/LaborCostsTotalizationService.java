package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregationByTypeService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 人件費・時間を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.人件費・時間を集計する
 * @author kumiko_otake
 */
@Stateless
public class LaborCostsTotalizationService {

	/**
	 * 金額を集計する
	 * @param targets 集計単位リスト
	 * @param values 勤怠時間リスト
	 * @return 集計結果
	 */
	public static Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeAmounts(
				List<AggregationUnitOfLaborCosts> targets
			,	List<AttendanceTimeOfDailyAttendance> values
	) {
		return LaborCostsTotalizationService
				.totalize(targets, values, AggregationUnitOfLaborCosts::getAmount);
	}


	/**
	 * 時間を集計する
	 * @param targets 集計単位リスト
	 * @param values 勤怠時間リスト
	 * @return 集計結果
	 */
	public static Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeTimes(
				List<AggregationUnitOfLaborCosts> targets
			,	List<AttendanceTimeOfDailyAttendance> values
	) {
		return LaborCostsTotalizationService
				.totalize(targets, values, AggregationUnitOfLaborCosts::getTime);
	}


	/**
	 * 値を集計する
	 * @param targets 集計単位リスト
	 * @param values 勤怠時間リスト
	 * @param getValue 値取得処理
	 * @return 集計結果
	 */
	private static Map<AggregationUnitOfLaborCosts, BigDecimal> totalize(
				List<AggregationUnitOfLaborCosts> targets
			,	List<AttendanceTimeOfDailyAttendance> values
			,	BiFunction<AggregationUnitOfLaborCosts, AttendanceTimeOfDailyAttendance, BigDecimal> getValue
	) {

		// 値を取得する
		val times = values.stream()
				.map( v -> targets.stream().collect(Collectors.toMap( t -> t, t -> getValue.apply( t, v ) )))
				.collect(Collectors.toList());

		// 集計(合計)
		return AggregationByTypeService.totalize(targets, times);

	}

}
