package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalRootOutput;

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
			GeneralDate standardDate,
			int sysAtr);
	
	/**
	 * 2.承認ルートを整理する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param baseDate 基準日
	 * @param appPhases 承認フーズ
	 */
	List<ApprovalRootOutput> adjustmentData(String cid, String sid, GeneralDate baseDate,  List<ApprovalRootOutput> appDatas);
}
