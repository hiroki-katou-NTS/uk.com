package nts.uk.ctx.at.record.dom.realitystatus.service;

import java.util.List;

import nts.uk.ctx.at.record.dom.adapter.request.service.ApprovalStatusEmployeeImport;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.SumCountOutput;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.UseSetingOutput;

public interface RealityStatusService {
	UseSetingOutput getUseSetting();

	SumCountOutput getApprovalSttConfirmWkpResults(List<ApprovalStatusEmployeeImport> listEmp, String wkpId,
			UseSetingOutput useSeting);
}
