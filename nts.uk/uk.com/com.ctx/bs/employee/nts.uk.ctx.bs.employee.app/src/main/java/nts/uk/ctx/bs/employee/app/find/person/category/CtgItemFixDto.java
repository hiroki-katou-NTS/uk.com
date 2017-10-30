package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.item.CtgItemType;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemAffiWorkplace;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemAssignedWorkplace;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurAffDept;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurrentJobPosDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemEmployee;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemFamilyCareDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemFamilySocialInsuranceDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemIncomeTaxDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemJobPosMain;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemTemporaryAbsence;
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
		return ItemTemporaryAbsence.createFromJavaType(employeeId, tempAbsenceId, tempAbsenceType, strD, endD, tempAbsenceReason, familyMemberId, birthDate, mulPregnancySegment);
	}
	
	public static CtgItemFixDto createJobPosMain(String sId, String jobPositionId, String jobTitleId, GeneralDate strD, GeneralDate endD){
		return ItemJobPosMain.createFromJavaType(sId, jobPositionId, jobTitleId, strD, endD);
	}
	
	public static CtgItemFixDto createAssignedWorkplace(String employeeId, String assignedWorkplaceId, String historyId, GeneralDate strD, GeneralDate endD){
		return ItemAssignedWorkplace.createFromJavaType(employeeId, assignedWorkplaceId, historyId, strD, endD);
	}
	
	public static CtgItemFixDto createAffiDepartment(String id, String employeeId, String departmentId, GeneralDate strD, GeneralDate endD){
		return ItemAffiWorkplace.createFromJavaType(id, employeeId, departmentId, strD, endD);
	}
	
	public static CtgItemFixDto createCurAffDept(String employeeId, String affiDeptId, String departmentId, GeneralDate strD, GeneralDate endD){
		return ItemCurAffDept.createCurAffDept(employeeId, affiDeptId, departmentId, strD, endD);
	}
}
