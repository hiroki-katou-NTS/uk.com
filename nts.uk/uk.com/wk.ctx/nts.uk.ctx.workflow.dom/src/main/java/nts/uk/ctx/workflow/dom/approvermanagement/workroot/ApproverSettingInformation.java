package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 承認者設定情報
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApproverSettingInformation {
	
	/** 承認フェーズ */
	private ApprovalPhase approvalPhase;
	
	/** 承認ルート */
	private PersonApprovalRoot personApprovalRoot;
	
}
