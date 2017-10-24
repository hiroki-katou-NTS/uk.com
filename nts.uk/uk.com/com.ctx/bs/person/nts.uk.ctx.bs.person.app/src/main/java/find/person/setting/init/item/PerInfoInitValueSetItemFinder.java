package find.person.setting.init.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.ReferenceMethodType;

@Stateless
public class PerInfoInitValueSetItemFinder {

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;

	public List<PerInfoInitValueSettingItemDto> getAllItem(String perInfoCtgId) {

		return this.settingItemRepo.getAllItem(perInfoCtgId).stream()
				.map(c -> PerInfoInitValueSettingItemDto.fromDomain(c)).collect(Collectors.toList());
	}

	// sonnlb
	public List<PerInfoInitValueSettingItemDto> getAllInitItem(String settingId, String categoryCd) {

		List<PerInfoInitValueSettingItemDto> resultItemList = new ArrayList<PerInfoInitValueSettingItemDto>();

		if (isPerSonSettingCtg(categoryCd)) {

			List<PerInfoInitValueSetItem> listItem = this.settingItemRepo.getAllInitItem(settingId, categoryCd);
			if (hasItemSameAsLogin(listItem)) {

				resultItemList = loadInfoData(listItem, categoryCd);

			} else {

				resultItemList = listItem.stream().map(x -> PerInfoInitValueSettingItemDto.fromDomain(x))
						.collect(Collectors.toList());

			}

		} else {

			resultItemList = loadBasicItem(categoryCd);

		}
		return resultItemList;
	}

	private List<PerInfoInitValueSettingItemDto> loadInfoData(List<PerInfoInitValueSetItem> listItem,
			String categoryCd) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean hasItemSameAsLogin(List<PerInfoInitValueSetItem> listItem) {
		if (!listItem.isEmpty()) {

			return listItem.stream().filter(obj -> obj.getRefMethodType().equals(ReferenceMethodType.SAMEASLOGIN))
					.findFirst().isPresent();

		} else {

			return false;

		}
	}

	private List<PerInfoInitValueSettingItemDto> loadBasicItem(String categoryCd) {

		switch (categoryCd) {

		case "CS00002":

			break;
		case "CS00011":

			break;
		case "CS00010":

			break;
		case "CS00009":

			break;
		case "CS00008":

			break;
		}

		return null;
	}

	private boolean isPerSonSettingCtg(String categoryCd) {
		return categoryCd.substring(1, 2).equals("O");
	}

	// sonnlb

}
