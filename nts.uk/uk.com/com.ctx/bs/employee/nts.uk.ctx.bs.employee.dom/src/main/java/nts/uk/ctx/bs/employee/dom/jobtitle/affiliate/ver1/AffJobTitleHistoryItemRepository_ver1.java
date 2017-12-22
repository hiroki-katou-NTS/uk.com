package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AffJobTitleHistoryItemRepository_ver1 {
	
	/**
	 * @param employeeId
	 * @param referDate
	 * @return
	 */
	Optional<AffJobTitleHistoryItem_ver1> getByEmpIdAndReferDate(String employeeId, GeneralDate referDate);
	
	/**
	 * find with primary key
	 * @param historyId
	 * @return
	 */
	Optional<AffJobTitleHistoryItem_ver1> findByHitoryId(String historyId);
	
	/**
	 * ドメインモデル「職務職位」を新規登録する
	 * 
	 * @param domain
	 */
	void add(AffJobTitleHistoryItem_ver1 domain);

	/**
	 * 取得した「職務職位」を更新する
	 * 
	 * @param domain
	 */
	void update(AffJobTitleHistoryItem_ver1 domain);

	/**
	 * ドメインモデル「職務職位」を削除する
	 * 
	 * @param jobTitleMainId
	 */
	void delete(String jobTitleMainId);
	
	List<AffJobTitleHistoryItem_ver1> getByJobIdAndReferDate(String jobId, GeneralDate referDate);
	
	List<AffJobTitleHistoryItem_ver1> getAllBySid(String sid);
	
	List<AffJobTitleHistoryItem_ver1> getAllByListSidDate(List<String> lstSid, GeneralDate referDate);
}
