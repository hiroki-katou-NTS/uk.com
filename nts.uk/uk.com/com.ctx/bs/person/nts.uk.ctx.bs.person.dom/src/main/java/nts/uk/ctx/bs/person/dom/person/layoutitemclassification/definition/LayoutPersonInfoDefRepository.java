package nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition;

import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;

public interface LayoutPersonInfoDefRepository {

	void add(LayoutPersonInfoDefinition layoutPersonInfoDefinition);

	void update(LayoutPersonInfoDefinition layoutPersonInfoDefinition);

	void remove(String layoutID, DisPOrder disPOrder, LayoutDisPOrder layoutDisPOrder);
}
