/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;


public interface LayoutPersonInfoClsRepository {
	
	
	List<LayoutPersonInfoClassification> getAllItemCls(String layoutId);
	
	List<PersonInfoItemDefinition> getAllPerInfoItemDefByLayoutId(String layoutId);

}
