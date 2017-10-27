package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface PerInfoHistorySelectionRepository {
	void add(PerInfoHistorySelection perInfoHistorySelection);

	void remove(String selectionItemId);
	
	Optional<PerInfoHistorySelection> getHistorySelectionItem(String histId);

	List<PerInfoHistorySelection> historySelection(String selectionItemId);
	
	List<PerInfoHistorySelection> historyStartDateSelection(GeneralDate startDate);

	List<PerInfoHistorySelection> getAllPerInfoHistorySelection(String selectionItemId, String companyId);
}
