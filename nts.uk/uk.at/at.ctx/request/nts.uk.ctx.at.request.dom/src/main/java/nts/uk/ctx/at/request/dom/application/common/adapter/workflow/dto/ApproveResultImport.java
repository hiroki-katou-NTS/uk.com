package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApproveResultImport {
	/**
	 * 承認フェーズ枠番
	 */
	private Integer approvalPhaseNumber;
	
	/**
	 * 承認ルートインスタンス
	 */
	private ApprovalRootStateImport_New approvalRootState;
}
