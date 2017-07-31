package nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition;

import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;

public interface LayoutPersonInfoClsDefRepository {

	void add(LayoutPersonInfoClsDefinition layoutPersonInfoDefinition);

	void update(LayoutPersonInfoClsDefinition layoutPersonInfoDefinition);

	void remove(String layoutID, DisPOrder disPOrder, LayoutDisPOrder layoutDisPOrder);
}
