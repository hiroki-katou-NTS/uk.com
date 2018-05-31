/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.layout.classification;

import java.util.List;
import java.util.Map;

public interface ILayoutPersonInfoClsRepository {

	List<LayoutPersonInfoClassification> getAllByLayoutId(String layoutId);
	
	Map<String, List<LayoutPersonInfoClassification>> getAllByLayoutIdList(List<String> layoutIdList);

	void removeAllByLayoutId(String layoutId);

	void addClassifications(List<LayoutPersonInfoClassification> domains);

	boolean checkExitItemCls(String layoutId);

	List<LayoutPersonInfoClassificationWithCtgCd> getAllWithCtdCdByLayoutId(String layoutId);
}
