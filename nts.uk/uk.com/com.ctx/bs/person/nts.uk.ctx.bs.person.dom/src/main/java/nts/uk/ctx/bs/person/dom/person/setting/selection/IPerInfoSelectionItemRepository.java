package nts.uk.ctx.bs.person.dom.person.setting.selection;

import java.util.List;

public interface IPerInfoSelectionItemRepository {
	
	void add(PerInfoSelectionItem perInfoSelectionItem);
	
	void update(PerInfoSelectionItem perInfoSelectionItem);
	
	void remove(PerInfoSelectionItem perInfoSelectionItem);
	
	List<PerInfoSelectionItem> getAllPerInfoSelectionItem();
	
	PerInfoSelectionItem getPerInfoSelectionItem(String selectionItemId);
	
	boolean checkExist(String selectionItemId);
	
}
