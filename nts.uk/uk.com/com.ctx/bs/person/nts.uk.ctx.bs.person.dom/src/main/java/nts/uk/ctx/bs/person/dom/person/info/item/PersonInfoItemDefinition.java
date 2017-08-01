package nts.uk.ctx.bs.person.dom.person.info.item;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
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
	private SystemRequired systemRequired;
	private RequireChangable requireChangable;
	private ItemTypeState itemTypeState;

	private PersonInfoItemDefinition(String personInfoCategoryId, String itemCode, String itemParentCode,
			String itemName, int isUsed, int isFixed, int isRequired, int systemRequired, int requireChangable) {
		super();
		this.personInfoItemDefinitionId = IdentifierUtil.randomUniqueId();
		this.personInfoCategoryId = personInfoCategoryId;
		this.itemCode = new ItemCode(itemCode);
		this.itemParentCode = new ItemCode(itemParentCode);
		this.itemName = new ItemName(itemName);
		this.isUsed = EnumAdaptor.valueOf(isUsed, IsUsed.class);
		this.isFixed = EnumAdaptor.valueOf(isFixed, IsFixed.class);
		this.isRequired = EnumAdaptor.valueOf(isRequired, IsRequired.class);
		this.systemRequired = EnumAdaptor.valueOf(systemRequired, SystemRequired.class);
		this.requireChangable = EnumAdaptor.valueOf(requireChangable, RequireChangable.class);
	}

	public static PersonInfoItemDefinition createFromJavaType(String personInfoCategoryId, String itemCode,
			String itemParentCode, String itemName, int isUsed, int isFixed, int isRequired, int systemRequired,
			int requireChangable) {
		return new PersonInfoItemDefinition(personInfoCategoryId, itemCode, itemParentCode, itemName, isUsed, isFixed,
				isRequired, systemRequired, requireChangable);
	}

	private void setItemTypeState(ItemTypeState itemTypeState) {
		this.itemTypeState = itemTypeState;
	}

	public void setSetItem(List<String> items) {
		setItemTypeState(ItemTypeState.createSetItem(items));
	};

	public void setSingleItem() {
		setItemTypeState(ItemTypeState.createSingleItem());
	};

}
