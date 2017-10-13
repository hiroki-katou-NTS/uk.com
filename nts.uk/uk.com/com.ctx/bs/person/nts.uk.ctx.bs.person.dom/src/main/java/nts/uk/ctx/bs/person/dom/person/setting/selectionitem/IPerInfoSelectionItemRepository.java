package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;

public interface IPerInfoSelectionItemRepository {

	void add(PerInfoSelectionItem perInfoSelectionItem);

	void update(PerInfoSelectionItem perInfoSelectionItem);

	void remove(String selectionItemId);

	List<PerInfoSelectionItem> getAllPerInfoSelectionItem(String contractCd);

	Optional<PerInfoSelectionItem> getPerInfoSelectionItem(String selectionItemId);

	Optional<PerInfoSelectionItem> getSelectionByName(String selectionItemName);
}
