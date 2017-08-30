package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.dto.ApprovalRootDataResult;

/**
 * 
 * @author vunv
 *
 */
public interface GetApprovalRootService {

	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 */
	List<ApprovalRootDataResult> getApprovalRootOfSubjectRequest(
			String cid, 
			String sid, 
			int employmentRootAtr, 
			int appType, 
			Date standardDate);
}
