package nts.uk.ctx.pereg.dom.person.setting.selectionitem.history;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

public interface SelectionHistoryRepository {
	
	Optional<SelectionHistory> get(String selectionItemId, String companyId);

	void add(SelectionHistory selectionHistory);
	
	public void update(SelectionHistory domain, DateHistoryItem itemToBeUpdated);
	
	public void delete(SelectionHistory domain, DateHistoryItem itemToBeDeleted);
}
