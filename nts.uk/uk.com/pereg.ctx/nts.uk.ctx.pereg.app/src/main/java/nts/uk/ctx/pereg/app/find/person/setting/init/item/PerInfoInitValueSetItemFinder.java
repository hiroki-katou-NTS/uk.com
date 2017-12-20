package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;

@Stateless
public class PerInfoInitValueSetItemFinder {

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;
	@Inject
	private SelectionRepository selectionRepo;

	public List<PerInfoInitValueSettingItemDto> getAllItem(String settingId, String perInfoCtgId) {

		List<PerInfoInitValueSetItem> item = this.settingItemRepo.getAllItem(settingId, perInfoCtgId);
//				.stream().filter(c -> (c.getDataType() != 0))
//				.collect(Collectors.toList());
		if (item != null) {
			List<PerInfoInitValueSettingItemDto> itemDto = item.stream().map(c -> {
				if (c.getDataType() == 6) {
					PerInfoInitValueSettingItemDto dto = PerInfoInitValueSettingItemDto.fromDomain(c);
					List<SelectionInitDto> selectionDto = this.selectionRepo
							.getAllSelectionByHistoryId(c.getSelectionItemId(), GeneralDate.today()).stream()
							.map(b -> SelectionInitDto.fromDomainSelection(b)).collect(Collectors.toList());
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
