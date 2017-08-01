package nts.uk.ctx.bs.person.dom.person.info.item;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.IsUsed;

@Getter
public class PersonInfoItemDefinition extends AggregateRoot {
	private String personInfoItemDefinitionId;
	private String personInfoCategoryId;
	private ItemCode itemCode;
	private ItemCode itemParentCode;
	private ItemName itemName;
	private IsUsed isUsed;
	private IsFixed isFixed;
	private IsRequired isRequired;
	private SystemRequired SystemRequired;
	private RequireChangable requireChangable;
	private ItemTypeState itemTypeState;

	private PersonInfoItemDefinition(String personInfoCategoryId, ItemCode itemCode, ItemCode itemParentCode,
			ItemName itemName, IsUsed isUsed, IsFixed isFixed, IsRequired isRequired,
			nts.uk.ctx.bs.person.dom.person.info.item.SystemRequired systemRequired, RequireChangable requireChangable,
			ItemTypeState itemTypeState) {
		super();
		this.personInfoCategoryId = personInfoCategoryId;
		this.itemCode = itemCode;
		this.itemParentCode = itemParentCode;
		this.itemName = itemName;
		this.isUsed = isUsed;
		this.isFixed = isFixed;
		this.isRequired = isRequired;
		this.SystemRequired = systemRequired;
		this.requireChangable = requireChangable;
		this.itemTypeState = itemTypeState;
	}

	public static PersonInfoItemDefinition createFromJavaType() {
		return null;
	}

}
