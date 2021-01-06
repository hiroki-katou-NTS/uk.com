package nts.uk.ctx.sys.assist.dom.status.adapter;

import java.util.List;
import java.util.Map;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.Imported.申請情報を取得する
 * @author DungDV
 *
 */
public interface ApplicaionInforAdapter {

	/**
	 * [1]申請情報を取得する
	 * @param a 社員IDリスト
	 * @param b 反映状態リスト
	 * @param c 期間
	 * return Map<社員ID、List<申請>>
	 */
	Map<String, List<String>> getAppInfor(List<String> empIds, List<String> reflections, DatePeriod period);
}
