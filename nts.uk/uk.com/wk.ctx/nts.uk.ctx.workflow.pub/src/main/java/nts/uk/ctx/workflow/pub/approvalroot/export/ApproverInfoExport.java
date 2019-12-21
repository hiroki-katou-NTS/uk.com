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
	/**社員ID*/
	private String sid;
	/** 承認フェーズ順序 */
	private int phaseOrder;
	/** 承認者順序 */
	private int approverOrder;
	/** 確定者 */
	private Boolean isConfirmPerson;
	private String name;
	/**確定者*/
	private Integer approvalAtr;

	public ApproverInfoExport(String jobId,String sid, int phaseOrder, int approverOrder, boolean isConfirmPerson, int approvalAtr) {
		super();
		this.jobId = jobId;
		this.sid = sid;
		this.phaseOrder = phaseOrder;
		this.isConfirmPerson = isConfirmPerson;
		this.approverOrder = approverOrder;
		this.approvalAtr = approvalAtr;
	}

	public void addEmployeeName(String name) {
		this.name = name;
	}
}
