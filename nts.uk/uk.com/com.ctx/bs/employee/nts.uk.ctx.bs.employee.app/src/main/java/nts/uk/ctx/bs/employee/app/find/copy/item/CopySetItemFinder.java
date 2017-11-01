package nts.uk.ctx.bs.employee.app.find.copy.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.employee.info.itemdata.EmpInfoItemDataFinder;
import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;
import nts.uk.ctx.bs.employee.dom.temporaryAbsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryAbsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopySetItemFinder {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	private EmpInfoItemDataFinder infoItemDataFinder;

	@Inject
	private TemporaryAbsenceRepository tempAbsenceRepo;

	public List<SettingItemDto> getEmpCopySettingItemList(String categoryCd, String employeeId, GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		List<EmpCopySettingItem> itemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId);

		List<SettingItemDto> resultItemList = itemList.stream().map(
				x -> SettingItemDto.createFromJavaType(x.getItemCode(), x.getItemName(), x.getIsRequired().value, ""))
				.collect(Collectors.toList());

		if (itemList.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("Msg_347"));
		}

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
			resultItemList = loadAffiliationDepartmentInfo(resultItemList, companyId, employeeId);
			break;

		}

		resultItemList.addAll(this.infoItemDataFinder.loadInfoItemDataList(categoryCd, companyId, employeeId));

		return resultItemList;

	}

	// TemporaryAbsence start
	private List<SettingItemDto> loadTemporaryAbsenceInfo(List<SettingItemDto> resultItemList, String employeeId,
			GeneralDate baseDate) {
		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		Optional<TemporaryAbsence> opttemAbsence = this.tempAbsenceRepo.getBySid(employeeId, baseDate);
		if (opttemAbsence.isPresent()) {

			returnList = mergeTemporaryAbsenceInfoAndItemCopyListToListDto(opttemAbsence.get(), resultItemList);

		}
		return returnList;
	}

	private List<SettingItemDto> mergeTemporaryAbsenceInfoAndItemCopyListToListDto(TemporaryAbsence tempDomain,
			List<SettingItemDto> resultItemList) {
		// code item not yet
		for (SettingItemDto item : resultItemList) {
			String itemCode = item.getItemCode();
			switch (itemCode) {
			case "IS00020":
				item.setData(tempDomain.getTempAbsenceType().value);
				break;
			case "IS00021":
				item.setData(tempDomain.getStartDate());

				break;
			case "IS00022":
				item.setData(tempDomain.getEndDate());
				break;
			case "IS00023":
				item.setData(tempDomain.getTempAbsenceReason());
				break;
			case "IS00024":
				item.setData(tempDomain.getBirthDate());
				break;
			}
		}
		return resultItemList;
	}

	// TemporaryAbsence end
	private List<SettingItemDto> loadAffiliationDepartmentInfo(List<SettingItemDto> resultItemList, String companyId,
			String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	// AffiliationWorkPlace start
	private List<SettingItemDto> loadAffiliationWorkPlaceInfo(List<SettingItemDto> resultItemList, String employeeId,
			GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	// AffiliationWorkPlace end

	// JobTitleHistory start
	private List<SettingItemDto> loadJobTitleHistoryInfo(List<SettingItemDto> resultItemList, String employeeId,
			GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	// JobTitleHistory end
	// Employee start
	private List<SettingItemDto> loadEmployeeInfo(List<SettingItemDto> resultItemList, String companyId,
			String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	// Employee end

}
