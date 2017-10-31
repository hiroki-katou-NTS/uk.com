package nts.uk.ctx.bs.employee.app.find.employee.item;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

@Value
public class PerInfoItemDefDto {
	private String id;
	private String perInfoCtgId;
	private String itemName;
	private int isAbolition;
	private int systemRequired;

	public static PerInfoItemDefDto fromDomain(PersonInfoItemDefinition domain) {

		return new PerInfoItemDefDto(domain.getPerInfoItemDefId(),
				domain.getPerInfoCategoryId(),domain.getItemName().v(),
				domain.getIsAbolition().value, domain.getSystemRequired().value);

	}

}
