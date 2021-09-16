package nts.uk.ctx.workflow.pub.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootServiceExport;
import nts.uk.ctx.workflow.pub.service.export.EmploymentRootAtrExport;
import nts.uk.ctx.workflow.pub.service.export.RootTypeExport;
import nts.uk.ctx.workflow.pub.service.export.SystemAtrExport;

public interface CollectApprovalRootServicePub {
	
	//承認ルートを登録する
	public ApprovalRootServiceExport createApprovalRootOfSubjectRequest(
			String companyID, 
			String employeeID, 
			EmploymentRootAtrExport rootAtr, 
			String appType, 
			GeneralDate standardDate,
			SystemAtrExport sysAtr,
			Optional<Boolean> lowerApprove,
			RootTypeExport rootType,
			String appId, GeneralDate appDate);
}
