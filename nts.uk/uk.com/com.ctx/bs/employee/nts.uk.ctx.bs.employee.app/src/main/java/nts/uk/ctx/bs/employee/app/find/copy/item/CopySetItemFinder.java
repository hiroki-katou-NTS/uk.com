package nts.uk.ctx.bs.employee.app.find.copy.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopySetItemFinder {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	private EmpInfoItemDataRepository infoItemDataRepo;

	public List<SettingItemDto> getEmpCopySettingItemList(String categoryCd, String employeeId, GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		List<EmpCopySettingItem> itemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId);

		List<SettingItemDto> resultItemList = new ArrayList<SettingItemDto>();

		if (itemList.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("Msg_347"));
		}

		switch (categoryCd) {
		// 社員基本情報 - Employee
		case "CS00002":
			resultItemList = loadEmployeeInfo(itemList, companyId, employeeId);
			break;
		// 休職・休業 - TemporaryAbsence
		case "CS00008":
			resultItemList = loadTemporaryAbsenceInfo(itemList, employeeId, baseDate);
			break;
		// 職務職位履歴 - JobTitleHistory
		case "CS00009":
			resultItemList = loadJobTitleHistoryInfo(itemList, employeeId, baseDate);
			break;
		// 所属職場 - AffiliationWorkplaceHistory
		case "CS00010":
			resultItemList = loadAffiliationWorkPlaceInfo(itemList, employeeId, baseDate);
			break;
		// 所属部門 - AffiliationDepartment
		case "CS00011":
			resultItemList = loadAffiliationDepartmentInfo(itemList, companyId, employeeId);
			break;

		}

		resultItemList.addAll(loadInfoItemDataList(categoryCd, companyId));

		return resultItemList;

	}

	private List<SettingItemDto> loadTemporaryAbsenceInfo(List<EmpCopySettingItem> itemList, String employeeId,
			GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SettingItemDto> loadAffiliationDepartmentInfo(List<EmpCopySettingItem> itemList, String companyId,
			String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SettingItemDto> loadAffiliationWorkPlaceInfo(List<EmpCopySettingItem> itemList, String employeeId,
			GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SettingItemDto> loadJobTitleHistoryInfo(List<EmpCopySettingItem> itemList, String employeeId,
			GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SettingItemDto> loadEmployeeInfo(List<EmpCopySettingItem> itemList, String companyId,
			String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SettingItemDto> loadInfoItemDataList(String categoryCd, String companyId) {
		return this.infoItemDataRepo.getAllInfoItem(categoryCd, companyId).stream()
				.map(x -> SettingItemDto.fromInfoDataItem(x)).collect(Collectors.toList());
	}

}
