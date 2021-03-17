package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregationByTypeService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 勤怠時間を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.勤怠時間を集計する
 * @author kumiko_otake
 */
@Stateless
public class AttendanceTimeTotalizationService {

	/**
	 * 集計する
	 * @param targets 集計対象リスト
	 * @param values 勤怠時間リスト
	 * @return 集計結果
	 */
	public static Map<AttendanceTimesForAggregation, BigDecimal> totalize(
				List<AttendanceTimesForAggregation> targets
			,	List<AttendanceTimeOfDailyAttendance> values
	) {

		// 値を取得
		val times = values.stream()
						.map( e -> AttendanceTimeTotalizationService.getTargetTimes(targets, e) )
						.collect(Collectors.toList());
		// 集計(合計)
		return AggregationByTypeService.totalize( targets, times );

	}

	/**
	 * 集計対象の値を取得する
	 * @param targets 集計対象リスト
	 * @param value 勤怠時間
	 * @return 集計対象の値
	 */
	private static Map<AttendanceTimesForAggregation, BigDecimal> getTargetTimes(
				List<AttendanceTimesForAggregation> targets
			,	AttendanceTimeOfDailyAttendance value
	) {

		return targets.stream()
			.collect(Collectors.toMap( e -> e, e -> e.getTime( value ) ));

	}

}
