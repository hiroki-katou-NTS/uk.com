/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DispOrder;

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
	private LayoutDisPOrder layoutDisPOrder;
	private DispOrder disPOrder;
	private String personInfoItemDefinitionID;

	
	public static LayoutPersonInfoClsDefinition createFromJavaType(String layoutId, int layoutDisPOrder, int disPOrder,
			String personInfoItemDefinitionID) {
		return new LayoutPersonInfoClsDefinition(layoutId, new LayoutDisPOrder(new BigDecimal(layoutDisPOrder)),
				new DispOrder(disPOrder), personInfoItemDefinitionID);
	}
}
