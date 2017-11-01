/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpPersonInfoClassDto;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpPersonInfoItemDto;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.person.dom.person.currentdddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentdddress.CurrentAddressRepository;
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
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;
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
	private PerInfoCtgDataRepository perInCtgDataRepo;

	@Inject
	private PerInfoItemDataRepository perInItemDataRepo;

	@Inject
	private EmInfoCtgDataRepository emInCtgDataRepo;

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
						getDataforSingleItem(perInfoCategory, dataInfoItems, standandDate);
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
			GeneralDate standandDate) {
		if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			// PERSON
			String personId = AppContexts.user().personId();
			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
				// FIXED CASE
				PerInfoCtgData perInfoCtgData = null;
				switch (perInfoCategory.getCategoryCode().v()) {
				case "CS00001":
					// Person
					Person person = personRepo.getByPersonId(personId).get();
					matchInformation(dataInfoItems, person);
					perInfoCtgData = perInCtgDataRepo.getByRecordId(personId).get();
					break;
				case "CS00003":
					// CurrentAddress
					CurrentAddress currentAddress = currentAddressRepo.get(personId, standandDate);
					matchInformation(dataInfoItems, currentAddress);
					perInfoCtgData = perInCtgDataRepo.getByRecordId(currentAddress.getCurrentAddressId()).get();
					break;
				case "CS00014":
					// WidowHistory
					WidowHistory widowHistory = widowHistoryRepo.get();
					matchInformation(dataInfoItems, widowHistory);
					perInfoCtgData = perInCtgDataRepo.getByRecordId(widowHistory.getWindowHistoryId()).get();
					break;
				}
				// 
				if ( perInfoCtgData != null ) {
					
				}
				
			} else {
				// UNFIXED CASE
				if (perInfoCategory.getCategoryType() == CategoryType.SINGLEINFO) {
					// single information
					PerInfoCtgData perInfoCtgData = perInCtgDataRepo
							.getByPerIdAndCtgId(personId, perInfoCategory.getPersonInfoCategoryId()).get(0);
					List<PersonInfoItemData> dataItems = perInItemDataRepo
							.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
					matchDataForDefItems(dataInfoItems, dataItems);
				} else if (perInfoCategory.getCategoryType() == CategoryType.CONTINUOUSHISTORY
						|| perInfoCategory.getCategoryType() == CategoryType.NODUPLICATEHISTORY) {
					// history
					getDataHistoryType(perInfoCategory.getPersonInfoCategoryId(), dataInfoItems, personId,
							standandDate);
				}
			}
		} else {
			// EMPLOYEE
		}
	}

	private void getDataHistoryType(String perInfoCategoryId, List<EmpPersonInfoItemDto> dataInfoItems, String personId,
			GeneralDate standandDate) {
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
				matchDataForDefItems(dataInfoItems, dataItems);
				break;
			}

		}
	}

	private void matchDataForDefItems(List<EmpPersonInfoItemDto> dataInfoItems, List<PersonInfoItemData> dataItems) {
		for (EmpPersonInfoItemDto dataInfoItem : dataInfoItems) {
			for (PersonInfoItemData dataItem : dataItems) {
				if (dataInfoItem.getId() == dataItem.getPerInfoCtgId()) {
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

	private void matchInformation(List<EmpPersonInfoItemDto> dataInfoItems, Person person) {
		for (EmpPersonInfoItemDto dataInfoItem : dataInfoItems) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00001":
				// 個人名グループ．個人名
				dataInfoItem.setData(person.getPersonNameGroup().getPersonName().v());
				break;
			case "IS00002":
				// 個人名グループ．個人名カナ
				dataInfoItem.setData(person.getPersonNameGroup().getPersonNameKana().v());
				break;
			case "IS00003":
				// 個人名グループ．個人名ローマ字．氏名
				dataInfoItem.setData(person.getPersonNameGroup().getPersonRomanji().getFullName().v());
				break;
			case "IS00004":
				// 個人名グループ．個人名ローマ字．氏名カナ
				dataInfoItem.setData(person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v());
				break;
			case "IS00005":
				// 個人名グループ．ビジネスネーム
				dataInfoItem.setData(person.getPersonNameGroup().getBusinessName().v());
				break;
			case "IS00006":
				// 個人名グループ．ビジネスネーム．英語
				dataInfoItem.setData(person.getPersonNameGroup().getBusinessEnglishName().v());
				break;
			case "IS00007":
				// 個人名グループ．ビジネスネーム．その他
				dataInfoItem.setData(person.getPersonNameGroup().getBusinessOtherName().v());
				break;
			case "IS00008":
				// 個人名グループ．個人旧氏名．氏名
				dataInfoItem.setData(person.getPersonNameGroup().getOldName().getFullName().v());
				break;
			case "IS00009":
				// 個人名グループ．個人旧氏名．氏名カナ
				dataInfoItem.setData(person.getPersonNameGroup().getOldName().getFullNameKana().v());
				break;
			case "IS00010":
				// 個人名グループ．個人届出名称．氏名
				dataInfoItem.setData(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00011":
				// 個人名グループ．個人届出名称．氏名カナ
				dataInfoItem.setData(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00012":
				// 個人名グループ．個人届出名称．氏名
				dataInfoItem.setData(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00013":
				// 個人名グループ．個人届出名称．氏名カナ
				dataInfoItem.setData(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00014":
				// 性別
				dataInfoItem.setData(person.getGender().value);
				break;
			case "IS00015":
				// 個人携帯
				dataInfoItem.setData(person.getPersonMobile().toString());
				break;
			case "IS00016":
				// 個人メールアドレス
				dataInfoItem.setData(person.getMailAddress().toString());
				break;
			case "IS00017":
				// 趣味
				dataInfoItem.setData(person.getHobBy().toString());
				break;
			case "IS00018":
				// 嗜好
				dataInfoItem.setData(person.getTaste().toString());
				break;
			case "IS00019":
				// 国籍
				dataInfoItem.setData(person.getCountryId().toString());
				break;
			}
		}
	}

	private void matchInformation(List<EmpPersonInfoItemDto> dataInfoItems, CurrentAddress currentAddress) {
		for (EmpPersonInfoItemDto dataInfoItem : dataInfoItems) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00029":
				/*
				 * 現住所．期間 現住所．期間．開始日 現住所．期間．終了日
				 */
				// dataInfoItem.setData(currentAddress.getPeriod());
				break;
			case "IS00030":
				// 現住所．郵便番号
				dataInfoItem.setData(currentAddress.getPostalCode().v());
				break;

			case "IS00031":
				// 現住所．都道府県
				dataInfoItem.setData(currentAddress.getPrefectures().v());
				break;
			case "IS00032":
				// 現住所．国
				dataInfoItem.setData(currentAddress.getCountryId());
				break;
			case "IS00033":
				// 現住所．住所１
				dataInfoItem.setData(currentAddress.getAddress1().getAddress1().v());
				break;
			case "IS00034":
				// 現住所．住所カナ１
				dataInfoItem.setData(currentAddress.getAddress1().getAddressKana1().v());
				break;
			case "IS00035":
				// 現住所．住所2
				dataInfoItem.setData(currentAddress.getAddress2().getAddress2().v());
				break;
			case "IS00036":
				// 現住所．住所カナ2
				dataInfoItem.setData(currentAddress.getAddress2().getAddressKana2().v());
				break;
			case "IS00037":
				// 現住所．電話番号
				dataInfoItem.setData(currentAddress.getPhoneNumber().v());
				break;
			case "IS00038":
				// 現住所．住宅状況種別
				dataInfoItem.setData(currentAddress.getHomeSituationType().v());
				break;
			case "IS00039":
				// 現住所．社宅家賃
				dataInfoItem.setData(currentAddress.getHouseRent().v());
				break;

			}
		}
	}

	private void matchInformation(List<EmpPersonInfoItemDto> dataInfoItems, WidowHistory widowHistory) {
		for (EmpPersonInfoItemDto dataInfoItem : dataInfoItems) {
		}
	}

	private void get(String recordId) {
		PerInfoCtgData perInfoCtgData = perInCtgDataRepo.getByRecordId(recordId).get();
	}

}
