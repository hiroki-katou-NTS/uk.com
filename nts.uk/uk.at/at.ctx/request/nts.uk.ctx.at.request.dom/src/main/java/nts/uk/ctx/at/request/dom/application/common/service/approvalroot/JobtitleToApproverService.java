package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.dto.ApproverInfo;

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

	/**
	 * 4.申請者の職位の序列は承認者のと比較する
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param jobTitleId
	 */
	public boolean compareRank(String cid, String sid, GeneralDate baseDate, String jobTitleId);
}
