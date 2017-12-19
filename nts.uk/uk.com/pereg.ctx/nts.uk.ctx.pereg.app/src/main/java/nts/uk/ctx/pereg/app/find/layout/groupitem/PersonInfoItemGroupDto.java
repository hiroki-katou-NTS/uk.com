/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout.groupitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.groupitem.PersonInfoItemGroup;

/**
 * @author laitv
 *
 */
@Getter
@AllArgsConstructor
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
