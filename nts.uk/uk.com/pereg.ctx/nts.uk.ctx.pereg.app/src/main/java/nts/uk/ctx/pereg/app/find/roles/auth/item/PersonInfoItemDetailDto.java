package nts.uk.ctx.pereg.app.find.roles.auth.item;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemDetail;

@Value
public class PersonInfoItemDetailDto {
	private String roleId;

	private String personCategoryAuthId;

	private String personItemDefId;

	private int selfAuth;

	private int otherAuth;

	private String itemCd;

	private String itemName;

	private int abolitionAtr;

	private int requiredAtr;

	private boolean setting;

	private List<PersonInfoItemDetailDto> setItems;

	public static PersonInfoItemDetailDto fromDomain(PersonInfoItemDetail domain) {
		return new PersonInfoItemDetailDto(domain.getRoleId(), domain.getPersonInfoCategoryAuthId(),
				domain.getPersonItemDefId(), domain.getSelfAuthType(), domain.getOtherPersonAuth(), domain.getItemCd(),
				domain.getItemName(), domain.getAbolitionAtr(), domain.getRequiredAtr(), domain.isSetting(),
				domain.getSetItems() != null
						? domain.getSetItems().stream().map(x -> fromDomain(x)).collect(Collectors.toList()) : null);
	}
}
