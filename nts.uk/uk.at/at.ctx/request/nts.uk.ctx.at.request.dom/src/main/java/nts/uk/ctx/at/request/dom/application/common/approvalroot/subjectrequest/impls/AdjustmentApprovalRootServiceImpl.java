package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.impls;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.AdjustmentApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.JobtitleToApproverService;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.dto.ApproverInfo;

/**
 * 2.承認ルートを整理する
 * 
 * @author vunv
 *
 */
@Stateless
public class AdjustmentApprovalRootServiceImpl implements AdjustmentApprovalRootService {

	@Inject
	private JobtitleToApproverService jobtitleToAppService;

	@Override
	public void adjustmentApprovalRootData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalPhaseAdaptorDto> appPhases) {
		for (ApprovalPhaseAdaptorDto phase : appPhases) {
			List<ApproverAdaptorDto> approvers = phase.getApproverDtos();
			if (!CollectionUtil.isEmpty(approvers)) {
				List<ApproverInfo> approversResult = new ArrayList<>();
				approvers.stream().forEach(x -> {
					// 個人の場合
					if (x.getApprovalAtr() == 0) {
						approversResult.add(new ApproverInfo(x.getEmployeeId(), true, x.getOrderNumber()));
					} else if (x.getApprovalAtr() == 1) {
						// 職位の場合
						List<ApproverInfo> approversOfJob = this.jobtitleToAppService.convertToApprover(cid, sid,
								baseDate, x.getJobTitleId());
						approversResult.addAll(approversOfJob);
					}
				});
				
				if (CollectionUtil.isEmpty(approversResult)) {
					
				}
			}
		}

	}

}
