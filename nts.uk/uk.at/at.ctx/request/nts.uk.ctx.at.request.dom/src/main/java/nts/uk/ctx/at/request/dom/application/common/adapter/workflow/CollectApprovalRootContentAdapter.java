package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface CollectApprovalRootContentAdapter {
	
	public ApprovalRootContentImport_New getApprovalRootContent(String companyID, String employeeID, Integer appTypeValue, GeneralDate appDate);
	
}
