package nts.uk.ctx.pereg.app.find.person.info.item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.SelectionInitDto;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.PerInfoSelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.PerInfoSelectionItemFinder;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.order.PerInfoItemDefOrder;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetTableItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItemDataType;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItemType;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
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

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private EmpInfoItemDataRepository empInfoRepo;

	@Inject
	private PerInfoItemDataRepository perItemRepo;

	@Inject
	private PersonInfoItemAuthRepository itemAuthRepo;

	@Inject
	private PerInfoInitValueSetItemRepository itemInitRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;
	
	@Inject ICompanyRepo companyRepo;

	public PerInfoItemDefFullEnumDto getAllPerInfoItemDefByCtgId(String perInfoCtgId, int personEmployeeType) {
		List<PerInfoItemDefShowListDto> perInfoItemDefs = this.pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, AppContexts.user().contractCode()).stream()
				.map(item -> {
					return new PerInfoItemDefShowListDto(item.getPerInfoItemDefId(), item.getItemName().v());
				}).collect(Collectors.toList());

		List<EnumConstant> dataTypeEnum = EnumAdaptor.convertToValueNameList(DataTypeValue.class, ukResouce);
		List<EnumConstant> stringItemTypeEnum = EnumAdaptor.convertToValueNameList(StringItemType.class, ukResouce);
		List<EnumConstant> stringItemDataTypeEnum = EnumAdaptor.convertToValueNameList(StringItemDataType.class,
				ukResouce);
		List<EnumConstant> dateItemTypeEnum = EnumAdaptor.convertToValueNameList(DateType.class, ukResouce);
		
		List<PerInfoSelectionItemDto> selectionItemLst = this.selectionItemFinder.getAllSelectionItem();
		return new PerInfoItemDefFullEnumDto(dataTypeEnum, stringItemTypeEnum, stringItemDataTypeEnum, dateItemTypeEnum,
				selectionItemLst, perInfoItemDefs);
	};

	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgId(String perInfoCtgId, String isAbolition) {
		if (isAbolition.equals("true")) {
			return this.pernfoItemDefRep
					.getAllPerInfoItemDefByCategoryIdWithoutSetItem(perInfoCtgId, AppContexts.user().contractCode())
					.stream().map(item -> {
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

	public PerInfoItemChangeDefDto getPerInfoItemDefByIdOfOtherCompany(String perInfoItemDefId,
			int personEmployeeType) {
		
		PerInfoItemDefDto itemDefDto = this.pernfoItemDefRep
				.getPerInfoItemDefById(perInfoItemDefId, AppContexts.user().contractCode()).map(item -> {
					return mappingFromDomaintoDto(item, 0);
				}).orElse(null);

		PerInfoCtgFullDto ctgDto = this.categoryFinder.getPerInfoCtg(itemDefDto.getPerInfoCtgId());

		String itemDefaultName = this.pernfoItemDefRep.getItemDefaultName(ctgDto.getCategoryCode(),
				itemDefDto.getItemCode());
		
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		List<SelectionInitDto> selectionDtoList = new ArrayList<>();

		if (itemDefDto.getItemTypeState().getItemType() == ItemType.SINGLE_ITEM.value) {
			ItemTypeStateDto x = itemDefDto.getItemTypeState();
			if (x.getItemType() == ItemType.SINGLE_ITEM.value) {
				SingleItemDto y = (SingleItemDto) x;
				if (y.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value) {
					SelectionItemDto selelection = (SelectionItemDto) y.getDataTypeState();
					if (selelection.getReferenceType().value == ReferenceTypes.CODE_NAME.value) {
						String typeCode = ((CodeNameRefTypeDto) selelection).getTypeCode();
						GeneralDate baseDateConvert = GeneralDate.today();
						String zeroCompanyId = AppContexts.user().zeroCompanyIdInContract();
						String companyId = AppContexts.user().companyId();
						List<Selection> selectionList = new ArrayList<>();
						
						if (personEmployeeType == PersonEmployeeType.PERSON.value) {
							selectionList = this.selectionRepo.getAllSelectionByCompanyId(zeroCompanyId, typeCode,
									baseDateConvert);
						} else {
							selectionList = this.selectionRepo.getAllSelectionByCompanyId(companyId, typeCode,
									baseDateConvert);
						}
						selectionDtoList = selectionList.stream().map(c -> SelectionInitDto.fromDomainSelection1(c))
								.collect(Collectors.toList());
					}
				}
			}
		}
		
		return new PerInfoItemChangeDefDto(itemDefDto.getId(), itemDefDto.getPerInfoCtgId(),
				itemDefDto.getItemCode(), itemDefDto.getItemName(), itemDefaultName, itemDefDto.getIsAbolition(),
				itemDefDto.getIsFixed(), itemDefDto.getIsRequired(), itemDefDto.getSystemRequired(),
				itemDefDto.getRequireChangable(), 0 , itemDefDto.getSelectionItemRefType(),
				itemDefDto.getItemTypeState(), selectionItemRefTypes,
				selectionDtoList.size() > 0 ? selectionDtoList.get(0).getSelectionItemName() : " ",
				// sua loi them sel item lst
				selectionDtoList, true, itemDefDto.isCanAbolition());
		
	}

	/**
	 * get item detail for cps005
	 * @param perInfoItemDefId
	 * @param personEmployeeType
	 * @return
	 */
	public PerInfoItemChangeDefDto getPerInfoItemDefById(String perInfoItemDefId, int personEmployeeType) {
		String zeroCompanyId = AppContexts.user().zeroCompanyIdInContract();
		String contractCd = AppContexts.user().contractCode();

		Optional<PersonInfoItemDefinition> itemDefinitionOpt = this.pernfoItemDefRep
				.getPerInfoItemDefById(perInfoItemDefId, AppContexts.user().contractCode());
		
		boolean enable = true;
		if (itemDefinitionOpt.isPresent()) {

			List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class,
					ukResouce);
			
			List<String> companyIdList = companyRepo.acquireAllCompany();
			PersonInfoCategory category = this.perInfoCtgRep.getPerInfoCategory(itemDefinitionOpt.get().getPerInfoCategoryId(), contractCd)
					.orElse(null);
			List<String> perInfoCtgIds = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList,
					category.getCategoryCode().v());
			
			String itemCode = itemDefinitionOpt.get().getItemCode().v();
			if ( empInfoRepo.hasItemData(itemCode, perInfoCtgIds) || perItemRepo.hasItemData(perInfoCtgIds, itemCode)) {
				enable = false;
			}
			PerInfoItemDefDto itemDefDto = mappingFromDomaintoDto(itemDefinitionOpt.get(), 0);

			// get selection list
			List<SelectionInitDto> selectionDtoList = new ArrayList<>();
			if (itemDefDto.getItemTypeState().getItemType() == 2) {
				ItemTypeStateDto x = itemDefDto.getItemTypeState();
				if (x.getItemType() == 2) {
					SingleItemDto y = (SingleItemDto) x;
					if (y.getDataTypeState().getDataTypeValue() == 6) {
						SelectionItemDto selelection = (SelectionItemDto) y.getDataTypeState();
						if (selelection.getReferenceType().value == 2) {
							String typeCode = ((CodeNameRefTypeDto) selelection).getTypeCode();
							List<Selection> selectionList = this.selectionRepo.getAllSelectionByCompanyId(zeroCompanyId,
									typeCode, GeneralDate.today());
							selectionDtoList = selectionList.stream().map(c -> SelectionInitDto.fromDomainSelection1(c))
									.collect(Collectors.toList());
						}
					}
				}
			}

			return new PerInfoItemChangeDefDto(itemDefDto.getId(), itemDefDto.getPerInfoCtgId(),
					itemDefDto.getItemCode(), itemDefDto.getItemName(), "", itemDefDto.getIsAbolition(),
					itemDefDto.getIsFixed(), itemDefDto.getIsRequired(), itemDefDto.getSystemRequired(),
					itemDefDto.getRequireChangable(), 0, itemDefDto.getSelectionItemRefType(),
					itemDefDto.getItemTypeState(), selectionItemRefTypes,
					selectionDtoList.size() > 0 ? selectionDtoList.get(0).getSelectionItemName() : " ",
					// sua loi them sel item lst
					selectionDtoList, enable, itemDefDto.isCanAbolition());
		} else {
			return null;
		}
	};

	public List<PerInfoItemDefDto> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return this.pernfoItemDefRep
				.getPerInfoItemDefByListId(listItemDefId, AppContexts.user().contractCode()).stream()
				.map(item -> {
					int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(item.getPerInfoCategoryId(),
							item.getPerInfoItemDefId());

					return mappingFromDomaintoDto(item, dispOrder);
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
	
	// Function get List Category Combobox CPS007
	public Map<String, List<Object[]>> mapCategoryIdAndLstItemDf(List<String> lstPerInfoCtgId) {
		
		return this.pernfoItemDefRep.getAllPerInfoItemDefByListCategoryId(lstPerInfoCtgId, AppContexts.user().contractCode());
		
	};

	// Function get item used for Layout
	public List<PerInfoItemDefDto> getAllPerInfoItemUsedByCtgIdForLayout(String perInfoCtgId) {
		List<PersonInfoItemDefinition> itemDefs = this.pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, AppContexts.user().contractCode()).stream()
				.filter(e -> (e.getItemParentCode().equals("") && e.getIsAbolition().value == 0)) // filter
																									// set
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
				.filter(e -> e.getItemParentCode().equals("")).collect(Collectors.toList());

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
		List<PersonInfoItemDefinition> itemDefinition = this.pernfoItemDefRep.getPerInfoItemDefByListIdv2(listItemDefId,
				AppContexts.user().contractCode());

		return itemDefinition.stream().map(i -> {
			int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(i.getPerInfoCategoryId(), i.getPerInfoItemDefId());
			return mappingFromDomaintoDto(i, dispOrder);

		}).collect(Collectors.toList());
	};

	// test bug hieu nang.
	public List<PerInfoItemDefDto> getPerInfoItemDefByIds(List<String> lstItemDefId) {
		String ccd = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		List<String> idsChild = new ArrayList<>();
		List<String> idsChildInChild = new ArrayList<>();
		List<PersonInfoItemDefinition> itemDfChild = new ArrayList<>();
		List<PersonInfoItemDefinition> itemDfChildinChild = new ArrayList<>();

		List<PersonInfoItemDefinition> itemDefinition = pernfoItemDefRep.getPerInfoItemDefByListIdv2(lstItemDefId, ccd);

		List<PerInfoItemDefDto> result = itemDefinition.stream().map(i -> {
			int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(i.getPerInfoCategoryId(), i.getPerInfoItemDefId());
			PerInfoItemDefDto perItemDefdto = mappingFromDomaintoDto(i, dispOrder);

			ItemTypeStateDto typeState = perItemDefdto.getItemTypeState();
			if (typeState != null) {
				int itemType = typeState.getItemType();

				if (itemType == 1 && typeState instanceof SetItemDto) {
					SetItemDto _typeState = (SetItemDto) typeState;

					idsChild.addAll(_typeState.getItems());
				} else if (itemType == 2 && typeState instanceof SingleItemDto) {
					SingleItemDto _typeState = (SingleItemDto) typeState;
					if (_typeState.getDataTypeState() instanceof RelatedCategoryDto) {
						RelatedCategoryDto relateDto = (RelatedCategoryDto) _typeState.getDataTypeState();

						idsChild.addAll(
								this.pernfoItemDefRep.getAllItemIdsByCtgCode(companyId, relateDto.getRelatedCtgCode()));

						// change code to id
						relateDto.setRelatedCtgCode(perInfoCtgRep.getCatId(companyId, relateDto.getRelatedCtgCode()));
					}
				} else if (itemType == 3 && typeState instanceof SetTableItemDto) {
					SetTableItemDto _typeState = (SetTableItemDto) typeState;

					idsChild.addAll(_typeState.getItems());
				}
			}

			return perItemDefdto;
		}).collect(Collectors.toList());

		if (!idsChild.isEmpty()) {
			itemDfChild = this.pernfoItemDefRep.getPerInfoItemDefByListIdv2(idsChild,
					AppContexts.user().contractCode());

			List<PerInfoItemDefDto> listItemDefDtoChild = itemDfChild.stream().map(i -> {
				int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(i.getPerInfoCategoryId(),
						i.getPerInfoItemDefId());
				PerInfoItemDefDto perItemDefdtochild = mappingFromDomaintoDto(i, dispOrder);

				if (perItemDefdtochild.getItemTypeState().getItemType() == 1
						&& (((SetItemDto) perItemDefdtochild.getItemTypeState()).getItems() != null)) {
					idsChildInChild.addAll(((SetItemDto) perItemDefdtochild.getItemTypeState()).getItems());
				}

				return perItemDefdtochild;
			}).collect(Collectors.toList());

			result.addAll(listItemDefDtoChild);
		}

		if (!idsChildInChild.isEmpty()) {
			itemDfChildinChild = this.pernfoItemDefRep.getPerInfoItemDefByListIdv2(idsChildInChild,
					AppContexts.user().contractCode());

			List<PerInfoItemDefDto> listItemDefDtoChildInChild = itemDfChildinChild.stream().map(i -> {
				int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(i.getPerInfoCategoryId(),
						i.getPerInfoItemDefId());
				return mappingFromDomaintoDto(i, dispOrder);

			}).collect(Collectors.toList());

			result.addAll(listItemDefDtoChildInChild);
		}

		return result.stream().collect(Collectors.groupingBy(PerInfoItemDefDto::getId)).values().stream().map(m -> {
			return m.stream().filter(f -> m.indexOf(f) == 0).collect(Collectors.toList());
		}).flatMap(f -> f.stream())
				.sorted(Comparator.comparing(PerInfoItemDefDto::getDispOrder, Comparator.nullsLast(Integer::compareTo)))
				.collect(Collectors.toList());
	}

	// return list id of item definition if it's require;
	public List<String> getRequiredIds() {
		String companyId = AppContexts.user().companyId();
		String contractCd = AppContexts.user().contractCode();
		return this.pernfoItemDefRep.getRequiredIds(contractCd, companyId);
	}

	// mapping data from domain to DTO

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

	public PerInfoItemDefDto mappingFromDomaintoDto(PersonInfoItemDefinition itemDef, int dispOrder) {
		
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		
		PerInfoItemDefDto dto = new PerInfoItemDefDto(itemDef);
		dto.setDispOrder(dispOrder);
		dto.setSelectionItemRefTypes(selectionItemRefTypes);
		return dto;
	}

	public static ItemTypeStateDto createItemTypeStateDto(ItemTypeState itemTypeState) {
		ItemType itemType = itemTypeState.getItemType();

		if (itemType == ItemType.SINGLE_ITEM) {
			SingleItem singleItemDom = (SingleItem) itemTypeState;
			DataTypeStateDto dataTypeStateDto = DataTypeStateDto.createDto(singleItemDom.getDataTypeState());
			return ItemTypeStateDto.createSingleItemDto(dataTypeStateDto);
		} else if (itemType == ItemType.SET_ITEM) {
			SetItem setItemDom = (SetItem) itemTypeState;
			return ItemTypeStateDto.createSetItemDto(setItemDom.getItems());
		} else {
			SetTableItem setItemDom = (SetTableItem) itemTypeState;
			return ItemTypeStateDto.createSetTableItemDto(setItemDom.getItems());
		}
	}

	public boolean checkExistedSelectionItemId(String selectionItemId) {
		return this.pernfoItemDefRep.checkExistedSelectionItemId(selectionItemId);
	}

	// lanlt start
	// return list id of item definition if it's require;
	public List<ItemRequiredBackGroud> getAllRequiredIdsByCompanyID() {
		String companyId = AppContexts.user().companyId();
		String contractCd = AppContexts.user().contractCode();
		List<ItemRequiredBackGroud> itemRequiredLst = new ArrayList<>();
		this.pernfoItemDefRep.getAllRequiredIds(contractCd, companyId).stream().forEach(item -> {

			ItemRequiredBackGroud itemNameRequired = new ItemRequiredBackGroud();
			itemNameRequired.setRowId(item);
			itemNameRequired.setColumnKey("itemName");
			itemNameRequired.setState(toList("ntsgrid-alarm"));

			ItemRequiredBackGroud setting = new ItemRequiredBackGroud();
			setting.setRowId(item);
			setting.setColumnKey("setting");
			setting.setState(toList("ntsgrid-alarm"));

			ItemRequiredBackGroud other = new ItemRequiredBackGroud();
			other.setRowId(item);
			other.setColumnKey("otherAuth");
			other.setState(toList("ntsgrid-alarm"));

			itemRequiredLst.add(itemNameRequired);
			itemRequiredLst.add(setting);
			itemRequiredLst.add(other);

		});
		return itemRequiredLst;
	}

	/**
	 * @author lanlt dung cho man hinh B cua CPS009 getAllItemRequiredIdsByCtgId
	 * @param ctgId
	 * @return
	 */
	public List<ItemRequiredBackGroud> getAllItemRequiredIdsByCtgId(String ctgId) {
		String contractCd = AppContexts.user().contractCode();
		List<ItemRequiredBackGroud> itemRequiredLst = new ArrayList<>();

		List<String> itemX = this.pernfoItemDefRep.getAllRequiredIdsByCtgId(contractCd, ctgId);
		itemX.stream().forEach(item -> {

			ItemRequiredBackGroud itemNameRequired = new ItemRequiredBackGroud();
			itemNameRequired.setRowId(item);
			itemNameRequired.setColumnKey("itemName");
			itemNameRequired.setState(toList("ntsgrid-alarm"));

			ItemRequiredBackGroud setting = new ItemRequiredBackGroud();
			setting.setRowId(item);
			setting.setColumnKey("setting");
			setting.setState(toList("ntsgrid-alarm"));

			ItemRequiredBackGroud other = new ItemRequiredBackGroud();
			other.setRowId(item);
			other.setColumnKey("otherAuth");
			other.setState(toList("ntsgrid-alarm"));

			itemRequiredLst.add(itemNameRequired);
			itemRequiredLst.add(setting);
			itemRequiredLst.add(other);

		});
		return itemRequiredLst;
	}

	private List<String> toList(String... item) {
		return Stream.of(item).collect(Collectors.toCollection(ArrayList::new));
	}

	public boolean isCheckData(String itemId) {
		String contractCd = AppContexts.user().contractCode();

		List<String> companyIdList = companyRepo.acquireAllCompany();
		PersonInfoItemDefinition oldItem = this.pernfoItemDefRep.getPerInfoItemDefById(itemId, contractCd).orElse(null);
		PersonInfoCategory category = this.perInfoCtgRep.getPerInfoCategory(oldItem.getPerInfoCategoryId(), contractCd)
				.orElse(null);

		if (category == null) {
			return false;
		}

		List<String> perInfoCtgIds = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList,
				category.getCategoryCode().v());
		
		String itemCode = oldItem.getItemCode().v();
		
		if ( itemAuthRepo.hasItemData(itemCode, perInfoCtgIds)) {
			return true;
		}
		if ( itemInitRepo.hasItemData(itemCode, perInfoCtgIds)) {
			return true;
		}
		
		if ( empInfoRepo.hasItemData(itemCode, perInfoCtgIds)) {
			return true;
		}
		
		if ( perItemRepo.hasItemData(perInfoCtgIds, itemCode)) {
			return true;
		}

		return false;
	}
	// lanlt end

	/**
	 * 
	 * @param ctgCd
	 * @return
	 */
	public List<SimpleItemDef> getSingpleItemDef(String ctgCd) {
		List<PersonInfoItemDefinition> itemDefs = this.pernfoItemDefRep.getPerInfoItemByCtgCd(ctgCd,
				AppContexts.user().companyId());

		return itemDefs.stream().map(x -> new SimpleItemDef(x.getItemCode().v(), x.getItemName().v(),
				x.getIsAbolition() == IsAbolition.NOT_ABOLITION)).collect(Collectors.toList());
	}
	

	public List<ItemOrder> getAllItemOrderByCtgId(String ctgId, List<String> itemId, String ctgCode) {
		String contractCd = AppContexts.user().contractCode();
		List<PersonInfoItemDefinition> itemLst = this.pernfoItemDefRep.getItemLstByListId(itemId, ctgId, ctgCode,
				contractCd);
		List<PerInfoItemDefOrder> itemOrder = this.pernfoItemDefRep.getItemOrderByCtgId(ctgId);
		return itemLst.stream().map(c -> {
			PerInfoItemDefOrder io = itemOrder.stream()
					.filter(order -> order.getPerInfoItemDefId().equals(c.getPerInfoItemDefId())).findFirst().get();
				return new ItemOrder(io.getPerInfoItemDefId(), io.getPerInfoCtgId(), c.getItemCode().v(),
						c.getItemParentCode().v(), io.getDispOrder().v(), io.getDisplayOrder().v());
		}).collect(Collectors.toList());

	}
}
