/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.groupitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.layout.classification.DispOrder;

/**
 * @author laitv
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoItemGroup extends AggregateRoot {
	
	private String personInfoItemGroupID;
	private String companyId;
	private FieldGroupName fieldGroupName;
	private DispOrder dispOrder;

	public static PersonInfoItemGroup createFromJavaType(String personInfoItemGroupID, String companyId,
			String fieldGroupName, int disPOrder) {
		return new PersonInfoItemGroup(personInfoItemGroupID, companyId, new FieldGroupName(fieldGroupName),
				new DispOrder(disPOrder));
	}
}
