/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layout.dto.SimpleEmpMainLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
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
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class LayoutFinder {

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	@Inject
	private EmployeeRepository employeeRepository;

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

	/**
	 * @param layoutQuery
	 * @return
	 */
	public EmpMaintLayoutDto getLayout(LayoutQuery layoutQuery) {
		EmpMaintLayoutDto result = new EmpMaintLayoutDto();
		// query properties
		GeneralDate stardardDate = GeneralDate.legacyDate(layoutQuery.getStandardDate());
		String browsingEmpId = layoutQuery.getBrowsingEmpId();

		Employee employee = employeeRepository.findBySid(AppContexts.user().companyId(), browsingEmpId).get();
		String browsingPeronId = employee.getPId();
		// validate standard date
		validateStandardDate(stardardDate, employee, result);

		// check authority & get data
		boolean selfBrowsing = browsingEmpId.equals(AppContexts.user().employeeId());
		List<LayoutPersonInfoClsDto> itemClassList = this.clsFinder.getListClsDto(layoutQuery.getLayoutId());
		List<LayoutPersonInfoClsDto> authItemClasList = new ArrayList<>();
		// String roleId = AppContexts.user().roles().forPersonnel();
		String roleId = "99900000-0000-0000-0000-000000000001";

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

					PersonInfoCategory perInfoCategory = perInfoCateRepo.getPerInfoCategory(
							authClassItem.getPersonInfoCategoryID(), AppContexts.user().contractCode()).get();

					PeregQuery query = new PeregQuery(perInfoCategory.getCategoryCode().v(),
							layoutQuery.getBrowsingEmpId(), browsingPeronId, stardardDate);

					// get data
					if (authClassItem.getLayoutItemType() == LayoutItemType.ITEM) {
						getDataforSingleItem(perInfoCategory, authClassItem, stardardDate, browsingPeronId,
								browsingEmpId, query);
					} else if (authClassItem.getLayoutItemType() == LayoutItemType.LIST) {
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
	 * @param stardardDate
	 * @param employee
	 * @param result
	 */
	private void validateStandardDate(GeneralDate stardardDate, Employee employee, EmpMaintLayoutDto result) {
		if (employee.getHistoryWithReferDate(stardardDate).isPresent()) {
			result.setStardandDate(stardardDate);
		} else {
			Optional<JobEntryHistory> hitoryOption = employee.getHistoryBeforeReferDate(stardardDate);
			if (hitoryOption.isPresent()) {
				stardardDate = hitoryOption.get().getRetirementDate();
			} else {
				hitoryOption = employee.getHistoryAfterReferDate(stardardDate);
				if (hitoryOption.isPresent()) {
					stardardDate = hitoryOption.get().getJoinDate();
				}
			}
		}
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
	 * @param stardardDate
	 * @param personId
	 * @param employeeId
	 * @param query
	 */
	private void getDataforSingleItem(PersonInfoCategory perInfoCategory, LayoutPersonInfoClsDto authClassItem,
			GeneralDate stardardDate, String personId, String employeeId, PeregQuery query) {
		// CLONE
		cloneDefItemToValueItem(perInfoCategory.getCategoryCode().v(), authClassItem);
		
		if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
			// get domain data
			PeregDto peregDto = layoutingProcessor.findSingle(query);
			if (peregDto != null) {
				MappingFactory.mapSingleClsDto(peregDto, authClassItem);
			}
		} else {
			if (perInfoCategory.getCategoryType() == CategoryType.SINGLEINFO) {
				getSingleInforData(perInfoCategory, authClassItem, personId, employeeId);
			} else if (perInfoCategory.getCategoryType() == CategoryType.CONTINUOUSHISTORY
					|| perInfoCategory.getCategoryType() == CategoryType.NODUPLICATEHISTORY) {
				if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
					// person history
					getPersDataHistoryType(perInfoCategory.getCategoryCode().v(),
							perInfoCategory.getPersonInfoCategoryId(), authClassItem, personId, stardardDate);
				} else {
					// employee history
					getEmpDataHistoryType(perInfoCategory.getCategoryCode().v(),
							perInfoCategory.getPersonInfoCategoryId(), authClassItem, personId, stardardDate);
				}

			}

		}
	}

	private void getSingleInforData(PersonInfoCategory perInfoCategory, LayoutPersonInfoClsDto authClassItem,
			String personId, String employeeId) {
		if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			PerInfoCtgData perInfoCtgData = perInCtgDataRepo
					.getByPerIdAndCtgId(personId, perInfoCategory.getPersonInfoCategoryId()).get(0);
			List<PersonInfoItemData> dataItems = perInItemDataRepo
					.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
			matchPersDataForSingleClsItem(perInfoCategory.getCategoryCode().v(), authClassItem, dataItems);
		} else {
			EmpInfoCtgData perInfoCtgData = empInCtgDataRepo
					.getEmpInfoCtgDataBySIdAndCtgId(employeeId, perInfoCategory.getPersonInfoCategoryId()).get();
			List<EmpInfoItemData> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
			matchEmpDataForDefItems(perInfoCategory.getCategoryCode().v(), authClassItem, dataItems);
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

	/**
	 * @param categoryCode
	 * @param perInfoCategoryId
	 * @param authClassItem
	 * @param personId
	 * @param stardardDate
	 *            Target: get data with history case. Person case
	 */
	private void getPersDataHistoryType(String categoryCode, String perInfoCategoryId,
			LayoutPersonInfoClsDto authClassItem, String personId, GeneralDate stardardDate) {
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

			if (startDate.before(stardardDate) && endDate.after(stardardDate)) {
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
	 * @param stardardDate
	 *            Target: get data with history case. Employee case
	 */
	private void getEmpDataHistoryType(String categoryCode, String perInfoCategoryId,
			LayoutPersonInfoClsDto authClassItem, String personId, GeneralDate stardardDate) {
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
				if (stardardDate.after(startDateOpt.get().getDataState().getDateValue())
						&& stardardDate.before(endDateOpt.get().getDataState().getDateValue())) {
					matchEmpDataForDefItems(categoryCode, authClassItem, dataItems);
					break;
				}
			}

		}
	}

	private void cloneDefItemToValueItem(String categoryCode, LayoutPersonInfoClsDto classItem) {
		List<Object> items = new ArrayList<>();
		for (PerInfoItemDefDto itemDef : classItem.getListItemDf()) {
			items.add(LayoutPersonInfoValueDto.cloneFromItemDef(categoryCode, itemDef));
		}
		classItem.setItems(items);
	}

}
