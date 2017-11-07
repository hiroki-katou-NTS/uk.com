package nts.uk.ctx.bs.employee.app.command.category;

import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.employee.LayoutPersonInfoCommand;
import nts.uk.ctx.bs.employee.dom.employeeinfo.CompanyMobile;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeMail;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.person.dom.person.currentaddress.AddressSet1;
import nts.uk.ctx.bs.person.dom.person.currentaddress.AddressSet2;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.HomeSituationType;
import nts.uk.ctx.bs.person.dom.person.currentaddress.HouseRent;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddress1;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddress2;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddressKana1;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PersonAddressKana2;
import nts.uk.ctx.bs.person.dom.person.currentaddress.PostalCode;
import nts.uk.ctx.bs.person.dom.person.currentaddress.Prefectures;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FullNameKana;
import nts.uk.ctx.bs.person.dom.person.family.NameMultiLangFull;
import nts.uk.ctx.bs.person.dom.person.family.NameMultiLangFullKana;
import nts.uk.ctx.bs.person.dom.person.family.NameRomajiFull;
import nts.uk.ctx.bs.person.dom.person.family.NameRomajiFullKana;
import nts.uk.ctx.bs.person.dom.person.family.OccupationName;
import nts.uk.ctx.bs.person.dom.person.family.RelationShip;
import nts.uk.ctx.bs.person.dom.person.family.SupportCareType;
import nts.uk.ctx.bs.person.dom.person.family.TogSepDivisionType;
import nts.uk.ctx.bs.person.dom.person.family.TokodekeName;
import nts.uk.ctx.bs.person.dom.person.family.WorkStudentType;
import nts.uk.ctx.bs.person.dom.person.info.CountryId;
import nts.uk.ctx.bs.person.dom.person.info.GenderPerson;
import nts.uk.ctx.bs.person.dom.person.info.Hobby;
import nts.uk.ctx.bs.person.dom.person.info.Nationality;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonMailAddress;
import nts.uk.ctx.bs.person.dom.person.info.PersonMobile;
import nts.uk.ctx.bs.person.dom.person.info.Taste;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessEnglishName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessOtherName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameKana;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class DomainValueFactory {
	/** The Constant DATE_FORMAT. */
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	public static void matchInformation(List<LayoutPersonInfoCommand> listItem, Person person) {
		PersonNameGroup perNameGroup = new PersonNameGroup();
		for (LayoutPersonInfoCommand dataInfoItem : listItem) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00001":
				// 個人名グループ．個人名
				perNameGroup.setPersonName(new PersonName(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00002":
				// 個人名グループ．個人名カナ
				perNameGroup.setPersonNameKana(new PersonNameKana(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00003":
				// 個人名グループ．個人名ローマ字．氏名
//				perNameGroup.setPersonRomanji(new FullName(dataInfoItem.getValue()));
//				authClassItem.getDataItems().add(
//						SaveDataDto.createDataDto(person.getPersonNameGroup().getPersonRomanji().getFullName().v()));
				break;
			case "IS00004":
				// 個人名グループ．個人名ローマ字．氏名カナ
//				authClassItem.getDataItems().add(person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v());
				break;
			case "IS00005":
				// 個人名グループ．ビジネスネーム
				perNameGroup.setBusinessName(new BusinessName(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00006":
				// 個人名グループ．ビジネスネーム．英語
				perNameGroup.setBusinessEnglishName(new BusinessEnglishName(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00007":
				// 個人名グループ．ビジネスネーム．その他
				perNameGroup.setBusinessOtherName(new BusinessOtherName(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00008":
				// 個人名グループ．個人旧氏名．氏名
//				perNameGroup.setOldName(p
//				authClassItem.getDataItems().add(person.getPersonNameGroup().getOldName().getFullName().v());
				break;
			case "IS00009":
				// 個人名グループ．個人旧氏名．氏名カナ
//				authClassItem.getDataItems().add(person.getPersonNameGroup().getOldName().getFullNameKana().v());
				break;
			case "IS00010":
				// 個人名グループ．個人届出名称．氏名
//				authClassItem.getDataItems().add(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00011":
				// 個人名グループ．個人届出名称．氏名カナ
//				authClassItem.getDataItems()
//						.add(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00012":
				// 個人名グループ．個人届出名称．氏名
//				authClassItem.getDataItems().add(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00013":
				// 個人名グループ．個人届出名称．氏名カナ
//				authClassItem.getDataItems()
//						.add(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00014":
				// 性別
				person.setGender(EnumAdaptor.valueOf(convertToInt(dataInfoItem.getValue()), GenderPerson.class));
				break;
			case "IS00015":
				// 個人携帯
				person.setPersonMobile(new PersonMobile(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00016":
				// 個人メールアドレス
				person.setMailAddress(new PersonMailAddress(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00017":
				// 趣味
				person.setHobBy(new Hobby(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00018":
				// 嗜好
				person.setTaste(new Taste(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00019":
				// 国籍
				person.setCountryId(new Nationality(convertToString(dataInfoItem.getValue())));
				break;
			}
			person.setPersonNameGroup(perNameGroup);
		}
	}

	public static void matchInformation(List<LayoutPersonInfoCommand> listItem, CurrentAddress currentAddress) {
		for (LayoutPersonInfoCommand dataInfoItem : listItem) {
			// Address 1
			AddressSet1 address1 = new AddressSet1();
			
			// Address 2
			AddressSet2 address2 = new AddressSet2();
			
			// Date period
			GeneralDate startDate = null;
			GeneralDate endDate = null;
			boolean flag = false;
			
			switch (dataInfoItem.getItemCode()) {
			case "IS00029":
				/*
				 * 現住所．期間 現住所．期間．開始日 現住所．期間．終了日
				 */
				if (!flag){
					startDate = convertToDate(dataInfoItem.getValue());
					flag = true;
				} else {
					endDate = convertToDate(dataInfoItem.getValue());
				}
				break;
			case "IS00030":
				// 現住所．郵便番号
				currentAddress.setPostalCode(new PostalCode(convertToString(dataInfoItem.getValue())));
				break;

			case "IS00031":
				// 現住所．都道府県
				currentAddress.setPrefectures(new Prefectures(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00032":
				// 現住所．国
				currentAddress.setCountryId(convertToString(dataInfoItem.getValue()));
				break;
			case "IS00033":
				// 現住所．住所１
				address1.setAddress1(new PersonAddress1(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00034":
				// 現住所．住所カナ１
				address1.setAddressKana1(new PersonAddressKana1(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00035":
				// 現住所．住所2
				address2.setAddress2(new PersonAddress2(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00036":
				// 現住所．住所カナ2
				address2.setAddressKana2(new PersonAddressKana2(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00037":
				// 現住所．電話番号
				currentAddress.setPhoneNumber(new PersonMobile(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00038":
				// 現住所．住宅状況種別
				currentAddress.setHomeSituationType(new HomeSituationType(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00039":
				// 現住所．社宅家賃
				currentAddress.setHouseRent(new HouseRent(convertToString(dataInfoItem.getValue())));
				break;

			}
			// Address 1
			currentAddress.setAddress1(address1);
			// Address 2
			currentAddress.setAddress2(address2);
			// date period
			currentAddress.setPeriod(new DatePeriod(startDate, endDate));
		}
	}

	public static void matchInformation(List<LayoutPersonInfoCommand> listItem, Family family) {
		for(LayoutPersonInfoCommand infoItem: listItem){
			switch(infoItem.getItemCode()){
			case "IS00040":
				//氏名
				family.setFullName(new nts.uk.ctx.bs.person.dom.person.family.FullName(convertToString(infoItem.getValue())));
				break;
			case "IS00041":
				//氏名カナ
				family.setFullNameKana(new FullNameKana(convertToString(infoItem.getValue())));
				break;
			case "IS00042":
				//氏名ローマ字
				family.setNameRomajiFull(new NameRomajiFull(convertToString(infoItem.getValue())));
				break;
			case "IS00043":
				//氏名ローマ字カナ
				family.setNameRomajiFullKana(new NameRomajiFullKana(convertToString(infoItem.getValue())));
				break;
			case "IS00044":
				//氏名他言語
				family.setNameMultiLangFull(new NameMultiLangFull(convertToString(infoItem.getValue())));
				break;
			case "IS00045":
				//氏名他言語カナ
				family.setNameMultiLangFullKana(new NameMultiLangFullKana(convertToString(infoItem.getValue())));
				break;
			case "IS00046":
				//届出氏名
				family.setTokodekeName(new TokodekeName(convertToString(infoItem.getValue())));
				break;
			case "IS00047":
				//生年月日
				family.setBirthday(convertToDate(infoItem.getValue()));
				break;
			case "IS00048":
				//死亡年月日
				family.setDeadDay(convertToDate(infoItem.getValue()));
				break;
			case "IS00049":
				//入籍年月日
				family.setDeadDay(convertToDate(infoItem.getValue()));
				break;				
			case "IS00050":
				//除籍年月日
				family.setExpelledDate(convertToDate(infoItem.getValue()));
				break;
			case "IS00051":
				//国籍
				family.setNationalityId(new CountryId(convertToString(infoItem.getValue())));
				break;
			case "IS00052":
				//職業
				family.setOccupationName(new OccupationName(convertToString(infoItem.getValue())));
				break;
			case "IS00053":
				//続柄
				family.setRelationship(new RelationShip(convertToString(infoItem.getValue())));
				break;
			case "IS00054":
				//同居別居区分
				family.setTogSepDivisionType(EnumAdaptor.valueOf(convertToInt(infoItem.getValue()), TogSepDivisionType.class));
				break;
			case "IS00055":
				//支援介護区分
				family.setSupportCareType(EnumAdaptor.valueOf(convertToInt(infoItem.getValue()), SupportCareType.class));
				break;
			case "IS00056":
				//勤労学生
				family.setWorkStudentType(EnumAdaptor.valueOf(convertToInt(infoItem.getValue()), WorkStudentType.class) );
				break;
			}
		}
	}
	public static void matchInformation(List<LayoutPersonInfoCommand> listItem, Employee employee) {
		JobEntryHistory jobHist = new JobEntryHistory();
		for (LayoutPersonInfoCommand dataInfoItem : listItem) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00020":
				// 社員．社員コード
				employee.setSCd(new EmployeeCode(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00021":
				// 社員．入社年月日
				jobHist.setJoinDate(convertToDate(dataInfoItem.getValue()));
				break;
			case "IS00022":
				// 社員．本採用年月日
				// dataInfoItem.setData(employee);
				// QA
				break;
			case "IS00024":
				// 社員．会社メールアドレス
				employee.setCompanyMail(new EmployeeMail(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00025":
				// 社員．会社携帯メールアドレス
				employee.setMobileMail(new EmployeeMail(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00026":
				// 社員．会社携帯電話番号
				employee.setCompanyMobile(new CompanyMobile(convertToString(dataInfoItem.getValue())));
				break;
			case "IS00027":
				// 社員．採用区分
				// dataInfoItem.setData(employee);
				// QA
				break;
			case "IS00028":
				// 社員．退職年月日
//				authClassItem.getDataItems().add(employee.getRetirementDate());
				break;
			}
			// Job history
//			employee.setListEntryJobHist(listEntryJobHist);
		}

	}

//	public static void matchInformation(List<EmpAddItemCommand> listItem, TemporaryAbsence leaveHoliday) {
//	}
//
//	public static void matchInformation(List<EmpAddItemCommand> listItem, JobTitleMain jobTitleMain) {
//	}
//
//	public static void matchInformation(List<EmpAddItemCommand> listItem, AssignedWorkplace assignedWorkplace) {
//	}
//
//	public static void matchInformation(List<EmpAddItemCommand> listItem, AffiliationDepartment affDepartment) {
//	}
//
//	public static void matchInformation(List<EmpAddItemCommand> listItem, SubJobPosition subJobPosition) {
//	}
//
//	public static void matchPersEmerConts(List<EmpAddItemCommand> listItem,
//			List<PersonEmergencyContact> perEmerConts) {
//	}
//
//	public static void matchFamilies(List<EmpAddItemCommand> listItem, List<Family> families) {
//	}
//
//	public static void matchsubJobPoses(List<EmpAddItemCommand> listItem, List<SubJobPosition> subJobPoses) {
//	}
	
	private static String convertToString(Object obj){
		return obj == null ? "" : obj.toString();
	}
	
	private static int convertToInt(Object obj){
		try {
			return Integer.parseInt(convertToString(obj));
		} catch (Exception e) {
			return 0;
		}
	}
	
	private static GeneralDate convertToDate(Object obj){
		try {
			return GeneralDate.fromString(convertToString(obj), DATE_FORMAT);
		} catch (Exception e) {
			return null;
		}
	}
}
