package repository.person.setting.selectionitem.selection;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;

/**
 * 
 * @author tuannv
 *
 */

@Stateless
public class JpaSelectionItemOrderRepository extends JpaRepository implements SelectionItemOrderRepository {

	@Override
	public void add(SelectionItemOrder selectionItemOrder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(SelectionItemOrder selectionItemOrder) {
		// TODO Auto-generated method stub

	}

}
