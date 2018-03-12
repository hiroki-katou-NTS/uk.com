package nts.uk.ctx.pereg.app.find.copysetting.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class CopySettingItemFinder {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	private LayoutingProcessor layoutProc;

	@Inject
	private SettingItemDtoMapping settingItemMap;

	public List<SettingItemDto> getAllCopyItemByCtgCode(boolean isScreenB, String categoryCd, String employeeId,
			GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		List<SettingItemDto> result = new ArrayList<SettingItemDto>();

		// check empployeeId
		boolean isSelf = AppContexts.user().employeeId().equals(employeeId) ? true : false;

		List<EmpCopySettingItem> itemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId,
				isSelf);

		if (isScreenB && CollectionUtil.isEmpty(itemList)) {

			boolean isPersonnelRepresentative = true;
			if (isPersonnelRepresentative) {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			}

		}

		itemList.forEach(x -> {
			result.add(SettingItemDto.createFromJavaType(x.getCategoryCode(), x.getItemDefId(), x.getItemCode(),
					x.getItemName(), x.getIsRequired().value, 1, GeneralDate.min(), BigDecimal.valueOf(0), "",
					x.getDataType(), x.getSelectionItemRefType(), x.getItemParentCd(), x.getDateType().value,
					x.getSelectionItemRefCd()));
		});

		PeregQuery query = new PeregQuery(categoryCd, employeeId, null, baseDate);

		PeregDto dto = this.layoutProc.findSingle(query);

		if (dto != null) {
			Map<String, Object> dataMap = MappingFactory.getFullDtoValue(dto);

			dataMap.forEach((k, v) -> {

				Optional<SettingItemDto> itemDtoOpt = result.stream().filter(x -> x.getItemCode().equals(k))
						.findFirst();

				if (itemDtoOpt.isPresent()) {
					SettingItemDto itemInfo = itemDtoOpt.get();

					itemInfo.setData(v != null ? v.toString() : "");
				}

			});
		} else {
			if (!isScreenB) {
				return Collections.emptyList();
			}

		}

		if (isScreenB) {

			this.settingItemMap.setTextForItem(result, employeeId, baseDate);

			return result.stream().filter(item -> StringUtils.isEmpty(item.getItemParentCd()))
					.collect(Collectors.toList());
		}
		return result;

	}

	public List<CopySettingItemDto> getPerInfoDefById(String perInfoCategoryId) {
		String companyId = AppContexts.user().companyId();
		String contractId = AppContexts.user().contractCode();
		List<CopySettingItemDto> listData = empCopyItemRepo
				.getPerInfoItemByCtgId(perInfoCategoryId, companyId, contractId).stream().map(item -> {
					return CopySettingItemDto.createFromDomain(item);
				}).collect(Collectors.toList());

		return listData;
	}

}
