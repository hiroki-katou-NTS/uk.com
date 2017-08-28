package nts.uk.ctx.workflow.pub.approvalroot;

import java.util.Date;
import java.util.List;

public interface ApprovalRootPub {
	/**
	 * 1.社員の対象申請の承認ルートを取得する（共通以外）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<ApprovalRootDto> findByBaseDate(String cid, String sid, Date standardDate, String appType);
	
	/**
	 * 1.社員の対象申請の承認ルートを取得する(共通）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<ApprovalRootDto> findByBaseDateOfCommon(String cid, String sid, Date standardDate);
}
