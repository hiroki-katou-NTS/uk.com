package find.person.info.item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.info.category.PerInfoCategoryFinder;
import find.person.info.category.PerInfoCtgFullDto;
import find.person.setting.selectionitem.PerInfoSelectionItemDto;
import find.person.setting.selectionitem.PerInfoSelectionItemFinder;
import find.person.setting.selectionitem.selection.SelectionDto;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.bs.person.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.bs.person.dom.person.info.dateitem.DateType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.bs.person.dom.person.info.order.PerInfoItemDefOrder;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.bs.person.dom.person.info.setitem.SetItem;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.bs.person.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.bs.person.dom.person.info.stringitem.StringItemDataType;
import nts.uk.ctx.bs.person.dom.person.info.stringitem.StringItemType;
import nts.uk.ctx.bs.person.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.bs.person.dom.person.info.timepointitem.TimePointItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class PerInfoItemDefFinder {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	I18NResourcesForUK ukResouce;

	@Inject
	private PerInfoCategoryFinder categoryFinder;
	
	@Inject
	private PerInfoSelectionItemFinder selectionItemFinder;

	public PerInfoItemDefFullEnumDto getAllPerInfoItemDefByCtgId(String perInfoCtgId) {
		List<PerInfoItemDefShowListDto> perInfoItemDefs = this.pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE).stream()
				.map(item -> {
					return new PerInfoItemDefShowListDto(item.getPerInfoItemDefId(), item.getItemName().v());
				}).collect(Collectors.toList());
		List<EnumConstant> dataTypeEnum = EnumAdaptor.convertToValueNameList(DataTypeValue.class, ukResouce);
		List<EnumConstant> stringItemTypeEnum = EnumAdaptor.convertToValueNameList(StringItemType.class,
				ukResouce);
		List<EnumConstant> stringItemDataTypeEnum = EnumAdaptor.convertToValueNameList(StringItemDataType.class,
				ukResouce);
		List<EnumConstant> dateItemTypeEnum = EnumAdaptor.convertToValueNameList(DateType.class, ukResouce);
		List<PerInfoSelectionItemDto> selectionItemLst= this.selectionItemFinder.getAllSelectionItem();
		return new PerInfoItemDefFullEnumDto(dataTypeEnum, stringItemTypeEnum, stringItemDataTypeEnum, dateItemTypeEnum,
				selectionItemLst, perInfoItemDefs);
	};

	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgId(String perInfoCtgId, String isAbolition) {
		if (isAbolition.equals("true")) {
			return this.pernfoItemDefRep
					.getAllPerInfoItemDefByCategoryIdWithoutSetItem(perInfoCtgId, AppContexts.user().contractCode()).stream()
					.map(item -> {
						return mappingFromDomaintoDto(item, 0);
					}).collect(Collectors.toList());

		} else {
			return this.pernfoItemDefRep
					.getAllPerInfoItemDefByCategoryIdWithoutAbolition(perInfoCtgId, AppContexts.user().contractCode())
					.stream().map(item -> {
						return mappingFromDomaintoDto(item, 0);
					}).collect(Collectors.toList());

		}

	}

	public PerInfoItemChangeDefDto getPerInfoItemDefByIdOfOtherCompany(String perInfoItemDefId) {

		PerInfoItemDefDto itemDefDto = this.pernfoItemDefRep
				.getPerInfoItemDefById(perInfoItemDefId, AppContexts.user().contractCode()).map(item -> {
					return mappingFromDomaintoDto(item, 0);
				}).orElse(null);

		PerInfoCtgFullDto ctgDto = this.categoryFinder.getPerInfoCtg(itemDefDto.getPerInfoCtgId());

		String itemDefaultName = this.pernfoItemDefRep.getItemDefaultName(ctgDto.getCategoryCode(),
				itemDefDto.getItemCode());
		return mappingFromDomaintoChangeDto(itemDefDto, itemDefaultName, 0);
	};

	private PerInfoItemChangeDefDto mappingFromDomaintoChangeDto(PerInfoItemDefDto itemDefDto, String defaultName,
			int dispOrder) {
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class,
				ukResouce);
		return new PerInfoItemChangeDefDto(itemDefDto.getId(), itemDefDto.getPerInfoCtgId(), itemDefDto.getItemCode(),
				itemDefDto.getItemName(), defaultName, itemDefDto.getIsAbolition(), itemDefDto.getIsFixed(),
				itemDefDto.getIsRequired(), itemDefDto.getSystemRequired(), itemDefDto.getRequireChangable(), dispOrder,
				itemDefDto.getSelectionItemRefType(),
				itemDefDto.getItemTypeState(), selectionItemRefTypes);
	}

	public PerInfoItemDefDto getPerInfoItemDefById(String perInfoItemDefId) {
		PerInfoItemDefDto itemDto = this.pernfoItemDefRep
				.getPerInfoItemDefById(perInfoItemDefId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE).map(item -> {
					return mappingFromDomaintoDto(item, 0);
				}).orElse(null);
		return itemDto;
	};

	public List<PerInfoItemDefDto> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return this.pernfoItemDefRep
				.getPerInfoItemDefByListId(listItemDefId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE).stream()
				.map(item -> {
					return mappingFromDomaintoDto(item, 0);
				}).collect(Collectors.toList());
	};

	// Function get data for Layout
	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgIdForLayout(String perInfoCtgId) {
		List<PersonInfoItemDefinition> itemDefs = this.pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, AppContexts.user().contractCode()).stream()
				.filter(e -> e.getItemParentCode().equals("")) // filter set
																// item or
																// single item
																// (has'nt
																// parent item)
				.collect(Collectors.toList());
		List<PerInfoItemDefOrder> itemOrders = this.pernfoItemDefRep.getPerInfoItemDefOrdersByCtgId(perInfoCtgId);
		return mappingItemAndOrder(itemDefs, itemOrders);
	};
	
	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCatgoryId(String perInfoCtgId) {
		List<PersonInfoItemDefinition> itemDefs = this.pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, AppContexts.user().contractCode()).stream()
				.filter(e -> e.getItemParentCode().equals(""))
				.collect(Collectors.toList());
		List<PerInfoItemDefOrder> itemOrders = this.pernfoItemDefRep.getPerInfoItemDefOrdersByCtgId(perInfoCtgId);
		return mappingItemAndOrder(itemDefs, itemOrders);
	};
	
	public PerInfoItemDefDto getPerInfoItemDefByItemDefId(String perInfoItemDefId) {
		PersonInfoItemDefinition itemDef = this.pernfoItemDefRep
				.getPerInfoItemDefById(perInfoItemDefId, AppContexts.user().contractCode()).orElse(null);
		int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(itemDef.getPerInfoCategoryId(),
				itemDef.getPerInfoItemDefId());
		return mappingFromDomaintoDto(itemDef, dispOrder);
	}
	

	public PerInfoItemDefDto getPerInfoItemDefByIdForLayout(String perInfoItemDefId) {
		PersonInfoItemDefinition itemDef = this.pernfoItemDefRep
				.getPerInfoItemDefById(perInfoItemDefId, AppContexts.user().contractCode()).orElse(null);
		int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(itemDef.getPerInfoCategoryId(),
				itemDef.getPerInfoItemDefId());
		return mappingFromDomaintoDto(itemDef, dispOrder);
	}

	public List<PerInfoItemDefDto> getPerInfoItemDefByListIdForLayout(List<String> listItemDefId) {
		return this.pernfoItemDefRep.getPerInfoItemDefByListId(listItemDefId, AppContexts.user().contractCode())
				.stream().map(i -> {
					int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(i.getPerInfoCategoryId(),
							i.getPerInfoItemDefId());
					return mappingFromDomaintoDto(i, dispOrder);
				}).collect(Collectors.toList());
	};

	// return list id of item definition if it's require;
	public List<String> getRequiredIds() {
		String companyId = AppContexts.user().companyId();
		String contractCd = AppContexts.user().contractCode();
		return this.pernfoItemDefRep.getRequiredIds(contractCd, companyId);
	}

	// mapping data from domain to DTO

	//vinhpx: start
	public List<PerInfoItemDefMapDto> getPerInfoDefById(String perInfoCategoryId){
		String companyId = AppContexts.user().companyId();
		String contractId = AppContexts.user().contractCode();
		return pernfoItemDefRep.getPerInfoItemByCtgId(perInfoCategoryId, 
				companyId, contractId).stream().map(item ->{
					String itemId  = item.getPerInfoItemDefId();
					boolean alreadyCopy = pernfoItemDefRep.countPerInfoItemDefInCopySetting(itemId, companyId) > 0 ? true : false;
					//boolean alreadyCopy = false;
					return new PerInfoItemDefMapDto(itemId, item.getPerInfoCategoryId(),
							item.getItemName().v(), alreadyCopy);
				}).collect(Collectors.toList());
	}
	//vinhpx: end
	
	private List<PerInfoItemDefDto> mappingItemAndOrder(List<PersonInfoItemDefinition> itemDefs,
			List<PerInfoItemDefOrder> itemOrders) {
		return itemDefs.stream().map(i -> {
			PerInfoItemDefOrder itemOrder = itemOrders.stream()
					.filter(o -> o.getPerInfoItemDefId().equals(i.getPerInfoItemDefId())).findFirst().orElse(null);
			if (itemOrder != null) {
				return mappingFromDomaintoDto(i, itemOrder.getDispOrder().v());
			}
			return mappingFromDomaintoDto(i, 0);
		}).collect(Collectors.toList());
	}

	private PerInfoItemDefDto mappingFromDomaintoDto(PersonInfoItemDefinition itemDef, int dispOrder) {
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class,
				ukResouce);
		return new PerInfoItemDefDto(itemDef.getPerInfoItemDefId(), itemDef.getPerInfoCategoryId(),
				itemDef.getItemCode().v(), itemDef.getItemName().v(), itemDef.getIsAbolition().value,
				itemDef.getIsFixed().value, itemDef.getIsRequired().value, itemDef.getSystemRequired().value,
				itemDef.getRequireChangable().value, dispOrder, 
				itemDef.getSelectionItemRefType(),
				createItemTypeStateDto(itemDef.getItemTypeState()),
				selectionItemRefTypes);
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
			BigDecimal numericItemMin = numItem.getNumericItemMin() != null ? numItem.getNumericItemMin().v() : null;
			BigDecimal numericItemMax = numItem.getNumericItemMax() != null ? numItem.getNumericItemMax().v() : null;
			return DataTypeStateDto.createNumericItemDto(numItem.getNumericItemMinus().value,
					numItem.getNumericItemAmount().value, numItem.getIntegerPart().v(), numItem.getDecimalPart().v(),
					numericItemMin, numericItemMax);
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
			return DataTypeStateDto.createSelectionItemDto(sItem.getReferenceTypeState());
		default:
			return null;
		}
	}
	
	

}
