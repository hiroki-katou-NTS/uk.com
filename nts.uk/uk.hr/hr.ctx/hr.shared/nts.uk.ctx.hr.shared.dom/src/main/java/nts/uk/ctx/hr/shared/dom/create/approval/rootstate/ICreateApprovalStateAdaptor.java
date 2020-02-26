/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.create.approval.rootstate;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootContentHrExport;

/**
 * @author laitv
 *
 */
public interface ICreateApprovalStateAdaptor {
	
	
	// lấy RequestList 637
	// ApprovalRootContentHrExport : là đầu ra của ReqList 309
	public boolean createApprStateHr(ApprovalRootContentHrExport apprSttHr, String rootStateID, String employeeID, GeneralDate appDate);

}
