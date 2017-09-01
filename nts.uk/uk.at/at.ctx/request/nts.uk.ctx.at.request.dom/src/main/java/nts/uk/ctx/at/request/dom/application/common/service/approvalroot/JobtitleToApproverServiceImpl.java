package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.JobtitleSearchSetAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.JobtitleSearchSetAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.dto.ApproverInfo;

/**
 * 3.職位から承認者へ変換する
 * 
 * @author vunv
 *
 */
@Stateless
public class JobtitleToApproverServiceImpl implements JobtitleToApproverService {

	@Inject
	private EmployeeAdaptor employeeAdaptor;

	@Inject
	private GetSubjectOfJobTitleService getSubjectOfJobTitleService;

	@Inject
	private JobtitleSearchSetAdaptor jobtitleSearchSetAdaptor;
	
	@Override
	public List<ApproverInfo> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		// 共通アルゴリズム「申請者の職位の序列は承認者のと比較する」を実行する
		boolean isSameRank = compareRank(cid, sid, baseDate, jobTitleId);
		if (isSameRank) {
			String wkpId = this.employeeAdaptor.getWorkplaceId(cid, sid, baseDate);
			// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
			List<ApproverInfo> approvers = this.getSubjectOfJobTitleService.getByWkp(cid, wkpId, baseDate, jobTitleId);
			if (!CollectionUtil.isEmpty(approvers)) {
				return approvers;
			}

			// lấy domain 「職位別のサーチ設定」
			JobtitleSearchSetAdaptorDto job = this.jobtitleSearchSetAdaptor.finById(cid, jobTitleId);
			if (!Objects.isNull(job)) {
				List<String> wkpIds = this.employeeAdaptor.findWpkIdsBySid(cid, sid, baseDate);
				wkpIds.remove(0);

				// 上位職場が存在する(not exist wkpId 上位)
				if (CollectionUtil.isEmpty(wkpIds)) {
					return null;
				}

				// 上位職場の先頭から最後ループ
				for (String id : wkpIds) {
					// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
					List<ApproverInfo> approversByWkp = this.getSubjectOfJobTitleService.getByWkp(cid, id, baseDate,
							jobTitleId);
					// If exist break and return
					if (!CollectionUtil.isEmpty(approversByWkp)) {
						return approversByWkp;
					}
				}
			}
		}

		return null;
	}

	@Override
	public boolean compareRank(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		String empCode = this.employeeAdaptor.getEmploymentCode(cid, sid, baseDate);
		
		
		return true;
	}

}
