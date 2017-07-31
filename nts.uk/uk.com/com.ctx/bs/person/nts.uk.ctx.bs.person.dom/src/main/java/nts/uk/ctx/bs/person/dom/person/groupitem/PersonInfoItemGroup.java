/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.groupitem;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoItemGroup extends AggregateRoot {
	
	private String personInfoItemGroupID;
	private String companyId;
	private FieldGroupName fieldGroupName;
	private DisPOrder disPOrder;

	public static PersonInfoItemGroup createFromJavaType(String personInfoItemGroupID, String companyId,
			String fieldGroupName, int disPOrder) {
		return new PersonInfoItemGroup(personInfoItemGroupID, companyId, new FieldGroupName(fieldGroupName),
				new DisPOrder(new BigDecimal(disPOrder)));
	}

}
