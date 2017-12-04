package nts.uk.ctx.pereg.app.find.roles.auth.item;

import lombok.Value;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;

@Value
public class PersonInfoItemAuthDto {
	private String roleId;
	private String personCategoryAuthId;
	private String personItemDefId;
	private int selfAuth;
	private int otherAuth;

	public static PersonInfoItemAuthDto fromDomain(PersonInfoItemAuth domain) {
		return new PersonInfoItemAuthDto(domain.getRoleId(), domain.getPersonCategoryAuthId(),
				domain.getPersonItemDefId(), domain.getSelfAuth().value, domain.getOtherAuth().value);
	}
}
