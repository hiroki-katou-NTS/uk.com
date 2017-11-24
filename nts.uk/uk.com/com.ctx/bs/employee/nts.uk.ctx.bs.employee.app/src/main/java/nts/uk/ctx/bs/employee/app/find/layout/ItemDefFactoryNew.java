package nts.uk.ctx.bs.employee.app.find.layout;

import java.util.ArrayList;
import java.util.List;

import find.layout.classification.ActionRole;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDept;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCare;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTax;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsurance;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMember;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;

/**
 * Item definition factory: (thay đổi so với cái hiện tại để tùy chỉnh theo chức năng)
 * @author xuan vinh
 *
 */

public class ItemDefFactoryNew {
	//Matching item Person
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, Person person) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
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
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	
	//Matching item: CurrentAddress
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, CurrentAddress currentAddress) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "IS00029":
				//期間
				data = currentAddress.getPeriod();
				break;
			case "IS00030":
				//郵便番号
				data = currentAddress.getPostalCode().v();
				break;
			case "IS00031":
				//都道府県
				data = currentAddress.getPrefectures().v();
				break;
			case "IS00032":
				//国
				data = currentAddress.getCountryId();
				break;
			case "IS00033":
				//住所１
				data = currentAddress.getAddress1().getAddress1().v();
				break;
			case "IS00034":
				//住所カナ１
				data = currentAddress.getAddress1().getAddressKana1().v();
				break;
			case "IS00035":
				//住所2
				data = currentAddress.getAddress2().getAddress2().v();
				break;
			case "IS00036":
				//住所カナ2
				data = currentAddress.getAddress2().getAddressKana2().v();
				break;
			case "IS00037":
				//電話番号
				data = currentAddress.getPhoneNumber().v();
				break;
			case "IS00038":
				//住宅状況種別
				data = currentAddress.getHomeSituationType().v();
				break;
			case "IS00039":
				//社宅家賃
				data = currentAddress.getHouseRent().v();
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//Family
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, FamilyMember family) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "IS00040":
				//氏名
				data = family.getFullName().v();
				break;
			case "IS00041":
				//氏名カナ
				data = family.getFullNameKana().v();
				break;
			case "IS00042":
				//氏名ローマ字
				data = family.getNameRomajiFull().v();
				break;
			case "IS00043":
				//氏名ローマ字カナ
				data = family.getNameRomajiFullKana().v();
				break;
			case "IS00044":
				//氏名他言語
				data = family.getNameMultiLangFull().v();
				break;
			case "IS00045":
				//氏名他言語カナ
				data = family.getNameMultiLangFullKana().v();
				break;
			case "IS00046":
				//届出氏名
				data = family.getTokodekeName().v();
				break;
			case "IS00047":
				//生年月日
				data = family.getBirthday();
				break;
			case "IS00048":
				//死亡年月日
				data = family.getDeadDay();
				break;
			case "IS00049":
				//入籍年月日
				data = family.getEntryDate();
				break;
			case "IS00050":
				//除籍年月日
				data = family.getExpelledDate();
				break;
			case "IS00051":
				//国籍
				data = family.getNationalityId().v();
				break;
			case "IS00052":
				//職業
				data = family.getOccupationName().v();
				break;
			case "IS00053":
				//続柄
				data = family.getRelationship().v();
				break;
			case "IS00054":
				//同居別居区分
				data = family.getTogSepDivisionType().value;
				break;
			case "IS00055":
				//支援介護区分
				data = family.getSupportCareType().value;
				break;
			case "IS00056":
				//勤労学生
				data = family.getWorkStudentType().value;
				break;
				
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//WidowHistory
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, WidowHistory widowHistory) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//PersonEmergencyContact
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, PersonEmergencyContact personEmergencyContact) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//Employee
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, Employee employee) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//TemporaryAbsence
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, TempAbsenceHisItem temporaryAbsence) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//JobTitleMain
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, JobTitleMain jobTitleMain) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//AssignedWorkplace
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, AssignedWorkplace assignedWorkplace) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//Affiliation Department
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, AffiliationDepartment affiDept) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	
	//CurrentAffiDept
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, CurrentAffiDept currentAffiDept) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//IncomeTax
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, IncomeTax incomeTax) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//FamilySocialInsurance
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, FamilySocialInsurance familySocialInsurance) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//FamilyCare
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, FamilyCare familyCare) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "":
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	//SubJobPosition
		public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, SubJobPosition subJobPosition) {
			LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
			layoutPerInfoClsDto.setListItemDf(listItemDef);
			layoutPerInfoClsDto.setItems(new ArrayList<>());
			//LayoutPersonInfoClsDto
			for (PerInfoItemDefDto itemDef : listItemDef) {
				Object data = null;
				switch (itemDef.getItemCode()) {
				case "":
					break;
				}
				if (data != null) {
					LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
					obj.setActionRole(actionRole);
					layoutPerInfoClsDto.getItems().add(obj);
				}				
			}
			return layoutPerInfoClsDto;
		}
}
