package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;

public interface JobtitleToApproverService {
	/**
	 * 3.職位から承認者へ変換する
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param jobTitleId
	 */
	List<ApproverInfo> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId);
}
