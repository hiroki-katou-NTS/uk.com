package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.info.FullNameSetDto;
import nts.uk.ctx.bs.employee.app.find.person.item.CtgItemType;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemAffiWorkplace;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemAssignedWorkplace;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurAffDept;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurrentAddressDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurrentJobPosDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemEmergencyContact;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemEmployee;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemFamilyCareDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemFamilyDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemFamilySocialInsuranceDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemIncomeTaxDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemJobPosMain;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemPersonDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemTemporaryAbsence;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemWidowHistory;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemSetCurJobPos;

/**
 * category item fix dto
 * @author xuan vinh
 *
 */

@NoArgsConstructor
public class CtgItemFixDto {
	@Getter
	protected CtgItemType ctgItemType; 
	
	public static CtgItemFixDto createIncomeTax(String incomeTaxID, String familyMemberId, String sid, GeneralDate startDate, GeneralDate endDate, 
			boolean supporter, int disabilityType, int deductionTargetType){
		return ItemIncomeTaxDto.createFromJavaType(incomeTaxID, familyMemberId, sid, startDate, endDate, 
				supporter, disabilityType, deductionTargetType);
	}
	public static CtgItemFixDto createFamilySocialInsurance(String familyMemberId, String sid, String socailInsuaranceId, GeneralDate startDate,
			GeneralDate endDate, boolean nursingCare, boolean healthInsuranceDependent, boolean nationalPensionNo3, String basicPensionNumber){
		return ItemFamilySocialInsuranceDto.createFromJavaType(familyMemberId, sid, socailInsuaranceId, startDate,
				endDate, nursingCare, healthInsuranceDependent, nationalPensionNo3, basicPensionNumber);
	}
	public static CtgItemFixDto createFamilyCare(String familyCareId, String familyId, String sid, 
			GeneralDate startDate, GeneralDate endDate, int careClassifi){
		return ItemFamilyCareDto.createFromJavaType(familyCareId, familyId, sid, startDate, endDate, careClassifi);
	}
	public static CtgItemFixDto createSetCurJobPos(List<ItemCurrentJobPosDto> lstCurrentJobPosDto){
		return ItemSetCurJobPos.createFromJavaType(lstCurrentJobPosDto);
	}
	public static CtgItemFixDto createEmployee(String personId, String employeeId, String employeeCode, String employeeMail,
			GeneralDate retirementDate, GeneralDate joinDate){
		return ItemEmployee.createFromJavaType(personId, employeeId, employeeCode, employeeMail, retirementDate, joinDate);
	}
	
	public static CtgItemFixDto createLeaveHoliday(String employeeId, String tempAbsenceId, int tempAbsenceType, GeneralDate strD, GeneralDate endD, 
			String tempAbsenceReason, String familyMemberId, GeneralDate birthDate, int mulPregnancySegment){
		return ItemTemporaryAbsence.createFromJavaType(employeeId, tempAbsenceId, tempAbsenceType, 
				strD, endD, tempAbsenceReason, familyMemberId, birthDate, mulPregnancySegment);
	}
	
	public static CtgItemFixDto createJobTitleMain(String sId, String hisId, String jobTitleId, GeneralDate strD, GeneralDate endD){
		return ItemJobPosMain.createFromJavaType(sId, hisId, jobTitleId, strD, endD);
	}
	
	public static CtgItemFixDto createAssignedWorkplace(String employeeId, String assignedWorkplaceId, List<DateHistoryItem> dateHistoryItem){
		return ItemAssignedWorkplace.createFromJavaType(employeeId, assignedWorkplaceId, dateHistoryItem);
	}
	
	public static CtgItemFixDto createAffiDepartment(String id, String employeeId, String departmentId, GeneralDate strD, GeneralDate endD){
		return ItemAffiWorkplace.createFromJavaType(id, employeeId, departmentId, strD, endD);
	}
	
	public static CtgItemFixDto createCurAffDept(String employeeId, String affiDeptId, String departmentId, List<DateHistoryItem> dateHistoryItem){
		return ItemCurAffDept.createCurAffDept(employeeId, affiDeptId, departmentId, dateHistoryItem);
	}
	
	public static CtgItemFixDto createPerson(String businessName, String personName, String businessOtherName, String businessEnglishName,
			String personNameKana, FullNameSetDto personRomanji, FullNameSetDto todokedeFullName, FullNameSetDto oldName, FullNameSetDto todokedeOldFullName){
		return ItemPersonDto.createFromJavaType(businessName, personName, businessOtherName, businessEnglishName, 
				personNameKana, personRomanji, todokedeFullName, oldName, todokedeOldFullName);
	}
	
	public static CtgItemFixDto createCurrentAddress(String currentAddressId, String pid, String countryId,
			String postalCode, String phoneNumber, String prefectures, String houseRent, GeneralDate StartDate,
			GeneralDate endDate, String address1, String addresskana1, String address2, String addresskana2,
			String homeSituationType, String personMailAddress, String houseType, String nearestStation){
		return ItemCurrentAddressDto.createFromJavaType(currentAddressId, pid, countryId, postalCode, phoneNumber, prefectures, houseRent, 
				StartDate, endDate, address1, addresskana1, address2, addresskana2, 
				homeSituationType, personMailAddress, houseType, nearestStation);
	}
	
	public static CtgItemFixDto createWidowHistory(String widdowHistoryId, GeneralDate startDate, GeneralDate endDate, int windowType){
		return ItemWidowHistory.createFromJavaType(widdowHistoryId, startDate, endDate, windowType);
	}
	
	public static CtgItemFixDto createFamily(GeneralDate birthday, GeneralDate deadDay, GeneralDate entryDate, GeneralDate expelledDate,
			String familyId, String name, String nameKana, String nameMulti, String nameMultiKana, String nameRomaji,
			String nameRomajiKana, String nationality, String occupationName, String personId, String relationship,
			int supportCareType, String notificationName, int togSepDivision, int workStudent){
		return ItemFamilyDto.createFromJavaType(birthday, deadDay, entryDate, expelledDate, familyId,
				name, nameKana, nameMulti, nameMultiKana, nameRomaji, nameRomajiKana, nationality, occupationName, 
				personId, relationship, supportCareType, notificationName, togSepDivision, workStudent);
	}
	
	public static CtgItemFixDto createEmergencyContact(String emgencyContactId, String pid, String personName,
			String personMailAddress, String streetAddressPerson, String phone, 
			int priorityEmegencyContact, String relationShip){
		return ItemEmergencyContact.createFromJavaType(emgencyContactId, pid, personName, personMailAddress, 
				streetAddressPerson, phone, priorityEmegencyContact, relationShip);
	}
}
