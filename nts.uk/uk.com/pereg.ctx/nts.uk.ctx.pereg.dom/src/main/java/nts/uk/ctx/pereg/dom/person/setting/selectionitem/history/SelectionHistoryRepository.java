package nts.uk.ctx.pereg.dom.person.setting.selectionitem.history;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

public interface SelectionHistoryRepository {
	
	Optional<SelectionHistory> get(String selectionItemId, String companyId);

	void add(SelectionHistory selectionHistory);
	
	void addAllDomain(SelectionHistory selectionHistory);
	
	void update(SelectionHistory domain, DateHistoryItem itemToBeUpdated);
	
	void delete(SelectionHistory domain, DateHistoryItem itemToBeDeleted);
	
	void removeAllOfSelectionItem(String selectionItemId);
}
