package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootServiceImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.EmploymentRootAtrImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.RootTypeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.SystemAtrImport;

public interface CollectApprovalRootServiceAdapter {
	
	public default  ApprovalRootServiceImport createDefaultApprovalRootApp(String companyID, String employeeID,
			String targetType, GeneralDate standardDate, String appId, GeneralDate appDate) {
		return createApprovalRootOfSubjectRequest(companyID, employeeID, EmploymentRootAtrImport.APPLICATION, 
				targetType, standardDate, 
				SystemAtrImport.WORK,
				Optional.empty(), 
				RootTypeImport.EMPLOYMENT_APPLICATION, appId, appDate);
	}
	
	// 承認ルートを登録する
	public ApprovalRootServiceImport createApprovalRootOfSubjectRequest(String companyID, String employeeID,
			EmploymentRootAtrImport rootAtr, String targetType, GeneralDate standardDate, SystemAtrImport sysAtr,
			Optional<Boolean> lowerApprove, RootTypeImport rootType, String appId, GeneralDate appDate);
}
