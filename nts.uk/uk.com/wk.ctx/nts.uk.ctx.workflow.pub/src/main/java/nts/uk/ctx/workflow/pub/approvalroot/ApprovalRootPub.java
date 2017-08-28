package nts.uk.ctx.workflow.pub.approvalroot;

import java.util.Date;

public interface ApprovalRootPub {
	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 */
	void getApprovalRootOfSubjectRequest(
			String cid, 
			String sid, 
			Date standardDate,
			String subjectRequest);
}
