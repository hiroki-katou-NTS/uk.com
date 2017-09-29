package nts.uk.ctx.workflow.pub.approvalroot.export;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 承認者IDリスト
 * 
 * @author vunv
 *
 */
@Getter
public class ApproverInfoExport {
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

	public ApproverInfoExport(String sid, String approvalPhaseId, boolean isConfirmPerson, int orderNumber) {
		super();
		this.sid = sid;
		this.approvalPhaseId = approvalPhaseId;
		this.isConfirmPerson = isConfirmPerson;
		this.orderNumber = orderNumber;
	}

	public void addEmployeeName(String name) {
		this.name = name;
	}
}
