/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.groupitem.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoItemDefGroup extends AggregateRoot{
	
	
	private String personInfoItemDefGroupID;
	private String personInfoItemGroupID;
	private String companyID;

	
	 
}
