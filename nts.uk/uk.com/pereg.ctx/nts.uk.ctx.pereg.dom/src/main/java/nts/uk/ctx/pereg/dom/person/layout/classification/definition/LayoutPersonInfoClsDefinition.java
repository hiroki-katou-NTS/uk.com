/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.layout.classification.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.layout.classification.DispOrder;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LayoutPersonInfoClsDefinition extends AggregateRoot {

	private String layoutID;
	private LayoutDispOrder layoutDisPOrder;
	private DispOrder dispOrder;
	private String personInfoItemDefinitionID;

	public static LayoutPersonInfoClsDefinition createFromJavaType(String layoutId, int layoutDispOrder, int disPOrder,
			String personInfoItemDefinitionID) {
		return new LayoutPersonInfoClsDefinition(layoutId, new LayoutDispOrder(layoutDispOrder),
				new DispOrder(disPOrder), personInfoItemDefinitionID);
	}
}
