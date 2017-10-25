package nts.uk.ctx.workflow.pub.approvalroot.export;

import lombok.Getter;

/**
 * 承認者IDリスト
 * 
 * @author vunv
 *
 */
@Getter
public class ApproverInfoExport {
	
	/**職位ID*/
	private String jobId;
	/**
	 * 社員ID
	 */
	private String sid;
	/** 承認フェーズID */
	private String approvalPhaseId;
	/** 確定者 */
	private Boolean isConfirmPerson;
	/** 順序 */
	private Integer orderNumber;

	private String name;
	/**確定者*/
	private Integer approvalAtr;

	public ApproverInfoExport(String jobId,String sid, String approvalPhaseId, boolean isConfirmPerson, int orderNumber,int approvalAtr) {
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
