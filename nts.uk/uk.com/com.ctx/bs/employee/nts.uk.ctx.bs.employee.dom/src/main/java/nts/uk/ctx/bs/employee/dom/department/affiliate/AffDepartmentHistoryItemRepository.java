package nts.uk.ctx.bs.employee.dom.department.affiliate;

public interface AffDepartmentHistoryItemRepository {
	
	/**
	 * ドメインモデル「所属部門」を新規登録する
	 * @param domain
	 */
	void addAffDepartment(AffDepartmentHistoryItem domain);
	/**
	 * 取得した「所属部門」を更新する
	 * @param domain
	 */
	void updateAffDepartment(AffDepartmentHistoryItem domain);
	/**
	 * ドメインモデル「所属部門（兼務）」を削除する
	 * @param domain
	 */
	void deleteAffDepartment(String histId);
}
