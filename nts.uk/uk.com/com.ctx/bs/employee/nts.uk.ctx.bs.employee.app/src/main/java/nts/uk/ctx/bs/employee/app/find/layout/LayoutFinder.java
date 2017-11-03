/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout;

import java.util.ArrayList;
import java.util.List;
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
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpPersonInfoClassDto;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpPersonInfoItemDto;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.leaveholiday.LeaveHoliday;
import nts.uk.ctx.bs.employee.dom.leaveholiday.LeaveHolidayRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmpInfoCtgData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;
import nts.uk.ctx.bs.person.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutPersonInfoClassification;
import nts.uk.ctx.bs.person.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoPermissionType;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuthRepository;
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
	private ILayoutPersonInfoClsRepository itemClsRepo;

	@Inject
	private PersonInfoRoleAuthRepository perInfoAuthRepo;

	@Inject
	private ILayoutPersonInfoClsDefRepository classItemDefRepo;

	@Inject
	private PersonInfoCategoryAuthRepository perInfoCtgAuthRepo;

	@Inject
	private PersonInfoItemAuthRepository perInfoItemAuthRepo;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PerInfoCategoryRepositoty perInfoCateRepo;

	@Inject
	private PersonRepository personRepo;

	@Inject
	private CurrentAddressRepository currentAddressRepo;

	@Inject
	private WidowHistoryRepository widowHistoryRepo;

	@Inject
	private LeaveHolidayRepository leaveHolidayRepo;

	@Inject
	private JobTitleMainRepository jobTitMainRepo;

	@Inject
	private AssignedWrkplcRepository assWorkPlaceRepo;

	@Inject
	private AffDepartmentRepository affDepartmentRepo;

	@Inject
	private SubJobPosRepository subJobPosRepo;

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

	public EmpMaintLayoutDto getLayout(GeneralDate standandDate, String mainteLayoutId, String browsingEmpId) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonnel();
		Employee employee = employeeRepo.findBySid(companyId, employeeId).get();
		GeneralDate joinDate = employee.getJoinDate();
		GeneralDate retirementDate = employee.getRetirementDate();
		if (standandDate.before(joinDate)) {
			throw new BusinessException("Msg_383");
		}
		if (standandDate.after(retirementDate)) {
			standandDate = retirementDate;
		}
		MaintenanceLayout maintenanceLayout = maintenanceRepo.getById(companyId, mainteLayoutId).get();
		EmpMaintLayoutDto result = EmpMaintLayoutDto.createFromDomain(maintenanceLayout);

		List<LayoutPersonInfoClassification> itemClassList = itemClsRepo.getAllByLayoutId(mainteLayoutId);
		// PersonInfoRoleAuth perInfoRoleAuth =
		// perInfoAuthRepo.getDetailPersonRoleAuth(roleId, companyId).get();
		boolean selfBrowsing = browsingEmpId == employeeId;
		List<EmpPersonInfoClassDto> authItemClasList = new ArrayList<>();

		for (LayoutPersonInfoClassification classItem : itemClassList) {
			if (validateAuthClassItem(roleId, classItem, selfBrowsing)) {
				EmpPersonInfoClassDto authClassItem = EmpPersonInfoClassDto.createfromDomain(classItem);

				List<String> itemIds = classItemDefRepo.getAllItemDefineIds(mainteLayoutId,
						classItem.getDispOrder().v());
				if (!itemIds.isEmpty()) {
					List<PersonInfoItemDefinition> listItemDef = pernfoItemDefRep.getPerInfoItemDefByListId(itemIds,
							contractCode);
					List<EmpPersonInfoItemDto> dataInfoItems = validateAuthItem(mainteLayoutId,
							classItem.getPersonInfoCategoryID(), contractCode, roleId, selfBrowsing, listItemDef);
					// get data
					PersonInfoCategory perInfoCategory = perInfoCateRepo
							.getPerInfoCategory(classItem.getPersonInfoCategoryID(), contractCode).get();
					if (classItem.getLayoutItemType() == LayoutItemType.ITEM) {
						getDataforSingleItem(perInfoCategory, dataInfoItems, standandDate, employee.getPId(),
								employee.getSId());
					} else if (classItem.getLayoutItemType() == LayoutItemType.LIST) {

					}
					authClassItem.setDataInfoitems(dataInfoItems);
				}
				authItemClasList.add(authClassItem);
			}
		}

		result.setClassificationItems(authItemClasList);
		return result;
	}

	private boolean validateAuthClassItem(String roleId, LayoutPersonInfoClassification item, boolean selfBrowsing) {
		PersonInfoCategoryAuth personCategoryAuth = perInfoCtgAuthRepo
				.getDetailPersonCategoryAuthByPId(roleId, item.getPersonInfoCategoryID()).get();
		if (selfBrowsing && personCategoryAuth.getAllowPersonRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		if (!selfBrowsing && personCategoryAuth.getAllowOtherRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		return false;
	}

	private List<EmpPersonInfoItemDto> validateAuthItem(String mainteLayoutId, String perInfocategoryId,
			String contractCode, String roleId, boolean selfBrowsing, List<PersonInfoItemDefinition> listItemDef) {
		List<EmpPersonInfoItemDto> dataInfoItems = new ArrayList<>();

		List<PersonInfoItemAuth> authItems = perInfoItemAuthRepo.getAllItemAuth(roleId, perInfocategoryId);
		for (PersonInfoItemDefinition itemDef : listItemDef) {
			PersonInfoItemAuth authItem = authItems.stream()
					.filter(p -> p.getPersonItemDefId().equals(itemDef.getPerInfoItemDefId()))
					.collect(Collectors.toList()).get(0);
			if ((selfBrowsing && authItem.getSelfAuth() != PersonInfoAuthType.HIDE)
					|| (!selfBrowsing && authItem.getOtherAuth() != PersonInfoAuthType.HIDE)) {
				int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(itemDef.getPerInfoCategoryId(),
						itemDef.getPerInfoItemDefId());
				dataInfoItems.add(EmpPersonInfoItemDto.createFromDomain(itemDef, dispOrder));
			}
		}

		return dataInfoItems;
	}

	private void getDataforSingleItem(PersonInfoCategory perInfoCategory, List<EmpPersonInfoItemDto> dataInfoItems,
			GeneralDate standandDate, String personId, String employeeId) {
		if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			// PERSON
			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
				// FIXED CASE
				switch (perInfoCategory.getCategoryCode().v()) {
				case "CS00001":
					// Person
					Person person = personRepo.getByPersonId(personId).get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, person);
					matchPersDataForDefItems(dataInfoItems, perInItemDataRepo.getAllInfoItemByRecordId(personId));
					break;
				case "CS00003":
					// CurrentAddress
					CurrentAddress currentAddress = currentAddressRepo.get(personId, standandDate);
					ItemDefinitionFactory.matchInformation(dataInfoItems, currentAddress);
					matchPersDataForDefItems(dataInfoItems,
							perInItemDataRepo.getAllInfoItemByRecordId(currentAddress.getCurrentAddressId()));
					break;
				case "CS00014":
					// WidowHistory
					WidowHistory widowHistory = widowHistoryRepo.get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, widowHistory);
					matchPersDataForDefItems(dataInfoItems,
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
					matchPersDataForDefItems(dataInfoItems, dataItems);
				} else if (perInfoCategory.getCategoryType() == CategoryType.CONTINUOUSHISTORY
						|| perInfoCategory.getCategoryType() == CategoryType.NODUPLICATEHISTORY) {
					// history
					getPersDataHistoryType(perInfoCategory.getPersonInfoCategoryId(), dataInfoItems, personId,
							standandDate);
				}
			}
		} else if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
			// EMPLOYEE
			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
				// FIXED CASE
				switch (perInfoCategory.getCategoryCode().v()) {
				case "CS00002":
					Employee employee = employeeRepo.getBySid(employeeId).get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, employee);
					matchEmpDataForDefItems(dataInfoItems, empInItemDataRepo.getAllInfoItemByRecordId(employeeId));
					break;
				case "CS00008":
					LeaveHoliday leaveHoliday = leaveHolidayRepo.getByEmpIdAndStandDate(employeeId, standandDate).get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, leaveHoliday);
					matchEmpDataForDefItems(dataInfoItems,
							empInItemDataRepo.getAllInfoItemByRecordId(leaveHoliday.getLeaveHolidayId()));
					break;
				case "CS00009":
					// can implement
					JobTitleMain jobTitleMain = jobTitMainRepo.getByEmpIdAndStandDate(employeeId, standandDate).get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, jobTitleMain);
					matchEmpDataForDefItems(dataInfoItems,
							empInItemDataRepo.getAllInfoItemByRecordId(jobTitleMain.getJobTitleId()));
					break;
				case "CS00010":
					AssignedWorkplace assignedWorkplace = assWorkPlaceRepo
							.getByEmpIdAndStandDate(employeeId, standandDate).get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, assignedWorkplace);
					matchEmpDataForDefItems(dataInfoItems,
							empInItemDataRepo.getAllInfoItemByRecordId(assignedWorkplace.getAssignedWorkplaceId()));
					break;
				case "CS00011":
					AffiliationDepartment affDepartment = affDepartmentRepo
							.getByEmpIdAndStandDate(employeeId, standandDate).get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, affDepartment);
					matchEmpDataForDefItems(dataInfoItems,
							empInItemDataRepo.getAllInfoItemByRecordId(affDepartment.getDepartmentId()));
					break;
				case "CS00012":
					SubJobPosition subJobPosition = subJobPosRepo.getByEmpIdAndStandDate(employeeId, standandDate)
							.get();
					ItemDefinitionFactory.matchInformation(dataInfoItems, subJobPosition);
					matchEmpDataForDefItems(dataInfoItems,
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
					matchEmpDataForDefItems(dataInfoItems, dataItems);
				} else if (perInfoCategory.getCategoryType() == CategoryType.CONTINUOUSHISTORY
						|| perInfoCategory.getCategoryType() == CategoryType.NODUPLICATEHISTORY) {
					// history
					getEmpDataHistoryType(perInfoCategory.getPersonInfoCategoryId(), dataInfoItems, employeeId,
							standandDate);
				}
			}

		}
	}

	private void getPersDataHistoryType(String perInfoCategoryId, List<EmpPersonInfoItemDto> dataInfoItems,
			String personId, GeneralDate standandDate) {
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
				matchPersDataForDefItems(dataInfoItems, dataItems);
				break;
			}

		}
	}

	private void getEmpDataHistoryType(String perInfoCategoryId, List<EmpPersonInfoItemDto> dataInfoItems,
			String personId, GeneralDate standandDate) {
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
				matchEmpDataForDefItems(dataInfoItems, dataItems);
				break;
			}

		}
	}

	private void matchPersDataForDefItems(List<EmpPersonInfoItemDto> dataInfoItems,
			List<PersonInfoItemData> dataItems) {
		for (EmpPersonInfoItemDto dataInfoItem : dataInfoItems) {
			for (PersonInfoItemData dataItem : dataItems) {
				if (dataInfoItem.getId() == dataItem.getPerInfoItemDefId()) {
					switch (dataItem.getDataState().getDataStateType()) {
					case String:
						dataInfoItem.setData(dataItem.getDataState().getStringValue());
						break;
					case Numeric:
						dataInfoItem.setData(dataItem.getDataState().getNumberValue().intValue());
						break;
					case Date:
						dataInfoItem.setData(dataItem.getDataState().getDateValue());
						break;
					}
				}
			}
		}

	}

	private void matchEmpDataForDefItems(List<EmpPersonInfoItemDto> dataInfoItems, List<EmpInfoItemData> dataItems) {
		for (EmpPersonInfoItemDto dataInfoItem : dataInfoItems) {
			for (EmpInfoItemData dataItem : dataItems) {
				if (dataInfoItem.getId() == dataItem.getPerInfoDefId()) {
					switch (dataItem.getDataState().getDataStateType()) {
					case String:
						dataInfoItem.setData(dataItem.getDataState().getStringValue());
						break;
					case Numeric:
						dataInfoItem.setData(dataItem.getDataState().getNumberValue().intValue());
						break;
					case Date:
						dataInfoItem.setData(dataItem.getDataState().getDateValue());
						break;
					}
				}
			}
		}

	}

	// sonnlb code

	public NewLayoutDto getWithDataByCreateType(int createType, String initSettingId, GeneralDate baseDate,
			String employeeId) {

		List<SettingItemDto> allItemData = loadAllItemByCreateType(createType, initSettingId, baseDate, employeeId);

		if (!allItemData.isEmpty()) {
			Optional<NewLayout> layout = repo.getLayout();
			if (layout.isPresent()) {
				NewLayout _layout = layout.get();

				// Get list Classification Item by layoutID
				List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(_layout.getLayoutID());

				for (LayoutPersonInfoClsDto itemCls : listItemCls) {
					int layoutType = itemCls.getLayoutItemType();
					switch (layoutType) {
					case 0: // item

						List<Object> itemValues = createItemValues(itemCls.getListItemDf(), allItemData);

						itemCls.setItems(itemValues);
						
						break;
					case 1: // list

						break;
					default:
					case 2: // spa
						break;
					}
					itemCls.setItems(null);
				}
				return NewLayoutDto.fromDomain(_layout, listItemCls);
			} else {
				return null;
			}

		} else {

			return null;
		}

	}

	private List<Object> createItemValues(List<PerInfoItemDefDto> listItemDf, List<SettingItemDto> allItemData) {
		List<Object> itemValueList = new ArrayList<Object>();
		for (PerInfoItemDefDto itemDf : listItemDf) {

			SettingItemDto item = findItemByCode(allItemData, itemDf.getItemCode());

			LayoutPersonInfoValueDto value = new LayoutPersonInfoValueDto(itemDf.getItemCode(),

					item != null ? item.getValueAsString() : "");
			itemValueList.add(value);
		}
		return itemValueList;
	}

	private SettingItemDto findItemByCode(List<SettingItemDto> allItemData, String itemCode) {

		return allItemData.stream().filter(i -> i.getItemCode().equals(itemCode)).findFirst().orElse(null);
	}

	private List<SettingItemDto> loadAllItemByCreateType(int createType, String initSettingId, GeneralDate baseDate,
			String employeeId) {
		// get all Data
		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		// Copy Type
		if (createType == 1) {
			List<SettingCtgDto> ctgList = new ArrayList<SettingCtgDto>();

			ctgList = this.copySettingFinder.getEmpCopySetting();

			for (SettingCtgDto settingCtg : ctgList) {

				List<SettingItemDto> itemList = this.copySetItemFinder
						.getAllCopyItemByCtgCode(settingCtg.getCategoryCd(), employeeId, baseDate);
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
