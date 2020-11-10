package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmpPeriod {
	
	private String wkpID;
	
	private String empID;
	
	private String empCD;
	
	private GeneralDate companyInDate;
	
	private GeneralDate companyOutDate;
	
	private GeneralDate employmentStartDate;
	
	private GeneralDate employmentEndDate;
	
	private String empMail;
	
	private String empName;
}
