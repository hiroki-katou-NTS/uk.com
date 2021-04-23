package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregationByTypeService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * 勤務方法別の人数を集計する	
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.勤務方法別の人数を集計する
 * @author dan_pv
 *
 */
public class NumberOfEmployeesByWorkMethodCountingService {
	
	/**
	 * 集計する
	 * @param require
	 * @param unit 集計単位
	 * @param workInfoList 勤務情報リスト
	 * @return
	 */
	public static Map<String, BigDecimal> count(
			Require require,
			AggregationUnitOfWorkMethod unit,
			List<WorkInfoOfDailyAttendance> workInfoList
			) {
		
		List<String> workMethodList = workInfoList.stream()
				.map( workInfo -> unit.getWorkMethod( require, workInfo) )
				.flatMap( OptionalUtil::stream )
				.collect( Collectors.toList() );
		
		return AggregationByTypeService.count( workMethodList );
	}
	
	public static interface Require extends AggregationUnitOfWorkMethod.Require {
		
	}

}
