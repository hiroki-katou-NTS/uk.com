package nts.uk.ctx.bs.employee.dom.department.affiliate;

import java.util.Optional;

public interface AffDepartmentHistoryItemRepository {
	
	Optional<AffDepartmentHistoryItem> getByHistId(String historyId);
	
	/**
	 * ドメインモデル「所属部門」を新規登録する
	 * @param domain
	 */
	void add(AffDepartmentHistoryItem domain);
	/**
	 * 取得した「所属部門」を更新する
	 * @param domain
	 */
	void update(AffDepartmentHistoryItem domain);
	/**
	 * ドメインモデル「所属部門（兼務）」を削除する
	 * @param domain
	 */
	void delete(String histId);
}
