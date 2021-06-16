package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 */
public interface ApprovalStatusPub {
	List<ApprovalStatusEmployeeExport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd);

	ApprovalStatusMailTempExport getApprovalStatusMailTemp(int transmissionAttr);

	List<EmployeeEmailExport> getListEmployeeEmail(List<String> listSId);
	
	String confirmApprovalStatusMailSender();
}
