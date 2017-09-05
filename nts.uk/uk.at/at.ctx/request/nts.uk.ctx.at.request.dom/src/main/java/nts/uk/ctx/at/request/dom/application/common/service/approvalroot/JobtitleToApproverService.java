package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApproverInfo;

public interface JobtitleToApproverService {
	/**
	 * 3.職位から承認者へ変換する
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param jobTitleId
	 */
	public List<ApproverInfo> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId);
}
