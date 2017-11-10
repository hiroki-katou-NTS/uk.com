package nts.uk.ctx.bs.employee.app.command.employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.app.find.init.item.SettingItemDto;
import nts.uk.ctx.bs.employee.app.find.layout.LayoutFinder;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmpInfoCtgData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sonnlb
 *
 */
@Stateless
public class AddEmployeeCommandHandler extends CommandHandler<AddEmployeeCommand> {
	@Inject
	private LayoutFinder layoutFinder;

	@Inject
	private FamilyRepository familyRepo;

	@Inject
	private PersonRepository personRepo;

	@Inject
	private EmployeeRepository employeeRepo;

	@Inject
	private CurrentAddressRepository currentAddressRepo;

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepo;

	@Inject
	private EmInfoCtgDataRepository empInfoCtgDataRepo;

	private String dateStringFormat = "ddMMyyyy";

	@Override
	protected void handle(CommandHandlerContext<AddEmployeeCommand> context) {

		AddEmployeeCommand command = context.getCommand();

		List<SettingItemDto> dataList = this.layoutFinder.loadAllItemByCreateType(command.getCreateType(),
				command.getInitSettingId(), command.getHireDate(), command.getEmployeeCopyId());

		// merge data from client with dataList
		dataList = mergeData(dataList, command.getItemDataList());

		String personId = IdentifierUtil.randomUniqueId();

		String companyId = AppContexts.user().companyId();

		String employeeId = IdentifierUtil.randomUniqueId();

		// 個人基本 1st

		createNewPerson(getAllItemInCategoryByCode(dataList, "CS00001"), personId, employeeId);

		// 社員基本情報 2nd

		createNewEmployee(getAllItemInCategoryByCode(dataList, "CS00002"), personId, employeeId, companyId);

		// 連続履歴

		createCurrentAddress(getAllItemInCategoryByCode(dataList, "CS00003"), personId, employeeId);

		// 家族

		createNewFamily(getAllItemInCategoryByCode(dataList, "CS00004"), personId, employeeId);

		// 社員情報カテゴリデータ

		// create Optional Category Data;

		// because getItemValueByCode remove all item system required = >
		// There's only Optinal Item
		List<List<SettingItemDto>> categoryList = new ArrayList<List<SettingItemDto>>();

		categoryList.stream().forEach(x -> createEmpInfoCategoryData(x, null, personId));

	}

	private List<SettingItemDto> getAllItemInCategoryByCode(List<SettingItemDto> sourceList, String categoryCode) {
		return sourceList.stream().filter(x -> x.getCategoryCode().equals(categoryCode)).collect(Collectors.toList());
	}

	// private void createPerInfoCategoryData(List<SettingItemDto> dataList,
	// String paramRecordId, String personId) {
	// if (!dataList.isEmpty()) {
	//
	// String CtgId = dataList.get(0).getPerInfoCtgId();
	//
	// String recordId = paramRecordId == null ? IdentifierUtil.randomUniqueId()
	// : paramRecordId;
	//
	// // add PersonInfoCtgData
	//
	// PerInfoCtgData perInfoCtgData = new PerInfoCtgData(recordId, CtgId,
	// personId);
	//
	// this.perInfoCtgDataRepo.addCategoryData(perInfoCtgData);
	//
	// // add PersonInfoItemData
	//
	// List<PersonInfoItemData> itemList = dataList.stream().map(x ->
	// toPersonInfoItemData(recordId, x))
	// .collect(Collectors.toList());
	//
	// for (PersonInfoItemData itemData : itemList) {
	//
	// this.perInfoItemDataRepo.addItemData(itemData);
	// }
	// }
	// }

	private void createEmpInfoCategoryData(List<SettingItemDto> dataList, String recordId, String EmployeeId) {
		if (!dataList.isEmpty()) {

			String CtgId = dataList.get(0).getPerInfoCtgId();

			// add EmployeeInfoCtgData

			EmpInfoCtgData empInfoCtgData = new EmpInfoCtgData(recordId != null ? recordId : CtgId, CtgId, EmployeeId);

			this.empInfoCtgDataRepo.addCategoryData(empInfoCtgData);

			// add PersonInfoItemData

			List<EmpInfoItemData> itemList = dataList.stream().map(x -> toEmployeeInfoItemData(recordId, x))
					.collect(Collectors.toList());

			for (EmpInfoItemData itemData : itemList) {

				this.empInfoItemDataRepo.addItemData(itemData);
			}
		}
	}

	private void createCurrentAddress(List<SettingItemDto> dataList, String personId, String employeeId) {

		String currentAddressId = IdentifierUtil.randomUniqueId();

		String countryId = getItemValueByCode(dataList, "IS00044");
		String postalCode = getItemValueByCode(dataList, "IS00044");
		String phoneNumber = getItemValueByCode(dataList, "IS00044");
		String prefectures = getItemValueByCode(dataList, "IS00044");
		String houseRent = getItemValueByCode(dataList, "IS00044");
		GeneralDate StartDate = GeneralDate.fromString(getItemValueByCode(dataList, "IS00047"), dateStringFormat);
		GeneralDate endDate = GeneralDate.fromString(getItemValueByCode(dataList, "IS00047"), dateStringFormat);
		String address1 = getItemValueByCode(dataList, "IS00044");
		String addresskana1 = getItemValueByCode(dataList, "IS00044");
		String address2 = getItemValueByCode(dataList, "IS00044");
		String addresskana2 = getItemValueByCode(dataList, "IS00044");
		String homeSituationType = getItemValueByCode(dataList, "IS00044");
		String personMailAddress = getItemValueByCode(dataList, "IS00044");
		String houseType = getItemValueByCode(dataList, "IS00044");
		String nearestStation = getItemValueByCode(dataList, "IS00044");

		CurrentAddress newCurrentAddress = CurrentAddress.createFromJavaType(currentAddressId, personId, countryId,
				postalCode, phoneNumber, prefectures, houseRent, StartDate, endDate, address1, addresskana1, address2,
				addresskana2, homeSituationType, personMailAddress, houseType, nearestStation);

		this.currentAddressRepo.addCurrentAddress(newCurrentAddress);

		// because getItemValueByCode remove all item system required = >
		// There's only Optinal Item

		createEmpInfoCategoryData(dataList, currentAddressId, employeeId);

	}

	private EmpInfoItemData toEmployeeInfoItemData(String recordId, SettingItemDto item) {

		int saveType = item.getSaveData().getSaveDataType().value;

		String stringValue = item.getValueAsString();

		GeneralDate dataValue = GeneralDate.fromString(item.getValueAsString(), dateStringFormat);

		BigDecimal bigDecimalvalue = BigDecimal.valueOf(Integer.parseInt(item.getValueAsString()));

		return EmpInfoItemData.createFromJavaType(item.getItemDefId(), recordId, saveType, stringValue, bigDecimalvalue,
				dataValue);

	}

	// private PersonInfoItemData toPersonInfoItemData(String recordId,
	// SettingItemDto item) {
	//
	// int saveType = item.getSaveData().getSaveDataType().value;
	//
	// String stringValue = item.getValueAsString();
	//
	// GeneralDate dataValue = GeneralDate.fromString(item.getValueAsString(),
	// dateStringFormat);
	//
	// BigDecimal bigDecimalvalue =
	// BigDecimal.valueOf(Integer.parseInt(item.getValueAsString()));
	//
	// return PersonInfoItemData.createFromJavaType(item.getItemDefId(),
	// recordId, saveType, stringValue,
	// bigDecimalvalue, dataValue);
	//
	// }

	private void createNewEmployee(List<SettingItemDto> dataList, String personId, String employeeId,
			String companyId) {

		String EmployeeCd = getItemValueByCode(dataList, "IS00044");
		String companyMail = getItemValueByCode(dataList, "IS00044");
		String mobileMail = getItemValueByCode(dataList, "IS00044");
		String companyMobile = getItemValueByCode(dataList, "IS00044");

		Employee newEmployee = Employee.createFromJavaType(companyId, personId, employeeId, EmployeeCd, companyMail,
				mobileMail, companyMobile);

		this.employeeRepo.addNewEmployee(newEmployee);

		// because getItemValueByCode remove all item system required = >
		// There's only Optinal Item

		createEmpInfoCategoryData(dataList, employeeId, employeeId);

	}

	private void createNewPerson(List<SettingItemDto> dataList, String personId, String employeeId) {

		GeneralDate birthDate = GeneralDate.fromString(getItemValueByCode(dataList, "IS00047"), dateStringFormat);
		int bloodType = Integer.parseInt(getItemValueByCode(dataList, "IS00055"));
		int gender = Integer.parseInt(getItemValueByCode(dataList, "IS00055"));
		String mailAddress = getItemValueByCode(dataList, "IS00044");
		String personMobile = getItemValueByCode(dataList, "IS00044");
		String businessName = getItemValueByCode(dataList, "IS00044");
		String personName = getItemValueByCode(dataList, "IS00044");
		String businessOtherName = getItemValueByCode(dataList, "IS00044");
		String businessEnglishName = getItemValueByCode(dataList, "IS00044");
		String personNameKana = getItemValueByCode(dataList, "IS00044");
		String personRomanji = getItemValueByCode(dataList, "IS00044");
		String personRomanjiKana = getItemValueByCode(dataList, "IS00044");
		String todokedeFullName = getItemValueByCode(dataList, "IS00044");
		String todokedeFullNameKana = getItemValueByCode(dataList, "IS00044");
		String oldName = getItemValueByCode(dataList, "IS00044");
		String oldNameKana = getItemValueByCode(dataList, "IS00044");
		String todokedeOldFullName = getItemValueByCode(dataList, "IS00044");
		String todokedeOldFullNameKana = getItemValueByCode(dataList, "IS00044");
		String hobBy = getItemValueByCode(dataList, "IS00044");
		String countryId = getItemValueByCode(dataList, "IS00044");
		String taste = getItemValueByCode(dataList, "IS00044");

		Person newPerson = Person.createFromJavaType(birthDate, bloodType, gender, personId, mailAddress, personMobile,
				businessName, personName, businessOtherName, businessEnglishName, personNameKana, personRomanji,
				personRomanjiKana, todokedeFullName, todokedeFullNameKana, oldName, oldNameKana, todokedeOldFullName,
				todokedeOldFullNameKana, hobBy, countryId, taste);

		this.personRepo.addNewPerson(newPerson);

		// because getItemValueByCode remove all item system required = >
		// There's only Optinal Item

		createEmpInfoCategoryData(dataList, personId, employeeId);

	}

	private void createNewFamily(List<SettingItemDto> dataList, String personId, String employeeId) {

		GeneralDate birthday = GeneralDate.fromString(getItemValueByCode(dataList, "IS00047"), dateStringFormat);
		GeneralDate deadDay = GeneralDate.fromString(getItemValueByCode(dataList, "IS00048"), dateStringFormat);
		GeneralDate entryDate = GeneralDate.fromString(getItemValueByCode(dataList, "IS00049"), dateStringFormat);
		GeneralDate expelledDate = GeneralDate.fromString(getItemValueByCode(dataList, "IS00050"), dateStringFormat);
		String familyId = IdentifierUtil.randomUniqueId();
		String fullName = getItemValueByCode(dataList, "IS00040");
		String fullNameKana = getItemValueByCode(dataList, "IS00041");
		String nameMultiLangFull = getItemValueByCode(dataList, "IS00044");
		String nameMultiLangFullKana = getItemValueByCode(dataList, "IS00045");
		String nameRomajiFull = getItemValueByCode(dataList, "IS00042");
		String nameRomajiFullKana = getItemValueByCode(dataList, "IS00043");
		String nationalityId = getItemValueByCode(dataList, "IS00051");
		String occupationName = getItemValueByCode(dataList, "IS00052");
		String relationship = getItemValueByCode(dataList, "IS00053");
		int supportCareType = Integer.parseInt(getItemValueByCode(dataList, "IS00055"));
		String tokodekeName = getItemValueByCode(dataList, "IS00046");
		int togSepDivisionType = Integer.parseInt(getItemValueByCode(dataList, "IS00054"));
		int workStudentType = Integer.parseInt(getItemValueByCode(dataList, "IS00056"));

		Family newFamily = Family.createFromJavaType(birthday, deadDay, entryDate, expelledDate, familyId, fullName,
				fullNameKana, nameMultiLangFull, nameMultiLangFullKana, nameRomajiFull, nameRomajiFullKana,
				nationalityId, occupationName, personId, relationship, supportCareType, tokodekeName,
				togSepDivisionType, workStudentType);

		this.familyRepo.addFamily(newFamily);

		// because getItemValueByCode remove all item system required = >
		// There's only Optinal Item

		createEmpInfoCategoryData(dataList, familyId, employeeId);

	}

	private String getItemValueByCode(List<SettingItemDto> sourceList, String itemCode) {

		String returnString = "";
		Optional<SettingItemDto> optItem = sourceList.stream().filter(x -> x.getItemCode().equals(itemCode))
				.findFirst();

		if (optItem.isPresent()) {

			returnString = optItem.get().getValueAsString();
			// remove item if found
			sourceList.remove(optItem.get());
		}

		return returnString;
	}

	private List<SettingItemDto> mergeData(List<SettingItemDto> sourceList,
			List<LayoutPersonInfoCommand> resourceList) {

		for (LayoutPersonInfoCommand itemCommand : resourceList) {

			Optional<SettingItemDto> itemOpt = findItemById(sourceList, itemCommand.getItemDefId());

			if (itemOpt.isPresent()) {
				SettingItemDto settingItem = itemOpt.get();
				settingItem.setData(itemCommand.getValue().toString());
			}

		}
		return sourceList;
	}

	private Optional<SettingItemDto> findItemById(List<SettingItemDto> dataList, String itemDefId) {

		return dataList.stream().filter(x -> x.getItemDefId().equals(itemDefId)).findFirst();
	}

}
