/**
 * 
 */
package find.groupitem;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroup;

/**
 * @author laitv
 *
 */
@Value
public class PersonInfoItemGroupDto {

	String personInfoItemGroupID;
	String companyId;
	String fieldGroupName;
	int disPOrder;

	public static PersonInfoItemGroupDto fromDomain(PersonInfoItemGroup domain) {
		return new PersonInfoItemGroupDto(domain.getPersonInfoItemGroupID(), domain.getCompanyId(),
				domain.getFieldGroupName().v(), domain.getDisPOrder().v().intValue());

	}

}
