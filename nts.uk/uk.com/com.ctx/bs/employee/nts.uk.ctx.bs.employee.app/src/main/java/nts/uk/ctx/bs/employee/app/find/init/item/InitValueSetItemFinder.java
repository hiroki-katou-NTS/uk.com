package nts.uk.ctx.bs.employee.app.find.init.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.ReferenceMethodType;

/**
 * @author sonnlb
 *
 */
@Stateless
public class InitValueSetItemFinder {

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;

	@Inject
	private EmpInfoItemDataRepository infoItemRepo;

	// sonnlb
	public List<InitValueSettingItemDto> getAllInitItem(String settingId, String categoryCd) {

		List<InitValueSettingItemDto> resultItemList = new ArrayList<InitValueSettingItemDto>();

		if (isPerSonSettingCtg(categoryCd)) {

			List<PerInfoInitValueSetItem> listItem = this.settingItemRepo.getAllInitItem(settingId, categoryCd);
			if (hasItemSameAsLogin(listItem)) {

				resultItemList = loadInfoData(listItem, categoryCd);

			} else {

				resultItemList = listItem.stream().map(x -> fromInitValuetoDto(x)).collect(Collectors.toList());

			}

		} else {

			resultItemList = loadBasicItem(categoryCd);

		}
		return resultItemList;
	}

	private List<InitValueSettingItemDto> loadInfoData(List<PerInfoInitValueSetItem> listItem, String categoryCd) {

		List<InitValueSettingItemDto> ItemListDto = listItem.stream().map(x -> fromInitValuetoDto(x))
				.collect(Collectors.toList());

		List<InitValueSettingItemDto> empInfoItemList = this.infoItemRepo.getAllInfoItem(categoryCd).stream()
				.map(x -> fromInfoItemtoDto(x)).collect(Collectors.toList());

		ItemListDto.addAll(empInfoItemList);

		return ItemListDto;
	}

	private InitValueSettingItemDto fromInfoItemtoDto(EmpInfoItemData domain) {

		return InitValueSettingItemDto.createFromJavaType(domain.getItemName(), domain.getIsRequired().value,
				domain.getDataState().getDataStateType().value, domain.getDataState().getDateValue(),
				domain.getDataState().getNumberValue(), domain.getDataState().getStringValue());
	}

	private boolean hasItemSameAsLogin(List<PerInfoInitValueSetItem> listItem) {
		if (!listItem.isEmpty()) {

			return listItem.stream().filter(obj -> obj.getRefMethodType().equals(ReferenceMethodType.SAMEASLOGIN))
					.findFirst().isPresent();

		} else {

			return false;

		}
	}

	private List<InitValueSettingItemDto> loadBasicItem(String categoryCd) {

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

	private InitValueSettingItemDto fromInitValuetoDto(PerInfoInitValueSetItem domain) {
		return InitValueSettingItemDto.createFromJavaType(domain.getItemName(), domain.getIsRequired().value,
				domain.getSaveDataType().value, domain.getDateValue(), domain.getIntValue().v(),
				domain.getStringValue().v());
	}

	// sonnlb

}
