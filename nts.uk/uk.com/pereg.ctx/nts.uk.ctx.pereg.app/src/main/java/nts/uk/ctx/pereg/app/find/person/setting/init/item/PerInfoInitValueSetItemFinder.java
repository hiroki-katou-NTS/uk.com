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
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
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
		List<PerInfoInitValueSetItem> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
		if (item != null && !item.isEmpty()) {
			List<PerInfoInitValueSettingItemDto> itemDto = this.convertItemDtoLst(item);
			return itemDto;
		}

		return new ArrayList<>();
	}

	public CategoryStateDto getAllItemRequired(String settingId, String perInfoCtgId) {
		String companyId = AppContexts.user().companyId();
		String contract = AppContexts.user().contractCode();
		CategoryStateDto ctgState = new CategoryStateDto();
		List<PerInfoInitValueSetItem> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
		PersonInfoCategory ctg = this.ctgRepo.getDetailCategoryInfo(companyId, perInfoCtgId, contract)
				.orElseThrow(null);
		if(item.isEmpty()) return ctgState;
		String ctgCode = item.get(0).getCtgCode();
		List<String> itemList = this.createItemTimePointOfCS00020();
		List<ItemRequiredBackGroud> itemRequired = new ArrayList<>();
		List<ItemDto> itemDto = new ArrayList<>();
		if (item != null) {
			if (ctgCode.equals("CS00020")) {
				item.stream().filter(c -> {
					return !itemList.contains(c.getItemCode());
				}).forEach(c -> {
					boolean checkDisable = c.getItemName().equals("終了日")
							&& (ctg != null ? (ctg.getCategoryType().value == 3 ? true : false) : false);
					itemDto.add(new ItemDto(c.getPerInfoItemDefId(), c.getItemName(), false, c.getIsRequired().value));
					ItemRequiredBackGroud itemNamebackGroud = new ItemRequiredBackGroud();
					ItemRequiredBackGroud disablebackGroud = new ItemRequiredBackGroud();
					itemNamebackGroud.setColumnKey("itemName");
					itemNamebackGroud.setRowId(c.getPerInfoItemDefId());
					disablebackGroud.setColumnKey("disabled");
					disablebackGroud.setRowId(c.getPerInfoItemDefId());
					if (checkDisable) {
						disablebackGroud.setState(toList("ntsgrid-disable"));
						if (c.getIsRequired().value == 1) {
							itemNamebackGroud.setState(toList("requiredCell"));
						} else {
							itemNamebackGroud.setState(toList("notrequiredCell"));
						}
					} else {
						if (c.getIsRequired().value == 1) {
							itemNamebackGroud.setState(toList("requiredCell"));
							disablebackGroud.setState(toList("requiredCell"));
						} else {
							itemNamebackGroud.setState(toList("notrequiredCell"));
							disablebackGroud.setState(toList("notrequiredCell"));
						}
					}
					itemRequired.add(itemNamebackGroud);
					itemRequired.add(disablebackGroud);
				});

			} else {
				item.stream().forEach(c -> {
					boolean checkDisable = c.getItemName().equals("終了日")
							&& (ctg != null ? (ctg.getCategoryType().value == 3 ? true : false) : false);
					itemDto.add(new ItemDto(c.getPerInfoItemDefId(), c.getItemName(), false, c.getIsRequired().value));
					ItemRequiredBackGroud itemNamebackGroud = new ItemRequiredBackGroud();
					ItemRequiredBackGroud disablebackGroud = new ItemRequiredBackGroud();
					itemNamebackGroud.setColumnKey("itemName");
					itemNamebackGroud.setRowId(c.getPerInfoItemDefId());
					disablebackGroud.setColumnKey("disabled");
					disablebackGroud.setRowId(c.getPerInfoItemDefId());

					if (checkDisable) {
						disablebackGroud.setState(toList("ntsgrid-disable"));
						if (c.getIsRequired().value == 1) {
							itemNamebackGroud.setState(toList("requiredCell"));
						} else {
							itemNamebackGroud.setState(toList("notrequiredCell"));
						}
					} else {
						if (c.getIsRequired().value == 1) {
							itemNamebackGroud.setState(toList("requiredCell"));
							disablebackGroud.setState(toList("requiredCell"));
						} else {
							itemNamebackGroud.setState(toList("notrequiredCell"));
							disablebackGroud.setState(toList("notrequiredCell"));
						}
					}
					itemRequired.add(itemNamebackGroud);
					itemRequired.add(disablebackGroud);
				});
			}
			ctgState.setItemLst(itemDto);
			ctgState.setItemRequired(itemRequired);
		}
		return ctgState;
	}

	private List<String> toList(String... item) {
		return Stream.of(item).collect(Collectors.toCollection(ArrayList::new));
	}

	public List<PerInfoInitValueSettingItemDto> convertItemDtoLst(List<PerInfoInitValueSetItem> items) {
		String ctgCode = items.get(0).getCtgCode();
		List<PerInfoInitValueSettingItemDto> itemDto = new ArrayList<>();
		//アルゴリズム「勤務開始終了時刻を活性にするかチェックする」
		List<String> itemEven = Arrays.asList("IS00133", "IS00134", "IS00142", "IS00143", "IS00160", "IS00161", "IS00169",
				"IS00170", "IS00178", "IS00179", "IS00151", "IS00152", "IS00196", "IS00197", "IS00205", "IS00206",
				"IS00214", "IS00215", "IS00223", "IS00224", "IS00232", "IS00233", "IS00214", "IS00215", "IS00241",
				"IS00242", "IS00187", "IS00188");

		//アルゴリズム「勤務開始終了時刻を活性にするかチェックする」AND 「アルゴリズム「複数回項目を活性にするかチェックする」
		List<String> itemOld = Arrays.asList("IS00136", "IS00137", "IS00145", "IS00146", "IS00163", "IS00164", "IS00172",
				"IS00173", "IS00181", "IS00182", "IS00154", "IS00155", "IS00199", "IS00200", "IS00208", "IS00209",
				"IS00217", "IS00218", "IS00226", "IS00227", "IS00235", "IS00236", "IS00217", "IS00218", "IS00244",
				"IS00245", "IS00190", "IS00191");
		
		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(ctgCode,companyId);
		
		if (!perInfoCategory.isPresent()){
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		PersonEmployeeType personEmployeeType = perInfoCategory.get().getPersonEmployeeType();
		
		if (ctgCode.equals("CS00020")) {
			itemDto = items.stream().map(item -> {

				PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(item);
				boolean isEven = itemEven.contains(item.getItemCode());
				boolean isOld = itemOld.contains(item.getItemCode());
				if (isOld || isEven) {
					dto.setDisableCombox(true);
				} else {
					dto.setDisableCombox(false);
				}

				if (isOld) {
					if (item.getSelectionItemId() == null) {
						dto.setEnableControl(false);
					} else {
						dto.setEnableControl(this.predetemineTimeSettingRepo.isWorkingTwice(item.getSelectionItemId())
								&& this.workTimeSettingRepo.isFlowWork(item.getSelectionItemId()));
					}
				}

				if (isEven) {
					if (item.getSelectionItemId() == null) {
						dto.setEnableControl(false);
					} else {
						dto.setEnableControl(this.workTimeSettingRepo.isFlowWork(item.getSelectionItemId()));
					}
				}
				
				return getInitItemDto(dto, personEmployeeType);
			}).collect(Collectors.toList());
		} else if (ctgCode.equals("CS00001")) {
			itemDto = items.stream().filter(c -> {
				return !c.getItemCode().equals("IS00001");
			}).map(c -> {
				PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(c);
				return getInitItemDto(dto, personEmployeeType);
			}).collect(Collectors.toList());
		} else {
			itemDto = items.stream().map(c -> {
				PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(c);
				return getInitItemDto(dto, personEmployeeType);
			}).collect(Collectors.toList());;
		}
		
		return itemDto;

	}
	
	private PerInfoInitValueSettingItemDto getInitItemDto(PerInfoInitValueSettingItemDto dto,
			PersonEmployeeType personEmployeeType) {
		int dataType = dto.getDataType();
		if (dataType == DataTypeValue.SELECTION.value || dataType == DataTypeValue.SELECTION_BUTTON.value
				|| dataType == DataTypeValue.SELECTION_RADIO.value) {
			boolean isDataType6 = dataType == DataTypeValue.SELECTION.value;
			dto.setSelection(getSelectionItem(dto, personEmployeeType, isDataType6));
		}
		return dto;
	}
	
	private List<ComboBoxObject> getSelectionItem(PerInfoInitValueSettingItemDto dto,
			PersonEmployeeType personEmployeeType, boolean isDataType6) {
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
				true, personEmployeeType, isDataType6);
	}

	public List<PerInfoInitValueSettingItemDto> filterItemTimePointOfCS00020(List<PerInfoInitValueSetItem> items) {
		String ctgCode = items.get(0).getCtgCode();
		List<String> itemList = this.createItemTimePointOfCS00020();
		
		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(ctgCode,companyId);
		
		if (!perInfoCategory.isPresent()){
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		List<PerInfoInitValueSetItem> filteredItems = null;
		
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
			return getInitItemDto(dto, perInfoCategory.get().getPersonEmployeeType());
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
}
