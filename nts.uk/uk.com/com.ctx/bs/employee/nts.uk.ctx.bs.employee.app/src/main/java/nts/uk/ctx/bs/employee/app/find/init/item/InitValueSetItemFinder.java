package nts.uk.ctx.bs.employee.app.find.init.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.ReferenceMethodType;
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
	private EmpInfoItemDataRepository infoItemDataRepo;

	@Inject
	private EmployeeRepository empBasicInfoRepo;

	// sonnlb
	public List<InitValueSettingItemDto> getAllInitItem(String settingId, String categoryCd) {

		List<InitValueSettingItemDto> resultItemList = new ArrayList<InitValueSettingItemDto>();

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		List<PerInfoInitValueSetItem> initListItem = this.settingItemRepo.getAllInitItem(settingId, categoryCd);
		resultItemList = initListItem.stream().map(x -> fromInitValuetoDto(x)).collect(Collectors.toList());
		if (hasItemSameAsLogin(initListItem)) {

			switch (categoryCd) {
			//Employee- 社員基本情報
			case "CS00002":
				resultItemList = loadEmployeeInfo(resultItemList, companyId, employeeId);
				break;
			// 休職・休業
			case "CS00008":
				break;
			// 職務職位履歴
			case "CS00009":

				break;
			// 所属職場
			case "CS00010":

				break;
			//AffiliationDepartment- 所属部門
			case "CS00011":

				break;

			}
			resultItemList.addAll(loadInfoItemList(categoryCd));

		}
		return resultItemList;
	}

	private List<InitValueSettingItemDto> loadInfoItemList(String categoryCd) {
		return this.infoItemDataRepo.getAllInfoItem(categoryCd).stream().map(x -> fromInfoItemtoDto(x))
				.collect(Collectors.toList());
	}

	private InitValueSettingItemDto fromInfoItemtoDto(EmpInfoItemData domain) {

		return InitValueSettingItemDto.createFromJavaType(domain.getItemCode().v(), domain.getItemName(),
				domain.getIsRequired().value, domain.getDataState().getDataStateType().value,
				domain.getDataState().getDateValue(), domain.getDataState().getNumberValue(),
				domain.getDataState().getStringValue());
	}

	private boolean hasItemSameAsLogin(List<PerInfoInitValueSetItem> listItem) {
		if (!listItem.isEmpty()) {

			return listItem.stream().filter(obj -> obj.getRefMethodType().equals(ReferenceMethodType.SAMEASLOGIN))
					.findFirst().isPresent();

		} else {

			return false;

		}
	}

	private List<InitValueSettingItemDto> loadEmployeeInfo(List<InitValueSettingItemDto> initItemList, String companyId,
			String employeeId) {

		List<InitValueSettingItemDto> returnList = new ArrayList<InitValueSettingItemDto>();

		Optional<Employee> empDomain = this.empBasicInfoRepo.findBySid(companyId, employeeId);
		if (empDomain.isPresent()) {
			returnList = mergeEmpBasicInfoAndItemDefListToListDto(empDomain.get(), initItemList);
		}
		return returnList;

	}

	private InitValueSettingItemDto fromInitValuetoDto(PerInfoInitValueSetItem domain) {
		return InitValueSettingItemDto.createFromJavaType(domain.getItemCode(), domain.getItemName(),
				domain.getIsRequired().value, domain.getSaveDataType().value, domain.getDateValue(),
				domain.getIntValue().v(), domain.getStringValue().v());
	}

	private List<InitValueSettingItemDto> mergeEmpBasicInfoAndItemDefListToListDto(Employee empDomain,
			List<InitValueSettingItemDto> resultItemList) {

		for (InitValueSettingItemDto itemDto : resultItemList) {
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

	// sonnlb

}
