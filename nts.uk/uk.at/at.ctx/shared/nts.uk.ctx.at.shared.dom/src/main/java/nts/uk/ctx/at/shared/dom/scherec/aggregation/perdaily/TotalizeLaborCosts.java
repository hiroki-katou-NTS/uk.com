package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregateValuesByType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 人件費・時間を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.人件費・時間を集計する
 * @author kumiko_otake
 */
public class TotalizeLaborCosts {

	/** 種類ごとに値を集計する **/
	@Inject
	AggregateValuesByType aggregateValuesByType;


	/**
	 * 金額を集計する
	 * @param targets 集計単位リスト
	 * @param values 勤怠時間リスト
	 * @return 集計結果
	 */
	public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeAmounts(
				List<AggregationUnitOfLaborCosts> targets
			,	List<AttendanceTimeOfDailyAttendance> values
	) {
		return this.totalize(targets, values, AggregationUnitOfLaborCosts::getAmount);
	}


	/**
	 * 時間を集計する
	 * @param targets 集計単位リスト
	 * @param values 勤怠時間リスト
	 * @return 集計結果
	 */
	public Map<AggregationUnitOfLaborCosts, BigDecimal> totalizeTimes(
				List<AggregationUnitOfLaborCosts> targets
			,	List<AttendanceTimeOfDailyAttendance> values
	) {
		return this.totalize(targets, values, AggregationUnitOfLaborCosts::getTime);
	}


	/**
	 * 値を集計する
	 * @param targets 集計単位リスト
	 * @param values 勤怠時間リスト
	 * @param getValue 値取得処理
	 * @return 集計結果
	 */
	private Map<AggregationUnitOfLaborCosts, BigDecimal> totalize(
				List<AggregationUnitOfLaborCosts> targets
			,	List<AttendanceTimeOfDailyAttendance> values
			,	BiFunction<AggregationUnitOfLaborCosts, AttendanceTimeOfDailyAttendance, BigDecimal> getValue
	) {

		// 値を取得する
		val items = values.stream()
				.map( v -> targets.stream().collect(Collectors.toMap( t -> t, t -> getValue.apply( t, v ) )))
				.collect(Collectors.toList());

		// 集計(合計)
		return this.aggregateValuesByType.totalize(items);

	}

}
