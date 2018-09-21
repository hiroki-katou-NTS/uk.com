package nts.uk.ctx.pereg.dom.person.info.item;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
@Setter
public class PersonInfoItemDefinition extends AggregateRoot {
	
	//Domain name: 個人情報項目定義
	
	/**
	 * 個人情報項目定義ID
	 */
	private String perInfoItemDefId;
	
	/**
	 * 個人情報カテゴリID
	 */
	private String perInfoCategoryId;
	
	/**
	 * ???
	 */
	private ItemCode itemParentCode;
	
	/**
	 * 項目コード
	 */
	private ItemCode itemCode;
	
	/**
	 * 項目名
	 */
	private ItemName itemName;

	/**
	 * 廃止区分
	 */
	private IsAbolition isAbolition;
	
	/**
	 * 既定区分
	 */
	private IsFixed isFixed;
	
	/**
	 * 必須区分
	 */
	private IsRequired isRequired;
	
	/**
	 * システム必須
	 */
	private SystemRequired systemRequired;
	
	/**
	 * 必須切替可能
	 */
	private RequireChangable requireChangable;
	
	/**
	 * 種類
	 */
	private ItemTypeState itemTypeState;
	
	/**
	 * ???
	 */
	private BigDecimal selectionItemRefType;
	
	/**
	 * リソースID
	 */
	private Optional<String> resourceId;
	
	/**
	 * 廃止切り替え可能か
	 */
	private boolean canAbolition;
	

	public PersonInfoItemDefinition() {
	};

	/**
	 * liên quan đến ver 18, bug liên quan #99565
	 * @param perInfoCategoryId
	 * @param itemCode
	 * @param itemParentCode
	 * @param itemName
	 * @param isAbolition
	 * @param isFixed
	 * @param isRequired
	 */
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
		this.canAbolition = false;
	}

	private PersonInfoItemDefinition(String perInfoItemDefId, String perInfoCategoryId, String itemCode,
			String itemParentCode, String itemName, int isAbolition, int isFixed, int isRequired, int systemRequired,
			int requireChangable, BigDecimal selectionItemRefType, String resourceId, int canAbolition) {
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
		this.resourceId = resourceId != null ? Optional.of(resourceId) : Optional.empty();
		this.canAbolition = canAbolition == 0? false: true;
	}

	// lanlt
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

	/**
	 * hàm tạo item của màn cps005b
	 * @param perInfoCategoryId
	 * @param itemCode
	 * @param itemParentCode
	 * @param itemName
	 */
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
		this.canAbolition = true;
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

	private PersonInfoItemDefinition(String ctgID, String itemId) {
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

	// lanlt
	public static PersonInfoItemDefinition createFromEntity(String perInfoItemDefId, String perInfoCategoryId,
			String itemCode, String itemParentCode, String itemName, int isAbolition, int isFixed, int isRequired,
			int systemRequired, int requireChangable, BigDecimal selectionItemRefType, String resourceId, int canAbolition) {
		return new PersonInfoItemDefinition(perInfoItemDefId, perInfoCategoryId, itemCode, itemParentCode, itemName,
				isAbolition, isFixed, isRequired, systemRequired, requireChangable, selectionItemRefType, resourceId, canAbolition);
	}

	public static PersonInfoItemDefinition createForAddItem(String perInfoCategoryId, String itemCode,
			String itemParentCode, String itemName) {
		return new PersonInfoItemDefinition(perInfoCategoryId, itemCode, itemParentCode, itemName);
	}

	public static PersonInfoItemDefinition createFromEntityMap(String perInfoItemDefId, String perInfoCategoryId,
			String itemName) {
		return new PersonInfoItemDefinition(perInfoItemDefId, perInfoCategoryId, itemName);
	}

	public static PersonInfoItemDefinition createFromEntityWithCodeAndName(String itemCode, String itemName, int abolitionAtr) {
		PersonInfoItemDefinition item = new PersonInfoItemDefinition();
		item.setItemCode(new ItemCode(itemCode));
		item.setItemName(itemName);
		item.setIsAbolition(EnumAdaptor.valueOf(abolitionAtr, IsAbolition.class));;
		return item;
	}

	public void setItemTypeState(ItemTypeState itemTypeState) {
		this.itemTypeState = itemTypeState;
	}

	public void setItemName(String name) {
		this.itemName = new ItemName(name);
	}
	
	public static PersonInfoItemDefinition createDomainWithNameAndAbolition(String id, String itemName, String itemCode){
		PersonInfoItemDefinition item = new PersonInfoItemDefinition();
		item.setPerInfoItemDefId(id);
		item.setItemName(itemName);
		item.setItemCode(new ItemCode(itemCode));
		return item;
	}
	
	
	/**
	 * @return
	 * @author danpv
	 */
	public static PersonInfoItemDefinition createNewPersonInfoItemDefinition(String perInfoItemDefId,
			String perInfoCategoryId, String itemParentCode, String itemCode, String itemName, int isAbolition,
			int isFixed, int isRequired, int systemRequired, int requireChangable, String resourceId, int itemType,
			BigDecimal dataType, BigDecimal stringItemLength, BigDecimal stringItemDataType,
			BigDecimal stringItemType,BigDecimal numericItemMinus, BigDecimal numericItemAmount, BigDecimal numericItemIntegerPart,
			BigDecimal numericItemDecimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax, BigDecimal dateItemType, 
			BigDecimal timeItemMax, BigDecimal timeItemMin, BigDecimal timepointItemMin, BigDecimal timepointItemMax, 
			BigDecimal selectionItemRefType, String selectionItemRefCode, String relatedCategoryCode, int canAbolition, List<String> items) {
		PersonInfoItemDefinition item = new PersonInfoItemDefinition();
		item.perInfoItemDefId = perInfoItemDefId;
		item.perInfoCategoryId = perInfoCategoryId;
		item.itemParentCode = new ItemCode(itemParentCode);
		item.itemCode = new ItemCode(itemCode);
		item.itemName = new ItemName(itemName);
		item.isAbolition = EnumAdaptor.valueOf(isAbolition, IsAbolition.class);
		item.isFixed = EnumAdaptor.valueOf(isFixed, IsFixed.class);
		item.isRequired = EnumAdaptor.valueOf(isRequired, IsRequired.class);
		item.systemRequired = EnumAdaptor.valueOf(systemRequired, SystemRequired.class);
		item.requireChangable = EnumAdaptor.valueOf(requireChangable, RequireChangable.class);
		item.canAbolition = canAbolition == 0? false: true;
		item.resourceId = resourceId != null ? Optional.of(resourceId) : Optional.empty();
		
		DataTypeState dataTypeState = null;
		ItemTypeState itemTypeState = null;
		if (itemType == ItemType.SINGLE_ITEM.value) {
			DataTypeValue dataTypeValue = EnumAdaptor.valueOf(dataType.intValue(), DataTypeValue.class);
			
			switch (dataTypeValue) {
			case STRING:
				dataTypeState = DataTypeState.createStringItem(stringItemLength.intValue(), stringItemType.intValue(),
						stringItemDataType.intValue());
				break;
			case NUMERIC:
				dataTypeState = DataTypeState.createNumericItem(numericItemMinus.intValue(),
						numericItemAmount.intValue(), numericItemIntegerPart.intValue(),
						numericItemDecimalPart == null? null: new Integer(numericItemDecimalPart.intValue()), numericItemMin, numericItemMax);
				break;
			case DATE:
				dataTypeState = DataTypeState.createDateItem(dateItemType.intValue());
				break;
			case TIME:
				dataTypeState = DataTypeState.createTimeItem(timeItemMax.intValue(), timeItemMin.intValue());
				break;
			case TIMEPOINT:
				dataTypeState = DataTypeState.createTimePointItem(timepointItemMin.intValue(),
						timepointItemMax.intValue());
				break;
			case SELECTION:
				dataTypeState = createSelectionItem(selectionItemRefType, selectionItemRefCode,
						DataTypeValue.SELECTION);
				break;
			case SELECTION_RADIO: // radio
				dataTypeState = createSelectionItem(selectionItemRefType, selectionItemRefCode,
						DataTypeValue.SELECTION_RADIO);
				break;
			case SELECTION_BUTTON: // button
				dataTypeState = createSelectionItem(selectionItemRefType, selectionItemRefCode,
						DataTypeValue.SELECTION_BUTTON);
				break;
			case READONLY:
				dataTypeState = DataTypeState.createReadonly(selectionItemRefCode);
				break;
			case RELATE_CATEGORY:
				dataTypeState = DataTypeState.createRelatedCategory(relatedCategoryCode);
				break;
			case NUMBERIC_BUTTON:
				dataTypeState = DataTypeState.createNumbericButton(numericItemMinus.intValue(),
						numericItemAmount.intValue(), numericItemIntegerPart.intValue(),
						numericItemDecimalPart.intValue(), numericItemMin, numericItemMax);
				break;
			case READONLY_BUTTON:
				dataTypeState = DataTypeState.createReadonlyButton(selectionItemRefCode);
				break;
			}

			itemTypeState = ItemTypeState.createSingleItem(dataTypeState);
			
		} else if (itemType == ItemType.SET_ITEM.value) {
			itemTypeState = ItemTypeState.createSetItem(items == null ? Arrays.asList(new String[] {}) : items);
		} else if (itemType == ItemType.TABLE_ITEM.value) {
			itemTypeState = ItemTypeState.createSetTableItem(items == null ? Arrays.asList(new String[] {}) : items);
		}
		
		item.setItemTypeState(itemTypeState);
		
		return item;
	}
	
	/**
	 * @param selectionItemRefType
	 * @param selectionItemRefCode
	 * @param selection
	 * @return
	 * @author danpv
	 */
	private static DataTypeState createSelectionItem(BigDecimal selectionItemRefType, String selectionItemRefCode,
			DataTypeValue selection) {
		DataTypeState dataTypeState = null;
		ReferenceTypeState referenceTypeState = null;

		if (selectionItemRefType != null) {
			if (selectionItemRefType.intValue() == ReferenceTypes.DESIGNATED_MASTER.value) {
				referenceTypeState = ReferenceTypeState.createMasterReferenceCondition(selectionItemRefCode);
			} else if (selectionItemRefType.intValue() == ReferenceTypes.CODE_NAME.value) {
				referenceTypeState = ReferenceTypeState.createCodeNameReferenceType(selectionItemRefCode);
			} else if (selectionItemRefType.intValue() == ReferenceTypes.ENUM.value) {
				referenceTypeState = ReferenceTypeState.createEnumReferenceCondition(selectionItemRefCode);
			}

			switch (selection) {
			case SELECTION:
				dataTypeState = DataTypeState.createSelectionItem(referenceTypeState);
				break;
			case SELECTION_RADIO:
				dataTypeState = DataTypeState.createSelectionRadio(referenceTypeState);
				break;
			case SELECTION_BUTTON:
				dataTypeState = DataTypeState.createSelectionButton(referenceTypeState);
				break;
			default:
				break;
			}

		}
		return dataTypeState;
	}
	
	public boolean haveNotParentCode() {
		return this.itemParentCode == null || this.itemParentCode.v().equals("");
	}
	
}
