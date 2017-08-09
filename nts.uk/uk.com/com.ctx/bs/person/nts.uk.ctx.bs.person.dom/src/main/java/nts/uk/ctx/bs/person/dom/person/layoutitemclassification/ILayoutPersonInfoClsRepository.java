/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

public interface ILayoutPersonInfoClsRepository {

	List<LayoutPersonInfoClassification> getAllItemClsById(String layoutId);

	List<String> getAllItemDefIdByLayoutId(String layoutId, String disPOrder);

	String getOneItemDfId(String layoutId, String disPOrder);

}
