/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.common.InitDefaultValue;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layout.dto.SimpleEmpMainLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.definition.LayoutPersonInfoClsDefFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgDataDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.DataTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.pereg.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.pereg.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutPersonInfoClassification;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.OptionalItemDataDto;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class LayoutFinder {

	@Inject
	private EmployeeDataMngInfoRepository employeeDataRepo;

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	@Inject
	private ILayoutPersonInfoClsRepository itemClsRepo;

	@Inject
	private PersonInfoItemAuthRepository perInfoItemAuthRepo;

	@Inject
	private PersonInfoCategoryAuthRepository perInfoCtgAuthRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCateRepo;

	@Inject
	private LayoutingProcessor layoutingProcessor;

	@Inject
	private PerInfoCtgDataRepository perInCtgDataRepo;

	@Inject
	private PerInfoItemDataRepository perInItemDataRepo;

	@Inject
	private EmInfoCtgDataRepository empInCtgDataRepo;

	@Inject
	private EmpInfoItemDataRepository empInItemDataRepo;

	@Inject
	private IMaintenanceLayoutRepository layoutRepo;

	@Inject
	private ComboBoxRetrieveFactory comboBoxFactory;

	@Inject
	private PerInfoCtgFinder categoryFinder;

	@Inject
	private LayoutPersonInfoClsDefFinder clsItemDefFinder;
	
	@Inject 
	private InitDefaultValue initDefaultValue;
	
	public List<SimpleEmpMainLayoutDto> getSimpleLayoutList(String browsingEmpId) {

		String loginEmpId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		// String roleId = "99900000-0000-0000-0000-000000000001";
		boolean selfBrowsing = loginEmpId.equals(browsingEmpId);

		List<MaintenanceLayout> simpleLayouts = layoutRepo.getAllMaintenanceLayout(companyId);

		Map<String, PersonInfoCategoryAuth> authCategoryMap = perInfoCtgAuthRepo.getAllCategoryAuthByRoleId(roleId)
				.stream().collect(Collectors.toMap(e -> e.getPersonInfoCategoryAuthId(), e -> e));

		Map<String, PerInfoCtgDataDto> availableCategoryMap = categoryFinder.getAllCtgUsedByCompanyId().stream()
				.collect(Collectors.toMap(x -> x.getCtgId(), x -> x));

		List<SimpleEmpMainLayoutDto> acceptSplLayouts = new ArrayList<>();
		for (MaintenanceLayout simpleLayout : simpleLayouts) {
			String layoutId = simpleLayout.getMaintenanceLayoutID();

			List<LayoutPersonInfoClassification> itemClassList = this.itemClsRepo.getAllByLayoutId(layoutId);

			if (haveAnItemAuth(itemClassList, authCategoryMap, availableCategoryMap, selfBrowsing)) {
				acceptSplLayouts.add(SimpleEmpMainLayoutDto.fromDomain(simpleLayout));
			}

		}
		return acceptSplLayouts;
	}

	/**
	 * @param layoutQuery
	 * @return
	 */
	public EmpMaintLayoutDto getLayout(LayoutQuery layoutQuery) {
		EmpMaintLayoutDto result = new EmpMaintLayoutDto();
		// query properties
		GeneralDate standardDate = GeneralDate.legacyDate(layoutQuery.getStandardDate());
		String browsingEmpId = layoutQuery.getBrowsingEmpId();
		EmployeeDataMngInfo employee = employeeDataRepo.findByEmpId(browsingEmpId).get();
		String browsingPeronId = employee.getPersonId();

		// validate standard date
		// standardDate = validateStandardDate(cid, browsingEmpId, standardDate,
		// result);
		result.setStandardDate(standardDate);

		// check authority & get data
		boolean selfBrowsing = browsingEmpId.equals(AppContexts.user().employeeId());
		List<LayoutPersonInfoClsDto> itemClassList = this.clsFinder.getListClsDto(layoutQuery.getLayoutId());
		List<LayoutPersonInfoClsDto> authItemClasList = new ArrayList<>();
		String roleId = AppContexts.user().roles().forPersonalInfo();

		Set<String> setCategories = itemClassList.stream().map(classItem -> classItem.getPersonInfoCategoryID())
				.collect(Collectors.toSet());
		List<String> categoryIdList = new ArrayList<>(setCategories);

		Map<String, PersonInfoCategoryAuth> categoryAuthMap = perInfoCtgAuthRepo.getByRoleIdAndCategories(roleId,
				categoryIdList);
		Map<String, List<PersonInfoItemAuth>> itemAuthMap = perInfoItemAuthRepo.getByRoleIdAndCategories(roleId,
				categoryIdList);

		// FILTER CLASS ITEMS WITH AUTHORITY
		for (LayoutPersonInfoClsDto classItem : itemClassList) {

			// if item is separator line, do not check
			if (classItem.getLayoutItemType() == LayoutItemType.SeparatorLine) {
				authItemClasList.add(classItem);
			} else if (isClassItemIsAccepted(categoryAuthMap.get(classItem.getPersonInfoCategoryID()), selfBrowsing)) {
				// check author of each definition in class-item
				List<PersonInfoItemAuth> inforAuthItems = itemAuthMap.get(classItem.getPersonInfoCategoryID());
				List<PerInfoItemDefDto> dataInfoItems = validateAuthItem(inforAuthItems, classItem.getListItemDf(),
						selfBrowsing);
				// if definition-items is empty, will NOT show this class-item
				if (dataInfoItems.isEmpty()) {
					continue;
				}
				classItem.setListItemDf(dataInfoItems);
				authItemClasList.add(classItem);
			}
		}

		// GET DATA WITH EACH CATEGORY
		Map<String, List<LayoutPersonInfoClsDto>> classItemInCategoryMap = authItemClasList.stream()
				.filter(classItem -> classItem.getLayoutItemType() != LayoutItemType.SeparatorLine)
				.collect(Collectors.groupingBy(LayoutPersonInfoClsDto::getPersonInfoCategoryID));
		
		for (Entry<String, List<LayoutPersonInfoClsDto>> classItemsOfCategory : classItemInCategoryMap.entrySet()) {
			String categoryId = classItemsOfCategory.getKey();
			List<LayoutPersonInfoClsDto> classItemList = classItemsOfCategory.getValue();
			PersonInfoCategory perInfoCategory = perInfoCateRepo
					.getPerInfoCategory(categoryId, AppContexts.user().contractCode()).get();
			PeregQuery query = PeregQuery.createQueryLayout(perInfoCategory.getCategoryCode().v(), layoutQuery.getBrowsingEmpId(),
					browsingPeronId, standardDate);
			
			switch (perInfoCategory.getCategoryType()) {
			case SINGLEINFO:
			case CONTINUOUSHISTORY:
			case NODUPLICATEHISTORY:
			case DUPLICATEHISTORY:
			case CONTINUOUS_HISTORY_FOR_ENDDATE:
				// get data
				getDataforSingleItem(perInfoCategory, classItemList, standardDate, browsingPeronId, browsingEmpId, query);
				classItemList.forEach(classItem -> {
					checkActionRoleItemData(itemAuthMap.get(classItem.getPersonInfoCategoryID()), classItem, selfBrowsing);
				});
				break;
			case MULTIINFO:
				getDataforListItem(perInfoCategory, classItemList.get(0), standardDate, browsingPeronId, browsingEmpId, query);
				break;

			default:
				break;
			}
			
		}

		result.setClassificationItems(authItemClasList);
		return result;

	}

	private boolean haveAnItemAuth(List<LayoutPersonInfoClassification> itemClassList,
			Map<String, PersonInfoCategoryAuth> authCategoryMap, Map<String, PerInfoCtgDataDto> availableCategoryMap,
			boolean selfBrowsing) {
		for (LayoutPersonInfoClassification itemClass : itemClassList) {

			if (itemClass.getLayoutItemType() == LayoutItemType.SeparatorLine) {
				continue;
			}

			String categoryIdOfClassItem = itemClass.getPersonInfoCategoryID();

			if (availableCategoryMap.containsKey(categoryIdOfClassItem)) {
				PerInfoCtgDataDto availableCategory = availableCategoryMap.get(categoryIdOfClassItem);

				List<String> itemIdList = this.clsItemDefFinder.getItemDefineIds(itemClass.getLayoutID(),
						itemClass.getDispOrder().v());

				if (anElementExistInList(availableCategory.getItemList(), itemIdList)) {
					PersonInfoCategoryAuth categoryAuth = authCategoryMap.get(categoryIdOfClassItem);
					if (categoryAuth == null) {
						continue;
					}
					if (selfBrowsing && categoryAuth.getAllowPersonRef() == PersonInfoPermissionType.YES) {
						return true;
					}
					if (!selfBrowsing && categoryAuth.getAllowOtherRef() == PersonInfoPermissionType.YES) {
						return true;
					}
				}

			}
		}
		return false;
	}

	private boolean anElementExistInList(List<String> fatherList, List<String> sonList) {
		for (String element : sonList) {
			if (fatherList.contains(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param roleId
	 * @param item
	 * @param selfBrowsing
	 *            Target: check author of person who login with class-item
	 * @return
	 */
	private boolean isClassItemIsAccepted(PersonInfoCategoryAuth personCategoryAuth, boolean selfBrowsing) {
		if (personCategoryAuth == null) {
			return false;
		}
		if (selfBrowsing && personCategoryAuth.getAllowPersonRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		if (!selfBrowsing && personCategoryAuth.getAllowOtherRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		return false;
	}

	/**
	 * @param authItems
	 * @param listItemDef
	 * @param selfBrowsing
	 * @return Target: check author of person who login with each
	 *         definition-items in class-item
	 */
	private List<PerInfoItemDefDto> validateAuthItem(List<PersonInfoItemAuth> authItems,
			List<PerInfoItemDefDto> listItemDef, boolean selfBrowsing) {

		if (authItems == null) {
			return new ArrayList<>();
		}

		List<PerInfoItemDefDto> dataInfoItems = new ArrayList<>();

		for (PerInfoItemDefDto itemDef : listItemDef) {
			Optional<PersonInfoItemAuth> authItemOpt = authItems.stream()
					.filter(p -> p.getPersonItemDefId().equals(itemDef.getId())).findFirst();
			if (authItemOpt.isPresent()) {
				if (selfBrowsing && authItemOpt.get().getSelfAuth() != PersonInfoAuthType.HIDE) {
					dataInfoItems.add(itemDef);
				} else if (!selfBrowsing && authItemOpt.get().getOtherAuth() != PersonInfoAuthType.HIDE) {
					dataInfoItems.add(itemDef);
				}
			}
		}

		return dataInfoItems;
	}

	/**
	 * @param perInfoCategory
	 * @param authClassItem
	 * @param standardDate
	 * @param personId
	 * @param employeeId
	 * @param query
	 */
	private void getDataforSingleItem(PersonInfoCategory perInfoCategory, List<LayoutPersonInfoClsDto> classItemList,
			GeneralDate standardDate, String personId, String employeeId, PeregQuery query) {

		cloneDefItemToValueItem(perInfoCategory, classItemList);

		GeneralDate comboBoxStandardDate = standardDate;

		if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
			// get domain data
			PeregDto peregDto = layoutingProcessor.findSingle(query);

			if (peregDto != null) {
				MappingFactory.mapListItemClass(peregDto, classItemList);

				Map<String, Object> itemValueMap = MappingFactory.getFullDtoValue(peregDto);
				List<String> standardDateItemCodes = Arrays.asList("IS00020", "IS00077", "IS00082", "IS00119");
				for (String itemCode : standardDateItemCodes) {
					if (itemValueMap.containsKey(itemCode)) {
						comboBoxStandardDate = (GeneralDate) itemValueMap.get(itemCode);
						break;
					}
				}
			}

		} else {
			switch (perInfoCategory.getCategoryType()) {
			case SINGLEINFO:
				// authClassItem);
				if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
					List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(personId,
							perInfoCategory.getPersonInfoCategoryId());
					if (!perInfoCtgDatas.isEmpty()) {
						String recordId = perInfoCtgDatas.get(0).getRecordId();
						List<OptionalItemDataDto> dataItems = perInItemDataRepo.getAllInfoItemByRecordId(recordId)
								.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
						MappingFactory.matchOptionalItemData(recordId, classItemList, dataItems);
					}
				} else {
					List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(employeeId,
							perInfoCategory.getPersonInfoCategoryId());
					if (!empInfoCtgDatas.isEmpty()) {
						String recordId = empInfoCtgDatas.get(0).getRecordId();
						List<OptionalItemDataDto> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(recordId).stream()
								.map(x -> x.genToPeregDto()).collect(Collectors.toList());
						MappingFactory.matchOptionalItemData(recordId, classItemList, dataItems);
					}

				}
				break;
			case CONTINUOUSHISTORY:
			case NODUPLICATEHISTORY:
				if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
					// person history
					getPersDataHistoryType(perInfoCategory.getPersonInfoCategoryId(), classItemList, personId,
							standardDate);
				} else {
					// employee history
					getEmpDataHistoryType(perInfoCategory.getPersonInfoCategoryId(), classItemList, employeeId,
							standardDate);
				}
				break;
			default:
				break;
			}

		}

		// For each classification item to get combo box list
		getComboBoxListForSelectionItems(employeeId, perInfoCategory, classItemList, comboBoxStandardDate);
		
		// set default value
		initDefaultValue.setDefaultValue(classItemList);

	}
	
	private void getComboBoxListForSelectionItems(String employeeId, PersonInfoCategory perInfoCategory,
			List<LayoutPersonInfoClsDto> classItemList, GeneralDate comboBoxStandardDate) {
		// For each classification item to get combo box list
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (LayoutPersonInfoValueDto valueItem : classItem.getItems()) {

				DataTypeStateDto itemDataTypeSate = valueItem.getItem();
				if (itemDataTypeSate != null) {
					int dataType = itemDataTypeSate.getDataTypeValue();
					if (dataType == DataTypeValue.SELECTION.value || dataType == DataTypeValue.SELECTION_RADIO.value
							|| dataType == DataTypeValue.SELECTION_BUTTON.value) {
						SelectionItemDto selectionItemDto = (SelectionItemDto) valueItem.getItem();
						boolean isDataType6 = dataType == DataTypeValue.SELECTION.value;
						valueItem.setLstComboBoxValue(comboBoxFactory.getComboBox(selectionItemDto, employeeId,
								comboBoxStandardDate, valueItem.isRequired(), perInfoCategory.getPersonEmployeeType(),
								isDataType6, perInfoCategory.getCategoryCode().v()));
					}
				}
			}
		}
	}
	
	private void getDataforListItem(PersonInfoCategory perInfoCategory, LayoutPersonInfoClsDto classItem,
			GeneralDate standardDate, String personId, String employeeId, PeregQuery query) {
		
		if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
			List<PeregDto> peregDtoList = layoutingProcessor.findList(query);
			List<LayoutPersonInfoValueDto> items = new ArrayList<>();
			
			for ( PeregDto peregDto : peregDtoList) {
				
				List<LayoutPersonInfoValueDto> itemsOfPeregDto = convertPeregDtoToValueItemList(perInfoCategory,
						peregDto, classItem.getListItemDf());
				
				items.addAll(itemsOfPeregDto);
			}
			classItem.setListItemDf(null);
			classItem.setPersonInfoCategoryCD(perInfoCategory.getCategoryCode().v());
			classItem.setItems(items);
		}
		
		//getComboBoxListForSelectionItems(employeeId, perInfoCategory, Arrays.asList(classItem), comboBoxStandardDate);
		
	}
	
	private List<LayoutPersonInfoValueDto> convertPeregDtoToValueItemList(PersonInfoCategory perInfoCategory,
			PeregDto peregDto, List<PerInfoItemDefDto> itemDefList) {
		
		List<LayoutPersonInfoValueDto> items = new ArrayList<>();
		
		Map<String, Object> itemCodeValueMap = MappingFactory.getFullDtoValue(peregDto);
		
		for (PerInfoItemDefDto itemDef : itemDefList) {
			
			// initial basic information
			LayoutPersonInfoValueDto valueItem = LayoutPersonInfoValueDto.cloneFromItemDef(perInfoCategory, itemDef);
			Object value = itemCodeValueMap.get(valueItem.getItemCode());
			
			if (valueItem.getItem() != null) {
				int itemType = valueItem.getItem().getDataTypeValue() ;
				if(itemType == DataTypeValue.SELECTION.value || 
						itemType == DataTypeValue.SELECTION_BUTTON.value || 
						itemType == DataTypeValue.SELECTION_RADIO.value) {
					value = value == null ? null : value.toString();
				}
			}
			
			// set value
			valueItem.setValue(value);
			
			// set recordId
			valueItem.setRecordId(peregDto.getDomainDto().getRecordId());
			
			items.add(valueItem);
		}
		
		return items;
	}
	
	/**
	 * @param perInfoCategoryId
	 * @param authClassItem
	 * @param personId
	 * @param stardardDate
	 *            Target: get data with history case. Person case
	 */
	private void getPersDataHistoryType(String perInfoCategoryId, List<LayoutPersonInfoClsDto> classItemList,
			String personId, GeneralDate stardardDate) {
		DateRangeItem dateRangeItem = perInfoCateRepo.getDateRangeItemByCtgId(perInfoCategoryId);
		List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(personId, perInfoCategoryId);
		String startDateId = dateRangeItem.getStartDateItemId();
		String endDateId = dateRangeItem.getEndDateItemId();
		for (PerInfoCtgData perInfoCtgData : perInfoCtgDatas) {
			String recordId = perInfoCtgData.getRecordId();

			List<OptionalItemDataDto> dataItems = perInItemDataRepo.getAllInfoItemByRecordId(recordId).stream()
					.map(x -> x.genToPeregDto()).collect(Collectors.toList());

			Optional<OptionalItemDataDto> startDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoItemDefId().equals(startDateId)).findFirst();

			Optional<OptionalItemDataDto> endDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoItemDefId().equals(endDateId)).findFirst();

			if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
				if (stardardDate.afterOrEquals((GeneralDate) startDateOpt.get().getValue())
						&& stardardDate.beforeOrEquals((GeneralDate) endDateOpt.get().getValue())) {
					MappingFactory.matchOptionalItemData(recordId, classItemList, dataItems);
					break;
				}
			}

		}
	}

	/**
	 * @param perInfoCategoryId
	 * @param classItemList
	 * @param employeeId
	 * @param stardardDate
	 *            Target: get data with history case. Employee case
	 */
	private void getEmpDataHistoryType(String perInfoCategoryId, List<LayoutPersonInfoClsDto> classItemList,
			String employeeId, GeneralDate stardardDate) {
		DateRangeItem dateRangeItem = perInfoCateRepo.getDateRangeItemByCtgId(perInfoCategoryId);
		List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(employeeId, perInfoCategoryId);
		String startDateId = dateRangeItem.getStartDateItemId();
		String endDateId = dateRangeItem.getEndDateItemId();

		for (EmpInfoCtgData empInfoCtgData : empInfoCtgDatas) {
			String recordId = empInfoCtgData.getRecordId();
			List<OptionalItemDataDto> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(recordId).stream()
					.map(x -> x.genToPeregDto()).collect(Collectors.toList());

			Optional<OptionalItemDataDto> startDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoItemDefId().equals(startDateId)).findFirst();
			Optional<OptionalItemDataDto> endDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoItemDefId().equals(endDateId)).findFirst();

			if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
				if (stardardDate.afterOrEquals((GeneralDate) startDateOpt.get().getValue())
						&& stardardDate.beforeOrEquals((GeneralDate) endDateOpt.get().getValue())) {
					MappingFactory.matchOptionalItemData(recordId, classItemList, dataItems);
					break;
				}
			}

		}
	}

	private void cloneDefItemToValueItem(PersonInfoCategory perInfoCategory,
			List<LayoutPersonInfoClsDto> classItemList) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			List<LayoutPersonInfoValueDto> items = new ArrayList<>();
			for (PerInfoItemDefDto itemDef : classItem.getListItemDf()) {
				items.add(LayoutPersonInfoValueDto.cloneFromItemDef(perInfoCategory, itemDef));
			}
			classItem.setPersonInfoCategoryCD(perInfoCategory.getCategoryCode().v());
			classItem.setListItemDf(null);
			classItem.setItems(items);
		}
	}

	private void checkActionRoleItemData(List<PersonInfoItemAuth> inforAuthItems, LayoutPersonInfoClsDto classItem,
			boolean selfBrowsing) {

		if (inforAuthItems == null) {
			return;
		}

		for (LayoutPersonInfoValueDto valueItem : classItem.getItems()) {

			Optional<PersonInfoItemAuth> authItemOpt = inforAuthItems.stream()
					.filter(p -> p.getPersonItemDefId().equals(valueItem.getItemDefId())).findFirst();

			if (authItemOpt.isPresent()) {
				ActionRole actionrole;
				if (selfBrowsing) {
					actionrole = EnumAdaptor.valueOf(authItemOpt.get().getSelfAuth().value, ActionRole.class);
				} else {
					actionrole = EnumAdaptor.valueOf(authItemOpt.get().getOtherAuth().value, ActionRole.class);
				}
				valueItem.setActionRole(actionrole);
			}

		}

	}

}
