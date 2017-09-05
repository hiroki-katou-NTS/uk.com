package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.Date;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApprovalPhaseOutput;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApprovalRootOutput;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ErrorFlag;

/**
 * 
 * @author vunv
 *
 */
public interface ApprovalRootService {

	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 */
	List<ApprovalRootOutput> getApprovalRootOfSubjectRequest(
			String cid, 
			String sid, 
			int employmentRootAtr, 
			int appType, 
			Date standardDate);
	
	/**
	 * 2.承認ルートを整理する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param baseDate 基準日
	 * @param appPhases 承認フーズ
	 */
	List<ApprovalRootOutput> adjustmentData(String cid, String sid, GeneralDate baseDate,  List<ApprovalRootOutput> appDatas);
	
	/**
	 * 7.承認ルートの異常チェック
	 * 
	 * @param beforeDatas
	 * @param afterDatas
	 * @return
	 */
	ErrorFlag checkError(List<ApprovalPhaseImport> beforeDatas, List<ApprovalPhaseOutput> afterDatas);
}
