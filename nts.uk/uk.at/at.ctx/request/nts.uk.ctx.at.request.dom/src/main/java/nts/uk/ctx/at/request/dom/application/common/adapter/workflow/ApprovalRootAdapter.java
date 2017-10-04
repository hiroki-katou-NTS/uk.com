package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;


/**
 * 1-2.承認ルートを取得する
 * @author vunv
 *
 */
public interface ApprovalRootAdapter {
	
	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * @param cid
	 * @param sid
	 * @param employmentRootAtr
	 * @param appType
	 * @param standardDate
	 * @return
	 */
	List<ApprovalRootImport> getApprovalRootOfSubjectRequest(String cid, String sid, int employmentRootAtr, int appType,
			GeneralDate standardDate);
	
	/**
	 * 2.承認ルートを整理する
	 * 
	 * @param cid
	 *            会社ID
	 * @param sid
	 *            社員ID（申請本人の社員ID）
	 * @param baseDate
	 *            基準日
	 * @param appPhases
	 *            承認フーズ
	 */
	List<ApprovalRootImport> adjustmentData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalRootImport> appDatas);

	/**
	 * 7.承認ルートの異常チェック
	 * 
	 * @param beforeDatas
	 * @param afterDatas
	 * @return
	 */
	ErrorFlagImport checkError(List<ApprovalPhaseImport> beforeDatas, List<ApprovalPhaseImport> afterDatas);
	
	/**
	 * 3.職位から承認者へ変換する
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param jobTitleId
	 */
	List<ApproverInfoImport> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId);
}
