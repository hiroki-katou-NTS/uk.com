package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author tuannv
 *
 */

public interface SelectionItemOrderRepository {
	
	void add(SelectionItemOrder selectionItemOrder);
	
	void addAll(List<SelectionItemOrder> selectionItemOrders);

	void remove(String selectionId);
	
	void removeInSelectionItemId(String selectionItemId);
	
	List<SelectionItemOrder> getAllOrderSelectionByHistId(String histId);
	
	Map<String, List<SelectionItemOrder>> getByHistIdList(List<String> histIdList);
	
	List<SelectionItemOrder> getAllOrderBySelectionId(String selectionId);
	
	void updateListSelOrder(List<SelectionItemOrder> lstSelOrder);
}
