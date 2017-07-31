/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.math.BigDecimal;

import javax.persistence.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LayoutPersonInfoClassification extends AggregateRoot {

	
	private String layoutID;
	private DisPOrder disPOrder;
	private String personInfoCategoryID;
	private LayoutItemType layoutItemType;

	public static LayoutPersonInfoClassification createFromJaveType(String layoutID, int disPOrder, String personInfoCategoryID,
			int layoutItemType) {
		return new LayoutPersonInfoClassification(layoutID, new DisPOrder(new BigDecimal(disPOrder)), personInfoCategoryID,
				EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class));
	}
}
