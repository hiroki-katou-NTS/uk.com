/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import nts.uk.shr.pereg.app.find.dto.EmpOptionalDto;
import nts.uk.shr.pereg.app.find.dto.PeregDto;
import nts.uk.shr.pereg.app.find.dto.PersonOptionalDto;

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

		// COLLECT CLASS ITEM WITH CATEGORY
		Map<String, List<LayoutPersonInfoClsDto>> classItemInCategoryMap = new HashMap<>();
		for (LayoutPersonInfoClsDto classItem : authItemClasList) {

			if (classItem.getLayoutItemType() != LayoutItemType.SeparatorLine) {
				if (classItem.getLayoutItemType() == LayoutItemType.ITEM) {
					List<LayoutPersonInfoClsDto> classItemList = classItemInCategoryMap
							.get(classItem.getPersonInfoCategoryID());
					if (classItemList == null) {
						classItemList = new ArrayList<>();
						classItemInCategoryMap.put(classItem.getPersonInfoCategoryID(), classItemList);
					}
					classItemList.add(classItem);
				} else {

				}
			}

		}

		// GET DATA WITH EACH CATEGORY
		for (Entry<String, List<LayoutPersonInfoClsDto>> classItemsOfCategory : classItemInCategoryMap.entrySet()) {
			String categoryId = classItemsOfCategory.getKey();
			List<LayoutPersonInfoClsDto> classItemList = classItemsOfCategory.getValue();
			PersonInfoCategory perInfoCategory = perInfoCateRepo
					.getPerInfoCategory(categoryId, AppContexts.user().contractCode()).get();
			PeregQuery query = new PeregQuery(perInfoCategory.getCategoryCode().v(), layoutQuery.getBrowsingEmpId(),
					browsingPeronId, standardDate);
			// get data
			getDataforSingleItem(perInfoCategory, classItemList, standardDate, browsingPeronId, browsingEmpId, query);

			classItemList.forEach(classItem -> {
				checkActionRoleItemData(itemAuthMap.get(classItem.getPersonInfoCategoryID()), classItem, selfBrowsing);
			});

			/*
			 * switch (perInfoCategory.getCategoryType()) { case SINGLEINFO:
			 * classItemList.forEach(classItem -> {
			 * checkActionRoleItemData(itemAuthMap.get(classItem.
			 * getPersonInfoCategoryID()), classItem, selfBrowsing); }); break;
			 * case CONTINUOUSHISTORY: case NODUPLICATEHISTORY: case
			 * DUPLICATEHISTORY: case CONTINUOUS_HISTORY_FOR_ENDDATE:
			 * 
			 * break; default: break; }
			 */

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

		GeneralDate comboBoxStandardDate = null;

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
						List<PersonOptionalDto> dataItems = perInItemDataRepo.getAllInfoItemByRecordId(recordId)
								.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
						MappingFactory.matchPerOptionData(recordId, classItemList, dataItems);
					}
				} else {
					List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(employeeId,
							perInfoCategory.getPersonInfoCategoryId());
					if (!empInfoCtgDatas.isEmpty()) {
						String recordId = empInfoCtgDatas.get(0).getRecordId();
						List<EmpOptionalDto> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(recordId).stream()
								.map(x -> x.genToPeregDto()).collect(Collectors.toList());
						MappingFactory.matchEmpOptionData(recordId, classItemList, dataItems);
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
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {

				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
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
		
		// set default value
		initDefaultValue.setDefaultValue(classItemList);

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

			List<PersonOptionalDto> dataItems = perInItemDataRepo.getAllInfoItemByRecordId(recordId).stream()
					.map(x -> x.genToPeregDto()).collect(Collectors.toList());

			Optional<PersonOptionalDto> startDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoItemDefId().equals(startDateId)).findFirst();

			Optional<PersonOptionalDto> endDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoItemDefId().equals(endDateId)).findFirst();

			if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
				if (stardardDate.afterOrEquals((GeneralDate) startDateOpt.get().getValue())
						&& stardardDate.beforeOrEquals((GeneralDate) endDateOpt.get().getValue())) {
					MappingFactory.matchPerOptionData(recordId, classItemList, dataItems);
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
			List<EmpOptionalDto> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(recordId).stream()
					.map(x -> x.genToPeregDto()).collect(Collectors.toList());

			Optional<EmpOptionalDto> startDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoDefId().equals(startDateId)).findFirst();
			Optional<EmpOptionalDto> endDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoDefId().equals(endDateId)).findFirst();

			if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
				if (stardardDate.afterOrEquals((GeneralDate) startDateOpt.get().getValue())
						&& stardardDate.beforeOrEquals((GeneralDate) endDateOpt.get().getValue())) {
					MappingFactory.matchEmpOptionData(recordId, classItemList, dataItems);
					break;
				}
			}

		}
	}

	private void cloneDefItemToValueItem(PersonInfoCategory perInfoCategory,
			List<LayoutPersonInfoClsDto> classItemList) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			List<Object> items = new ArrayList<>();
			for (PerInfoItemDefDto itemDef : classItem.getListItemDf()) {
				items.add(LayoutPersonInfoValueDto.cloneFromItemDef(perInfoCategory, itemDef));
			}
			classItem.setListItemDf(null);
			classItem.setItems(items);
		}
	}

	private void checkActionRoleWithData(PersonInfoCategory perInfoCategory, PersonInfoCategoryAuth personCategoryAuth,
			List<PersonInfoItemAuth> inforAuthItems, LayoutPersonInfoClsDto authClassItem, GeneralDate standardDate,
			boolean selfBrowsing) {
		switch (perInfoCategory.getCategoryType()) {
		case MULTIINFO:
			List<Object> mulSeigoItemsData = new ArrayList<>();
			for (Object mulItem : authClassItem.getItems()) {
				@SuppressWarnings("unchecked")
				List<LayoutPersonInfoValueDto> mulRowData = (List<LayoutPersonInfoValueDto>) mulItem;
				List<LayoutPersonInfoValueDto> mulActionRoleRowData = checkAndSetActionRole(mulRowData, inforAuthItems,
						selfBrowsing);
				mulSeigoItemsData.add(mulActionRoleRowData);
			}
			authClassItem.setItems(mulSeigoItemsData);
			break;
		case CONTINUOUSHISTORY:
		case NODUPLICATEHISTORY:
		case DUPLICATEHISTORY:
		case CONTINUOUS_HISTORY_FOR_ENDDATE:
			DateRangeItem dateRangeItem = perInfoCateRepo
					.getDateRangeItemByCtgId(perInfoCategory.getPersonInfoCategoryId());
			String startDateId = dateRangeItem.getStartDateItemId();
			String endDateId = dateRangeItem.getEndDateItemId();
			List<Object> seigoItemsData = new ArrayList<>();
			for (Object item : authClassItem.getItems()) {
				@SuppressWarnings("unchecked")
				List<LayoutPersonInfoValueDto> rowData = (List<LayoutPersonInfoValueDto>) item;
				Optional<LayoutPersonInfoValueDto> startDateOpt = rowData.stream()
						.filter(column -> column.getItemDefId().equals(startDateId)).findFirst();
				Optional<LayoutPersonInfoValueDto> endDateOpt = rowData.stream()
						.filter(column -> column.getItemDefId().equals(endDateId)).findFirst();

				if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
					PersonInfoAuthType auth = PersonInfoAuthType.UPDATE;
					if (standardDate.after((GeneralDate) endDateOpt.get().getValue())) {
						// past
						auth = selfBrowsing ? personCategoryAuth.getSelfPastHisAuth()
								: personCategoryAuth.getOtherPastHisAuth();
					} else if (standardDate.before((GeneralDate) startDateOpt.get().getValue())) {
						// future
						auth = selfBrowsing ? personCategoryAuth.getSelfFutureHisAuth()
								: personCategoryAuth.getOtherFutureHisAuth();
					}
					switch (auth) {
					case REFERENCE:
						rowData.forEach(element -> element.setActionRole(ActionRole.VIEW_ONLY));
						seigoItemsData.add(rowData);
						break;
					case UPDATE:
						List<LayoutPersonInfoValueDto> actionRoleRowData = checkAndSetActionRole(rowData,
								inforAuthItems, selfBrowsing);
						seigoItemsData.add(actionRoleRowData);
						break;
					case HIDE:
						// do NOT add to authItemsData
						break;
					}
				}
			}
			authClassItem.setItems(seigoItemsData);
			break;
		default:
			break;
		}
	}

	private List<LayoutPersonInfoValueDto> checkAndSetActionRole(List<LayoutPersonInfoValueDto> rowData,
			List<PersonInfoItemAuth> inforAuthItems, boolean selfBrowsing) {
		List<LayoutPersonInfoValueDto> actionRoleRowData = new ArrayList<>();
		for (LayoutPersonInfoValueDto element : rowData) {
			Optional<PersonInfoItemAuth> authItemOpt = inforAuthItems.stream()
					.filter(authItem -> authItem.getPersonItemDefId().equals(element.getItemDefId())).findFirst();
			if (authItemOpt.isPresent()) {
				PersonInfoAuthType auth = selfBrowsing ? authItemOpt.get().getSelfAuth()
						: authItemOpt.get().getOtherAuth();
				switch (auth) {
				case REFERENCE:
					element.setActionRole(ActionRole.VIEW_ONLY);
					actionRoleRowData.add(element);
					break;
				case UPDATE:
					element.setActionRole(ActionRole.EDIT);
					actionRoleRowData.add(element);
					break;
				case HIDE:
					// do NOT add to actionRoleRowData
					break;
				}
			}
		}
		return actionRoleRowData;
	}

	private void checkActionRoleItemData(List<PersonInfoItemAuth> inforAuthItems, LayoutPersonInfoClsDto classItem,
			boolean selfBrowsing) {

		if (inforAuthItems == null) {
			return;
		}

		for (Object item : classItem.getItems()) {
			LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;

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
