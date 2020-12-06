package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmpPeriodDto {
	private String wkpID;
	
	private String empID;
	
	private String empCD;
	
	private String workplaceStartDate;
	
	private String workplaceEndDate;
	
	private String companyInDate;
	
	private String companyOutDate;
	
	private String employmentStartDate;
	
	private String employmentEndDate;
	
	public static EmpPeriodDto fromDomain(EmpPeriod empPeriod) {
		return new EmpPeriodDto(
				empPeriod.getWkpID(), 
				empPeriod.getEmpID(), 
				empPeriod.getEmpCD(), 
				empPeriod.getWorkplaceStartDate().toString(),
				empPeriod.getWorkplaceEndDate().toString(),
				empPeriod.getCompanyInDate().toString(), 
				empPeriod.getCompanyOutDate().toString(), 
				empPeriod.getEmploymentStartDate() == null ? null : empPeriod.getEmploymentStartDate().toString(), 
				empPeriod.getEmploymentEndDate() == null ? null : empPeriod.getEmploymentEndDate().toString());
	}
}
