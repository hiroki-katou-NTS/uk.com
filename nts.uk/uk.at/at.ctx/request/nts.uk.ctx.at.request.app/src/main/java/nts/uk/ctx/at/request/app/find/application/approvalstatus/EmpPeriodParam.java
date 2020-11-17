package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmpPeriodParam {
	private String wkpID;
	
	private String empID;
	
	private String empCD;
	
	private String companyInDate;
	
	private String companyOutDate;
	
	private String employmentStartDate;
	
	private String employmentEndDate;
	
	private String empMail;
	
	public EmpPeriod toDomain() {
		return new EmpPeriod(
				wkpID, 
				empID, 
				empCD, 
				GeneralDate.fromString(companyInDate, "yyyy/MM/dd"), 
				GeneralDate.fromString(companyOutDate, "yyyy/MM/dd"), 
				employmentStartDate == null ? null : GeneralDate.fromString(employmentStartDate, "yyyy/MM/dd"), 
				employmentEndDate == null ? null : GeneralDate.fromString(employmentEndDate, "yyyy/MM/dd"),
				empMail);
	}
}
