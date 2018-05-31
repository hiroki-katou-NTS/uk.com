package nts.uk.ctx.pereg.app.find.roles.auth.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemDetail;

@Data
@AllArgsConstructor
public class PersonInfoItemDetailDto {
	private String roleId;

	private String personCategoryAuthId;

	private String personItemDefId;

	private int selfAuth;

	private int otherAuth;

	private String itemCd;

	private String itemName;

	private String parrentCd;

	private int abolitionAtr;

	private int requiredAtr;

	private boolean setting;
	
	private int dataType;

	public static PersonInfoItemDetailDto createDto(PersonInfoItemDetail itemDetail) {
		return new PersonInfoItemDetailDto(itemDetail.getRoleId(), itemDetail.getPersonInfoCategoryAuthId(),
				itemDetail.getPersonItemDefId(), itemDetail.getSelfAuthType(), itemDetail.getOtherPersonAuth(), itemDetail.getItemCd(),
				itemDetail.getItemName(), itemDetail.getItemParentCd(), itemDetail.getAbolitionAtr(), itemDetail.getRequiredAtr(),
				itemDetail.isSetting(), itemDetail.getDataType());
	}
}
