package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregateValuesByType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * 社員属性別の人数を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.社員属性別の人数を集計する
 * @author kumiko_otake
 */
public class CountEmployeesByAttribute {

	/** 種類ごとに値を集計する **/
	@Inject
	AggregateValuesByType aggregateValuesByType;


	/**
	 * 集計する
	 * @param require Require
	 * @param unit 集計単位リスト
	 * @param values 勤務情報リスト
	 * @return
	 */
	// TODO 総称型
	public Map<String, BigDecimal> count(
				Require require
			,	AggregationUnitOfEmployeeAttribute unit
			,	List<WorkInfoWithAffiliationInfo> values
	) {

		// 属性を取得する
		val attributes = values.stream()
				// TODO 出勤系かどうかの判定処理
				.filter( e -> e.getWorkInfo().getWorkStyle(require).orElse( WorkStyle.ONE_DAY_REST ) != WorkStyle.ONE_DAY_REST )
				.map( e -> unit.getAttribute( e.getAffiliationInfo() ) )
				.collect(Collectors.toList());

		// 集計(カウント)
		return this.aggregateValuesByType.count(attributes);

	}


	public interface Require extends WorkInfoOfDailyAttendance.Require {
	}

}
