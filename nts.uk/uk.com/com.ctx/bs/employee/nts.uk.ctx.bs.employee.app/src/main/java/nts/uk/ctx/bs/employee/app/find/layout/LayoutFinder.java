/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.NewLayoutDto;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoClsFinder;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.setting.copysetting.EmpCopySettingFinder;
import find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import find.person.setting.init.category.SettingCtgDto;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.copy.item.CopySetItemFinder;
import nts.uk.ctx.bs.employee.app.find.init.item.InitValueSetItemFinder;
import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmpInfoCtgData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoPermissionType;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Stateless
public class LayoutFinder {

	@Inject
	private EmployeeRepository employeeRepo;

	@Inject
	private IMaintenanceLayoutRepository maintenanceRepo;

	@Inject
	private PersonInfoCategoryAuthRepository perInfoCtgAuthRepo;

	@Inject
	private PersonInfoItemAuthRepository perInfoItemAuthRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCateRepo;

	@Inject
	private PersonRepository personRepo;

	// @Inject private PersonInfoRoleAuthRepository persInfoRoleAuthRepo;

	@Inject
	private CurrentAddressRepository currentAddressRepo;

	@Inject
	private WidowHistoryRepository widowHistoryRepo;

	@Inject
	private TemporaryAbsenceRepository tempAbsenceRepo;

	@Inject
	private JobTitleMainRepository jobTitMainRepo;

	@Inject
	private AssignedWrkplcRepository assWorkPlaceRepo;

	@Inject
	private AffDepartmentRepository affDepartmentRepo;

	@Inject
	private SubJobPosRepository subJobPosRepo;

	@Inject
	private PersonEmergencyCtRepository perEmerContRepo;

	@Inject
	private FamilyRepository familyRepo;

	// inject category-data-repo
	@Inject
	private PerInfoCtgDataRepository perInCtgDataRepo;

	@Inject
	private PerInfoItemDataRepository perInItemDataRepo;

	@Inject
	private EmInfoCtgDataRepository empInCtgDataRepo;

	@Inject
	private EmpInfoItemDataRepository empInItemDataRepo;

	// sonnlb start code
	@Inject
	private INewLayoutReposotory repo;

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	@Inject
	private PerInfoInitValueSettingCtgFinder initCtgSettingFinder;

	@Inject
	private InitValueSetItemFinder initItemSettingFinder;

	@Inject
	private EmpCopySettingFinder copySettingFinder;

	private CopySetItemFinder copySetItemFinder;
	// sonnlb end

	/**
	 * @param query
	 * @return get layout and data of layout with browsing employee
	 */
	public EmpMaintLayoutDto getLayout(LayoutQuery query) {
		// query properties
		GeneralDate standardDate = GeneralDate.legacyDate(query.getStandardDate());
		String mainteLayoutId = query.getMainteLayoutId();
		String browsingEmpId = query.getBrowsingEmpId();
		// login information
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmployeeId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonnel();
		// check standard date
		Employee employee = employeeRepo.findBySid(companyId, browsingEmpId).get();
		GeneralDate joinDate = employee.getJoinDate();
		GeneralDate retirementDate = employee.getRetirementDate();
		if (standardDate.before(joinDate)) {
			throw new BusinessException("Msg_383");
		}
		if (standardDate.after(retirementDate)) {
			standardDate = retirementDate;
		}
		// get layout
		MaintenanceLayout maintenanceLayout = maintenanceRepo.getById(companyId, mainteLayoutId).get();
		List<LayoutPersonInfoClsDto> itemClassList = this.clsFinder.getListClsDto(mainteLayoutId);
		EmpMaintLayoutDto result = EmpMaintLayoutDto.createFromDomain(maintenanceLayout);
		boolean selfBrowsing = browsingEmpId == loginEmployeeId;
		List<LayoutPersonInfoClsDto> authItemClasList = new ArrayList<>();
		/*
		 * for each class-item, check author of person who login with class-item
		 * then, get data for class-item
		 */
		for (LayoutPersonInfoClsDto classItem : itemClassList) {
			// if item is separator line, do not check
			if (classItem.getLayoutItemType() == LayoutItemType.SeparatorLine) {
				authItemClasList.add(classItem);
			} else if (validateAuthClassItem(roleId, classItem, selfBrowsing)) {
				LayoutPersonInfoClsDto authClassItem = classItem;
				// check author of each definition in class-item
				List<PerInfoItemDefDto> dataInfoItems = validateAuthItem(mainteLayoutId,
						classItem.getPersonInfoCategoryID(), contractCode, roleId, selfBrowsing,
						authClassItem.getListItemDf());
				authClassItem.setListItemDf(dataInfoItems);

				PersonInfoCategory perInfoCategory = perInfoCateRepo
						.getPerInfoCategory(authClassItem.getPersonInfoCategoryID(), contractCode).get();
				// get data
				if (authClassItem.getLayoutItemType() == LayoutItemType.ITEM) {
					getDataforSingleItem(perInfoCategory, authClassItem, standardDate, employee.getPId(),
							employee.getSId());
				} else if (authClassItem.getLayoutItemType() == LayoutItemType.LIST) {
					getDataforListItem(perInfoCategory, authClassItem, standardDate, employee.getPId(),
							employee.getSId());
				}

				authItemClasList.add(authClassItem);
			}
		}

		result.setClassificationItems(authItemClasList);
		return result;
	}

	/**
	 * @param roleId
	 * @param item
	 * @param selfBrowsing
	 *            Target: check author of person who login with class-item
	 * @return
	 */
	private boolean validateAuthClassItem(String roleId, LayoutPersonInfoClsDto item, boolean selfBrowsing) {
		Optional<PersonInfoCategoryAuth> personCategoryAuthOpt = perInfoCtgAuthRepo
				.getDetailPersonCategoryAuthByPId(roleId, item.getPersonInfoCategoryID());
		if (!personCategoryAuthOpt.isPresent()) {
			return false;
		}

		if (selfBrowsing && personCategoryAuthOpt.get().getAllowPersonRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		if (!selfBrowsing && personCategoryAuthOpt.get().getAllowOtherRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		return false;
	}

	/**
	 * @param mainteLayoutId
	 * @param perInfocategoryId
	 * @param contractCode
	 * @param roleId
	 * @param selfBrowsing
	 * @param listItemDef
	 *            Target: check author of person who login with each
	 *            definition-items in class-item
	 * @return
	 */
	private List<PerInfoItemDefDto> validateAuthItem(String mainteLayoutId, String perInfocategoryId,
			String contractCode, String roleId, boolean selfBrowsing, List<PerInfoItemDefDto> listItemDef) {
		List<PerInfoItemDefDto> dataInfoItems = new ArrayList<>();
		List<PersonInfoItemAuth> authItems = perInfoItemAuthRepo.getAllItemAuth(roleId, perInfocategoryId);
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
	 * @param standandDate
	 * @param personId
	 * @param employeeId
	 *            Target: get data with definition-items
	 */
	private void getDataforSingleItem(PersonInfoCategory perInfoCategory, LayoutPersonInfoClsDto authClassItem,
			GeneralDate standandDate, String personId, String employeeId) {
		if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			// PERSON
			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
				// FIXED CASE
				switch (perInfoCategory.getCategoryCode().v()) {
				case "CS00001":
					// Person
					Person person = personRepo.getByPersonId(personId).get();
					ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem, person,
							null);
					matchPersDataForSingleClsItem(perInfoCategory.getCategoryCode().v(), authClassItem,
							perInItemDataRepo.getAllInfoItemByRecordId(personId));
					break;
				case "CS00003":
					// CurrentAddress
					CurrentAddress currentAddress = currentAddressRepo.get(personId, standandDate);
					ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
							currentAddress, null);
					matchPersDataForSingleClsItem(perInfoCategory.getCategoryCode().v(), authClassItem,
							perInItemDataRepo.getAllInfoItemByRecordId(currentAddress.getCurrentAddressId()));
					break;
				case "CS00014":
					// WidowHistory
					WidowHistory widowHistory = widowHistoryRepo.get();
					ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
							widowHistory, null);
					matchPersDataForSingleClsItem(perInfoCategory.getCategoryCode().v(), authClassItem,
							perInItemDataRepo.getAllInfoItemByRecordId(widowHistory.getWidowHistoryId()));
					break;
				}
			} else {
				// UNFIXED CASE
				if (perInfoCategory.getCategoryType() == CategoryType.SINGLEINFO) {
					// single information
					PerInfoCtgData perInfoCtgData = perInCtgDataRepo
							.getByPerIdAndCtgId(personId, perInfoCategory.getPersonInfoCategoryId()).get(0);
					List<PersonInfoItemData> dataItems = perInItemDataRepo
							.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
					matchPersDataForSingleClsItem(perInfoCategory.getCategoryCode().v(), authClassItem, dataItems);
				} else if (perInfoCategory.getCategoryType() == CategoryType.CONTINUOUSHISTORY
						|| perInfoCategory.getCategoryType() == CategoryType.NODUPLICATEHISTORY) {
					// history
					getPersDataHistoryType(perInfoCategory.getCategoryCode().v(),
							perInfoCategory.getPersonInfoCategoryId(), authClassItem, personId, standandDate);
				}
			}
		} else if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
			// EMPLOYEE
			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
				// FIXED CASE
				switch (perInfoCategory.getCategoryCode().v()) {
				case "CS00002":
					Employee employee = employeeRepo.getBySid(employeeId).get();
					ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
							employee, null);
					matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
							empInItemDataRepo.getAllInfoItemByRecordId(employeeId));
					break;
				case "CS00008":
					Optional<TemporaryAbsence> tempAbsc = tempAbsenceRepo.getBySidAndReferDate(employeeId,
							standandDate);
					if (tempAbsc.isPresent()) {
						ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
								tempAbsc.get(), null);
						matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
								empInItemDataRepo.getAllInfoItemByRecordId(tempAbsc.get().getTempAbsenceId()));
					}
					break;
				case "CS00009":
					// Job Title Main
					Optional<JobTitleMain> jobTitleMainOpt = jobTitMainRepo.getByEmpIdAndStandDate(employeeId,
							standandDate);
					if (jobTitleMainOpt.isPresent()) {
						ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
								jobTitleMainOpt.get(), null);
						matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
								empInItemDataRepo.getAllInfoItemByRecordId(jobTitleMainOpt.get().getJobTitleId()));
					}
					break;
				case "CS00010":
					// Assigned Work Place
					AssignedWorkplace assignedWorkplace = assWorkPlaceRepo
							.getByEmpIdAndStandDate(employeeId, standandDate).get();
					ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
							assignedWorkplace, null);
					matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
							empInItemDataRepo.getAllInfoItemByRecordId(assignedWorkplace.getAssignedWorkplaceId()));
					break;
				case "CS00011":
					// Affiliation Department
					AffiliationDepartment affDepartment = affDepartmentRepo
							.getByEmpIdAndStandDate(employeeId, standandDate).get();
					ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
							affDepartment, null);
					matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
							empInItemDataRepo.getAllInfoItemByRecordId(affDepartment.getDepartmentId()));
					break;
				case "CS00012":
					// Sub Job Position
					SubJobPosition subJobPosition = subJobPosRepo.getByEmpIdAndStandDate(employeeId, standandDate)
							.get();
					ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
							subJobPosition, null);
					matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
							empInItemDataRepo.getAllInfoItemByRecordId(subJobPosition.getAffiDeptId()));
					break;
				}
			} else {
				// UNFIXED CASE
				if (perInfoCategory.getCategoryType() == CategoryType.SINGLEINFO) {
					// single information
					EmpInfoCtgData perInfoCtgData = empInCtgDataRepo
							.getEmpInfoCtgDataBySIdAndCtgId(employeeId, perInfoCategory.getPersonInfoCategoryId())
							.get();
					List<EmpInfoItemData> dataItems = empInItemDataRepo
							.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
					matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem, dataItems);
				} else if (perInfoCategory.getCategoryType() == CategoryType.CONTINUOUSHISTORY
						|| perInfoCategory.getCategoryType() == CategoryType.NODUPLICATEHISTORY) {
					// history
					getEmpDataHistoryType(perInfoCategory.getCategoryCode().v(),
							perInfoCategory.getPersonInfoCategoryId(), authClassItem, employeeId, standandDate);
				}
			}

		}
	}

	/**
	 * @param perInfoCategory
	 * @param authClassItem
	 * @param standandDate
	 * @param personId
	 * @param employeeId
	 *            Target: get data with definition-items
	 */
	private void getDataforListItem(PersonInfoCategory perInfoCategory, LayoutPersonInfoClsDto authClassItem,
			GeneralDate standandDate, String personId, String employeeId) {
		if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
				// FIXED
				switch (perInfoCategory.getCategoryCode().v()) {
				case "CS00015":
					// Person Emergency Contact
					List<PersonEmergencyContact> perEmerConts = perEmerContRepo.getListbyPid(personId);
					Map<String, List<LayoutPersonInfoValueDto>> ecMapFixedData = ItemDefinitionFactory
							.matchPersEmerConts(authClassItem, perEmerConts);
					Map<String, List<LayoutPersonInfoValueDto>> ecMapOptionData = getPersDataOptionalForListClsItem(
							perInfoCategory.getCategoryCode().v(), authClassItem, personId);
					authClassItem.setItems(mapFixDataWithOptionData(ecMapFixedData, ecMapOptionData));
					break;
				case "CS00004":
					// Family
					List<Family> families = familyRepo.getListByPid(personId);
					Map<String, List<LayoutPersonInfoValueDto>> fMapFixedData = ItemDefinitionFactory
							.matchFamilies(authClassItem, families);
					Map<String, List<LayoutPersonInfoValueDto>> fMapOptionData = getPersDataOptionalForListClsItem(
							perInfoCategory.getCategoryCode().v(), authClassItem, personId);
					authClassItem.setItems(mapFixDataWithOptionData(fMapFixedData, fMapOptionData));
					break;
				}
			} else {
				// UNFIXED
				Map<String, List<LayoutPersonInfoValueDto>> mapOptionData = getPersDataOptionalForListClsItem(
						perInfoCategory.getCategoryCode().v(), authClassItem, personId);
				authClassItem.setItems(new ArrayList<>(mapOptionData.values()));
			}
		} else if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
				switch (perInfoCategory.getCategoryCode().v()) {
				case "CS00012":
					// Sub Job Position
					List<SubJobPosition> subJobPoses = subJobPosRepo.getByEmpId(employeeId);
					Map<String, List<LayoutPersonInfoValueDto>> sjpMapFixedData = ItemDefinitionFactory
							.matchsubJobPoses(authClassItem, subJobPoses);
					Map<String, List<LayoutPersonInfoValueDto>> sjpMapOptionData = getPersDataOptionalForListClsItem(
							perInfoCategory.getCategoryCode().v(), authClassItem, personId);
					authClassItem.setItems(mapFixDataWithOptionData(sjpMapFixedData, sjpMapOptionData));
					break;
				}
			} else {
				// UNFIXED
				Map<String, List<LayoutPersonInfoValueDto>> mapOptionData = getEmpDataForListClsItem(
						perInfoCategory.getCategoryCode().v(), authClassItem, employeeId);
				authClassItem.setItems(new ArrayList<>(mapOptionData.values()));
			}
		}
	}

	/**
	 * @param categoryCode
	 * @param authClassItem
	 * @param personId
	 * @return Target: get OPTIONAL data with definition-items for item which
	 *         have type is LIST
	 */
	private Map<String, List<LayoutPersonInfoValueDto>> getPersDataOptionalForListClsItem(String categoryCode,
			LayoutPersonInfoClsDto authClassItem, String personId) {
		// ドメインモデル「個人情報カテゴリデータ」を取得する
		Map<String, List<LayoutPersonInfoValueDto>> resultMap = new HashMap<>();
		List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(personId,
				authClassItem.getPersonInfoCategoryID());
		perInfoCtgDatas.forEach(perInfoCtgData -> {
			List<PersonInfoItemData> dataItems = perInItemDataRepo
					.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
			List<LayoutPersonInfoValueDto> ctgDataList = new ArrayList<>();
			for (PerInfoItemDefDto item : authClassItem.getListItemDf()) {

				Optional<PersonInfoItemData> dataItemOpt = dataItems.stream()
						.filter(dataItem -> dataItem.getPerInfoItemDefId() == item.getId()).findFirst();
				if (dataItemOpt.isPresent()) {
					PersonInfoItemData data = dataItemOpt.get();
					Object value = null;
					switch (data.getDataState().getDataStateType()) {
					case String:
						value = data.getDataState().getStringValue();
						break;
					case Numeric:
						value = data.getDataState().getNumberValue().intValue();
						break;
					case Date:
						value = data.getDataState().getDateValue();
						break;
					}
					if (value != null) {
						ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, value));
					}
				} else {
					ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, null));
				}
			}
			resultMap.put(perInfoCtgData.getRecordId(), ctgDataList);
		});
		return resultMap;
	}

	/**
	 * @param mapFixData
	 * @param mapOptionData
	 * @return Target: map data of fixed items with optional items
	 */
	private List<Object> mapFixDataWithOptionData(Map<String, List<LayoutPersonInfoValueDto>> mapFixData,
			Map<String, List<LayoutPersonInfoValueDto>> mapOptionData) {
		List<Object> resultList = new ArrayList<Object>();
		mapFixData.forEach((domainId, fixDataList) -> {
			List<LayoutPersonInfoValueDto> optionDataList = mapOptionData.get(domainId);
			if (optionDataList != null) {
				List<LayoutPersonInfoValueDto> rowList = new ArrayList<>();
				rowList.addAll(fixDataList);
				rowList.addAll(optionDataList);
				resultList.add(rowList);
			}
		});
		return resultList;
	}

	/**
	 * @param categoryCode
	 * @param authClassItem
	 * @param employeeId
	 * @return Target
	 */
	private Map<String, List<LayoutPersonInfoValueDto>> getEmpDataForListClsItem(String categoryCode,
			LayoutPersonInfoClsDto authClassItem, String employeeId) {
		Map<String, List<LayoutPersonInfoValueDto>> resultMap = new HashMap<>();
		List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(employeeId,
				authClassItem.getPersonInfoCategoryID());
		empInfoCtgDatas.forEach(perInfoCtgData -> {
			List<EmpInfoItemData> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
			List<LayoutPersonInfoValueDto> ctgDataList = new ArrayList<>();
			authClassItem.getListItemDf().forEach(item -> {
				Optional<EmpInfoItemData> dataItemOpt = dataItems.stream()
						.filter(dataItem -> dataItem.getPerInfoDefId() == item.getId()).findFirst();
				if (dataItemOpt.isPresent()) {
					EmpInfoItemData data = dataItemOpt.get();
					Object value = null;
					switch (data.getDataState().getDataStateType()) {
					case String:
						value = data.getDataState().getStringValue();
						break;
					case Numeric:
						value = data.getDataState().getNumberValue().intValue();
						break;
					case Date:
						value = data.getDataState().getDateValue();
						break;
					}
					if (value != null) {
						ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, value));
					}
				} else {
					ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, null));
				}

			});
			resultMap.put(perInfoCtgData.getRecordId(), ctgDataList);
		});
		return resultMap;
	}

	/**
	 * @param categoryCode
	 * @param perInfoCategoryId
	 * @param authClassItem
	 * @param personId
	 * @param standandDate
	 *            Target: get data with history case. Person case
	 */
	private void getPersDataHistoryType(String categoryCode, String perInfoCategoryId,
			LayoutPersonInfoClsDto authClassItem, String personId, GeneralDate standandDate) {
		DateRangeItem dateRangeItem = perInfoCateRepo.getDateRangeItemByCtgId(perInfoCategoryId);
		List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(personId, perInfoCategoryId);
		String startDateId = dateRangeItem.getStartDateItemId();
		String endDateId = dateRangeItem.getEndDateItemId();
		for (PerInfoCtgData perInfoCtgData : perInfoCtgDatas) {
			List<PersonInfoItemData> dataItems = perInItemDataRepo
					.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
			GeneralDate startDate = null;
			GeneralDate endDate = null;
			for (PersonInfoItemData dataItem : dataItems) {
				if (dataItem.getPerInfoItemDefId() == startDateId) {
					startDate = dataItem.getDataState().getDateValue();
				} else if (dataItem.getPerInfoItemDefId() == endDateId) {
					endDate = dataItem.getDataState().getDateValue();
				}
			}

			if (startDate == null || endDate == null) {
				continue;
			}

			if (startDate.before(standandDate) && endDate.after(standandDate)) {
				matchPersDataForSingleClsItem(categoryCode, authClassItem, dataItems);
				break;
			}

		}
	}

	/**
	 * @param categoryCode
	 * @param perInfoCategoryId
	 * @param authClassItem
	 * @param personId
	 * @param standandDate
	 *            Target: get data with history case. Employee case
	 */
	private void getEmpDataHistoryType(String categoryCode, String perInfoCategoryId,
			LayoutPersonInfoClsDto authClassItem, String personId, GeneralDate standandDate) {
		DateRangeItem dateRangeItem = perInfoCateRepo.getDateRangeItemByCtgId(perInfoCategoryId);
		List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(personId, perInfoCategoryId);
		String startDateId = dateRangeItem.getStartDateItemId();
		String endDateId = dateRangeItem.getEndDateItemId();

		for (EmpInfoCtgData empInfoCtgData : empInfoCtgDatas) {
			List<EmpInfoItemData> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			GeneralDate startDate = null;
			GeneralDate endDate = null;
			for (EmpInfoItemData dataItem : dataItems) {
				if (dataItem.getPerInfoDefId() == startDateId) {
					startDate = dataItem.getDataState().getDateValue();
				} else if (dataItem.getPerInfoDefId() == endDateId) {
					endDate = dataItem.getDataState().getDateValue();
				}
			}

			if (startDate == null || endDate == null) {
				continue;
			}

			if (startDate.before(standandDate) && endDate.after(standandDate)) {
				matchEmpDataForDefItems(categoryCode, authClassItem, dataItems);
				break;
			}

		}
	}

	/**
	 * @param categoryCode
	 * @param authClassItem
	 * @param dataItems
	 *            Target: map optional data with definition item. Person case
	 */
	private void matchPersDataForSingleClsItem(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			List<PersonInfoItemData> dataItems) {
		for (PerInfoItemDefDto itemDef : authClassItem.getListItemDf()) {
			for (PersonInfoItemData dataItem : dataItems) {
				if (itemDef.getId() == dataItem.getPerInfoItemDefId()) {
					Object data = null;
					switch (dataItem.getDataState().getDataStateType()) {
					case String:
						data = dataItem.getDataState().getStringValue();
						break;
					case Numeric:
						data = dataItem.getDataState().getNumberValue().intValue();
						break;
					case Date:
						data = dataItem.getDataState().getDateValue();
						break;
					}
					if (data != null) {
						authClassItem.getItems().add(LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data));
					}
				}
			}
		}

	}

	/**
	 * @param categoryCode
	 * @param authClassItem
	 * @param dataItems
	 *            Target: map optional data with definition item. employee case
	 */
	private void matchEmpDataForDefItems(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			List<EmpInfoItemData> dataItems) {
		for (PerInfoItemDefDto itemDef : authClassItem.getListItemDf()) {
			for (EmpInfoItemData dataItem : dataItems) {
				if (itemDef.getId() == dataItem.getPerInfoDefId()) {
					Object data = null;
					switch (dataItem.getDataState().getDataStateType()) {
					case String:
						data = dataItem.getDataState().getStringValue();
						break;
					case Numeric:
						data = dataItem.getDataState().getNumberValue().intValue();
						break;
					case Date:
						data = dataItem.getDataState().getDateValue();
						break;
					}
					if (data != null) {
						authClassItem.getItems().add(LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data));
					}
				}
			}
		}

	}

	// sonnlb code start

	/**
	 * get Layout Dto by create type 
	 * @param command : command from client push to webservice
	 * @return NewLayoutDto
	 */
	public NewLayoutDto getByCreateType(GetLayoutByCeateTypeDto command) {

		Optional<NewLayout> layout = repo.getLayout();
		if (!layout.isPresent()) {

			return null;
		}

		NewLayout _layout = layout.get();

		// Get list Classification Item by layoutID
		List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(_layout.getLayoutID());

		if (command.getCreateType() != 3) {

			List<SettingItemDto> layoutItemList = loadAllItemByCreateType(command.getCreateType(),
					command.getInitSettingId(), command.getBaseDate(), command.getEmployeeId());

			if (layoutItemList.isEmpty()) {

				return null;

			}

			for (LayoutPersonInfoClsDto itemCls : listItemCls) {
				LayoutItemType layoutType = itemCls.getLayoutItemType();
				switch (layoutType) {
				case ITEM: // item

					List<Object> itemValues = createItemValueList(itemCls.getListItemDf(), layoutItemList);

					itemCls.setItems(itemValues);

					break;
				case LIST: // list

					break;

				case SeparatorLine: // spa

					break;
				}
				itemCls.setItems(null);
			}

		}

		// remove all category no item;
		listItemCls = listItemCls.stream().filter(x -> x.getListItemDf() != null ? x.getListItemDf().size() > 0 : false)
				.collect(Collectors.toList());

		return NewLayoutDto.fromDomain(_layout, listItemCls);

	}

	/**
	 * create item list from each item layout list and value from dataSourceList
	 * @param dataSourceList : datasource List
	 * @param layoutItemList : itemList need set value
	 * @return itemList as List<Object>
	 */
	private List<Object> createItemValueList(List<PerInfoItemDefDto> dataSourceList, List<SettingItemDto> layoutItemList) {
		List<Object> itemValueList = new ArrayList<Object>();
		for (PerInfoItemDefDto itemDf : dataSourceList) {

			SettingItemDto item = findItemFromList(layoutItemList, itemDf);

			if (item != null) {
				// because is single item
				int rowIndex = 0;
				LayoutPersonInfoValueDto value = new LayoutPersonInfoValueDto(itemDf.getPerInfoCtgId(),
						item.getCategoryCode(), itemDf.getId(), itemDf.getItemName(), itemDf.getItemCode(), rowIndex,
						item.getValueAsString());
				itemValueList.add(value);
			} else {
				// remove itemDf not found
				layoutItemList.remove(itemDf);

			}

		}
		return itemValueList;
	}

	/**
	 * get item from list when same itemcode and categoryId
	 * @param itemDataList list source
	 * @param item condiction
	 * @return SettingItemDto
	 */
	private SettingItemDto findItemFromList(List<SettingItemDto> itemDataList, PerInfoItemDefDto item) {

		return itemDataList.stream().filter(i -> i.getItemCode().equals(item.getItemCode())
				&& i.getPerInfoCtgId().equals(item.getPerInfoCtgId())).findFirst().orElse(null);
	}

	/**
	 * load All SettingItemDto in database by createType
	 * @param createType : type client need create data
	 * @param initSettingId : settingId need find item in
	 * @param baseDate : date need find
	 * @param employeeCopyId : id of employee copy 
	 * @return SettingItemDto List
	 */
	public List<SettingItemDto> loadAllItemByCreateType(int createType, String initSettingId, GeneralDate baseDate,
			String employeeCopyId) {
		// get all Data
		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		// Copy Type
		if (createType == 1) {
			List<SettingCtgDto> ctgList = new ArrayList<SettingCtgDto>();

			ctgList = this.copySettingFinder.getEmpCopySetting();

			for (SettingCtgDto settingCtg : ctgList) {

				List<SettingItemDto> itemList = this.copySetItemFinder
						.getAllCopyItemByCtgCode(settingCtg.getCategoryCd(), employeeCopyId, baseDate);
				returnList.addAll(itemList);
			}

		} else {
			// Init Value Type

			List<SettingCtgDto> ctgList = new ArrayList<SettingCtgDto>();

			ctgList = this.initCtgSettingFinder.getAllCategoryBySetId(initSettingId);

			for (SettingCtgDto settingCtg : ctgList) {

				List<SettingItemDto> itemList = this.initItemSettingFinder.getAllInitItemByCtgCode(initSettingId,
						settingCtg.getCategoryCd(), baseDate);
				returnList.addAll(itemList);

			}

		}
		// fill all item in list to save resources when searching by itemcode
		return returnList;
	}
	// sonnlb code end

}
