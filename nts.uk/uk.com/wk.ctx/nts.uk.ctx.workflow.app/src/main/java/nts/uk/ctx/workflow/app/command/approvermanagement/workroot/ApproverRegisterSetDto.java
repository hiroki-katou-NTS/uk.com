package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
@Getter
@AllArgsConstructor
public class ApproverRegisterSetDto {
	/** 会社単位  */
	private int companyUnit;
	/** 職場単位  */
	private int workplaceUnit;
	/** 社員単位  */
	private int employeeUnit;
	
	public static ApproverRegisterSetDto fromDomain(ApproverRegisterSet approver) {
		
		return new ApproverRegisterSetDto(approver.getCompanyUnit().value,
				approver.getWorkplaceUnit().value,
				approver.getEmployeeUnit().value);
	}
}
