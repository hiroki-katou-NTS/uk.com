package repository.person.itemclassification.difination;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DispOrder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition.LayoutDispOrder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition.LayoutPersonInfoClsDefinition;

public class JpaItemClassificationDifination extends JpaRepository implements ILayoutPersonInfoClsDefRepository {

	private static final String REMOVE_ALL_BY_LAYOUT_ID = "DELETE ";

	@Override
	public void add(LayoutPersonInfoClsDefinition layoutPersonInfoDefinition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllByLayoutId(String layoutId) {
		// TODO Auto-generated method stub
		queryProxy().query(REMOVE_ALL_BY_LAYOUT_ID).setParameter("layoutId", layoutId);
	}

	@Override
	public List<String> getAllItemDfByClassifId(String layoutId, int dispOrder) {
		// TODO Auto-generated method stub
		return null;
	}
}
