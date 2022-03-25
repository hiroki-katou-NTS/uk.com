package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregationByTypeService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * 社員属性別の人数を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.社員属性別の人数を集計する
 * @author kumiko_otake
 */
@Stateless
public class NumberOfEmployeesByAttributeCountingService {

	/**
	 * 集計する
	 * @param require Require
	 * @param companyId 会社ID
	 * @param unit 集計単位
	 * @param infoList 勤務情報リスト
	 * @return
	 */
	public static Map<String, BigDecimal> count(
				Require require,
				String companyId
			,	AggregationUnitOfEmployeeAttribute unit
			,	List<WorkInfoWithAffiliationInfo> infoList
	) {

		// 属性別の勤務状況を取得する
		List<AttributeToBeCounted<String>> workingStatusList = infoList.stream()
				.map( info -> {
					return new AttributeToBeCounted<String>(
								unit.getAttribute( info.getAffiliationInfo() )
							,	info.getWorkInfo().isAttendanceRate( require, companyId )
						);
				} ).collect(Collectors.toList());


		// カウント対象の属性を取得する
		val targets = workingStatusList.stream()
				.map( AttributeToBeCounted::getAttribute ).distinct()
				.collect(Collectors.toList());
		// カウント対象の勤怠(＝出勤)をフィルタする
		val attributes = workingStatusList.stream()
				.filter( AttributeToBeCounted::isIncluded )
				.map( AttributeToBeCounted::getAttribute )
				.collect(Collectors.toList());

		return AggregationByTypeService.count(targets, attributes);

	}


	public static interface Require extends WorkInfoOfDailyAttendance.Require {
	}

}
