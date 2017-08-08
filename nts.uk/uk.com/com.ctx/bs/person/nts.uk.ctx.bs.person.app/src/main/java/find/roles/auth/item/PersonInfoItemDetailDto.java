package find.roles.auth.item;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemDetail;

@Value
public class PersonInfoItemDetailDto {
	String roleId;
	
	String personCategoryAuthId;
	
	String personItemDefId;
	
	int selfAuth;
	
	int otherAuth;
	
	String itemCd;
	
	String itemName;
	
	int abolitionAtr;
	
	int requiredAtr;
	
	boolean setting;

	public static PersonInfoItemDetailDto fromDomain(PersonInfoItemDetail domain) {
		return new PersonInfoItemDetailDto(domain.getRoleId(), domain.getPersonInfoCategoryAuthId(),
				domain.getPersonItemDefId(), domain.getSelfAuthType(), domain.getOtherPersonAuth(), domain.getItemCd(),
				domain.getItemName(), domain.getAbolitionAtr(), domain.getRequiredAtr(), domain.isSetting());
	}
}
