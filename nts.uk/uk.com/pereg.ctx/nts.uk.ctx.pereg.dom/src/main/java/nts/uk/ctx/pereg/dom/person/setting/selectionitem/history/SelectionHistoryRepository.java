package nts.uk.ctx.pereg.dom.person.setting.selectionitem.history;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

public interface SelectionHistoryRepository {
	
	Optional<SelectionHistory> get(String selectionItemId, String companyId);
	
	List<SelectionHistory> getList(String selectionItemId, List<String> companyIds);

	void add(SelectionHistory selectionHistory);
	
	void addAllDomain(SelectionHistory selectionHistory);
	
	void update(SelectionHistory domain, DateHistoryItem itemToBeUpdated);
	
	void delete(SelectionHistory domain, DateHistoryItem itemToBeDeleted);
	
	void removeAllHistoryIds(List<String> historyIds);
	
	void removeAllOfSelectionItem(String selectionItemId);
}
