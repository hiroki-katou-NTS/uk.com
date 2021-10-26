package nts.uk.ctx.workflow.dom.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;

@AllArgsConstructor
@Getter
public class ApproveResultOuput {
	/**
	 * 承認フェーズ枠番
	 */
	private Integer approvalPhaseNumber;
	
	/**
	 * 承認ルートインスタンス
	 */
	private ApprovalRootState approvalRootState;
}
