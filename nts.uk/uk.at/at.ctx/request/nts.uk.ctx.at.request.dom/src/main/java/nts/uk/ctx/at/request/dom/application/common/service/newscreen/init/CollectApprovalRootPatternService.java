package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;

/**
 * 1-4.新規画面起動時の承認ルート取得パターン
 * @author Doan Duy Hung
 *
 */
public interface CollectApprovalRootPatternService {
	
	public ApprovalRootPattern getApprovalRootPatternService(String companyID, String employeeID, 
			EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate appDate, String appID, Boolean isCreate);
	
	public ApprovalRootContentImport_New getgetApprovalRootPatternNew(String companyID, String employeeID, 
			EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate appDate);
}
