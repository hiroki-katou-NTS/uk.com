package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface PerInfoHistorySelectionRepository {
	void add(PerInfoHistorySelection perInfoHistorySelection);

	Optional<PerInfoHistorySelection> getHistorySelectionItem(String histId);

	void remove(String selectionItemId);

	List<PerInfoHistorySelection> historySelection(String selectionItemId);
	
	List<PerInfoHistorySelection> historyStartDateSelection(GeneralDate startDate);
}
