package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;

@AllArgsConstructor
public class ApproverRegisterSetDto {
	/** 会社単位  */
	public int companyUnit;
	/** 職場単位  */
	public int workplaceUnit;
	/** 社員単位  */
	public int employeeUnit;
	
	public static ApproverRegisterSetDto fromDomain(ApproverRegisterSet approver) {
		
		return new ApproverRegisterSetDto(approver.getCompanyUnit().value,
				approver.getWorkplaceUnit().value,
				approver.getEmployeeUnit().value);
	}
}
