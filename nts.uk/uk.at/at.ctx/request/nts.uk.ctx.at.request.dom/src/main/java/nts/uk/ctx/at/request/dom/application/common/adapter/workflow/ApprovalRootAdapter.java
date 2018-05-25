package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverInfoImport;


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
	 * 3.職位から承認者へ変換する
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param jobTitleId
	 */
	List<ApproverInfoImport> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId);
}
