package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AffJobTitleHistoryItemRepository_v1 {
	
	Optional<AffJobTitleHistoryItem> getByEmpIdAndReferDate(String employeeId, GeneralDate referDate);
	
	/**
	 * ドメインモデル「職務職位」を新規登録する
	 * 
	 * @param domain
	 */
	void add(AffJobTitleHistoryItem domain);

	/**
	 * 取得した「職務職位」を更新する
	 * 
	 * @param domain
	 */
	void update(AffJobTitleHistoryItem domain);

	/**
	 * ドメインモデル「職務職位」を削除する
	 * 
	 * @param jobTitleMainId
	 */
	void delete(String jobTitleMainId);
}
