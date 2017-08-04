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

	private String personInfoItemGroupID;
	private String companyId;
	private String fieldGroupName;
	private int dispOrder;

	public static PersonInfoItemGroupDto fromDomain(PersonInfoItemGroup domain) {
		return new PersonInfoItemGroupDto(domain.getPersonInfoItemGroupID(), domain.getCompanyId(),
				domain.getFieldGroupName().v(), domain.getDispOrder().v().intValue());

	}

}
