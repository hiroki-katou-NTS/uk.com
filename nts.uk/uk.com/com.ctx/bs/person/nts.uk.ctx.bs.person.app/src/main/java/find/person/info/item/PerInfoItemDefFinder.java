package find.person.info.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceType;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.bs.person.dom.person.info.setitem.SetItem;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.bs.person.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.bs.person.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.bs.person.dom.person.info.timepointitem.TimePointItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoItemDefFinder {

	@Inject
	private PernfoItemDefRepositoty pernfoItemDefRep;

	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgId(String perInfoCtgId) {
		return pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE).stream()
				.map(item -> {
					return mappingFromDomaintoDto(item);
				}).collect(Collectors.toList());
	};

	public PerInfoItemDefDto getPerInfoItemDefById(String perInfoItemDefId) {
		return pernfoItemDefRep.getPerInfoItemDefById(perInfoItemDefId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.map(item -> {
					return mappingFromDomaintoDto(item);
				}).orElse(null);
	};

	public List<PerInfoItemDefDto> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return pernfoItemDefRep.getPerInfoItemDefByListId(listItemDefId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.stream().map(item -> {
					return mappingFromDomaintoDto(item);
				}).collect(Collectors.toList());
	};
	
	//Function get data for Layout
	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgIdForLayout(String perInfoCtgId) {
		return pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, AppContexts.user().companyCode()).stream()
				.map(item -> {
					return mappingFromDomaintoDto(item);
				}).collect(Collectors.toList());
	};

	public PerInfoItemDefDto getPerInfoItemDefByIdForLayout(String perInfoItemDefId) {
		return pernfoItemDefRep.getPerInfoItemDefById(perInfoItemDefId, AppContexts.user().companyCode())
				.map(item -> {
					return mappingFromDomaintoDto(item);
				}).orElse(null);
	};

	public List<PerInfoItemDefDto> getPerInfoItemDefByListIdForLayout(List<String> listItemDefId) {
		return pernfoItemDefRep.getPerInfoItemDefByListId(listItemDefId, AppContexts.user().companyCode())
				.stream().map(item -> {
					return mappingFromDomaintoDto(item);
				}).collect(Collectors.toList());
	};

	//mapping data from domain to DTO
	private PerInfoItemDefDto mappingFromDomaintoDto(PersonInfoItemDefinition itemDef) {
		return new PerInfoItemDefDto(itemDef.getPerInfoItemDefId(), itemDef.getPerInfoCategoryId(),
				itemDef.getItemCode().v(), itemDef.getItemName().v(), itemDef.getIsAbolition().value,
				itemDef.getIsFixed().value, itemDef.getIsRequired().value, itemDef.getSystemRequired().value,
				itemDef.getRequireChangable().value, createItemTypeStateDto(itemDef.getItemTypeState()));
	}

	private ItemTypeStateDto createItemTypeStateDto(ItemTypeState itemTypeState) {

		ItemType itemType = itemTypeState.getItemType();
		if (itemType == ItemType.SINGLE_ITEM) {
			SingleItem singleItemDom = (SingleItem) itemTypeState;
			return ItemTypeStateDto.createSingleItemDto(createDataTypeStateDto(singleItemDom.getDataTypeState()));
		} else {
			SetItem setItemDom = (SetItem) itemTypeState;
			return ItemTypeStateDto.createSetItemDto(setItemDom.getItems());
		}
	}

	private DataTypeStateDto createDataTypeStateDto(DataTypeState dataTypeState) {

		int dataTypeValue = dataTypeState.getDataTypeValue().value;
		switch (dataTypeValue) {
		case 1:
			StringItem strItem = (StringItem) dataTypeState;
			return DataTypeStateDto.createStringItemDto(strItem.getStringItemLength().v(),
					strItem.getStringItemType().value, strItem.getStringItemDataType().value);
		case 2:
			NumericItem numItem = (NumericItem) dataTypeState;
			return DataTypeStateDto.createNumericItemDto(numItem.getNumericItemMinus().value,
					numItem.getNumericItemAmount().value, numItem.getIntegerPart().v(), numItem.getDecimalPart().v(),
					numItem.getNumericItemMin().v(), numItem.getNumericItemMax().v());
		case 3:
			DateItem dItem = (DateItem) dataTypeState;
			return DataTypeStateDto.createDateItemDto(dItem.getDateItemType().value);
		case 4:
			TimeItem tItem = (TimeItem) dataTypeState;
			return DataTypeStateDto.createTimeItemDto(tItem.getMax().v(), tItem.getMin().v());
		case 5:
			TimePointItem tPointItem = (TimePointItem) dataTypeState;
			return DataTypeStateDto.createTimePointItemDto(tPointItem.getTimePointItemMin().v(),
					tPointItem.getTimePointItemMax().v());
		case 6:
			SelectionItem sItem = (SelectionItem) dataTypeState;
			return DataTypeStateDto.createSelectionItemDto(createRefTypeStateDto(sItem.getReferenceTypeState()));
		default:
			return null;
		}
	}

	private ReferenceTypeStateDto createRefTypeStateDto(ReferenceTypeState refTypeState) {
		ReferenceType refType = refTypeState.getReferenceType();
		if (refType == ReferenceType.DESIGNATED_MASTER) {
			MasterReferenceCondition masterRef = (MasterReferenceCondition) refTypeState;
			return ReferenceTypeStateDto.createMasterRefDto(masterRef.getMasterType().v());
		} else if (refType == ReferenceType.CODE_NAME) {
			CodeNameReferenceType codeNameRef = (CodeNameReferenceType) refTypeState;
			return ReferenceTypeStateDto.createCodeNameRefDto(codeNameRef.getTypeCode().v());
		} else {
			EnumReferenceCondition enumRef = (EnumReferenceCondition) refTypeState;
			return ReferenceTypeStateDto.createEnumRefDto(enumRef.getEnumName().v());
		}
	}
}
