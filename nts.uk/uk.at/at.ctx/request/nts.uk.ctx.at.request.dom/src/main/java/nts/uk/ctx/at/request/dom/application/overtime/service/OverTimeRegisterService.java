package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;

public interface OverTimeRegisterService {
	public void register(
			String companyId,
			AppOverTime appOverTime,
			List<ApprovalPhaseStateImport_New> lstApproval,
			Boolean mailServerSet);
	
}
