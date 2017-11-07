/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout;

import java.util.List;

import find.layout.classification.LayoutPersonInfoClsDto;
import find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.bs.employee.app.find.init.item.SaveDataDto;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpPersonInfoClassDto;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpPersonInfoItemDto;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;

/**
 * @author danpv
 * 
 *         Class lưu trữ cấu trúc đề mapping ItemDefinition và các Object Domain
 *         File tham khảo
 *         \\192.168.50.4\share\500_新構想開発\03_概要設計\01_概要設計書\04_共通\CPS_個人情報\個人情報定義\項目定義-説明書.xlsx
 *
 */
public class ItemDefinitionFactory {

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, Person person) {
		for (EmpPersonInfoItemDto dataInfoItem : authClassItem.getInfoItems()) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00001":
				// 個人名グループ．個人名
				authClassItem.getDataItems()
						.add(SaveDataDto.createDataDto(person.getPersonNameGroup().getPersonName().v()));
				break;
			case "IS00002":
				// 個人名グループ．個人名カナ
				authClassItem.getDataItems()
						.add(SaveDataDto.createDataDto(person.getPersonNameGroup().getPersonNameKana().v()));
				break;
			case "IS00003":
				// 個人名グループ．個人名ローマ字．氏名
				authClassItem.getDataItems().add(
						SaveDataDto.createDataDto(person.getPersonNameGroup().getPersonRomanji().getFullName().v()));
				break;
			case "IS00004":
				// 個人名グループ．個人名ローマ字．氏名カナ
				authClassItem.getDataItems().add(person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v());
				break;
			case "IS00005":
				// 個人名グループ．ビジネスネーム
				authClassItem.getDataItems().add(person.getPersonNameGroup().getBusinessName().v());
				break;
			case "IS00006":
				// 個人名グループ．ビジネスネーム．英語
				authClassItem.getDataItems().add(person.getPersonNameGroup().getBusinessEnglishName().v());
				break;
			case "IS00007":
				// 個人名グループ．ビジネスネーム．その他
				authClassItem.getDataItems().add(person.getPersonNameGroup().getBusinessOtherName().v());
				break;
			case "IS00008":
				// 個人名グループ．個人旧氏名．氏名
				authClassItem.getDataItems().add(person.getPersonNameGroup().getOldName().getFullName().v());
				break;
			case "IS00009":
				// 個人名グループ．個人旧氏名．氏名カナ
				authClassItem.getDataItems().add(person.getPersonNameGroup().getOldName().getFullNameKana().v());
				break;
			case "IS00010":
				// 個人名グループ．個人届出名称．氏名
				authClassItem.getDataItems().add(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00011":
				// 個人名グループ．個人届出名称．氏名カナ
				authClassItem.getDataItems()
						.add(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00012":
				// 個人名グループ．個人届出名称．氏名
				authClassItem.getDataItems().add(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00013":
				// 個人名グループ．個人届出名称．氏名カナ
				authClassItem.getDataItems()
						.add(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00014":
				// 性別
				authClassItem.getDataItems().add(person.getGender().value);
				break;
			case "IS00015":
				// 個人携帯
				authClassItem.getDataItems().add(person.getPersonMobile().toString());
				break;
			case "IS00016":
				// 個人メールアドレス
				authClassItem.getDataItems().add(person.getMailAddress().toString());
				break;
			case "IS00017":
				// 趣味
				authClassItem.getDataItems().add(person.getHobBy().toString());
				break;
			case "IS00018":
				// 嗜好
				authClassItem.getDataItems().add(person.getTaste().toString());
				break;
			case "IS00019":
				// 国籍
				authClassItem.getDataItems().add(person.getCountryId().toString());
				break;
			}
		}
	}

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, CurrentAddress currentAddress) {
		for (EmpPersonInfoItemDto dataInfoItem : authClassItem.getInfoItems()) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00029":
				/*
				 * 現住所．期間 現住所．期間．開始日 現住所．期間．終了日
				 */
				// dataInfoItem.setData(currentAddress.getPeriod());
				break;
			case "IS00030":
				// 現住所．郵便番号
				authClassItem.getDataItems().add(currentAddress.getPostalCode().v());
				break;

			case "IS00031":
				// 現住所．都道府県
				authClassItem.getDataItems().add(currentAddress.getPrefectures().v());
				break;
			case "IS00032":
				// 現住所．国
				authClassItem.getDataItems().add(currentAddress.getCountryId());
				break;
			case "IS00033":
				// 現住所．住所１
				authClassItem.getDataItems().add(currentAddress.getAddress1().getAddress1().v());
				break;
			case "IS00034":
				// 現住所．住所カナ１
				authClassItem.getDataItems().add(currentAddress.getAddress1().getAddressKana1().v());
				break;
			case "IS00035":
				// 現住所．住所2
				authClassItem.getDataItems().add(currentAddress.getAddress2().getAddress2().v());
				break;
			case "IS00036":
				// 現住所．住所カナ2
				authClassItem.getDataItems().add(currentAddress.getAddress2().getAddressKana2().v());
				break;
			case "IS00037":
				// 現住所．電話番号
				authClassItem.getDataItems().add(currentAddress.getPhoneNumber().v());
				break;
			case "IS00038":
				// 現住所．住宅状況種別
				authClassItem.getDataItems().add(currentAddress.getHomeSituationType().v());
				break;
			case "IS00039":
				// 現住所．社宅家賃
				authClassItem.getDataItems().add(currentAddress.getHouseRent().v());
				break;

			}
		}
	}

	//vinhpx: start
	//person
	public static void matchInformation(LayoutPersonInfoClsDto authClassItem, Person person) {
		for (PerInfoItemDefDto dataInfoItem : authClassItem.getListItemDf()) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00001":
				// 個人名グループ．個人名
				authClassItem.getItems()
						.add(SaveDataDto.createDataDto(person.getPersonNameGroup().getPersonName().v()));
				break;
			case "IS00002":
				// 個人名グループ．個人名カナ
				authClassItem.getItems()
						.add(SaveDataDto.createDataDto(person.getPersonNameGroup().getPersonNameKana().v()));
				break;
			case "IS00003":
				// 個人名グループ．個人名ローマ字．氏名
				authClassItem.getItems().add(
						SaveDataDto.createDataDto(person.getPersonNameGroup().getPersonRomanji().getFullName().v()));
				break;
			case "IS00004":
				// 個人名グループ．個人名ローマ字．氏名カナ
				authClassItem.getItems().add(person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v());
				break;
			case "IS00005":
				// 個人名グループ．ビジネスネーム
				authClassItem.getItems().add(person.getPersonNameGroup().getBusinessName().v());
				break;
			case "IS00006":
				// 個人名グループ．ビジネスネーム．英語
				authClassItem.getItems().add(person.getPersonNameGroup().getBusinessEnglishName().v());
				break;
			case "IS00007":
				// 個人名グループ．ビジネスネーム．その他
				authClassItem.getItems().add(person.getPersonNameGroup().getBusinessOtherName().v());
				break;
			case "IS00008":
				// 個人名グループ．個人旧氏名．氏名
				authClassItem.getItems().add(person.getPersonNameGroup().getOldName().getFullName().v());
				break;
			case "IS00009":
				// 個人名グループ．個人旧氏名．氏名カナ
				authClassItem.getItems().add(person.getPersonNameGroup().getOldName().getFullNameKana().v());
				break;
			case "IS00010":
				// 個人名グループ．個人届出名称．氏名
				authClassItem.getItems().add(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00011":
				// 個人名グループ．個人届出名称．氏名カナ
				authClassItem.getItems()
						.add(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00012":
				// 個人名グループ．個人届出名称．氏名
				authClassItem.getItems().add(person.getPersonNameGroup().getTodokedeFullName().getFullName().v());
				break;
			case "IS00013":
				// 個人名グループ．個人届出名称．氏名カナ
				authClassItem.getItems()
						.add(person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v());
				break;
			case "IS00014":
				// 性別
				authClassItem.getItems().add(person.getGender().value);
				break;
			case "IS00015":
				// 個人携帯
				authClassItem.getItems().add(person.getPersonMobile().toString());
				break;
			case "IS00016":
				// 個人メールアドレス
				authClassItem.getItems().add(person.getMailAddress().toString());
				break;
			case "IS00017":
				// 趣味
				authClassItem.getItems().add(person.getHobBy().toString());
				break;
			case "IS00018":
				// 嗜好
				authClassItem.getItems().add(person.getTaste().toString());
				break;
			case "IS00019":
				// 国籍
				authClassItem.getItems().add(person.getCountryId().toString());
				break;
			}
		}
	}
	public static void matchInformation(LayoutPersonInfoClsDto authClassItem, Family family) {
		for(PerInfoItemDefDto infoItem: authClassItem.getListItemDf()){
			switch(infoItem.getItemCode()){
			case "IS00040":
				//氏名
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getFullName().v()));
				break;
			case "IS00041":
				//氏名カナ
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getFullNameKana().v()));
				break;
			case "IS00042":
				//氏名ローマ字
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getNameRomajiFull().v()));
				break;
			case "IS00043":
				//氏名ローマ字カナ
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getNameRomajiFullKana().v()));
				break;
			case "IS00044":
				//氏名他言語
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getNameMultiLangFull().v()));
				break;
			case "IS00045":
				//氏名他言語カナ
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getNameMultiLangFullKana().v()));
				break;
			case "IS00046":
				//届出氏名
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getTokodekeName().v()));
				break;
			case "IS00047":
				//生年月日
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getBirthday()));
				break;
			case "IS00048":
				//死亡年月日
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getDeadDay()));
				break;
			case "IS00049":
				//入籍年月日
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getEntryDate()));
				break;				
			case "IS00050":
				//除籍年月日
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getExpelledDate()));
				break;
			case "IS00051":
				//国籍
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getNationalityId().v()));
				break;
			case "IS00052":
				//職業
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getOccupationName().v()));
				break;
			case "IS00053":
				//続柄
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getRelationship().v()));
				break;
			case "IS00054":
				//同居別居区分
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getTogSepDivisionType().value));
				break;
			case "IS00055":
				//支援介護区分
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getSupportCareType().value));
				break;
			case "IS00056":
				//勤労学生
				authClassItem.getItems().add(SaveDataDto.createDataDto(family.getWorkStudentType().value));
				break;
			}
		}
	}
//vinhpx: end
	
	public static void matchInformation(EmpPersonInfoClassDto authClassItem, Employee employee) {
		for (EmpPersonInfoItemDto dataInfoItem : authClassItem.getInfoItems()) {
			switch (dataInfoItem.getItemCode()) {
			case "IS00020":
				// 社員．社員コード
				authClassItem.getDataItems().add(employee.getSCd().v());
				break;
			case "IS00021":
				// 社員．入社年月日
				authClassItem.getDataItems().add(employee.getJoinDate());
				break;
			case "IS00022":
				// 社員．本採用年月日
				// dataInfoItem.setData(employee);
				// QA
				break;
			case "IS00024":
				// 社員．会社メールアドレス
				authClassItem.getDataItems().add(employee.getCompanyMail().v());
				break;
			case "IS00025":
				// 社員．会社携帯メールアドレス
				authClassItem.getDataItems().add(employee.getMobileMail().v());
				break;
			case "IS00026":
				// 社員．会社携帯電話番号
				authClassItem.getDataItems().add(employee.getCompanyMobile().v());
				break;
			case "IS00027":
				// 社員．採用区分
				// dataInfoItem.setData(employee);
				// QA
				break;
			case "IS00028":
				// 社員．退職年月日
				authClassItem.getDataItems().add(employee.getRetirementDate());
				break;
			}
		}

	}

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, TemporaryAbsence leaveHoliday) {
	}

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, JobTitleMain jobTitleMain) {
	}

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, AssignedWorkplace assignedWorkplace) {
	}

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, AffiliationDepartment affDepartment) {
	}

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, SubJobPosition subJobPosition) {
	}

	public static void matchPersEmerConts(EmpPersonInfoClassDto authClassItem,
			List<PersonEmergencyContact> perEmerConts) {
	}

	public static void matchFamilies(EmpPersonInfoClassDto authClassItem, List<Family> families) {
	}

	public static void matchsubJobPoses(EmpPersonInfoClassDto authClassItem, List<SubJobPosition> subJobPoses) {
	}

}
