package nts.uk.ctx.pereg.dom.person.layout.classification.definition;

import java.util.List;

public interface ILayoutPersonInfoClsDefRepository {
	
	void removeAllByLayoutId(String layoutId);

	void addClassificationItemDefines(List<LayoutPersonInfoClsDefinition> domains);

	List<String> getAllItemDefineIds(String layoutId, int classDispOrder);
	
	boolean checkExitItemClsDf(String layoutId);
}
