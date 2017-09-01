package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApprovalPhaseOutput;

/**
 * 2.承認ルートを整理する
 * 
 * @author vunv
 */
public interface AdjustmentApprovalRootService {

	/**
	 * 2.承認ルートを整理する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param baseDate 基準日
	 * @param appPhases 承認フーズ
	 */
	List<ApprovalPhaseOutput> adjustmentApprovalRootData(
			String cid, 
			String sid, 
			GeneralDate baseDate,
			List<ApprovalPhaseImport> appPhases);
}
