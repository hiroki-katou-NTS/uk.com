package nts.uk.ctx.pereg.app.find.copysetting.item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDtoMapping;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItem;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItemRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class CopySettingItemFinder {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;
	
	@Inject
	private EmpCopySettingRepository empCopyRepo;

	@Inject
	private LayoutingProcessor layoutProcessor;

	@Inject
	private SettingItemDtoMapping settingItemMap;

	public List<SettingItemDto> getAllCopyItemByCtgCode(String categoryCd, String employeeId, GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		// check empployeeId
		boolean isSelf = AppContexts.user().employeeId().equals(employeeId) ? true : false;

		List<EmpCopySettingItem> copyItemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId,
				isSelf);

		if (CollectionUtil.isEmpty(copyItemList)) {

			// TODO: ログイン者の権限（ロール）をチェックする (Kiểm tra quyền của người đăng nhập)
			boolean isPersonnelRepresentative = true;
			if (isPersonnelRepresentative) {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			}

		}

		// initial with null-value
		List<SettingItemDto> result = copyItemList.stream()
				.map(copyItem -> SettingItemDto.createFromJavaType(copyItem, null)).collect(Collectors.toList());

		PeregQuery query = new PeregQuery(categoryCd, employeeId, null, baseDate);
		PeregDto dto = this.layoutProcessor.findSingle(query);

		if (dto != null) {
			Map<String, Object> dataMap = MappingFactory.getFullDtoValue(dto);
			// set data to setting-item-DTO
			result.forEach(settingItemDto -> settingItemDto.setData(dataMap.get(settingItemDto.getItemCode())));

		}

		this.settingItemMap.setTextForItem(result, employeeId, baseDate, categoryCd);

		return result.stream().filter(item -> StringUtils.isEmpty(item.getItemParentCd())).collect(Collectors.toList());

	}
	
	public List<SettingItemDto> getValueCopyItem(String categoryCd, String employeeId,
			GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		// check empployeeId
		boolean isSelf = AppContexts.user().employeeId().equals(employeeId) ? true : false;

		List<EmpCopySettingItem> copyItemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId,
				isSelf);
		
		PeregDto dto = this.layoutProcessor.findSingle(new PeregQuery(categoryCd, employeeId, null, baseDate));
		if ( dto == null ) {
			return Collections.emptyList();
		}
		
		Map<String, Object> dataMap = MappingFactory.getFullDtoValue(dto);
		// set data to setting-item-DTO
		
		// initial with null-value
		return copyItemList.stream().filter(copyItem -> dataMap.get(copyItem.getItemCode()) != null)
				.map(copyItem -> SettingItemDto.createFromJavaType(copyItem, dataMap.get(copyItem.getItemCode())))
				.collect(Collectors.toList());
	}

	public List<CopySettingItemDto> getPerInfoDefById(String perInfoCategoryId) {
		String companyId = AppContexts.user().companyId();
		List<CopySettingItemDto> listData = empCopyRepo.getPerInfoItemByCtgId(companyId, perInfoCategoryId).stream()
				.map(item -> CopySettingItemDto.createFromDomain(item)).collect(Collectors.toList());
		return listData;
	}

}
