package nts.uk.ctx.bs.person.dom.person.setting.selection;

import java.util.List;
import java.util.Optional;

public interface PerInfoHistorySelectionRepository {
	void add(PerInfoHistorySelection perInfoHistorySelection);
	//void removeHistoryItem(String selectionItemId);
	Optional<PerInfoHistorySelection> getHistorySelectionItem(String histId);
	
	void remove(String selectionItemId);
	List<PerInfoHistorySelection> historySelection(String selectionItemId);
}
