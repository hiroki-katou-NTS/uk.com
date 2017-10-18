package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

import java.util.List;

/**
 * 
 * @author tuannv
 *
 */

public interface SelectionItemOrderRepository {
	void add(SelectionItemOrder selectionItemOrder);

	void remove(SelectionItemOrder selectionItemOrder);
	
	List<SelectionItemOrder> getAllOrderItemSelection(String selectedId);
}
