package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.ConcurrentEmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;

/**
 * 承認者IDリスト
 * 
 * @author vunv
 *
 */
@Getter
@AllArgsConstructor
public class ApproverInfo {
	/**職位ID*/
	private String jobId;
	/**
	 * 社員ID
	 */
	private String sid;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**確定者*/
	private Boolean isConfirmPerson;
	/**順序*/
	private Integer orderNumber;
	
	private String name;
	/**区分*/
	private ApprovalAtr approvalAtr;
	
	public static ApproverInfo create(Approver x, String employeeName) {
		return new ApproverInfo(x.getJobTitleId(),
				x.getEmployeeId(), 
				x.getApprovalPhaseId(), 
				true, 
				x.getOrderNumber(),
				employeeName,
				x.getApprovalAtr());
	}
	
	public static ApproverInfo create(ConcurrentEmployeeImport emp) {
		return new ApproverInfo(
				emp.getJobId(),
				emp.getEmployeeId(),
				null,
				null,
				null,
				emp.getPersonName(),
				ApprovalAtr.JOB_TITLE);
	}
}
