package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
	/**確定者*/
	private int approvalAtr;
}
