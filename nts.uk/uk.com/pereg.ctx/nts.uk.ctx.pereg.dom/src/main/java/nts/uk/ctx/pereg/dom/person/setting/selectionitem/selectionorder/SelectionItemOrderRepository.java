package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder;

import java.util.List;

/**
 * 
 * @author tuannv
 *
 */

public interface SelectionItemOrderRepository {
	
	void add(SelectionItemOrder selectionItemOrder);
	
	void addAll(List<SelectionItemOrder> selectionItemOrders);

	void remove(String selectionId);
	
	void removeAll(List<String> selectionIds);
	
	void removeInSelectionItemId(String selectionItemId);
	
	List<SelectionItemOrder> getAllOrderSelectionByHistId(String histId);
	
	List<SelectionItemOrder> getByHistIdList(List<String> histIdList);
	
	List<SelectionItemOrder> getAllOrderBySelectionId(String selectionId);
	
	void updateListSelOrder(List<SelectionItemOrder> lstSelOrder);
}
