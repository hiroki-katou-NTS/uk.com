package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.item.ReferenceMethodType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author sonnlb
 *
 */
@Stateless
public class InitValueSetItemFinder {

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;
	@Inject
	private LayoutingProcessor layoutProc;

	// sonnlb
	public List<SettingItemDto> getAllInitItemByCtgCode(String settingId, String categoryCd, GeneralDate baseDate) {

		List<SettingItemDto> result = new ArrayList<SettingItemDto>();

		String employeeId = AppContexts.user().employeeId();

		List<PerInfoInitValueSetItem> itemList = this.settingItemRepo.getAllInitItem(settingId, categoryCd);

		result.addAll(itemList.stream().map(x -> fromInitValuetoDto(x)).collect(Collectors.toList()));

		if (isHaveItemSameAsLogin(itemList)) {

			PeregQuery query = new PeregQuery(categoryCd, employeeId, null, baseDate);

			PeregDto dto = this.layoutProc.findSingle(query);

			Map<String, Object> dataMap = MappingFactory.getAllItem(dto);

			dataMap.forEach((k, v) -> {

				Optional<PerInfoInitValueSetItem> itemInfoOpt = itemList.stream().filter(x -> x.getItemCode() == k)
						.findFirst();

				if (itemInfoOpt.isPresent()) {
					PerInfoInitValueSetItem itemInfo = itemInfoOpt.get();

					result.add(SettingItemDto.createFromJavaType(itemInfo.getCtgCode(), itemInfo.getPerInfoItemDefId(),
							k, itemInfo.getItemName(), itemInfo.getIsRequired().value, v.toString()));
				}

			});

		}
		return result;
	}

	private SettingItemDto fromInitValuetoDto(PerInfoInitValueSetItem domain) {

		return SettingItemDto.createFromJavaType(domain.getCtgCode(), domain.getPerInfoItemDefId(),
				domain.getItemCode(), domain.getItemName(), domain.getIsRequired().value,
				domain.getSaveDataType().value, domain.getDateValue(), domain.getIntValue().v(),
				domain.getStringValue().v());
	}

	private boolean isHaveItemSameAsLogin(List<PerInfoInitValueSetItem> listItem) {
		if (!listItem.isEmpty()) {

			return listItem.stream().filter(obj -> obj.getRefMethodType().equals(ReferenceMethodType.SAMEASLOGIN))
					.findFirst().isPresent();

		} else {

			return false;

		}
	}

	// sonnlb

}
