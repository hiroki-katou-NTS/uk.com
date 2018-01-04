/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.layout.classification;

import java.util.List;

public interface ILayoutPersonInfoClsRepository {

	List<LayoutPersonInfoClassification> getAllByLayoutId(String layoutId);

	void removeAllByLayoutId(String layoutId);

	void addClassifications(List<LayoutPersonInfoClassification> domains);

	boolean checkExitItemCls(String layoutId);

	List<LayoutPersonInfoClassificationWithCtgCd> getAllWithCtdCdByLayoutId(String layoutId);
}
