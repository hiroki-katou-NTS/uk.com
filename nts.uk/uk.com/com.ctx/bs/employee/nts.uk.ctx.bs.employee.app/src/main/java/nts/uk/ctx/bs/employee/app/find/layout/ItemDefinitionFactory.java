/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout;

import java.util.List;
import java.util.Map;

import find.layout.classification.ActionRole;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMember;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;

/**
 * @author danpv
 * 
 *         Class lưu trữ cấu trúc đề mapping ItemDefinition và các Object Domain
 *         dành cho việc chuyển data từ server đến client File tham khảo
 *         \\192.168.50.4\share\500_新構想開発\03_概要設計\01_概要設計書\04_共通\CPS_個人情報\個人情報定義\項目定義-説明書.xlsx
 *
 */
public class ItemDefinitionFactory {

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			CurrentAddress currentAddress, ActionRole actionRole) {
		for (PerInfoItemDefDto itemDef : authClassItem.getListItemDf()) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "IS00029":
				/*
				 * 現住所．期間 現住所．期間．開始日 現住所．期間．終了日
				 */
				// dataInfoItem.setData(currentAddress.getPeriod());
				break;
			case "IS00030":
				// 現住所．郵便番号
				data = currentAddress.getPostalCode().v();
				break;

			case "IS00031":
				// 現住所．都道府県
				data = currentAddress.getPrefectures().v();
				break;
			case "IS00032":
				// 現住所．国
				data = currentAddress.getCountryId();
				break;
			case "IS00033":
				// 現住所．住所１
				data = currentAddress.getAddress1().getAddress1().v();
				break;
			case "IS00034":
				// 現住所．住所カナ１
				data = currentAddress.getAddress1().getAddressKana1().v();
				break;
			case "IS00035":
				// 現住所．住所2
				data = currentAddress.getAddress2().getAddress2().v();
				break;
			case "IS00036":
				// 現住所．住所カナ2
				data = currentAddress.getAddress2().getAddressKana2().v();
				break;
			case "IS00037":
				// 現住所．電話番号
				data = currentAddress.getPhoneNumber().v();
				break;
			case "IS00038":
				// 現住所．住宅状況種別
				data = currentAddress.getHomeSituationType().v();
				break;
			case "IS00039":
				// 現住所．社宅家賃
				data = currentAddress.getHouseRent().v();
				break;
			}
			if (data != null) {
				authClassItem.getItems()
						.add(LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data, actionRole));
			}
		}
	}

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			List<CurrentAddress> listCurrentAddress, ActionRole actionRole) {
		for (int i = 0; i < listCurrentAddress.size(); i++) {
			for (PerInfoItemDefDto itemDef : authClassItem.getListItemDf()) {
				Object data = null;
				switch (itemDef.getItemCode()) {
				case "IS00029":
					/*
					 * 現住所．期間 現住所．期間．開始日 現住所．期間．終了日
					 */
					// dataInfoItem.setData(listCurrentAddress.getPeriod());
					break;
				case "IS00030":
					// 現住所．郵便番号
					data = listCurrentAddress.get(i).getPostalCode().v();
					break;

				case "IS00031":
					// 現住所．都道府県
					data = listCurrentAddress.get(i).getPrefectures().v();
					break;
				case "IS00032":
					// 現住所．国
					data = listCurrentAddress.get(i).getCountryId();
					break;
				case "IS00033":
					// 現住所．住所１
					data = listCurrentAddress.get(i).getAddress1().getAddress1().v();
					break;
				case "IS00034":
					// 現住所．住所カナ１
					data = listCurrentAddress.get(i).getAddress1().getAddressKana1().v();
					break;
				case "IS00035":
					// 現住所．住所2
					data = listCurrentAddress.get(i).getAddress2().getAddress2().v();
					break;
				case "IS00036":
					// 現住所．住所カナ2
					data = listCurrentAddress.get(i).getAddress2().getAddressKana2().v();
					break;
				case "IS00037":
					// 現住所．電話番号
					data = listCurrentAddress.get(i).getPhoneNumber().v();
					break;
				case "IS00038":
					// 現住所．住宅状況種別
					data = listCurrentAddress.get(i).getHomeSituationType().v();
					break;
				case "IS00039":
					// 現住所．社宅家賃
					data = listCurrentAddress.get(i).getHouseRent().v();
					break;
				}
				if (data != null) {
					authClassItem.getItems()
							.add(LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data, actionRole));
				}
			}
		}
	}

	// vinhpx: start
	// person
	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem, Person person,
			ActionRole actionRole) {
		for (PerInfoItemDefDto itemDef : authClassItem.getListItemDf()) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "IS00001":
				// 個人名グループ．個人名
				data = person.getPersonNameGroup().getPersonName().v();
				break;
			case "IS00002":
				// 個人名グループ．個人名カナ
				data = person.getPersonNameGroup().getPersonNameKana().v();
				break;
			case "IS00003":
				// 個人名グループ．個人名ローマ字．氏名
				data = person.getPersonNameGroup().getPersonRomanji().getFullName().v();
				break;
			case "IS00004":
				// 個人名グループ．個人名ローマ字．氏名カナ
				data = person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v();
				break;
			case "IS00005":
				// 個人名グループ．ビジネスネーム
				data = person.getPersonNameGroup().getBusinessName().v();
				break;
			case "IS00006":
				// 個人名グループ．ビジネスネーム．英語
				data = person.getPersonNameGroup().getBusinessEnglishName().v();
				break;
			case "IS00007":
				// 個人名グループ．ビジネスネーム．その他
				data = person.getPersonNameGroup().getBusinessOtherName().v();
				break;
			case "IS00008":
				// 個人名グループ．個人旧氏名．氏名
				data = person.getPersonNameGroup().getOldName().getFullName().v();
				break;
			case "IS00009":
				// 個人名グループ．個人旧氏名．氏名カナ
				data = person.getPersonNameGroup().getOldName().getFullNameKana().v();
				break;
			case "IS00010":
				// 個人名グループ．個人届出名称．氏名
				data = person.getPersonNameGroup().getTodokedeFullName().getFullName().v();
				break;
			case "IS00011":
				// 個人名グループ．個人届出名称．氏名カナ
				data = person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
				break;
			case "IS00012":
				// 個人名グループ．個人届出名称．氏名
				data = person.getPersonNameGroup().getTodokedeFullName().getFullName().v();
				break;
			case "IS00013":
				// 個人名グループ．個人届出名称．氏名カナ
				data = person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
				break;
			case "IS00014":
				// 性別
				data = person.getGender().value;
				break;
			}
			if (data != null) {
				authClassItem.getItems()
						.add(LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data, actionRole));
			}
		}
	}

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem, FamilyMember family,
			ActionRole actionRole) {
		for (PerInfoItemDefDto itemDef : authClassItem.getListItemDf()) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "IS00040":
				// 氏名
				data = family.getFullName().v();
				break;
			case "IS00041":
				// 氏名カナ
				data = family.getFullNameKana().v();
				break;
			case "IS00042":
				// 氏名ローマ字
				data = family.getNameRomajiFull().v();
				break;
			case "IS00043":
				// 氏名ローマ字カナ
				data = family.getNameRomajiFullKana().v();
				break;
			case "IS00044":
				// 氏名他言語
				data = family.getNameMultiLangFull().v();
				break;
			case "IS00045":
				// 氏名他言語カナ
				data = family.getNameMultiLangFullKana().v();
				break;
			case "IS00046":
				// 届出氏名
				data = family.getTokodekeName().v();
				break;
			case "IS00047":
				// 生年月日
				data = family.getBirthday();
				break;
			case "IS00048":
				// 死亡年月日
				data = family.getDeadDay();
				break;
			case "IS00049":
				// 入籍年月日
				data = family.getEntryDate();
				break;
			case "IS00050":
				// 除籍年月日
				data = family.getExpelledDate();
				break;
			case "IS00051":
				// 国籍
				data = family.getNationalityId().v();
				break;
			case "IS00052":
				// 職業
				data = family.getOccupationName().v();
				break;
			case "IS00053":
				// 続柄
				data = family.getRelationship().v();
				break;
			case "IS00054":
				// 同居別居区分
				data = family.getTogSepDivisionType().value;
				break;
			case "IS00055":
				// 支援介護区分
				data = family.getSupportCareType().value;
				break;
			case "IS00056":
				// 勤労学生
				data = family.getWorkStudentType().value;
				break;
			}
			if (data != null) {
				authClassItem.getItems()
						.add(LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data, actionRole));
			}
		}
	}
	// vinhpx: end

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem, Employee employee,
			ActionRole actionRole) {
		for (PerInfoItemDefDto itemDef : authClassItem.getListItemDf()) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "IS00020":
				// 社員．社員コード
				data = employee.getSCd().v();
				break;
			case "IS00021":
				// 社員．入社年月日
				data = employee.getJoinDate();
				break;
			case "IS00022":
				// 社員．本採用年月日
				// dataInfoItem.setData(employee);
				// QA
				break;
			case "IS00024":
				// 社員．会社メールアドレス
				data = employee.getCompanyMail().v();
				break;
			case "IS00025":
				// 社員．会社携帯メールアドレス
				data = employee.getMobileMail().v();
				break;
			case "IS00026":
				// 社員．会社携帯電話番号
				data = employee.getCompanyMobile().v();
				break;
			case "IS00027":
				// 社員．採用区分
				// dataInfoItem.setData(employee);
				// QA
				break;
			case "IS00028":
				// 社員．退職年月日
				data = employee.getRetirementDate();
				break;
			}
			if (data != null) {
				authClassItem.getItems()
						.add(LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data, actionRole));
			}
		}

	}


	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			TempAbsenceHisItem leaveHoliday, ActionRole actionRole) {
	}

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			JobTitleMain jobTitleMain, ActionRole actionRole) {
	}

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			AssignedWorkplace assignedWorkplace, ActionRole actionRole) {
	}

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			AffiliationDepartment affDepartment, ActionRole actionRole) {
	}

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			SubJobPosition subJobPosition, ActionRole actionRole) {
	}

	public static void matchInformation(String categoryCode, LayoutPersonInfoClsDto authClassItem,
			WidowHistory widowHistory, ActionRole actionRole) {
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchCurrentAddress(
			List<LayoutPersonInfoClsDto> listClsDto, ActionRole actionRole, List<CurrentAddress> currentAddress) {

		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchWidowHistory(LayoutPersonInfoClsDto personInfoClsDto,
			List<WidowHistory> lstWidowHistory) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchPersEmerConts(LayoutPersonInfoClsDto authClassItem,
			List<PersonEmergencyContact> perEmerConts) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchFamilies(LayoutPersonInfoClsDto authClassItem,
			List<FamilyMember> families) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchFamily(LayoutPersonInfoClsDto personInfoClsDto,
			ActionRole actionRole, List<FamilyMember> families) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchWidowHistory(LayoutPersonInfoClsDto personInfoClsDto,
			ActionRole actionRole, List<WidowHistory> widowHistory) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchPersonEmergencyContact(
			LayoutPersonInfoClsDto personInfoClsDto, ActionRole actionRole,
			List<PersonEmergencyContact> personEmergencyContact) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchsubJobPoses(LayoutPersonInfoClsDto authClassItem,
			List<SubJobPosition> subJobPoses) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchTemporaryAbsence(
			LayoutPersonInfoClsDto personInfoClsDto, List<TempAbsenceHistory> temporaryAbsence) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchTemporaryAbsence(
			LayoutPersonInfoClsDto personInfoClsDto, ActionRole actionRole, List<TempAbsenceHistory> temporaryAbsence) {
		return null;
	}

	public static Map<String, List<LayoutPersonInfoValueDto>> matchJobTitleMain(LayoutPersonInfoClsDto personInfoClsDto,
			ActionRole actionRole, List<JobTitleMain> jobTitleMain) {
		return null;
	}
}
