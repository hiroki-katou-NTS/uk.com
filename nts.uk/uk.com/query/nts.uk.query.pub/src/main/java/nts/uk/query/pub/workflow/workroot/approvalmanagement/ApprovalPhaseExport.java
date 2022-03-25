package nts.uk.query.pub.workflow.workroot.approvalmanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalPhaseExport {

	private List<ApproverExport> approver;
	/** 承認ID */
	private String approvalId;
	/** 承認フェーズ順序 */
	private int phaseOrder;
	/** 承認形態 */
	private Integer approvalForm;
	/** 閲覧フェーズ */
	private Integer browsingPhase;
	/** 承認者指定区分 */
	private int approvalAtr;
}
