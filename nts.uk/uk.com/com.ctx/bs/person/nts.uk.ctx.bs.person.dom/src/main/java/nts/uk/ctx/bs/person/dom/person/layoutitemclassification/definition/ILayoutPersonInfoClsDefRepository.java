package nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition;

import java.util.List;

public interface ILayoutPersonInfoClsDefRepository {
	void removeAllByLayoutId(String layoutId);

	void add(LayoutPersonInfoClsDefinition layoutPersonInfoDefinition);

	List<String> getAllItemDfByClassifId(String layoutId, int dispOrder);
}
