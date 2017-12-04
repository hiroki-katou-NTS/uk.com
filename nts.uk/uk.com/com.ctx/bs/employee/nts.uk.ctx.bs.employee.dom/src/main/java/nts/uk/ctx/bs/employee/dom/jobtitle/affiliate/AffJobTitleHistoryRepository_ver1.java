package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

public interface AffJobTitleHistoryRepository_ver1 {
	
	Optional<AffJobTitleHistory_ver1> getByHistoryId(String historyId);
	
	// get listbypid
	Optional<AffJobTitleHistory_ver1> getListBySid(String sid);

	/**
	 * ドメインモデル「職務職位」を新規登録する
	 * 
	 * @param item
	 * @param sid
	 */
	void add(String sid, DateHistoryItem item);

	/**
	 * 取得した「職務職位」を更新する
	 * 
	 * @param item
	 */
	void update(DateHistoryItem item);

	/**
	 * ドメインモデル「職務職位」を削除する
	 * 
	 * @param sid
	 */
	void delete(String sid);
}
