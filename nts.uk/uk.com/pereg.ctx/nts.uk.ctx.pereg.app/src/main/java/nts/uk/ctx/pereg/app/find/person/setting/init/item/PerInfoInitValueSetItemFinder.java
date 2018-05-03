package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemRequiredBackGroud;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.CategoryStateDto;
import nts.uk.ctx.pereg.dom.common.PredetemineTimeSettingRepo;
import nts.uk.ctx.pereg.dom.common.WorkTimeSettingRepo;
import nts.uk.ctx.pereg.dom.copysetting.item.IsRequired;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemDetail;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class PerInfoInitValueSetItemFinder {

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;
	@Inject
	private ComboBoxRetrieveFactory comboBoxFactory;
	@Inject
	private WorkTimeSettingRepo workTimeSettingRepo;
	@Inject
	private PredetemineTimeSettingRepo predetemineTimeSettingRepo;
	@Inject
	private PerInfoCtgByCompanyRepositoty ctgRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;

	public List<PerInfoInitValueSettingItemDto> getAllItem(String settingId, String perInfoCtgId) {
		List<PerInfoInitValueSetItemDetail> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
		if (item != null && !item.isEmpty()) {
			List<PerInfoInitValueSettingItemDto> itemDto = this.convertItemDtoLst(item, perInfoCtgId);
			return itemDto;
		}

		return new ArrayList<>();
	}

	public CategoryStateDto getAllItemRequired(String settingId, String perInfoCtgId) {
		String companyId = AppContexts.user().companyId();
		String contract = AppContexts.user().contractCode();
		CategoryStateDto ctgState = new CategoryStateDto();
		List<PerInfoInitValueSetItemDetail> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
		PersonInfoCategory ctg = this.ctgRepo.getDetailCategoryInfo(companyId, perInfoCtgId, contract)
				.orElseThrow(null);
		if (item.isEmpty())
			return ctgState;
		String ctgCode = item.get(0).getCtgCode();
		List<String> itemList = this.createItemTimePointOfCS00020();
		List<ItemRequiredBackGroud> itemRequired = new ArrayList<>();
		List<ItemDto> itemDto = new ArrayList<>();
		if (item != null) {
			if (ctgCode.equals("CS00020")) {
				item = item.stream().filter(c -> {
					return !itemList.contains(c.getItemCode());
				}).collect(Collectors.toList());
			}
			this.setRequiredBackGround(item, ctg, itemDto, itemRequired);
			ctgState.setItemLst(itemDto);
			ctgState.setItemRequired(itemRequired);
		}
		return ctgState;
	}

	private List<String> toList(String... item) {
		return Stream.of(item).collect(Collectors.toCollection(ArrayList::new));
	}

	public List<PerInfoInitValueSettingItemDto> convertItemDtoLst(List<PerInfoInitValueSetItemDetail> items,
			String categoryCode) {
		String ctgCode = items.get(0).getCtgCode();
		List<PerInfoInitValueSettingItemDto> itemDto = new ArrayList<>();
		// アルゴリズム「勤務開始終了時刻を活性にするかチェックする」
		List<String> itemEven = Arrays.asList("IS00133", "IS00134", "IS00142", "IS00143", "IS00160", "IS00161",
				"IS00169", "IS00170", "IS00178", "IS00179", "IS00151", "IS00152", "IS00196", "IS00197", "IS00205",
				"IS00206", "IS00214", "IS00215", "IS00223", "IS00224", "IS00232", "IS00233", "IS00214", "IS00215",
				"IS00241", "IS00242", "IS00187", "IS00188");

		// アルゴリズム「勤務開始終了時刻を活性にするかチェックする」AND 「アルゴリズム「複数回項目を活性にするかチェックする」
		List<String> itemOld = Arrays.asList("IS00136", "IS00137", "IS00145", "IS00146", "IS00163", "IS00164",
				"IS00172", "IS00173", "IS00181", "IS00182", "IS00154", "IS00155", "IS00199", "IS00200", "IS00208",
				"IS00209", "IS00217", "IS00218", "IS00226", "IS00227", "IS00235", "IS00236", "IS00217", "IS00218",
				"IS00244", "IS00245", "IS00190", "IS00191");
		List<String> itemParents = Arrays.asList("IS00131", "IS00140", "IS00158", "IS00167", "IS00176", "IS00149",
				"IS00194", "IS00203", "IS00212", "IS00221", "IS00230", "IS00239", "IS00185");

		List<PerInfoInitValueSetItemDetail> itemFilter = items.stream().filter(c -> {
			return itemParents.contains(c.getItemCode());
		}).collect(Collectors.toList());
		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(ctgCode,
				companyId);
		boolean isContinious = perInfoCategory.isPresent()
				? (perInfoCategory.get().getCategoryType() == CategoryType.CONTINUOUSHISTORY ? true : false)
				: false;

		if (!perInfoCategory.isPresent()) {
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		PersonEmployeeType personEmployeeType = perInfoCategory.get().getPersonEmployeeType();
		if (ctgCode.equals("CS00001")) {
			items = items.stream().filter(c -> {
				return !c.getItemCode().equals("IS00001");
			}).collect(Collectors.toList());
			
		}
		if (ctgCode.equals("CS00020")) {
			itemDto = items.stream().map(item -> {

				PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(item);
				boolean isEven = itemEven.contains(item.getItemCode());
				boolean isOld = itemOld.contains(item.getItemCode());
				String selectionId = null;
				this.setCompareItemCode(item, itemFilter, dto, selectionId, isOld, isEven);
				if (((item.getItemName().equals("終了日") && isContinious)) || isEven || isOld) {
					dto.setDisableCombox(true);
				}
				return getInitItemDto(dto, personEmployeeType, categoryCode);
			}).collect(Collectors.toList());
		} else {
			itemDto = items.stream().map(c -> {
				PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(c);
				if (c.getItemName().equals("終了日") && isContinious) {
					dto.setDisableCombox(true);
				}
				return getInitItemDto(dto, personEmployeeType, categoryCode);
			}).collect(Collectors.toList());
			;
		}

		return itemDto;

	}

	private PerInfoInitValueSettingItemDto getInitItemDto(PerInfoInitValueSettingItemDto dto,
			PersonEmployeeType personEmployeeType, String categoryCode) {
		int dataType = dto.getDataType();
		if (dataType == DataTypeValue.SELECTION.value || dataType == DataTypeValue.SELECTION_BUTTON.value
				|| dataType == DataTypeValue.SELECTION_RADIO.value) {
			boolean isDataType6 = dataType == DataTypeValue.SELECTION.value;
			dto.setSelection(getSelectionItem(dto, personEmployeeType, isDataType6, categoryCode));
		}
		return dto;
	}

	private List<ComboBoxObject> getSelectionItem(PerInfoInitValueSettingItemDto dto,
			PersonEmployeeType personEmployeeType, boolean isDataType6, String categoryCode) {
		SelectionItemDto selectionItemDto = null;
		ReferenceTypes refenceType = EnumAdaptor.valueOf(dto.getSelectionItemRefType(), ReferenceTypes.class);
		switch (refenceType) {
		case DESIGNATED_MASTER:
			selectionItemDto = SelectionItemDto.createMasterRefDto(dto.getSelectionItemId(),
					dto.getSelectionItemRefType());
			break;
		case CODE_NAME:
			selectionItemDto = SelectionItemDto.createCodeNameRefDto(dto.getSelectionItemId(),
					dto.getSelectionItemRefType());
			break;
		case ENUM:
			selectionItemDto = SelectionItemDto.createEnumRefDto(dto.getSelectionItemId(),
					dto.getSelectionItemRefType());
			break;
		}
		return this.comboBoxFactory.getComboBox(selectionItemDto, AppContexts.user().employeeId(), GeneralDate.today(),
				true, personEmployeeType, isDataType6, categoryCode);
	}

	public List<PerInfoInitValueSettingItemDto> filterItemTimePointOfCS00020(List<PerInfoInitValueSetItemDetail> items,
			String categorycode) {
		String ctgCode = items.get(0).getCtgCode();
		List<String> itemList = this.createItemTimePointOfCS00020();

		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(ctgCode,
				companyId);

		if (!perInfoCategory.isPresent()) {
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		List<PerInfoInitValueSetItemDetail> filteredItems = null;

		if (ctgCode.equals("CS00020")) {
			filteredItems = items.stream().filter(x -> {
				return !itemList.contains(x.getItemCode());
			}).collect(Collectors.toList());
		} else if (ctgCode.equals("CS00001")) {
			filteredItems = items.stream().filter(c -> {
				return !c.getItemCode().equals("IS00001");
			}).collect(Collectors.toList());
		} else {
			filteredItems = items;
		}

		return filteredItems.stream().map(c -> {
			PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(c);
			return getInitItemDto(dto, perInfoCategory.get().getPersonEmployeeType(), categorycode);
		}).collect(Collectors.toList());
	}

	public List<String> createItemTimePointOfCS00020() {
		return this.toList("IS00133", "IS00134", "IS00136", "IS00137", "IS00142", "IS00143", "IS00145", "IS00146",
				"IS00160", "IS00161", "IS00163", "IS00164", "IS00169", "IS00170", "IS00172", "IS00173", "IS00178",
				"IS00179", "IS00181", "IS00182", "IS00151", "IS00152", "IS00154", "IS00155", "IS00196", "IS00197",
				"IS00199", "IS00200", "IS00205", "IS00206", "IS00208", "IS00209", "IS00214", "IS00215", "IS00217",
				"IS00218", "IS00223", "IS00224", "IS00226", "IS00227", "IS00232", "IS00233", "IS00235", "IS00236",
				"IS00214", "IS00215", "IS00217", "IS00218", "IS00241", "IS00242", "IS00244", "IS00245", "IS00187",
				"IS00188", "IS00190", "IS00191");
	}

	private void setCompareItemCode(PerInfoInitValueSetItemDetail item, List<PerInfoInitValueSetItemDetail> itemFilter,
			PerInfoInitValueSettingItemDto dto, String selectionId, boolean isOld, boolean isEven) {
		if (item.getItemCode().equals("IS00133") || item.getItemCode().equals("IS00134")
				|| item.getItemCode().equals("IS00136") || item.getItemCode().equals("IS00137")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00131");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00142") || item.getItemCode().equals("IS00143")
				|| item.getItemCode().equals("IS00145") || item.getItemCode().equals("IS00146")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00140");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00160") || item.getItemCode().equals("IS00161")
				|| item.getItemCode().equals("IS00163") || item.getItemCode().equals("IS00164")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00158");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00169") || item.getItemCode().equals("IS00170")
				|| item.getItemCode().equals("IS00172") || item.getItemCode().equals("IS00173")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00167");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00178") || item.getItemCode().equals("IS00178")
				|| item.getItemCode().equals("IS00181") || item.getItemCode().equals("IS00182")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00176");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00151") || item.getItemCode().equals("IS00152")
				|| item.getItemCode().equals("IS00154") || item.getItemCode().equals("IS00155")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00149");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00196") || item.getItemCode().equals("IS00197")
				|| item.getItemCode().equals("IS00199") || item.getItemCode().equals("IS00200")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00194");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00205") || item.getItemCode().equals("IS00206")
				|| item.getItemCode().equals("IS00208") || item.getItemCode().equals("IS00209")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00203");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00214") || item.getItemCode().equals("IS00215")
				|| item.getItemCode().equals("IS00217") || item.getItemCode().equals("IS00218")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00212");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00223") || item.getItemCode().equals("IS00224")
				|| item.getItemCode().equals("IS00226") || item.getItemCode().equals("IS00227")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00221");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00232") || item.getItemCode().equals("IS00233")
				|| item.getItemCode().equals("IS00235") || item.getItemCode().equals("IS00236")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00230");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00241") || item.getItemCode().equals("IS00242")
				|| item.getItemCode().equals("IS00244") || item.getItemCode().equals("IS00245")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00239");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}

		if (item.getItemCode().equals("IS00187") || item.getItemCode().equals("IS00188")
				|| item.getItemCode().equals("IS00190") || item.getItemCode().equals("IS00191")) {
			List<PerInfoInitValueSetItemDetail> itemParent = itemFilter.stream().filter(c -> {
				return c.getItemCode().equals("IS00185");
			}).collect(Collectors.toList());
			setEnableControl(item, itemParent, dto, selectionId, isOld, isEven);
		}
	}

	private void setEnableControl(PerInfoInitValueSetItemDetail item, List<PerInfoInitValueSetItemDetail> itemParent,
			PerInfoInitValueSettingItemDto dto, String selectionId, boolean isOld, boolean isEven) {

		if (itemParent.size() > 0) {
			if (itemParent.get(0).getSelectionItemId() != null && itemParent.get(0).getStringValue() != null) {
				selectionId = itemParent.get(0).getStringValue();
			} else {
				selectionId = itemParent.get(0).getSelectionItemId();
			}
			if (isOld) {

				if (selectionId == null) {
					dto.setEnableControl(false);
				} else {
					dto.setEnableControl(this.predetemineTimeSettingRepo.isWorkingTwice(selectionId)
							&& this.workTimeSettingRepo.isFlowWork(selectionId));
				}
			}

			if (isEven) {
				if (selectionId == null) {
					dto.setEnableControl(false);
				} else {
					dto.setEnableControl(this.workTimeSettingRepo.isFlowWork(selectionId));
				}
			}
		}
	}

	private void setRequiredBackGround(List<PerInfoInitValueSetItemDetail> item, PersonInfoCategory ctg,
			List<ItemDto> itemDto, List<ItemRequiredBackGroud> itemRequired) {
		item.stream().forEach(c -> {
			boolean checkDisable = c.getItemName().equals("終了日")
					&& (ctg != null ? (ctg.getCategoryType() == CategoryType.CONTINUOUSHISTORY ? true : false) : false);
			itemDto.add(new ItemDto(c.getPerInfoItemDefId(), c.getItemName(), false, c.getIsRequired()));
			ItemRequiredBackGroud itemNamebackGroud = new ItemRequiredBackGroud();
			ItemRequiredBackGroud disablebackGroud = new ItemRequiredBackGroud();
			itemNamebackGroud.setColumnKey("itemName");
			itemNamebackGroud.setRowId(c.getPerInfoItemDefId());
			disablebackGroud.setColumnKey("disabled");
			disablebackGroud.setRowId(c.getPerInfoItemDefId());

			if (checkDisable) {
				disablebackGroud.setState(toList("ntsgrid-disable"));
				if (c.getIsRequired() == IsRequired.REQUIRED.value) {
					itemNamebackGroud.setState(toList("requiredCell"));
				}
			} else {
				if (c.getIsRequired() == IsRequired.REQUIRED.value) {
					itemNamebackGroud.setState(toList("requiredCell"));
					disablebackGroud.setState(toList("requiredCell"));
				}
			}
			itemRequired.add(itemNamebackGroud);
			itemRequired.add(disablebackGroud);
		});
	}

}
