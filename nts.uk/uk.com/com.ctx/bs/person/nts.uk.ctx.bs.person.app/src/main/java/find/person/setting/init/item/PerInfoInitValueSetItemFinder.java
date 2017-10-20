package find.person.setting.init.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;

@Stateless
public class PerInfoInitValueSetItemFinder {

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;

	public List<PerInfoInitValueSettingItemDto> getAllItem(String perInfoCtgId) {

		return this.settingItemRepo.getAllItem(perInfoCtgId).stream()
				.map(c -> PerInfoInitValueSettingItemDto.fromDomain(c)).collect(Collectors.toList());
	}
	
	//sonnlb
	public List<PerInfoInitValueSettingItemDto> getAllInitItem(String perInfoCtgId) {

		return this.settingItemRepo.getAllInitItem(perInfoCtgId).stream()
				.map(c -> PerInfoInitValueSettingItemDto.fromDomain(c)).collect(Collectors.toList());
	}
	
	//sonnlb

}
