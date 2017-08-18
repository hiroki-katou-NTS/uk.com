package nts.uk.ctx.bs.person.dom.person.info.item;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.info.category.IsAbolition;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;

@Getter
public class PersonInfoItemDefinition extends AggregateRoot {
	private String perInfoItemDefId;
	private String perInfoCategoryId;
	private ItemCode itemCode;
	private ItemCode itemParentCode;
	private ItemName itemName;
	private IsAbolition isAbolition;
	private IsFixed isFixed;
	private IsRequired isRequired;
	private SystemRequired systemRequired;
	private RequireChangable requireChangable;
	private ItemTypeState itemTypeState;
	
	public final static String ROOT_CONTRACT_CODE = "000000000000";
	
	private PersonInfoItemDefinition(String perInfoCategoryId, String itemCode, String itemParentCode, String itemName,
			int isAbolition, int isFixed, int isRequired) {
		super();
		this.perInfoItemDefId = IdentifierUtil.randomUniqueId();
		this.perInfoCategoryId = perInfoCategoryId;
		this.itemCode = new ItemCode(itemCode);
		this.itemParentCode = new ItemCode(itemParentCode);
		this.itemName = new ItemName(itemName);
		this.isAbolition = EnumAdaptor.valueOf(isAbolition, IsAbolition.class);
		this.isFixed = EnumAdaptor.valueOf(isFixed, IsFixed.class);
		this.isRequired = EnumAdaptor.valueOf(isRequired, IsRequired.class);
		this.systemRequired = SystemRequired.NONE_REQUIRED;
		this.requireChangable = RequireChangable.NONE_REQUIRED;
	}

	private PersonInfoItemDefinition(String perInfoItemDefId, String perInfoCategoryId, String itemCode,
			String itemParentCode, String itemName, int isAbolition, int isFixed, int isRequired, int systemRequired,
			int requireChangable) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.perInfoCategoryId = perInfoCategoryId;
		this.itemCode = new ItemCode(itemCode);
		this.itemParentCode = new ItemCode(itemParentCode);
		this.itemName = new ItemName(itemName);
		this.isAbolition = EnumAdaptor.valueOf(isAbolition, IsAbolition.class);
		this.isFixed = EnumAdaptor.valueOf(isFixed, IsFixed.class);
		this.isRequired = EnumAdaptor.valueOf(isRequired, IsRequired.class);
		this.systemRequired = EnumAdaptor.valueOf(systemRequired, SystemRequired.class);
		this.requireChangable = EnumAdaptor.valueOf(requireChangable, RequireChangable.class);
	}

	public static PersonInfoItemDefinition createFromJavaType(String personInfoCategoryId, String itemCode,
			String itemParentCode, String itemName, int isAbolition, int isFixed, int isRequired) {
		return new PersonInfoItemDefinition(personInfoCategoryId, itemCode, itemParentCode, itemName, isAbolition,
				isFixed, isRequired);
	}

	public static PersonInfoItemDefinition createFromEntity(String perInfoItemDefId, String perInfoCategoryId,
			String itemCode, String itemParentCode, String itemName, int isAbolition, int isFixed, int isRequired,
			int systemRequired, int requireChangable) {
		return new PersonInfoItemDefinition(perInfoItemDefId, perInfoCategoryId, itemCode, itemParentCode, itemName,
				isAbolition, isFixed, isRequired, systemRequired, requireChangable);
	}

	public void setItemTypeState(ItemTypeState itemTypeState) {
		this.itemTypeState = itemTypeState;
	}

}
