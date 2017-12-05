package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class TempAbsHistoryService {
	@Inject
	private TempAbsHistRepository temporaryAbsenceHistRepository;
	
	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * @param domain
	 */
	public void add(TempAbsenceHistory domain){
		if (domain.getDateHistoryItems().isEmpty()) {
			return;
		}
		// Insert last element
		DateHistoryItem lastItem = domain.getDateHistoryItems().get(domain.getDateHistoryItems().size() - 1);
		temporaryAbsenceHistRepository.add(domain.getCompanyId(), domain.getEmployeeId(), lastItem);
		
		// Update item before and after
		updateItemBefore(domain, lastItem);
	}
	/**
	 * 取得した「休職休業」を更新する
	 * @param domain
	 */
	public void update(TempAbsenceHistory domain, DateHistoryItem item){
		temporaryAbsenceHistRepository.update(item);
		// Update item before and after
		updateItemBefore(domain, item);
		updateItemAfter(domain, item);
	}
	
	/**
	 * ドメインモデル「休職休業」を削除する
	 * @param domain
	 */
	public void delete(TempAbsenceHistory domain, DateHistoryItem item){
		temporaryAbsenceHistRepository.delete(item.identifier());
		
		// Update item before
		if (domain.getDateHistoryItems().size() > 0) {
			DateHistoryItem lastItem = domain.getDateHistoryItems().get(domain.getDateHistoryItems().size() - 1);
			temporaryAbsenceHistRepository.update(lastItem);
		}
	}
	
	/**
	 * Update item before
	 * @param domain
	 * @param item
	 */
	private void updateItemBefore(TempAbsenceHistory domain, DateHistoryItem item){
		Optional<DateHistoryItem> itemToBeUpdated = domain.immediatelyBefore(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		temporaryAbsenceHistRepository.update(itemToBeUpdated.get());
	}
	/**
	 * Update item after
	 * @param domain
	 * @param item
	 */
	private void updateItemAfter(TempAbsenceHistory domain, DateHistoryItem item){
		Optional<DateHistoryItem> itemToBeUpdated = domain.immediatelyAfter(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		temporaryAbsenceHistRepository.update(itemToBeUpdated.get());
	}
}
