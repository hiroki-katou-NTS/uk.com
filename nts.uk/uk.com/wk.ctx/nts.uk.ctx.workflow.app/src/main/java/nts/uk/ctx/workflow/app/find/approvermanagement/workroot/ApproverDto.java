package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
@Value
public class ApproverDto {
	/**承認者ID*/
	private String approverId;
	/**職位ID*/
	private String jobTitleId;
	/**社員ID*/
	private String employeeId;
	/**Name*/
	private String employeeName;
	/**順序*/
	private int orderNumber;
	/**区分*/
	private int approvalAtr;
	/**確定者*/
	private int confirmPerson;
	public static ApproverDto fromDomain(Approver domain,String employeeName ){
		return new ApproverDto(domain.getApproverId(),
					domain.getJobTitleId(),
					domain.getEmployeeId(),
					employeeName,
					domain.getOrderNumber(),
					domain.getApprovalAtr().value,
					domain.getConfirmPerson().value);
	}
}
