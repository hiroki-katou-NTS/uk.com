package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

import java.util.List;
import java.util.Optional;

public interface SelectionRepository {

	void add(Selection selection);

	void update(Selection selection);

	void remove(String selectionId);

	List<Selection> getAllHistorySelection(String histId);

	Optional<Selection> getHistSelection(String histId);
	
	Optional<Selection> getCheckBySelectionCD(String selectionCD);
	
	List<String> getAllHist(String histId);

	List<Selection> geSelectionList(String selectionCd, String histId);
	
}
