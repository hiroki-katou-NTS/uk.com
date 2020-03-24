package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

/**
 * DS : 打刻機能が利用できるか
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻機能が利用できるか
 * @author tutk
 *
 */
public class StampFunctionAvailableService {
	
	/**
	 * 	[1] 判断する
	 * @param require
	 * @param employeeId
	 */
	public static boolean decide(Require require,String employeeId) {
		
		List<StampCard> data = require.getListStampCard(employeeId);
		
		return !data.isEmpty();
	}
	
	
	public static interface Require {
		/**
		 * 	[R-1] 打刻カード番号を取得する   StampCardRepository
		 * @param sid
		 * @return
		 */
		List<StampCard> getListStampCard(String sid);
	}

}
