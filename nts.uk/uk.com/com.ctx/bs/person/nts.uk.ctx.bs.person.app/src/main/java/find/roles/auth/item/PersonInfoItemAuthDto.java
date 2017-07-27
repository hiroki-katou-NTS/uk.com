package find.roles.auth.item;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuth;

@Value
public class PersonInfoItemAuthDto {
	String roleId;
	String personCategoryAuthId;
	String personItemDefId;
	int selfAuth;
	int otherAuth;

	public static PersonInfoItemAuthDto fromDomain(PersonInfoItemAuth domain) {
		return new PersonInfoItemAuthDto(domain.getRoleId(), domain.getPersonCategoryAuthId(),
				domain.getPersonItemDefId(), domain.getSelfAuth().value, domain.getOtherAuth().value);
	}
}
