package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder;

import java.util.List;

/**
 * 
 * @author tuannv
 *
 */

public interface SelectionItemOrderRepository {
	
	void add(SelectionItemOrder selectionItemOrder);

	void remove(String selectionId);
	
	void removeInSelectionItemId(String selectionItemId);
	
	List<SelectionItemOrder> getAllOrderSelectionByHistId(String histId);
	
	List<SelectionItemOrder> getAllOrderBySelectionId(String selectionId);
	
	void updateListSelOrder(List<SelectionItemOrder> lstSelOrder);
}
