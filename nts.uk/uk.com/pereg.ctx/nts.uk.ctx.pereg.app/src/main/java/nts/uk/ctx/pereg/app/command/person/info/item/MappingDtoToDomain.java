package nts.uk.ctx.pereg.app.command.person.info.item;

import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;
import nts.uk.ctx.pereg.dom.person.info.item.IsRequired;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

public class MappingDtoToDomain {

	private final static String ITEM_NAME_START_DATE = "開始日";
	private final static String ITEM_NAME_END_DATE = "終了日";
	private final static String ITEM_NAME_PERIOD = "期間";

	public static PersonInfoItemDefinition mappingFromDomaintoDtoForStartDate(AddItemCommand addItemCommand) {
		PersonInfoItemDefinition itemDef = PersonInfoItemDefinition.createFromJavaType(addItemCommand.getPerInfoCtgId(),
				addItemCommand.getItemCode(), addItemCommand.getItemParentCode(), ITEM_NAME_START_DATE,
				IsAbolition.NOT_ABOLITION.value, IsFixed.FIXED.value, IsRequired.REQUIRED.value);
		itemDef.setItemTypeState(createItemTypeState(addItemCommand, ItemType.SINGLE_ITEM, DataTypeValue.DATE));
		return itemDef;
	}

	public static PersonInfoItemDefinition mappingFromDomaintoDtoForEndtDate(AddItemCommand addItemCommand) {
		PersonInfoItemDefinition itemDef = PersonInfoItemDefinition.createFromJavaType(addItemCommand.getPerInfoCtgId(),
				addItemCommand.getItemCode(), addItemCommand.getItemParentCode(), ITEM_NAME_END_DATE,
				IsAbolition.NOT_ABOLITION.value, IsFixed.FIXED.value, IsRequired.REQUIRED.value);
		itemDef.setItemTypeState(createItemTypeState(addItemCommand, ItemType.SINGLE_ITEM, DataTypeValue.DATE));
		return itemDef;
	}

	public static PersonInfoItemDefinition mappingFromDomaintoDtoForPeriod(AddItemCommand addItemCommand) {
		PersonInfoItemDefinition itemDef = PersonInfoItemDefinition.createFromJavaType(addItemCommand.getPerInfoCtgId(),
				addItemCommand.getItemCode(), addItemCommand.getItemParentCode(), ITEM_NAME_PERIOD,
				IsAbolition.NOT_ABOLITION.value, IsFixed.FIXED.value, IsRequired.REQUIRED.value);
		itemDef.setItemTypeState(createItemTypeState(addItemCommand, ItemType.SET_ITEM, null));
		return itemDef;
	}

	public static PersonInfoItemDefinition mappingFromDomaintoCommand(AddItemCommand addItemCommand) {
		PersonInfoItemDefinition itemDef = PersonInfoItemDefinition.createForAddItem(addItemCommand.getPerInfoCtgId(),
				addItemCommand.getItemCode(), addItemCommand.getItemParentCode(), addItemCommand.getItemName());
		itemDef.setItemTypeState(createItemTypeState(addItemCommand, ItemType.SINGLE_ITEM, null));
		return itemDef;
	}
	
	public static PersonInfoItemDefinition mappingFromDomaintoCommandForUpdate(UpdateItemCommand updateItem, PersonInfoItemDefinition item) {
		item.setItemTypeState(createItemTypeStateForUpdate(updateItem));
		return item;
	}

	private static ItemTypeState createItemTypeState(AddItemCommand addItemCommand, ItemType itemType,
			DataTypeValue dataTypeValue) {
		if (itemType == ItemType.SINGLE_ITEM) {
			return ItemTypeState.createSingleItem(createDataTypeState(addItemCommand.getSingleItem(), dataTypeValue));
		} else {
			return ItemTypeState.createSetItem(null);
		}
	}

	private static ItemTypeState createItemTypeStateForUpdate(UpdateItemCommand updateItem) {
		return ItemTypeState.createSingleItem(createDataTypeState(updateItem.getSingleItem(), null));
	}

	private static DataTypeState createDataTypeState(SingleItemCommand singleI, DataTypeValue dataTypeValue) {
		if (singleI == null) {
			if (dataTypeValue == DataTypeValue.DATE) {
				return DataTypeState.createDateItem(DateType.YEARMONTHDAY.value);
			}
			return null; 
		}
		switch (singleI.getDataType()) {
		case 1:
			return DataTypeState.createStringItem(singleI.getStringItemLength(), singleI.getStringItemType(),
					singleI.getStringItemDataType());
		case 2:
			return DataTypeState.createNumericItem(singleI.getNumericItemMinus(), singleI.getNumericItemAmount(),
					singleI.getIntegerPart(), singleI.getDecimalPart()== null ? null: singleI.getDecimalPart(), singleI.getNumericItemMin(),
					singleI.getNumericItemMax());
		case 3:
			return DataTypeState.createDateItem(singleI.getDateItemType());
		case 4:
			return DataTypeState.createTimeItem(singleI.getTimeItemMax(), singleI.getTimeItemMin());
		case 5:
			return DataTypeState.createTimePointItem(singleI.getTimePointItemMin(), singleI.getTimePointItemMax());
		case 6:
			ReferenceTypeState referenceType = null;
			referenceType = ReferenceTypeState.createCodeNameReferenceType(singleI.getReferenceCode());
//			if (singleI.getReferenceType() == ReferenceTypes.DESIGNATED_MASTER.value) {
//				referenceType = ReferenceTypeState.createMasterReferenceCondition(singleI.getReferenceCode());
//			} else if (singleI.getReferenceType() == ReferenceTypes.CODE_NAME.value) {
				
//			} else {
//				referenceType = ReferenceTypeState.createEnumReferenceCondition(singleI.getReferenceCode());
//			}
			return DataTypeState.createSelectionItem(referenceType);
		default:
			return null;
		}
	}
}
