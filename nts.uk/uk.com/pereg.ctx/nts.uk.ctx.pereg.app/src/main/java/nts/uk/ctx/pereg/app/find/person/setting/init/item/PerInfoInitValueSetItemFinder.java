package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemRequiredBackGroud;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.CategoryStateDto;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
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
	private PerInfoItemDefFinder itemDefFinder;
	@Inject
	private PerInfoCtgByCompanyRepositoty ctgRepo;

	public List<PerInfoInitValueSettingItemDto> getAllItem(String settingId, String perInfoCtgId) {

		List<PerInfoInitValueSetItem> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
		if (item != null) {
			List<PerInfoInitValueSettingItemDto> itemDto = item.stream().map(c -> {
				if (c.getDataType() == 6 || c.getDataType() == 7 || c.getDataType() == 8) {
					PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(c);
					SelectionItemDto selectionItemDto = null;
					if (dto.getSelectionItemRefType() == 1) {
						selectionItemDto = SelectionItemDto.createMasterRefDto(dto.getSelectionItemId(),
								dto.getSelectionItemRefType());
					} else if (dto.getSelectionItemRefType() == 2) {
						selectionItemDto = SelectionItemDto.createCodeNameRefDto(dto.getSelectionItemId(),
								dto.getSelectionItemRefType());
					} else if (dto.getSelectionItemRefType() == 3) {
						selectionItemDto = SelectionItemDto.createEnumRefDto(dto.getSelectionItemId(),
								dto.getSelectionItemRefType());
					}

					List<ComboBoxObject> selectionDto = this.comboBoxFactory.getComboBox(selectionItemDto,
							AppContexts.user().employeeId(), GeneralDate.today(), true);

					dto.setSelection(selectionDto);
					return dto;
				} else {
					return PerInfoInitValueSettingItemDto.fromDomain(c);
				}
			}).collect(Collectors.toList());

			return itemDto;
		}

		return new ArrayList<>();
	}

	public CategoryStateDto getAllItemRequired(String settingId, String perInfoCtgId) {
		CategoryStateDto ctgState = new CategoryStateDto();
		String companyId = AppContexts.user().companyId(), contract = AppContexts.user().contractCode();
		PersonInfoCategory ctg = this.ctgRepo.getDetailCategoryInfo(companyId, perInfoCtgId, contract)
				.orElseThrow(null);

		List<PerInfoInitValueSetItem> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
		List<ItemRequiredBackGroud> itemRequired = new ArrayList<>();
		List<ItemDto> itemDto = new ArrayList<>();
		if (item != null) {
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
					
					if (c.getIsRequired().value == 1) {
						itemNamebackGroud.setState(toList("requiredCell"));
						disablebackGroud.setState(toList("requiredCell"));
					} else {
						itemNamebackGroud.setState(toList("notrequiredCell"));
						disablebackGroud.setState(toList("notrequiredCell"));

					}
				itemRequired.add(itemNamebackGroud);
				itemRequired.add(disablebackGroud);
			});

			ctgState.setItemLst(itemDto);
			ctgState.setItemRequired(itemRequired);
		}
		return ctgState;
	}

	private List<String> toList(String... item) {
		return Stream.of(item).collect(Collectors.toCollection(ArrayList::new));
	}
}
