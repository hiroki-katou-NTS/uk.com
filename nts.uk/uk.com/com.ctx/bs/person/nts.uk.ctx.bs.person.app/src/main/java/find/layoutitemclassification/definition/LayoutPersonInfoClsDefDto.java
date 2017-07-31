/**
 * 
 */
package find.layoutitemclassification.definition;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition.LayoutDisPOrder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition.LayoutPersonInfoClsDefinition;

/**
 * @author laitv
 *
 */
@Value
public class LayoutPersonInfoClsDefDto {

	String layoutID;
	int layoutDisPOrder;
	int disPOrder;
	String personInfoItemDefinitionID;

	public static LayoutPersonInfoClsDefDto fromDomain(LayoutPersonInfoClsDefinition domain) {
		return new LayoutPersonInfoClsDefDto(domain.getLayoutID(), domain.getLayoutDisPOrder().v().intValue(),
				domain.getDisPOrder().v().intValue(), domain.getPersonInfoItemDefinitionID());
	}
}
