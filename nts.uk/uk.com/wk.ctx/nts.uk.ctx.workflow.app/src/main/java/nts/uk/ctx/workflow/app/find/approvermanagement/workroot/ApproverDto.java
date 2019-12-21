package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
@Data
@AllArgsConstructor
public class ApproverDto {
	/**承認ID*/
	private String approvalId;
	/**職位ID*/
	private String jobTitleId;
	/**社員ID*/
	private String employeeId;
	/**Name*/
	private String name;
	/**順序*/
	private int approverOrder;
	/**区分*/
	private int approvalAtr;
	/**確定者*/
	private int confirmPerson;
	/**confirmPerson Name*/
	private String confirmName;
	
	public static ApproverDto fromDomain(Approver domain, String name , String confirmName){
		return new ApproverDto(domain.getApprovalId(),
					domain.getJobTitleId(),
					domain.getEmployeeId(),
					name,
					domain.getApproverOrder(),
					domain.getApprovalAtr().value,
					domain.getConfirmPerson().value,
					confirmName);
	}
}
