/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.util.List;

public interface ILayoutPersonInfoClsRepository {

	List<LayoutPersonInfoClassification> getAllByLayoutId(String layoutId);

	void removeAllByLayoutId(String layoutId);

	void addClassifications(List<LayoutPersonInfoClassification> domains);
}
