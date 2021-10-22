package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApproveResultExport {
	/**
	 * 承認フェーズ枠番
	 */
	private Integer approvalPhaseNumber;
	
	/**
	 * 承認ルートインスタンス
	 */
	private ApprovalRootStateExport approvalRootState;
}
