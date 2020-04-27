package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * DS: 打刻カード未登録の最新打刻データを取得する
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻カード未登録の最新打刻データを取得する
 * 
 * @author chungnt
 *
 */

public class GetNewestStampNotRegisteredService {

	/**
	 * 	[1] 取得する
	 * @param require
	 * @param period
	 * @return
	 */
	
	public Optional<GeneralDateTime> get(Require require,  DatePeriod period){
		
		//	$打刻情報リスト = 打刻カード未登録の打刻データを取得する#取得する(require, 期間)		
		List<StampInfoDisp> list = RetrieveNoStampCardRegisteredService.get(require, period);
		
		if (list.isEmpty()) {
			return null;
		}
		
		Optional<GeneralDateTime> stampDatetime = list.stream().map(x -> x.getStampDatetime()).sorted(Comparator.reverseOrder()).findFirst();
		return stampDatetime;
		
	}
	
	public static interface Require extends RetrieveNoStampCardRegisteredService.Require  {
	
	}
	
}
