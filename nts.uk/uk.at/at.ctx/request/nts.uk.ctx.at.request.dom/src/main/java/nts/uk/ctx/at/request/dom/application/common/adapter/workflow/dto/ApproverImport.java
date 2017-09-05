package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認者
 * @author vunv
 *
 */
@Getter
@AllArgsConstructor
public class ApproverImport {
	/**会社ID*/
	private String companyId;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**承認者ID*/
	private String approverId;
	/**職位ID*/
	private String jobTitleId;
	/**社員ID*/
	private String employeeId;
	/**順序*/
	private int orderNumber;
	/**区分*/
	private int approvalAtr;
	/**確定者*/
	private int confirmPerson;
}
