/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout;

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
		for (EmpPersonInfoItemDto dataInfoItem : authClassItem.getDataInfoitems()) {
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
		for (EmpPersonInfoItemDto dataInfoItem : authClassItem.getDataInfoitems()) {
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

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, WidowHistory widowHistory) {
	}

	public static void matchInformation(EmpPersonInfoClassDto authClassItem, Employee employee) {
		for (EmpPersonInfoItemDto dataInfoItem : authClassItem.getDataInfoitems()) {
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

}
