package nts.uk.ctx.workflow.pub.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApproverInfoExport;

public interface ApprovalRootPub {
	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * 
	 * @param cid
	 *            会社ID
	 * @param sid
	 *            社員ID（申請本人の社員ID）
	 * @param employmentRootAtr
	 *            就業ルート区分
	 * @param subjectRequest
	 *            対象申請
	 * @param standardDate
	 *            基準日
	 */
	List<ApprovalRootExport> getApprovalRootOfSubjectRequest(String cid, String sid, int employmentRootAtr, int appType,
			GeneralDate standardDate);

	/**
	 * 3.職位から承認者へ変換する
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param jobTitleId
	 */
	List<ApproverInfoExport> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId);
	
	Integer getCurrentApprovePhase(String rootStateID, Integer rootType);
}
