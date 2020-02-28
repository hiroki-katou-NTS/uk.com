package nts.uk.ctx.pereg.pubimp.person.info.item;

import java.math.BigDecimal;

import nts.uk.ctx.pereg.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.NumericButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReadOnly;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReadOnlyButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.RelatedCategory;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionRadio;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetTableItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;
import nts.uk.ctx.pereg.pub.person.info.item.DataTypeStateExport;
import nts.uk.ctx.pereg.pub.person.info.item.DateRangeItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.ItemTypeStateExport;
import nts.uk.ctx.pereg.pub.person.info.item.PerInfoItemDefExport;
import nts.uk.ctx.pereg.pub.person.info.item.SelectionItemExport;
public class ItemConvertDomainToDto {
	
	public static SelectionItemExport createFromJavaType(ReferenceTypeState referenceTypeState, int dataTypeValue) {
		ReferenceTypes refType = referenceTypeState.getReferenceType();

		if (refType == ReferenceTypes.DESIGNATED_MASTER) {
			MasterReferenceCondition masterRef = (MasterReferenceCondition) referenceTypeState;
			return SelectionItemExport.createMasterRefDto(masterRef.getMasterType().v(), dataTypeValue);
		} else if (refType == ReferenceTypes.CODE_NAME) {
			CodeNameReferenceType codeNameRef = (CodeNameReferenceType) referenceTypeState;
			return SelectionItemExport.createCodeNameRefDto(codeNameRef.getTypeCode().v(), dataTypeValue);
		} else {
			EnumReferenceCondition enumRef = (EnumReferenceCondition) referenceTypeState;
			return SelectionItemExport.createEnumRefDto(enumRef.getEnumName().v(), dataTypeValue);
		}
	}
	
	public static DataTypeStateExport createSelectionItemDto(ReferenceTypeState refTypeState) {
		return createFromJavaType(refTypeState, DataTypeValue.SELECTION.value);

	}

	public static DataTypeStateExport createSelectionButtonDto(ReferenceTypeState refTypeState) {
		return createFromJavaType(refTypeState, DataTypeValue.SELECTION_BUTTON.value);

	}

	public static DataTypeStateExport createSelectionRadioDto(ReferenceTypeState refTypeState) {
		return createFromJavaType(refTypeState, DataTypeValue.SELECTION_RADIO.value);

	}
	
	public static DateRangeItemExport createObjectFromObjectDomain(DateRangeItem domain){
		return new DateRangeItemExport(domain.getPersonInfoCtgId(), domain.getStartDateItemId(),
				domain.getEndDateItemId(), domain.getDateRangeItemId());
	}
	
	public static DataTypeStateExport createDto(DataTypeState dataTypeState) {
		DataTypeValue dataTypeValue = dataTypeState.getDataTypeValue();
		switch (dataTypeValue) {
		case STRING:
			StringItem strItem = (StringItem) dataTypeState;
			return DataTypeStateExport.createStringItemDto(strItem.getStringItemLength().v(),
					strItem.getStringItemType().value, strItem.getStringItemDataType().value);
		case NUMERIC:
			NumericItem numItem = (NumericItem) dataTypeState;
			Integer decimalPart = numItem.getDecimalPart() == null? null: numItem.getDecimalPart().v();
			
			BigDecimal numericItemMin = numItem.getNumericItemMin() != null ? numItem.getNumericItemMin().v() : null;
			BigDecimal numericItemMax = numItem.getNumericItemMax() != null ? numItem.getNumericItemMax().v() : null;
			return DataTypeStateExport.createNumericItemDto(numItem.getNumericItemMinus().value,
					numItem.getNumericItemAmount().value, numItem.getIntegerPart().v(), decimalPart,
					numericItemMin, numericItemMax);
		case DATE:
			DateItem dItem = (DateItem) dataTypeState;
			return DataTypeStateExport.createDateItemDto(dItem.getDateItemType().value);
		case TIME:
			TimeItem tItem = (TimeItem) dataTypeState;
			return DataTypeStateExport.createTimeItemDto(tItem.getMax().v(), tItem.getMin().v());
		case TIMEPOINT:
			TimePointItem tPointItem = (TimePointItem) dataTypeState;
			return DataTypeStateExport.createTimePointItemDto(tPointItem.getTimePointItemMin().v(),
					tPointItem.getTimePointItemMax().v());
		case SELECTION:
			SelectionItem sItem = (SelectionItem) dataTypeState;
			return createSelectionItemDto(sItem.getReferenceTypeState());

		case SELECTION_RADIO:
			SelectionRadio rItem = (SelectionRadio) dataTypeState;
			return createSelectionRadioDto(rItem.getReferenceTypeState());

		case SELECTION_BUTTON:
			SelectionButton bItem = (SelectionButton) dataTypeState;
			return createSelectionButtonDto(bItem.getReferenceTypeState());

		case READONLY:
			ReadOnly rOnlyItem = (ReadOnly) dataTypeState;
			return DataTypeStateExport.createReadOnly(rOnlyItem.getReadText().v());

		case RELATE_CATEGORY:
			RelatedCategory reCtgDto = (RelatedCategory) dataTypeState;
			return DataTypeStateExport.createRelatedCategory(reCtgDto.getRelatedCtgCode().v());

		case NUMBERIC_BUTTON:
			NumericButton numberButton = (NumericButton) dataTypeState;
			BigDecimal numericButtonMin = numberButton.getNumericItemMin() != null ? numberButton.getNumericItemMin().v() : null;
			BigDecimal numericButtonMax = numberButton.getNumericItemMax() != null ? numberButton.getNumericItemMax().v() : null;
			return DataTypeStateExport.createNumericButtonDto(numberButton.getNumericItemMinus().value,
					numberButton.getNumericItemAmount().value, numberButton.getIntegerPart().v(), numberButton.getDecimalPart().v(),
					numericButtonMin, numericButtonMax);

		case READONLY_BUTTON:
			ReadOnlyButton rOnlyButton = (ReadOnlyButton) dataTypeState;
			return DataTypeStateExport.createReadOnlyButton(rOnlyButton.getReadText().v());
		default:
			return null;
		}
	}
	
	/**
	 * @param itemDefinition
	 * This constructor initial value from domain
	 * don't initial for dispOrder and selectionItemRefTypes
	 */
	public static PerInfoItemDefExport createPerInfoItemDefExport(PersonInfoItemDefinition itemDefinition) {
		PerInfoItemDefExport dto = new PerInfoItemDefExport();
		dto.setId(itemDefinition.getPerInfoItemDefId());
		dto.setPerInfoCtgId(itemDefinition.getPerInfoCategoryId());
		dto.setItemCode(itemDefinition.getItemCode().v());
		dto.setItemParentCode(itemDefinition.getItemParentCode().v());
		dto.setItemName(itemDefinition.getItemName().v());
		dto.setIsAbolition(itemDefinition.getIsAbolition().value);
		dto.setIsFixed(itemDefinition.getIsFixed().value);
		dto.setIsRequired(itemDefinition.getIsRequired().value);
		dto.setSystemRequired(itemDefinition.getSystemRequired().value);
		dto.setRequireChangable(itemDefinition.getRequireChangable().value);

		ItemType itemType = itemDefinition.getItemTypeState().getItemType();

		if (itemType == ItemType.SINGLE_ITEM) {
			SingleItem singleItemDom = (SingleItem) itemDefinition.getItemTypeState();
			DataTypeStateExport dataTypeStateDto = createDto(singleItemDom.getDataTypeState());
			dto.setItemTypeState(ItemTypeStateExport.createSingleItemDto(dataTypeStateDto));
		} else if (itemType == ItemType.SET_ITEM) {
			SetItem setItemDom = (SetItem) itemDefinition.getItemTypeState();
			dto.setItemTypeState(ItemTypeStateExport.createSetItemDto(setItemDom.getItems()));
		} else {
			SetTableItem setItemDom = (SetTableItem) itemDefinition.getItemTypeState();
			dto.setItemTypeState(ItemTypeStateExport.createSetTableItemDto(setItemDom.getItems()));
		}

		dto.setResourceId(itemDefinition.getResourceId().isPresent() ? itemDefinition.getResourceId().get() : null);
		// will remove
		dto.setSelectionItemRefType(itemDefinition.getSelectionItemRefType());
		dto.setCanAbolition(itemDefinition.isCanAbolition());
		return dto;

	}
	

	
}
