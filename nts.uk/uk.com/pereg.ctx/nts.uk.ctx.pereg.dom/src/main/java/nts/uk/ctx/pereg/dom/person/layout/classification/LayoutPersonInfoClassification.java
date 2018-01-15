/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.layout.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LayoutPersonInfoClassification extends AggregateRoot {

	protected String layoutID;
	protected DispOrder dispOrder;
	protected String personInfoCategoryID;
	protected LayoutItemType layoutItemType;

	public static LayoutPersonInfoClassification createFromJaveType(String layoutID, int dispOrder,
			String personInfoCategoryID, int layoutItemType) {
		
		return new LayoutPersonInfoClassification(layoutID, new DispOrder(dispOrder),
				personInfoCategoryID, EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class));
	}
}
