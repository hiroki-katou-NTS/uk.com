package nts.uk.ctx.pereg.dom.roles.auth.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonInfoItemDetail {

	private String roleId;

	private String personInfoCategoryAuthId;

	private String personItemDefId;

	private int selfAuthType;

	private int otherPersonAuth;

	private String itemCd;

	private String itemName;

	private int abolitionAtr;

	private int requiredAtr;

	private boolean setting;

	private String itemParentCd;
	
	private int dataType;

}
