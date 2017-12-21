package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import java.util.List;
import java.util.Optional;

public interface AffWorkplaceHistoryItemRepository_v1 {
	/**
	 * ドメインモデル「所属職場」を新規登録する
	 * @param domain
	 */
	void add(AffWorkplaceHistoryItem domain);
	/**
	 * ドメインモデル「所属職場」を削除する
	 * @param domain
	 */
	void delete(String histID);
	
	/**
	 * ドメインモデル「所属職場」を取得する
	 * @param domain
	 */
	void update(AffWorkplaceHistoryItem domain);
	
	List<AffWorkplaceHistoryItem> getAffWrkplaHistItemByEmpId(String employeeId);
	
	Optional<AffWorkplaceHistoryItem> getByHistId(String historyId);
	
	List<AffWorkplaceHistoryItem> findByHistIds(List<String> hisIds);
	
	List<AffWorkplaceHistoryItem> findeByWplIDs(List<String> wplIDs);
}
