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

	@Inject
	private PerInfoItemDefRepositoty infoItemDefRepo;

	// sonnlb
	public List<InitValueSettingItemDto> getAllInitItem(String settingId, String categoryCd) {

		List<InitValueSettingItemDto> resultItemList = new ArrayList<InitValueSettingItemDto>();

		if (isPerSonSettingCtg(categoryCd)) {

			List<PerInfoInitValueSetItem> listItem = this.settingItemRepo.getAllInitItem(settingId, categoryCd);
			if (hasItemSameAsLogin(listItem)) {

				resultItemList = loadInfoData(listItem, categoryCd);

			} else {

				resultItemList = listItem.stream().map(x -> fromInitValuetoDto(x)).collect(Collectors.toList());

			}

		} else {

			resultItemList = loadBasicItem(categoryCd);

		}
		return resultItemList;
	}

	private List<InitValueSettingItemDto> loadInfoData(List<PerInfoInitValueSetItem> listItem, String categoryCd) {

		List<InitValueSettingItemDto> ItemListDto = listItem.stream().map(x -> fromInitValuetoDto(x))
				.collect(Collectors.toList());

		ItemListDto.addAll(loadInfoItemList(categoryCd));

		return ItemListDto;
	}

	private List<InitValueSettingItemDto> loadInfoItemList(String categoryCd) {
		return this.infoItemDataRepo.getAllInfoItem(categoryCd).stream().map(x -> fromInfoItemtoDto(x))
				.collect(Collectors.toList());
	}

	private InitValueSettingItemDto fromInfoItemtoDto(EmpInfoItemData domain) {

		return InitValueSettingItemDto.createFromJavaType(domain.getItemName(), domain.getIsRequired().value,
				domain.getDataState().getDataStateType().value, domain.getDataState().getDateValue(),
				domain.getDataState().getNumberValue(), domain.getDataState().getStringValue());
	}

	private boolean hasItemSameAsLogin(List<PerInfoInitValueSetItem> listItem) {
		if (!listItem.isEmpty()) {

			return listItem.stream().filter(obj -> obj.getRefMethodType().equals(ReferenceMethodType.SAMEASLOGIN))
					.findFirst().isPresent();

		} else {

			return false;

		}
	}

	private List<InitValueSettingItemDto> loadBasicItem(String categoryCd) {
		List<InitValueSettingItemDto> returnList = new ArrayList<InitValueSettingItemDto>();
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		switch (categoryCd) {
		// 社員基本情報
		case "CS00002":
			returnList = loadEmployeeInfo(companyId, employeeId, categoryCd);
			break;
		case "CS00011":

			break;
		case "CS00010":

			break;
		case "CS00009":

			break;
		case "CS00008":

			break;
		}

		returnList.addAll(loadInfoItemList(categoryCd));

		return returnList;
	}

	private List<InitValueSettingItemDto> loadEmployeeInfo(String companyId, String employeeId, String categoryCd) {

		List<InitValueSettingItemDto> returnList = new ArrayList<InitValueSettingItemDto>();

		Optional<Employee> empDomain = this.empBasicInfoRepo.findBySid(companyId, employeeId);
		if (empDomain.isPresent()) {

			List<PersonInfoItemDefinition> itemDefListDomain = this.infoItemDefRepo.getAllItemFromCodeList(companyId,
					categoryCd, Employee.getItemCodes());
			returnList = mergeEmpBasicInfoAndItemDefListToListDto(empDomain.get(), itemDefListDomain);
		}
		return returnList;

	}

	private boolean isPerSonSettingCtg(String categoryCd) {
		return categoryCd.substring(1, 2).equals("O");
	}

	private InitValueSettingItemDto fromInitValuetoDto(PerInfoInitValueSetItem domain) {
		return InitValueSettingItemDto.createFromJavaType(domain.getItemName(), domain.getIsRequired().value,
				domain.getSaveDataType().value, domain.getDateValue(), domain.getIntValue().v(),
				domain.getStringValue().v());
	}

	private List<InitValueSettingItemDto> mergeEmpBasicInfoAndItemDefListToListDto(Employee empDomain,
			List<PersonInfoItemDefinition> itemDefListDomain) {
		List<InitValueSettingItemDto> returnList = new ArrayList<InitValueSettingItemDto>();

		PersonInfoItemDefinition item = findItemByCode("IS00020", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				empDomain.getSCd().v()));

		item = findItemByCode("IS00021", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1).getJoinDate()));

		item = findItemByCode("IS00022", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1).getAdoptDate()));

		item = findItemByCode("IS00024", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				empDomain.getCompanyMail().v()));

		item = findItemByCode("IS00025", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				empDomain.getMobileMail().v()));

		item = findItemByCode("IS00026", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				empDomain.getCompanyMobile().v()));

		item = findItemByCode("IS00027", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				new BigDecimal(empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1)
						.getHiringType().v())));

		item = findItemByCode("IS00028", itemDefListDomain);
		returnList.add(InitValueSettingItemDto.createFromJavaType(item.getItemName().v(), item.getIsRequired().value,
				empDomain.getListEntryJobHist().get(empDomain.getListEntryJobHist().size() - 1).getRetirementDate()));

		return returnList;
	}

	private PersonInfoItemDefinition findItemByCode(String itemCode, List<PersonInfoItemDefinition> itemDefListDomain) {
		return itemDefListDomain.stream().filter(x -> x.getItemCode().equals(itemCode)).findFirst().get();
	}

	// sonnlb

}
