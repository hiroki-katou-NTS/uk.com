package nts.uk.ctx.at.request.dom.applicationapproval.application.common.adapter.workflow.dto;

import lombok.Getter;

/**
 * 承認者IDリスト
 * 
 * @author vunv
 *
 */
@Getter
public class ApproverInfoImport {
	
	/**職位ID*/
	private String jobId;
	/**
	 * 社員ID
	 */
	private String sid;
	/** 承認フェーズID */
	private String approvalPhaseId;
	/** 確定者 */
	private boolean isConfirmPerson;
	/** 順序 */
	private int orderNumber;

	private String name;
	/**確定者*/
	private int approvalAtr;

	public ApproverInfoImport(String jobId,String sid, String approvalPhaseId, boolean isConfirmPerson, int orderNumber,int approvalAtr) {
		super();
		this.jobId = jobId;
		this.sid = sid;
		this.approvalPhaseId = approvalPhaseId;
		this.isConfirmPerson = isConfirmPerson;
		this.orderNumber = orderNumber;
		this.approvalAtr = approvalAtr;
	}

	public void addEmployeeName(String name) {
		this.name = name;
	}
}
