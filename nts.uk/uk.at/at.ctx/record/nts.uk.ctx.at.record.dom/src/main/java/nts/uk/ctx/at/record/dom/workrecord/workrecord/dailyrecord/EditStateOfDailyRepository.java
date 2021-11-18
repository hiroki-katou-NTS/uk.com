package nts.uk.ctx.at.record.dom.workrecord.workrecord.dailyrecord;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 時間帯別勤怠の削除一覧Repository
 * @author tutt
 *
 */
public interface EditStateOfDailyRepository {
	/**
	 * 	[1] 登録する
	 * @param sId 社員ID
	 * @param ymd 年月日
	 * @param itemIdList 勤怠項目ID一覧
	 */
	void deleteByListItemId(String sId, GeneralDate ymd, List<Integer> itemIdList);
}
