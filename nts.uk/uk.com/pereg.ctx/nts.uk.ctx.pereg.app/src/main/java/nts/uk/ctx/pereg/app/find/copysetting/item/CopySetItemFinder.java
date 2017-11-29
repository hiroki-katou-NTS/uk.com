package nts.uk.ctx.pereg.app.find.copysetting.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopySetItemFinder {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	private InitValueSetItemFinder initItemFinder;

	public List<SettingItemDto> getAllCopyItemByCtgCode(String categoryCd, String employeeId, GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		// check empployeeId
		boolean isSelf = employeeId == AppContexts.user().employeeId() ? true : false;

		List<EmpCopySettingItem> itemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId,
				isSelf);

		List<SettingItemDto> resultItemList = itemList
				.stream().map(x -> SettingItemDto.createFromJavaType(x.getPerInfoCtgId(), x.getCategoryCode(),
						x.getItemDefId(), x.getItemCode(), x.getItemName(), x.getIsRequired().value, ""))
				.collect(Collectors.toList());

		if (resultItemList.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("Msg_347"));
		}

		return this.initItemFinder.loadSettingItems(resultItemList, categoryCd, companyId, employeeId, baseDate);

	}

}
