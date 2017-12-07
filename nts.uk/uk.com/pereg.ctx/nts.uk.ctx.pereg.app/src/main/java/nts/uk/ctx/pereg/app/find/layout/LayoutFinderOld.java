/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMember;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMemberRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layout.dto.SimpleEmpMainLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.pereg.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Stateless
public class LayoutFinderOld {

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
	private TempAbsItemRepository tempAbsenceRepo;

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
	private FamilyMemberRepository familyRepo;

	// inject category-data-repo
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
	private LayoutPersonInfoClsFinder clsFinder;

	public List<SimpleEmpMainLayoutDto> getSimpleLayoutList(String browsingEmpId) {
		String loginEmpId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		// String roleId = AppContexts.user().roles().forPersonnel();
		String roleId = "99900000-0000-0000-0000-000000000001";
		boolean selfBrowsing = loginEmpId.equals(browsingEmpId);

		List<MaintenanceLayout> simpleLayouts = layoutRepo.getAllMaintenanceLayout(companyId);
		Map<String, PersonInfoCategoryAuth> mapCategoryAuth = perInfoCtgAuthRepo.getAllCategoryAuthByRoleId(roleId)
				.stream().collect(Collectors.toMap(e -> e.getPersonInfoCategoryAuthId(), e -> e));
		List<SimpleEmpMainLayoutDto> acceptSplLayouts = new ArrayList<>();
		for (MaintenanceLayout simpleLayout : simpleLayouts) {
			if (haveAnItemAuth(simpleLayout.getMaintenanceLayoutID(), mapCategoryAuth, selfBrowsing)) {
				acceptSplLayouts.add(SimpleEmpMainLayoutDto.fromDomain(simpleLayout));
			}
		}
		return acceptSplLayouts;
	}

	private boolean haveAnItemAuth(String layoutId, Map<String, PersonInfoCategoryAuth> mapCategoryAuth,
			boolean selfBrowsing) {
		List<LayoutPersonInfoClsDto> itemClassList = this.clsFinder.getListClsDto(layoutId);
		for (LayoutPersonInfoClsDto itemClass : itemClassList) {
			if (itemClass.getLayoutItemType() == LayoutItemType.SeparatorLine) {
				continue;
			}
			PersonInfoCategoryAuth categoryAuth = mapCategoryAuth.get(itemClass.getPersonInfoCategoryID());
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
		return false;
	}

	/**
	 * @param query
	 * @return get layout and data of layout with browsing employee
	 */
	public EmpMaintLayoutDto getLayout(LayoutQuery query) {
		// query properties
		GeneralDate standardDate = GeneralDate.legacyDate(query.getStandardDate());
		String mainteLayoutId = query.getLayoutId();
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
		boolean selfBrowsing = browsingEmpId.equals(loginEmployeeId);
		List<LayoutPersonInfoClsDto> authItemClasList = new ArrayList<>();
		/*
		 * for each class-item, check author of person who login with class-item
		 * then, get data for class-item
		 */
		for (LayoutPersonInfoClsDto classItem : itemClassList) {
			// if item is separator line, do not check
			if (classItem.getLayoutItemType() == LayoutItemType.SeparatorLine) {
				authItemClasList.add(classItem);
			} else {
				Optional<PersonInfoCategoryAuth> personCategoryAuthOpt = perInfoCtgAuthRepo
						.getDetailPersonCategoryAuthByPId(roleId, classItem.getPersonInfoCategoryID());

				if (validateAuthClassItem(personCategoryAuthOpt, selfBrowsing)) {
					LayoutPersonInfoClsDto authClassItem = classItem;
					// check author of each definition in class-item
					List<PersonInfoItemAuth> inforAuthItems = perInfoItemAuthRepo.getAllItemAuth(roleId,
							classItem.getPersonInfoCategoryID());
					List<PerInfoItemDefDto> dataInfoItems = validateAuthItem(inforAuthItems, classItem.getListItemDf(),
							selfBrowsing);
					// if definition-items is empty, will NOT show this
					// class-item
					if (dataInfoItems.isEmpty()) {
						continue;
					}

					authClassItem.setListItemDf(dataInfoItems);

					PersonInfoCategory perInfoCategory = perInfoCateRepo
							.getPerInfoCategory(authClassItem.getPersonInfoCategoryID(), contractCode).get();
					// get data
					if (authClassItem.getLayoutItemType() == LayoutItemType.ITEM) {
						getDataforSingleItem(perInfoCategory, authClassItem, standardDate, employee.getPId(),
								employee.getSId());
					} else if (authClassItem.getLayoutItemType() == LayoutItemType.LIST) {
						getDataforListItem(perInfoCategory, personCategoryAuthOpt.get(), inforAuthItems, authClassItem,
								standardDate, employee.getPId(), employee.getSId(), selfBrowsing);
					}

					authItemClasList.add(authClassItem);
				}
			}
		}

		List<LayoutPersonInfoClsDto> authItemClasList1 = new ArrayList<>();
		for (int i = 0; i < authItemClasList.size(); i++) {
			if (i == 0) {
				authItemClasList1.add(authItemClasList.get(i));
			} else {
				boolean notAcceptElement = authItemClasList.get(i).getLayoutItemType() == LayoutItemType.SeparatorLine
						&& authItemClasList.get(i - 1).getLayoutItemType() == LayoutItemType.SeparatorLine;
				if (!notAcceptElement) {
					authItemClasList1.add(authItemClasList.get(i));
				}
			}
		}

		result.setClassificationItems(authItemClasList1);
		return result;
	}

	/**
	 * @param roleId
	 * @param item
	 * @param selfBrowsing
	 *            Target: check author of person who login with class-item
	 * @return
	 */
	private boolean validateAuthClassItem(Optional<PersonInfoCategoryAuth> personCategoryAuthOpt,
			boolean selfBrowsing) {
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
	 * @param authItems
	 * @param listItemDef
	 * @param selfBrowsing
	 * @return Target: check author of person who login with each
	 *         definition-items in class-item
	 */
	private List<PerInfoItemDefDto> validateAuthItem(List<PersonInfoItemAuth> authItems,
			List<PerInfoItemDefDto> listItemDef, boolean selfBrowsing) {
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
					Optional<CurrentAddress> currentAddressOpt = currentAddressRepo.getByPerIdAndStd(personId,
							standandDate);
					if (currentAddressOpt.isPresent()) {
						ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
								currentAddressOpt.get(), null);
						matchPersDataForSingleClsItem(perInfoCategory.getCategoryCode().v(), authClassItem,
								perInItemDataRepo
										.getAllInfoItemByRecordId(currentAddressOpt.get().getCurrentAddressId()));
					}
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
					Optional<TempAbsenceHisItem> tempAbsc = tempAbsenceRepo.getItemByEmpIdAndReferDate(employeeId,
							standandDate);
					if (tempAbsc.isPresent()) {
						ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
								tempAbsc.get(), null);
						matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
								empInItemDataRepo.getAllInfoItemByRecordId(tempAbsc.get().getHistoryId()));
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
					Optional<AffiliationDepartment> affDepartmentOpt = affDepartmentRepo
							.getByEmpIdAndStandDate(employeeId, standandDate);
					if (affDepartmentOpt.isPresent()) {
						ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
								affDepartmentOpt.get(), null);
						matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
								empInItemDataRepo.getAllInfoItemByRecordId(affDepartmentOpt.get().getDepartmentId()));
					}
					break;
				case "CS00012":
					// Sub Job Position
					Optional<SubJobPosition> subJobPositionOpt = subJobPosRepo.getByEmpIdAndStandDate(employeeId,
							standandDate);
					if (subJobPositionOpt.isPresent()) {
						ItemDefinitionFactory.matchInformation(perInfoCategory.getCategoryCode().v(), authClassItem,
								subJobPositionOpt.get(), null);
						matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem,
								empInItemDataRepo.getAllInfoItemByRecordId(subJobPositionOpt.get().getAffiDeptId()));
					}
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
	 * @param standardDate
	 * @param personId
	 * @param employeeId
	 *            Target: get data with definition-items
	 */
	private void getDataforListItem(PersonInfoCategory perInfoCategory, PersonInfoCategoryAuth personCategoryAuth,
			List<PersonInfoItemAuth> inforAuthItems, LayoutPersonInfoClsDto authClassItem, GeneralDate standardDate,
			String personId, String employeeId, boolean selfBrowsing) {
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
					List<FamilyMember> families = familyRepo.getListByPid(personId);
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
		// 一覧表を制御する
		checkActionRoleWithData(perInfoCategory, personCategoryAuth, inforAuthItems, authClassItem, standardDate,
				selfBrowsing);
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

			Optional<EmpInfoItemData> startDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoDefId().equals(startDateId)).findFirst();
			Optional<EmpInfoItemData> endDateOpt = dataItems.stream()
					.filter(column -> column.getPerInfoDefId().equals(endDateId)).findFirst();

			if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
				if (standandDate.after(startDateOpt.get().getDataState().getDateValue())
						&& standandDate.before(endDateOpt.get().getDataState().getDateValue())) {
					matchEmpDataForDefItems(categoryCode, authClassItem, dataItems);
					break;
				}
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

}
