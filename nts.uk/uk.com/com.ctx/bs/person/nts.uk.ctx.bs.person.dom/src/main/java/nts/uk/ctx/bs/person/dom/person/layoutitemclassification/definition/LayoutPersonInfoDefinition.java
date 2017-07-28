/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;

/**
 * @author laitv
 *
 */
public class LayoutPersonInfoDefinition extends AggregateRoot {

	@Getter
	private String layoutID;

	@Getter
	private LayoutDisPOrder layoutDisPOrder;

	@Getter
	private DisPOrder disPOrder;

	@Getter
	private String personInfoItemDefinitionID;

	/**
	 * 
	 * @param layoutID
	 * @param layoutDisPOrder
	 * @param disPOrder
	 * @param personInfoItemDefinitionID
	 */
	public LayoutPersonInfoDefinition(String layoutID, LayoutDisPOrder layoutDisPOrder, DisPOrder disPOrder,
			String personInfoItemDefinitionID) {
		super();
		this.layoutID = layoutID;
		this.layoutDisPOrder = layoutDisPOrder;
		this.disPOrder = disPOrder;
		this.personInfoItemDefinitionID = personInfoItemDefinitionID;
	}

	public static LayoutPersonInfoDefinition createFromJavaType(String layoutId, int layoutDisPOrder, int disPOrder,
			String personInfoItemDefinitionID) {
		return new LayoutPersonInfoDefinition(layoutId, new LayoutDisPOrder(new BigDecimal(layoutDisPOrder)),
				new DisPOrder(new BigDecimal(disPOrder)), personInfoItemDefinitionID);
	}
}
