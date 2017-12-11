package nts.uk.ctx.pereg.app.find.copysetting.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItem;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class CopySettingItemFinder {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	private LayoutingProcessor layoutProc;

	public List<SettingItemDto> getAllCopyItemByCtgCode(String categoryCd, String employeeId, GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		List<SettingItemDto> result = new ArrayList<SettingItemDto>();

		// check empployeeId
		boolean isSelf = employeeId == AppContexts.user().employeeId() ? true : false;

		List<EmpCopySettingItem> itemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId,
				isSelf);

		if (itemList.isEmpty()) {
			boolean isPersonnelRepresentative = true;

			if (isPersonnelRepresentative) {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			}
		}

		PeregQuery query = new PeregQuery(categoryCd, employeeId, null, baseDate);

		PeregDto dto = this.layoutProc.findSingle(query);

		Map<String, Object> dataMap = MappingFactory.getAllItem(dto);

		dataMap.forEach((k, v) -> {

			Optional<EmpCopySettingItem> itemInfoOpt = itemList.stream().filter(x -> x.getItemCode() == k).findFirst();

			if (itemInfoOpt.isPresent()) {
				EmpCopySettingItem itemInfo = itemInfoOpt.get();

				result.add(new SettingItemDto(itemInfo.getCategoryCode(), itemInfo.getItemDefId(), k,
						itemInfo.getItemName(), itemInfo.getIsRequired().value,
						SettingItemDto.createSaveDataDto(1, v.toString()), itemInfo.getDataType()));
			}

		});

		return result;

	}

	public List<CopySettingItemDto> getPerInfoDefById(String perInfoCategoryId) {
		String companyId = AppContexts.user().companyId();
		String contractId = AppContexts.user().contractCode();
		return empCopyItemRepo.getPerInfoItemByCtgId(perInfoCategoryId, companyId, contractId).stream().map(item -> {
			return new CopySettingItemDto(item.getItemDefId(), item.getPerInfoCtgId(), item.getItemName(),
					item.isAlreadyCopy());
		}).collect(Collectors.toList());
	}

}
