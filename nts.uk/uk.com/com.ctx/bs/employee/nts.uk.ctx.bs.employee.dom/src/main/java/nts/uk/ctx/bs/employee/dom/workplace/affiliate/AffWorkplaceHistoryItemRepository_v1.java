package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AffWorkplaceHistoryItemRepository_v1 {
	/**
	 * ドメインモッ�「所属�場」を新規登録する
	 * @param domain
	 */
	void add(AffWorkplaceHistoryItem domain);
	/**
	 * ドメインモッ�「所属�場」を削除する
	 * @param domain
	 */
	void delete(String histID);
	
	/**
	 * ドメインモッ�「所属�場」を取得す�
	 * @param domain
	 */
	void update(AffWorkplaceHistoryItem domain);
	
	List<AffWorkplaceHistoryItem> getAffWrkplaHistItemByEmpId(String employeeId);
	
	Optional<AffWorkplaceHistoryItem> getByHistId(String historyId);
	
	List<AffWorkplaceHistoryItem> getAffWrkplaHistItemByListEmpIdAndDate(GeneralDate basedate, List<String> employeeId);
	
	List<AffWorkplaceHistoryItem> getAffWrkplaHistItemByListWkpIdAndDate(GeneralDate basedate, List<String> workplaceId);
	
	List<AffWorkplaceHistoryItem> getAffWrkplaHistItemByEmpIdAndDate(GeneralDate basedate, String employeeId);
	
	List<AffWorkplaceHistoryItem> findByHistIds(List<String> hisIds);
	
	List<AffWorkplaceHistoryItem> findeByWplIDs(List<String> wplIDs);
}
