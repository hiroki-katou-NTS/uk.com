package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;

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
public class ApprovalSettingInformation {
	
	/** 承認フェーズ */
	private List<ApprovalPhase> approvalPhases;
	
	/** 承認ルート */
	private PersonApprovalRoot personApprovalRoot;
	
}
