/**
 * 
 */
package find.layoutitemclassification;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutItemType;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClassification;

/**
 * @author laitv
 *
 */
@Value
public class LayoutPersonInfoClsDto {

	String layoutID;
	int disPOrder;
	String personInfoCategoryID;
	int layoutItemType;

	public static LayoutPersonInfoClsDto fromDomain(LayoutPersonInfoClassification domain) {
		return new LayoutPersonInfoClsDto(domain.getLayoutID(), domain.getDisPOrder().v().intValue(),
				domain.getPersonInfoCategoryID(), domain.getLayoutItemType().value);

	}
}
