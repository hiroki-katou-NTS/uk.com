package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
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

	public List<PerInfoInitValueSettingItemDto> getAllItem(String settingId, String perInfoCtgId) {

		List<PerInfoInitValueSetItem> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
		if (item != null) {
			List<PerInfoInitValueSettingItemDto> itemDto = item.stream().map(c -> {
				if (c.getDataType() == 6) {
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
							AppContexts.user().employeeId(), GeneralDate.today(), true, true);

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

}
