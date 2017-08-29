package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author vunv
 *
 */
public interface AdjustmentApprovalRootService {

	/**
	 * 2.承認ルートを整理する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 * @param branchIds
	 */
	void getApprovalRootOfSubjectRequest(
			String cid, 
			String sid, 
			Date standardDate,
			List<String> branchIds);
}
