package nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition;

import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DispOrder;

public interface ILayoutPersonInfoClsDefRepository {

	void add(LayoutPersonInfoClsDefinition layoutPersonInfoDefinition);

	void update(LayoutPersonInfoClsDefinition layoutPersonInfoDefinition);

	void remove(String layoutID, DispOrder disPOrder, LayoutDispOrder layoutDisPOrder);
}
