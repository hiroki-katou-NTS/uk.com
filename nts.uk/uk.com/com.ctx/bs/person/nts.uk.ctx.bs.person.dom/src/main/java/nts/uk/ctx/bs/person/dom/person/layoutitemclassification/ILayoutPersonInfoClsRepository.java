/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;


public interface ILayoutPersonInfoClsRepository {	
	
	List<LayoutPersonInfoClassification> getAllItemCls(String layoutId);
	
	List<PersonInfoItemDefinition> getAllPerInfoItemDefByLayoutId(String layoutId);
}
