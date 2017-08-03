package nts.uk.ctx.bs.person.dom.person.role.auth.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoItemDetail {
	@Setter
	@Getter
	private String roleId;
	@Setter
	@Getter
	private String personInfoCategoryAuthId;
	@Setter
	@Getter
	private String personItemDefId;
	@Setter
	@Getter
	private int selfAuthType;
	@Setter
	@Getter
	private int otherPersonAuth;
	@Setter
	@Getter
	private String itemCd;
	@Setter
	@Getter
	private String itemName;
	@Setter
	@Getter
	private int abolitionAtr;
	@Setter
	@Getter
	private int requiredAtr;

}
