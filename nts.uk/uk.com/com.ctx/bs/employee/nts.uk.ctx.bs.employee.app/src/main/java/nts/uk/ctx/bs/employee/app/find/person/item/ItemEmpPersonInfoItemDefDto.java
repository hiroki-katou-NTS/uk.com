package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

@Value
public class ItemEmpPersonInfoItemDefDto {
	private String id;
	private String perInfoCtgId;
	private String itemName;
	private int isAbolition;
	private int systemRequired;

	public static ItemEmpPersonInfoItemDefDto fromDomain(PersonInfoItemDefinition domain) {

		return new ItemEmpPersonInfoItemDefDto(domain.getPerInfoItemDefId(),
				domain.getPerInfoCategoryId(),domain.getItemName().v(),
				domain.getIsAbolition().value, domain.getSystemRequired().value);

	}

}
