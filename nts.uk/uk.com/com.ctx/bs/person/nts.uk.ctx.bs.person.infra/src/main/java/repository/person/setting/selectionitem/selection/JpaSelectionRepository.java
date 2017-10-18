package repository.person.setting.selectionitem.selection;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionRepository;

/**
 * 
 * @author tuannv
 *
 */

@Stateless
public class JpaSelectionRepository extends JpaRepository implements SelectionRepository {

	@Override
	public void add(Selection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Selection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String selectionId) {
		// TODO Auto-generated method stub

	}

}
