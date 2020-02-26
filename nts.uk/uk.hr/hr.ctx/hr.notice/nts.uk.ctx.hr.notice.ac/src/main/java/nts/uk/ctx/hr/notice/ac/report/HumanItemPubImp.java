package nts.uk.ctx.hr.notice.ac.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.notice.dom.report.valueImported.DataTypeStateImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.DateRangeItemImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.HumanItemPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ItemTypeStateImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.PerInfoItemDefImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.SelectionItemImport;
import nts.uk.ctx.pereg.pub.person.info.item.CodeNameRefTypeExport;
import nts.uk.ctx.pereg.pub.person.info.item.DataTypeStateExport;
import nts.uk.ctx.pereg.pub.person.info.item.DataTypeValueExport;
import nts.uk.ctx.pereg.pub.person.info.item.DateItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.DateRangeItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.EnumRefConditionExport;
import nts.uk.ctx.pereg.pub.person.info.item.ItemTypeExport;
import nts.uk.ctx.pereg.pub.person.info.item.ItemTypeStateExport;
import nts.uk.ctx.pereg.pub.person.info.item.MasterRefConditionExport;
import nts.uk.ctx.pereg.pub.person.info.item.NumericButtonExport;
import nts.uk.ctx.pereg.pub.person.info.item.NumericItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.PerInfoItemDefExport;
import nts.uk.ctx.pereg.pub.person.info.item.PersonInfoItemPub;
import nts.uk.ctx.pereg.pub.person.info.item.ReadOnlyButtonExport;
import nts.uk.ctx.pereg.pub.person.info.item.ReadOnlyExport;
import nts.uk.ctx.pereg.pub.person.info.item.ReferenceTypesExport;
import nts.uk.ctx.pereg.pub.person.info.item.RelatedCategoryExport;
import nts.uk.ctx.pereg.pub.person.info.item.SelectionItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.SetItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.SetTableItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.SingleItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.StringItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.TimeItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.TimePointItemExport;

@Stateless
public class HumanItemPubImp implements HumanItemPub{
	
	@Inject
	private PersonInfoItemPub itemPub;

	@Override
	public List<PerInfoItemDefImport> getAll(List<String> itemIds) {
		List<PerInfoItemDefExport> itemExports = itemPub.getPerInfoItemDefByListIdForLayout(itemIds);
		if (CollectionUtil.isEmpty(itemExports))
			return new ArrayList<>();
		return itemExports.stream().map(c -> {
			return convertFromExport(c);
		}).collect(Collectors.toList());
	}
	
	public PerInfoItemDefImport convertFromExport(PerInfoItemDefExport export) {
		PerInfoItemDefImport result = new PerInfoItemDefImport(export.getId(), export.getPerInfoCtgId(),
				export.getItemCode(), export.getItemParentCode(), export.getItemName(), export.getIsAbolition(),
				export.getIsFixed(), export.getIsRequired(), export.getSystemRequired(), export.getRequireChangable(),
				null, export.getResourceId(), export.getDispOrder(), export.getSelectionItemRefType(),
				export.getSelectionItemRefTypes(), export.isCanAbolition());
		ItemTypeStateExport itemTypeState = export.getItemTypeState();

		if (itemTypeState.getItemType() == ItemTypeExport.SINGLE_ITEM.value) {
			SingleItemExport singleItemDom = (SingleItemExport) itemTypeState;
			DataTypeStateImport dataTypeStateImport = createDto(singleItemDom.getDataTypeState());
			result.setItemTypeState(ItemTypeStateImport.createSingleItemDto(dataTypeStateImport));
		} else if (itemTypeState.getItemType() == ItemTypeExport.SET_ITEM.value) {
			SetItemExport setItemDom = (SetItemExport) itemTypeState;
			result.setItemTypeState(ItemTypeStateImport.createSetItemDto(setItemDom.getItems()));
		} else {
			SetTableItemExport setItemDom = (SetTableItemExport) itemTypeState;
			result.setItemTypeState(ItemTypeStateImport.createSetTableItemDto(setItemDom.getItems()));
		}
		return result;
	}
	
	public static DataTypeStateImport createDto(DataTypeStateExport dataTypeState) {
		int dataTypeValue = dataTypeState.getDataTypeValue();
		switch (dataTypeValue) {
		case 1:
			StringItemExport strItem = (StringItemExport) dataTypeState;
			return DataTypeStateImport.createStringItemDto(strItem.getStringItemLength(),
					strItem.getStringItemType(), strItem.getStringItemDataType());
		case 2:
			NumericItemExport numItem = (NumericItemExport) dataTypeState;
			Integer decimalPart = numItem.getDecimalPart() == null? null: numItem.getDecimalPart();
			
			BigDecimal numericItemMin = numItem.getNumericItemMin() != null ? numItem.getNumericItemMin() : null;
			BigDecimal numericItemMax = numItem.getNumericItemMax() != null ? numItem.getNumericItemMax() : null;
			return DataTypeStateImport.createNumericItemDto(numItem.getNumericItemMinus(),
					numItem.getNumericItemAmount(), numItem.getIntegerPart(), decimalPart,
					numericItemMin, numericItemMax);
		case 3:
			DateItemExport dItem = (DateItemExport) dataTypeState;
			return DataTypeStateImport.createDateItemDto(dItem.getDateItemType());
		case 4:
			TimeItemExport tItem = (TimeItemExport) dataTypeState;
			return DataTypeStateImport.createTimeItemDto(tItem.getMax(), tItem.getMin());
		case 5:
			TimePointItemExport tPointItem = (TimePointItemExport) dataTypeState;
			return DataTypeStateImport.createTimePointItemDto((int) tPointItem.getTimePointItemMin(),
					(int) tPointItem.getTimePointItemMax());
		case 6:
			SelectionItemExport selItemExport = (SelectionItemExport) dataTypeState;
			return createSelectionItemDto(selItemExport);
		case 7:
			SelectionItemExport rItem = (SelectionItemExport) dataTypeState;
			return createSelectionRadioDto(rItem);

		case 8:
			SelectionItemExport bItem = (SelectionItemExport) dataTypeState;
			return createSelectionButtonDto(bItem);

		case 9:
			ReadOnlyExport rOnlyItem = (ReadOnlyExport) dataTypeState;
			return DataTypeStateImport.createReadOnly(rOnlyItem.getReadText());

		case 10:
			RelatedCategoryExport reCtgDto = (RelatedCategoryExport) dataTypeState;
			return DataTypeStateImport.createRelatedCategory(reCtgDto.getRelatedCtgCode());

		case 11:
			NumericButtonExport numberButton = (NumericButtonExport) dataTypeState;
			BigDecimal numericButtonMin = numberButton.getNumericItemMin() != null ? numberButton.getNumericItemMin() : null;
			BigDecimal numericButtonMax = numberButton.getNumericItemMax() != null ? numberButton.getNumericItemMax() : null;
			return DataTypeStateImport.createNumericButtonDto(numberButton.getNumericItemMinus(),
					numberButton.getNumericItemAmount(), numberButton.getIntegerPart(), numberButton.getDecimalPart(),
					numericButtonMin, numericButtonMax);

		case 12:
			ReadOnlyButtonExport rOnlyButton = (ReadOnlyButtonExport) dataTypeState;
			return DataTypeStateImport.createReadOnlyButton(rOnlyButton.getReadText());
		default:
			return null;
		}
	}
	
	public static SelectionItemImport createFromJavaType(SelectionItemExport referenceTypeState, int dataTypeValue) {
		ReferenceTypesExport refType = referenceTypeState.getReferenceType();

		if (refType == ReferenceTypesExport.DESIGNATED_MASTER) {
			MasterRefConditionExport masterRef = (MasterRefConditionExport) referenceTypeState;
			return SelectionItemImport.createMasterRefDto(masterRef.getMasterType(), dataTypeValue);
		} else if (refType == ReferenceTypesExport.CODE_NAME) {
			CodeNameRefTypeExport codeNameRef = (CodeNameRefTypeExport) referenceTypeState;
			return SelectionItemImport.createCodeNameRefDto(codeNameRef.getTypeCode(), dataTypeValue);
		} else {
			EnumRefConditionExport enumRef = (EnumRefConditionExport) referenceTypeState;
			return SelectionItemImport.createEnumRefDto(enumRef.getEnumName(), dataTypeValue);
		}
	}
	
	public static DataTypeStateImport createSelectionItemDto(SelectionItemExport refTypeState) {
		return createFromJavaType(refTypeState, DataTypeValueExport.SELECTION.value);

	}

	public static DataTypeStateImport createSelectionButtonDto(SelectionItemExport refTypeState) {
		return createFromJavaType(refTypeState, DataTypeValueExport.SELECTION_BUTTON.value);

	}

	public static DataTypeStateImport createSelectionRadioDto(SelectionItemExport refTypeState) {
		return createFromJavaType(refTypeState, DataTypeValueExport.SELECTION_RADIO.value);

	}
	
	public static DateRangeItemImport createObjectFromObjectDomain(DateRangeItemExport domain){
		return new DateRangeItemImport(domain.getPersonInfoCtgId(), domain.getStartDateItemId(),
				domain.getEndDateItemId(), domain.getDateRangeItemId());
	}

	@Override
	public List<String> getAllItemIds(String cid, List<String> ctgCodes, List<String> itemCds) {
		return this.itemPub.getAllItemIds(cid, ctgCodes, itemCds);
	}

	@Override
	public String getCategoryName(String cid, String categoryCode) {
		return this.itemPub.getCategoryName(cid, categoryCode);
	}

	@Override
	public DateRangeItemImport getDateRangeItemByCtgId(String categoryId) {
		DateRangeItemExport dateRange = this.itemPub.getDateRangeItemByCtgId(categoryId);
		return new DateRangeItemImport(dateRange.getPersonInfoCtgId(), dateRange.getStartDateItemId(),
				dateRange.getEndDateItemId(), dateRange.getStartDateItemId());
	}

	@Override
	public String getItemDfId(String ctgId, String itemCd) {
		String itemDfID = itemPub.getItemDfId(ctgId, itemCd);
		return itemDfID;
	}
	
}
