package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

@Value
public class EmpPersonInfoItemDefDto {
	private String id;
	private String perInfoCtgId;
	private String itemName;
	private int isAbolition;
	private int systemRequired;

	public static EmpPersonInfoItemDefDto fromDomain(PersonInfoItemDefinition domain) {

		return new EmpPersonInfoItemDefDto(domain.getPerInfoItemDefId(),
				domain.getPerInfoCategoryId(),domain.getItemName().v(),
				domain.getIsAbolition().value, domain.getSystemRequired().value);

	}

}
