package nts.uk.ctx.pereg.dom.person.info.item;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;

@Getter
@Setter
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
    private BigDecimal  selectionItemRefType;

	public static String ROOT_CONTRACT_CODE = "000000000000";
	public PersonInfoItemDefinition(){};

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
			int requireChangable, BigDecimal  selectionItemRefType) {
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
		this.selectionItemRefType = selectionItemRefType;
	}
	
	
	//lanlt
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

	private PersonInfoItemDefinition(String perInfoCategoryId, String itemCode, String itemParentCode,
			String itemName) {
		super();
		this.perInfoItemDefId = IdentifierUtil.randomUniqueId();
		this.perInfoCategoryId = perInfoCategoryId;
		this.itemCode = new ItemCode(itemCode);
		this.itemParentCode = new ItemCode(itemParentCode);
		this.itemName = new ItemName(itemName);
		this.isAbolition = IsAbolition.NOT_ABOLITION;
		this.isFixed = IsFixed.NOT_FIXED;
		this.isRequired = IsRequired.NONE_REQUIRED;
		this.systemRequired = SystemRequired.NONE_REQUIRED;
		this.requireChangable = RequireChangable.REQUIRED;
	}
	
	private PersonInfoItemDefinition(String perInfoItemDefId, String perInfoCategoryId, String itemName) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.perInfoCategoryId = perInfoCategoryId;
		this.itemCode = new ItemCode("");
		this.itemParentCode = new ItemCode("");
		this.itemName = new ItemName(itemName);
		this.isAbolition = IsAbolition.NOT_ABOLITION;
		this.isFixed = IsFixed.NOT_FIXED;
		this.isRequired = IsRequired.NONE_REQUIRED;
		this.systemRequired = SystemRequired.NONE_REQUIRED;
		this.requireChangable = RequireChangable.REQUIRED;
	}
	
	private PersonInfoItemDefinition(String ctgID,String itemId) {
		super();
		this.perInfoItemDefId = itemId;
		this.perInfoCategoryId = ctgID;
	}
	
	public static PersonInfoItemDefinition createFromJavaType(String ctgId, String itemId) {
		return new PersonInfoItemDefinition(ctgId, itemId);
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
	 //lanlt
	public static PersonInfoItemDefinition createFromEntity(String perInfoItemDefId, String perInfoCategoryId,
			String itemCode, String itemParentCode, String itemName, int isAbolition, int isFixed, int isRequired,
			int systemRequired, int requireChangable, BigDecimal selectionItemRefType ) {
		return new PersonInfoItemDefinition(perInfoItemDefId, perInfoCategoryId, itemCode, itemParentCode, itemName,
				isAbolition, isFixed, isRequired, systemRequired, requireChangable, selectionItemRefType);
	}

	public static PersonInfoItemDefinition createForAddItem(String perInfoCategoryId, String itemCode,
			String itemParentCode, String itemName) {
		return new PersonInfoItemDefinition(perInfoCategoryId, itemCode, itemParentCode, itemName);
	}
	
	public static PersonInfoItemDefinition createFromEntityMap(String perInfoItemDefId, String perInfoCategoryId, String itemName) {
		return new PersonInfoItemDefinition(perInfoItemDefId, perInfoCategoryId, itemName);
	}

	public void setItemTypeState(ItemTypeState itemTypeState) {
		this.itemTypeState = itemTypeState;
	}

	public void setItemName(String name) {
		this.itemName = new ItemName(name);
	}

}
