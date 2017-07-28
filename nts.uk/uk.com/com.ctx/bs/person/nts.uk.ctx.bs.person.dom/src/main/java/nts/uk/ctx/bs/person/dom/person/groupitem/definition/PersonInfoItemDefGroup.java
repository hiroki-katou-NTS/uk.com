/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.groupitem.definition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author laitv
 *
 */
public class PersonInfoItemDefGroup extends AggregateRoot{
	
	@Getter
	private String personInfoItemDefGroupID;
	@Getter
	private String personInfoItemGroupID;
	@Getter
	private String companyID;

	/**
	 * @param personInfoItemDefGroupID
	 * @param personInfoItemGroupID
	 * @param companyID
	 */
	public PersonInfoItemDefGroup(String personInfoItemDefGroupID,
			String personInfoItemGroupID, String companyID) {
		super();
		this.personInfoItemDefGroupID = personInfoItemDefGroupID;
		this.personInfoItemGroupID = personInfoItemGroupID;
		this.companyID = companyID;
	}
	
	
	 
}
