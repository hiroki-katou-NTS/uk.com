package nts.uk.ctx.bs.employee.app.find.copy.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpCopySettingItemFinder {

	@Inject
	EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	PerInfoItemDefRepositoty PerInfoItemRepo;

	public List<SettingItemDto> getEmpCopySettingItemList(String categoryId) {
		List<EmpCopySettingItem> itemList = this.empCopyItemRepo.getAllItemFromCategoryId(categoryId);

		if (itemList.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("Msg_347"));
		}

		List<String> itemIdList = new ArrayList<String>();

		itemList.stream().forEach(i -> itemIdList.add(i.getPerInfoItemDefId()));

		return this.PerInfoItemRepo.getAllItemFromIdList(AppContexts.user().contractCode(), itemList).stream()
				.map(x -> fromDomain(x)).collect(Collectors.toList());

	}

	public SettingItemDto fromDomain(PersonInfoItemDefinition domain) {
		
		return null;

//		return SettingItemDto.createFromJavaType(domain.getItemCode(), domain.getItemName(),
//				domain.getIsRequired().value, domain.getSaveDataType().value, domain.getDateValue(),
//				domain.getIntValue().v(), domain.getStringValue().v());
	}

}
