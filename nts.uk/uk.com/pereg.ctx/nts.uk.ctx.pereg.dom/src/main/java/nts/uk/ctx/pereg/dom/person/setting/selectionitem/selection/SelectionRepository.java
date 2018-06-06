package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SelectionRepository {

	void add(Selection selection);
	
	void addAll(List<Selection> selectionList);

	void update(Selection selection);

	void remove(String selectionId);
	
	void removeInSelectionItemId(String selectionItemId);

	List<Selection> getAllSelectByHistId(String histId);
	
	Map<String, List<Selection>> getByHistIdList(List<String> histIdList);

	Optional<Selection> getSelectionBySelectionCd(String selectionCD);

	List<String> getAllHistId(String histId);

	List<Selection> getAllSelectionBySelectionCdAndHistId(String selectionCd, String histId);

	List<Selection> getAllSelectionByCompanyId(String companyId, String selectionItemId, GeneralDate baseDate);
	
	List<Selection> getAllSelectionByHistoryId(String selectionItemId, GeneralDate baseDate);

	List<Selection> getAllSelectionBySelectionID(String selectionId);
	
}
