package nts.uk.ctx.pereg.app.find.copysetting.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDtoMapping;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopyCategory;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmployeeCopySetting;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryDetail;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class CopySettingItemFinder {


	@Inject
	private EmpCopySettingRepository empCopyRepo;

	@Inject
	private LayoutingProcessor layoutProcessor;

	@Inject
	private SettingItemDtoMapping settingItemMap;
	
	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	@Inject
	private PerInfoItemDefRepositoty itemDefRepo;
	
	@Inject
	private PersonInfoCategoryAuthRepository perInfoCtgRepo;
	
	private static final String END_DATE_NAME = "終了日";
	
	public List<SettingItemDto> getAllCopyItemByCtgCode(CopySettingItemQuery query) {
		String categoryCd = query.getCategoryCd();
		String selectedEmployeeId = query.getSelectedEmployeeId();
		GeneralDate baseDate = query.getBaseDate();

		String companyId = AppContexts.user().companyId();
		
		// Get perInfoCategory
		PersonInfoCategory perInfoCategory = getCategory(categoryCd, companyId);
		
		// get employee-copy-category
		EmployeeCopyCategory empCopyCategory = getEmpCopyCategory(companyId, perInfoCategory.getPersonInfoCategoryId());
		
		List<PersonInfoItemDefinition> itemDefList = itemDefRepo.getItemLstByListIdForCPS002B(
				empCopyCategory.getItemDefIdList(), AppContexts.user().contractCode(), companyId, Arrays.asList(categoryCd));
		
		List<String> itemIdList =itemDefList.stream().map(i -> i.getPerInfoItemDefId()).collect(Collectors.toList());
		// get data
		// Map ItemDfId- Value
		Map<String, Object> dataMap = getCategoryData(Arrays.asList(categoryCd), selectedEmployeeId, baseDate, itemIdList);
		
		List<SettingItemDto> copyItemList = convertToSettingItemDtoOfCategory(itemDefList, dataMap, categoryCd);
		
		// EA No746
		if (perInfoCategory.getCategoryType() == CategoryType.CONTINUOUSHISTORY) {
			setMaxEndDate(copyItemList);
		}
		
		GeneralDate comboBoxStandardDate = baseDate;
		List<String> standardDateItemCodes = Arrays.asList("IS00020", "IS00077", "IS00082", "IS00119");
		for (SettingItemDto settingItemDto : copyItemList) {
			if (standardDateItemCodes.contains(settingItemDto.getItemCode())) {
				comboBoxStandardDate = (GeneralDate) settingItemDto.getSaveData().getValue();
				break;
			}
		}
		
		this.settingItemMap.setTextForItem(copyItemList, selectedEmployeeId, comboBoxStandardDate, perInfoCategory);
		
		return copyItemList;

	}
	
	private void setMaxEndDate(List<SettingItemDto> copyItemList) {
		copyItemList.forEach(copyItem -> {
			if (copyItem.getItemName().equals(END_DATE_NAME)) {
				copyItem.setData(GeneralDate.max());
			}
		});
		
	}
	
	public List<SettingItemDto> getValueCopyItem(String employeeId, GeneralDate baseDate) {
		
		String companyId = AppContexts.user().companyId();
		
		// get employee-copy-setting
		Optional<EmployeeCopySetting> empCopySettingOpt = empCopyRepo.findSetting(companyId);
		if (!empCopySettingOpt.isPresent()) {
			return new ArrayList<>();
		}	
		List<String> copySettingCategoryIdList = empCopySettingOpt.get().getCopySettingCategoryIdList();
		List<String> copySettingItemIdList = empCopySettingOpt.get().getCopySettingItemIdList();
		
		List<PersonInfoCategoryDetail> categoryDetails = perInfoCtgRepo.getAllCategoryByCtgIdList(companyId,
				copySettingCategoryIdList);

		List<String> categoryCodes = categoryDetails.stream().map(category -> category.getCategoryCode())
				.collect(Collectors.toList());
		
		// get item-definition-list
		List<PersonInfoItemDefinition> itemDefList = itemDefRepo.getItemLstByListId(copySettingItemIdList,
				AppContexts.user().contractCode(), companyId, categoryCodes);
		
		// get data
		Map<String, Object> dataMap = getCategoryData(categoryCodes, employeeId, baseDate, copySettingItemIdList);
		
		
		List<SettingItemDto> copyItemList = convertToSettingItemDto(itemDefList, dataMap, categoryDetails);
		
		// EA No746
		setMaxEndDate(copyItemList, categoryDetails);
		
		return copyItemList;
	}
	
	private void setMaxEndDate(List<SettingItemDto> copyItemList, List<PersonInfoCategoryDetail> categoryDetails) {
		Map<String, Integer> mapCategoryType = categoryDetails.stream().collect(
				Collectors.toMap(category -> category.getCategoryCode(), category -> category.getCategoryType()));
		copyItemList.forEach(copyItem -> {
			int categoryType = mapCategoryType.get(copyItem.getCategoryCode());
			if (categoryType == CategoryType.CONTINUOUSHISTORY.value && copyItem.getItemName().equals(END_DATE_NAME)) {
				copyItem.setData(GeneralDate.max());
			}
		});
	}
	
	private PersonInfoCategory getCategory(String categoryCd, String companyId) {
		// Get perInfoCategory
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(categoryCd,
				companyId);
		
		if (!perInfoCategory.isPresent()) {
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		
		return perInfoCategory.get();
	}
	
	private EmployeeCopyCategory getEmpCopyCategory(String companyId, String categoryId) {
		Optional<EmployeeCopyCategory> empCopyCategoryOpt = empCopyRepo.findCopyCategory(companyId,
				categoryId);

		if (!empCopyCategoryOpt.isPresent() || CollectionUtil.isEmpty(empCopyCategoryOpt.get().getItemDefIdList())) {

			// TODO: ログイン者の権限（ロール）をチェックする (Kiểm tra quyền của người đăng nhập)
			boolean isPersonnelRepresentative = true;
			if (isPersonnelRepresentative) {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			}

		}
		
		return empCopyCategoryOpt.get();
	}
	
	private List<SettingItemDto> convertToSettingItemDtoOfCategory(List<PersonInfoItemDefinition> itemDefList, 
			Map<String, Object> dataMap, String categoryCd) {
		List<SettingItemDto> copyItemList = new ArrayList<>();
		for (PersonInfoItemDefinition itemDef : itemDefList) {
			if (itemDef.getItemTypeState().getItemType() != ItemType.SINGLE_ITEM) {
				continue;
			}
			
			SingleItem singleItemTypeState = (SingleItem) itemDef.getItemTypeState();
			DataTypeState dataTypeState = singleItemTypeState.getDataTypeState();
			
			DataTypeValue dataType = dataTypeState.getDataTypeValue();
			
			if (dataType == DataTypeValue.READONLY
					|| dataType == DataTypeValue.RELATE_CATEGORY) {
				continue;
			}
			Object value = null;
			if(itemDef.getItemCode().toString().charAt(1) == 'S') {
				value = dataMap.get(itemDef.getItemCode().v());
			}else if(itemDef.getItemCode().toString().charAt(1) == '0') {
				value = dataMap.get(itemDef.getPerInfoItemDefId());
			}
			
			copyItemList.add(SettingItemDto.createFromJavaType1(categoryCd,
					itemDef.getPerInfoItemDefId(), itemDef.getItemCode().v(), itemDef.getItemName().v(),
					itemDef.getIsRequired().value, value,
					dataTypeState.getDataTypeValue(), dataTypeState.getReferenceTypes(),
					itemDef.getItemParentCode().v(), null, dataTypeState.getReferenceCode()));
		}
		
		return copyItemList;
	}
	
	
	private Map<String, Object> getCategoryData(List<String> categoryCodes, String employeeId, GeneralDate baseDate, List<String> copySettingItemIdList) {
		Map<String, Object> dataMap = new HashMap<>();
		categoryCodes.forEach(categoryCode -> {
			PeregDto dto = this.layoutProcessor.findSingle(PeregQuery.createQueryLayout(categoryCode, employeeId, null, baseDate));
			if ( dto != null ) {
				dataMap.putAll(MappingFactory.getFullDtoValue2(dto));
			}
		});
		return dataMap;
	}
	
	private List<SettingItemDto> convertToSettingItemDto(List<PersonInfoItemDefinition> itemDefList,
			Map<String, Object> dataMap, List<PersonInfoCategoryDetail> categoryDetails) {
		
		Map<String, String> categoryIdvsCodeMap = categoryDetails.stream()
				.collect(Collectors.toMap(x -> x.getCategoryId(), x -> x.getCategoryCode()));
		
		List<SettingItemDto> copyItemList = new ArrayList<>();
		for (PersonInfoItemDefinition itemDef : itemDefList) {
			if (itemDef.getItemTypeState().getItemType() != ItemType.SINGLE_ITEM) {
				continue;
			}

			if (dataMap.get(itemDef.getItemCode().v()) == null) {
				continue;
			}

			SingleItem singleItemTypeState = (SingleItem) itemDef.getItemTypeState();
			DataTypeState dataTypeState = singleItemTypeState.getDataTypeState();
			DataTypeValue dataType = dataTypeState.getDataTypeValue();

			if (dataType == DataTypeValue.READONLY || dataType == DataTypeValue.RELATE_CATEGORY) {
				continue;
			}
			
			Object value = null;
			if(itemDef.getItemCode().toString().charAt(1) == 'S') {
				value = dataMap.get(itemDef.getItemCode().v());
			}else if(itemDef.getItemCode().toString().charAt(1) == '0'){
				value = dataMap.get(itemDef.getPerInfoItemDefId());
			}

			copyItemList.add(SettingItemDto.createFromJavaType1(categoryIdvsCodeMap.get(itemDef.getPerInfoCategoryId()),
					itemDef.getPerInfoItemDefId(), itemDef.getItemCode().v(), itemDef.getItemName().v(),
					itemDef.getIsRequired().value, value, dataType, dataTypeState.getReferenceTypes(),
					itemDef.getItemParentCode().v(), null, dataTypeState.getReferenceCode()));

		}
		return copyItemList;
	}
	
	public List<CopySettingItemDto> getPerInfoDefById(String perInfoCategoryId) {
		String companyId = AppContexts.user().companyId();
		List<CopySettingItemDto> listData = empCopyRepo.getPerInfoItemByCtgId(companyId, perInfoCategoryId).stream()
				.map(item -> CopySettingItemDto.createFromDomain(item)).collect(Collectors.toList());
		return listData;
	}

}
