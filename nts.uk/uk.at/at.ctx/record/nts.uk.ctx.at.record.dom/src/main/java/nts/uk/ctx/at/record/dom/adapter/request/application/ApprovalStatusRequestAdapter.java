package nts.uk.ctx.at.record.dom.adapter.request.application;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.ApprovalStatusMailTempImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.EmployeeUnconfirmImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.RealityStatusEmployeeImport;

public interface ApprovalStatusRequestAdapter {
	public List<RealityStatusEmployeeImport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd);

	public ApprovalStatusMailTempImport getApprovalStatusMailTemp(int type);

	public List<EmployeeUnconfirmImport> getListEmployeeEmail(List<String> listSId);
	
	String confirmApprovalStatusMailSender();
}
