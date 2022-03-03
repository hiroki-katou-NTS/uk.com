package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;

@AllArgsConstructor
@Getter
public class ApproveAppOutput {
	/**
	 * 承認完了フラグ
	 */
	private boolean approvalCompleteFlg;
	/**
	 * 排他エラー
	 */
	private boolean exclusiveError;
	/**
	 * 申請削除済エラー
	 */
	private boolean appDeleteError;
	/**
	 * Optional<承認フェーズ枠番>
	 */
	private Optional<Integer> opApprovalPhaseNumber;
	/**
	 * Optional<承認ルートインスタンス>
	 */
	private Optional<ApprovalRootStateImport_New> opApprovalRootState;
}
