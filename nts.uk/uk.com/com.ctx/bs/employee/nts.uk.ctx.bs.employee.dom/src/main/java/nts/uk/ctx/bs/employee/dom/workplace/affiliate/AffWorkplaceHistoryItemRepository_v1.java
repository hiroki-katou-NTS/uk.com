package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import java.util.List;

public interface AffWorkplaceHistoryItemRepository_v1 {
	/**
	 * ドメインモデル「所属職場」を新規登録する
	 * @param domain
	 */
	void addAffWorkplaceHistory(AffWorkplaceHistoryItem domain);
	/**
	 * ドメインモデル「所属職場」を削除する
	 * @param domain
	 */
	void deleteAffWorkplaceHistory(String histID);
	
	/**
	 * ドメインモデル「所属職場」を取得する
	 * @param domain
	 */
	void updateAffWorkplaceHistory(AffWorkplaceHistoryItem domain);
	
	List<AffWorkplaceHistoryItem> getAffWrkplaHistItemByEmpId(String employeeId);
}
