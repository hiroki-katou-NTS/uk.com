package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApproverInfo;

/**
 * 6.職場に指定する職位の対象者を取得する
 * 
 * @author vunv
 *
 */
public interface GetSubjectOfJobTitleService {

	/**
	 * 6.職場に指定する職位の対象者を取得する
	 * 
	 * @param cid
	 * @param wkpId 職場ID（申請本人の所属職場）
	 * @param baseDate
	 * @param jobTitleId 職位ID（承認者）
	 */
	public List<ApproverInfo> getByWkp(String cid, String wkpId, GeneralDate baseDate, String jobTitleId);
}
