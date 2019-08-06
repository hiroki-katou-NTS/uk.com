package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;

@Stateless
public class DetailScreenBeforeImpl implements DetailScreenBefore {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Override
	public List<ApprovalPhaseStateImport_New> getApprovalDetail(String appID) {
		return approvalRootStateAdapter.getApprovalDetail(appID);
	}

}
