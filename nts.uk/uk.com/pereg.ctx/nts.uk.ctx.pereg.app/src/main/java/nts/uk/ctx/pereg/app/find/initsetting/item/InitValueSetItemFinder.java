package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.Leave;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.MidweekClosure;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.ReferenceMethodType;
import nts.uk.ctx.pereg.app.find.additionaldata.item.EmpInfoItemDataFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sonnlb
 *
 */
@Stateless
public class InitValueSetItemFinder {

	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;

	@Inject
	private EmpInfoItemDataFinder infoItemDataFinder;

	@Inject
	private EmployeeRepository empBasicInfoRepo;

	@Inject
	private AffDepartmentRepository affDepartmentRepo;

	@Inject
	private AffWorkplaceHistoryRepository affWorkRepo;

	@Inject
	private TemporaryAbsenceRepository tempAbsenceRepo;

	// sonnlb
	public List<SettingItemDto> getAllInitItemByCtgCode(String settingId, String categoryCd, GeneralDate baseDate) {

		List<SettingItemDto> resultItemList = new ArrayList<SettingItemDto>();

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		List<PerInfoInitValueSetItem> initListItem = this.settingItemRepo.getAllInitItem(settingId, categoryCd);
		resultItemList = initListItem.stream().map(x -> fromInitValuetoDto(x)).collect(Collectors.toList());
		if (isHaveItemSameAsLogin(initListItem)) {

			resultItemList = loadSettingItems(resultItemList, categoryCd, companyId, employeeId, baseDate);

		}
		return resultItemList;
	}

	public List<SettingItemDto> loadSettingItems(List<SettingItemDto> resultItemList, String categoryCd,
			String companyId, String employeeId, GeneralDate baseDate) {
		switch (categoryCd) {
		// 社員基本情報 - Employee
		case "CS00002":
			resultItemList = loadEmployeeInfo(resultItemList, companyId, employeeId);
			break;
		// 休職・休業 - TemporaryAbsence
		case "CS00008":
			resultItemList = loadTemporaryAbsenceInfo(resultItemList, employeeId, baseDate);
			break;
		// 職務職位履歴 - JobTitleHistory
		case "CS00009":
			resultItemList = loadJobTitleHistoryInfo(resultItemList, employeeId, baseDate);
			break;
		// 所属職場 - AffiliationWorkplaceHistory
		case "CS00010":
			resultItemList = loadAffiliationWorkPlaceInfo(resultItemList, employeeId, baseDate);
			break;
		// 所属部門 - AffiliationDepartment
		case "CS00011":
			resultItemList = loadAffiliationDepartmentInfo(resultItemList, employeeId, baseDate);
			break;
		}

		resultItemList.addAll(this.infoItemDataFinder.loadInfoItemDataList(categoryCd, companyId, employeeId));

		return resultItemList;
	}

	// load JobTitleHistory start

	private List<SettingItemDto> loadJobTitleHistoryInfo(List<SettingItemDto> resultItemList, String employeeId,
			GeneralDate baseDate) {
		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();
		//
		// Optional<JobTitleHistory> optJ =
		// this.job.getByEmpIdAndStandDate(employeeId, baseDate);
		// if (opttemAbsence.isPresent()) {
		//
		// returnList =
		// mergeTemporaryAbsenceInfoAndItemDefListToListDto(opttemAbsence.get(),
		// initItemList);
		//
		// }
		return returnList;

	}

	// JobTitleHistory end
	// TemporaryAbsence start

	private List<SettingItemDto> loadTemporaryAbsenceInfo(List<SettingItemDto> initItemList, String employeeId,
			GeneralDate baseDate) {
		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		Optional<TempAbsenceHisItem> opttemAbsence = this.tempAbsenceRepo.getBySidAndReferDate(employeeId, baseDate);
		if (opttemAbsence.isPresent()) {

			returnList = mergeTemporaryAbsenceInfoAndItemDefListToListDto(opttemAbsence.get(), initItemList);

		}
		return returnList;
	}

	private List<SettingItemDto> mergeTemporaryAbsenceInfoAndItemDefListToListDto(TempAbsenceHisItem tempDomain,
			List<SettingItemDto> resultItemList) {
		// code item not yet
		for (SettingItemDto itemDto : resultItemList) {
			String itemCode = itemDto.getItemCode();
			switch (itemCode) {
			case "IS00020":
				itemDto.setData(tempDomain.getLeaveHolidayType().value);
				break;
			case "IS00021":
				//itemDto.setData(tempDomain.getDateHistoryItem().start());
				break;
			case "IS00022":
				//itemDto.setData(tempDomain.getDateHistoryItem().end());
				break;
			case "IS00023":
				//Leave leave = (Leave) tempDomain.getLeaveHolidayState();
				//itemDto.setData(leave.getReason());
				break;
			case "IS00024":
				//MidweekClosure midweekClosure = (MidweekClosure) tempDomain.getLeaveHolidayState();
				//itemDto.setData(midweekClosure.getBirthDate());
				break;
			}
			// TODO
		}
		return resultItemList;
	}
	// TemporaryAbsence end

	private boolean isHaveItemSameAsLogin(List<PerInfoInitValueSetItem> listItem) {
		if (!listItem.isEmpty()) {

			return listItem.stream().filter(obj -> obj.getRefMethodType().equals(ReferenceMethodType.SAMEASLOGIN))
					.findFirst().isPresent();

		} else {

			return false;

		}
	}
	// load Employee Start

	private List<SettingItemDto> loadEmployeeInfo(List<SettingItemDto> initItemList, String companyId,
			String employeeId) {

		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		Optional<Employee> empDomain = this.empBasicInfoRepo.findBySid(companyId, employeeId);
		if (empDomain.isPresent()) {
			returnList = mergeEmpBasicInfoAndItemDefListToListDto(empDomain.get(), initItemList);
		}
		return returnList;

	}

	private List<SettingItemDto> mergeEmpBasicInfoAndItemDefListToListDto(Employee empDomain,
			List<SettingItemDto> resultItemList) {

		for (SettingItemDto itemDto : resultItemList) {
			String itemCode = itemDto.getItemCode();
			switch (itemCode) {
			case "IS00020":
				itemDto.setData(empDomain.getSCd().v());
				break;
			case "IS00021":
				itemDto.setData(
						empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1).getJoinDate());
				break;
			case "IS00022":
				itemDto.setData(
						empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1).getAdoptDate());
				break;
			case "IS00024":
				itemDto.setData(
						empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1).getAdoptDate());
				break;
			case "IS00025":
				itemDto.setData(empDomain.getMobileMail().v());
				break;
			case "IS00026":
				itemDto.setData(empDomain.getCompanyMobile().v());
				break;
			case "IS00027":
				itemDto.setData(empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1)
						.getHiringType().v());
				break;
			case "IS00028":
				itemDto.setData(empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1)
						.getRetirementDate());
				break;
			}
		}

		return resultItemList;
	}

	// Employee End

	// load AffiliationDepartment start

	private List<SettingItemDto> loadAffiliationDepartmentInfo(List<SettingItemDto> initItemList, String employeeId,
			GeneralDate baseDate) {

		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		Optional<AffiliationDepartment> affDomain = this.affDepartmentRepo.getByEmpIdAndStandDate(employeeId, baseDate);
		if (affDomain.isPresent()) {
			returnList = mergeAffDepartmentAndItemDefListToListDto(affDomain.get(), initItemList);
		}
		return returnList;
	}

	private List<SettingItemDto> mergeAffDepartmentAndItemDefListToListDto(AffiliationDepartment affDomain,
			List<SettingItemDto> resultItemList) {

		for (SettingItemDto itemDto : resultItemList) {
			String itemCode = itemDto.getItemCode();
			switch (itemCode) {
			case "IS00020":
				itemDto.setData(affDomain.getPeriod().start());
				break;
			case "IS00021":
				itemDto.setData(affDomain.getPeriod().end());
				break;
			}
		}

		return resultItemList;
	}

	// AffiliationDepartment end

	// load Aff WorkPlace start
	private List<SettingItemDto> loadAffiliationWorkPlaceInfo(List<SettingItemDto> initItemList, String employeeId,
			GeneralDate baseDate) {
		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		List<AffWorkplaceHistory> affWorkDomain = this.affWorkRepo.searchWorkplaceHistoryByEmployee(employeeId,
				baseDate);
		if (!affWorkDomain.isEmpty()) {

			returnList = mergeAffWorkAndItemDefListToListDto(affWorkDomain.get(affWorkDomain.size() - 1), initItemList);

		}
		return returnList;
	}

	private List<SettingItemDto> mergeAffWorkAndItemDefListToListDto(AffWorkplaceHistory affWorkDomain,
			List<SettingItemDto> resultItemList) {
		// code item not yet
		for (SettingItemDto itemDto : resultItemList) {
			String itemCode = itemDto.getItemCode();
			switch (itemCode) {
			case "IS00020":
				itemDto.setData(affWorkDomain.getPeriod().start());
				break;
			case "IS00021":
				itemDto.setData(affWorkDomain.getPeriod().end());
				break;
			}
		}
		return resultItemList;

	}
	// Aff WorkPlace end

	private SettingItemDto fromInitValuetoDto(PerInfoInitValueSetItem domain) {
		return SettingItemDto.createFromJavaType(domain.getPerInfoCtgId(), domain.getCtgCode(),
				domain.getPerInfoItemDefId(), domain.getItemCode(), domain.getItemName(), domain.getIsRequired().value,
				domain.getSaveDataType().value, domain.getDateValue(), domain.getIntValue().v(),
				domain.getStringValue().v());
	}

	// sonnlb

}
